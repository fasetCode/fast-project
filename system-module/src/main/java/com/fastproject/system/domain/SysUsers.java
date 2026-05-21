package com.fastproject.system.domain;

import com.fastproject.db.BaseEntity;
import com.fastproject.system.tenant.TenantScopedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Table(name = "sys_users")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_users set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class SysUsers extends BaseEntity implements TenantScopedEntity {

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 性别 0男 1女 2未知
     */
    private Integer gender;

    /**
     * 角色状态
     */
    private Integer status;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 部门
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private SysDepartment department;

    /**
     * 个人简介
     */
    private String remark;

    /**
     * 岗位
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private SysPost post;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sys_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<SysRole> roles = new HashSet<>();
}
