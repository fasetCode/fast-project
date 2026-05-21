package com.fastproject.system.vo.config;

import lombok.Data;

/**
 * 系统配置创建
 */
@Data
public class SysConfigCreate {

    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
