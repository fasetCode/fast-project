package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentFavoriteRepository extends JpaRepository<ContentFavorite, Long>, JpaSpecificationExecutor<ContentFavorite> {
}

