# 鉴权与权限点

## 权限点校验

Controller 常用写法：

`@PreAuthorize("@ps.hasPermission('admin:mall:product:add')")`

其中 `ps` 对应运行时模块中的 `run-admin/src/main/java/com/fastproject/module/security/PermissionService.java`（`@Service("ps")`）。

## 当前实现要点

- 权限串为空直接拒绝
- 从登录态中取当前用户与权限集合
- 用户 `userId == 1` 作为超级管理员直接放行（如需调整以业务规则为准）

## 前端联动

前端按钮权限与路由权限通常依赖后端下发的权限集合；新增接口权限点时，建议同步检查：

- 后端接口 `@PreAuthorize` 的权限串
- 权限数据（`system-module` 下的权限/角色/菜单模型）
- 前端页面按钮权限判断逻辑（`admin-vue` 的权限工具与 Store）
