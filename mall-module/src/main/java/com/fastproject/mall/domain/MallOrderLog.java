package com.fastproject.mall.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 商城 - 订单流转日志（订单状态/操作审计）
 *
 * 适用场景：下单、支付、发货、签收、关闭、取消、退款、评价等关键节点的留痕。
 */
@Table(name = "mall_order_log")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_order_log set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallOrderLog extends BaseEntity {

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
    @Column(length = 100)
    private String operatorName;

    /**
     * 操作动作（枚举字符串）
     * CREATE / PAY / SHIP / RECEIVE / CONFIRM / CANCEL / CLOSE
     * REFUND_APPLY / REFUND_PASS / REFUND_REJECT / REFUND_DONE
     * COMMENT / REMARK / OTHER
     */
    @Column(length = 32)
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
    @Column(length = 1000)
    private String content;

    /**
     * 操作来源 IP
     */
    @Column(length = 64)
    private String ip;

    /**
     * 来源 1 PC 2 H5 3 小程序 4 APP 5 后台 6 系统
     */
    private Integer sourceType;

    /**
     * 业务扩展字段（JSON 字符串，存放变更前后字段对比等）
     */
    @Column(columnDefinition = "TEXT")
    private String extra;
}
