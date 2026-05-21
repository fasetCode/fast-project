package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallOrderItem;
import com.fastproject.mall.mapper.MallOrderItemMapper;
import com.fastproject.mall.repository.db.MallOrderItemRepository;
import com.fastproject.mall.service.MallOrderItemService;
import com.fastproject.mall.vo.orderitem.MallOrderItemCreate;
import com.fastproject.mall.vo.orderitem.MallOrderItemQuery;
import com.fastproject.mall.vo.orderitem.MallOrderItemUpdate;
import com.fastproject.mall.vo.orderitem.MallOrderItemVo;
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
 * 订单明细 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallOrderItemServiceImpl implements MallOrderItemService {

    private final MallOrderItemRepository mallOrderItemRepository;
    private final QueryHelp<MallOrderItem> queryHelp;
    private final MallOrderItemMapper mallOrderItemMapper;

    @Override
    public Long save(MallOrderItemCreate create) {
        log.info("保存订单明细：{}", create);
        MallOrderItem item = mallOrderItemMapper.toOrderItem(create);
        mallOrderItemRepository.save(item);
        return item.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallOrderItemUpdate update) {
        log.info("修改订单明细：{}", update);
        MallOrderItem item = mallOrderItemRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("订单明细不存在"));
        mallOrderItemMapper.updateOrderItemFromDto(update, item);
        mallOrderItemRepository.save(item);
    }

    @Override
    public void delete(Long id) {
        log.info("删除订单明细：{}", id);
        mallOrderItemRepository.findById(id).orElseThrow(() -> new BusinessException("订单明细不存在"));
        mallOrderItemRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除订单明细：{}", ids);
        mallOrderItemRepository.deleteAllById(ids);
    }

    @Override
    public MallOrderItemVo findById(Long id) {
        log.info("根据ID查询订单明细：{}", id);
        MallOrderItem item = mallOrderItemRepository.findById(id).orElse(null);
        return item != null ? mallOrderItemMapper.toVo(item) : null;
    }

    @Override
    public PageVo<List<MallOrderItemVo>> findPage(MallOrderItemQuery query) {
        log.info("分页查询订单明细：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Specification<MallOrderItem> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getOrderId() != null) predicates.add(cb.equal(root.get("orderId"), query.getOrderId()));
            if (StringUtils.hasText(query.getOrderNo())) predicates.add(cb.equal(root.get("orderNo"), query.getOrderNo()));
            if (query.getUserId() != null) predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            if (query.getShopId() != null) predicates.add(cb.equal(root.get("shopId"), query.getShopId()));
            if (query.getProductId() != null) predicates.add(cb.equal(root.get("productId"), query.getProductId()));
            if (query.getSkuId() != null) predicates.add(cb.equal(root.get("skuId"), query.getSkuId()));
            if (query.getRefundStatus() != null) predicates.add(cb.equal(root.get("refundStatus"), query.getRefundStatus()));
            if (query.getCommented() != null) predicates.add(cb.equal(root.get("commented"), query.getCommented()));
            return predicates;
        });
        Page<MallOrderItem> page = mallOrderItemRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallOrderItemMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallOrderItemVo> findAll() {
        return mallOrderItemMapper.toVo(mallOrderItemRepository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<MallOrderItemVo> findByOrderId(Long orderId) {
        return mallOrderItemMapper.toVo(mallOrderItemRepository.findByOrderId(orderId));
    }
}
