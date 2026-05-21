package com.fastproject.mall.vo.freighttemplate;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 运费模板查询 VO
 */
@Getter
@Setter
public class MallFreightTemplateQuery extends PageQuery {

    /**
     * 模板名称（模糊查询）
     */
    private String name;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 计费方式 1 按件 2 按重量(kg) 3 按体积(m³)
     */
    private Integer chargeType;

    /**
     * 状态 1 启用 2 停用
     */
    private Integer status;

    /**
     * 是否默认 0 否 1 是
     */
    private Integer isDefault;
}
