package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentCommentLikeRepository extends JpaRepository<ContentCommentLike, Long>, JpaSpecificationExecutor<ContentCommentLike> {
}

