package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentFavorite;
import com.fastproject.content.repository.db.ContentFavoriteRepository;
import com.fastproject.content.service.ContentFavoriteService;
import org.springframework.stereotype.Service;

@Service
public class ContentFavoriteServiceImpl extends AbstractCrudServiceImpl<ContentFavorite, ContentFavoriteRepository> implements ContentFavoriteService {

    public ContentFavoriteServiceImpl(ContentFavoriteRepository repository) {
        super(repository);
    }
}

