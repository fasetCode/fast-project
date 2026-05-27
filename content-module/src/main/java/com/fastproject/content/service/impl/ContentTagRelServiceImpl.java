package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentTagRel;
import com.fastproject.content.mapper.ContentTagRelMapper;
import com.fastproject.content.repository.db.ContentTagRelRepository;
import com.fastproject.content.service.ContentTagRelService;
import com.fastproject.content.vo.tagrel.ContentTagRelCreate;
import com.fastproject.content.vo.tagrel.ContentTagRelQuery;
import com.fastproject.content.vo.tagrel.ContentTagRelUpdate;
import com.fastproject.content.vo.tagrel.ContentTagRelVo;
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
public class ContentTagRelServiceImpl implements ContentTagRelService {

    private final ContentTagRelRepository repository;
    private final ContentTagRelMapper mapper;

    @Override
    public Long save(ContentTagRelCreate create) {
        ContentTagRel entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentTagRelUpdate update) {
        ContentTagRel entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentTagRelVo findById(Long id) {
        ContentTagRel entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentTagRelVo>> findPage(ContentTagRelQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentTagRel> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentTagRelVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentTagRelVo> selectAll() {
        return findAll();
    }
}
