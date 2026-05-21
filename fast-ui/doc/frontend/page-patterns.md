# 页面开发约定（admin-vue）

## 页面形态

后台页面多数采用“单文件集成式”：

- 一个 `index.vue` 完成搜索 + 表格 + 分页 + 弹窗表单 + 提交逻辑
- 表单校验失败时，优先让用户能快速定位错误字段

## 文件命名与对应关系

通常一一对应：

- 后端 Controller：`SysTenantController`
- 前端 API：`src/api/system/systenant.ts`
- 前端页面：`src/views/system/systenant/index.vue`

## 建议

- API 类型（Query/Create/Update/Vo）跟后端 VO 命名保持一致
- 新增字段时同步更新：搜索项、表格列、表单项、提交参数
