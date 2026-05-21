package com.fastproject.system.repository.db;

import com.fastproject.system.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 角色 Repository
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Long>, JpaSpecificationExecutor<SysRole> {

    /**
     * 判断角色名称是否存在
     */
    boolean existsByTitle(String name);

    boolean existsByTitleAndTenantId(String name, Long tenantId);

    /**
     * 判断角色编码是否存在
     */
    boolean existsByCode(String code);

    boolean existsByCodeAndTenantId(String code, Long tenantId);

    /**
     * 判断角色名称是否存在（排除指定ID）
     */
    boolean existsByTitleAndIdNot(String name, Long id);

    boolean existsByTitleAndIdNotAndTenantId(String name, Long id, Long tenantId);

    /**
     * 判断角色编码是否存在（排除指定ID）
     */
    boolean existsByCodeAndIdNot(String code, Long id);

    boolean existsByCodeAndIdNotAndTenantId(String code, Long id, Long tenantId);
}
