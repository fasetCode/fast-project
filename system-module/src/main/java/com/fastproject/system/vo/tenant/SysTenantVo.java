package com.fastproject.system.vo.tenant;

import lombok.Data;

/**
 * 租户 VO
 */
@Data
public class SysTenantVo {

    /**
     * 租户ID
     */
    private Long id;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 租户管理员ID
     */
    private Long adminId;

    /**
     * 租户状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 租户域名
     */
    private String domain;

    /**
     * 过期时间
     */
    private String expireTime;

    /**
     * 账号额度
     */
    private Integer accountCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createTime;
}
