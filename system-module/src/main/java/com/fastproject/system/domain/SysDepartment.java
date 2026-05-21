package com.fastproject.system.domain;

import com.fastproject.db.BaseEntity;
import com.fastproject.system.tenant.TenantScopedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "sys_department")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_department set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class SysDepartment extends BaseEntity implements TenantScopedEntity {

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父级部门ID
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 租户ID
     */
    private Long tenantId;
}
