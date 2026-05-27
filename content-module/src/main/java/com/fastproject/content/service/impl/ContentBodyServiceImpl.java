package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentBody;
import com.fastproject.content.mapper.ContentBodyMapper;
import com.fastproject.content.repository.db.ContentBodyRepository;
import com.fastproject.content.service.ContentBodyService;
import com.fastproject.content.vo.body.ContentBodyCreate;
import com.fastproject.content.vo.body.ContentBodyQuery;
import com.fastproject.content.vo.body.ContentBodyUpdate;
import com.fastproject.content.vo.body.ContentBodyVo;
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
public class ContentBodyServiceImpl implements ContentBodyService {

    private final ContentBodyRepository repository;
    private final ContentBodyMapper mapper;

    @Override
    public Long save(ContentBodyCreate create) {
        ContentBody entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentBodyUpdate update) {
        ContentBody entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentBodyVo findById(Long id) {
        ContentBody entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentBodyVo>> findPage(ContentBodyQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentBody> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentBodyVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentBodyVo> selectAll() {
        return findAll();
    }
}
