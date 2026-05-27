package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentComment;
import com.fastproject.content.mapper.ContentCommentMapper;
import com.fastproject.content.repository.db.ContentCommentRepository;
import com.fastproject.content.service.ContentCommentService;
import com.fastproject.content.vo.comment.ContentCommentCreate;
import com.fastproject.content.vo.comment.ContentCommentQuery;
import com.fastproject.content.vo.comment.ContentCommentUpdate;
import com.fastproject.content.vo.comment.ContentCommentVo;
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
public class ContentCommentServiceImpl implements ContentCommentService {

    private final ContentCommentRepository repository;
    private final ContentCommentMapper mapper;

    @Override
    public Long save(ContentCommentCreate create) {
        ContentComment entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentCommentUpdate update) {
        ContentComment entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentCommentVo findById(Long id) {
        ContentComment entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentCommentVo>> findPage(ContentCommentQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentComment> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentCommentVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentCommentVo> selectAll() {
        return findAll();
    }
}
