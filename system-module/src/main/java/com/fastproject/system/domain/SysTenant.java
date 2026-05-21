package com.fastproject.system.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

/**
 * 租户模型
 */
@Table(name = "sys_tenant")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_tenant set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class SysTenant extends BaseEntity {

    /**
     * 租户名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 租户管理员ID
     */
    private Long adminId;

    /**
     * 租户状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 租户域名
     */
    private String domain;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 账号额度（最大允许创建的用户数）
     */
    private Integer accountCount;

    /**
     * 备注
     */
    private String remark;
}
