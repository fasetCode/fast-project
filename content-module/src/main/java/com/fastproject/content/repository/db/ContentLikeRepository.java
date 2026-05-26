package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentLikeRepository extends JpaRepository<ContentLike, Long>, JpaSpecificationExecutor<ContentLike> {
}

