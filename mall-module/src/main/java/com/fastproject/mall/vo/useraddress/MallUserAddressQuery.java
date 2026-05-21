package com.fastproject.mall.vo.useraddress;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户收货地址查询 VO
 */
@Getter
@Setter
public class MallUserAddressQuery extends PageQuery {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 是否默认 0 否 1 是
     */
    private Integer isDefault;
}
