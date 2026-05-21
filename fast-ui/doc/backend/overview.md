# 后端总览

## 技术栈

- Gradle 多模块
- Java 25
- Spring Boot 4.0.3
- Spring Data JPA + Hibernate 7
- Spring Security
- PostgreSQL / H2
- Jedis + Caffeine
- MapStruct

## 模块类型

### 运行时应用（可直接启动）

- `run-admin`：后台管理 REST API
- `run-web`：Web 站点/对外接口相关（按实际业务演进）
- `run-customer-plugin`：客服插件后端
- `server-work`：监控采集
- `websocket`：独立 WebSocket 服务

### 领域能力模块（被运行时应用依赖）

- 基础：`common`、`utils`
- 系统：`system-module`
- 文件：`file-module` / `file-api`
- 日志：`logs-module` / `logs-api`
- 幂等：`idempotent-module` / `idempotent-api`
- 限流：`ratelimit-module` / `ratelimit-api`
- 消息：`message-module` / `message-api`
- 用户成长：`user-growth-module` / `user-growth-api`
- 支付：`pay-module` / `pay-api`
- 页面搭建：`page-module` / `page-api`
- 商城：`mall-module` / `mall-api`

## 分层模式

多数领域模块遵循：

`domain -> repository -> mapper -> service -> (run 模块的 controller)`
