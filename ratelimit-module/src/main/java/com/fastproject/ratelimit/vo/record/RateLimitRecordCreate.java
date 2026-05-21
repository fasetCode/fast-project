package com.fastproject.ratelimit.vo.record;

import com.fastproject.ratelimit.enums.RateLimitType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 限流记录创建
 */
@Data
public class RateLimitRecordCreate {

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 限流标识Key
     */
    private String limitKey;

    /**
     * 限流类型
     */
    private RateLimitType limitType;

    /**
     * 目标值(IP/用户ID等)
     */
    private String targetValue;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 请求头
     */
    private String headers;

    /**
     * 查询参数
     */
    private String queryParams;

    /**
     * 触发限制的原因
     */
    private String limitReason;
}