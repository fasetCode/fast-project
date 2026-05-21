# 数据库与 JPA

## 数据库类型

- `run-admin`：PostgreSQL（默认）
- `server-work`：H2 文件数据库（默认）

配置文件位于各运行时模块的 `src/main/resources/application*.yml`。

## ddl-auto 风险提示

当前配置中可能存在：

`spring.jpa.hibernate.ddl-auto=update`

会自动更新表结构。改实体字段前建议先确认：

- 是否需要数据迁移
- 是否影响生产环境
- 是否需要先加默认值/可空字段过渡

## 实体与查询约定

- 实体通常继承 `common` 中的 `BaseEntity`
- 查询条件通常通过 `QueryHelp` 等工具拼装
- DTO/VO 与实体映射优先使用 MapStruct
