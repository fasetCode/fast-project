---
layout: home
title: Fast Project
titleTemplate: false

hero:
  name: Fast Project
  text: 全项目开发与架构文档
  tagline: 前后端分离、多模块 Gradle + pnpm workspace 的业务系统模板。面向团队协作、规范沉淀与快速迭代。
  image:
    src: /logo.svg
    alt: Fast Project
  actions:
    - theme: brand
      text: 快速开始
      link: /guide/quickstart
    - theme: alt
      text: 架构总览
      link: /architecture/overview
    - theme: alt
      text: 构建与运行
      link: /deploy/build-and-run

features:
  - title: 清晰的模块边界
    details: 运行时应用 + 领域能力模块的组合模式，模块职责明确，便于复用与扩展。
  - title: 统一的工程化约定
    details: 目录结构、接口规范、日志/幂等/限流等通用能力均有文档沉淀，降低上手成本。
  - title: 前端工作区一体化
    details: Vue 3 + TypeScript + pnpm workspace，多应用共享规范与基础能力。
---

## 快速入口

<div class="vp-doc">

| 模块 | 入口 |
| --- | --- |
| 指南 | [快速开始](/guide/quickstart) · [开发流程](/guide/dev-workflow) · [关键约定](/guide/conventions) |
| 后端 | [后端总览](/backend/overview) · [模块职责](/backend/modules) · [接口规范](/backend/api-conventions) |
| 前端 | [工作区总览](/frontend/overview) · [请求与 Token](/frontend/http-and-token) · [权限与路由](/frontend/permission-and-route) |
| 部署 | [构建与运行](/deploy/build-and-run) · [GraalVM Native Image](/deploy/graalvm-native) |

</div>

## 本地预览（文档站点）

在 `fast-ui/doc` 目录执行：

```bash
pnpm install
pnpm dev
```

## 现有资料

- 仓库级约定与入口：仓库根目录 `AGENTS.md`
- 历史 Wiki（中文）：`.qoder/repowiki/zh/content`
