package com.fastproject.usergrowth.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "user_integral_account")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update user_level_account set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class UserIntegralAccount extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 等级积分
     */
    private Long integral;

    /**
     * 状态
     */
    private Integer status;
}
