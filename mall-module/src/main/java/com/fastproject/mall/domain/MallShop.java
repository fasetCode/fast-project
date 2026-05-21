package com.fastproject.mall.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 商城 - 店铺
 */
@Table(name = "mall_shop")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_shop set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallShop extends BaseEntity {

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺编码
     */
    private String code;

    /**
     * 店铺 Logo（文件访问路径）
     */
    @Column(length = 500)
    private String logo;

    /**
     * 店铺横幅 / 封面图
     */
    @Column(length = 500)
    private String banner;

    /**
     * 店铺描述
     */
    @Column(length = 1000)
    private String description;

    /**
     * 店主用户ID
     */
    private Long ownerId;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 店铺地址
     */
    @Column(length = 500)
    private String address;

    /**
     * 状态 1 正常 2 冻结
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    @Column(length = 500)
    private String remark;

}
