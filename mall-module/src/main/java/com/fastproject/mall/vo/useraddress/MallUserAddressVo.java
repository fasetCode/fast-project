package com.fastproject.mall.vo.useraddress;

import lombok.Data;

/**
 * 用户收货地址 VO
 */
@Data
public class MallUserAddressVo {

    /**
     * 主键ID
     */
    private Long id;

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
