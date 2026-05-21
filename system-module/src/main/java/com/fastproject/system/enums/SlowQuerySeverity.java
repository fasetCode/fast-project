package com.fastproject.system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 慢查询严重程度枚举
 */
@Getter
@RequiredArgsConstructor
public enum SlowQuerySeverity {

    /**
     * 警告 (200-500ms)
     */
    WARNING("警告"),

    /**
     * 严重 (500-1000ms)
     */
    CRITICAL("严重"),

    /**
     * 严重警告 (>1000ms)
     */
    FATAL("严重警告");

    private final String description;

    /**
     * 根据描述获取枚举
     */
    public static SlowQuerySeverity fromDescription(String description) {
        for (SlowQuerySeverity severity : values()) {
            if (severity.getDescription().equals(description)) {
                return severity;
            }
        }
        return null;
    }
}
