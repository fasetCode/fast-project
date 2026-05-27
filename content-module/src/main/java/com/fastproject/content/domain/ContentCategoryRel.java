package com.fastproject.content.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.fastproject.utils.id.IdGenerator;

/**
 * 内容分类关联表
 */
@Table(name = "content_category_rel")
@Getter
@Setter
@Entity
public class ContentCategoryRel {

    @Id
    private Long id;

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 分类ID（关联 content_category.id）
     */
    private Long categoryId;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = IdGenerator.nextId();
        }
    }
}
