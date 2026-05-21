package com.fastproject.mall.vo.shop;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 商城店铺查询 VO
 */
@Getter
@Setter
public class MallShopQuery extends PageQuery {

    /**
     * 店铺名称（模糊查询）
     */
    private String name;

    /**
     * 店铺编码
     */
    private String code;

    /**
     * 状态 1 正常 2 冻结
     */
    private Integer status;
}
