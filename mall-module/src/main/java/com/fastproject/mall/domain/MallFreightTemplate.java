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
 * 商城 - 运费模板
 */
@Table(name = "mall_freight_template")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_freight_template set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallFreightTemplate extends BaseEntity {

    /**
     * 模板名称
     */
    @Column(length = 200)
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
    @Column(precision = 12, scale = 2)
    private BigDecimal freeAmount;

    /**
     * 包邮阈值-件数（freeType=3 使用）
     */
    private Integer freeQuantity;

    /**
     * 包邮阈值-重量(kg)（freeType=4 使用）
     */
    @Column(precision = 10, scale = 3)
    private BigDecimal freeWeight;

    /**
     * 默认首件 / 首重 / 首体积
     */
    @Column(precision = 10, scale = 3)
    private BigDecimal defaultFirstUnit;

    /**
     * 默认首费
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal defaultFirstFee;

    /**
     * 默认续件 / 续重 / 续体积
     */
    @Column(precision = 10, scale = 3)
    private BigDecimal defaultAdditionalUnit;

    /**
     * 默认续费
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal defaultAdditionalFee;

    /**
     * 分地区规则（JSON 字符串）
     * 例：[{"areaCodes":["110000","120000"],"firstUnit":1,"firstFee":8,"addUnit":1,"addFee":2}]
     */
    @Column(columnDefinition = "TEXT")
    private String regionRules;

    /**
     * 不配送地区（JSON 字符串，区编码列表）
     */
    @Column(columnDefinition = "TEXT")
    private String excludeRegions;

    /**
     * 分地区包邮规则（JSON 字符串，可选）
     */
    @Column(columnDefinition = "TEXT")
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
    @Column(length = 500)
    private String remark;
}
