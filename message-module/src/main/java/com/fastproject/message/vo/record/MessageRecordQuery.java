package com.fastproject.message.vo.record;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 消息记录查询对象
 */
@Getter
@Setter
public class MessageRecordQuery extends PageQuery {
    /**
     * 标题
     */
    private String title;
    /**
     * 接收者
     */
    private String receiver;
    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 状态
     */
    private String status;
}
