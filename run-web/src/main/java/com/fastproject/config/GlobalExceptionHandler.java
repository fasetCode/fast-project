package com.fastproject.config;

import com.fastproject.exception.BusinessException;
import com.fastproject.utils.vo.ResultVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理未登录异常（401）
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResultVo<Object> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        log.warn("未登录访问: {} - {}", request.getRequestURI(), e.getMessage());
        return ResultVo.fail(HttpStatus.UNAUTHORIZED.value(), "请先登录");
    }

    /**
     * 处理未授权异常（403）
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResultVo<Object> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        log.warn("未授权访问: {} - {}", request.getRequestURI(), e.getMessage());
        return ResultVo.fail(HttpStatus.FORBIDDEN.value(), "没有权限访问该资源");
    }

    /**
     * 处理业务异常（401）
     * 用于验证码错误等业务校验失败场景
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVo<Object> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.warn("业务异常: {} - {}", request.getRequestURI(), e.getMessage());
        return ResultVo.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultVo<Object> handleException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResultVo.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常");
    }

}
