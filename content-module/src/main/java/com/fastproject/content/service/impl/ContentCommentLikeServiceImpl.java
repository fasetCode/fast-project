package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentCommentLike;
import com.fastproject.content.mapper.ContentCommentLikeMapper;
import com.fastproject.content.repository.db.ContentCommentLikeRepository;
import com.fastproject.content.service.ContentCommentLikeService;
import com.fastproject.content.vo.commentlike.ContentCommentLikeCreate;
import com.fastproject.content.vo.commentlike.ContentCommentLikeQuery;
import com.fastproject.content.vo.commentlike.ContentCommentLikeUpdate;
import com.fastproject.content.vo.commentlike.ContentCommentLikeVo;
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
public class ContentCommentLikeServiceImpl implements ContentCommentLikeService {

    private final ContentCommentLikeRepository repository;
    private final ContentCommentLikeMapper mapper;

    @Override
    public Long save(ContentCommentLikeCreate create) {
        ContentCommentLike entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentCommentLikeUpdate update) {
        ContentCommentLike entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentCommentLikeVo findById(Long id) {
        ContentCommentLike entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentCommentLikeVo>> findPage(ContentCommentLikeQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentCommentLike> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentCommentLikeVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentCommentLikeVo> selectAll() {
        return findAll();
    }
}
