package com.fastproject.system.tenant;

import com.fastproject.exception.BusinessException;
import com.fastproject.system.config.TenantProperties;
import com.fastproject.utils.TokenUtils;
import com.fastproject.vo.SecurityUserVo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 多租户访问控制辅助类
 */
@Component
@RequiredArgsConstructor
public class TenantAccessSupport {

    private static final Long SUPER_ADMIN_ID = 1L;

    private final TenantProperties tenantProperties;
    private final TokenUtils tokenUtils;

    public boolean isEnabled() {
        return tenantProperties.isEnabled();
    }

    public boolean isSuperAdmin() {
        SecurityUserVo user = tokenUtils.getUser();
        return user != null && Objects.equals(user.getUserId(), SUPER_ADMIN_ID);
    }

    public boolean shouldApplyTenantScope() {
        return isEnabled() && !isSuperAdmin();
    }

    public Long getCurrentTenantId() {
        SecurityUserVo user = tokenUtils.getUser();
        if (user == null) {
            return null;
        }
        if (user.getTenantId() != null) {
            return user.getTenantId();
        }
        if (user.getUser() != null) {
            return user.getUser().getTenantId();
        }
        return null;
    }

    public Long requireCurrentTenantId() {
        if (!shouldApplyTenantScope()) {
            return null;
        }
        Long tenantId = getCurrentTenantId();
        if (tenantId == null) {
            throw new BusinessException("当前用户未绑定租户");
        }
        return tenantId;
    }

    public void applyTenantPredicate(List<Predicate> predicates, Root<?> root, CriteriaBuilder cb) {
        Long tenantId = requireCurrentTenantId();
        if (tenantId != null) {
            predicates.add(cb.equal(root.get("tenantId"), tenantId));
        }
    }

    public void bindCurrentTenant(TenantScopedEntity entity) {
        Long tenantId = requireCurrentTenantId();
        if (tenantId != null) {
            entity.setTenantId(tenantId);
        }
    }

    public void checkTenantAccess(Long tenantId) {
        Long currentTenantId = requireCurrentTenantId();
        if (currentTenantId != null && !Objects.equals(currentTenantId, tenantId)) {
            throw new BusinessException("无权访问当前租户数据");
        }
    }

    public void checkEntityAccess(TenantScopedEntity entity, String message) {
        if (entity == null) {
            return;
        }
        Long currentTenantId = requireCurrentTenantId();
        if (currentTenantId != null && !Objects.equals(currentTenantId, entity.getTenantId())) {
            throw new BusinessException(message);
        }
    }

    public void checkTenantManagementAccess() {
        if (!isEnabled()) {
            throw new BusinessException("租户功能未开启，请在application.yml中配置 fastproject.tenant.enabled=true");
        }
        if (!isSuperAdmin()) {
            throw new BusinessException("仅超级管理员可管理租户");
        }
    }
}
