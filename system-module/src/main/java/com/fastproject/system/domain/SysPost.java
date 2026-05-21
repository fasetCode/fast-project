package com.fastproject.system.domain;

import com.fastproject.db.BaseEntity;
import com.fastproject.system.tenant.TenantScopedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "sys_post")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_post set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class SysPost extends BaseEntity implements TenantScopedEntity {

    /**
     * 岗位名称
     */
    private String name;

    /**
     * 岗位编码
     */
    private String code;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户ID
     */
    private Long tenantId;
}
