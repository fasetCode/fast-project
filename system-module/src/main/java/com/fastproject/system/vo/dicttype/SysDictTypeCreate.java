package com.fastproject.system.vo.dicttype;

import lombok.Data;

/**
 * 字典类型创建
 */
@Data
public class SysDictTypeCreate {

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
