package com.fastproject.logs.annotation;

import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 日志描述
     */
    String value() default "";

    /**
     * 日志类型
     */
    LogType type() default LogType.BUSINESS;

    /**
     * 操作方法
     */
    LogAction action() default LogAction.OTHER;

    /**
     * 是否保存请求参数（默认保存，敏感接口可关闭）
     */
    boolean saveRequestParam() default true;

    /**
     * 是否保存响应结果（默认不保存，避免数据量过大）
     */
    boolean saveResponseData() default false;

    /**
     * 是否记录方法执行时间（默认记录）
     */
    boolean recordTimeCost() default true;
}