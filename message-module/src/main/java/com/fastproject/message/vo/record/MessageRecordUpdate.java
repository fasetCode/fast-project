package com.fastproject.message.vo.record;

import lombok.Data;

/**
 * 消息记录更新对象
 */
@Data
public class MessageRecordUpdate {
    /**
     * ID
     */
    private Long id;
    /**
     * 消息配置ID
     */
    private Long configId;
    /**
     * 接收者
     */
    private String receiver;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 状态
     */
    private String status;
    /**
     * 标题
     */
    private String title;
    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 操作人ID
     */
    private Long operatorId;
    /**
     * 用户类型
     */
    private String userType;
}
