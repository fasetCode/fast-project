package com.fastproject.ratelimit.config;

import com.fastproject.jedis.JedisTemplate;
import com.fastproject.ratelimit.service.GlobalRateLimitConfigService;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigVo;
import com.fastproject.utils.SpringContextUtil;
import com.fastproject.utils.TokenUtils;
import com.fastproject.utils.utils.JsonUtils;
import com.fastproject.utils.vo.ResultVo;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.fastproject.ratelimit.enums.RateLimitType;
import com.fastproject.ratelimit.service.RateLimitRecordService;
import com.fastproject.ratelimit.vo.record.RateLimitRecordCreate;
import com.fastproject.utils.IpUtils;
import com.fastproject.vo.SecurityUserVo;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 全局限流
 */
@Component
@Order(100000)
@RequiredArgsConstructor
public class GlobalLimitFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(GlobalLimitFilter.class);

    private static final String CONFIG_CACHE_KEY = "global_rate_limit_config";

    // 使用 Caffeine 缓存配置，过期时间 30 秒
    private final Cache<String, GlobalRateLimitConfigVo> configCache = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .maximumSize(10)
            .build();

    private final RateLimitProps rateLimitProps;

    // 本地令牌缓存
    private final AtomicLong localTokens = new AtomicLong(0);
    private final Object lock = new Object();
    // 当 Redis 明确无令牌时，短时间内的冷却，防止高并发下持续穿透 Redis
    private volatile long lastRedisEmptyTime = 0;
    private static final long FETCH_COOLDOWN_MS = 50;

    // 令牌桶限流 Lua 脚本（支持批量获取令牌，避免频繁请求Redis）
    private static final String TOKEN_BUCKET_BATCH_LUA_SCRIPT =
            "local key = KEYS[1]\n" +
            "local capacity = tonumber(ARGV[1])\n" +
            "local rate = tonumber(ARGV[2])\n" +
            "local now = tonumber(ARGV[3])\n" +
            "local request_cost = tonumber(ARGV[4])\n" +
            "local batch_size = tonumber(ARGV[5])\n" +
            "local last_tokens = tonumber(redis.call('HGET', key, 'tokens'))\n" +
            "if last_tokens == nil then\n" +
            "    last_tokens = capacity\n" +
            "end\n" +
            "local last_refreshed = tonumber(redis.call('HGET', key, 'timestamp'))\n" +
            "if last_refreshed == nil then\n" +
            "    last_refreshed = now\n" +
            "end\n" +
            "local delta = math.max(0, now - last_refreshed)\n" +
            "local filled_tokens = math.min(capacity, last_tokens + (delta * rate / 1000.0))\n" +
            "local available_requests = math.floor(filled_tokens / request_cost)\n" +
            "local granted_requests = math.min(available_requests, batch_size)\n" +
            "if granted_requests > 0 then\n" +
            "    local cost = granted_requests * request_cost\n" +
            "    local new_tokens = filled_tokens - cost\n" +
            "    redis.call('HMSET', key, 'tokens', new_tokens, 'timestamp', now)\n" +
            "    local ttl = math.ceil((capacity / rate) * 2)\n" +
            "    if ttl < 10 then ttl = 10 end\n" +
            "    redis.call('EXPIRE', key, ttl)\n" +
            "else\n" +
            "    redis.call('HMSET', key, 'tokens', filled_tokens, 'timestamp', now)\n" +
            "    local ttl = math.ceil((capacity / rate) * 2)\n" +
            "    if ttl < 10 then ttl = 10 end\n" +
            "    redis.call('EXPIRE', key, ttl)\n" +
            "end\n" +
            "return granted_requests\n";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        GlobalRateLimitConfigService rateLimitConfigService = SpringContextUtil.getBean(GlobalRateLimitConfigService.class);

        String appCode = rateLimitProps.getAppCode();
        String configCacheKey = StringUtils.hasText(appCode) ? CONFIG_CACHE_KEY + ":" + appCode : CONFIG_CACHE_KEY;
        String redisKey = StringUtils.hasText(appCode) ? "ratelimit:global:token_bucket:" + appCode : "ratelimit:global:token_bucket";

        // 使用 Caffeine 缓存获取配置，避免每次请求都查数据库
        GlobalRateLimitConfigVo enabledConfig = configCache.get(configCacheKey, key -> {
            GlobalRateLimitConfigVo config = rateLimitConfigService.findEnabledConfig();
            // 如果数据库中没有启用的配置，返回一个标识为未启用的空对象，避免缓存穿透
            if (config == null) {
                config = new GlobalRateLimitConfigVo();
                config.setEnabled(false);
            }
            return config;
        });

        if (enabledConfig != null && Boolean.TRUE.equals(enabledConfig.getEnabled())) {
            Long maxRequests = enabledConfig.getMaxRequests();
            Integer timeWindow = enabledConfig.getTimeWindow();
            Long burstCapacity = enabledConfig.getBurstCapacity();

            if (maxRequests != null && timeWindow != null && maxRequests > 0 && timeWindow > 0) {
                // 如果没有设置突发容量，默认等于最大请求数
                if (burstCapacity == null || burstCapacity <= 0) {
                    burstCapacity = maxRequests;
                }

                boolean allowed = tryAcquire(redisKey, maxRequests, timeWindow, burstCapacity);

                if (!allowed) {
                    log.warn("全局限流触发：当前系统访问人数过多");
                    logLimitRequest(request, enabledConfig, "全局限流触发");
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.setStatus(429);
                    httpResponse.setContentType("application/json;charset=utf-8");
                    ResultVo<Object> failResult = ResultVo.fail(429, "当前系统访问人数过多，请稍候再试");
                    httpResponse.getWriter().write(JsonUtils.toJson(failResult));
                    return;
                }
            }
        }

        

        chain.doFilter(request, response);
    }

    // 获取记录限流记录
    private void logLimitRequest(ServletRequest request, GlobalRateLimitConfigVo config, String reason) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            RateLimitRecordService recordService = SpringContextUtil.getBean(RateLimitRecordService.class);
            TokenUtils tokenUtils = SpringContextUtil.getBean(TokenUtils.class);
            SecurityUserVo user = tokenUtils.getUser();

            RateLimitRecordCreate create = new RateLimitRecordCreate();
            create.setAppCode(rateLimitProps.getAppCode());
            create.setLimitKey("global_rate_limit");
            create.setLimitType(RateLimitType.GLOBAL);
            create.setTargetValue(IpUtils.getIp(httpRequest));
            create.setMethod(httpRequest.getMethod());
            create.setUrl(httpRequest.getRequestURI());
            create.setIp(IpUtils.getIp(httpRequest));
            if (user != null) {
                create.setUserId(user.getUserId());
            }

            // Headers
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = httpRequest.getHeaderNames();
            while (headerNames != null && headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                headers.put(name, httpRequest.getHeader(name));
            }
            create.setHeaders(JsonUtils.toJson(headers));

            // Query Params
            create.setQueryParams(httpRequest.getQueryString());

            String limitReason = reason;
            if (config != null) {
                limitReason += " (配置阈值: " + config.getMaxRequests() + "/" + config.getTimeWindow() + "s)";
            }
            create.setLimitReason(limitReason);

            recordService.save(create);
        } catch (Exception e) {
            log.error("记录限流日志失败", e);
        }
    }


    private boolean tryAcquire(String redisKey, long maxRequests, long timeWindow, long burstCapacity) {
        // 1. 尝试从本地缓存获取令牌（快速路径）
        long current = localTokens.get();
        while (current > 0) {
            if (localTokens.compareAndSet(current, current - 1)) {
                return true; // 成功获取本地令牌
            }
            current = localTokens.get();
        }

        // 2. 本地令牌耗尽，去 Redis 批量获取
        synchronized (lock) {
            // 双重检查，防止其他线程已经获取到了批量的令牌
            current = localTokens.get();
            if (current > 0) {
                localTokens.decrementAndGet();
                return true;
            }

            // 只有 Redis 明确无令牌时才短暂冷却，避免成功取到令牌后误伤后续请求
            long nowMs = System.currentTimeMillis();
            if (nowMs - lastRedisEmptyTime < FETCH_COOLDOWN_MS) {
                return false;
            }

            // 动态计算批量大小，避免高 QPS 场景下固定 100 导致频繁回源 Redis
            long batchSize = Math.min(burstCapacity, Math.max(20L, maxRequests));

            long requestCost = timeWindow * 1000L;
            long capacity = burstCapacity * requestCost;
            long rate = maxRequests * 1000L;

            try {
                JedisTemplate jedisTemplate = SpringContextUtil.getBean(JedisTemplate.class);
                Object result = jedisTemplate.eval(
                        TOKEN_BUCKET_BATCH_LUA_SCRIPT,
                        Collections.singletonList(redisKey),
                        Arrays.asList(
                                String.valueOf(capacity),
                                String.valueOf(rate),
                                String.valueOf(nowMs),
                                String.valueOf(requestCost),
                                String.valueOf(batchSize)
                        )
                );

                if (result != null) {
                    long granted = Long.parseLong(result.toString());
                    if (granted > 0) {
                        lastRedisEmptyTime = 0;
                        // 当前请求消耗 1 个，剩下的放入本地缓存
                        if (granted > 1) {
                            localTokens.addAndGet(granted - 1);
                        }
                        return true;
                    }
                }

                lastRedisEmptyTime = nowMs;
            } catch (Exception e) {
                log.error("执行全局限流 Lua 脚本异常，降级放行", e);
                return true; // 异常降级放行
            }

            return false;
        }
    }
}
