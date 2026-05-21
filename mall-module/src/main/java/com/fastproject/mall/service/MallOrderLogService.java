package com.fastproject.mall.service;

import com.fastproject.mall.vo.orderlog.MallOrderLogCreate;
import com.fastproject.mall.vo.orderlog.MallOrderLogQuery;
import com.fastproject.mall.vo.orderlog.MallOrderLogUpdate;
import com.fastproject.mall.vo.orderlog.MallOrderLogVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 订单日志 Service 接口
 */
public interface MallOrderLogService {

    Long save(MallOrderLogCreate create);

    void update(MallOrderLogUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallOrderLogVo findById(Long id);

    PageVo<List<MallOrderLogVo>> findPage(MallOrderLogQuery query);

    List<MallOrderLogVo> findAll();
}
