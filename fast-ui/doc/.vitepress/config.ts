import { defineConfig } from 'vitepress'

export default defineConfig({
  lang: 'zh-CN',
  title: 'Fast Project 文档',
  description: 'fast-project 全项目开发与架构文档',
  themeConfig: {
    logo: '/logo.svg',
    nav: [
      { text: '指南', link: '/guide/quickstart' },
      { text: '后端', link: '/backend/overview' },
      { text: '前端', link: '/frontend/overview' },
      { text: '部署', link: '/deploy/build-and-run' }
    ],
    sidebar: {
      '/guide/': [
        {
          text: '指南',
          items: [
            { text: '快速开始', link: '/guide/quickstart' },
            { text: '目录结构', link: '/guide/repo-structure' },
            { text: '开发流程', link: '/guide/dev-workflow' },
            { text: '关键约定', link: '/guide/conventions' },
            { text: '常见问题', link: '/guide/troubleshooting' }
          ]
        }
      ],
      '/architecture/': [
        {
          text: '架构',
          items: [{ text: '总览', link: '/architecture/overview' }]
        }
      ],
      '/backend/': [
        {
          text: '后端',
          items: [
            { text: '总览', link: '/backend/overview' },
            { text: '启动与配置', link: '/backend/run-and-config' },
            { text: '模块职责', link: '/backend/modules' },
            { text: '接口规范', link: '/backend/api-conventions' },
            { text: '鉴权与权限点', link: '/backend/security' },
            { text: '数据库与 JPA', link: '/backend/database' },
            { text: 'Redis 与缓存', link: '/backend/redis' },
            { text: '日志与审计', link: '/backend/logging' },
            { text: '幂等', link: '/backend/idempotent' },
            { text: '限流', link: '/backend/ratelimit' },
            { text: '分层与 CRUD 模板', link: '/backend/layer-and-crud' },
            { text: '多租户', link: '/backend/tenant' }
          ]
        }
      ],
      '/frontend/': [
        {
          text: '前端',
          items: [
            { text: '工作区总览', link: '/frontend/overview' },
            { text: 'admin-vue', link: '/frontend/admin-vue' },
            { text: '请求与 Token', link: '/frontend/http-and-token' },
            { text: '权限与路由', link: '/frontend/permission-and-route' },
            { text: '页面开发约定', link: '/frontend/page-patterns' },
            { text: 'customer-service-vue', link: '/frontend/customer-service-vue' },
            { text: 'web-web', link: '/frontend/web-web' }
          ]
        }
      ],
      '/deploy/': [
        {
          text: '部署',
          items: [
            { text: '构建与运行', link: '/deploy/build-and-run' },
            { text: 'GraalVM Native Image', link: '/deploy/graalvm-native' }
          ]
        }
      ]
    },
    outline: { level: [2, 3] },
    search: { provider: 'local' },
    footer: {
      message: 'Fast Project 文档站点',
      copyright: 'Copyright © Fast Project'
    }
  }
})
