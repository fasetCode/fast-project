<template>
  <a-layout-header class="app-header" :class="[appStore.layout === 'horizontal' ? (appStore.siderTheme === 'dark' ? 'header-dark' : 'header-light') : '']">
    <div class="header-left">
      <!-- Horizontal Layout Logo -->
      <div class="logo-horizontal" v-if="appStore.layout === 'horizontal' && !isMobile">
        <img :src="appLogo" alt="logo" />
        <span class="title">{{ appTitle }}</span>
      </div>

      <span class="trigger" @click="emit('update:collapsed', !props.collapsed)" v-if="appStore.layout !== 'horizontal' || isMobile">
        <MenuUnfoldOutlined v-if="props.collapsed" />
        <MenuFoldOutlined v-else />
      </span>
      <a-breadcrumb class="breadcrumb" v-if="!isMobile && appStore.layout !== 'horizontal'">
        <a-breadcrumb-item>
          <router-link to="/">
            <HomeOutlined />
            <span>首页</span>
          </router-link>
        </a-breadcrumb-item>
        <template v-for="(item, index) in breadcrumbList" :key="item.path">
          <a-breadcrumb-item v-if="item.meta?.title && item.meta?.title !== '首页'">
            <span v-if="index === breadcrumbList.length - 1">{{ item.meta.title }}</span>
            <router-link v-else :to="item.path">{{ item.meta.title }}</router-link>
          </a-breadcrumb-item>
        </template>
      </a-breadcrumb>
    </div>

    <!-- Horizontal Menu -->
    <div class="header-menu" v-if="appStore.layout === 'horizontal' && !isMobile">
      <a-menu
        v-model:selectedKeys="selectedKeys"
        v-model:openKeys="openKeys"
        mode="horizontal"
        :theme="appStore.siderTheme"
        class="horizontal-menu"
      >
        <a-menu-item key="/">
          <template #icon>
            <DashboardOutlined />
          </template>
          <router-link to="/">控制台</router-link>
        </a-menu-item>
        
        <template v-for="(route, idx) in dynamicRoutes">
          <a-sub-menu v-if="route.children && route.children.length > 0 && route.meta?.showLink !== false" :key="getRouteKey(route, idx)">
            <template #icon>
              <component :is="getIcon(route.meta?.icon) || SettingOutlined" />
            </template>
            <template #title>
              <span>{{ route.meta?.title }}</span>
            </template>
            
            <template v-for="(child, childIdx) in route.children" :key="getRouteKey(child, childIdx, getRouteKey(route, idx))">
              <a-menu-item v-if="child.meta?.showLink !== false" :key="getRouteKey(child, childIdx, getRouteKey(route, idx))">
                <template #icon>
                  <component :is="getIcon(child.meta?.icon) || SafetyCertificateOutlined" />
                </template>
                <router-link :to="child.path">{{ child.meta?.title }}</router-link>
              </a-menu-item>
            </template>
          </a-sub-menu>
          
          <a-menu-item v-else-if="route.meta?.showLink !== false && route.path !== '/'" :key="getRouteKey(route, idx)">
            <template #icon>
              <component :is="getIcon(route.meta?.icon) || DashboardOutlined" />
            </template>
            <router-link :to="route.path">{{ route.meta?.title }}</router-link>
          </a-menu-item>
        </template>
      </a-menu>
    </div>

    <div class="header-right">
      <a-tooltip placement="bottom" title="搜索" v-if="!isMobile">
        <span class="header-action" role="button" tabindex="0" @click="openSearch" @keydown.enter="openSearch">
          <SearchOutlined />
        </span>
      </a-tooltip>
      <a-tooltip placement="bottom" title="搜索" v-else>
        <span class="header-action-mobile" @click="openSearch">
          <SearchOutlined />
        </span>
      </a-tooltip>
      
<!--      <a-tooltip placement="bottom" title="消息通知">-->
<!--        <span class="header-action">-->
<!--          <a-badge dot>-->
<!--            <BellOutlined />-->
<!--          </a-badge>-->
<!--        </span>-->
<!--      </a-tooltip>-->
<!--      <a-tooltip placement="bottom" title="项目仓库" v-if="!isMobile">-->
<!--        <span class="header-action"><GithubOutlined /></span>-->
<!--      </a-tooltip>-->

      <a-dropdown>
        <span class="user-action">
          <a-avatar size="small" :src="userAvatar" :style="{ backgroundColor: appStore.themeColor }">
            <template #icon><UserOutlined /></template>
          </a-avatar>
          <span class="username" v-if="!isMobile">{{ displayUsername }}</span>
          <DownOutlined class="dropdown-icon" v-if="!isMobile" />
        </span>
        <template #overlay>
          <a-menu class="user-dropdown-menu" @click="handleMenuClick">
            <a-menu-item key="profile">
              <UserOutlined /> 个人中心
            </a-menu-item>
            <a-menu-item key="settings">
              <SettingOutlined /> 偏好设置
            </a-menu-item>
            <a-menu-divider />
            <a-menu-item key="logout" class="logout-item">
              <LogoutOutlined /> 退出登录
            </a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
    </div>
    
    <AppSettings ref="settingsRef" />
  </a-layout-header>

  <a-modal
    v-model:open="isSearchOpen"
    class="route-search-modal"
    title="搜索"
    :footer="null"
    :destroyOnClose="true"
    @cancel="closeSearch"
  >
    <a-input
      ref="searchInputRef"
      v-model:value="keyword"
      placeholder="输入路由标题或路径"
      allow-clear
      @pressEnter="onEnter"
    />
    <a-list class="search-result-list" :data-source="filteredItems" size="small">
      <template #renderItem="{ item }">
        <a-list-item class="search-result-item" @click="go(item.path)">
          <a-list-item-meta :title="item.title" :description="item.path" />
        </a-list-item>
      </template>
    </a-list>
  </a-modal>
</template>

<script setup lang="ts">
import { computed, nextTick, onBeforeUnmount, onMounted, ref, inject, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import {
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  UserOutlined,
  HomeOutlined,
  SearchOutlined,
  BellOutlined,
  GithubOutlined,
  DownOutlined,
  SettingOutlined,
  LogoutOutlined,
  DashboardOutlined,
  SafetyCertificateOutlined
} from '@ant-design/icons-vue'
import * as Icons from '@ant-design/icons-vue'
import AppSettings from './AppSettings.vue'
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import { logout, getUserInfo } from '@/api/system/auth.ts'
import TokenService from '@/utils/token'
import type { AppRouteRecordRaw } from '@/stores/modules/permission'
import type { UserInfoVo } from '@/api/system/auth.ts'

const appTitle = import.meta.env.VITE_APP_TITLE || 'Fast Admin Pro'
const appLogo = import.meta.env.VITE_APP_LOGO || '/favicon.svg'

const props = withDefaults(
  defineProps<{
    collapsed: boolean
    username?: string
  }>(),
  {
    username: '管理员',
  }
)

const emit = defineEmits<{
  (e: 'update:collapsed', value: boolean): void
}>()

const route = useRoute()
const router = useRouter()
const appStore = useAppStore()
const permissionStore = usePermissionStore()
const isMobile = inject('isMobile', ref(false))

// 当前登录用户信息
const userInfo = ref<UserInfoVo | null>(null)

// 用户名（优先使用接口返回的昵称，其次使用 props 传入的 username）
const displayUsername = computed(() => {
  return userInfo.value?.nickname || userInfo.value?.username || props.username
})

// 用户头像
const userAvatar = computed(() => {
  return userInfo.value?.avatar || ''
})

// --- 水平菜单逻辑 ---
const selectedKeys = ref<string[]>([])
const openKeys = ref<string[]>([])

const dynamicRoutes = computed(() => permissionStore.routes)

const getIcon = (iconName?: string) => {
  if (!iconName) return null
  return (Icons as any)[iconName] || null
}

const getRouteKey = (r: AppRouteRecordRaw, idx: number, parentKey = 'root') => {
  // 优先使用 path，确保 key 的唯一性和稳定性
  if (r.path && r.path.trim() !== '') {
    return r.path
  }
  // 其次使用 name
  if (r.name && (r.name as string).trim() !== '') {
    return r.name as string
  }
  // 最后使用 meta.title 和索引的组合
  const title = r.meta?.title || 'unnamed'
  return `${parentKey}_${title}_${idx}`
}

watch(
  () => route.path,
  () => {
    selectedKeys.value = [route.path]
  },
  { immediate: true }
)
// ------------------

const breadcrumbList = computed(() => {
  if (!appStore.showBreadcrumb) return []
  return route.matched.filter(item => item.meta && item.meta.title && item.name !== 'Layout')
})

const isSearchOpen = ref(false)
const keyword = ref('')
const searchInputRef = ref<any>(null)
const settingsRef = ref<any>(null)

const handleMenuClick = async ({ key }: { key: string }) => {
  if (key === 'profile') {
    router.push('/personal')
  } else if (key === 'settings') {
    settingsRef.value?.open()
  } else if (key === 'logout') {
    try {
      await logout()
      TokenService.removeToken()
      message.success({ content: '已安全退出', class: 'custom-message' })
      router.push('/login')
    } catch (error) {
      console.error('退出登录失败:', error)
      TokenService.removeToken()
      router.push('/login')
    }
  }
}

type RouteSearchItem = {
  path: string
  title: string
}

const allItems = computed<RouteSearchItem[]>(() => {
  const seen = new Set<string>()
  const routes = router
    .getRoutes()
    .filter((r) => !!r.path)
    .filter((r) => r.meta?.showLink !== false)
    .filter((r) => r.meta?.title)
    .map((r) => ({
      path: r.path,
      title: String(r.meta?.title),
    }))
    .filter((r) => {
      const key = `${r.title}__${r.path}`
      if (seen.has(key)) return false
      seen.add(key)
      return true
    })
    .sort((a, b) => a.title.localeCompare(b.title, 'zh-Hans-CN'))

  return routes
})

const filteredItems = computed<RouteSearchItem[]>(() => {
  const k = keyword.value.trim().toLowerCase()
  const items = allItems.value
  if (!k) return items.slice(0, 20)
  return items
    .filter((i) => i.title.toLowerCase().includes(k) || i.path.toLowerCase().includes(k))
    .slice(0, 20)
})

const openSearch = async () => {
  isSearchOpen.value = true
  await nextTick()
  searchInputRef.value?.focus?.()
}

const closeSearch = () => {
  isSearchOpen.value = false
  keyword.value = ''
}

const go = (path: string) => {
  closeSearch()
  router.push(path)
}

const onEnter = () => {
  const first = filteredItems.value[0]
  if (!first) return
  go(first.path)
}

const onGlobalKeydown = (e: KeyboardEvent) => {
  const key = e.key.toLowerCase()
  if ((e.ctrlKey || e.metaKey) && key === 'k') {
    e.preventDefault()
    openSearch()
    return
  }
  if (e.key === 'Escape' && isSearchOpen.value) {
    e.preventDefault()
    closeSearch()
  }
}

// 加载用户信息
const loadUserInfo = async () => {
  try {
    const res = await getUserInfo()
    if (res.data) {
      userInfo.value = res.data
    }
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

onMounted(() => {
  window.addEventListener('keydown', onGlobalKeydown)
  loadUserInfo()
})

onBeforeUnmount(() => {
  window.removeEventListener('keydown', onGlobalKeydown)
})
</script>

<style scoped>
.app-header {
  background: #fff;
  padding: 0 24px 0 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
  z-index: 9;
  height: 64px;
}

html.dark .app-header {
  background: #1e293b;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
}

html.dark .header-action,
html.dark .header-action-mobile,
html.dark .trigger {
  color: #94a3b8;
}

html.dark .username {
  color: #e2e8f0;
}

.header-dark {
  background: #0f172a !important;
  color: #e2e8f0;
}

.header-dark .header-action,
.header-dark .trigger,
.header-dark .username {
  color: #e2e8f0 !important;
}

.header-dark .header-action:hover,
.header-dark .trigger:hover,
.header-dark .user-action:hover {
  background: rgba(255, 255, 255, 0.08) !important;
}

.logo-horizontal {
  display: flex;
  align-items: center;
  padding: 0 24px;
  cursor: pointer;
}

.logo-horizontal img {
  width: 32px;
  height: 32px;
}

.logo-horizontal .title {
  margin-left: 12px;
  font-size: 18px;
  font-weight: 700;
  color: var(--app-primary-color);
  white-space: nowrap;
}

.header-dark .logo-horizontal .title {
  color: #fff;
}

.header-menu {
  flex: 1;
  min-width: 0;
  padding: 0 24px;
}

.horizontal-menu {
  line-height: 64px;
  border-bottom: none !important;
  background: transparent;
}

.horizontal-menu :deep(.ant-menu-item),
.horizontal-menu :deep(.ant-menu-submenu-title) {
  padding: 0 16px;
}

.header-left {
  display: flex;
  align-items: center;
}

.trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: all 0.3s;
  display: inline-flex;
  align-items: center;
}

.trigger:hover {
  color: var(--app-primary-color);
  background: rgba(0, 0, 0, 0.025);
}

.breadcrumb {
  margin-left: 8px;
  display: flex;
  align-items: center;
}

.header-right {
  display: flex;
  align-items: center;
  height: 100%;
}

.header-action {
  cursor: pointer;
  padding: 0 12px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 64px;
  font-size: 18px;
  color: #515a6e;
  transition: all 0.3s;
}

.header-action-mobile {
  cursor: pointer;
  padding: 0 8px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 64px;
  font-size: 18px;
  color: #515a6e;
}

.header-action:hover {
  background: rgba(0, 0, 0, 0.025);
  color: var(--app-primary-color);
}

.user-action {
  cursor: pointer;
  padding: 0 16px;
  display: flex;
  align-items: center;
  height: 64px;
  gap: 8px;
  transition: all 0.3s;
}

.user-action:hover {
  background: rgba(0, 0, 0, 0.025);
}

.username {
  color: #333;
  font-weight: 500;
  font-size: 14px;
}

.dropdown-icon {
  font-size: 12px;
  color: #999;
}

.user-dropdown-menu {
  width: 150px;
}

.user-dropdown-menu .anticon {
  margin-right: 8px;
}

.logout-item {
  color: #ff4d4f;
}

.logout-item:hover {
  color: #ff4d4f !important;
  background-color: #fff2f0 !important;
}

.search-result-list {
  margin-top: 12px;
}

.search-result-item {
  cursor: pointer;
  border-radius: 8px;
  padding-inline: 12px;
}

.search-result-item:hover {
  background: rgba(0, 0, 0, 0.025);
}
</style>
