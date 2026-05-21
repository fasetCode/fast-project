package com.fastproject.mall.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/**
 * 商城 - 自提门店
 */
@Table(name = "mall_pickup_shop")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_pickup_shop set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallPickupShop extends BaseEntity {

    /**
     * 所属店铺ID（多商户场景；为空表示平台直营自提点）
     */
    private Long shopId;

    /**
     * 门店名称
     */
    @Column(length = 200)
    private String name;

    /**
     * 门店编码
     */
    @Column(length = 64)
    private String code;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 省 / 省编码
     */
    private String province;
    private String provinceCode;

    /**
     * 市 / 市编码
     */
    private String city;
    private String cityCode;

    /**
     * 区/县 / 区县编码
     */
    private String district;
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
     * 经度
     */
    @Column(precision = 10, scale = 6)
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @Column(precision = 10, scale = 6)
    private BigDecimal latitude;

    /**
     * 营业时间（文本，例：09:00-22:00）
     */
    @Column(length = 100)
    private String openTime;

    /**
     * 自提须知（HTML / 富文本）
     */
    @Column(columnDefinition = "TEXT")
    private String pickupNotice;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 1 启用 2 停用
     */
    private Integer status;

    /**
     * 备注
     */
    @Column(length = 500)
    private String remark;
}
