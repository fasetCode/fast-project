package com.fastproject.file.vo.config;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件配置查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileConfigQuery extends PageQuery {

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
}
