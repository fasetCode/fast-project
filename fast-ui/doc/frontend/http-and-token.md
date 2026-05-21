# 请求封装与 Token

## HTTP 客户端

`admin-vue` 使用 Axios，并在 `src/utils/axios.ts` 做统一封装：

- baseURL
- 请求/响应拦截
- Token 注入
- 错误处理

定位文件：

- `fast-ui/apps/admin-vue/src/utils/axios.ts`
- `fast-ui/apps/admin-vue/src/utils/token.ts`

## 开发建议

- 新增接口请求优先放到 `src/api/<domain>/**.ts` 统一管理
- 返回结构通常对应后端 `ResultVo` / `PageVo`
