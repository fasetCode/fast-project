# 启动与配置

## 启动入口

- `run-admin/src/main/java/com/fastproject/RunAdmin.java`
- `run-customer-plugin/src/main/java/com/fastproject/RunCustomer.java`
- `server-work/src/main/java/com/fastproject/RunServerWork.java`
- `websocket/src/main/java/com/fastproject/WebSocketRun.java`

## 常用命令

仓库根目录：

```bash
./gradlew :run-admin:compileJava
./gradlew :run-admin:bootRun
```

Windows：

```powershell
.\gradlew.bat :run-admin:compileJava
.\gradlew.bat :run-admin:bootRun
```

## 配置文件

主要配置集中在各运行时模块的 `src/main/resources`：

- `run-admin/src/main/resources/application.yml`
- `run-customer-plugin/src/main/resources/application.yml`
- `server-work/src/main/resources/application.yml`
- `websocket/src/main/resources/application.yml`

常见关注点：

- 数据源（PostgreSQL/H2）
- Redis 连接（Jedis）
- `spring.jpa.hibernate.ddl-auto`（实体变更风险）
