package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentCommentRepository extends JpaRepository<ContentComment, Long>, JpaSpecificationExecutor<ContentComment> {
}

