package com.fastproject.mall.vo.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品更新 VO
 */
@Data
public class MallProductUpdate {

    /**
     * 主键ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品副标题 / 卖点
     */
    private String subTitle;

    /**
     * 所属店铺ID
     */
    private Long shopId;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 商品编码 / 货号
     */
    private String productSn;

    /**
     * 主图（文件访问路径）
     */
    private String mainImage;

    /**
     * 商品相册，多张图以英文逗号分隔
     */
    private String albumImages;

    /**
     * 商品详情（富文本 HTML）
     */
    private String detail;

    /**
     * 销售价
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
    private String keywords;
}
