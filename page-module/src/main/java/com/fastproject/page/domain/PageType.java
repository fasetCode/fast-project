package com.fastproject.page.domain;


import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "page_type")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update page_type set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class PageType extends BaseEntity {

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
}
