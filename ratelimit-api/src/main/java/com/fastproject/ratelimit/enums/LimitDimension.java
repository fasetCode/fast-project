package com.fastproject.ratelimit.enums;

/**
 * 限流维度
 */
public enum LimitDimension {
    /**
     * 全局
     */
    GLOBAL,
    /**
     * IP
     */
    IP,
    /**
     * 用户
     */
    USER
}
