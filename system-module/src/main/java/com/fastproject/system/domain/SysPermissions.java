package com.fastproject.system.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "sys_permissions")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_permissions set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class SysPermissions extends BaseEntity {
    /**
     * 标题
     */
    private String title;

    /**
     * 权限代码
     */
    private String code;

    /**
     * 地址
     */
    private String url;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 组件
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 父级部门
     */
    private Long parentId;

    /**
     * 组件名
     */
    private String componentName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 应用
     */
    private Long applicationId;

    /**
     * 应用代理
     */
    private String applicationCode;
}
