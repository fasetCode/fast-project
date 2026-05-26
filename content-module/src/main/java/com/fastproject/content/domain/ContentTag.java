package com.fastproject.content.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 内容标签表
 */
@Table(name = "content_tag")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update content_tag set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ContentTag extends BaseEntity {

    /**
     * 标签名称
     */
    @Column(length = 100)
    private String name;

    /**
     * 标签颜色（可选）
     */
    @Column(length = 50)
    private String color;

    /**
     * 图标（可选，iconfont class / svg / url 等，由业务自行约定）
     */
    @Column(length = 500)
    private String icon;

    /**
     * 图片（可选，用于“图片标签”展示）
     */
    @Column(length = 500)
    private String image;

    /**
     * 展示类型（1文字，2图标+文字，3图片）
     */
    private Integer displayType;

    /**
     * 状态（NORMAL/DISABLED 等，由业务自行约定）
     */
    private Integer status;
}
