package com.fastproject.system.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 租户功能配置
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "fastproject.tenant")
public class TenantProperties {

    /**
     * 是否启用租户功能，默认关闭
     */
    private boolean enabled = false;
}
