package com.fastproject.ratelimit.vo.global;

import lombok.Data;

/**
 * 全局限流配置 VO
 */
@Data
public class GlobalRateLimitConfigVo {

    /**
     * 配置ID
     */
    private Long id;

    /**
     * 应用代码
     */
    private String appCode;

    /**
     * 全局最大QPS
     */
    private Long maxRequests;

    /**
     * 时间窗口(秒)
     */
    private Integer timeWindow;

    /**
     * 突发容量(令牌桶)
     */
    private Long burstCapacity;

    /**
     * 是否启用
     */
    private Boolean enabled;
}