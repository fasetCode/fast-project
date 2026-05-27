package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentCategoryRel;
import com.fastproject.content.domain.ContentInfo;
import com.fastproject.content.domain.ContentTagRel;
import com.fastproject.content.mapper.ContentInfoMapper;
import com.fastproject.content.repository.db.ContentCategoryRelRepository;
import com.fastproject.content.repository.db.ContentInfoRepository;
import com.fastproject.content.repository.db.ContentTagRelRepository;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentInfoServiceImpl implements ContentInfoService {

    private final ContentInfoRepository repository;
    private final ContentCategoryRelRepository categoryRelRepository;
    private final ContentTagRelRepository tagRelRepository;
    private final ContentInfoMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(ContentInfoCreate create) {
        ContentInfo entity = mapper.toEntity(create);
        repository.save(entity);
        saveRelations(entity.getId(), create.getCategoryIds(), create.getTagIds());
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentInfoUpdate update) {
        ContentInfo entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
        mapper.updateFromDto(update, entity);
        repository.save(entity);
        saveRelations(entity.getId(), update.getCategoryIds(), update.getTagIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new BusinessException("数据不存在"));
        deleteRelations(List.of(id));
        repository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        deleteRelations(ids);
        repository.deleteAllById(ids);
    }

    @Override
    public ContentInfoVo findById(Long id) {
        ContentInfo entity = repository.findById(id).orElse(null);
        return entity != null ? fillRelationData(mapper.toVo(entity)) : null;
    }

    @Override
    public PageVo<List<ContentInfoVo>> findPage(ContentInfoQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentInfo> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), fillRelationData(mapper.toVo(page.getContent())));
    }

    @Override
    public List<ContentInfoVo> findAll() {
        return fillRelationData(mapper.toVo(repository.findAll(Sort.by("id").descending())));
    }

    @Override
    public List<ContentInfoVo> selectAll() {
        return findAll();
    }

    private void saveRelations(Long contentId, List<Long> categoryIds, List<Long> tagIds) {
        replaceCategoryRelations(contentId, categoryIds);
        replaceTagRelations(contentId, tagIds);
    }

    private void replaceCategoryRelations(Long contentId, List<Long> categoryIds) {
        List<ContentCategoryRel> existing = categoryRelRepository.findAllByContentId(contentId);
        if (!existing.isEmpty()) {
            categoryRelRepository.deleteAll(existing);
        }
        List<Long> normalizedCategoryIds = normalizeIds(categoryIds);
        if (normalizedCategoryIds.isEmpty()) {
            return;
        }
        List<ContentCategoryRel> relations = new ArrayList<>(normalizedCategoryIds.size());
        for (Long categoryId : normalizedCategoryIds) {
            ContentCategoryRel relation = new ContentCategoryRel();
            relation.setContentId(contentId);
            relation.setCategoryId(categoryId);
            relations.add(relation);
        }
        categoryRelRepository.saveAll(relations);
    }

    private void replaceTagRelations(Long contentId, List<Long> tagIds) {
        List<ContentTagRel> existing = tagRelRepository.findAllByContentId(contentId);
        if (!existing.isEmpty()) {
            tagRelRepository.deleteAll(existing);
        }
        List<Long> normalizedTagIds = normalizeTagIds(tagIds);
        if (normalizedTagIds.isEmpty()) {
            return;
        }
        List<ContentTagRel> relations = new ArrayList<>(normalizedTagIds.size());
        for (Long tagId : normalizedTagIds) {
            ContentTagRel relation = new ContentTagRel();
            relation.setContentId(contentId);
            relation.setTagId(tagId);
            relations.add(relation);
        }
        tagRelRepository.saveAll(relations);
    }

    private void deleteRelations(List<Long> contentIds) {
        if (contentIds == null || contentIds.isEmpty()) {
            return;
        }
        List<ContentCategoryRel> categoryRelations = categoryRelRepository.findAllByContentIdIn(contentIds);
        if (!categoryRelations.isEmpty()) {
            categoryRelRepository.deleteAll(categoryRelations);
        }
        List<ContentTagRel> tagRelations = tagRelRepository.findAllByContentIdIn(contentIds);
        if (!tagRelations.isEmpty()) {
            tagRelRepository.deleteAll(tagRelations);
        }
    }

    private ContentInfoVo fillRelationData(ContentInfoVo vo) {
        if (vo == null || vo.getId() == null) {
            return vo;
        }
        List<ContentCategoryRel> categoryRelations = categoryRelRepository.findAllByContentId(vo.getId());
        vo.setCategoryIds(normalizeIds(categoryRelations.stream().map(ContentCategoryRel::getCategoryId).toList()));
        List<ContentTagRel> tagRelations = tagRelRepository.findAllByContentId(vo.getId());
        vo.setTagIds(normalizeTagIds(tagRelations.stream().map(ContentTagRel::getTagId).toList()));
        return vo;
    }

    private List<ContentInfoVo> fillRelationData(List<ContentInfoVo> list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        List<Long> contentIds = list.stream()
                .map(ContentInfoVo::getId)
                .filter(id -> id != null)
                .toList();
        if (contentIds.isEmpty()) {
            return list;
        }

        Map<Long, List<Long>> categoryMap = categoryRelRepository.findAllByContentIdIn(contentIds).stream()
                .collect(Collectors.groupingBy(
                        ContentCategoryRel::getContentId,
                        Collectors.mapping(ContentCategoryRel::getCategoryId, Collectors.toList())
                ));
        Map<Long, List<Long>> tagMap = tagRelRepository.findAllByContentIdIn(contentIds).stream()
                .collect(Collectors.groupingBy(
                        ContentTagRel::getContentId,
                        Collectors.mapping(ContentTagRel::getTagId, Collectors.toList())
                ));

        for (ContentInfoVo vo : list) {
            if (vo.getId() == null) {
                continue;
            }
            vo.setCategoryIds(normalizeIds(categoryMap.get(vo.getId())));
            vo.setTagIds(normalizeTagIds(tagMap.get(vo.getId())));
        }
        return list;
    }

    private List<Long> normalizeTagIds(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(tagIds.stream()
                .filter(tagId -> tagId != null)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    private List<Long> normalizeIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(ids.stream()
                .filter(id -> id != null)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
    }
}
