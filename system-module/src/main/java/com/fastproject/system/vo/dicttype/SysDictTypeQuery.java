package com.fastproject.system.vo.dicttype;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictTypeQuery extends PageQuery {

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
}
