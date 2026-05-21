package com.fastproject.mall.vo.orderrefund;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 退款单查询 VO
 */
@Getter
@Setter
public class MallOrderRefundQuery extends PageQuery {

    /**
     * 退款单号
     */
    private String refundNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 退款类型 1 仅退款 2 退货退款 3 换货
     */
    private Integer refundType;

    /**
     * 退款状态 1 待审核 2 审核通过待打款 3 退货中 4 已完成 5 已拒绝 6 已撤销
     */
    private Integer refundStatus;

    /**
     * 申请开始时间
     */
    private LocalDateTime startTime;

    /**
     * 申请结束时间
     */
    private LocalDateTime endTime;
}
