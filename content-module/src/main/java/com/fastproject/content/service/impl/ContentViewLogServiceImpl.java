package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentViewLog;
import com.fastproject.content.repository.db.ContentViewLogRepository;
import com.fastproject.content.service.ContentViewLogService;
import org.springframework.stereotype.Service;

@Service
public class ContentViewLogServiceImpl extends AbstractCrudServiceImpl<ContentViewLog, ContentViewLogRepository> implements ContentViewLogService {

    public ContentViewLogServiceImpl(ContentViewLogRepository repository) {
        super(repository);
    }
}

