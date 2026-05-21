package com.fastproject.mall.vo.cart;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 购物车查询 VO
 */
@Getter
@Setter
public class MallCartQuery extends PageQuery {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 是否选中 0 未选中 1 已选中
     */
    private Integer selected;
}
