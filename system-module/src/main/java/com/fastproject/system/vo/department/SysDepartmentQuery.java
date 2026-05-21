package com.fastproject.system.vo.department;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 部门查询 VO
 */
@Getter
@Setter
public class SysDepartmentQuery extends PageQuery {

    /**
     * 部门名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 父级部门ID
     */
    private Long parentId;
}
