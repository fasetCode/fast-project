package com.fastproject.system.vo.role;

import lombok.Data;

import java.util.List;

/**
 * 角色更新 VO
 */
@Data
public class SysRoleUpdate {

    /**
     * 角色ID
     */
    private Long id;

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
     * 应用
     */
    private Long applicationId;

    /**
     * 应用代理
     */
    private String applicationCode;

    /**
     * 权限ID集合
     */
    private List<Long> permissionIds;
}
