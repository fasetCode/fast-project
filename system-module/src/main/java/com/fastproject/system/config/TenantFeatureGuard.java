package com.fastproject.system.config;

import com.fastproject.system.tenant.TenantAccessSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 租户功能开关守卫
 */
@Component
@RequiredArgsConstructor
public class TenantFeatureGuard {

    private final TenantAccessSupport tenantAccessSupport;

    public void checkEnabled() {
        tenantAccessSupport.checkTenantManagementAccess();
    }
}
