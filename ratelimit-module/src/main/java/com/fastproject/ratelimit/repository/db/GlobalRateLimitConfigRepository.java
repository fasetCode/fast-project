package com.fastproject.ratelimit.repository.db;

import com.fastproject.ratelimit.domain.GlobalRateLimitConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 全局限流配置 Repository
 */
@Repository
public interface GlobalRateLimitConfigRepository extends JpaRepository<GlobalRateLimitConfig, Long>, JpaSpecificationExecutor<GlobalRateLimitConfig> {

    /**
     * 查找启用的配置
     */
    GlobalRateLimitConfig findByEnabledTrue();

    GlobalRateLimitConfig findByAppCodeAndEnabledTrue(String appCode);
}
