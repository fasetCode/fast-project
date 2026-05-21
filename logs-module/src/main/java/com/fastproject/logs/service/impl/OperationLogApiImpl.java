package com.fastproject.logs.service.impl;

import com.fastproject.logs.api.OperationLogApi;
import com.fastproject.logs.domain.OperationLog;
import com.fastproject.logs.dto.OperationLogDTO;
import com.fastproject.logs.mapper.OperationLogMapper;
import com.fastproject.logs.repository.OperationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 操作日志 API 实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationLogApiImpl implements OperationLogApi {

    private final OperationLogRepository operationLogRepository;
    private final OperationLogMapper operationLogMapper;

    @Override
    public Long addLog(OperationLogDTO logDTO) {
        try {
            OperationLog operationLog = convertToEntity(logDTO);
            operationLogRepository.save(operationLog);
            log.debug("操作日志记录成功: {}", operationLog.getId());
            return operationLog.getId();
        } catch (Exception e) {
            log.error("操作日志记录失败: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    @Async("taskExecutor")
    public void addLogAsync(OperationLogDTO logDTO) {
        addLog(logDTO);
    }

    /**
     * DTO 转换为实体
     */
    private OperationLog convertToEntity(OperationLogDTO dto) {
        OperationLog entity = new OperationLog();
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setAction(dto.getAction());
        entity.setRequestParams(dto.getRequestParams());
        entity.setResponseData(dto.getResponseData());
        entity.setTimeCost(dto.getTimeCost());
        entity.setIp(dto.getIp());
        entity.setUrl(dto.getUrl());
        entity.setHttpMethod(dto.getHttpMethod());
        entity.setClassName(dto.getClassName());
        entity.setMethodName(dto.getMethodName());
        entity.setSuccess(dto.getSuccess());
        entity.setErrorMsg(dto.getErrorMsg());

        // 设置操作人信息
        if (dto.getOperatorId() != null) {
            entity.setCreateBy(dto.getOperatorId());
        }

        return entity;
    }
}
