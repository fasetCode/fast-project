package com.fastproject.ratelimit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 黑白名单类型
 */
@Getter
@AllArgsConstructor
public enum BlackWhiteListType {

    /**
     * 黑名单
     */
    BLACK("黑名单"),

    /**
     * 白名单
     */
    WHITE("白名单");

    private final String description;
}
