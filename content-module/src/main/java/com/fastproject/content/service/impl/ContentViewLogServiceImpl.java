package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentViewLog;
import com.fastproject.content.mapper.ContentViewLogMapper;
import com.fastproject.content.repository.db.ContentViewLogRepository;
import com.fastproject.content.service.ContentViewLogService;
import com.fastproject.content.vo.viewlog.ContentViewLogCreate;
import com.fastproject.content.vo.viewlog.ContentViewLogQuery;
import com.fastproject.content.vo.viewlog.ContentViewLogUpdate;
import com.fastproject.content.vo.viewlog.ContentViewLogVo;
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
public class ContentViewLogServiceImpl implements ContentViewLogService {

    private final ContentViewLogRepository repository;
    private final ContentViewLogMapper mapper;

    @Override
    public Long save(ContentViewLogCreate create) {
        ContentViewLog entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentViewLogUpdate update) {
        ContentViewLog entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentViewLogVo findById(Long id) {
        ContentViewLog entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentViewLogVo>> findPage(ContentViewLogQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentViewLog> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentViewLogVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentViewLogVo> selectAll() {
        return findAll();
    }
}
