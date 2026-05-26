package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentCommentLike;
import com.fastproject.content.repository.db.ContentCommentLikeRepository;
import com.fastproject.content.service.ContentCommentLikeService;
import org.springframework.stereotype.Service;

@Service
public class ContentCommentLikeServiceImpl extends AbstractCrudServiceImpl<ContentCommentLike, ContentCommentLikeRepository> implements ContentCommentLikeService {

    public ContentCommentLikeServiceImpl(ContentCommentLikeRepository repository) {
        super(repository);
    }
}

