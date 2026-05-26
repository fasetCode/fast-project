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
 * 内容举报表
 */
@Table(name = "content_report")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_report set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentReport extends BaseEntity {

    /**
     * 目标类型（内容/评论等，由业务自行约定）
     */
    private Integer targetType;

    /**
     * 目标ID（目标类型对应的主键）
     */
    private Long targetId;

    /**
     * 举报人用户ID
     */
    private Long reportBy;

    /**
     * 举报原因类型（垃圾广告/辱骂/涉政等，由业务自行约定）
     */
    private Integer reasonType;

    /**
     * 举报原因描述
     */
    @Column(columnDefinition = "TEXT")
    private String reason;

    /**
     * 处理状态（待处理/已处理/已驳回等，由业务自行约定）
     */
    private Integer status;

    /**
     * 处理人ID
     */
    private Long handleBy;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * IP 地址
     */
    @Column(length = 50)
    private String ip;

    /**
     * User-Agent
     */
    @Column(length = 500)
    private String userAgent;
}

