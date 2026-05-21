# 常见问题（Troubleshooting）

## 前端安装依赖时提示 Ignored build scripts

pnpm 可能会提示某些依赖的构建脚本被忽略（如 `core-js`、`esbuild`）。需要时可执行：

```bash
pnpm approve-builds
```

然后按提示选择允许的依赖构建脚本。

## 后端启动失败：JDK 版本

后端使用 Java 25。若 IDE 或命令行使用的 JDK 版本不一致，可能导致编译/启动失败。

建议先用编译命令确认环境：

```powershell
.\gradlew.bat :run-admin:compileJava
```

## 后端启动失败：数据库连接

`run-admin` 默认使用 PostgreSQL。

排查顺序：

- 数据库是否启动、账号密码是否正确
- `run-admin/src/main/resources/application.yml` 中数据源配置是否匹配
- 是否切换到了正确的 profile（`application-dev*.yml`）

## 后端启动失败：Redis 连接

部分模块依赖 Redis/Jedis。若报连接错误：

- 确认 Redis 可用
- 检查后端 `application.yml` 的 Redis 配置
