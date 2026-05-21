package com.fastproject.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 消息类型枚举
 */
@Getter
@RequiredArgsConstructor
public enum MessageTypeEnum {

    /**
     * 验证码
     */
    CODE("CODE", "验证码"),

    /**
     * 通知
     */
    NOTICE("NOTICE", "通知");

    private final String code;
    private final String description;
}
