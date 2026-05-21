package com.fastproject.logs.domain;

import com.fastproject.db.BaseEntity;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 操作日志
 */
@Table(name = "sys_operation_log")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_operation_log set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class OperationLog extends BaseEntity {

    /**
     * 日志描述
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * 日志类型
     */
    @Enumerated(EnumType.STRING)
    private LogType type;

    /**
     * 操作方法
     */
    @Enumerated(EnumType.STRING)
    private LogAction action;

    /**
     * 请求参数
     */
    @Column(name = "request_params", columnDefinition = "TEXT")
    private String requestParams;

    /**
     * 响应结果
     */
    @Column(name = "response_data", columnDefinition = "TEXT")
    private String responseData;

    /**
     * 执行时间（毫秒）
     */
    private Long timeCost;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 请求方法（GET/POST/PUT/DELETE等）
     */
    private String httpMethod;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    @Column(name = "error_msg", columnDefinition = "TEXT")
    private String errorMsg;
}