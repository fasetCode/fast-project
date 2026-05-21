package com.fastproject.ratelimit.vo.global;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局限流配置查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GlobalRateLimitConfigQuery extends PageQuery {

    /**
     * 应用代码
     */
    private String appCode;

    /**
     * 是否启用
     */
    private Boolean enabled;
}