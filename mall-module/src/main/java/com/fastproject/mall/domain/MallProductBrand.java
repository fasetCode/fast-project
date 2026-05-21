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
 * 商城 - 商品品牌
 */
@Table(name = "mall_product_brand")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_product_brand set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallProductBrand extends BaseEntity {

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌编码
     */
    private String code;

    /**
     * 品牌 Logo
     */
    @Column(length = 500)
    private String logo;

    /**
     * 首字母（用于品牌分组检索，如 A B C ... Z）
     */
    @Column(length = 2)
    private String firstLetter;

    /**
     * 品牌故事 / 描述
     */
    @Column(length = 1000)
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否在首页显示 0 否 1 是
     */
    private Integer showInHome;

    /**
     * 状态 1 启用 2 禁用
     */
    private Integer status;

}
