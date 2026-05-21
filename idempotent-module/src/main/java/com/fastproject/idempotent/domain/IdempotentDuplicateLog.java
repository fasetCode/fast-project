package com.fastproject.idempotent.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

/**
 * 幂等重复提交记录
 * 用于记录重复提交的请求信息
 */
@Table(name = "sys_idempotent_duplicate_log")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_idempotent_duplicate_log set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class IdempotentDuplicateLog extends BaseEntity {

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
    @Column(name = "request_params", columnDefinition = "TEXT")
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
     * IP地址
     */
    private String ipAddress;

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