package com.fastproject.mall.vo.product;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品查询 VO
 */
@Getter
@Setter
public class MallProductQuery extends PageQuery {

    /**
     * 商品名称（模糊查询）
     */
    private String name;

    /**
     * 所属店铺ID
     */
    private Long shopId;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 状态 1 上架 2 下架
     */
    private Integer status;

    /**
     * 是否新品 0 否 1 是
     */
    private Integer isNew;

    /**
     * 是否热卖 0 否 1 是
     */
    private Integer isHot;

    /**
     * 是否推荐 0 否 1 是
     */
    private Integer isRecommend;
}
