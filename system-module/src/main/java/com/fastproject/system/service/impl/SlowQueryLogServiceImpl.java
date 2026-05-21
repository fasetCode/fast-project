package com.fastproject.system.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.system.domain.SlowQueryLog;
import com.fastproject.system.enums.SlowQuerySeverity;
import com.fastproject.system.enums.SlowQueryStatus;
import com.fastproject.system.mapper.SlowQueryLogMapper;
import com.fastproject.system.repository.db.SlowQueryLogRepository;
import com.fastproject.system.service.SlowQueryLogService;
import com.fastproject.system.vo.slowquery.SlowQueryLogQuery;
import com.fastproject.system.vo.slowquery.SlowQueryLogVo;
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
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlowQueryLogServiceImpl implements SlowQueryLogService {

    private final SlowQueryLogRepository slowQueryLogRepository;
    private final SlowQueryLogMapper slowQueryLogMapper;
    private final QueryHelp<SlowQueryLog> queryHelp;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void log(String sql, long executionTime) {
        if (executionTime < 200) {
            return;
        }

        String md5 = DigestUtils.md5DigestAsHex(sql.getBytes(StandardCharsets.UTF_8));
        
        // 判断严重程度
        SlowQuerySeverity severity;
        if (executionTime >= 1000) {
            severity = SlowQuerySeverity.FATAL;
        } else if (executionTime >= 500) {
            severity = SlowQuerySeverity.CRITICAL;
        } else {
            severity = SlowQuerySeverity.WARNING;
        }

        Optional<SlowQueryLog> existing = slowQueryLogRepository.findBySqlMd5AndSeverity(md5, severity);
        SlowQueryLog logEntry;
        if (existing.isPresent()) {
            logEntry = existing.get();
            // 如果已经存在同等级记录，更新耗时并累加触发次数
            logEntry.setExecutionTime(executionTime);
            logEntry.setTriggerCount((logEntry.getTriggerCount() == null ? 0 : logEntry.getTriggerCount()) + 1);
        } else {
            // 不存在同等级记录，创建新记录
            logEntry = new SlowQueryLog();
            logEntry.setSqlContent(sql);
            logEntry.setSqlMd5(md5);
            logEntry.setExecutionTime(executionTime);
            logEntry.setSeverity(severity);
            logEntry.setModule("JPA");
            logEntry.setTriggerCount(1);
            logEntry.setStatus(SlowQueryStatus.PENDING);
        }
        
        slowQueryLogRepository.save(logEntry);
    }

    @Override
    @Transactional(readOnly = true)
    public PageVo<List<SlowQueryLogVo>> findPage(SlowQueryLogQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        
        Specification<SlowQueryLog> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getSqlContent())) {
                predicates.add(cb.like(root.get("sqlContent"), "%" + query.getSqlContent() + "%"));
            }
            if (StringUtils.hasText(query.getSqlMd5())) {
                predicates.add(cb.equal(root.get("sqlMd5"), query.getSqlMd5()));
            }
            if (StringUtils.hasText(query.getSeverity())) {
                SlowQuerySeverity severity = SlowQuerySeverity.fromDescription(query.getSeverity());
                if (severity != null) {
                    predicates.add(cb.equal(root.get("severity"), severity));
                } else {
                    // 兼容直接传枚举名称的情况
                    try {
                        severity = SlowQuerySeverity.valueOf(query.getSeverity());
                        predicates.add(cb.equal(root.get("severity"), severity));
                    } catch (IllegalArgumentException e) {
                        log.warn("Invalid severity value: {}", query.getSeverity());
                    }
                }
            }
            if (StringUtils.hasText(query.getModule())) {
                predicates.add(cb.equal(root.get("module"), query.getModule()));
            }
            if (StringUtils.hasText(query.getStatus())) {
                SlowQueryStatus status = SlowQueryStatus.fromDescription(query.getStatus());
                if (status != null) {
                    predicates.add(cb.equal(root.get("status"), status));
                } else {
                    try {
                        status = SlowQueryStatus.valueOf(query.getStatus());
                        predicates.add(cb.equal(root.get("status"), status));
                    } catch (IllegalArgumentException e) {
                        log.warn("Invalid status value: {}", query.getStatus());
                    }
                }
            }
            return predicates;
        });

        Page<SlowQueryLog> page = slowQueryLogRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), slowQueryLogMapper.toVo(page.getContent()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        slowQueryLogRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SlowQueryLogVo vo) {
        SlowQueryLog entity = slowQueryLogRepository.findById(vo.getId())
                .orElseThrow(() -> new RuntimeException("记录不存在"));
        entity.setRemark(vo.getRemark());
        
        if (StringUtils.hasText(vo.getStatus())) {
            SlowQueryStatus status = SlowQueryStatus.fromDescription(vo.getStatus());
            if (status != null) {
                entity.setStatus(status);
            } else {
                try {
                    entity.setStatus(SlowQueryStatus.valueOf(vo.getStatus()));
                } catch (IllegalArgumentException ignored) {}
            }
        }
        
        slowQueryLogRepository.save(entity);
    }
}
