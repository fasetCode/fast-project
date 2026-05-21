package com.fastproject.message.vo.type;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 消息类型查询 VO
 */
@Getter
@Setter
public class MessageTypeQuery extends PageQuery {

    /**
     * 标题
     */
    private String title;

    /**
     * 代码
     */
    private String code;

    /**
     * 状态
     */
    private Integer status;
}