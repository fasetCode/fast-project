package com.fastproject.page.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "page_application")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update page_application set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class PageApplication extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 代码
     */
    private String code;

    /**
     * 状态
     */
    private Integer status;

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

}
