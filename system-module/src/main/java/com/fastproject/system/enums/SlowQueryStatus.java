package com.fastproject.system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 慢查询处理状态枚举
 */
@Getter
@RequiredArgsConstructor
public enum SlowQueryStatus {

    /**
     * 需要处理 (默认)
     */
    PENDING("需要处理"),

    /**
     * 不需要处理 (已忽略)
     */
    IGNORED("不需要处理");

    private final String description;

    /**
     * 根据描述获取枚举
     */
    public static SlowQueryStatus fromDescription(String description) {
        for (SlowQueryStatus status : values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        return null;
    }
}
