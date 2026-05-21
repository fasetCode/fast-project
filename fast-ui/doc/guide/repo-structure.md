# 目录结构

## 仓库根目录

- `build.gradle`：根 Gradle 构建脚本（统一依赖版本、公共插件）
- `settings.gradle`：声明所有 Gradle 子模块
- `run-admin`：后台管理后端启动工程（聚合各领域模块并提供 REST API）
- `run-customer-plugin`：客服插件后端启动工程
- `server-work`：监控采集服务
- `websocket`：独立 WebSocket 服务
- `system-module` / `file-module` / `logs-module` / `idempotent-module` / `ratelimit-module` / `message-module`：领域能力模块
- `fast-ui`：前端 pnpm workspace（包含多个 Vue 应用）

## 前端工作区（fast-ui）

- `pnpm-workspace.yaml`：工作区定义
- `apps/admin-vue`：后台管理前端（Vue 3 + TS + Vite + Ant Design Vue）
- `apps/customer-service-vue`：客服插件端前端
- `apps/web-web`：Web 站点/页面渲染相关前端
- `doc`：本项目文档站点（VitePress）
