package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentInfo;
import com.fastproject.content.repository.db.ContentInfoRepository;
import com.fastproject.content.service.ContentInfoService;
import org.springframework.stereotype.Service;

@Service
public class ContentInfoServiceImpl extends AbstractCrudServiceImpl<ContentInfo, ContentInfoRepository> implements ContentInfoService {

    public ContentInfoServiceImpl(ContentInfoRepository repository) {
        super(repository);
    }
}

