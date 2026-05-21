package com.fastproject.mall.service;

import com.fastproject.mall.vo.category.MallProductCategoryCreate;
import com.fastproject.mall.vo.category.MallProductCategoryQuery;
import com.fastproject.mall.vo.category.MallProductCategoryUpdate;
import com.fastproject.mall.vo.category.MallProductCategoryVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 商品分类 Service 接口
 */
public interface MallProductCategoryService {

    Long save(MallProductCategoryCreate create);

    void update(MallProductCategoryUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallProductCategoryVo findById(Long id);

    PageVo<List<MallProductCategoryVo>> findPage(MallProductCategoryQuery query);

    List<MallProductCategoryVo> findTree();

    List<MallProductCategoryVo> findAll();

    List<MallProductCategoryVo> selectAll();
}
