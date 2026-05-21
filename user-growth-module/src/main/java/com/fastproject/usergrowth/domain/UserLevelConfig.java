package com.fastproject.usergrowth.domain;


import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "user_level_config")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update user_level_config set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class UserLevelConfig extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 成长值积分
     */
    private Long growthValue;

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * 背景
     */
    private String background;

    /**
     * 颜色
     */
    private String color;

    /**
     * 描述
     */
    @Column(name = "description" ,columnDefinition="TEXT")
    private String description;
}
