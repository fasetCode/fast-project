package com.fastproject.content.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

/**
 * 内容分类表
 */
@Table(name = "content_category")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_category set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentCategory extends BaseEntity {

    /**
     * 分类名称
     */
    @Column(length = 100)
    private String name;

    /**
     * 父级ID（0 或 null 表示顶级）
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态（NORMAL/DISABLED 等，由业务自行约定）
     */
    private Integer status;

    /**
     * 备注
     */
    @Column(length = 500)
    private String remark;

    @Transient
    private List<ContentCategory> children;
}
