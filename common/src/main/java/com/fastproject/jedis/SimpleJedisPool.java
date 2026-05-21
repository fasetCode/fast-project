package com.fastproject.jedis;

import redis.clients.jedis.Jedis;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 手写简单的 Jedis 连接池，解决 Native Image 下 JedisPool (commons-pool2) 的兼容性问题
 */
public class SimpleJedisPool {
    private static final Logger log = LoggerFactory.getLogger(SimpleJedisPool.class);

    private final LinkedBlockingQueue<Jedis> pool;
    private final int maxTotal;
    private final String host;
    private final int port;
    private final int timeout;
    private final String password;
    private final int database;
    private final AtomicInteger count = new AtomicInteger(0);

    public SimpleJedisPool(int maxTotal, String host, int port, int timeout, String password, int database) {
        this.maxTotal = maxTotal;
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.password = password;
        this.database = database;
        this.pool = new LinkedBlockingQueue<>(maxTotal);
    }

    public Jedis getResource() {
        Jedis jedis = pool.poll();
        if (jedis != null) {
            // 检查连接是否可用
            try {
                if (jedis.isConnected() && "PONG".equals(jedis.ping())) {
                    return jedis;
                }
            } catch (Exception e) {
                log.warn("Jedis connection in pool is invalid, closing and creating new one");
                count.decrementAndGet();
                try { jedis.close(); } catch (Exception ignore) {}
            }
        }

        // 如果池中没有可用连接且未达到最大限制，创建新连接
        if (count.get() < maxTotal) {
            if (count.incrementAndGet() <= maxTotal) {
                return createJedis();
            } else {
                count.decrementAndGet();
            }
        }

        // 如果达到最大限制，阻塞等待可用连接
        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Wait for Jedis connection interrupted", e);
        }
    }

    public void returnResource(Jedis jedis) {
        if (jedis == null) return;
        if (!pool.offer(jedis)) {
            // 如果池满了（理论上不应该，因为 count 控制了总量），直接关闭
            jedis.close();
            count.decrementAndGet();
        }
    }

    private Jedis createJedis() {

            Jedis jedis = new Jedis(host, port);
            jedis.auth(password);  // 密码
            jedis.select(database);       // 数据库索引

            return jedis;
    }

    public void close() {
        Jedis jedis;
        while ((jedis = pool.poll()) != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error("Error closing jedis connection during pool shutdown", e);
            }
        }
    }
}
