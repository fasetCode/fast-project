package com.fastproject.mall.service;

import com.fastproject.mall.vo.brand.MallProductBrandCreate;
import com.fastproject.mall.vo.brand.MallProductBrandQuery;
import com.fastproject.mall.vo.brand.MallProductBrandUpdate;
import com.fastproject.mall.vo.brand.MallProductBrandVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 商品品牌 Service 接口
 */
public interface MallProductBrandService {

    Long save(MallProductBrandCreate create);

    void update(MallProductBrandUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallProductBrandVo findById(Long id);

    PageVo<List<MallProductBrandVo>> findPage(MallProductBrandQuery query);

    List<MallProductBrandVo> findAll();

    List<MallProductBrandVo> selectAll();
}
