package com.fastproject.mall.service;

import com.fastproject.mall.vo.orderitem.MallOrderItemCreate;
import com.fastproject.mall.vo.orderitem.MallOrderItemQuery;
import com.fastproject.mall.vo.orderitem.MallOrderItemUpdate;
import com.fastproject.mall.vo.orderitem.MallOrderItemVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 订单明细 Service 接口
 */
public interface MallOrderItemService {

    Long save(MallOrderItemCreate create);

    void update(MallOrderItemUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallOrderItemVo findById(Long id);

    PageVo<List<MallOrderItemVo>> findPage(MallOrderItemQuery query);

    List<MallOrderItemVo> findAll();

    List<MallOrderItemVo> findByOrderId(Long orderId);
}
