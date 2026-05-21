package com.fastproject.mall.vo.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商品分类更新 VO
 */
@Data
public class MallProductCategoryUpdate {

    /**
     * 主键ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

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
    private String icon;

    /**
     * 分类图片
     */
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
    private String description;
}
