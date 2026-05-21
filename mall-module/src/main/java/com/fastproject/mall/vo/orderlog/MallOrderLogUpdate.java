package com.fastproject.mall.vo.orderlog;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 订单日志更新 VO
 */
@Data
public class MallOrderLogUpdate {

    /**
     * 主键ID
     */
    @NotNull(message = "ID不能为空")
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
     * 操作人类型 1 用户 2 商家 3 系统 4 管理员
     */
    private Integer operatorType;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人昵称 / 名称（冗余）
     */
    private String operatorName;

    /**
     * 操作动作（枚举字符串）
     */
    private String action;

    /**
     * 起始订单状态
     */
    private Integer fromStatus;

    /**
     * 结束订单状态
     */
    private Integer toStatus;

    /**
     * 描述 / 备注
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
     * 业务扩展字段（JSON 字符串）
     */
    private String extra;
}
