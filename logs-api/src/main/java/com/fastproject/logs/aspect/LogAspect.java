package com.fastproject.logs.aspect;

import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.api.OperationLogApi;
import com.fastproject.logs.dto.OperationLogDTO;
import com.fastproject.utils.TokenUtils;
import com.fastproject.vo.SecurityUserVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 日志记录切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final OperationLogApi operationLogApi;
    private final TokenUtils tokenUtils;

    /**
     * 定义切入点：拦截所有带有 @Log 注解的方法
     */
    @Pointcut("@annotation(com.fastproject.logs.annotation.Log)")
    public void logPointcut() {
    }

    /**
     * 环绕通知：记录正常执行的日志
     */
    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法上的注解
        Log logAnnotation = getLogAnnotation(joinPoint);
        if (logAnnotation == null) {
            return joinPoint.proceed();
        }

        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 获取请求信息
        HttpServletRequest request = getRequest();

        // 构建日志DTO
        OperationLogDTO logDTO = new OperationLogDTO();
        logDTO.setDescription(getDescription(logAnnotation, joinPoint));
        logDTO.setType(logAnnotation.type());
        logDTO.setAction(logAnnotation.action());

        // 设置请求参数
        if (logAnnotation.saveRequestParam()) {
            logDTO.setRequestParams(getRequestParams(joinPoint));
        }

        // 设置类名和方法名
        logDTO.setClassName(joinPoint.getTarget().getClass().getName());
        logDTO.setMethodName(joinPoint.getSignature().getName());

        // 设置请求信息
        if (request != null) {
            logDTO.setIp(getClientIp(request));
            logDTO.setUrl(request.getRequestURI());
            logDTO.setHttpMethod(request.getMethod());
        }

        // 设置操作用户
        SecurityUserVo currentUser = tokenUtils.getUser();
        if (currentUser != null) {
            logDTO.setOperatorId(currentUser.getUserId());
            logDTO.setOperatorName(currentUser.getUser() != null ? currentUser.getUser().getUsername() : null);
        }

        Object result = null;
        try {
            // 执行目标方法
            result = joinPoint.proceed();

            // 设置成功状态
            logDTO.setSuccess(true);

            // 设置响应结果
            if (logAnnotation.saveResponseData() && result != null) {
                logDTO.setResponseData(truncateString(result.toString(), 2000));
            }

        } catch (Throwable e) {
            // 设置失败状态
            logDTO.setSuccess(false);
            logDTO.setErrorMsg(getStackTrace(e));
            throw e;
        } finally {
            // 计算执行时间
            if (logAnnotation.recordTimeCost()) {
                logDTO.setTimeCost(System.currentTimeMillis() - startTime);
            }

            // 异步保存日志
            operationLogApi.addLogAsync(logDTO);
        }

        return result;
    }

    /**
     * 异常通知：记录异常日志（备用，环绕通知已处理）
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        // 异常处理已在环绕通知中完成，此处仅作备用
        log.debug("方法执行异常: {}", e.getMessage());
    }

    /**
     * 获取方法上的 @Log 注解
     */
    private Log getLogAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(Log.class);
    }

    /**
     * 获取日志描述
     */
    private String getDescription(Log logAnnotation, JoinPoint joinPoint) {
        if (!logAnnotation.value().isEmpty()) {
            return logAnnotation.value();
        }
        // 如果没有设置描述，使用方法名
        return joinPoint.getSignature().getName();
    }

    /**
     * 获取请求参数
     */
    private String getRequestParams(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return "{}";
        }

        StringBuilder params = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                params.append(", ");
            }
            try {
                Object arg = args[i];
                if (arg != null) {
                    // 过滤掉敏感信息和大型对象
                    String argStr = truncateString(arg.toString(), 500);
                    params.append("arg").append(i).append("=").append(argStr);
                }
            } catch (Exception e) {
                params.append("arg").append(i).append("=[无法序列化]");
            }
        }
        return params.toString();
    }

    /**
     * 获取客户端IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    /**
     * 获取当前请求
     */
    private HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                return attributes.getRequest();
            }
        } catch (Exception e) {
            log.debug("获取请求失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取异常堆栈信息
     */
    private String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return truncateString(sw.toString(), 2000);
    }

    /**
     * 截断字符串
     */
    private String truncateString(String str, int maxLength) {
        if (str == null) {
            return null;
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "... [截断]";
    }
}
