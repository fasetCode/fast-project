package com.fastproject.mall.vo.category;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品分类查询 VO
 */
@Getter
@Setter
public class MallProductCategoryQuery extends PageQuery {

    /**
     * 分类名称（模糊查询）
     */
    private String name;

    /**
     * 父级分类ID
     */
    private Long parentId;

    /**
     * 状态 1 启用 2 禁用
     */
    private Integer status;
}
