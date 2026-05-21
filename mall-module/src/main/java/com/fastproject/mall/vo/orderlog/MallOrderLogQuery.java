package com.fastproject.mall.vo.orderlog;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单日志查询 VO
 */
@Getter
@Setter
public class MallOrderLogQuery extends PageQuery {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 操作人类型 1 用户 2 商家 3 系统 4 管理员
     */
    private Integer operatorType;

    /**
     * 操作动作（枚举字符串）
     */
    private String action;

    /**
     * 来源 1 PC 2 H5 3 小程序 4 APP 5 后台 6 系统
     */
    private Integer sourceType;
}
