# 快速开始

## 环境准备

- JDK 25
- Node.js（建议 LTS）
- pnpm（仓库前端工作区使用 `pnpm@10.14.0`）
- PostgreSQL（`run-admin` 默认使用）
- Redis（部分模块依赖 Jedis）

## 后端启动

在仓库根目录执行：

```bash
./gradlew :run-admin:bootRun
```

Windows：

```powershell
.\gradlew.bat :run-admin:bootRun
```

常用入口：

- 后台管理后端：`run-admin`
- 客服插件后端：`run-customer-plugin`
- 监控采集：`server-work`
- WebSocket：`websocket`

## 前端启动

在 `fast-ui` 目录执行：

```bash
pnpm install
pnpm admin-vue:dev
```

客服插件端：

```bash
pnpm --filter customer-service-vue dev
```

## 常见问题

### 数据库与表结构

`run-admin` 中的 `spring.jpa.hibernate.ddl-auto=update` 可能会自动更新表结构；修改实体字段前建议先评估迁移策略。

### Redis

如果启动时报 Redis 连接错误，先确认本机/环境中 Redis 可用，并检查后端 `application.yml` 中的 Redis 配置项。
