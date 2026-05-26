package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentReportRepository extends JpaRepository<ContentReport, Long>, JpaSpecificationExecutor<ContentReport> {
}

