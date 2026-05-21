package com.fastproject.message.vo.template;

import lombok.Data;

/**
 * 消息模版更新 VO
 */
@Data
public class MessageTemplateUpdate {

    /**
     * ID
     */
    private Long id;

    /**
     * 模版代码
     */
    private String code;

    /**
     * 模版标题
     */
    private String title;

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 内容
     */
    private String content;

    /**
     * 模版类型ID
     */
    private Long typeId;
}