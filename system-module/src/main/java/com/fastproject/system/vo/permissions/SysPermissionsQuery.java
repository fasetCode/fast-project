package com.fastproject.system.vo.permissions;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限查询 VO
 */
@Getter
@Setter
public class SysPermissionsQuery extends PageQuery {

    /**
     * 标题
     */
    private String title;

    /**
     * 权限代码
     */
    private String code;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 应用ID
     */
    private Long applicationId;
}
