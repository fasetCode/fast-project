package com.fastproject.file.vo.domain;

import lombok.Data;

/**
 * 文件域名更新
 */
@Data
public class FileDomainUpdate {

    /**
     * 域名ID
     */
    private Long id;

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 域名
     */
    private String domain;

    /**
     * 状态
     */
    private Integer status;
}
