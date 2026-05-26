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
 * 评论表
 */
@Table(name = "content_comment")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_comment set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentComment extends BaseEntity {

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 父评论ID（一级评论可为 0 或 null，由业务约定）
     */
    private Long parentId;

    /**
     * 根评论ID（同一楼中最顶层评论ID，便于聚合查询）
     */
    private Long rootId;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 昵称（冗余字段，便于展示）
     */
    @Column(length = 100)
    private String nickname;

    /**
     * 头像（冗余字段，便于展示）
     */
    @Column(length = 500)
    private String avatar;

    /**
     * 评论内容
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 状态（NORMAL/DISABLED/审核中等，由业务自行约定）
     */
    private Integer status;

    /**
     * 点赞数
     */
    private Long likeCount;

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
     * 回复目标用户ID
     */
    private Long replyToUserId;

    /**
     * 回复目标评论ID
     */
    private Long replyToCommentId;

}
