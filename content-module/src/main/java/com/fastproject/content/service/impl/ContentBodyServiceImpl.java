package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentBody;
import com.fastproject.content.repository.db.ContentBodyRepository;
import com.fastproject.content.service.ContentBodyService;
import org.springframework.stereotype.Service;

@Service
public class ContentBodyServiceImpl extends AbstractCrudServiceImpl<ContentBody, ContentBodyRepository> implements ContentBodyService {

    public ContentBodyServiceImpl(ContentBodyRepository repository) {
        super(repository);
    }
}

