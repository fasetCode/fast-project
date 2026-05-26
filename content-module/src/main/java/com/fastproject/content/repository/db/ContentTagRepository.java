package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentTagRepository extends JpaRepository<ContentTag, Long>, JpaSpecificationExecutor<ContentTag> {
}

