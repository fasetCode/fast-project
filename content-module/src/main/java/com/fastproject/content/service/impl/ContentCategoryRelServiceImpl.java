package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentCategoryRel;
import com.fastproject.content.repository.db.ContentCategoryRelRepository;
import com.fastproject.content.service.ContentCategoryRelService;
import org.springframework.stereotype.Service;

@Service
public class ContentCategoryRelServiceImpl extends AbstractCrudServiceImpl<ContentCategoryRel, ContentCategoryRelRepository> implements ContentCategoryRelService {

    public ContentCategoryRelServiceImpl(ContentCategoryRelRepository repository) {
        super(repository);
    }
}

