package com.fastproject.mall.vo.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单创建 VO
 */
@Data
public class MallOrderCreate {

    /**
     * 订单号（业务唯一）
     */
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    /**
     * 下单用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 商品总额（不含运费/优惠）
     */
    private BigDecimal totalAmount;

    /**
     * 实付金额
     */
    private BigDecimal payAmount;

    /**
     * 运费金额
     */
    private BigDecimal freightAmount;

    /**
     * 优惠金额
     */
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
     * 商品快照（JSON 字符串）
     */
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
    private String payTradeNo;

    /**
     * 用户备注
     */
    private String userRemark;

    /**
     * 商家备注
     */
    private String shopRemark;
}
