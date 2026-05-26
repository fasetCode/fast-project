package com.fastproject.content.repository.db;

import com.fastproject.content.domain.ContentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentAttachmentRepository extends JpaRepository<ContentAttachment, Long>, JpaSpecificationExecutor<ContentAttachment> {
}

