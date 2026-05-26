package com.fastproject.content.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 内容收藏记录表
 */
@Table(name = "content_favorite")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_favorite set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentFavorite extends BaseEntity {

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 用户ID
     */
    private Long userId;
}
