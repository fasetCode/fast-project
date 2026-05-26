package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentAttachment;
import com.fastproject.content.repository.db.ContentAttachmentRepository;
import com.fastproject.content.service.ContentAttachmentService;
import org.springframework.stereotype.Service;

@Service
public class ContentAttachmentServiceImpl extends AbstractCrudServiceImpl<ContentAttachment, ContentAttachmentRepository> implements ContentAttachmentService {

    public ContentAttachmentServiceImpl(ContentAttachmentRepository repository) {
        super(repository);
    }
}

