package com.fastproject.mall.vo.sku;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品SKU创建 VO
 */
@Data
public class MallProductSkuCreate {

    /**
     * 所属商品(SPU) ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    /**
     * 所属店铺ID（冗余，便于按店铺统计/隔离）
     */
    private Long shopId;

    /**
     * SKU 编码 / 货号
     */
    private String skuSn;

    /**
     * 条形码
     */
    private String barcode;

    /**
     * 规格文本（前端展示用，例如：颜色:红色;尺码:M）
     */
    private String specText;

    /**
     * 规格属性（JSON 字符串，结构化存储）
     */
    private String specs;

    /**
     * SKU 主图（覆盖 SPU 主图，可选）
     */
    private String image;

    /**
     * 支付方式 1 现金 2 积分 3 现金+积分
     */
    private Integer payType;

    /**
     * 销售价（现金部分）
     */
    private BigDecimal price;

    /**
     * 原价 / 划线价
     */
    private BigDecimal originalPrice;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 积分单价（payType=2 / 3 时使用，0 表示不使用积分）
     */
    private Integer pointsPrice;

    /**
     * 购买可获积分（赠送积分，可选）
     */
    private Integer giftPoints;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 锁定库存（待支付/未发货占用）
     */
    private Integer lockStock;

    /**
     * 已售数量
     */
    private Integer sales;

    /**
     * 重量（kg；虚拟商品可为空）
     */
    private BigDecimal weight;

    /**
     * 体积（m³；可选，物流计费用）
     */
    private BigDecimal volume;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 1 启用 2 禁用
     */
    private Integer status;

    /**
     * 业务扩展字段（JSON 字符串）
     */
    private String extra;
}
