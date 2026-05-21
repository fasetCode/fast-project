package com.fastproject.message.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "message_record")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update message_record set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MessageRecord extends BaseEntity {

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 接收人
     */
    private String receiver;

    /**
     * 内容
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
     * 操作用户
     */
    private Long operatorId;

    /**
     * 用户类型
     */
    private String userType;
}
