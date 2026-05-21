import {createRouter, createWebHashHistory, createWebHistory, type RouteRecordRaw} from 'vue-router'

const constantRoutes: RouteRecordRaw[] = [
  {
    path: "/",
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/home/index.vue'),
    meta: { title: '首页' }
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes
})

router.beforeEach((to, _from, next) => {
  // 设置页面标题
  if (to.meta.title) {
    document.title = to.meta.title as string
  }
  next()
})

export default router
