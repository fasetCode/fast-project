<template>
  <a-layout class="app-layout" :class="[
    { 'dark-sider': appStore.siderTheme === 'dark' },
    `layout-${appStore.layout}`
  ]">
    <!-- Desktop Sidebar (Vertical & Columns) -->
    <template v-if="!isMobile && appStore.layout !== 'horizontal'">
      <!-- Columns Layout Narrow Sider -->
      <div v-if="appStore.layout === 'columns'" class="columns-narrow-sider" :class="appStore.siderTheme">
        <div class="logo-narrow">
          <img :src="appLogo" alt="logo" />
        </div>
        <div class="narrow-menu">
          <div 
            v-for="(route, idx) in rootRoutes" 
            :key="getRouteKey(route, idx)"
            class="narrow-menu-item"
            :class="{ active: activeRootPath === (route.path || route.meta?.title) }"
            @click="handleRootClick(route)"
          >
            <component :is="getIcon(route.meta?.icon) || DashboardOutlined" class="narrow-icon" />
            <span class="narrow-title">{{ route.meta?.title }}</span>
          </div>
        </div>
      </div>

      <!-- Main Sider -->
      <a-layout-sider 
        v-if="appStore.layout === 'vertical' || appStore.layout === 'columns'"
        v-model:collapsed="collapsed" 
        :trigger="null" 
        collapsible 
        class="app-sider"
        :theme="appStore.siderTheme" 
        :width="220"
        :style="{ display: appStore.layout === 'columns' && currentSubRoutes.length === 0 ? 'none' : 'block' }"
      >
        <div class="logo" v-if="appStore.layout === 'vertical' || appStore.layout === 'columns'">
          <img :src="appLogo" alt="logo" v-if="appStore.layout === 'vertical'" />
          <span v-if="!collapsed && appStore.layout === 'vertical'" class="title">{{ appTitle }}</span>
          <span v-if="!collapsed && appStore.layout === 'columns'" class="title columns-title">{{ activeRootTitle === '控制台' ? appTitle : activeRootTitle }}</span>
        </div>
        <SidebarMenu :collapsed="collapsed" :routes="currentSubRoutes" />
      </a-layout-sider>
    </template>

    <!-- Mobile Sidebar Drawer -->
    <a-drawer v-if="isMobile" :open="!collapsed" placement="left" :closable="false" class="mobile-sider-drawer" :width="260"
      @close="collapsed = true" :bodyStyle="{ padding: 0 }">
      <div class="app-sider mobile-sider">
        <div class="logo">
          <img :src="appLogo" alt="logo" />
          <span class="title">{{ appTitle }}</span>
        </div>
        <SidebarMenu :collapsed="false" @menu-click="collapsed = true" />
      </div>
    </a-drawer>

    <!-- Main -->
    <a-layout class="app-main-layout">
      <!-- Header -->
      <AppHeader v-model:collapsed="collapsed" />

      <!-- Tabs -->
      <AppTabs v-if="appStore.showTabs" />
      <!-- Content -->
      <a-layout-content class="app-content-wrapper">
        <router-view v-slot="{ Component, route }">
          <transition name="fade-transform" mode="out-in">
            <component :is="Component" :key="route.path" v-if="isRouterAlive" />
          </transition>
        </router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import { ref, provide, nextTick, onMounted, onBeforeUnmount, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import SidebarMenu from './components/SidebarMenu.vue'
import AppHeader from './components/AppHeader.vue'
import AppTabs from './components/AppTabs.vue'
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import { useDictStore } from '@/stores/modules/dict'
import type { AppRouteRecordRaw } from '@/stores/modules/permission'
import { DashboardOutlined } from '@ant-design/icons-vue'
import * as Icons from '@ant-design/icons-vue'

const appTitle = import.meta.env.VITE_APP_TITLE || 'Fast Admin Pro'
const appLogo = import.meta.env.VITE_APP_LOGO || '/favicon.svg'

const appStore = useAppStore()
const permissionStore = usePermissionStore()
const dictStore = useDictStore()
const route = useRoute()
const router = useRouter()

const collapsed = ref(false)
const isMobile = ref(false)
const isRouterAlive = ref(true)

// --- 分栏布局逻辑 ---
const activeRootPath = ref('')
const activeRootTitle = ref('')

const rootRoutes = computed(() => {
  const homeRoute: AppRouteRecordRaw = {
    name: 'Home',
    path: '/',
    component: 'Layout',
    meta: { title: '控制台', icon: 'DashboardOutlined', showLink: true }
  }
  return [homeRoute, ...permissionStore.routes.filter(r => r.meta?.showLink !== false)]
})

const currentSubRoutes = computed(() => {
  if (appStore.layout !== 'columns') {
    return permissionStore.routes
  }
  // 优先通过 path 匹配，如果没有找到则通过 title 匹配
  const root = rootRoutes.value.find(r => {
    const rPath = r.path || ''
    // path 不为空时，匹配 path
    if (rPath !== '') {
      return rPath === activeRootPath.value
    }
    // path 为空时，匹配 title
    return r.meta?.title === activeRootPath.value
  })
  return root?.children || []
})

const getRoutePath = (r: AppRouteRecordRaw) => {
  // 优先使用 path，确保不为空
  if (r.path && r.path.trim() !== '') {
    return r.path
  }
  // 其次使用 name
  if (r.name && (r.name as string).trim() !== '') {
    return r.name as string
  }
  // 最后使用 meta.title
  return r.meta?.title || ''
}

const getRouteKey = (r: AppRouteRecordRaw, idx: number) => {
  // 优先使用 path
  if (r.path && r.path.trim() !== '') {
    return r.path
  }
  // 其次使用 name
  if (r.name && (r.name as string).trim() !== '') {
    return r.name as string
  }
  // 最后使用 title + 索引
  return `${r.meta?.title || 'unnamed'}_${idx}`
}

const getIcon = (iconName?: string) => {
  if (!iconName) return null
  return (Icons as any)[iconName] || null
}

const handleRootClick = (r: AppRouteRecordRaw) => {
  // 如果 path 为空，使用 title 作为标识
  activeRootPath.value = r.path || r.meta?.title || ''
  activeRootTitle.value = r.meta?.title as string || ''
  
  // 如果有子路由，不直接跳转，只切换菜单
  // 如果没有子路由，直接跳转
  if (!r.children || r.children.length === 0) {
    router.push(r.path)
  } else {
    // 可以选择自动跳转到第一个子路由
    const firstChild = r.children.find(c => c.meta?.showLink !== false)
    if (firstChild) {
      router.push(firstChild.path)
    }
  }
}

// 监听路由和布局变化，更新 activeRootPath
watch([() => route.path, () => appStore.layout, () => permissionStore.isRoutesLoaded], ([newPath]) => {
  if (appStore.layout === 'columns') {
    // 处理首页的特殊情况
    if (newPath === '/') {
      activeRootPath.value = '/'
      activeRootTitle.value = '控制台'
      return
    }
    // 找到当前路由所属的根路由
    for (const r of rootRoutes.value) {
      const rPath = r.path || ''
      
      // 情况1：根路由有明确的 path，直接匹配
      if (rPath && rPath !== '') {
        if (newPath === rPath || newPath.startsWith(rPath + '/')) {
          activeRootPath.value = rPath
          activeRootTitle.value = r.meta?.title as string || ''
          break
        }
      } 
      // 情况2：根路由 path 为空（如系统管理），检查子路由
      else if (r.children && r.children.length > 0) {
        for (const child of r.children) {
          const childPath = child.path || ''
          if (childPath && (newPath === childPath || newPath.startsWith(childPath + '/'))) {
            // 使用 title 作为根路由的标识（因为 path 为空）
            activeRootPath.value = r.meta?.title || ''
            activeRootTitle.value = r.meta?.title as string || ''
            break
          }
        }
        // 如果已经找到匹配，跳出外层循环
        if (activeRootTitle.value === r.meta?.title) {
          break
        }
      }
    }
  }
}, { immediate: true })

// --- 基础逻辑 ---
const checkMobile = () => {
  const isNowMobile = window.innerWidth < 768
  if (isNowMobile !== isMobile.value) {
    isMobile.value = isNowMobile
    // 切换到移动端时默认折叠菜单
    if (isNowMobile) {
      collapsed.value = true
    } else {
      collapsed.value = false
    }
  }
}

onMounted(() => {
  checkMobile()
  window.addEventListener('resize', checkMobile)
  // 获取字典数据
  dictStore.fetchDicts()
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', checkMobile)
})

const reload = () => {
  isRouterAlive.value = false
  nextTick(() => {
    isRouterAlive.value = true
  })
}

provide('reload', reload)
provide('isMobile', isMobile)
</script>

<style scoped>
.app-layout {
  min-height: 100vh;
}

.app-sider {
  box-shadow: 2px 0 8px 0 rgba(15, 23, 42, 0.05);
  z-index: 10;
  border-right: 1px solid #f1f5f9;
  background: #fff;
}

.dark-sider .app-sider {
  background: #0f172a;
  border-right: none;
}

/* 分栏布局样式 */
.columns-narrow-sider {
  width: 80px;
  background: #fff;
  border-right: 1px solid #f1f5f9;
  display: flex;
  flex-direction: column;
  align-items: center;
  z-index: 11;
  box-shadow: 2px 0 8px 0 rgba(15, 23, 42, 0.05);
}

.columns-narrow-sider.dark {
  background: #0f172a;
  border-right: 1px solid #1e293b;
}

.logo-narrow {
  height: 64px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid #f1f5f9;
}

.columns-narrow-sider.dark .logo-narrow {
  border-bottom: 1px solid #1e293b;
}

.logo-narrow img {
  width: 32px;
  height: 32px;
}

.narrow-menu {
  flex: 1;
  width: 100%;
  padding: 12px 0;
  overflow-y: auto;
  overflow-x: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.narrow-menu::-webkit-scrollbar {
  width: 0;
}

.narrow-menu-item {
  width: 64px;
  height: 64px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #64748b;
  transition: all 0.3s;
}

.columns-narrow-sider.dark .narrow-menu-item {
  color: #94a3b8;
}

.narrow-menu-item:hover {
  color: var(--app-primary-color);
  background: #f1f5f9;
}

.columns-narrow-sider.dark .narrow-menu-item:hover {
  color: #fff;
  background: rgba(255, 255, 255, 0.08);
}

.narrow-menu-item.active {
  color: #fff;
  background: var(--app-primary-color);
  box-shadow: 0 4px 12px var(--app-primary-color-light);
}

.columns-narrow-sider.dark .narrow-menu-item.active {
  color: #fff;
  background: var(--app-primary-color);
}

.narrow-icon {
  font-size: 20px;
  margin-bottom: 4px;
}

.narrow-title {
  font-size: 12px;
  text-align: center;
  white-space: nowrap;
  transform: scale(0.9);
}

.columns-sider-header {
  height: 64px;
  display: flex;
  align-items: center;
  padding: 0 16px;
  border-bottom: 1px solid #f1f5f9;
}

.columns-sider-header .title {
  font-size: 16px;
  font-weight: 700;
  color: #334155;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.dark-sider .columns-sider-header {
  border-bottom: 1px solid #1e293b;
}

.dark-sider .columns-sider-header .title {
  color: #fff;
}

.columns-title {
  margin-left: 0 !important;
  font-size: 16px !important;
  color: #334155 !important;
}

.dark-sider .columns-title {
  color: #fff !important;
}

.dark-sider .logo {
  border-bottom: 1px solid #1e293b;
}

.dark-sider .logo .title {
  color: #fff;
}

.mobile-sider {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

:deep(.mobile-sider-drawer .ant-drawer-body) {
  padding: 0;
}

.app-main-layout {
  background: #f8fafc;
  display: flex;
  flex-direction: column;
}

html.dark .app-main-layout {
  background: #0f172a;
}

.logo {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 16px;
  overflow: hidden;
  transition: all 0.3s;
  border-bottom: 1px solid #f1f5f9;
}

.logo img {
  width: 32px;
  height: 32px;
}

.logo .title {
  margin-left: 12px;
  font-size: 18px;
  font-weight: 700;
  color: #6366f1;
  white-space: nowrap;
  letter-spacing: 0.5px;
}

.app-content-wrapper {
  padding: 16px;
  /* flex: 1; */
  height: calc(100vh - 64px - 48px);
  overflow-y: auto;
}



/* 路由切换动画 */
.fade-transform-leave-active,
.fade-transform-enter-active {
  transition: all 0.3s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(-20px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(20px);
}
</style>
