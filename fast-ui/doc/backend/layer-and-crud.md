# 分层与 CRUD 模板

## 典型调用链

`Controller -> Service -> Repository -> Entity`

Controller 基本集中在运行时模块（如 `run-admin`），领域能力尽量下沉到 `*-module`。

## 一个标准模型的最小文件集合

以 `Foo` 为例：

- `*-module`
  - `domain/Foo.java`
  - `repository/db/FooRepository.java`
  - `mapper/FooMapper.java`
  - `service/FooService.java`
  - `service/impl/FooServiceImpl.java`
  - `vo/foo/FooCreate.java`
  - `vo/foo/FooUpdate.java`
  - `vo/foo/FooQuery.java`
  - `vo/foo/FooVo.java`
- `run-admin`
  - `module/<domain>/controller/FooController.java`

## 接口拆分建议

- `POST /foo`：新增
- `PUT /foo`：修改
- `DELETE /foo/{id}`：删除
- `DELETE /foo/batch`：批量删除
- `POST /foo/page`：分页查询
- `GET /foo/{id}`：详情
- `GET /foo/list`：查询列表
- `GET /foo/selectAll`：下拉可选项

更完整模板参考：仓库根目录 `AGENTS.md`（14.*）
