package com.fastproject.system.vo.post;

import lombok.Data;

/**
 * 岗位 VO
 */
@Data
public class SysPostVo {

    /**
     * 岗位ID
     */
    private Long id;

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
}
