package com.fastproject.mall.service;

import com.fastproject.mall.vo.product.MallProductCreate;
import com.fastproject.mall.vo.product.MallProductQuery;
import com.fastproject.mall.vo.product.MallProductUpdate;
import com.fastproject.mall.vo.product.MallProductVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface MallProductService {
    Long save(MallProductCreate create);
    void update(MallProductUpdate update);
    void delete(Long id);
    void batchDelete(List<Long> ids);
    MallProductVo findById(Long id);
    PageVo<List<MallProductVo>> findPage(MallProductQuery query);
    List<MallProductVo> findAll();
    List<MallProductVo> selectAll();
}
