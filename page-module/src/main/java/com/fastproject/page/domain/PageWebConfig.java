package com.fastproject.page.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "page_web_config")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update page_web_config set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class PageWebConfig extends BaseEntity {
    /**
     * 请求地址
     */
    private String pathUrl;

    /**
     * 页面配置
     */
    @Column(columnDefinition = "TEXT")
    private String config;

    /**
     * 应用
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private PageApplication application;
}
