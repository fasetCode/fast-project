package com.fastproject.ratelimit.vo.api;

import com.fastproject.db.PageQuery;
import com.fastproject.ratelimit.enums.LimitDimension;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * API限流配置查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiRateLimitConfigQuery extends PageQuery {

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
     * 限流维度
     */
    private LimitDimension limitDimension;

    /**
     * 是否启用
     */
    private Boolean enabled;
}