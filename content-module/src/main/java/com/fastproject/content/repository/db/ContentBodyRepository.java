package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentBodyRepository extends JpaRepository<ContentBody, Long>, JpaSpecificationExecutor<ContentBody> {
}

