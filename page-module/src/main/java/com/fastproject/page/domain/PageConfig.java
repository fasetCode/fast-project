package com.fastproject.page.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "page_config")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update page_config set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class PageConfig  extends BaseEntity {

    /**
     * 标题
     */
    private String title;

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
     * 状态
     */
    private Integer status;

    /**
     * 版本
     */
    private String version;

    /**
     * 应用
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private PageApplication application;

}
