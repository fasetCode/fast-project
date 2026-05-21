package com.fastproject.ratelimit.vo.record;

import com.fastproject.db.PageQuery;
import com.fastproject.ratelimit.enums.RateLimitType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 限流记录查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RateLimitRecordQuery extends PageQuery {

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 限流标识Key
     */
    private String limitKey;

    /**
     * 限流类型
     */
    private RateLimitType limitType;

    /**
     * 目标值(IP/用户ID等)
     */
    private String targetValue;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建时间-起始
     */
    private LocalDateTime createTimeBegin;

    /**
     * 创建时间-结束
     */
    private LocalDateTime createTimeEnd;

}