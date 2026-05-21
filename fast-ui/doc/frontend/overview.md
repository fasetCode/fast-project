# 前端工作区总览

前端位于 `fast-ui`，使用 pnpm workspace 管理多个 Vue 应用。

## 技术栈（各应用共性）

- Vue 3 + TypeScript
- Vite
- Pinia
- Vue Router
- Axios

## 工作区结构

- `fast-ui/pnpm-workspace.yaml`：工作区定义（默认包含 `apps/*`、`packages/*`）
- `fast-ui/apps/admin-vue`：后台管理端（UI 主要为 Ant Design Vue）
- `fast-ui/apps/customer-service-vue`：客服插件端
- `fast-ui/apps/web-web`：Web 站点/页面渲染相关

## 常用命令

在 `fast-ui` 目录：

```bash
pnpm install
pnpm admin-vue:dev
pnpm admin-vue:build
```

也可以直接过滤到某个子应用：

```bash
pnpm --filter admin-vue dev
pnpm --filter customer-service-vue dev
```
