package com.fastproject.idempotent.aspect;

import com.fastproject.exception.BusinessException;
import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.idempotent.api.IdempotentDuplicateEvent;
import com.fastproject.idempotent.api.IdempotentService;
import com.fastproject.idempotent.constants.IdempotentConstants;
import com.fastproject.jedis.JedisTemplate;
import com.fastproject.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 幂等性注解切面
 * 使用Redis实现分布式幂等控制
 */
@Slf4j
@Aspect
@Component
@Order(1) // 确保在事务注解之前执行
@RequiredArgsConstructor
public class IdempotentAspect {

    private final IdempotentService idempotentService;
    private final JedisTemplate jedisTemplate;
    private final TokenUtils tokenUtils;

    /**
     * 定义切点：所有带有@Idempotent注解的方法
     */
    @Pointcut("@annotation(com.fastproject.idempotent.annotation.Idempotent)")
    public void idempotentPointcut() {
    }

    /**
     * 环绕通知：处理幂等逻辑
     */
    @Around("idempotentPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Idempotent idempotent = method.getAnnotation(Idempotent.class);

        // 获取HTTP请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            log.warn("非Web请求环境，跳过幂等校验");
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();
        String requestId = getRequestId(request);

        // 没有requestId，抛出异常阻止请求
        if (requestId == null || requestId.isEmpty()) {
            log.warn("请求头中未找到X-Request-Id，拒绝请求");
            throw new BusinessException("系统繁忙 EX 99");
        }

        String prefix = idempotent.prefix();
        int expireTime = idempotent.expireTime();
        String message = idempotent.message();

        // 检查幂等状态
        String status = checkStatus(requestId, prefix, expireTime);
        Long userId = null;
        // userId
        if (tokenUtils.getUser()!=null) {
            userId = tokenUtils.getUser().getUserId();
        }
        if (IdempotentConstants.STATUS_PROCESSING.equals(status)) {
            // 请求正在处理中，发布重复提交事件
            log.warn("检测到重复请求：requestId={}, prefix={}", requestId, prefix);
            publishDuplicateEvent(request, idempotent, requestId, joinPoint.getArgs(),userId);
            throw new BusinessException(message);
        }

        if (IdempotentConstants.STATUS_COMPLETED.equals(status)) {
            // 请求已完成，发布重复提交事件并返回提示
            log.warn("请求已处理完成，请勿重复提交：requestId={}, prefix={}", requestId, prefix);
            publishDuplicateEvent(request, idempotent, requestId, joinPoint.getArgs(),userId);
            throw new BusinessException("请求已处理完成，请勿重复提交");
        }

        // 首次请求，设置处理中状态
        log.debug("首次请求，开始处理：requestId={}, prefix={}", requestId, prefix);

        try {
            // 执行目标方法
            Object result = joinPoint.proceed();

            // 标记请求已完成
             complete(requestId, prefix, expireTime);
            log.debug("请求处理完成：requestId={}, prefix={}", requestId, prefix);

            return result;
        } catch (Exception e) {
            // 处理异常，删除处理中标记，允许重试
            log.error("请求处理异常，清除幂等标记：requestId={}, prefix={}", requestId, prefix, e);
             remove(requestId, prefix);
            throw e;
        }
    }

    /**
     * 发布重复提交事件
     */
    @Async("taskExecutor")
    public void publishDuplicateEvent(HttpServletRequest request, Idempotent idempotent,
                                        String requestId, Object[] args, Long userId) {
        try {


            IdempotentDuplicateEvent event = IdempotentDuplicateEvent.builder()
                    .requestId(requestId)
                    .prefix(idempotent.prefix())
                    .requestUrl(request.getRequestURI())
                    .requestMethod(request.getMethod())
                    .requestParams(args != null && args.length > 0 ? args[0].toString() : null)
                    .userAgent(request.getHeader("User-Agent"))
                    .title(idempotent.title())
                    .userId(userId)
                    .message(idempotent.message())
                    .firstRequestTime(LocalDateTime.now())
                    .lastDuplicateTime(LocalDateTime.now())
                    .duplicateCount(1)
                    .build();


            idempotentService.create(event);
        } catch (Exception e) {
            log.error("记录重复提交日志失败", e);
        }
    }


    public String getRequestId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getHeader(IdempotentConstants.REQUEST_ID_HEADER);
    }

    public String checkStatus(String requestId, String prefix, int expireTime) {
//        String key = buildKey(prefix, requestId);
//        String value = jedisTemplate.get(key);
//
//        if (value == null) {
//            // 首次请求，设置处理中状态
//            String result = jedisTemplate.setex(key, IdempotentConstants.STATUS_PROCESSING, expireTime);
//            if ("OK".equals(result)) {
//                log.debug("设置幂等键处理中状态：key={}, expireTime={}", key, expireTime);
//                return null; // 返回null表示首次请求
//            } else {
//                // 设置失败，可能是并发情况，重新获取
//                value = jedisTemplate.get(key);
//                return value;
//            }
//        }
//
//        return value;
        String key = buildKey(prefix, requestId);

        // 原子化设置 PROCESSING 状态
        String result = jedisTemplate.setNxEx(key, IdempotentConstants.STATUS_PROCESSING, expireTime);

        if ("OK".equals(result)) {
            // 首次请求
            log.debug("设置幂等键处理中状态：key={}, expireTime={}", key, expireTime);
            return null;
        }

        // key 已存在 → 返回当前状态（PROCESSING 或 COMPLETED）
        return jedisTemplate.get(key);
    }


    public String buildKey(String prefix, String requestId) {
        return IdempotentConstants.KEY_PREFIX + prefix + IdempotentConstants.KEY_SEPARATOR + requestId;
    }

    public void complete(String requestId, String prefix, int expireTime) {
        String key = buildKey(prefix, requestId);
        // 更新状态为已完成，并延长过期时间
        String result = jedisTemplate.setex(key, IdempotentConstants.STATUS_COMPLETED, expireTime);
        log.debug("标记请求已完成：key={}, result={}", key, result);
    }

    public void remove(String requestId, String prefix) {
        String key = buildKey(prefix, requestId);
        Long result = jedisTemplate.del(key);
        log.debug("删除幂等键：key={}, result={}", key, result);
    }


}
