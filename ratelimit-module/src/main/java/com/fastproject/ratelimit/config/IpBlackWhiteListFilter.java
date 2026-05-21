package com.fastproject.ratelimit.config;

import com.fastproject.ratelimit.enums.BlackWhiteListType;
import com.fastproject.ratelimit.enums.RateLimitType;
import com.fastproject.ratelimit.service.IpBlackWhiteListService;
import com.fastproject.ratelimit.service.RateLimitRecordService;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListVo;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * IP黑白名单过滤器
 */
@Component
@Order(89000) // 在 IP限流 (90000) 之前执行
@RequiredArgsConstructor
@Slf4j
public class IpBlackWhiteListFilter implements Filter {

    private static final String CACHE_PREFIX = "ip_bw_list:";

    private final Cache<String, Optional<IpBlackWhiteListVo>> configCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .maximumSize(10000)
            .build();

    private final RateLimitProps rateLimitProps;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!rateLimitProps.isEnableIpBwFilter()) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String appCode = rateLimitProps.getAppCode();
        String ip = IpUtils.getIp(httpRequest);

        if (!StringUtils.hasText(ip)) {
            chain.doFilter(request, response);
            return;
        }

        IpBlackWhiteListVo config = getMatchingConfig(appCode, ip);
        if (config != null && Boolean.TRUE.equals(config.getEnabled())) {
            // 黑名单直接拒绝
            if (BlackWhiteListType.BLACK.equals(config.getListType())) {
                String reason = StringUtils.hasText(config.getLimitMsg()) ? config.getLimitMsg() : "您的IP已被列入黑名单";
                handleReject(httpRequest, httpResponse, config, reason);
                return;
            }
            // 白名单直接放行，跳过后续限流 (或者仅做标记，这里简单处理为直接 chain.doFilter 且 return 提前终止当前过滤器)
            if (BlackWhiteListType.WHITE.equals(config.getListType())) {
                chain.doFilter(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private IpBlackWhiteListVo getMatchingConfig(String appCode, String ip) {
        String cacheKey = CACHE_PREFIX + appCode + ":" + ip;
        Optional<IpBlackWhiteListVo> configOpt = configCache.get(cacheKey, key -> {
            IpBlackWhiteListService service = SpringContextUtil.getBean(IpBlackWhiteListService.class);
            return Optional.ofNullable(service.checkIp(appCode, ip));
        });
        return configOpt != null ? configOpt.orElse(null) : null;
    }

    private void handleReject(HttpServletRequest request, HttpServletResponse response, IpBlackWhiteListVo config, String reason) throws IOException {
        log.warn("IP黑名单拦截触发：IP={}, 原因={}", IpUtils.getIp(request), reason);
        logLimitRecord(request, config, reason);
        response.setStatus(403);
        response.setContentType("application/json;charset=utf-8");
        ResultVo<Object> failResult = ResultVo.fail(403, reason);
        response.getWriter().write(JsonUtils.toJson(failResult));
    }

    private void logLimitRecord(HttpServletRequest request, IpBlackWhiteListVo config, String reason) {
        try {
            RateLimitRecordService recordService = SpringContextUtil.getBean(RateLimitRecordService.class);
            TokenUtils tokenUtils = SpringContextUtil.getBean(TokenUtils.class);
            SecurityUserVo user = tokenUtils.getUser();

            RateLimitRecordCreate create = new RateLimitRecordCreate();
            create.setAppCode(rateLimitProps.getAppCode());
            create.setLimitKey("ip_bw_limit:" + config.getIpAddress());
            create.setLimitType(RateLimitType.IP); // 复用IP限流类型
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

            create.setLimitReason("触发IP黑名单拦截: " + reason);

            recordService.save(create);
        } catch (Exception e) {
            log.error("记录IP黑名单拦截日志失败", e);
        }
    }
}
