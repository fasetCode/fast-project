package com.fastproject.ratelimit.domain;

import com.fastproject.db.BaseEntity;
import com.fastproject.ratelimit.enums.IpType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * IP限流配置
 */
@Table(name = "rate_limit_ip_config")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update rate_limit_ip_config set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class IpRateLimitConfig extends BaseEntity {
    
    /**
     * 应用代码
     */
    @Column(name = "app_code")
    private String appCode;

    /**
     * IP地址或IP段
     */
    @Column(name = "ip_address")
    private String ipAddress;

    /**
     * IP类型: ALL-全部IP, SINGLE-单个IP, SEGMENT-IP段
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "ip_type")
    private IpType ipType;

    /**
     * 每秒最大请求次数(QPS)
     */
    @Column(name = "max_requests")
    private Long maxRequests;

    /**
     * 时间窗口(秒)
     */
    @Column(name = "time_window")
    private Integer timeWindow;

    /**
     * 突发容量
     */
    @Column(name = "burst_capacity")
    private Long burstCapacity;

    /**
     * 是否启用
     */
    @Column(name = "enabled")
    private Boolean enabled;

}