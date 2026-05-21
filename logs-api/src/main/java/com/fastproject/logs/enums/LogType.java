package com.fastproject.logs.enums;

/**
 * 日志类型枚举
 */
public enum LogType {

    /**
     * 系统日志（系统级别的操作，如定时任务、系统启动等）
     */
    SYSTEM,

    /**
     * 业务日志（业务操作，如订单处理、数据查询等）
     */
    BUSINESS,

    /**
     * 登录日志（用户登录、登出等）
     */
    LOGIN,

    /**
     * 异常日志（异常信息记录）
     */
    EXCEPTION,

    /**
     * 操作日志（通用的增删改查操作）
     */
    OPERATION
}
