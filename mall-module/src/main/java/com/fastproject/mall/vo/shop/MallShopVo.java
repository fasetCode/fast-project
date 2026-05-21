package com.fastproject.mall.vo.shop;

import lombok.Data;

/**
 * 商城店铺 VO
 */
@Data
public class MallShopVo {

    /**
     * 主键ID
     */
    private Long id;

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
    private String logo;

    /**
     * 店铺横幅 / 封面图
     */
    private String banner;

    /**
     * 店铺描述
     */
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
    private String remark;
}
