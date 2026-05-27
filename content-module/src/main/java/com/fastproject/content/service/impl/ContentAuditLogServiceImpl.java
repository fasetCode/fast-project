package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentAuditLog;
import com.fastproject.content.mapper.ContentAuditLogMapper;
import com.fastproject.content.repository.db.ContentAuditLogRepository;
import com.fastproject.content.service.ContentAuditLogService;
import com.fastproject.content.vo.auditlog.ContentAuditLogCreate;
import com.fastproject.content.vo.auditlog.ContentAuditLogQuery;
import com.fastproject.content.vo.auditlog.ContentAuditLogUpdate;
import com.fastproject.content.vo.auditlog.ContentAuditLogVo;
import com.fastproject.exception.BusinessException;
import com.fastproject.utils.vo.PageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentAuditLogServiceImpl implements ContentAuditLogService {

    private final ContentAuditLogRepository repository;
    private final ContentAuditLogMapper mapper;

    @Override
    public Long save(ContentAuditLogCreate create) {
        ContentAuditLog entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentAuditLogUpdate update) {
        ContentAuditLog entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
        mapper.updateFromDto(update, entity);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new BusinessException("数据不存在"));
        repository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public ContentAuditLogVo findById(Long id) {
        ContentAuditLog entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentAuditLogVo>> findPage(ContentAuditLogQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentAuditLog> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentAuditLogVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentAuditLogVo> selectAll() {
        return findAll();
    }
}
