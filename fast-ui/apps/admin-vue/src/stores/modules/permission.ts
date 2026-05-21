import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from '@/utils/axios'

// 路由元信息接口
export interface RouteMeta extends Record<PropertyKey, unknown> {
  title: string
  icon?: string
  rank?: number
  showLink?: boolean
}

// 路由接口
export interface AppRouteRecordRaw {
  name: string
  path: string
  component: string
  meta: RouteMeta
  children?: AppRouteRecordRaw[]
}

export const usePermissionStore = defineStore('permission', () => {
  const routes = ref<AppRouteRecordRaw[]>([])
  const isRoutesLoaded = ref(false)
  const routesLoadFailed = ref(false)
  // 按钮权限列表
  const buttonPermissions = ref<string[]>([])
  const isButtonPermissionsLoaded = ref(false)

  // 从后端获取路由数据
  const fetchRoutes = async () => {
    try {
      routesLoadFailed.value = false
      const res = await axios.get('/auth/getAsyncRoutes')
      if (res && res.code === 200) {
        routes.value = res.data || []
        return routes.value
      }

      routes.value = []
      routesLoadFailed.value = true
      return []
    } catch (error) {
      routes.value = []
      routesLoadFailed.value = true
      console.error('Failed to fetch routes:', error)
      return []
    } finally {
      // 无论成功还是失败，都结束首次加载状态，避免路由守卫无限重试导致白屏
      isRoutesLoaded.value = true
    }
  }

  // 从后端获取按钮权限数据（仅在未加载时请求）
  const fetchButtonPermissions = async () => {
    // 已有数据直接返回
    if (isButtonPermissionsLoaded.value && buttonPermissions.value.length > 0) {
      return buttonPermissions.value
    }
    try {
      const res = await axios.get('/auth/getButtonPermissions')
      if (res && res.code === 200) {
        buttonPermissions.value = res.data || []
        isButtonPermissionsLoaded.value = true
        return buttonPermissions.value
      }
      return []
    } catch (error) {
      console.error('Failed to fetch button permissions:', error)
      return []
    }
  }

  // 重置路由状态
  const resetRoutes = () => {
    routes.value = []
    isRoutesLoaded.value = false
    routesLoadFailed.value = false
  }

  // 重置按钮权限状态
  const resetButtonPermissions = () => {
    buttonPermissions.value = []
    isButtonPermissionsLoaded.value = false
  }

  return {
    routes,
    isRoutesLoaded,
    routesLoadFailed,
    buttonPermissions,
    isButtonPermissionsLoaded,
    fetchRoutes,
    fetchButtonPermissions,
    resetRoutes,
    resetButtonPermissions
  }
})
