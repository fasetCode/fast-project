package com.fastproject.usergrowth.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "user_integral_record")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update user_integral_record set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class UserIntegralRecord extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 交易前 积分
     */
    private Integer beforeIntegral;

    /**
     * 交易后 积分
     */
    private Integer afterIntegral;

    /**
     * 变更 积分
     */
    private Long changeValue;
    /**
     * 说明
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 业务ID
     */
    private Long businessId;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 业务类型
     */
    private String bizType;
}
