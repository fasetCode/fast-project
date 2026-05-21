package com.fastproject.system.vo.config;

import com.fastproject.db.BaseEntity;
import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigQuery extends PageQuery {


    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 状态
     */
    private Integer status;
}
