package com.fastproject.logs.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.logs.domain.OperationLog;
import com.fastproject.logs.mapper.OperationLogMapper;
import com.fastproject.logs.repository.OperationLogRepository;
import com.fastproject.logs.service.OperationLogService;
import com.fastproject.logs.vo.OperationLogCreate;
import com.fastproject.logs.vo.OperationLogQuery;
import com.fastproject.logs.vo.OperationLogUpdate;
import com.fastproject.logs.vo.OperationLogVo;
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
 * 操作日志 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {

    private final OperationLogRepository operationLogRepository;
    private final QueryHelp<OperationLog> queryHelp;
    private final OperationLogMapper operationLogMapper;

    @Override
    public Long save(OperationLogCreate create) {
        log.info("保存操作日志信息：{}", create);
        OperationLog operationLog = operationLogMapper.toOperationLog(create);
        operationLogRepository.save(operationLog);
        return operationLog.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OperationLogUpdate update) {
        log.info("修改操作日志信息：{}", update);
        OperationLog operationLog = operationLogRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("日志不存在"));
        operationLogMapper.updateOperationLogFromDto(update, operationLog);
        operationLogRepository.save(operationLog);
    }

    @Override
    public void delete(Long id) {
        log.info("删除操作日志信息：{}", id);
        operationLogRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除操作日志信息：{}", ids);
        operationLogRepository.deleteAllById(ids);
    }

    @Override
    public OperationLogVo findById(Long id) {
        log.info("根据ID查询操作日志信息：{}", id);
        OperationLog operationLog = operationLogRepository.findById(id).orElse(null);
        if (operationLog != null) {
            return operationLogMapper.toVo(operationLog);
        }
        return null;
    }

    @Override
    public PageVo<List<OperationLogVo>> findPage(OperationLogQuery query) {
        log.info("分页查询操作日志信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<OperationLog> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getDescription())) {
                predicates.add(cb.like(root.get("description"), "%" + query.getDescription() + "%"));
            }
            if (query.getType() != null) {
                predicates.add(cb.equal(root.get("type"), query.getType()));
            }
            if (query.getAction() != null) {
                predicates.add(cb.equal(root.get("action"), query.getAction()));
            }
            if (StringUtils.hasText(query.getIp())) {
                predicates.add(cb.like(root.get("ip"), "%" + query.getIp() + "%"));
            }
            if (StringUtils.hasText(query.getUrl())) {
                predicates.add(cb.like(root.get("url"), "%" + query.getUrl() + "%"));
            }
            if (query.getSuccess() != null) {
                predicates.add(cb.equal(root.get("success"), query.getSuccess()));
            }
            if (query.getCreateBy() != null) {
                predicates.add(cb.equal(root.get("createBy"), query.getCreateBy()));
            }
            if (query.getStartTime() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), query.getStartTime()));
            }
            if (query.getEndTime() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), query.getEndTime()));
            }
            return predicates;
        });

        Page<OperationLog> page = operationLogRepository.findAll(spec, pageable);
        List<OperationLogVo> list = operationLogMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }
}
