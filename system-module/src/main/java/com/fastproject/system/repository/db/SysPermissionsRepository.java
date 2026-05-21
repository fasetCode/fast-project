package com.fastproject.system.repository.db;

import com.fastproject.system.domain.SysPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 权限 Repository
 */
@Repository
public interface SysPermissionsRepository extends JpaRepository<SysPermissions, Long>, JpaSpecificationExecutor<SysPermissions> {
    /**
     * 判断权限代码是否存在
     */
    boolean existsByCode(String code);

    /**
     * 判断权限代码是否存在（排除指定ID）
     */
    boolean existsByCodeAndIdNot(String code, Long id);
}
