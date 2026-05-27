package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentAttachment;
import com.fastproject.content.mapper.ContentAttachmentMapper;
import com.fastproject.content.repository.db.ContentAttachmentRepository;
import com.fastproject.content.service.ContentAttachmentService;
import com.fastproject.content.vo.attachment.ContentAttachmentCreate;
import com.fastproject.content.vo.attachment.ContentAttachmentQuery;
import com.fastproject.content.vo.attachment.ContentAttachmentUpdate;
import com.fastproject.content.vo.attachment.ContentAttachmentVo;
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
public class ContentAttachmentServiceImpl implements ContentAttachmentService {

    private final ContentAttachmentRepository repository;
    private final ContentAttachmentMapper mapper;

    @Override
    public Long save(ContentAttachmentCreate create) {
        ContentAttachment entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentAttachmentUpdate update) {
        ContentAttachment entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentAttachmentVo findById(Long id) {
        ContentAttachment entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentAttachmentVo>> findPage(ContentAttachmentQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentAttachment> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentAttachmentVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentAttachmentVo> selectAll() {
        return findAll();
    }
}
