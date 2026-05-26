package com.fastproject.content.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 评论点赞记录表
 */
@Table(name = "content_comment_like")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_comment_like set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentCommentLike extends BaseEntity {

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 评论ID（关联 content_comment.id）
     */
    private Long commentId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 操作类型（点赞/踩等，由业务自行约定）
     */
    private Integer action;
}

