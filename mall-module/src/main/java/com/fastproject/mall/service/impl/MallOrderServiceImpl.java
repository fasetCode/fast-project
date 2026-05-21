package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallOrder;
import com.fastproject.mall.mapper.MallOrderMapper;
import com.fastproject.mall.repository.db.MallOrderRepository;
import com.fastproject.mall.service.MallOrderService;
import com.fastproject.mall.vo.order.MallOrderCreate;
import com.fastproject.mall.vo.order.MallOrderQuery;
import com.fastproject.mall.vo.order.MallOrderUpdate;
import com.fastproject.mall.vo.order.MallOrderVo;
import com.fastproject.utils.vo.PageVo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallOrderServiceImpl implements MallOrderService {

    private final MallOrderRepository mallOrderRepository;
    private final QueryHelp<MallOrder> queryHelp;
    private final MallOrderMapper mallOrderMapper;

    @Override
    public Long save(MallOrderCreate create) {
        log.info("保存订单：{}", create);
        if (StringUtils.hasText(create.getOrderNo())
                && mallOrderRepository.findByOrderNo(create.getOrderNo()) != null) {
            throw new BusinessException("订单号已存在");
        }
        MallOrder order = mallOrderMapper.toOrder(create);
        mallOrderRepository.save(order);
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallOrderUpdate update) {
        log.info("修改订单：{}", update);
        MallOrder order = mallOrderRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("订单不存在"));
        mallOrderMapper.updateOrderFromDto(update, order);
        mallOrderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        log.info("删除订单：{}", id);
        mallOrderRepository.findById(id).orElseThrow(() -> new BusinessException("订单不存在"));
        mallOrderRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除订单：{}", ids);
        mallOrderRepository.deleteAllById(ids);
    }

    @Override
    public MallOrderVo findById(Long id) {
        log.info("根据ID查询订单：{}", id);
        MallOrder order = mallOrderRepository.findById(id).orElse(null);
        return order != null ? mallOrderMapper.toVo(order) : null;
    }

    @Override
    public MallOrderVo findByOrderNo(String orderNo) {
        log.info("根据订单号查询订单：{}", orderNo);
        MallOrder order = mallOrderRepository.findByOrderNo(orderNo);
        return order != null ? mallOrderMapper.toVo(order) : null;
    }

    @Override
    public PageVo<List<MallOrderVo>> findPage(MallOrderQuery query) {
        log.info("分页查询订单：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Specification<MallOrder> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getOrderNo())) predicates.add(cb.equal(root.get("orderNo"), query.getOrderNo()));
            if (query.getUserId() != null) predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            if (query.getShopId() != null) predicates.add(cb.equal(root.get("shopId"), query.getShopId()));
            if (query.getStatus() != null) predicates.add(cb.equal(root.get("status"), query.getStatus()));
            if (query.getPayType() != null) predicates.add(cb.equal(root.get("payType"), query.getPayType()));
            if (query.getSourceType() != null) predicates.add(cb.equal(root.get("sourceType"), query.getSourceType()));
            if (query.getStartTime() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), query.getStartTime()));
            if (query.getEndTime() != null) predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), query.getEndTime()));
            return predicates;
        });
        Page<MallOrder> page = mallOrderRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallOrderMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallOrderVo> findAll() {
        return mallOrderMapper.toVo(mallOrderRepository.findAll(Sort.by("id").descending()));
    }
}
