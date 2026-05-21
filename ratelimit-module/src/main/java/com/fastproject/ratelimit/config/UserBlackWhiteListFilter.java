package com.fastproject.ratelimit.config;

import com.fastproject.ratelimit.enums.BlackWhiteListType;
import com.fastproject.ratelimit.enums.RateLimitType;
import com.fastproject.ratelimit.service.RateLimitRecordService;
import com.fastproject.ratelimit.service.UserBlackWhiteListService;
import com.fastproject.ratelimit.vo.record.RateLimitRecordCreate;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListVo;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 用户黑白名单过滤器
 */
@Component
@Order(89001) // 在 IP黑白名单之后，限流之前执行
@RequiredArgsConstructor
@Slf4j
public class UserBlackWhiteListFilter implements Filter {

    private static final String CACHE_PREFIX = "user_bw_list:";

    private final Cache<String, Optional<UserBlackWhiteListVo>> configCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .maximumSize(10000)
            .build();

    private final RateLimitProps rateLimitProps;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!rateLimitProps.isEnableUserBwFilter()) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 尝试获取当前登录用户
        TokenUtils tokenUtils;
        try {
            tokenUtils = SpringContextUtil.getBean(TokenUtils.class);
        } catch (Exception e) {
            // 如果获取不到 TokenUtils（例如没有引入common），直接放行
            chain.doFilter(request, response);
            return;
        }

        SecurityUserVo user = tokenUtils.getUser();
        if (user == null || user.getUserId() == null) {
            // 未登录请求，不进行用户名单拦截
            chain.doFilter(request, response);
            return;
        }

        String appCode = rateLimitProps.getAppCode();
        Long userId = user.getUserId();

        UserBlackWhiteListVo config = getMatchingConfig(appCode, userId);
        if (config != null && Boolean.TRUE.equals(config.getEnabled())) {
            // 黑名单直接拒绝
            if (BlackWhiteListType.BLACK.equals(config.getListType())) {
                String reason = StringUtils.hasText(config.getLimitMsg()) ? config.getLimitMsg() : "您的账号已被列入黑名单";
                handleReject(httpRequest, httpResponse, config, user, reason);
                return;
            }
            // 白名单直接放行
            if (BlackWhiteListType.WHITE.equals(config.getListType())) {
                chain.doFilter(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private UserBlackWhiteListVo getMatchingConfig(String appCode, Long userId) {
        String cacheKey = CACHE_PREFIX + appCode + ":" + userId;
        Optional<UserBlackWhiteListVo> configOpt = configCache.get(cacheKey, key -> {
            UserBlackWhiteListService service = SpringContextUtil.getBean(UserBlackWhiteListService.class);
            return Optional.ofNullable(service.checkUser(appCode, userId));
        });
        return configOpt != null ? configOpt.orElse(null) : null;
    }

    private void handleReject(HttpServletRequest request, HttpServletResponse response, UserBlackWhiteListVo config, SecurityUserVo user, String reason) throws IOException {
        log.warn("用户黑名单拦截触发：userId={}, 原因={}", user.getUserId(), reason);
        logLimitRecord(request, config, user, reason);
        response.setStatus(403);
        response.setContentType("application/json;charset=utf-8");
        ResultVo<Object> failResult = ResultVo.fail(403, reason);
        response.getWriter().write(JsonUtils.toJson(failResult));
    }

    private void logLimitRecord(HttpServletRequest request, UserBlackWhiteListVo config, SecurityUserVo user, String reason) {
        try {
            RateLimitRecordService recordService = SpringContextUtil.getBean(RateLimitRecordService.class);

            RateLimitRecordCreate create = new RateLimitRecordCreate();
            create.setAppCode(rateLimitProps.getAppCode());
            create.setLimitKey("user_bw_limit:" + config.getUserId());
            create.setLimitType(RateLimitType.GLOBAL); // 暂且复用全局类型
            create.setTargetValue(String.valueOf(config.getUserId()));
            create.setMethod(request.getMethod());
            create.setUrl(request.getRequestURI());
            create.setIp(IpUtils.getIp(request));
            create.setUserId(user.getUserId());

            // Headers
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames != null && headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                headers.put(name, request.getHeader(name));
            }
            create.setHeaders(JsonUtils.toJson(headers));
            create.setQueryParams(request.getQueryString());

            create.setLimitReason("触发用户黑名单拦截: " + reason);

            recordService.save(create);
        } catch (Exception e) {
            log.error("记录用户黑名单拦截日志失败", e);
        }
    }
}
