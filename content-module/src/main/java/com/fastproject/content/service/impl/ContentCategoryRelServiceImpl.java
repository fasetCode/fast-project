package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentCategoryRel;
import com.fastproject.content.mapper.ContentCategoryRelMapper;
import com.fastproject.content.repository.db.ContentCategoryRelRepository;
import com.fastproject.content.service.ContentCategoryRelService;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelCreate;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelQuery;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelUpdate;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelVo;
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
public class ContentCategoryRelServiceImpl implements ContentCategoryRelService {

    private final ContentCategoryRelRepository repository;
    private final ContentCategoryRelMapper mapper;

    @Override
    public Long save(ContentCategoryRelCreate create) {
        ContentCategoryRel entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentCategoryRelUpdate update) {
        ContentCategoryRel entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentCategoryRelVo findById(Long id) {
        ContentCategoryRel entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentCategoryRelVo>> findPage(ContentCategoryRelQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentCategoryRel> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentCategoryRelVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentCategoryRelVo> selectAll() {
        return findAll();
    }
}
