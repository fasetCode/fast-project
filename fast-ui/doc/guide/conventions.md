# 关键约定（必读）

## 后端分层约定

领域模块常见结构：

- `domain`：JPA 实体（通常继承 `BaseEntity`）
- `repository/db`：Spring Data JPA 仓储
- `mapper`：MapStruct 映射器
- `service` / `service/impl`：业务接口与实现
- `vo`：请求/响应对象

## 接口返回结构

- `ResultVo`：统一返回 `{ code, msg, data }`
- `PageVo`：分页返回 `{ total, data }`

## 权限点

Controller 常用：

`@PreAuthorize("@ps.hasPermission('xxx')")`

新增接口时建议同步补齐：

- 权限串（后端注解）
- 权限数据（角色/权限配置）
- 前端按钮/路由权限判断

## 前端页面形态（admin-vue）

常见页面是“一个 index.vue 内集成完整 CRUD”：

- 搜索区
- 表格区 + 分页
- 新增/编辑弹窗表单
- 提交与校验
