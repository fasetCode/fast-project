package com.fastproject.mall.vo.orderitem;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单明细查询 VO
 */
@Getter
@Setter
public class MallOrderItemQuery extends PageQuery {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单号
     */
    private String orderNo;

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
     * 退款状态 0 未退款 1 退款中 2 已退款 3 退款失败
     */
    private Integer refundStatus;

    /**
     * 是否已评价 0 否 1 是
     */
    private Integer commented;
}
