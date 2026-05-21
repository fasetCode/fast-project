package com.fastproject.mall.service;

import com.fastproject.mall.vo.pickupshop.MallPickupShopCreate;
import com.fastproject.mall.vo.pickupshop.MallPickupShopQuery;
import com.fastproject.mall.vo.pickupshop.MallPickupShopUpdate;
import com.fastproject.mall.vo.pickupshop.MallPickupShopVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 自提门店 Service 接口
 */
public interface MallPickupShopService {

    Long save(MallPickupShopCreate create);

    void update(MallPickupShopUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallPickupShopVo findById(Long id);

    PageVo<List<MallPickupShopVo>> findPage(MallPickupShopQuery query);

    List<MallPickupShopVo> findAll();

    List<MallPickupShopVo> selectAll();
}
