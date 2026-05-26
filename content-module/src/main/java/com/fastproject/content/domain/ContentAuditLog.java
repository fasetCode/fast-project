package com.fastproject.content.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

/**
 * 内容审核记录表
 */
@Table(name = "content_audit_log")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_audit_log set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentAuditLog extends BaseEntity {

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 审核状态（待审核/通过/驳回等，由业务自行约定）
     */
    private Integer auditStatus;

    /**
     * 审核原因/备注
     */
    @Column(columnDefinition = "TEXT")
    private String reason;

    /**
     * 审核人ID
     */
    private Long auditBy;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;
}
