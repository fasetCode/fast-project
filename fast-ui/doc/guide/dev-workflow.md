# 开发流程（建议）

## 改一个功能的定位路径

大多数后台功能都符合这条链路：

`页面 -> API 封装 -> Controller -> Service -> Repository -> Entity`

如果是“系统管理”类菜单，通常从以下路径找：

- 前端页面：`fast-ui/apps/admin-vue/src/views/system/**`
- 前端 API：`fast-ui/apps/admin-vue/src/api/system/**`
- 后端 Controller：`run-admin/src/main/java/com/fastproject/module/system/controller/**`
- 领域 Service：`system-module/src/main/java/com/fastproject/system/service/**`
- 领域 Entity：`system-module/src/main/java/com/fastproject/system/domain/**`

## 新增一个标准 CRUD 模型（最小清单）

后端：

- `domain`
- `repository/db`
- `vo`（Create/Update/Query/Vo）
- `mapper`（MapStruct）
- `service` + `service/impl`
- `run-admin` 中增加 Controller 与权限点

前端：

- `src/api/<domain>/<model>.ts`
- `src/views/<domain>/<model>/index.vue`

## 本地验证建议

- 后端：`.\gradlew.bat :run-admin:compileJava`
- 前端：`pnpm --filter admin-vue build`
- 文档：`pnpm -C fast-ui docs:build`
