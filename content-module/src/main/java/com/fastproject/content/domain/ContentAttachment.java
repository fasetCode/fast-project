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
 * 内容附件表
 */
@Table(name = "content_attachment")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_attachment set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentAttachment extends BaseEntity {

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 文件ID（可对接 file-api 的文件主键）
     */
    private Long fileId;

    /**
     * 附件URL（可选，不走 fileId 时使用）
     */
    @Column(length = 500)
    private String url;

    /**
     * 附件名称
     */
    @Column(length = 200)
    private String name;

    /**
     * MIME 类型
     */
    @Column(length = 100)
    private String mimeType;

    /**
     * 文件大小（字节）
     */
    private Long size;

    /**
     * 排序
     */
    private Integer sort;
}
