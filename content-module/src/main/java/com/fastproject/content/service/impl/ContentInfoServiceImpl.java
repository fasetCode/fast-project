package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentInfo;
import com.fastproject.content.mapper.ContentInfoMapper;
import com.fastproject.content.repository.db.ContentInfoRepository;
import com.fastproject.content.service.ContentInfoService;
import com.fastproject.content.vo.info.ContentInfoCreate;
import com.fastproject.content.vo.info.ContentInfoQuery;
import com.fastproject.content.vo.info.ContentInfoUpdate;
import com.fastproject.content.vo.info.ContentInfoVo;
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
public class ContentInfoServiceImpl implements ContentInfoService {

    private final ContentInfoRepository repository;
    private final ContentInfoMapper mapper;

    @Override
    public Long save(ContentInfoCreate create) {
        ContentInfo entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentInfoUpdate update) {
        ContentInfo entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentInfoVo findById(Long id) {
        ContentInfo entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentInfoVo>> findPage(ContentInfoQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentInfo> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentInfoVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentInfoVo> selectAll() {
        return findAll();
    }
}
