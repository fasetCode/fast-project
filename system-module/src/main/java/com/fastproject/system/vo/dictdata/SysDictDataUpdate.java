package com.fastproject.system.vo.dictdata;

import lombok.Data;

/**
 * 字典数据更新
 */
@Data
public class SysDictDataUpdate {

    /**
     * 字典数据ID
     */
    private Long id;

    /**
     * 字典排序
     */
    private Integer sort;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 字典类型ID
     */
    private Long dictTypeId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
