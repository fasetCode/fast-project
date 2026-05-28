package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentBody;
import com.fastproject.content.domain.ContentCategoryRel;
import com.fastproject.content.domain.ContentInfo;
import com.fastproject.content.domain.ContentRevision;
import com.fastproject.content.domain.ContentTagRel;
import com.fastproject.content.mapper.ContentInfoMapper;
import com.fastproject.content.repository.db.ContentBodyRepository;
import com.fastproject.content.repository.db.ContentCategoryRelRepository;
import com.fastproject.content.repository.db.ContentInfoRepository;
import com.fastproject.content.repository.db.ContentRevisionRepository;
import com.fastproject.content.repository.db.ContentTagRelRepository;
import com.fastproject.content.service.ContentInfoService;
import com.fastproject.content.vo.info.ContentInfoCreate;
import com.fastproject.content.vo.info.ContentInfoQuery;
import com.fastproject.content.vo.info.ContentInfoUpdate;
import com.fastproject.content.vo.info.ContentInfoVo;
import com.fastproject.exception.BusinessException;
import com.fastproject.utils.vo.PageVo;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentInfoServiceImpl implements ContentInfoService {

    private final ContentInfoRepository repository;
    private final ContentBodyRepository contentBodyRepository;
    private final ContentRevisionRepository contentRevisionRepository;
    private final ContentCategoryRelRepository categoryRelRepository;
    private final ContentTagRelRepository tagRelRepository;
    private final ContentInfoMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(ContentInfoCreate create) {
        ContentInfo entity = mapper.toEntity(create);
        repository.save(entity);
        saveBody(entity.getId(), create.getFormat(), create.getContent(), create.getContentHtml(), create.getWordCount(), create.getReadingTime());
        saveRevision(entity.getId(), create.getFormat(), create.getContent(), create.getContentHtml(), create.getWordCount());
        saveRelations(entity.getId(), create.getCategoryIds(), create.getTagIds());
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentInfoUpdate update) {
        ContentInfo entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
        mapper.updateFromDto(update, entity);
        repository.save(entity);
        saveBody(entity.getId(), update.getFormat(), update.getContent(), update.getContentHtml(), update.getWordCount(), update.getReadingTime());
        saveRevision(entity.getId(), update.getFormat(), update.getContent(), update.getContentHtml(), update.getWordCount());
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
        return entity != null ? fillBodyData(fillRelationData(mapper.toVo(entity))) : null;
    }

    @Override
    public PageVo<List<ContentInfoVo>> findPage(ContentInfoQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Specification<ContentInfo> spec = (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(query.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + query.getTitle().trim() + "%"));
            }
            if (query.getAuthorId() != null) {
                predicates.add(cb.equal(root.get("authorId"), query.getAuthorId()));
            }
            if (query.getAuditBy() != null) {
                predicates.add(cb.equal(root.get("auditBy"), query.getAuditBy()));
            }

            List<Long> categoryIds = normalizeIds(query.getCategoryIds());
            if (!categoryIds.isEmpty()) {
                Subquery<Long> categorySubQuery = criteriaQuery.subquery(Long.class);
                Root<ContentCategoryRel> categoryRoot = categorySubQuery.from(ContentCategoryRel.class);
                categorySubQuery.select(categoryRoot.get("contentId"))
                        .where(categoryRoot.get("categoryId").in(categoryIds));
                predicates.add(root.get("id").in(categorySubQuery));
            }

            List<Long> tagIds = normalizeTagIds(query.getTagIds());
            if (!tagIds.isEmpty()) {
                Subquery<Long> tagSubQuery = criteriaQuery.subquery(Long.class);
                Root<ContentTagRel> tagRoot = tagSubQuery.from(ContentTagRel.class);
                tagSubQuery.select(tagRoot.get("contentId"))
                        .where(tagRoot.get("tagId").in(tagIds));
                predicates.add(root.get("id").in(tagSubQuery));
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<ContentInfo> page = repository.findAll(spec, pageable);
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
        List<ContentBody> bodies = contentBodyRepository.findAllByContentIdIn(contentIds);
        if (!bodies.isEmpty()) {
            contentBodyRepository.deleteAll(bodies);
        }
        List<ContentRevision> revisions = contentRevisionRepository.findAllByContentIdIn(contentIds);
        if (!revisions.isEmpty()) {
            contentRevisionRepository.deleteAll(revisions);
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

    private ContentInfoVo fillBodyData(ContentInfoVo vo) {
        if (vo == null || vo.getId() == null) {
            return vo;
        }
        ContentBody body = contentBodyRepository.findTopByContentIdOrderByIdDesc(vo.getId());
        if (body == null) {
            return vo;
        }
        vo.setFormat(body.getFormat());
        vo.setContent(body.getContent());
        vo.setContentHtml(body.getContentHtml());
        vo.setWordCount(body.getWordCount());
        vo.setReadingTime(body.getReadingTime());
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

    private void saveBody(Long contentId, String format, String content, String contentHtml, Integer wordCount, Integer readingTime) {
        if (contentId == null) {
            return;
        }
        ContentBody body = contentBodyRepository.findTopByContentIdOrderByIdDesc(contentId);
        if (body == null) {
            if (isBodyEmpty(format, content, contentHtml, wordCount, readingTime)) {
                return;
            }
            body = new ContentBody();
            body.setContentId(contentId);
        }
        body.setFormat(format);
        body.setContent(content);
        body.setContentHtml(contentHtml);
        body.setWordCount(wordCount);
        body.setReadingTime(readingTime);
        contentBodyRepository.save(body);
    }

    private boolean isBodyEmpty(String format, String content, String contentHtml, Integer wordCount, Integer readingTime) {
        return isBlank(format)
                && isBlank(content)
                && isBlank(contentHtml)
                && wordCount == null
                && readingTime == null;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private void saveRevision(Long contentId, String format, String content, String contentHtml, Integer wordCount) {
        if (contentId == null || isBodyEmpty(format, content, contentHtml, wordCount, null)) {
            return;
        }
        ContentRevision latest = contentRevisionRepository.findTopByContentIdOrderByVersionDescIdDesc(contentId);
        if (latest != null && isSameRevision(latest, format, content, contentHtml, wordCount)) {
            return;
        }
        ContentRevision revision = new ContentRevision();
        revision.setContentId(contentId);
        revision.setVersion(latest == null || latest.getVersion() == null ? 1 : latest.getVersion() + 1);
        revision.setFormat(format);
        revision.setContent(content);
        revision.setContentHtml(contentHtml);
        revision.setWordCount(wordCount);
        contentRevisionRepository.save(revision);
    }

    private boolean isSameRevision(ContentRevision revision, String format, String content, String contentHtml, Integer wordCount) {
        return Objects.equals(revision.getFormat(), format)
                && Objects.equals(revision.getContent(), content)
                && Objects.equals(revision.getContentHtml(), contentHtml)
                && Objects.equals(revision.getWordCount(), wordCount);
    }
}
