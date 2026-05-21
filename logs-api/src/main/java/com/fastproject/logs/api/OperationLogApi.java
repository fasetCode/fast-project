package com.fastproject.logs.api;

import com.fastproject.logs.dto.OperationLogDTO;

/**
 * 操作日志 API 接口
 * 供其他模块调用记录操作日志
 */
public interface OperationLogApi {

    /**
     * 添加操作日志
     *
     * @param logDTO 日志信息
     * @return 日志ID
     */
    Long addLog(OperationLogDTO logDTO);

    /**
     * 异步添加操作日志
     *
     * @param logDTO 日志信息
     */
    void addLogAsync(OperationLogDTO logDTO);
}