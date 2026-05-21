package com.fastproject.mall.vo.order;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 订单查询 VO
 */
@Getter
@Setter
public class MallOrderQuery extends PageQuery {

    /**
     * 订单号（精确/模糊查询）
     */
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
     * 订单状态：0 待付款 1 待发货 2 待收货 3 已完成 4 已取消 5 退款中 6 已退款
     */
    private Integer status;

    /**
     * 支付方式 1 微信 2 支付宝 3 余额 4 其它
     */
    private Integer payType;

    /**
     * 来源 1 PC 2 H5 3 小程序 4 APP
     */
    private Integer sourceType;

    /**
     * 下单开始时间
     */
    private LocalDateTime startTime;

    /**
     * 下单结束时间
     */
    private LocalDateTime endTime;
}
