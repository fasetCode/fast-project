package com.fastproject.message.vo.config;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 消息配置查询对象
 */
@Getter
@Setter
public class MessageConfigQuery extends PageQuery {

    /**
     * 配置标题
     */
    private String title;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 状态：1-正常，2-禁用
     */
    private Integer status;
}