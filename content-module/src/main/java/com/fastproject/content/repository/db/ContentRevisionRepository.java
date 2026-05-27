package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ContentRevisionRepository extends JpaRepository<ContentRevision, Long>, JpaSpecificationExecutor<ContentRevision> {
    ContentRevision findTopByContentIdOrderByVersionDescIdDesc(Long contentId);

    List<ContentRevision> findAllByContentIdIn(List<Long> contentIds);
}
