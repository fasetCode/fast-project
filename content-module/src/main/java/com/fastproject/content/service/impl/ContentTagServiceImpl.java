package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentTag;
import com.fastproject.content.repository.db.ContentTagRepository;
import com.fastproject.content.service.ContentTagService;
import org.springframework.stereotype.Service;

@Service
public class ContentTagServiceImpl extends AbstractCrudServiceImpl<ContentTag, ContentTagRepository> implements ContentTagService {

    public ContentTagServiceImpl(ContentTagRepository repository) {
        super(repository);
    }
}

