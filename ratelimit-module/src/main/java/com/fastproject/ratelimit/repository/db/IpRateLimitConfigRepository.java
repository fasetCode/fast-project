package com.fastproject.ratelimit.repository.db;

import com.fastproject.ratelimit.domain.IpRateLimitConfig;
import com.fastproject.ratelimit.enums.IpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * IP限流配置 Repository
 */
@Repository
public interface IpRateLimitConfigRepository extends JpaRepository<IpRateLimitConfig, Long>, JpaSpecificationExecutor<IpRateLimitConfig> {

    /**
     * 根据应用代码查询配置
     */
    IpRateLimitConfig findByAppCode(String appCode);

    /**
     * 根据应用代码和IP类型查询配置
     */
    IpRateLimitConfig findByAppCodeAndIpType(String appCode, IpType ipType);

    /**
     * 判断IP地址是否存在
     */
    boolean existsByIpAddress(String ipAddress);

    /**
     * 判断IP地址是否存在（排除指定ID）
     */
    boolean existsByIpAddressAndIdNot(String ipAddress, Long id);

    /**
     * 根据IP地址查询配置
     */
    IpRateLimitConfig findByIpAddress(String ipAddress);

    /**
     * 根据是否启用查询配置列表
     */
    List<IpRateLimitConfig> findByEnabled(Boolean enabled);

    /**
     * 根据应用代码获取所有启用的配置
     */
    List<IpRateLimitConfig> findByAppCodeAndEnabledTrue(String appCode);
}