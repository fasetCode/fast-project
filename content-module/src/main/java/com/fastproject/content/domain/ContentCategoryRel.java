package com.fastproject.content.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 内容分类关联表
 */
@Table(name = "content_category_rel")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_category_rel set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentCategoryRel extends BaseEntity {

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 分类ID（关联 content_category.id）
     */
    private Long categoryId;
}

