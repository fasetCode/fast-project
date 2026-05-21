package com.fastproject.system.vo.tenant;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户查询请求 DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysTenantQuery extends PageQuery {

    /**
     * 租户名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 租户状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 租户域名
     */
    private String domain;
}
