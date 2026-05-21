package com.fastproject.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jedis 操作模板接口
 * 提供 Redis 常用操作的统一封装
 */
public interface JedisTemplate {

    /**
     * 执行 Lua 脚本
     *
     * @param script  Lua 脚本内容
     * @param keys    键列表
     * @param args    参数列表
     * @return 脚本执行结果
     */
    Object eval(String script, List<String> keys, List<String> args);
    /**
     * 尝试获取分布式锁（使用 Redis set nx px）
     *
     * @param key          锁的键名
     * @param value        锁的值（通常使用 requestId）
     * @param seconds   锁的过期时间（毫秒）
     * @return 锁的值，成功获取锁时返回，否则返回 null
     */
    String setNxEx(String key, String value, long seconds);

    /**
     * 归还 Jedis 连接到连接池
     *
     * @param jedis Jedis 连接实例
     */
    void returnResource(Jedis jedis);

    /**
     * 从连接池获取 Jedis 连接
     *
     * @return Jedis 连接实例
     */
    Jedis getJedis();

    /**
     * 设置字符串类型的键值对
     *
     * @param key   键
     * @param value 值
     * @return 操作结果状态码
     */
    String set(String key, String value);

    /**
     * 设置字符串类型的键值对，并指定过期时间
     *
     * @param key     键
     * @param value   值
     * @param seconds 过期时间（秒）
     * @return 操作结果状态码
     */
    String setex(String key, String value, long seconds);

    /**
     * 获取字符串类型的键值
     *
     * @param key 键
     * @return 键对应的值，不存在时返回 null
     */
    String get(String key);

    /**
     * 删除指定的键
     *
     * @param keys 要删除的键数组
     * @return 成功删除的键数量
     */
    Long del(String... keys);

    /**
     * 判断键是否存在
     *
     * @param key 键
     * @return true 存在，false 不存在
     */
    boolean exists(String key);

    /**
     * 根据模式匹配查找所有键
     *
     * @param pattern 匹配模式，如 "user:*"
     * @return 匹配的键集合
     */
    Set<String> keys(String pattern);

    /**
     * 设置键的过期时间
     *
     * @param key     键
     * @param seconds 过期时间（秒）
     * @return 1 成功设置，0 键不存在或已设置过期时间
     */
    Long expire(String key, long seconds);

    /**
     * 获取键的剩余生存时间
     *
     * @param key 键
     * @return 剩余秒数，-1 表示未设置过期时间，-2 表示键不存在
     */
    Long ttl(String key);

    /**
     * 设置哈希表中字段的值
     *
     * @param key   哈希表键
     * @param field 字段名
     * @param value 字段值
     * @return 1 表示新建字段，0 表示更新已有字段
     */
    Long hset(String key, String field, String value);

    /**
     * 获取哈希表中字段的值
     *
     * @param key   哈希表键
     * @param field 字段名
     * @return 字段值，不存在时返回 null
     */
    String hget(String key, String field);

    /**
     * 获取哈希表中所有字段和值
     *
     * @param key 哈希表键
     * @return 包含所有字段和值的 Map
     */
    Map<String, String> hgetAll(String key);

    /**
     * 向列表左侧插入元素
     *
     * @param key    列表键
     * @param values 要插入的值数组
     * @return 插入后列表的长度
     */
    Long lpush(String key, String... values);

    /**
     * 获取列表指定范围的元素
     *
     * @param key   列表键
     * @param start 起始索引（0 开始）
     * @param end   结束索引（-1 表示到最后）
     * @return 元素列表
     */
    List<String> lrange(String key, long start, long end);

    /**
     * 向集合中添加成员
     *
     * @param key     集合键
     * @param members 要添加的成员数组
     * @return 成功添加的成员数量（不包括已存在的）
     */
    Long sadd(String key, String... members);

    /**
     * 获取集合中的所有成员
     *
     * @param key 集合键
     * @return 成员集合
     */
    Set<String> smembers(String key);

    /**
     * 尝试获取分布式锁（使用 Redis set nx px）
     *
     * @param lockKey    锁的键名
     * @param requestId  请求标识（用于释放锁时验证）
     * @param expireTime 锁的过期时间（毫秒）
     * @return true 获取成功，false 获取失败
     */
    boolean tryGetDistributedLock(String lockKey, String requestId, long expireTime);

    /**
     * 释放分布式锁（使用 Lua 脚本保证原子性）
     *
     * @param lockKey   锁的键名
     * @param requestId 请求标识（必须与获取时一致）
     * @return true 释放成功，false 释放失败
     */
    boolean releaseDistributedLock(String lockKey, String requestId);

}
