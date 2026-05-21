package com.fastproject.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractJedisTemplate implements JedisTemplate{

    private static final Logger log = LoggerFactory.getLogger(AbstractJedisTemplate.class);


    // 新增原子 SET NX EX 方法
    public String setNxEx(String key, String value, long seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            // SET key value NX EX seconds
            SetParams params = SetParams.setParams().nx().ex(seconds);
            return jedis.set(key, value, params);
        } catch (Exception e) {
            log.error("Jedis setNxEx error: key={}, value={}, seconds={}", key, value, seconds, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    // 可选：执行 Lua 脚本
    public Object eval(String script, List<String> keys, List<String> args) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.eval(script, keys, args);
        } catch (Exception e) {
            log.error("Jedis eval error: keys={}, args={}", keys, args, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    // ======================= String (字符串操作) =======================

    public  String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.set(key, value);
        } catch (Exception e) {
            log.error("Jedis set error: key={}, value={}", key, value, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public  String setex(String key, String value, long seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error("Jedis setex error: key={}, value={}, seconds={}", key, value, seconds, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String sta = jedis.get(key);
            return sta;
        } catch (Exception e) {
            log.error("Jedis get error: key={}", key, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    // ======================= Key (通用操作) =======================

    public Long del(String... keys) {
        if (keys == null || keys.length == 0) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.del(keys);
        } catch (Exception e) {
            log.error("Jedis del error: keys={}", (Object) keys, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key);
        } catch (Exception e) {
            log.error("Jedis exists error: key={}", key, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.keys(pattern);
        } catch (Exception e) {
            log.error("Jedis keys error: pattern={}", pattern, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public Long expire(String key, long seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("Jedis expire error: key={}, seconds={}", key, seconds, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public Long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ttl(key);
        } catch (Exception e) {
            log.error("Jedis ttl error: key={}", key, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    // ======================= Hash (哈希操作) =======================

    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            log.error("Jedis hset error: key={}, field={}, value={}", key, field, value, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hget(key, field);
        } catch (Exception e) {
            log.error("Jedis hget error: key={}, field={}", key, field, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("Jedis hgetAll error: key={}", key, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    // ======================= List (列表操作) =======================

    public Long lpush(String key, String... values) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.lpush(key, values);
        } catch (Exception e) {
            log.error("Jedis lpush error: key={}, values={}", key, values, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error("Jedis lrange error: key={}, start={}, end={}", key, start, end, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    // ======================= Set (集合操作) =======================

    public Long sadd(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.sadd(key, members);
        } catch (Exception e) {
            log.error("Jedis sadd error: key={}, members={}", key, members, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.smembers(key);
        } catch (Exception e) {
            log.error("Jedis smembers error: key={}", key, e);
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    // ======================= 分布式锁 =======================

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    public boolean tryGetDistributedLock(String lockKey, String requestId, long expireTime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            SetParams params = SetParams.setParams().nx().px(expireTime);
            String result = jedis.set(lockKey, requestId, params);
            return LOCK_SUCCESS.equals(result);
        } catch (Exception e) {
            log.error("Jedis tryGetDistributedLock error: lockKey={}, requestId={}", lockKey, requestId, e);
            return false;
        } finally {
            returnResource(jedis);
        }
    }

    public boolean releaseDistributedLock(String lockKey, String requestId) {
        String script =
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "    return redis.call('del', KEYS[1]) " +
                        "else " +
                        "    return 0 " +
                        "end";
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            return RELEASE_SUCCESS.equals(result);
        } catch (Exception e) {
            log.error("Jedis releaseDistributedLock error: lockKey={}, requestId={}", lockKey, requestId, e);
            return false;
        } finally {
            returnResource(jedis);
        }
    }

}
