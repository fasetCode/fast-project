# 多租户

仓库已存在第一版多租户后端隔离能力。

## 开关

- 配置项：`fastproject.tenant.enabled`
- 默认位置：`run-admin/src/main/resources/application.yml`
- 默认值：`false`

## 核心代码位置

- `system-module/src/main/java/com/fastproject/system/config/TenantProperties.java`
- `system-module/src/main/java/com/fastproject/system/config/TenantFeatureGuard.java`
- `system-module/src/main/java/com/fastproject/system/tenant/TenantAccessSupport.java`

## 当前隔离范围（第一版）

- 用户
- 角色
- 部门
- 岗位

权限菜单目前仍偏全局模型；若要做彻底租户化，需继续评估 `SysPermissions`、动态路由与按钮权限链路。
