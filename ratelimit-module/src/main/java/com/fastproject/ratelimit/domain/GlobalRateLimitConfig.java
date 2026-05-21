package com.fastproject.ratelimit.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 全局限流配置
 */
@Table(name = "rate_limit_global_config")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update rate_limit_global_config set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class GlobalRateLimitConfig extends BaseEntity {

    /**
     * 应用代码
     */
    @Column(name = "app_code")
    private String appCode;

    /**
     * 全局最大QPS
     */
    @Column(name = "max_requests", nullable = false)
    private Long maxRequests;

    /**
     * 时间窗口(秒)
     */
    @Column(name = "time_window", nullable = false)
    private Integer timeWindow;

    /**
     * 突发容量(令牌桶)
     */
    @Column(name = "burst_capacity")
    private Long burstCapacity;

    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
}