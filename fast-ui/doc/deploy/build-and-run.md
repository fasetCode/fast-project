# 构建与运行

## 后端构建

仓库根目录：

```bash
./gradlew :run-admin:compileJava
./gradlew :run-admin:bootJar
```

Windows：

```powershell
.\gradlew.bat :run-admin:compileJava
.\gradlew.bat :run-admin:bootJar
```

## Native Image（GraalVM）

如果需要构建本地可执行文件（native-image），参考：

- [GraalVM Native Image](/deploy/graalvm-native)

## 前端构建

在 `fast-ui` 目录：

```bash
pnpm install
pnpm admin-vue:build
```

## 文档站点构建

在 `fast-ui` 目录：

```bash
pnpm install
pnpm docs:build
```

构建产物默认在 `fast-ui/doc/.vitepress/dist`，可部署为静态网站（Nginx、对象存储、GitHub Pages 等）。
