package com.fastproject.message.vo.verificationcode;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 验证码查询 VO
 */
@Getter
@Setter
public class MessageVerificationCodeQuery extends PageQuery {

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
}