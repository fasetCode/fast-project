package com.fastproject.mall.vo.freighttemplate;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 运费模板 VO
 */
@Data
public class MallFreightTemplateVo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 所属店铺ID（平台模板为空）
     */
    private Long shopId;

    /**
     * 计费方式 1 按件 2 按重量(kg) 3 按体积(m³)
     */
    private Integer chargeType;

    /**
     * 包邮方式 0 不包邮 1 全国包邮 2 满金额包邮 3 满件数包邮 4 满重量包邮
     */
    private Integer freeType;

    /**
     * 包邮阈值-金额（freeType=2 使用）
     */
    private BigDecimal freeAmount;

    /**
     * 包邮阈值-件数（freeType=3 使用）
     */
    private Integer freeQuantity;

    /**
     * 包邮阈值-重量(kg)（freeType=4 使用）
     */
    private BigDecimal freeWeight;

    /**
     * 默认首件 / 首重 / 首体积
     */
    private BigDecimal defaultFirstUnit;

    /**
     * 默认首费
     */
    private BigDecimal defaultFirstFee;

    /**
     * 默认续件 / 续重 / 续体积
     */
    private BigDecimal defaultAdditionalUnit;

    /**
     * 默认续费
     */
    private BigDecimal defaultAdditionalFee;

    /**
     * 分地区规则（JSON 字符串）
     */
    private String regionRules;

    /**
     * 不配送地区（JSON 字符串，区编码列表）
     */
    private String excludeRegions;

    /**
     * 分地区包邮规则（JSON 字符串，可选）
     */
    private String freeRegionRules;

    /**
     * 是否默认 0 否 1 是（同店铺只能有一个默认）
     */
    private Integer isDefault;

    /**
     * 状态 1 启用 2 停用
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
