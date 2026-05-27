package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ContentBodyRepository extends JpaRepository<ContentBody, Long>, JpaSpecificationExecutor<ContentBody> {
    ContentBody findTopByContentIdOrderByIdDesc(Long contentId);

    List<ContentBody> findAllByContentIdIn(List<Long> contentIds);
}
