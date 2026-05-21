package com.fastproject.ratelimit.vo.ip;

import com.fastproject.ratelimit.enums.IpType;
import lombok.Data;

/**
 * IP限流配置创建
 */
@Data
public class IpRateLimitConfigCreate {

    /**
     * 应用代码
     */
    private String appCode;

    /**
     * IP地址或IP段
     */
    private String ipAddress;

    /**
     * IP类型: ALL-全部IP, SINGLE-单个IP, SEGMENT-IP段
     */
    private IpType ipType;

    /**
     * 最大请求次数
     */
    private Long maxRequests;

    /**
     * 时间窗口(秒)
     */
    private Integer timeWindow;

    /**
     * 突发容量
     */
    private Long burstCapacity;

    /**
     * 是否启用
     */
    private Boolean enabled;
}