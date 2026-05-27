package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentTag;
import com.fastproject.content.mapper.ContentTagMapper;
import com.fastproject.content.repository.db.ContentTagRepository;
import com.fastproject.content.service.ContentTagService;
import com.fastproject.content.vo.tag.ContentTagCreate;
import com.fastproject.content.vo.tag.ContentTagQuery;
import com.fastproject.content.vo.tag.ContentTagUpdate;
import com.fastproject.content.vo.tag.ContentTagVo;
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
public class ContentTagServiceImpl implements ContentTagService {

    private final ContentTagRepository repository;
    private final ContentTagMapper mapper;

    @Override
    public Long save(ContentTagCreate create) {
        ContentTag entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentTagUpdate update) {
        ContentTag entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentTagVo findById(Long id) {
        ContentTag entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentTagVo>> findPage(ContentTagQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentTag> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentTagVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentTagVo> selectAll() {
        return findAll();
    }
}
