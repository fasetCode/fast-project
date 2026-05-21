package com.fastproject.ratelimit.vo.api;

import com.fastproject.ratelimit.enums.LimitDimension;
import lombok.Data;

/**
 * API限流配置创建
 */
@Data
public class ApiRateLimitConfigCreate {

    /**
     * 应用代码
     */
    private String appCode;

    /**
     * API路径
     */
    private String apiPath;

    /**
     * HTTP方法: GET, POST, PUT, DELETE等
     */
    private String httpMethod;

    /**
     * 最大请求次数
     */
    private Long maxRequests;

    /**
     * 时间窗口(秒)
     */
    private Integer timeWindow;

    /**
     * 限流维度
     */
    private LimitDimension limitDimension;

    /**
     * 是否启用
     */
    private Boolean enabled;
}