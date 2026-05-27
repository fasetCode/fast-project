package com.fastproject.content.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.fastproject.utils.id.IdGenerator;

/**
 * 内容标签关联表
 */
@Table(name = "content_tag_rel")
@Getter
@Setter
@Entity
public class ContentTagRel {

    @Id
    private Long id;

    /**
     * 内容ID（关联 content_info.id）
     */
    private Long contentId;

    /**
     * 标签ID（关联 content_tag.id）
     */
    private Long tagId;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = IdGenerator.nextId();
        }
    }
}
