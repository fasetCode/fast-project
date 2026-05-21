package com.fastproject.page.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "page_component")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update page_component set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class PageComponent extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private PageType type;

    /**
     * 代码
     */
    private String code;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;
}
