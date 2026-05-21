package com.fastproject.system.repository.db;

import com.fastproject.system.domain.SysDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 部门 Repository
 */
@Repository
public interface SysDepartmentRepository extends JpaRepository<SysDepartment, Long>, JpaSpecificationExecutor<SysDepartment> {

    /**
     * 判断部门名称是否存在
     */
    boolean existsByName(String name);

    boolean existsByNameAndTenantId(String name, Long tenantId);

    /**
     * 判断部门名称是否存在（排除指定ID）
     */
    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByNameAndIdNotAndTenantId(String name, Long id, Long tenantId);

    /**
     * 判断是否存在子部门
     */
    boolean existsByParentId(Long parentId);

    boolean existsByParentIdAndTenantId(Long parentId, Long tenantId);
}
