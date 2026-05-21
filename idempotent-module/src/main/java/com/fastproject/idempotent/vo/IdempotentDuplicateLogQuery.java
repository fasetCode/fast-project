package com.fastproject.idempotent.vo;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 幂等重复提交记录查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IdempotentDuplicateLogQuery extends PageQuery {

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 幂等键前缀
     */
    private String prefix;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 操作标题
     */
    private String title;

    /**
     * 首次请求时间开始
     */
    private LocalDateTime firstRequestTimeStart;

    /**
     * 首次请求时间结束
     */
    private LocalDateTime firstRequestTimeEnd;
}
