package com.fastproject.config;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Wrapper;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

/**
 * 为 SQL 耗时日志使用的 JDBC JDK 代理注册 native image hints。
 */
public class JpaSqlTimingRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.proxies().registerJdkProxy(Connection.class, Wrapper.class, AutoCloseable.class);
        hints.proxies().registerJdkProxy(Statement.class, Wrapper.class, AutoCloseable.class);
        hints.proxies().registerJdkProxy(PreparedStatement.class, Statement.class, Wrapper.class, AutoCloseable.class);
        hints.proxies().registerJdkProxy(CallableStatement.class, PreparedStatement.class, Statement.class, Wrapper.class,
                AutoCloseable.class);
    }
}
