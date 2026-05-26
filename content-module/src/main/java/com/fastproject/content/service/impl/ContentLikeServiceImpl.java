package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentLike;
import com.fastproject.content.repository.db.ContentLikeRepository;
import com.fastproject.content.service.ContentLikeService;
import org.springframework.stereotype.Service;

@Service
public class ContentLikeServiceImpl extends AbstractCrudServiceImpl<ContentLike, ContentLikeRepository> implements ContentLikeService {

    public ContentLikeServiceImpl(ContentLikeRepository repository) {
        super(repository);
    }
}

