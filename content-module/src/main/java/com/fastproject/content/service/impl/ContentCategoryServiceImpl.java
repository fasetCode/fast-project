package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentCategory;
import com.fastproject.content.repository.db.ContentCategoryRepository;
import com.fastproject.content.service.ContentCategoryService;
import org.springframework.stereotype.Service;

@Service
public class ContentCategoryServiceImpl extends AbstractCrudServiceImpl<ContentCategory, ContentCategoryRepository> implements ContentCategoryService {

    public ContentCategoryServiceImpl(ContentCategoryRepository repository) {
        super(repository);
    }
}

