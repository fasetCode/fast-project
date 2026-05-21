package com.fastproject.message.vo.type;

import lombok.Data;

/**
 * 消息类型更新 VO
 */
@Data
public class MessageTypeUpdate {

    /**
     * ID
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 代码
     */
    private String code;
}