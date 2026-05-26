package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentViewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentViewLogRepository extends JpaRepository<ContentViewLog, Long>, JpaSpecificationExecutor<ContentViewLog> {
}

