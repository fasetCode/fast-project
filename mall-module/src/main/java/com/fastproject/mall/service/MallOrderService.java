package com.fastproject.mall.service;

import com.fastproject.mall.vo.order.MallOrderCreate;
import com.fastproject.mall.vo.order.MallOrderQuery;
import com.fastproject.mall.vo.order.MallOrderUpdate;
import com.fastproject.mall.vo.order.MallOrderVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 订单 Service 接口
 */
public interface MallOrderService {

    Long save(MallOrderCreate create);

    void update(MallOrderUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallOrderVo findById(Long id);

    MallOrderVo findByOrderNo(String orderNo);

    PageVo<List<MallOrderVo>> findPage(MallOrderQuery query);

    List<MallOrderVo> findAll();
}
