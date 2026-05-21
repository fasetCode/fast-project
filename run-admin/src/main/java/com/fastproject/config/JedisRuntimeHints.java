package com.fastproject.config;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

/**
 * 为 Native Image 注册 Jedis 缺少的资源文件，解决 JedisMetaInfo 加载失败导致的 NPE 问题
 */
public class JedisRuntimeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        // 注册 Jedis 内部尝试通过反射/ClassLoader 读取的 pom.properties 资源模式
        hints.resources().registerPattern("META-INF/maven/redis.clients/jedis/pom.properties");
    }
}
