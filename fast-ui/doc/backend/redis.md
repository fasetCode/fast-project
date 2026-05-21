# Redis 与缓存

## 组件

- Jedis：Redis 客户端
- Caffeine：本地缓存

## 排查思路

启动时报 Redis 连接错误时优先确认：

- Redis 服务可用
- `application.yml` 的 host/port/password 是否正确
- 环境 profile 是否切换正确（`application-dev*.yml`）
