package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentAuditLog;
import com.fastproject.content.repository.db.ContentAuditLogRepository;
import com.fastproject.content.service.ContentAuditLogService;
import org.springframework.stereotype.Service;

@Service
public class ContentAuditLogServiceImpl extends AbstractCrudServiceImpl<ContentAuditLog, ContentAuditLogRepository> implements ContentAuditLogService {

    public ContentAuditLogServiceImpl(ContentAuditLogRepository repository) {
        super(repository);
    }
}

