package com.fastproject.mall.service;

import com.fastproject.mall.vo.sku.MallProductSkuCreate;
import com.fastproject.mall.vo.sku.MallProductSkuQuery;
import com.fastproject.mall.vo.sku.MallProductSkuUpdate;
import com.fastproject.mall.vo.sku.MallProductSkuVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 商品SKU Service 接口
 */
public interface MallProductSkuService {

    Long save(MallProductSkuCreate create);

    void update(MallProductSkuUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallProductSkuVo findById(Long id);

    List<MallProductSkuVo> findByProductId(Long productId);

    List<MallProductSkuVo> findByShopId(Long shopId);

    PageVo<List<MallProductSkuVo>> findPage(MallProductSkuQuery query);

    List<MallProductSkuVo> findAll();
}
