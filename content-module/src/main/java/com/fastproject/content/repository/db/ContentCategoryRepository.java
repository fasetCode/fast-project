package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentCategoryRepository extends JpaRepository<ContentCategory, Long>, JpaSpecificationExecutor<ContentCategory> {
}

