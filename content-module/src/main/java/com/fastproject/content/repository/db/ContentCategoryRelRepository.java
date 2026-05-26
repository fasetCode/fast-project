package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentCategoryRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentCategoryRelRepository extends JpaRepository<ContentCategoryRel, Long>, JpaSpecificationExecutor<ContentCategoryRel> {
}

