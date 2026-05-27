package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentFavorite;
import com.fastproject.content.mapper.ContentFavoriteMapper;
import com.fastproject.content.repository.db.ContentFavoriteRepository;
import com.fastproject.content.service.ContentFavoriteService;
import com.fastproject.content.vo.favorite.ContentFavoriteCreate;
import com.fastproject.content.vo.favorite.ContentFavoriteQuery;
import com.fastproject.content.vo.favorite.ContentFavoriteUpdate;
import com.fastproject.content.vo.favorite.ContentFavoriteVo;
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
public class ContentFavoriteServiceImpl implements ContentFavoriteService {

    private final ContentFavoriteRepository repository;
    private final ContentFavoriteMapper mapper;

    @Override
    public Long save(ContentFavoriteCreate create) {
        ContentFavorite entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentFavoriteUpdate update) {
        ContentFavorite entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
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
    public ContentFavoriteVo findById(Long id) {
        ContentFavorite entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentFavoriteVo>> findPage(ContentFavoriteQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentFavorite> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentFavoriteVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentFavoriteVo> selectAll() {
        return findAll();
    }
}
