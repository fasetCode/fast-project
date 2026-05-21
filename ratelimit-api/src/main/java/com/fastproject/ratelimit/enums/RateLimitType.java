package com.fastproject.ratelimit.enums;

/**
 * 限流类型
 */
public enum RateLimitType {
    /**
     * 全局限流
     */
    GLOBAL,
    /**
     * IP限流
     */
    IP,
    /**
     * 用户限流
     */
    USER,
    /**
     * API限流
     */
    API
}
