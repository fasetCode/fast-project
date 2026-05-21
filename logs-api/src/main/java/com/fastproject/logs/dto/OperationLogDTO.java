package com.fastproject.logs.dto;

import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import lombok.Data;

/**
 * 操作日志 DTO
 */
@Data
public class OperationLogDTO {

    /**
     * 日志描述
     */
    private String description;

    /**
     * 日志类型
     */
    private LogType type = LogType.BUSINESS;

    /**
     * 操作方法
     */
    private LogAction action = LogAction.OTHER;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应结果
     */
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
    private Boolean success = true;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;
}
