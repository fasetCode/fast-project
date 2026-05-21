package com.fastproject.ratelimit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * IP限制类型
 */
@Getter
@AllArgsConstructor
public enum IpType {

    /**
     * 全部IP
     */
    ALL("全部IP"),

    /**
     * 单个IP
     */
    SINGLE("单个IP"),

    /**
     * IP段
     */
    SEGMENT("IP段");

    private final String description;
}
