# 日志与审计

## 操作日志（业务审计）

Controller 常用：

`@Log(value = "...", type = LogType.BUSINESS, action = LogAction.CREATE)`

用途：

- 记录关键业务操作（新增/修改/删除）
- 便于审计、追踪与排障

## 代码建议

- 关键写操作接口优先补齐 `@Log`
- 日志内容要“可读且可定位”，例如写明业务对象与动作
