package com.fastproject.mall.service;

import com.fastproject.mall.vo.orderrefund.MallOrderRefundCreate;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundQuery;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundUpdate;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 退款单 Service 接口
 */
public interface MallOrderRefundService {

    Long save(MallOrderRefundCreate create);

    void update(MallOrderRefundUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallOrderRefundVo findById(Long id);

    MallOrderRefundVo findByRefundNo(String refundNo);

    PageVo<List<MallOrderRefundVo>> findPage(MallOrderRefundQuery query);

    List<MallOrderRefundVo> findAll();
}
