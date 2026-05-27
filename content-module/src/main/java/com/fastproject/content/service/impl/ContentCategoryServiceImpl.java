package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentCategory;
import com.fastproject.content.mapper.ContentCategoryMapper;
import com.fastproject.content.repository.db.ContentCategoryRepository;
import com.fastproject.content.service.ContentCategoryService;
import com.fastproject.content.vo.category.ContentCategoryCreate;
import com.fastproject.content.vo.category.ContentCategoryQuery;
import com.fastproject.content.vo.category.ContentCategoryUpdate;
import com.fastproject.content.vo.category.ContentCategoryVo;
import com.fastproject.exception.BusinessException;
import com.fastproject.utils.vo.PageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ContentCategoryServiceImpl implements ContentCategoryService {

    private final ContentCategoryRepository repository;
    private final ContentCategoryMapper mapper;

    @Override
    public Long save(ContentCategoryCreate create) {
        ContentCategory entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentCategoryUpdate update) {
        ContentCategory entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
        mapper.updateFromDto(update, entity);
        repository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (id == null) {
            return;
        }
        softDeleteByIds(collectSelfAndDescendantIds(Set.of(id)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        softDeleteByIds(collectSelfAndDescendantIds(new HashSet<>(ids)));
    }

    @Override
    public ContentCategoryVo findById(Long id) {
        ContentCategory entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentCategoryVo>> findPage(ContentCategoryQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentCategory> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentCategoryVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentCategoryVo> selectAll() {
        return findAll();
    }

    @Override
    public List<ContentCategoryVo> findTree() {
        List<ContentCategoryVo> list = mapper.toVo(repository.findAll(
                Sort.by(Sort.Order.asc("sort").nullsLast(), Sort.Order.desc("id"))
        ));

        Map<Long, ContentCategoryVo> idMap = new HashMap<>(Math.max(16, list.size()));
        for (ContentCategoryVo c : list) {
            idMap.put(c.getId(), c);
            c.setChildren(null);
        }

        List<ContentCategoryVo> roots = new ArrayList<>();
        for (ContentCategoryVo c : list) {
            Long parentId = c.getParentId();
            if (parentId == null || parentId == 0L) {
                roots.add(c);
                continue;
            }

            ContentCategoryVo parent = idMap.get(parentId);
            if (parent == null || Objects.equals(parent.getId(), c.getId())) {
                roots.add(c);
                continue;
            }

            List<ContentCategoryVo> children = parent.getChildren();
            if (children == null) {
                children = new ArrayList<>();
                parent.setChildren(children);
            }
            children.add(c);
        }

        return roots;
    }

    private void softDeleteByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        for (Long id : ids) {
            if (id != null) {
                repository.deleteById(id);
            }
        }
    }

    private Set<Long> collectSelfAndDescendantIds(Set<Long> rootIds) {
        List<ContentCategory> all = repository.findAll();

        Map<Long, List<Long>> childrenMap = new HashMap<>(Math.max(16, all.size()));
        for (ContentCategory c : all) {
            Long parentId = c.getParentId();
            if (parentId == null || parentId == 0L) {
                continue;
            }
            childrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(c.getId());
        }

        Set<Long> result = new LinkedHashSet<>();
        Deque<Long> stack = new ArrayDeque<>(rootIds);
        while (!stack.isEmpty()) {
            Long current = stack.pop();
            if (current == null || !result.add(current)) {
                continue;
            }
            List<Long> children = childrenMap.get(current);
            if (children != null && !children.isEmpty()) {
                for (Long childId : children) {
                    if (childId != null && !Objects.equals(childId, current)) {
                        stack.push(childId);
                    }
                }
            }
        }

        return result;
    }
}
