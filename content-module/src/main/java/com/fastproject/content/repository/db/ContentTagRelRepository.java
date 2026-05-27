package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentTagRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ContentTagRelRepository extends JpaRepository<ContentTagRel, Long>, JpaSpecificationExecutor<ContentTagRel> {
    List<ContentTagRel> findAllByContentId(Long contentId);

    List<ContentTagRel> findAllByContentIdIn(List<Long> contentIds);
}
