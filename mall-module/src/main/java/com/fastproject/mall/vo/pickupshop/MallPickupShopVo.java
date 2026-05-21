package com.fastproject.mall.vo.pickupshop;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 自提门店 VO
 */
@Data
public class MallPickupShopVo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 所属店铺ID
     */
    private Long shopId;

    /**
     * 门店名称
     */
    private String name;

    /**
     * 门店编码
     */
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
     * 区县编码
     */
    private String districtCode;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 营业时间（文本，例：09:00-22:00）
     */
    private String openTime;

    /**
     * 自提须知（HTML / 富文本）
     */
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
    private String remark;
}
