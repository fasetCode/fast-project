# admin-vue

后台管理前端应用路径：`fast-ui/apps/admin-vue`

## 关键目录

- `src/api/**`：接口封装与前端类型定义（按业务域分目录）
- `src/views/**`：页面（通常一个 `index.vue` 内集成搜索/表格/弹窗表单/提交逻辑）
- `src/router/index.ts`：路由
- `src/stores/**`：Pinia 状态管理
- `src/utils/**`：axios、权限、token、字典等工具
- `src/components/**`：通用组件（如图片上传、富文本等）

## 常见定位方式

### 以“系统管理-岗位”为例

- 页面：`src/views/system/syspost/index.vue`
- API：`src/api/system/syspost.ts`

### 以“商城-商品”为例

- 页面：`src/views/mall/mallproduct/index.vue`
- API：`src/api/mall/mallproduct.ts`

## 页面开发习惯

- 搜索区 + 表格区 + 分页 + 弹窗表单，一页内完成 CRUD
- 权限控制通常通过工具函数/Store 统一处理（按钮显示/可用性）
