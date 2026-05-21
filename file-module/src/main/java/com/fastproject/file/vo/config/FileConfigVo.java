package com.fastproject.file.vo.config;

import lombok.Data;

/**
 * 文件配置 VO
 */
@Data
public class FileConfigVo {

    /**
     * 配置ID
     */
    private Long id;

    /**
     * 存储路径
     */
    private String storagePath;

    /**
     * 访问域名
     */
    private String accessDomain;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 类型 本地 / 远程
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 远程url地址
     */
    private String remoteUrl;

    /**
     * 远程上传凭证
     */
    private String remoteToken;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 配置
     */
    private String config;
}
