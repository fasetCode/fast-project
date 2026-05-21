package com.fastproject.mall.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/**
 * 商城 - 商品（SPU）
 */
@Table(name = "mall_product")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_product set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallProduct extends BaseEntity {

    /**
     * 商品名称
     */
    @Column(length = 200)
    private String name;

    /**
     * 商品副标题 / 卖点
     */
    @Column(length = 500)
    private String subTitle;

    /**
     * 所属店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", insertable = false, updatable = false)
    private MallShop shop;

    /**
     * 分类ID
     */
    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private MallProductCategory category;

    /**
     * 品牌ID
     */
    @Column(name = "brand_id")
    private Long brandId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    private MallProductBrand brand;

    /**
     * 商品编码 / 货号
     */
    private String productSn;

    /**
     * 主图（文件访问路径）
     */
    @Column(length = 500)
    private String mainImage;

    /**
     * 商品相册，多张图以英文逗号分隔
     */
    @Column(length = 2000)
    private String albumImages;

    /**
     * 商品详情（富文本 HTML）
     */
    @Lob
    private String detail;

    /**
     * 销售价
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
     * 库存
     */
    private Integer stock;

    /**
     * 已售数量
     */
    private Integer sales;

    /**
     * 单位（件、份、kg ...）
     */
    private String unit;

    /**
     * 重量（kg）
     */
    @Column(precision = 10, scale = 3)
    private BigDecimal weight;

    /**
     * 状态 1 上架 2 下架
     */
    private Integer status;

    /**
     * 是否新品 0 否 1 是
     */
    private Integer isNew;

    /**
     * 是否热卖 0 否 1 是
     */
    private Integer isHot;

    /**
     * 是否推荐 0 否 1 是
     */
    private Integer isRecommend;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 关键字（搜索用，多个以英文逗号分隔）
     */
    @Column(length = 500)
    private String keywords;

}
