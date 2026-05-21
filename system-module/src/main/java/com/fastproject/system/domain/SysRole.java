package com.fastproject.system.domain;

import com.fastproject.db.BaseEntity;
import com.fastproject.system.tenant.TenantScopedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Table(name = "sys_role")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_role set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class SysRole extends BaseEntity implements TenantScopedEntity {
    /**
     * 标题
     */
    private String title;

    /**
     * 角色代码
     */
    private String code;

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 应用
     */
    private Long applicationId;

    /**
     * 应用代理
     */
    private String applicationCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sys_role_permissions",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    private Set<SysPermissions> permissions = new HashSet<>();
}
