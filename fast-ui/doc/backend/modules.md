# 模块职责

本节用于快速定位需求应该改哪里：先看“运行时应用”暴露接口，再下沉到对应“领域模块”。

## 基础能力

- `common`：基础实体、查询辅助、异常、安全、Redis/Token 等公共能力
- `utils`：纯工具与通用枚举（如状态枚举等）

## 系统与通用领域

- `system-module`：用户/角色/权限/部门/岗位/租户/字典/配置/慢查询
- `file-module` / `file-api`：文件上传、文件信息、域名与配置、URL 解析
- `logs-module` / `logs-api`：操作日志与日志注解能力
- `idempotent-module` / `idempotent-api`：幂等控制与重复请求日志
- `ratelimit-module` / `ratelimit-api`：全局/接口/IP 等限流配置与命中记录
- `message-module` / `message-api`：消息模板/记录/验证码等（接入程度按业务演进）

## 业务领域

- `mall-module` / `mall-api`：商城（商品、订单、购物车、运费模板等）
- `page-module` / `page-api`：页面搭建/页面配置/组件库（低代码画布相关）
- `user-growth-module` / `user-growth-api`：成长体系（积分、等级等）
- `pay-module` / `pay-api`：支付相关能力
