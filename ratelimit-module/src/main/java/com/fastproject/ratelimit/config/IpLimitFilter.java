package com.fastproject.ratelimit.config;

import com.fastproject.jedis.JedisTemplate;
import com.fastproject.ratelimit.enums.RateLimitType;
import com.fastproject.ratelimit.service.IpRateLimitConfigService;
import com.fastproject.ratelimit.service.RateLimitRecordService;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigVo;
import com.fastproject.ratelimit.vo.record.RateLimitRecordCreate;
import com.fastproject.utils.IpUtils;
import com.fastproject.utils.SpringContextUtil;
import com.fastproject.utils.TokenUtils;
import com.fastproject.utils.utils.JsonUtils;
import com.fastproject.utils.vo.ResultVo;
import com.fastproject.vo.SecurityUserVo;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * IP限流过滤器 - 采用漏桶算法 (Leaky Bucket)
 */
@Component
@Order(90000) // 在全局限流之前执行
@RequiredArgsConstructor
@Slf4j
public class IpLimitFilter implements Filter {

    private static final String IP_CONFIG_CACHE_PREFIX = "ip_rate_limit_config:";
    private static final String REDIS_IP_LEAKY_BUCKET_PREFIX = "ratelimit:ip:leaky_bucket:";
    private static final int BATCH_TOKEN_SIZE = 5;

    // 使用 Caffeine 缓存 IP 配置，过期时间 10 秒
    private final Cache<String, Optional<IpRateLimitConfigVo>> configCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .maximumSize(10000)
            .build();

    // 本地令牌缓存，减少 Redis 请求频率
    private final Cache<String, Integer> tokenCache = Caffeine.newBuilder()
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .maximumSize(100000)
            .build();

    private final RateLimitProps rateLimitProps;

    // 漏桶算法 Lua 脚本
    private static final String LEAKY_BUCKET_LUA_SCRIPT =
            "local key = KEYS[1]\n" +
            "local capacity = tonumber(ARGV[1])\n" +
            "local leak_rate = tonumber(ARGV[2])\n" +
            "local now = tonumber(ARGV[3])\n" +
            "local requested = tonumber(ARGV[4])\n" +
            "local bucket = redis.call('HMGET', key, 'water', 'last_time')\n" +
            "local water = tonumber(bucket[1]) or 0\n" +
            "local last_time = tonumber(bucket[2]) or now\n" +
            "local delta_time = math.max(0, now - last_time)\n" +
            "local leaked = delta_time * leak_rate / 1000.0\n" +
            "water = math.max(0, water - leaked)\n" +
            "if water + requested <= capacity then\n" +
            "    water = water + requested\n" +
            "    redis.call('HMSET', key, 'water', water, 'last_time', now)\n" +
            "    redis.call('EXPIRE', key, math.ceil(capacity / leak_rate) + 60)\n" +
            "    return 1\n" +
            "else\n" +
            "    redis.call('HMSET', key, 'water', water, 'last_time', now)\n" +
            "    return 0\n" +
            "end";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String appCode = rateLimitProps.getAppCode();
        String ip = IpUtils.getIp(httpRequest);

        if (!StringUtils.hasText(ip)) {
            chain.doFilter(request, response);
            return;
        }

        // 获取匹配的 IP 配置
        IpRateLimitConfigVo config = getMatchingConfig(appCode, ip);
        if (config != null && Boolean.TRUE.equals(config.getEnabled())) {
            boolean b = tryAcquire(appCode, ip, config);
            // 限流校验
            if (!b) {
                handleReject(httpRequest, httpResponse, config, "RIE002");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private IpRateLimitConfigVo getMatchingConfig(String appCode, String ip) {
        String cacheKey = IP_CONFIG_CACHE_PREFIX + appCode + ":" + ip;
        Optional<IpRateLimitConfigVo> configOpt = configCache.get(cacheKey, key -> {
            IpRateLimitConfigService service = SpringContextUtil.getBean(IpRateLimitConfigService.class);
            return Optional.ofNullable(service.findMatchingConfig(appCode, ip));
        });
        return configOpt != null ? configOpt.orElse(null) : null;
    }

    private boolean tryAcquire(String appCode, String ip, IpRateLimitConfigVo config) {
        String redisKey = REDIS_IP_LEAKY_BUCKET_PREFIX + appCode + ":" + ip;
        String localCacheKey = appCode + ":" + ip;

        // 1. 尝试从本地缓存获取令牌
        AtomicBoolean localAcquired = new AtomicBoolean(false);
        tokenCache.asMap().computeIfPresent(localCacheKey, (k, v) -> {
            if (v > 0) {
                localAcquired.set(true);
                return v - 1;
            }
            return v;
        });

        if (localAcquired.get()) {
            return true;
        }

        long maxRequests = config.getMaxRequests();
        int timeWindow = config.getTimeWindow();
        long burstCapacity = config.getBurstCapacity() != null ? config.getBurstCapacity() : maxRequests;

        // 漏水速率 (tokens per second)
        double leakRate = (double) maxRequests / timeWindow;
        long now = System.currentTimeMillis();

        // 批量获取大小，不能超过桶容量
        int batchSize = (int) Math.min(BATCH_TOKEN_SIZE, burstCapacity);

        try {
            JedisTemplate jedisTemplate = SpringContextUtil.getBean(JedisTemplate.class);

            // 2. 尝试批量获取
            Object result = jedisTemplate.eval(
                    LEAKY_BUCKET_LUA_SCRIPT,
                    Collections.singletonList(redisKey),
                    Arrays.asList(
                            String.valueOf(burstCapacity),
                            String.valueOf(leakRate),
                            String.valueOf(now),
                            String.valueOf(batchSize)
                    )
            );

            if ("1".equals(String.valueOf(result))) {
                if (batchSize > 1) {
                    tokenCache.put(localCacheKey, batchSize - 1);
                }
                return true;
            }

            // 3. 批量获取失败，尝试只获取 1 个（保底逻辑）
            if (batchSize > 1) {
                result = jedisTemplate.eval(
                        LEAKY_BUCKET_LUA_SCRIPT,
                        Collections.singletonList(redisKey),
                        Arrays.asList(
                                String.valueOf(burstCapacity),
                                String.valueOf(leakRate),
                                String.valueOf(now),
                                "1"
                        )
                );
                return "1".equals(String.valueOf(result));
            }

            return false;
        } catch (Exception e) {
            log.error("执行IP限流漏桶算法异常", e);
            return true; // 异常降级放行
        }
    }

    private void handleReject(HttpServletRequest request, HttpServletResponse response, IpRateLimitConfigVo config, String reason) throws IOException {
        log.warn("IP限流/拦截触发：IP={}, 原因={}", IpUtils.getIp(request), reason);
        logLimitRecord(request, config, reason);
        response.setStatus(429);
        response.setContentType("application/json;charset=utf-8");
        ResultVo<Object> failResult = ResultVo.fail(429, reason + "，请稍候再试");
        response.getWriter().write(JsonUtils.toJson(failResult));
    }

    private void logLimitRecord(HttpServletRequest request, IpRateLimitConfigVo config, String reason) {
        try {
            RateLimitRecordService recordService = SpringContextUtil.getBean(RateLimitRecordService.class);
            TokenUtils tokenUtils = SpringContextUtil.getBean(TokenUtils.class);
            SecurityUserVo user = tokenUtils.getUser();

            RateLimitRecordCreate create = new RateLimitRecordCreate();
            create.setAppCode(rateLimitProps.getAppCode());
            create.setLimitKey("ip_limit:" + config.getIpType() + ":" + (config.getIpAddress() != null ? config.getIpAddress() : "ALL"));
            create.setLimitType(RateLimitType.IP);
            create.setTargetValue(IpUtils.getIp(request));
            create.setMethod(request.getMethod());
            create.setUrl(request.getRequestURI());
            create.setIp(IpUtils.getIp(request));
            if (user != null) {
                create.setUserId(user.getUserId());
            }

            // Headers
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames != null && headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                headers.put(name, request.getHeader(name));
            }
            create.setHeaders(JsonUtils.toJson(headers));
            create.setQueryParams(request.getQueryString());

            String limitReason = reason + " (配置: " + config.getMaxRequests() + "/" + config.getTimeWindow() + "s, 突发: " + config.getBurstCapacity() + ")";
            create.setLimitReason(limitReason);

            recordService.save(create);
        } catch (Exception e) {
            log.error("记录IP限流日志失败", e);
        }
    }
}
