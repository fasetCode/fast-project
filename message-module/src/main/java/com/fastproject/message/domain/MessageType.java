package com.fastproject.message.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "message_type")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update message_type set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MessageType extends BaseEntity {

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
