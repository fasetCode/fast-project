import { createRouter, createWebHashHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { usePermissionStore } from '@/stores/modules/permission'
import type { AppRouteRecordRaw } from '@/stores/modules/permission'
import TokenService from '@/utils/token'

// 静态路由，不需要权限即可访问
const constantRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录', requiresAuth: false }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/layout/index.vue'),
    redirect: '/',
    children: [
      {
        path: '',
        name: 'home',
        component: () => import('@/views/HomeView.vue'),
        meta: { title: '首页', requiresAuth: true }
      },
      {
        path: '/personal',
        name: 'Personal',
        component: () => import('@/views/personal/index.vue'),
        meta: { title: '个人中心', requiresAuth: true }
      },
      {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: () => import('@/views/NotFound.vue'),
        meta: { title: '404', requiresAuth: false }
      }
    ]
  },
  {
    path: '/page/pageconfig',
    name: 'PageConfig',
    component: () => import('@/views/page/pageconfig/index.vue'),
    meta: { title: '页面配置', requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: constantRoutes
})

// Vite 动态导入视图组件
const modules = import.meta.glob('../views/**/*.vue')

// 将后端返回的路由转换为 Vue Router 需要的格式
function formatAsyncRoutes(routes: AppRouteRecordRaw[]): RouteRecordRaw[] {
  return routes.map(route => {
    const formattedRoute: any = {
      path: route.path,
      name: route.name,
      meta: route.meta
    }

    // 处理组件
    if (route.component) {
      if (route.component === 'Layout') {
        ;(formattedRoute as any).component = () => import('@/layout/index.vue')
      } else {
        // 去除开头的 '/' 并且如果是 views 目录下的，去掉可能多余的 views 前缀
        let viewPath = route.component.replace(/^\//, '')
        // 后端可能返回 views/system/sysuser/index.vue 或者 system/sysuser/index.vue
        if (viewPath.startsWith('views/')) {
          viewPath = viewPath.substring(6)
        }
        
        // 处理可能没有 .vue 后缀的情况
        if (!viewPath.endsWith('.vue')) {
           viewPath = `${viewPath}.vue`
        }

        const matchComponent = modules[`../views/${viewPath}`]
        if (matchComponent) {
          ;(formattedRoute as any).component = matchComponent
        } else {
          // 尝试匹配 index.vue
          if (!viewPath.endsWith('index.vue') && viewPath.endsWith('.vue')) {
            const indexViewPath = viewPath.replace(/\.vue$/, '/index.vue')
            const matchIndexComponent = modules[`../views/${indexViewPath}`]
            if (matchIndexComponent) {
              ;(formattedRoute as any).component = matchIndexComponent
            } else {
              console.warn(`未找到组件: ../views/${viewPath} 或 ../views/${indexViewPath}`)
            }
          } else {
             console.warn(`未找到组件: ../views/${viewPath}`)
          }
        }
      }
    }

    // 处理子路由
    if (route.children && route.children.length > 0) {
      ;(formattedRoute as any).children = formatAsyncRoutes(route.children)
    }

    return formattedRoute as RouteRecordRaw
  })
}

// 路由守卫：动态加载路由
router.beforeEach(async (to) => {
  // 检查是否已登录
  const hasToken = TokenService.hasToken()

  // 已登录访问登录页，跳转到首页
  if (to.path === '/login' && hasToken) {
    return { path: '/', replace: true }
  }

  const permissionStore = usePermissionStore()

  // 动态路由未加载，则请求后端获取
  // 核心修复：只要已登录，就尝试加载动态路由，而不是仅检查 requiresAuth
  // 因为访问未知路径时会匹配到 NotFound 路由，其 requiresAuth 为 false
  if (!permissionStore.isRoutesLoaded) {
    try {
      // 如果未登录且访问的页面不是公开页面（meta.requiresAuth !== false），跳转到登录页
      // 注意：如果是访问 /login 页面，上面已经处理了
      if (!hasToken && to.meta.requiresAuth !== false) {
        return { path: '/login', query: { redirect: to.fullPath } }
      }

      // 如果已登录，获取动态路由
      if (hasToken) {
        const routes = await permissionStore.fetchRoutes()
        const asyncRoutes = formatAsyncRoutes(routes)
        let hasAddedRoute = false
        
        asyncRoutes.forEach(route => {
          if (!router.hasRoute(route.name as string)) {
            router.addRoute('Layout', route)
            hasAddedRoute = true
          }
        })
        
        // 只有在真正新增了动态路由时才重新导航，避免接口失败时反复 replace 导致白屏
        if (hasAddedRoute) {
          return { path: to.path, query: to.query, hash: to.hash, replace: true }
        }

        return true
      }
    } catch (error) {
      console.error('Failed to load routes', error)
      // 加载失败且未登录，跳转登录页
      if (!hasToken) {
        return { path: '/login' }
      }
      return true
    }
  }

  // 权限检查：路由加载完成后，再次检查是否需要登录
  if (to.meta.requiresAuth !== false && !hasToken) {
    return { path: '/login', query: { redirect: to.fullPath } }
  }

  return true
})

// 路由守卫：设置页面标题
router.afterEach((to) => {
  // 从路由 meta 中获取标题，如果没有则使用默认值
  const appTitle = import.meta.env.VITE_APP_TITLE || 'Fast Admin Pro'
  const pageTitle = to.meta?.title as string || appTitle
  // 设置 document.title
  document.title = `${pageTitle}`
})

export default router
