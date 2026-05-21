package com.fastproject.ratelimit.vo.ip;

import com.fastproject.db.PageQuery;
import com.fastproject.ratelimit.enums.IpType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IP限流配置查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IpRateLimitConfigQuery extends PageQuery {

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
     * 是否启用
     */
    private Boolean enabled;
}