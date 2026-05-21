package com.fastproject.mall.vo.orderrefund;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退款单更新 VO
 */
@Data
public class MallOrderRefundUpdate {

    /**
     * 主键ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 退款单号
     */
    private String refundNo;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单号（冗余）
     */
    private String orderNo;

    /**
     * 订单明细ID
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
    private BigDecimal refundAmount;

    /**
     * 退款积分数
     */
    private Integer refundPoints;

    /**
     * 退款数量（部分退时填）
     */
    private Integer refundQuantity;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 用户补充说明
     */
    private String description;

    /**
     * 凭证（JSON 字符串，图片URL列表）
     */
    private String evidence;

    /**
     * 商家审核时间
     */
    private LocalDateTime shopReplyTime;

    /**
     * 商家审核备注
     */
    private String shopReplyText;

    /**
     * 商家拒绝原因
     */
    private String shopRejectReason;

    /**
     * 退货物流公司
     */
    private String returnExpressCompany;

    /**
     * 退货物流单号
     */
    private String returnExpressNo;

    /**
     * 退款打款流水号（第三方）
     */
    private String payRefundTradeNo;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 撤销时间
     */
    private LocalDateTime cancelTime;

    /**
     * 业务扩展字段（JSON 字符串）
     */
    private String extra;
}
