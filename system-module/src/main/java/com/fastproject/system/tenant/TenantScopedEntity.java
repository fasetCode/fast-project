package com.fastproject.system.tenant;

/**
 * 标记租户隔离实体
 */
public interface TenantScopedEntity {

    Long getTenantId();

    void setTenantId(Long tenantId);
}
