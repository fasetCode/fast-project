# 接口规范与返回结构

## 统一返回

后端接口统一使用：

- `utils/src/main/java/com/fastproject/utils/vo/ResultVo.java`：`{ code, msg, data }`
- `utils/src/main/java/com/fastproject/utils/vo/PageVo.java`：分页 `data + total`

典型分页返回：

`ResultVo<PageVo<List<Vo>>>`

## Controller 常用注解

以 `run-admin/src/main/java/com/fastproject/module/mall/controller/MallProductController.java` 为例，常见组合：

- `@RestController` + `@RequestMapping`：接口分组
- `@PreAuthorize("@ps.hasPermission('xxx')")`：权限点控制
- `@Log(...)`：操作日志（业务审计）
- `@Idempotent(...)`：幂等控制（防重复提交）

## CRUD 路由约定（常见）

- `POST /xxx`：新增
- `PUT /xxx`：修改
- `DELETE /xxx/{id}`：删除
- `DELETE /xxx/batch`：批量删除
- `POST /xxx/page`：分页查询（使用 Query VO）
- `GET /xxx/{id}`：详情
- `GET /xxx/list`：列表
- `GET /xxx/selectAll`：下拉可选项（通常不加权限或权限更宽）
