package com.fastproject.message.vo.template;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 消息模版查询 VO
 */
@Getter
@Setter
public class MessageTemplateQuery extends PageQuery {

    /**
     * 模版标题
     */
    private String title;

    /**
     * 模版代码
     */
    private String code;

    /**
     * 模版类型ID
     */
    private Long typeId;

    /**
     * 状态
     */
    private Integer status;
}