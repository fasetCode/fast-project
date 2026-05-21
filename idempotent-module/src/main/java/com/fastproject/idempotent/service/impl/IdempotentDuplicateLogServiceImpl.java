package com.fastproject.idempotent.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.idempotent.domain.IdempotentDuplicateLog;
import com.fastproject.idempotent.mapper.IdempotentDuplicateLogMapper;
import com.fastproject.idempotent.repository.db.IdempotentDuplicateLogRepository;
import com.fastproject.idempotent.service.IdempotentDuplicateLogService;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogCreate;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogQuery;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogUpdate;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogVo;
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
 * 幂等重复提交记录 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class IdempotentDuplicateLogServiceImpl implements IdempotentDuplicateLogService {

    private final IdempotentDuplicateLogRepository logRepository;
    private final QueryHelp<IdempotentDuplicateLog> queryHelp;
    private final IdempotentDuplicateLogMapper logMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(IdempotentDuplicateLogCreate create) {
        log.info("保存幂等重复提交记录：{}", create);

        // 检查请求ID是否已存在
        if (logRepository.existsByRequestId(create.getRequestId())) {
            throw new BusinessException("请求ID已存在");
        }

        IdempotentDuplicateLog entity = logMapper.toLog(create);
        logRepository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(IdempotentDuplicateLogUpdate update) {
        log.info("修改幂等重复提交记录：{}", update);
        IdempotentDuplicateLog entity = logRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("记录不存在"));

        // 检查请求ID是否已存在（排除自身）
        if (StringUtils.hasText(update.getRequestId()) 
                && !update.getRequestId().equals(entity.getRequestId())
                && logRepository.existsByRequestId(update.getRequestId())) {
            throw new BusinessException("请求ID已存在");
        }

        logMapper.updateLogFromDto(update, entity);
        logRepository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除幂等重复提交记录：{}", id);
        logRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除幂等重复提交记录：{}", ids);
        logRepository.deleteAllById(ids);
    }

    @Override
    public IdempotentDuplicateLogVo findById(Long id) {
        log.info("根据ID查询幂等重复提交记录：{}", id);
        IdempotentDuplicateLog entity = logRepository.findById(id).orElse(null);
        if (entity != null) {
            return logMapper.toVo(entity);
        }
        return null;
    }

    @Override
    public PageVo<List<IdempotentDuplicateLogVo>> findPage(IdempotentDuplicateLogQuery query) {
        log.info("分页查询幂等重复提交记录：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<IdempotentDuplicateLog> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getRequestId())) {
                predicates.add(cb.like(root.get("requestId"), "%" + query.getRequestId() + "%"));
            }
            if (StringUtils.hasText(query.getPrefix())) {
                predicates.add(cb.like(root.get("prefix"), "%" + query.getPrefix() + "%"));
            }
            if (StringUtils.hasText(query.getRequestUrl())) {
                predicates.add(cb.like(root.get("requestUrl"), "%" + query.getRequestUrl() + "%"));
            }
            if (StringUtils.hasText(query.getRequestMethod())) {
                predicates.add(cb.equal(root.get("requestMethod"), query.getRequestMethod()));
            }
            if (query.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            }
            if (StringUtils.hasText(query.getUsername())) {
                predicates.add(cb.like(root.get("username"), "%" + query.getUsername() + "%"));
            }
            if (StringUtils.hasText(query.getIpAddress())) {
                predicates.add(cb.like(root.get("ipAddress"), "%" + query.getIpAddress() + "%"));
            }
            if (StringUtils.hasText(query.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            if (query.getFirstRequestTimeStart() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("firstRequestTime"), query.getFirstRequestTimeStart()));
            }
            if (query.getFirstRequestTimeEnd() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("firstRequestTime"), query.getFirstRequestTimeEnd()));
            }
            return predicates;
        });

        Page<IdempotentDuplicateLog> page = logRepository.findAll(spec, pageable);
        List<IdempotentDuplicateLogVo> list = logMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }
}
