# 权限与路由

## 路由入口

- `admin-vue`：`src/router/index.ts`

## 权限相关

常用文件：

- `fast-ui/apps/admin-vue/src/utils/permission.ts`
- `fast-ui/apps/admin-vue/src/stores/modules/permission.ts`

## 开发建议

- 新增后端权限点（`@PreAuthorize`）后，前端需要同步：
  - 按钮显示/可用性判断
  - 路由/菜单权限刷新逻辑
