package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallOrderRefund;
import com.fastproject.mall.mapper.MallOrderRefundMapper;
import com.fastproject.mall.repository.db.MallOrderRefundRepository;
import com.fastproject.mall.service.MallOrderRefundService;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundCreate;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundQuery;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundUpdate;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundVo;
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
 * 退款单 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallOrderRefundServiceImpl implements MallOrderRefundService {

    private final MallOrderRefundRepository mallOrderRefundRepository;
    private final QueryHelp<MallOrderRefund> queryHelp;
    private final MallOrderRefundMapper mallOrderRefundMapper;

    @Override
    public Long save(MallOrderRefundCreate create) {
        log.info("保存退款单：{}", create);
        if (StringUtils.hasText(create.getRefundNo())
                && mallOrderRefundRepository.findByRefundNo(create.getRefundNo()) != null) {
            throw new BusinessException("退款单号已存在");
        }
        MallOrderRefund refund = mallOrderRefundMapper.toOrderRefund(create);
        mallOrderRefundRepository.save(refund);
        return refund.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallOrderRefundUpdate update) {
        log.info("修改退款单：{}", update);
        MallOrderRefund refund = mallOrderRefundRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("退款单不存在"));
        mallOrderRefundMapper.updateOrderRefundFromDto(update, refund);
        mallOrderRefundRepository.save(refund);
    }

    @Override
    public void delete(Long id) {
        log.info("删除退款单：{}", id);
        mallOrderRefundRepository.findById(id).orElseThrow(() -> new BusinessException("退款单不存在"));
        mallOrderRefundRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除退款单：{}", ids);
        mallOrderRefundRepository.deleteAllById(ids);
    }

    @Override
    public MallOrderRefundVo findById(Long id) {
        log.info("根据ID查询退款单：{}", id);
        MallOrderRefund refund = mallOrderRefundRepository.findById(id).orElse(null);
        return refund != null ? mallOrderRefundMapper.toVo(refund) : null;
    }

    @Override
    public MallOrderRefundVo findByRefundNo(String refundNo) {
        log.info("根据退款单号查询退款单：{}", refundNo);
        MallOrderRefund refund = mallOrderRefundRepository.findByRefundNo(refundNo);
        return refund != null ? mallOrderRefundMapper.toVo(refund) : null;
    }

    @Override
    public PageVo<List<MallOrderRefundVo>> findPage(MallOrderRefundQuery query) {
        log.info("分页查询退款单：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Specification<MallOrderRefund> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getRefundNo())) predicates.add(cb.equal(root.get("refundNo"), query.getRefundNo()));
            if (StringUtils.hasText(query.getOrderNo())) predicates.add(cb.equal(root.get("orderNo"), query.getOrderNo()));
            if (query.getOrderId() != null) predicates.add(cb.equal(root.get("orderId"), query.getOrderId()));
            if (query.getUserId() != null) predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            if (query.getShopId() != null) predicates.add(cb.equal(root.get("shopId"), query.getShopId()));
            if (query.getRefundType() != null) predicates.add(cb.equal(root.get("refundType"), query.getRefundType()));
            if (query.getRefundStatus() != null) predicates.add(cb.equal(root.get("refundStatus"), query.getRefundStatus()));
            if (query.getStartTime() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), query.getStartTime()));
            if (query.getEndTime() != null) predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), query.getEndTime()));
            return predicates;
        });
        Page<MallOrderRefund> page = mallOrderRefundRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallOrderRefundMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallOrderRefundVo> findAll() {
        return mallOrderRefundMapper.toVo(mallOrderRefundRepository.findAll(Sort.by("id").descending()));
    }
}
