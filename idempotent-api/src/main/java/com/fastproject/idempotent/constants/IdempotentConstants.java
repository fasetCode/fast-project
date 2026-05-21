package com.fastproject.idempotent.constants;

/**
 * 幂等性模块常量类
 */
public class IdempotentConstants {

    /**
     * 请求头中的幂等键名称
     */
    public static final String REQUEST_ID_HEADER = "X-Request-Id";

    /**
     * Redis 键前缀
     */
    public static final String KEY_PREFIX = "idempotent:";

    /**
     * 默认过期时间（秒）
     */
    public static final int DEFAULT_EXPIRE_TIME = 60;

    /**
     * 默认幂等键分隔符
     */
    public static final String KEY_SEPARATOR = ":";

    /**
     * 请求处理中状态
     */
    public static final String STATUS_PROCESSING = "PROCESSING";

    /**
     * 请求已完成状态
     */
    public static final String STATUS_COMPLETED = "COMPLETED";

    private IdempotentConstants() {
        // 私有构造方法，防止实例化
    }

}