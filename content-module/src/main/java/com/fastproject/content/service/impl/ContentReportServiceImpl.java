package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentReport;
import com.fastproject.content.repository.db.ContentReportRepository;
import com.fastproject.content.service.ContentReportService;
import org.springframework.stereotype.Service;

@Service
public class ContentReportServiceImpl extends AbstractCrudServiceImpl<ContentReport, ContentReportRepository> implements ContentReportService {

    public ContentReportServiceImpl(ContentReportRepository repository) {
        super(repository);
    }
}

