package com.fastproject.message.vo.test;

import lombok.Data;

/**
 * 验证码测试验证对象
 */
@Data
public class MessageTestVerify {
    /**
     * 目标（手机号/邮箱）
     */
    private String target;
    /**
     * 验证码
     */
    private String code;
}
