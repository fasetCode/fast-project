package com.fastproject.system.repository.db;

import com.fastproject.system.domain.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 系统配置 Repository
 */
@Repository
public interface SysConfigRepository extends JpaRepository<SysConfig, Long>, JpaSpecificationExecutor<SysConfig> {

    /**
     * 判断配置键是否存在
     */
    boolean existsByConfigKey(String configKey);

    /**
     * 判断配置键是否存在（排除指定ID）
     */
    boolean existsByConfigKeyAndIdNot(String configKey, Long id);

    /**
     * 根据配置键查询配置
     */
    SysConfig findByConfigKey(String configKey);
}
