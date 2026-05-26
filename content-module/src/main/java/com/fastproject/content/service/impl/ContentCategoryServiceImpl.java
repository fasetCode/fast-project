package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentCategory;
import com.fastproject.content.repository.db.ContentCategoryRepository;
import com.fastproject.content.service.ContentCategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ContentCategoryServiceImpl extends AbstractCrudServiceImpl<ContentCategory, ContentCategoryRepository> implements ContentCategoryService {

    public ContentCategoryServiceImpl(ContentCategoryRepository repository) {
        super(repository);
    }

    @Override
    public List<ContentCategory> findTree() {
        List<ContentCategory> list = repository.findAll(
                Sort.by(Sort.Order.asc("sort").nullsLast(), Sort.Order.desc("id"))
        );

        Map<Long, ContentCategory> idMap = new HashMap<>(Math.max(16, list.size()));
        for (ContentCategory c : list) {
            idMap.put(c.getId(), c);
            c.setChildren(null);
        }

        List<ContentCategory> roots = new ArrayList<>();
        for (ContentCategory c : list) {
            Long parentId = c.getParentId();
            if (parentId == null || parentId == 0L) {
                roots.add(c);
                continue;
            }

            ContentCategory parent = idMap.get(parentId);
            if (parent == null || Objects.equals(parent.getId(), c.getId())) {
                roots.add(c);
                continue;
            }

            List<ContentCategory> children = parent.getChildren();
            if (children == null) {
                children = new ArrayList<>();
                parent.setChildren(children);
            }
            children.add(c);
        }

        return roots;
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
