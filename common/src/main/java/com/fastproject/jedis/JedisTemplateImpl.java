package com.fastproject.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;


@Slf4j
public class JedisTemplateImpl extends AbstractJedisTemplate{

    private final SimpleJedisPool jedisPool;

    public JedisTemplateImpl(SimpleJedisPool jedisPool) {
        log.info("JedisTemplateImpl init");
        this.jedisPool = jedisPool;
    }


    @Override
    public void returnResource(Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public Jedis getJedis() {
        if (jedisPool == null) {
             throw new IllegalStateException("SimpleJedisPool 未初始化，请先调用 JedisUtil.setJedisPool() 注入连接池");
        }
        return jedisPool.getResource();
    }
}
