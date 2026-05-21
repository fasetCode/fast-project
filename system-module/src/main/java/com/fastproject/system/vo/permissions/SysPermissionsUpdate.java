package com.fastproject.system.vo.permissions;

import lombok.Data;

/**
 * 权限更新 VO
 */
@Data
public class SysPermissionsUpdate {

    /**
     * 权限ID
     */
    private Long id;

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
     * 父级ID
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
     * 应用ID
     */
    private Long applicationId;

    /**
     * 应用编码
     */
    private String applicationCode;
}
