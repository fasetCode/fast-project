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
import java.time.LocalDateTime;

/**
 * 商城 - 订单
 *
 * 设计说明：
 * - 商品/SKU 行明细以独立表 {@link MallOrderItem} 持久化（参与售后、退款、对账聚合）
 * - 订单头额外保留 {@link #itemsSnapshot} 作为商品快照 JSON：
 *   用于订单列表/详情的快速展示，避免每次 JOIN 明细表，也避免商品后续修改后展示失真
 */
@Table(name = "mall_order")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_order set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallOrder extends BaseEntity {

    /**
     * 订单号（业务唯一）
     */
    @Column(length = 64)
    private String orderNo;

    /**
     * 下单用户ID
     */
    private Long userId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 商品总额（不含运费/优惠）
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal totalAmount;

    /**
     * 实付金额
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal payAmount;

    /**
     * 运费金额
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal freightAmount;

    /**
     * 优惠金额
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal discountAmount;

    /**
     * 商品总数量
     */
    private Integer totalQuantity;

    /**
     * 商品种类数（不同商品/SKU 行数）
     */
    private Integer itemKindCount;

    /**
     * 商品快照（JSON 字符串，订单维度的商品摘要） 
     */
    @Column(columnDefinition = "TEXT")
    private String itemsSnapshot;

    /**
     * 支付方式 1 微信 2 支付宝 3 余额 4 其它
     */
    private Integer payType;

    /**
     * 来源 1 PC 2 H5 3 小程序 4 APP
     */
    private Integer sourceType;

    /**
     * 订单状态：0 待付款 1 待发货 2 待收货 3 已完成 4 已取消 5 退款中 6 已退款
     */
    private Integer status;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 订单关闭时间
     */
    private LocalDateTime closeTime;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 收货省
     */
    private String receiverProvince;

    /**
     * 收货市
     */
    private String receiverCity;

    /**
     * 收货区/县
     */
    private String receiverDistrict;

    /**
     * 收货详细地址
     */
    @Column(length = 500)
    private String receiverAddress;

    /**
     * 邮政编码
     */
    private String receiverPostalCode;

    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 第三方支付流水号
     */
    @Column(length = 128)
    private String payTradeNo;

    /**
     * 用户备注
     */
    @Column(length = 500)
    private String userRemark;

    /**
     * 商家备注
     */
    @Column(length = 500)
    private String shopRemark;

}
