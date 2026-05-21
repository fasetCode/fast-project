package com.fastproject.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 验证码状态枚举
 */
@Getter
@RequiredArgsConstructor
public enum MessageVerificationCodeStatusEnum {

    /**
     * 有效
     */
    VALID(1, "有效"),

    /**
     * 已使用
     */
    USED(2, "已使用"),

    /**
     * 已过期
     */
    EXPIRED(3, "已过期");

    private final Integer code;
    private final String description;
}
