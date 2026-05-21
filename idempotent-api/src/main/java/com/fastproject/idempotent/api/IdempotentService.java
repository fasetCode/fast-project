package com.fastproject.idempotent.api;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 幂等性服务接口
 * 提供幂等校验的核心方法
 */
public interface IdempotentService {

    /**
     * 创建幂等记录
     *
     * @param event 幂等事件
     * @return 幂等ID
     */
    Long create(IdempotentDuplicateEvent event);
}
