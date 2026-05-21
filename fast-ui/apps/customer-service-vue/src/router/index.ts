import { createRouter, createWebHashHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Home',
    meta: { title: '首页' },
    component: () => import('@/views/home/index.vue')
  },
  {
    path: '/chat',
    name: 'Chat',
    meta: { title: '客服聊天' },
    component: () => import('@/views/chat/index.vue')
  },
  {
    path: '/admin',
    name: 'Admin',
    meta: { title: '管理' },
    component: () => import('@/views/admin/index.vue')
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    meta: { title: '404' },
    component: () => import('@/views/error/404.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to) => {
  if (to.meta.title) {
    document.title = `${to.meta.title}`
  }
})

export default router
