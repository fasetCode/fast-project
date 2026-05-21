package com.fastproject.mall.vo.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品 VO
 */
@Data
public class MallProductVo {

    /**
     * 主键ID
     */
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
     * 店铺名称（冗余展示用）
     */
    private String shopName;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称（冗余展示用）
     */
    private String categoryName;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 品牌名称（冗余展示用）
     */
    private String brandName;

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

    /**
     * 关联店铺信息（嵌套展示）
     */
    private Shop shop;

    /**
     * 关联分类信息（嵌套展示）
     */
    private Category category;

    /**
     * 关联品牌信息（嵌套展示）
     */
    private Brand brand;

    /**
     * 内部类 - 店铺简要信息
     */
    @Data
    public static class Shop {
        /**
         * 店铺ID
         */
        private Long id;

        /**
         * 店铺名称
         */
        private String name;
    }

    /**
     * 内部类 - 商品分类简要信息
     */
    @Data
    public static class Category {
        /**
         * 分类ID
         */
        private Long id;

        /**
         * 分类名称
         */
        private String name;
    }

    /**
     * 内部类 - 品牌简要信息
     */
    @Data
    public static class Brand {
        /**
         * 品牌ID
         */
        private Long id;

        /**
         * 品牌名称
         */
        private String name;
    }
}
