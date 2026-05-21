package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallOrderLog;
import com.fastproject.mall.mapper.MallOrderLogMapper;
import com.fastproject.mall.repository.db.MallOrderLogRepository;
import com.fastproject.mall.service.MallOrderLogService;
import com.fastproject.mall.vo.orderlog.MallOrderLogCreate;
import com.fastproject.mall.vo.orderlog.MallOrderLogQuery;
import com.fastproject.mall.vo.orderlog.MallOrderLogUpdate;
import com.fastproject.mall.vo.orderlog.MallOrderLogVo;
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
 * 订单日志 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallOrderLogServiceImpl implements MallOrderLogService {

    private final MallOrderLogRepository mallOrderLogRepository;
    private final QueryHelp<MallOrderLog> queryHelp;
    private final MallOrderLogMapper mallOrderLogMapper;

    @Override
    public Long save(MallOrderLogCreate create) {
        log.info("保存订单日志：{}", create);
        MallOrderLog entity = mallOrderLogMapper.toOrderLog(create);
        mallOrderLogRepository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallOrderLogUpdate update) {
        log.info("修改订单日志：{}", update);
        MallOrderLog entity = mallOrderLogRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("订单日志不存在"));
        mallOrderLogMapper.updateOrderLogFromDto(update, entity);
        mallOrderLogRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("删除订单日志：{}", id);
        mallOrderLogRepository.findById(id).orElseThrow(() -> new BusinessException("订单日志不存在"));
        mallOrderLogRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除订单日志：{}", ids);
        mallOrderLogRepository.deleteAllById(ids);
    }

    @Override
    public MallOrderLogVo findById(Long id) {
        log.info("根据ID查询订单日志：{}", id);
        MallOrderLog entity = mallOrderLogRepository.findById(id).orElse(null);
        return entity != null ? mallOrderLogMapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<MallOrderLogVo>> findPage(MallOrderLogQuery query) {
        log.info("分页查询订单日志：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Specification<MallOrderLog> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getOrderId() != null) predicates.add(cb.equal(root.get("orderId"), query.getOrderId()));
            if (StringUtils.hasText(query.getOrderNo())) predicates.add(cb.equal(root.get("orderNo"), query.getOrderNo()));
            if (query.getOperatorType() != null) predicates.add(cb.equal(root.get("operatorType"), query.getOperatorType()));
            if (StringUtils.hasText(query.getAction())) predicates.add(cb.equal(root.get("action"), query.getAction()));
            if (query.getSourceType() != null) predicates.add(cb.equal(root.get("sourceType"), query.getSourceType()));
            return predicates;
        });
        Page<MallOrderLog> page = mallOrderLogRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallOrderLogMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallOrderLogVo> findAll() {
        return mallOrderLogMapper.toVo(mallOrderLogRepository.findAll(Sort.by("id").descending()));
    }
}
