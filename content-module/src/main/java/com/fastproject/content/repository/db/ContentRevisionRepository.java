package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentRevisionRepository extends JpaRepository<ContentRevision, Long>, JpaSpecificationExecutor<ContentRevision> {
    ContentRevision findTopByContentIdOrderByVersionDescIdDesc(Long contentId);
}
