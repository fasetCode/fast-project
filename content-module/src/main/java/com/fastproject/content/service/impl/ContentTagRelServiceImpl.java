package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentTagRel;
import com.fastproject.content.repository.db.ContentTagRelRepository;
import com.fastproject.content.service.ContentTagRelService;
import org.springframework.stereotype.Service;

@Service
public class ContentTagRelServiceImpl extends AbstractCrudServiceImpl<ContentTagRel, ContentTagRelRepository> implements ContentTagRelService {

    public ContentTagRelServiceImpl(ContentTagRelRepository repository) {
        super(repository);
    }
}

