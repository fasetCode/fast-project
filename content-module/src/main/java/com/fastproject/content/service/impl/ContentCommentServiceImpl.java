package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentComment;
import com.fastproject.content.repository.db.ContentCommentRepository;
import com.fastproject.content.service.ContentCommentService;
import org.springframework.stereotype.Service;

@Service
public class ContentCommentServiceImpl extends AbstractCrudServiceImpl<ContentComment, ContentCommentRepository> implements ContentCommentService {

    public ContentCommentServiceImpl(ContentCommentRepository repository) {
        super(repository);
    }
}

