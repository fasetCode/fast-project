package com.fastproject.mall.vo.pickupshop;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 自提门店查询 VO
 */
@Getter
@Setter
public class MallPickupShopQuery extends PageQuery {

    /**
     * 门店名称（模糊查询）
     */
    private String name;

    /**
     * 所属店铺ID
     */
    private Long shopId;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 状态 1 启用 2 停用
     */
    private Integer status;
}
