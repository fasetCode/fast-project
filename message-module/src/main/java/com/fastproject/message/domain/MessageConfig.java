package com.fastproject.message.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "message_config")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update message_config set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MessageConfig extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 配置类型 对应 字典
     */
    private String type;

    /**
     * 配置信息
     */
    private String config;

    /**
     * 配置描述
     */
    private String description;

    /**
     * 数据状态
     */
    private Integer status;

}
