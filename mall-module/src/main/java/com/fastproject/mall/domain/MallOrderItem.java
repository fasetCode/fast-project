package com.fastproject.mall.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/**
 * 商城 - 订单明细（订单中的每一个商品行）
 *
 * 设计说明：
 * - 仅保留 ID 维度字段用于查询/聚合统计（订单、用户、店铺、商品、SKU、分类、品牌）
 * - 仅保留参与金额计算的字段（price、quantity、discountAmount、totalAmount 等）
 * - 商品名称、主图、货号、单位、SKU 规格、属性等"展示用"字段统一以 JSON 形式存放在
 *   {@link #productSnapshot} / {@link #skuSnapshot} 中，避免随商品后续变更失真
 */
@Table(name = "mall_order_item")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_order_item set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallOrderItem extends BaseEntity {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单号（冗余，便于按订单号直接查询）
     */
    @Column(length = 64)
    private String orderNo;

    /**
     * 下单用户ID（冗余，便于按用户聚合查询）
     */
    private Long userId;

    /**
     * 店铺ID（冗余，支持多店铺统计）
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
     * 商品分类ID（冗余，便于按品类统计）
     */
    private Long categoryId;

    /**
     * 品牌ID（冗余）
     */
    private Long brandId;

    /**
     * 商品快照（JSON 字符串）
     * 推荐结构： 
     */
    @Column(columnDefinition = "TEXT")
    private String productSnapshot;

    /**
     * SKU 快照（JSON 字符串） 
     */
    @Column(columnDefinition = "TEXT")
    private String skuSnapshot;

    /**
     * 下单时单价（快照，参与金额计算需独立列；现金部分）
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    /**
     * 下单时原价 / 划线价（快照，参与展示与折扣计算）
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal originalPrice;

    /**
     * 支付方式 1 现金 2 积分 3 现金+积分（行级快照）
     */
    private Integer payType;

    /**
     * 下单时积分单价（快照，0 表示不使用积分）
     */
    private Integer pointsPrice;

    /**
     * 该明细使用积分小计 = pointsPrice * quantity
     */
    private Integer pointsAmount;

    /**
     * 该明细赠送积分小计（快照）
     */
    private Integer giftPointsAmount;

    /**
     * 配送方式 1 物流 2 无需物流 3 到店自提 4 同城配送（行级快照）
     */
    private Integer deliveryType;

    /**
     * 虚拟商品子类型快照（deliveryType=2 时使用）
     * 1 卡密 2 充值码 3 兑换券 4 线上服务 5 会员权益
     */
    private Integer virtualType;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 该明细优惠金额
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal discountAmount;

    /**
     * 该明细实付小计 = price * quantity - discountAmount
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal totalAmount;

    /**
     * 退款状态 0 未退款 1 退款中 2 已退款 3 退款失败
     */
    private Integer refundStatus;

    /**
     * 已退款金额
     */
    @Column(precision = 12, scale = 2)
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
    @Column(length = 500)
    private String remark;

    /**
     * 业务扩展字段（JSON 字符串，预留促销/赠品/分销等场景）
     */
    @Column(columnDefinition = "TEXT")
    private String extra;
}
