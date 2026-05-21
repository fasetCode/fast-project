package com.fastproject.system.repository.db;

import com.fastproject.system.domain.SysPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 岗位 Repository
 */
@Repository
public interface SysPostRepository extends JpaRepository<SysPost, Long>, JpaSpecificationExecutor<SysPost> {

    /**
     * 判断岗位编码是否存在
     */
    boolean existsByCode(String code);

    boolean existsByCodeAndTenantId(String code, Long tenantId);

    /**
     * 判断岗位编码是否存在（排除指定ID）
     */
    boolean existsByCodeAndIdNot(String code, Long id);

    boolean existsByCodeAndIdNotAndTenantId(String code, Long id, Long tenantId);
}
