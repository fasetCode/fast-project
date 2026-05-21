package com.fastproject.system.vo.post;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 岗位查询 VO
 */
@Getter
@Setter
public class SysPostQuery extends PageQuery {

    /**
     * 岗位名称
     */
    private String name;

    /**
     * 岗位编码
     */
    private String code;

    /**
     * 状态
     */
    private Integer status;
}
