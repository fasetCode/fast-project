package com.fastproject.content.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 内容正文表
 */
@Table(name = "content_body")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_body set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentBody extends BaseEntity {

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 正文格式（markdown/html/text 等）
     */
    @Column(length = 50)
    private String format;

    /**
     * 正文内容（原始格式）
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 正文HTML（可选，便于前端直出）
     */
    @Column(columnDefinition = "TEXT")
    private String contentHtml;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 预计阅读时长（分钟）
     */
    private Integer readingTime;
}
