package com.fastproject.ratelimit.domain;

import com.fastproject.db.BaseEntity;
import com.fastproject.ratelimit.enums.RateLimitType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

/**
 * 限流记录
 */
@Table(name = "rate_limit_record")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update rate_limit_record set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class RateLimitRecord extends BaseEntity {

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
    @Enumerated(EnumType.STRING)
    private RateLimitType limitType;

    /**
     * 目标值(IP/用户ID等)
     */
    private String targetValue;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求地址
     */
    @Column(length = 1000)
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
     * 请求头
     */
    @Column(columnDefinition = "text")
    private String headers;

    /**
     * 查询参数
     */
    @Column(columnDefinition = "text")
    private String queryParams;

    /**
     * 触发限制的原因
     */
    @Column(length = 500)
    private String limitReason;

}