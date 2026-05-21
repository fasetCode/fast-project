package com.fastproject.config;

import com.fastproject.system.service.SlowQueryLogService;
import com.fastproject.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * 为 JPA 经过的数据源统一打印 SQL 执行耗时。
 */
@Configuration
@ImportRuntimeHints(JpaSqlTimingRuntimeHints.class)
public class JpaSqlTimingConfig {

    private static final Logger log = LoggerFactory.getLogger(JpaSqlTimingConfig.class);

    @Bean
    public BeanPostProcessor sqlTimingDataSourceBeanPostProcessor(
            @Value("${fastproject.jpa.sql-timing.enabled:true}") boolean enabled,
            @Value("${fastproject.jpa.sql-timing.threshold-ms:0}") long thresholdMs) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (!enabled || !(bean instanceof DataSource dataSource) || bean instanceof TimingDataSource) {
                    return bean;
                }
                log.info("JPA SQL timing enabled on DataSource bean '{}' ({}) with threshold {} ms",
                        beanName, dataSource.getClass().getName(), thresholdMs);
                return new TimingDataSource(dataSource, thresholdMs);
            }
        };
    }

    private static final class TimingDataSource implements DataSource {

        private final DataSource delegate;
        private final long thresholdMs;

        private TimingDataSource(DataSource delegate, long thresholdMs) {
            this.delegate = delegate;
            this.thresholdMs = Math.max(thresholdMs, 0L);
        }

        @Override
        public Connection getConnection() throws SQLException {
            return wrapConnection(delegate.getConnection(), thresholdMs);
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return wrapConnection(delegate.getConnection(username, password), thresholdMs);
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            if (iface.isInstance(this)) {
                return iface.cast(this);
            }
            return delegate.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return iface.isInstance(this) || delegate.isWrapperFor(iface);
        }

        @Override
        public java.io.PrintWriter getLogWriter() throws SQLException {
            return delegate.getLogWriter();
        }

        @Override
        public void setLogWriter(java.io.PrintWriter out) throws SQLException {
            delegate.setLogWriter(out);
        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {
            delegate.setLoginTimeout(seconds);
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return delegate.getLoginTimeout();
        }

        @Override
        public java.util.logging.Logger getParentLogger() throws java.sql.SQLFeatureNotSupportedException {
            return delegate.getParentLogger();
        }
    }

    private static Connection wrapConnection(Connection connection, long thresholdMs) {
        InvocationHandler handler = new ConnectionInvocationHandler(connection, thresholdMs);
        return (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class, java.sql.Wrapper.class, AutoCloseable.class}, handler);
    }

    private static Statement wrapStatement(Statement statement, String sql, long thresholdMs, Class<?>[] interfaces) {
        InvocationHandler handler = new StatementInvocationHandler(statement, sql, thresholdMs);
        return (Statement) Proxy.newProxyInstance(Statement.class.getClassLoader(), interfaces, handler);
    }

    private static final class ConnectionInvocationHandler implements InvocationHandler {

        private final Connection delegate;
        private final long thresholdMs;

        private ConnectionInvocationHandler(Connection delegate, long thresholdMs) {
            this.delegate = delegate;
            this.thresholdMs = thresholdMs;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            try {
                Object result = method.invoke(delegate, args);
                if (!(result instanceof Statement statement)) {
                    return result;
                }
                if ("createStatement".equals(methodName)) {
                    return wrapStatement(statement, null, thresholdMs,
                            new Class<?>[]{Statement.class, java.sql.Wrapper.class, AutoCloseable.class});
                }
                if ("prepareStatement".equals(methodName)) {
                    return wrapStatement(statement, firstSqlArg(args), thresholdMs,
                            new Class<?>[]{PreparedStatement.class, Statement.class, java.sql.Wrapper.class,
                                    AutoCloseable.class});
                }
                if ("prepareCall".equals(methodName)) {
                    return wrapStatement(statement, firstSqlArg(args), thresholdMs,
                            new Class<?>[]{CallableStatement.class, PreparedStatement.class, Statement.class,
                                    java.sql.Wrapper.class, AutoCloseable.class});
                }
                return result;
            } catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        }
    }

    private static final class StatementInvocationHandler implements InvocationHandler {

        private static final Logger log = LoggerFactory.getLogger("com.fastproject.jpa.sql");
        private static final Set<String> EXECUTE_METHODS = Set.of(
                "execute",
                "executeQuery",
                "executeUpdate",
                "executeLargeUpdate",
                "executeBatch",
                "executeLargeBatch"
        );

        private final Statement delegate;
        private final String preparedSql;
        private final long thresholdMs;

        private StatementInvocationHandler(Statement delegate, String preparedSql, long thresholdMs) {
            this.delegate = delegate;
            this.preparedSql = preparedSql;
            this.thresholdMs = thresholdMs;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();

            if (!EXECUTE_METHODS.contains(methodName)) {
                try {

                    return method.invoke(delegate, args);
                } catch (InvocationTargetException ex) {
                    throw ex.getTargetException();
                }
            }

            String sql = resolveSql(args);
            long start = System.nanoTime();
            try {
                Object result = method.invoke(delegate, args);
                logIfNeeded(sql, methodName, start, false, null);
                return result;
            } catch (InvocationTargetException ex) {
                Throwable target = ex.getTargetException();
                logIfNeeded(sql, methodName, start, true, target);
                throw target;
            }
        }

        private String resolveSql(Object[] args) {
            String currentSql = firstSqlArg(args);
            return currentSql != null ? currentSql : preparedSql;
        }

        private void logIfNeeded(String sql, String operation, long start, boolean failed, Throwable throwable) {
            long elapsedNanos = System.nanoTime() - start;
            double elapsedMs = elapsedNanos / 1_000_000.0d;
            if (elapsedMs < thresholdMs) {
                return;
            }

            String elapsedText = String.format(Locale.ROOT, "%.3f", elapsedMs);
            String compactSql = compactSql(sql);
            if (failed) {
                log.warn("JPA SQL {} FAIL {} ms | {} | {}", operation, elapsedText, compactSql,
                        throwable == null ? "unknown error" : throwable.getMessage());
                return;
            }
            /**
             * 记录 SQL 执行耗时
             */
            log.info("JPA SQL {} {} ms | {}", operation, elapsedText, compactSql);

            // 异步记录慢查询到数据库 (阈值 200ms)
            // 注意：排除对 sys_slow_query_log 表本身的记录，防止循环记录
            if (elapsedMs >= 200 && (sql == null || !sql.toLowerCase().contains("sys_slow_query_log"))) {
                CompletableFuture.runAsync(() -> {
                    try {
                        SlowQueryLogService slowQueryLogService = SpringContextUtil.getBean(SlowQueryLogService.class);
                        if (slowQueryLogService != null) {
                            slowQueryLogService.log(sql, (long) elapsedMs);
                        }
                    } catch (Exception e) {
                        log.error("Failed to record slow query log", e);
                    }
                });
            }
        }
    }

    private static String firstSqlArg(Object[] args) {
        if (args == null || args.length == 0 || !(args[0] instanceof String sql)) {
            return null;
        }
        return sql;
    }

    private static String compactSql(String sql) {
        if (sql == null || sql.isBlank()) {
            return "<unknown-sql>";
        }
        return sql.replaceAll("\\s+", " ").trim();
    }
}
