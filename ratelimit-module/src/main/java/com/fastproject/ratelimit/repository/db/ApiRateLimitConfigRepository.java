package com.fastproject.ratelimit.repository.db;

import com.fastproject.ratelimit.domain.ApiRateLimitConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * API限流配置 Repository
 */
@Repository
public interface ApiRateLimitConfigRepository extends JpaRepository<ApiRateLimitConfig, Long>, JpaSpecificationExecutor<ApiRateLimitConfig> {

    /**
     * 判断应用代码、API路径和HTTP方法组合是否存在
     */
    boolean existsByAppCodeAndApiPathAndHttpMethod(String appCode, String apiPath, String httpMethod);

    /**
     * 判断应用代码、API路径和HTTP方法组合是否存在（排除指定ID）
     */
    boolean existsByAppCodeAndApiPathAndHttpMethodAndIdNot(String appCode, String apiPath, String httpMethod, Long id);

    /**
     * 根据应用代码、API路径和HTTP方法查询配置
     */
    ApiRateLimitConfig findByAppCodeAndApiPathAndHttpMethod(String appCode, String apiPath, String httpMethod);
}