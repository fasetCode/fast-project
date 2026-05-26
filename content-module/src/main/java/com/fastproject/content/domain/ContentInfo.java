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
 * 内容主表
 */
@Table(name = "content_info")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_info set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentInfo extends BaseEntity {

    /**
     * 内容类型（文章/帖子/知识库等，由业务自行约定）
     */
    private Integer type;

    /**
     * 标题
     */
    @Column(length = 200)
    private String title;

    /**
     * 摘要
     */
    @Column(length = 500)
    private String summary;

    /**
     * 封面图
     */
    @Column(length = 500)
    private String cover;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 标签（可用逗号分隔等方式存储）
     */
    @Column(length = 1000)
    private String tags;

    /**
     * 作者用户ID
     */
    private Long authorId;

    /**
     * 作者名称（冗余字段，便于展示）
     */
    @Column(length = 100)
    private String authorName;

    /**
     * 来源（原创/转载/翻译等）
     */
    @Column(length = 100)
    private String source;

    /**
     * 来源链接
     */
    @Column(length = 500)
    private String sourceUrl;

    /**
     * 是否置顶
     */
    private Boolean topFlag;

    /**
     * 是否推荐
     */
    private Boolean recommendFlag;

    /**
     * 是否允许评论
     */
    private Boolean allowComment;

    /**
     * 状态（NORMAL/DISABLED 等，由业务自行约定）
     */
    private Integer status;

    /**
     * 发布状态（草稿/已发布/下线等，由业务自行约定）
     */
    private Integer publishStatus;

    /**
     * 审核状态（未提交/待审核/通过/驳回等，由业务自行约定）
     */
    private Integer auditStatus;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 审核人ID
     */
    private Long auditBy;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 最后评论时间
     */
    private LocalDateTime lastCommentTime;

    /**
     * 浏览数
     */
    private Long viewCount;

    /**
     * 点赞数
     */
    private Long likeCount;

    /**
     * 收藏数
     */
    private Long favoriteCount;

    /**
     * 评论数
     */
    private Long commentCount;

}
