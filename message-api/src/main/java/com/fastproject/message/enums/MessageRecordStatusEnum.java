package com.fastproject.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 消息记录状态枚举
 */
@Getter
@RequiredArgsConstructor
public enum MessageRecordStatusEnum {

    /**
     * 已发送
     */
    SENT("SENT", "已发送"),

    /**
     * 发送失败
     */
    FAILED("FAILED", "发送失败");


    private final String code;
    private final String description;
}
