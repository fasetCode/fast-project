package com.fastproject.system.vo.department;

import lombok.Data;

import java.util.List;

/**
 * 部门 VO
 */
@Data
public class SysDepartmentVo {

    /**
     * 部门ID
     */
    private Long id;

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
     * 子部门
     */
    private List<SysDepartmentVo> children;
}
