package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentAuditLogRepository extends JpaRepository<ContentAuditLog, Long>, JpaSpecificationExecutor<ContentAuditLog> {
}

