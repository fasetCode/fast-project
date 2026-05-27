package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentRevision;
import com.fastproject.content.mapper.ContentRevisionMapper;
import com.fastproject.content.repository.db.ContentRevisionRepository;
import com.fastproject.content.service.ContentRevisionService;
import com.fastproject.content.vo.revision.ContentRevisionCreate;
import com.fastproject.content.vo.revision.ContentRevisionQuery;
import com.fastproject.content.vo.revision.ContentRevisionUpdate;
import com.fastproject.content.vo.revision.ContentRevisionVo;
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
public class ContentRevisionServiceImpl implements ContentRevisionService {

    private final ContentRevisionRepository repository;
    private final ContentRevisionMapper mapper;

    @Override
    public Long save(ContentRevisionCreate create) {
        ContentRevision entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentRevisionUpdate update) {
        ContentRevision entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentRevisionVo findById(Long id) {
        ContentRevision entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentRevisionVo>> findPage(ContentRevisionQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentRevision> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentRevisionVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentRevisionVo> selectAll() {
        return findAll();
    }
}
