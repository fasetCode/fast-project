package com.fastproject.system.vo.dicttype;

import lombok.Data;

/**
 * 字典类型 VO
 */
@Data
public class SysDictTypeVo {

    /**
     * 字典ID
     */
    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
