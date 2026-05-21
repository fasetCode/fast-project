package com.fastproject.idempotent.annotation;

import java.lang.annotation.*;

/**
 * 幂等性注解
 * 用于标记 Controller 方法，防止重复提交
 * 基于前端生成的 request-id 请求头实现
 *
 * 使用示例：
 * <pre>
 * @Idempotent(expireTime = 10, title = "创建订单")
 * @PostMapping("/order")
 * public ResultVo createOrder(@RequestBody OrderDto dto) {
 *     // 业务逻辑
 * }
 * </pre>
 *
 * 前端需在请求头中添加: X-Request-Id: uuid
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    /**
     * 幂等键前缀
     * 最终存储到 Redis 的键格式：idempotent:{prefix}:{requestId}
     *
     * @return 键前缀
     */
    String prefix() default "default";

    /**
     * 幂等键过期时间（秒）
     * 默认 60 秒，即在指定时间内同一请求视为重复请求
     *
     * @return 过期时间（秒）
     */
    int expireTime() default 60;

    /**
     * 重复请求时的提示消息
     *
     * @return 提示消息
     */
    String message() default "请求正在处理中，请勿重复提交";

    /**
     * 幂等操作标题
     * 用于描述该幂等操作的业务含义
     *
     * @return 操作标题
     */
    String title() default "";

}