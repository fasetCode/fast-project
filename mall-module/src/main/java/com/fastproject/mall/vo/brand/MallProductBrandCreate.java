package com.fastproject.mall.vo.brand;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 商品品牌创建 VO
 */
@Data
public class MallProductBrandCreate {

    /**
     * 品牌名称
     */
    @NotBlank(message = "品牌名称不能为空")
    private String name;

    /**
     * 品牌编码
     */
    private String code;

    /**
     * 品牌 Logo
     */
    private String logo;

    /**
     * 首字母（用于品牌分组检索，如 A B C ... Z）
     */
    private String firstLetter;

    /**
     * 品牌故事 / 描述
     */
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
