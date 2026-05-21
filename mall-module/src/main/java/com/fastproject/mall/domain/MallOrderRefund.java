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
 * 商城 - 退款单（一笔订单可发起多次部分退款）
 */
@Table(name = "mall_order_refund")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_order_refund set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallOrderRefund extends BaseEntity {

    /**
     * 退款单号（业务编号，前端展示）
     */
    @Column(length = 64)
    private String refundNo;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单号（冗余）
     */
    @Column(length = 64)
    private String orderNo;

    /**
     * 订单明细ID（整单退款时为空，部分退款时指向具体行）
     */
    private Long orderItemId;

    /**
     * 用户ID（冗余）
     */
    private Long userId;

    /**
     * 店铺ID（冗余）
     */
    private Long shopId;

    /**
     * 退款类型 1 仅退款 2 退货退款 3 换货
     */
    private Integer refundType;

    /**
     * 退款状态
     * 1 待审核 2 审核通过待打款 3 退货中 4 已完成 5 已拒绝 6 已撤销
     */
    private Integer refundStatus;

    /**
     * 退款现金金额
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal refundAmount;

    /**
     * 退款积分数（混合/积分订单）
     */
    private Integer refundPoints;

    /**
     * 退款数量（部分退时填）
     */
    private Integer refundQuantity;

    /**
     * 退款原因（建议结合字典）
     */
    @Column(length = 200)
    private String reason;

    /**
     * 用户补充说明
     */
    @Column(length = 1000)
    private String description;

    /**
     * 凭证（JSON 字符串，图片URL列表）
     */
    @Column(columnDefinition = "TEXT")
    private String evidence;

    /**
     * 商家审核时间
     */
    private LocalDateTime shopReplyTime;

    /**
     * 商家审核备注（同意时可留言）
     */
    @Column(length = 500)
    private String shopReplyText;

    /**
     * 商家拒绝原因（refundStatus=5 时使用）
     */
    @Column(length = 500)
    private String shopRejectReason;

    /**
     * 退货物流公司（refundType=2 时使用）
     */
    private String returnExpressCompany;

    /**
     * 退货物流单号
     */
    private String returnExpressNo;

    /**
     * 退款打款流水号（第三方）
     */
    @Column(length = 128)
    private String payRefundTradeNo;

    /**
     * 完成时间（已打款 / 已完成）
     */
    private LocalDateTime finishTime;

    /**
     * 撤销时间
     */
    private LocalDateTime cancelTime;

    /**
     * 业务扩展字段（JSON 字符串）
     */
    @Column(columnDefinition = "TEXT")
    private String extra;
}
