package com.fastproject.mall.vo.orderitem;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细 VO
 */
@Data
public class MallOrderItemVo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单号（冗余）
     */
    private String orderNo;

    /**
     * 下单用户ID（冗余）
     */
    private Long userId;

    /**
     * 店铺ID（冗余）
     */
    private Long shopId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU ID（无 SKU 时为空）
     */
    private Long skuId;

    /**
     * 商品分类ID（冗余）
     */
    private Long categoryId;

    /**
     * 品牌ID（冗余）
     */
    private Long brandId;

    /**
     * 商品快照（JSON 字符串）
     */
    private String productSnapshot;

    /**
     * SKU 快照（JSON 字符串）
     */
    private String skuSnapshot;

    /**
     * 下单时单价（快照）
     */
    private BigDecimal price;

    /**
     * 下单时原价 / 划线价（快照）
     */
    private BigDecimal originalPrice;

    /**
     * 支付方式 1 现金 2 积分 3 现金+积分
     */
    private Integer payType;

    /**
     * 下单时积分单价（快照，0 表示不使用积分）
     */
    private Integer pointsPrice;

    /**
     * 该明细使用积分小计
     */
    private Integer pointsAmount;

    /**
     * 该明细赠送积分小计（快照）
     */
    private Integer giftPointsAmount;

    /**
     * 配送方式 1 物流 2 无需物流 3 到店自提 4 同城配送
     */
    private Integer deliveryType;

    /**
     * 虚拟商品子类型快照
     */
    private Integer virtualType;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 该明细优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 该明细实付小计
     */
    private BigDecimal totalAmount;

    /**
     * 退款状态 0 未退款 1 退款中 2 已退款 3 退款失败
     */
    private Integer refundStatus;

    /**
     * 已退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 已退款数量
     */
    private Integer refundQuantity;

    /**
     * 是否已评价 0 否 1 是
     */
    private Integer commented;

    /**
     * 备注
     */
    private String remark;

    /**
     * 业务扩展字段（JSON 字符串）
     */
    private String extra;
}
