package com.fastproject.idempotent.enums;

/**
 * 幂等键生成策略枚举
 * 定义不同场景下的幂等键生成方式
 */
public enum IdempotentStrategy {

    /**
     * 默认策略：用户ID + 请求路径 + 请求参数 MD5
     */
    DEFAULT,

    /**
     * 仅基于请求参数 MD5
     * 适用于不考虑用户的全局幂等
     */
    PARAMS_MD5,

    /**
     * 仅基于 Token
     * 适用于基于用户会话的幂等
     */
    TOKEN,

    /**
     * 自定义 SpEL 表达式
     * 使用注解中指定的 key() 表达式
     */
    CUSTOM

}
