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
 * 商城 - 用户收货地址
 */
@Table(name = "mall_user_address")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_user_address set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallUserAddress extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 省
     */
    private String province;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 市
     */
    private String city;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区/县
     */
    private String district;

    /**
     * 区/县编码
     */
    private String districtCode;

    /**
     * 详细地址
     */
    @Column(length = 500)
    private String detailAddress;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 是否默认 0 否 1 是
     */
    private Integer isDefault;

    /**
     * 标签（如：家、公司、学校）
     */
    private String tag;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

}
