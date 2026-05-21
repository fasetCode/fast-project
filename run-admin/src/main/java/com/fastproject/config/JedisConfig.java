package com.fastproject.config;

import com.fastproject.jedis.JedisTemplate;
import com.fastproject.jedis.JedisTemplateImpl;
import com.fastproject.jedis.SimpleJedisPool;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;


/**
 * Jedis 配置类，用于在 Spring 启动时初始化 SimpleJedisPool 并注入到 JedisUtil 工具类中
 */
@Configuration
@ImportRuntimeHints(JedisRuntimeHints.class)
public class JedisConfig {
    
    private static final Logger log = LoggerFactory.getLogger(JedisConfig.class);

    @Resource
    private JedisProperties jedisProperties;

    // 创建链接
    @Bean("simpleJedisPool")
    public SimpleJedisPool simpleJedisPool() {
        String password = jedisProperties.getPassword();
        if (password != null && password.trim().isEmpty()) {
            password = null;
        }
        SimpleJedisPool simpleJedisPool = new SimpleJedisPool(
                jedisProperties.getPool().getMaxTotal(),
                jedisProperties.getHost(),
                jedisProperties.getPort(),
                jedisProperties.getTimeout(),
                password,
                jedisProperties.getDatabase()
        );
        // 初始化注入到工具类
        log.info(">>> SimpleJedisPool 初始化成功并已注入到 JedisUtil. Redis 服务器: {}:{} 数据库: {}", 
                jedisProperties.getHost(), jedisProperties.getPort(), jedisProperties.getDatabase());
        return simpleJedisPool;
    }

    // 创建 JedisTemplate
    @Bean("cacheRedisTemplate")
    public JedisTemplate cacheRedisTemplate(@Qualifier("simpleJedisPool") SimpleJedisPool pool) {
        return new JedisTemplateImpl(pool);
    }


}