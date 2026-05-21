package com.fastproject.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redis 属性配置绑定类
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "fastproject.redis")
public class JedisProperties {

    private String host = "127.0.0.1";
    private int port = 6379;
    private String password;
    private int database = 0;
    private int timeout = 2000;
    private Pool pool = new Pool();

    @Setter
    @Getter
    public static class Pool {
        private int maxTotal = 200;
        private int maxIdle = 50;
        private int minIdle = 10;
    }
}