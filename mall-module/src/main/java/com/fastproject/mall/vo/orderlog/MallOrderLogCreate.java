package com.fastproject.mall.vo.orderlog;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 订单日志创建 VO
 */
@Data
public class MallOrderLogCreate {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    /**
     * 订单号（冗余）
     */
    private String orderNo;

    /**
     * 操作人类型 1 用户 2 商家 3 系统 4 管理员
     */
    private Integer operatorType;

    /**
     * 操作人ID（系统操作可空）
     */
    private Long operatorId;

    /**
     * 操作人昵称 / 名称（冗余，便于直接展示）
     */
    private String operatorName;

    /**
     * 操作动作（枚举字符串）
     * CREATE / PAY / SHIP / RECEIVE / CONFIRM / CANCEL / CLOSE
     * REFUND_APPLY / REFUND_PASS / REFUND_REJECT / REFUND_DONE
     * COMMENT / REMARK / OTHER
     */
    private String action;

    /**
     * 起始订单状态（可空，无状态变化时不填）
     */
    private Integer fromStatus;

    /**
     * 结束订单状态（可空）
     */
    private Integer toStatus;

    /**
     * 描述 / 备注（可读文本）
     */
    private String content;

    /**
     * 操作来源 IP
     */
    private String ip;

    /**
     * 来源 1 PC 2 H5 3 小程序 4 APP 5 后台 6 系统
     */
    private Integer sourceType;

    /**
     * 业务扩展字段（JSON 字符串，存放变更前后字段对比等）
     */
    private String extra;
}
