package com.fastproject.idempotent.service.impl;

import com.fastproject.idempotent.api.IdempotentDuplicateEvent;
import com.fastproject.idempotent.api.IdempotentService;
import com.fastproject.idempotent.constants.IdempotentConstants;
import com.fastproject.idempotent.domain.IdempotentDuplicateLog;
import com.fastproject.idempotent.repository.db.IdempotentDuplicateLogRepository;
import com.fastproject.idempotent.service.IdempotentDuplicateLogService;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogCreate;
import com.fastproject.jedis.JedisTemplate;
import com.fastproject.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 幂等性服务实现类
 * 基于Redis实现分布式幂等控制
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IdempotentServiceImpl implements IdempotentService {
    private final IdempotentDuplicateLogRepository idempotentDuplicateLogRepository;




    @Override
    public Long create(IdempotentDuplicateEvent event) {
        try {
            IdempotentDuplicateLog create = new IdempotentDuplicateLog();
            create.setRequestId(event.getRequestId());
            create.setPrefix(event.getPrefix());
            create.setRequestUrl(event.getRequestUrl());
            create.setRequestMethod(event.getRequestMethod());
            create.setRequestParams(event.getRequestParams());
            create.setUserId(event.getUserId());
            create.setUsername(event.getUsername());
            create.setUserAgent(event.getUserAgent());
            create.setTitle(event.getTitle());
            create.setMessage(event.getMessage());
            create.setFirstRequestTime(event.getFirstRequestTime());
            create.setLastDuplicateTime(event.getLastDuplicateTime());
            create.setDuplicateCount(event.getDuplicateCount());

            // 获取IP地址
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                create.setIpAddress(IpUtils.getIp(request));
            }

            idempotentDuplicateLogRepository.save(create);
            log.debug("重复提交日志已记录：requestId={}", event.getRequestId());
        } catch (Exception e) {
            log.error("记录重复提交日志失败", e);
        }
        return null;
    }
}
