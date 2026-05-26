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
 * 内容浏览记录表
 */
@Table(name = "content_view_log")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_view_log set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentViewLog extends BaseEntity {

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 用户ID（未登录可为空）
     */
    private Long userId;

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

    /**
     * 来源页面
     */
    @Column(length = 500)
    private String referer;
}
