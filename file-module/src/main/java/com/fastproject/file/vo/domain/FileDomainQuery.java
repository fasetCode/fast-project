package com.fastproject.file.vo.domain;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件域名查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileDomainQuery extends PageQuery {

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
