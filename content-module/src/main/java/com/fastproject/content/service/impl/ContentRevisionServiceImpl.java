package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentRevision;
import com.fastproject.content.repository.db.ContentRevisionRepository;
import com.fastproject.content.service.ContentRevisionService;
import org.springframework.stereotype.Service;

@Service
public class ContentRevisionServiceImpl extends AbstractCrudServiceImpl<ContentRevision, ContentRevisionRepository> implements ContentRevisionService {

    public ContentRevisionServiceImpl(ContentRevisionRepository repository) {
        super(repository);
    }
}

