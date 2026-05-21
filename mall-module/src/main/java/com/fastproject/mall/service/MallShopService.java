package com.fastproject.mall.service;

import com.fastproject.mall.vo.shop.MallShopCreate;
import com.fastproject.mall.vo.shop.MallShopQuery;
import com.fastproject.mall.vo.shop.MallShopUpdate;
import com.fastproject.mall.vo.shop.MallShopVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 商城店铺 Service 接口
 */
public interface MallShopService {

    Long save(MallShopCreate create);

    void update(MallShopUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallShopVo findById(Long id);

    PageVo<List<MallShopVo>> findPage(MallShopQuery query);

    List<MallShopVo> findAll();

    List<MallShopVo> selectAll();
}
