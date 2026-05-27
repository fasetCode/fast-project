package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentLike;
import com.fastproject.content.mapper.ContentLikeMapper;
import com.fastproject.content.repository.db.ContentLikeRepository;
import com.fastproject.content.service.ContentLikeService;
import com.fastproject.content.vo.like.ContentLikeCreate;
import com.fastproject.content.vo.like.ContentLikeQuery;
import com.fastproject.content.vo.like.ContentLikeUpdate;
import com.fastproject.content.vo.like.ContentLikeVo;
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
public class ContentLikeServiceImpl implements ContentLikeService {

    private final ContentLikeRepository repository;
    private final ContentLikeMapper mapper;

    @Override
    public Long save(ContentLikeCreate create) {
        ContentLike entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentLikeUpdate update) {
        ContentLike entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentLikeVo findById(Long id) {
        ContentLike entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentLikeVo>> findPage(ContentLikeQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentLike> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentLikeVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentLikeVo> selectAll() {
        return findAll();
    }
}
