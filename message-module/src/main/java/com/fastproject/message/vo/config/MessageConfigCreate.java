package com.fastproject.message.vo.config;

import lombok.Data;

/**
 * 消息配置创建对象
 */
@Data
public class MessageConfigCreate {

    /**
     * 配置标题
     */
    private String title;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 配置内容
     */
    private String config;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：1-正常，2-禁用
     */
    private Integer status;
}