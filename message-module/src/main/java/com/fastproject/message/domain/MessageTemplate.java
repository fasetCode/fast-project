package com.fastproject.message.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "message_template")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update message_template set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MessageTemplate extends BaseEntity {

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
     * 模版类型
     */
    private Long typeId;

}
