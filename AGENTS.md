# Fast Project AGENTS Guide

## 1. 项目概览

`fast-project` 是一个前后端分离、多模块 Gradle + pnpm workspace 的业务系统仓库，当前主要包含：

- 后台管理端：`run-admin` + `fast-ui/apps/admin-vue`
- 客服插件端：`run-customer-plugin` + `fast-ui/apps/customer-service-vue`
- 独立监控节点：`server-work`
- 独立 WebSocket 服务：`websocket`
- 业务能力库模块：`system-module`、`file-module`、`logs-module`、`idempotent-module`、`ratelimit-module`、`message-module`

整体结构偏向“运行时应用 + 领域能力模块 + 前端工作区”的组合模式。

## 2. 技术栈

### 后端

- Gradle 多模块工程
- Java 25
- Spring Boot 4.0.3
- Spring Data JPA + Hibernate 7
- PostgreSQL / H2
- Spring Security
- Jedis + Caffeine
- MapStruct
- GraalVM Native 相关支持

### 前端

- pnpm workspace
- Vue 3 + TypeScript
- Vite
- Pinia
- Vue Router
- Ant Design Vue
- Axios

## 3. 顶层目录说明

- `build.gradle`：根 Gradle 构建脚本，统一依赖版本、公共插件、模块依赖关系
- `settings.gradle`：声明所有 Gradle 子模块
- `common`：后端公共基础能力，如 `BaseEntity`、`QueryHelp`、异常、Redis/Jedis、Token 工具
- `utils`：纯工具模块，包含 `StatusEnum`（状态枚举：NORMAL=1 正常、DISABLED=2 冻结）等通用工具
- `system-module`：系统管理核心领域，包含用户、角色、权限、部门、岗位、租户、字典、配置、慢查询
- `file-api` / `file-module`：文件上传、文件信息、文件域名、文件配置
- `logs-api` / `logs-module`：操作日志
- `idempotent-api` / `idempotent-module`：幂等控制与重复请求日志
- `ratelimit-api` / `ratelimit-module`：限流配置与限流记录
- `message-api` / `message-module`：消息领域，当前接入程度较低
- `run-admin`：后台管理后端启动工程
- `run-customer-plugin`：客服插件后端启动工程
- `server-work`：监控采集服务
- `websocket`：独立 WebSocket 服务
- `fast-ui`：前端工作区，包含多个 Vue 应用
- `AGENTS.md`：当前文档

## 4. 启动入口

### 后端应用入口

- `run-admin/src/main/java/com/fastproject/RunAdmin.java`
- `run-customer-plugin/src/main/java/com/fastproject/RunCustomer.java`
- `server-work/src/main/java/com/fastproject/RunServerWork.java`
- `websocket/src/main/java/com/fastproject/WebSocketRun.java`

### 主要配置文件

- `run-admin/src/main/resources/application.yml`
- `run-customer-plugin/src/main/resources/application.yml`
- `server-work/src/main/resources/application.yml`
- `websocket/src/main/resources/application.yml`

## 5. 后端分层模式

仓库中的核心业务模块大多遵循以下结构：

- `domain`：JPA 实体
- `repository/db`：Spring Data JPA 仓储
- `mapper`：MapStruct 映射器
- `service` / `service/impl`：业务接口与实现
- `vo`：请求/响应对象，通常按领域再拆子目录
- `controller`：只出现在启动模块，例如 `run-admin`

典型调用链为：

`Controller -> Service -> Repository -> Entity`

补充说明：

- Controller 基本集中在运行时模块，如 `run-admin/module/**/controller`
- 领域能力尽量下沉到 `*-module`
- 公共登录态、Token、基础实体、分页工具统一在 `common`

## 6. 前端结构

`fast-ui` 使用 pnpm workspace 管理前端应用。

### 工作区

- 根工作区配置：`fast-ui/pnpm-workspace.yaml`
- 根脚本：`fast-ui/package.json`

### 子应用

- `fast-ui/apps/admin-vue`
  - 后台管理端
  - API 目录：`src/api/**`
  - 页面目录：`src/views/**`
  - 系统管理页面集中在 `src/views/system/**`

- `fast-ui/apps/customer-service-vue`
  - 客服插件前端
  - 聊天、会话、用户相关页面集中在 `src/views/**`

## 7. 模块职责速查

### `system-module`

系统管理核心模块，优先查看这里处理以下需求：

- 用户、角色、权限
- 部门、岗位
- 租户
- 系统配置
- 字典
- 慢查询日志

如果需求来自后台“系统管理”菜单，通常先从这里找实体、VO、Service，再到 `run-admin` 对应 Controller 和 `fast-ui/apps/admin-vue/src/views/system` 页面联动修改。

### `file-module`

处理以下需求：

- 文件上传
- 文件信息存储
- 文件域名与文件配置
- 文件 URL 解析

### `logs-module`

处理操作日志记录与查询，配合 `logs-api` 注解/切面能力使用。

### `idempotent-module`

处理接口幂等控制、重复请求日志，通常和 `idempotent-api` 的注解切面一起工作。

### `ratelimit-module`

处理全局限流、IP 限流、API 限流、限流命中记录。

### `run-admin`

后台管理后端聚合模块，特点：

- 提供 REST API
- 提供安全配置、JWT 鉴权、全局异常处理
- 聚合 `system-module`、`file-module`、`logs-module`、`idempotent-module`、`ratelimit-module`

## 8. 构建与运行命令

### 后端

在仓库根目录执行：

```bash
./gradlew :run-admin:compileJava
./gradlew :run-admin:bootRun
./gradlew :run-customer-plugin:bootRun
./gradlew :server-work:bootRun
./gradlew :websocket:bootRun
```

Windows 下通常使用：

```powershell
.\gradlew.bat :run-admin:compileJava
```

### 前端

在 `fast-ui` 目录执行：

```bash
pnpm install
pnpm admin-vue:dev
pnpm admin-vue:build
```

如果只进子应用目录，也可以直接：

```bash
pnpm dev
pnpm build
```

## 9. 重要约定

### Gradle 模块约定

- 库模块使用 `java-library`，不是 Spring Boot 启动模块
- 根 `build.gradle` 统一导入 Spring Boot BOM
- 库模块构建输出重定向到根目录 `.module-build/<module>`
- 含 Hibernate 增强的模块如 `system-module` 使用 `org.hibernate.orm` 插件

### 数据访问约定

- 实体基础字段继承 `common` 中的 `BaseEntity`
- JPA 查询辅助通常通过 `QueryHelp`
- DTO/VO 与实体映射优先使用 MapStruct
- 逻辑删除通常通过 `@SQLDelete` + `@SQLRestriction`

### 配置约定

- `run-admin` 使用 PostgreSQL
- `server-work` 默认使用 H2 文件数据库
- 很多模块依赖 Redis/Jedis，请先确认 Redis 可用
- 当前 `run-admin` 配置中 `spring.jpa.hibernate.ddl-auto=update`，会自动更新表结构，改实体时要谨慎

## 10. 租户相关说明

当前仓库已存在租户管理与第一版多租户后端隔离能力：

- 配置开关：`fastproject.tenant.enabled`
- 默认配置位置：`run-admin/src/main/resources/application.yml`
- 默认值：`false`
- 租户核心代码位置：
  - `system-module/src/main/java/com/fastproject/system/config/TenantProperties.java`
  - `system-module/src/main/java/com/fastproject/system/config/TenantFeatureGuard.java`
  - `system-module/src/main/java/com/fastproject/system/tenant/TenantAccessSupport.java`

当前多租户第一版隔离范围主要在：

- 用户
- 角色
- 部门
- 岗位

权限菜单目前仍偏全局模型，后续如要做彻底租户化，需要继续评估 `SysPermissions`、动态路由、按钮权限链路。

## 11. 改需求时的定位建议

### 后台系统管理类需求

优先按下面顺序定位：

1. `fast-ui/apps/admin-vue/src/views/system/**`
2. `fast-ui/apps/admin-vue/src/api/system/**`
3. `run-admin/src/main/java/com/fastproject/module/system/controller/**`
4. `system-module/src/main/java/com/fastproject/system/service/**`
5. `system-module/src/main/java/com/fastproject/system/domain/**`

### 文件类需求

优先看：

1. `fast-ui/apps/admin-vue/src/views/file/**`
2. `run-admin/module/file/controller/**`
3. `file-module/**`

### 限流/幂等/日志类需求

优先看：

1. `run-admin/module/<domain>/controller/**`
2. `<domain>-module/**`
3. `<domain>-api/**`

## 12. 当前已知特点

- 仓库偏业务快速迭代风格，很多模块以“实体 + VO + Mapper + Service + Controller”方式平铺展开
- 自动化测试文件较少，改完代码后通常至少要做编译校验
- 前后端命名多数一一对应，例如：
  - 后端 `SysTenantController`
  - 前端 `src/api/system/systenant.ts`
  - 前端页面 `src/views/system/systenant/index.vue`

## 13. 推荐的改动后检查

后端改动后建议至少执行：

```powershell
.\gradlew.bat :run-admin:compileJava
```

前端改动后建议至少执行：

```bash
pnpm --filter admin-vue build
```

如果改动涉及实体结构：

- 检查 `application.yml` 中 `ddl-auto`
- 确认数据库字段是否需要初始化或迁移
- 同步检查前端 API 和页面表单/列表/搜索项是否需要联动

## 14. 模型集成 CRUD 包内分布模板

这一节用于说明：如果要在当前项目里新增一个“标准后台管理模型”，应该如何按现有风格组织后端与前端代码。

参考样例：

- 后端 Controller：`run-admin/src/main/java/com/fastproject/module/system/controller/SysPostController.java`
- 后端领域模块：`system-module` 下的 `SysPost`
- 前端页面：`fast-ui/apps/admin-vue/src/views/system/syspost/index.vue`
- 前端 API：`fast-ui/apps/admin-vue/src/api/system/syspost.ts`

### 14.1 后端分布

以一个模型 `Foo` 为例，建议按下面方式组织：

- Controller
  - 路径：`run-admin/src/main/java/com/fastproject/module/<domain>/controller/FooController.java`
  - 作用：暴露 REST 接口，负责接收前端参数、权限注解、日志注解、幂等注解
  - 参考 `SysPostController` 的接口拆分：
    - `POST /foo`：新增
    - `PUT /foo`：修改
    - `DELETE /foo/{id}`：删除
    - `DELETE /foo/batch`：批量删除
    - `POST /foo/page`：分页查询
    - `GET /foo/{id}`：详情
    - `GET /foo/list`：查询全部列表
    - `GET /foo/selectAll`：查询可选项

- Service 接口
  - 路径：`system-module/src/main/java/com/fastproject/system/service/FooService.java`
  - 作用：定义模型的标准 CRUD 能力
  - 常见方法：
    - `save(Create)`
    - `update(Update)`
    - `delete(id)`
    - `batchDelete(ids)`
    - `findById(id)`
    - `findPage(Query)`
    - `findAll()`
    - `selectAll()`

- Service 实现
  - 路径：`system-module/src/main/java/com/fastproject/system/service/impl/FooServiceImpl.java`
  - 作用：承接真正的业务逻辑
  - 通常负责：
    - 唯一性校验
    - 查询条件拼装
    - 仓储调用
    - MapStruct 对象转换
    - 状态过滤
    - 多租户校验或绑定

- Domain 实体
  - 路径：`system-module/src/main/java/com/fastproject/system/domain/Foo.java`
  - 作用：JPA 实体，对应数据库表
  - 通常继承 `BaseEntity`
  - 常见约定：
    - `@Entity`
    - `@Table`
    - `@SQLDelete`
    - `@SQLRestriction`

- Repository
  - 路径：`system-module/src/main/java/com/fastproject/system/repository/db/FooRepository.java`
  - 作用：数据库访问
  - 一般继承：
    - `JpaRepository<Foo, Long>`
    - `JpaSpecificationExecutor<Foo>`
  - 常放内容：
    - 唯一性判断方法
    - 按租户判断方法
    - 少量定制查询

- Mapper
  - 路径：`system-module/src/main/java/com/fastproject/system/mapper/FooMapper.java`
  - 作用：实体与 VO/DTO 转换
  - 常见方法：
    - `toFoo(Create create)`
    - `updateFooFromDto(Update dto, @MappingTarget Foo entity)`
    - `toVo(Foo entity)`
    - `toVo(List<Foo> list)`

- VO 包
  - 路径：`system-module/src/main/java/com/fastproject/system/vo/foo/`
  - 作用：按 CRUD 语义拆分输入输出对象
  - 通常至少包含：
    - `FooCreate.java`
    - `FooUpdate.java`
    - `FooQuery.java`
    - `FooVo.java`

### 14.2 以 SysPost 为例的后端文件职责

- `SysPostController.java`
  - 提供岗位模块所有 HTTP CRUD 接口
  - 负责权限控制、日志记录、幂等控制

- `SysPostService.java`
  - 定义岗位标准 CRUD 接口

- `SysPostServiceImpl.java`
  - 实现岗位新增、修改、删除、分页、列表、选择框查询
  - 当前还包含租户隔离逻辑与岗位编码唯一性校验

- `SysPost.java`
  - 岗位实体
  - 对应数据库表 `sys_post`

- `SysPostRepository.java`
  - 提供岗位编码唯一性检查与分页查询基础能力

- `SysPostMapper.java`
  - 处理 `SysPostCreate`、`SysPostUpdate`、`SysPostVo` 与实体之间的转换

- `vo/post/*`
  - `SysPostCreate.java`：新增入参
  - `SysPostUpdate.java`：修改入参
  - `SysPostQuery.java`：分页查询入参
  - `SysPostVo.java`：列表/详情返回结构

### 14.3 前端分布

前端后台管理模型通常按下面结构组织：

- API 文件
  - 路径：`fast-ui/apps/admin-vue/src/api/system/foo.ts`
  - 作用：封装后端接口请求
  - 一般同时定义：
    - `FooVo`
    - `FooQuery`
    - `FooCreate`
    - `FooUpdate`
    - `PageVo`
    - `ResultVo`
  - 常见导出方法：
    - `getFooPage`
    - `getFooById`
    - `createFoo`
    - `updateFoo`
    - `deleteFoo`
    - `batchDeleteFoo`
    - `getFooList`
    - `getFooSelectAll`

- 页面文件
  - 路径：`fast-ui/apps/admin-vue/src/views/system/foo/index.vue`
  - 作用：一个页面内完成搜索、表格、分页、弹窗表单、提交逻辑
  - 当前项目风格通常不是拆成多个小文件，而是一个 `index.vue` 直接集成：
    - 搜索区
    - 表格区
    - 批量操作
    - 新增/编辑弹窗
    - 表单校验
    - 调接口与权限控制

### 14.4 以 SysPost 为例的前端文件职责

- `src/api/system/syspost.ts`
  - 定义岗位前端类型
  - 封装岗位分页、详情、新增、修改、删除、批量删除、列表、下拉接口

- `src/views/system/syspost/index.vue`
  - 页面内集成岗位完整 CRUD
  - 包含：
    - 搜索条件：岗位名称、岗位编码、状态
    - 列表展示：表格、分页、多选
    - 操作按钮：新增、编辑、删除、批量删除
    - 弹窗表单：新增/编辑共用
    - 权限控制：`canAdd`、`canUpdate`、`canDelete`

### 14.5 一个新模型接入 CRUD 时最少要补哪些文件

后端最少需要：

- `domain/Foo.java`
- `repository/db/FooRepository.java`
- `mapper/FooMapper.java`
- `service/FooService.java`
- `service/impl/FooServiceImpl.java`
- `vo/foo/FooCreate.java`
- `vo/foo/FooUpdate.java`
- `vo/foo/FooQuery.java`
- `vo/foo/FooVo.java`
- `run-admin/.../controller/FooController.java`

前端最少需要：

- `src/api/system/foo.ts`
- `src/views/system/foo/index.vue`

如果还要接入菜单/权限，通常还要补：

- 权限点，如 `admin:system:foo:add`
- 菜单或路由配置
- 页面按钮权限判断

### 14.6 命名建议

当前项目推荐保持下面的一致命名：

- 实体：`SysFoo`
- Service：`SysFooService`
- ServiceImpl：`SysFooServiceImpl`
- Repository：`SysFooRepository`
- Mapper：`SysFooMapper`
- Controller：`SysFooController`
- Create：`SysFooCreate`
- Update：`SysFooUpdate`
- Query：`SysFooQuery`
- 返回对象：`SysFooVo`
- 前端 API：`sysfoo.ts`
- 前端页面目录：`sysfoo/index.vue`

### 14.7 推荐开发顺序

新增一个标准 CRUD 模型时，推荐按这个顺序做：

1. 先建实体 `domain`
2. 再建 `Repository`
3. 再建 `VO`
4. 再建 `Mapper`
5. 再建 `Service` 与 `ServiceImpl`
6. 再建 `Controller`
7. 再补前端 `api`
8. 最后补前端 `index.vue`

如果字段有变化，记得同步检查：

- 实体字段
- Create / Update / Query / Vo
- Mapper
- Service 查询逻辑
- 前端 API 类型
- 前端搜索项、表格列、表单项
