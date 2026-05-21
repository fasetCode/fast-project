package com.fastproject.usergrowth.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.usergrowth.domain.UserIntegralRecord;
import com.fastproject.usergrowth.mapper.UserIntegralRecordMapper;
import com.fastproject.usergrowth.repository.db.UserIntegralRecordRepository;
import com.fastproject.usergrowth.service.UserIntegralRecordService;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordCreate;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordQuery;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordUpdate;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordVo;
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

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserIntegralRecordServiceImpl implements UserIntegralRecordService {

    private final UserIntegralRecordRepository repository;
    private final QueryHelp<UserIntegralRecord> queryHelp;
    private final UserIntegralRecordMapper mapper;

    @Override
    public Long save(UserIntegralRecordCreate create) {
        log.info("保存信息：{}", create);
        UserIntegralRecord entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserIntegralRecordUpdate update) {
        log.info("修改信息：{}", update);
        UserIntegralRecord entity = repository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("数据不存在"));
        mapper.updateFromDto(update, entity);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("删除信息：{}", id);
        repository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除信息：{}", ids);
        repository.deleteAllById(ids);
    }

    @Override
    public UserIntegralRecordVo findById(Long id) {
        log.info("根据ID查询信息：{}", id);
        return repository.findById(id).map(mapper::toVo).orElse(null);
    }

    @Override
    public PageVo<List<UserIntegralRecordVo>> findPage(UserIntegralRecordQuery query) {
        log.info("分页查询信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<UserIntegralRecord> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            }
            if (query.getChangeValue() != null) {
                predicates.add(cb.equal(root.get("changeValue"), query.getChangeValue()));
            }
            if (org.springframework.util.StringUtils.hasText(query.getDescription())) {
                predicates.add(cb.like(root.get("description"), "%" + query.getDescription() + "%"));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            if (query.getType() != null) {
                predicates.add(cb.equal(root.get("type"), query.getType()));
            }
            if (query.getBusinessId() != null) {
                predicates.add(cb.equal(root.get("businessId"), query.getBusinessId()));
            }
            if (org.springframework.util.StringUtils.hasText(query.getBusinessName())) {
                predicates.add(cb.like(root.get("businessName"), "%" + query.getBusinessName() + "%"));
            }
            if (org.springframework.util.StringUtils.hasText(query.getBizType())) {
                predicates.add(cb.like(root.get("bizType"), "%" + query.getBizType() + "%"));
            }
            return predicates;
        });

        Page<UserIntegralRecord> page = repository.findAll(spec, pageable);
        List<UserIntegralRecordVo> list = mapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }
}
