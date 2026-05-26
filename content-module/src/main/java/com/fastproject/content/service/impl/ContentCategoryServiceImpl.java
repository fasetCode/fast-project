package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentCategory;
import com.fastproject.content.repository.db.ContentCategoryRepository;
import com.fastproject.content.service.ContentCategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
}
