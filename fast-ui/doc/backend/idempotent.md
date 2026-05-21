# 幂等（防重复提交）

Controller 常用：

`@Idempotent(prefix = "...", expireTime = 120, title = "...")`

适用场景：

- 新增/支付/发货等写操作
- 前端可能重复点击提交按钮
- 网络重试导致重复请求

建议：

- prefix 体现业务语义与接口动作（add/update/pay 等）
- expireTime 覆盖用户可能的重复提交时间窗
