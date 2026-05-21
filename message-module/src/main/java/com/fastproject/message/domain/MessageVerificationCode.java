package com.fastproject.message.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "message_verification_code")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update message_verification_code set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MessageVerificationCode extends BaseEntity {

    /**
     * 验证码
     */
    private String code;

    /**
     * 发送目标
     */
    private String target;

    /**
     * 配置Id
     */
    private Long configId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 业务数据
     */
    private String businessData;

    /**
     * 过期时间
     */
    private Long expireTime;
}
