package com.fastproject.ratelimit.domain;

import com.fastproject.db.BaseEntity;
import com.fastproject.ratelimit.enums.LimitDimension;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * API接口限流配置
 */
@Table(name = "rate_limit_api_config")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update rate_limit_api_config set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class ApiRateLimitConfig extends BaseEntity {

    /**
     * 应用代码
     */
    @Column(name = "app_code", length = 32, nullable = false)
    private String appCode;

    /**
     * API路径
     */
    @Column(name = "api_path", length = 256, nullable = false)
    private String apiPath;

    /**
     * HTTP方法: GET, POST, PUT, DELETE等
     */
    @Column(name = "http_method", length = 16)
    private String httpMethod;

    /**
     * 最大请求次数
     */
    @Column(name = "max_requests", nullable = false)
    private Long maxRequests;

    /**
     * 时间窗口(秒)
     */
    @Column(name = "time_window", nullable = false)
    private Integer timeWindow;

    /**
     * 限流维度
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "limit_dimension", length = 32, nullable = false)
    private LimitDimension limitDimension;

    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
}