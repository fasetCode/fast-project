package com.fastproject.logs.vo;

import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import lombok.Data;

/**
 * 操作日志更新
 */
@Data
public class OperationLogUpdate {

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 日志描述
     */
    private String description;

    /**
     * 日志类型
     */
    private LogType type;

    /**
     * 操作方法
     */
    private LogAction action;

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
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMsg;
}
