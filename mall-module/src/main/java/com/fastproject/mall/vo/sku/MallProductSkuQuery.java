package com.fastproject.mall.vo.sku;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品SKU查询 VO
 */
@Getter
@Setter
public class MallProductSkuQuery extends PageQuery {

    /**
     * 所属商品(SPU) ID
     */
    private Long productId;

    /**
     * 所属店铺ID
     */
    private Long shopId;

    /**
     * SKU 编码 / 货号
     */
    private String skuSn;

    /**
     * 状态 1 启用 2 禁用
     */
    private Integer status;
}
