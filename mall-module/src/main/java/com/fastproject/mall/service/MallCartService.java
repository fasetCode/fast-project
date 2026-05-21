package com.fastproject.mall.service;

import com.fastproject.mall.vo.cart.MallCartCreate;
import com.fastproject.mall.vo.cart.MallCartQuery;
import com.fastproject.mall.vo.cart.MallCartUpdate;
import com.fastproject.mall.vo.cart.MallCartVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 购物车 Service 接口
 */
public interface MallCartService {

    Long save(MallCartCreate create);

    void update(MallCartUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallCartVo findById(Long id);

    PageVo<List<MallCartVo>> findPage(MallCartQuery query);

    List<MallCartVo> findAll();

    List<MallCartVo> findByUserId(Long userId);
}
