package com.fastproject.idempotent.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 幂等重复提交事件
 * 当检测到重复请求时发布此事件
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdempotentDuplicateEvent {

    /**
     * 请求ID（前端生成的唯一标识）
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
     * 请求方法（GET/POST/PUT/DELETE）
     */
    private String requestMethod;

    /**
     * 请求参数（JSON）
     */
    private String requestParams;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * User-Agent
     */
    private String userAgent;

    /**
     * 操作标题
     */
    private String title;

    /**
     * 首次请求时间
     */
    private LocalDateTime firstRequestTime;

    /**
     * 最后重复提交时间
     */
    private LocalDateTime lastDuplicateTime;

    /**
     * 重复提交次数
     */
    private Integer duplicateCount;

    /**
     * 提示消息
     */
    private String message;
}
