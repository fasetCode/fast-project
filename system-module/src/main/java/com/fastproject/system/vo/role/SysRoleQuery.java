package com.fastproject.system.vo.role;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色查询 VO
 */
@Getter
@Setter
public class SysRoleQuery extends PageQuery {

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
}
