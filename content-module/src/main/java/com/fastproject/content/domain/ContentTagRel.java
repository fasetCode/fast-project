package com.fastproject.content.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 内容标签关联表
 */
@Table(name = "content_tag_rel")
@Getter
@Setter
@Entity
public class ContentTagRel {

    /**
     *  主键
     */
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
}
