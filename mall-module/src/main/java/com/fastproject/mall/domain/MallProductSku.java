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

/**
 * 商城 - 商品 SKU（最小可售单元，多规格组合）
 */
@Table(name = "mall_product_sku")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_product_sku set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallProductSku extends BaseEntity {

    /**
     * 所属商品(SPU) ID
     */
    private Long productId;

    /**
     * 所属店铺ID（冗余，便于按店铺统计/隔离）
     */
    private Long shopId;

    /**
     * SKU 编码 / 货号
     */
    @Column(length = 64)
    private String skuSn;

    /**
     * 条形码
     */
    @Column(length = 64)
    private String barcode;

    /**
     * 规格文本（前端展示用，例如：颜色:红色;尺码:M）
     */
    @Column(length = 500)
    private String specText;

    /**
     * 规格属性（JSON 字符串，结构化存储）
     * 例：[{"name":"颜色","value":"红色"},{"name":"尺码","value":"M"}]
     */
    @Column(columnDefinition = "TEXT")
    private String specs;

    /**
     * SKU 主图（覆盖 SPU 主图，可选）
     */
    @Column(length = 500)
    private String image;

    /**
     * 支付方式 1 现金 2 积分 3 现金+积分（默认随 SPU，可单独覆盖）
     */
    private Integer payType;

    /**
     * 销售价（现金部分）
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    /**
     * 原价 / 划线价
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal originalPrice;

    /**
     * 成本价
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal costPrice;

    /**
     * 积分单价（payType=2 / 3 时使用，0 表示不使用积分）
     */
    private Integer pointsPrice;

    /**
     * 购买可获积分（赠送积分，可选；不填随 SPU）
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
    @Column(precision = 10, scale = 3)
    private BigDecimal weight;

    /**
     * 体积（m³；可选，物流计费用）
     */
    @Column(precision = 10, scale = 3)
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
    @Column(columnDefinition = "TEXT")
    private String extra;
}
