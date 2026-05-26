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
 * 内容修订记录表
 */
@Table(name = "content_revision")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_revision set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentRevision extends BaseEntity {

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 修订版本号
     */
    private Integer version;

    /**
     * 编辑人ID
     */
    private Long editorId;

    /**
     * 编辑原因/备注
     */
    @Column(columnDefinition = "TEXT")
    private String reason;

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
     * 正文HTML（可选）
     */
    @Column(columnDefinition = "TEXT")
    private String contentHtml;

    /**
     * 字数
     */
    private Integer wordCount;
}

