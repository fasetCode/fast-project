# GraalVM Native Image（native-image）

本项目的多个后端启动模块已接入 GraalVM Native Build Tools（Gradle 插件），支持将 Spring Boot 应用编译为本地可执行文件（Native Image）。

## 支持范围

当前仓库内以下启动模块已应用 `org.graalvm.buildtools.native` 插件，可直接执行 `nativeCompile`：

- `run-admin`
- `run-customer-plugin`
- `run-web`
- `server-work`
- `websocket`

说明：领域能力模块（如 `system-module`、`file-module` 等）是库模块，通常不单独产出可执行文件，它们会随启动模块一起进入 Native Image。

## 环境前置

- 安装 GraalVM JDK（建议选用与项目 Java 版本兼容的 GraalVM 发行版）
- 安装 `native-image` 组件
  - GraalVM Updater：`gu install native-image`
- 建议先确认 Gradle 与构建链路正常：`.\gradlew.bat :run-admin:compileJava`

## 构建命令

### 查看可用任务

Windows：

```powershell
.\gradlew.bat :run-admin:tasks --all
```

Linux/macOS：

```bash
./gradlew :run-admin:tasks --all
```

### 生成 Native 可执行文件

Windows：

```powershell
.\gradlew.bat :run-admin:nativeCompile
```

Linux/macOS：

```bash
./gradlew :run-admin:nativeCompile
```

其它模块同理（将 `:run-admin:` 替换为对应模块）：

- `:run-customer-plugin:nativeCompile`
- `:run-web:nativeCompile`
- `:server-work:nativeCompile`
- `:websocket:nativeCompile`

## 产物位置与运行

一般产物位于：

- `/<module>/build/native/nativeCompile/`

示例（Windows）：

```powershell
.\run-admin\build\native\nativeCompile\run-admin.exe
```

示例（Linux/macOS）：

```bash
./run-admin/build/native/nativeCompile/run-admin
```

## 常见问题排查

- 首次构建时间较长：属于正常现象，可先从 `run-web` 或 `server-work` 这类相对依赖较少的模块验证链路
- 反射/代理相关报错：优先关注构建日志中的 AOT / native-image 输出，定位缺失的反射/资源配置或 reachability metadata
- 内存不足：适当提高 Gradle JVM 内存参数（如 `GRADLE_OPTS` / `org.gradle.jvmargs`）
