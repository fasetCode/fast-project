package com.fastproject.mall.vo.brand;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品品牌查询 VO
 */
@Getter
@Setter
public class MallProductBrandQuery extends PageQuery {

    /**
     * 品牌名称（模糊查询）
     */
    private String name;

    /**
     * 品牌编码
     */
    private String code;

    /**
     * 首字母
     */
    private String firstLetter;

    /**
     * 状态 1 启用 2 禁用
     */
    private Integer status;
}
