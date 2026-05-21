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
 * 商城 - 商品分类（树形结构）
 */
@Table(name = "mall_product_category")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_product_category set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallProductCategory extends BaseEntity {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父级分类ID（顶级为 0）
     */
    private Long parentId;

    /**
     * 层级（1 一级 2 二级 3 三级 ...）
     */
    private Integer level;

    /**
     * 分类图标
     */
    @Column(length = 500)
    private String icon;

    /**
     * 分类图片
     */
    @Column(length = 500)
    private String image;

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

    /**
     * 描述
     */
    @Column(columnDefinition = "TEXT")
    private String description;

}
