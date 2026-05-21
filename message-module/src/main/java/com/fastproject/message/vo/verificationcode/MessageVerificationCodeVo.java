package com.fastproject.message.vo.verificationcode;

import lombok.Data;

/**
 * 验证码 VO
 */
@Data
public class MessageVerificationCodeVo {

    /**
     * ID
     */
    private Long id;

    /**
     * 验证码
     */
    private String code;

    /**
     * 发送目标
     */
    private String target;

    /**
     * 配置Id
     */
    private Long configId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 业务数据
     */
    private String businessData;

    /**
     * 过期时间
     */
    private Long expireTime;
}