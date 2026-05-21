package com.fastproject.system.vo.dictdata;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictDataQuery extends PageQuery {

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
}
