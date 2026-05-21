package com.fastproject.ratelimit.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.ratelimit.domain.RateLimitRecord;
import com.fastproject.ratelimit.mapper.RateLimitRecordMapper;
import com.fastproject.ratelimit.repository.db.RateLimitRecordRepository;
import com.fastproject.ratelimit.service.RateLimitRecordService;
import com.fastproject.ratelimit.vo.record.RateLimitRecordCreate;
import com.fastproject.ratelimit.vo.record.RateLimitRecordUpdate;
import com.fastproject.ratelimit.vo.record.RateLimitRecordQuery;
import com.fastproject.ratelimit.vo.record.RateLimitRecordVo;
import com.fastproject.utils.vo.PageVo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 限流记录 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RateLimitRecordServiceImpl implements RateLimitRecordService {

    private final RateLimitRecordRepository rateLimitRecordRepository;
    private final QueryHelp<RateLimitRecord> queryHelp;
    private final RateLimitRecordMapper rateLimitRecordMapper;

    @Override
    public Long save(RateLimitRecordCreate create) {
        log.info("保存限流记录信息：{}", create);

        RateLimitRecord record = rateLimitRecordMapper.toRecord(create);
        rateLimitRecordRepository.save(record);
        return record.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RateLimitRecordUpdate update) {
        log.info("修改限流记录信息：{}", update);
        RateLimitRecord record = rateLimitRecordRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("记录不存在"));

        rateLimitRecordMapper.updateRecordFromDto(update, record);
        rateLimitRecordRepository.save(record);
    }

    @Override
    public void delete(Long id) {
        log.info("删除限流记录信息：{}", id);
        rateLimitRecordRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除限流记录信息：{}", ids);
        rateLimitRecordRepository.deleteAllById(ids);
    }

    @Override
    public RateLimitRecordVo findById(Long id) {
        log.info("根据ID查询限流记录信息：{}", id);
        RateLimitRecord record = rateLimitRecordRepository.findById(id).orElse(null);
        if (record != null) {
            return rateLimitRecordMapper.toVo(record);
        }
        return null;
    }

    @Override
    public PageVo<List<RateLimitRecordVo>> findPage(RateLimitRecordQuery query) {
        log.info("分页查询限流记录信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<RateLimitRecord> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getAppCode())) {
                predicates.add(cb.equal(root.get("appCode"), query.getAppCode()));
            }
            if (StringUtils.hasText(query.getLimitKey())) {
                predicates.add(cb.like(root.get("limitKey"), "%" + query.getLimitKey() + "%"));
            }
            if (query.getLimitType() != null) {
                predicates.add(cb.equal(root.get("limitType"), query.getLimitType()));
            }
            if (StringUtils.hasText(query.getTargetValue())) {
                predicates.add(cb.like(root.get("targetValue"), "%" + query.getTargetValue() + "%"));
            }
            if (StringUtils.hasText(query.getUrl())) {
                predicates.add(cb.like(root.get("url"), "%" + query.getUrl() + "%"));
            }
            if (StringUtils.hasText(query.getIp())) {
                predicates.add(cb.equal(root.get("ip"), query.getIp()));
            }
            if (query.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            }
            if (query.getCreateTimeBegin() != null && query.getCreateTimeEnd() != null) {
                predicates.add(cb.between(root.get("createTime"), query.getCreateTimeBegin(), query.getCreateTimeEnd()));
            } else if (query.getCreateTimeBegin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), query.getCreateTimeBegin()));
            } else if (query.getCreateTimeEnd() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), query.getCreateTimeEnd()));
            }
            return predicates;
        });

        Page<RateLimitRecord> page = rateLimitRecordRepository.findAll(spec, pageable);
        List<RateLimitRecordVo> list = rateLimitRecordMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }
}