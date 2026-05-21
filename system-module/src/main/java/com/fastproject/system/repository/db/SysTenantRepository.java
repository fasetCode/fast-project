package com.fastproject.system.repository.db;

import com.fastproject.system.domain.SysTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 租户仓库接口
 */
@Repository
public interface SysTenantRepository extends JpaRepository<SysTenant, Long>, JpaSpecificationExecutor<SysTenant> {
    /**
     * 判断租户名称是否存在
     */
    boolean existsByName(String name);

    /**
     * 判断租户域名是否存在
     */
    boolean existsByDomain(String domain);

    /**
     * 判断租户名称是否存在（排除指定ID）
     */
    boolean existsByNameAndIdNot(String name, Long id);

    /**
     * 判断租户域名是否存在（排除指定ID）
     */
    boolean existsByDomainAndIdNot(String domain, Long id);
}
