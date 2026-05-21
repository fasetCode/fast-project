<template>
  <div class="menu-container">
    <!-- 增加一个侧边栏装饰背景，提升层次感 -->
    <div class="sidebar-decoration"></div>
  <!-- 菜单 -->
    <a-menu
      v-model:selectedKeys="selectedKeys"
      v-model:openKeys="openKeys"
      mode="inline"
      :theme="appStore.siderTheme"
      :inline-indent="16"
      :class="['sidebar-menu', { 'is-collapsed': collapsed }]"
      @click="() => emit('menu-click')"
    >
   
      <a-menu-item key="/" v-if="showHome">
        <template #icon>
          <div class="icon-wrapper glass-icon">
            <DashboardOutlined />
          </div>
        </template>
        <router-link to="/">
          <span class="menu-title">控制台</span>
        </router-link>
      </a-menu-item>
       
      <template v-for="(route, idx) in dynamicRoutes">
        <!-- 有子路由的情况 -->
        <a-sub-menu v-if="route.children && route.children.length > 0 && route.meta?.showLink !== false" :key="getRouteKey(route, idx)" popupClassName="pro-sub-menu-popup">
          <template #icon>
            <div class="icon-wrapper glass-icon">
              <component :is="getIcon(route.meta?.icon) || SettingOutlined" />
            </div>
          </template>
          <template #title>
            <span class="menu-title">{{ route.meta?.title }}</span>
          </template>
          
          <template v-for="(child, childIdx) in route.children" :key="getRouteKey(child, childIdx, getRouteKey(route, idx))">
            <a-menu-item v-if="child.meta?.showLink !== false" :key="getRouteKey(child, childIdx, getRouteKey(route, idx))">
              <template #icon>
                <component :is="getIcon(child.meta?.icon) || SafetyCertificateOutlined" class="sub-icon" />
              </template>
              <router-link :to="child.path">
                {{ child.meta?.title }}
              </router-link>
            </a-menu-item>
          </template>
        </a-sub-menu>
        
        <!-- 没有子路由的情况 -->
        <a-menu-item v-else-if="route.meta?.showLink !== false && route.path !== '/'" :key="getRouteKey(route, idx)">
          <template #icon>
            <div class="icon-wrapper glass-icon">
              <component :is="getIcon(route.meta?.icon) || DashboardOutlined" />
            </div>
          </template>
          <router-link :to="route.path">
            <span class="menu-title">{{ route.meta?.title }}</span>
          </router-link>
        </a-menu-item>
      </template>
 
    </a-menu>
 
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import { usePermissionStore } from '@/stores/modules/permission'
import type { AppRouteRecordRaw } from '@/stores/modules/permission'
import * as Icons from '@ant-design/icons-vue'
import { 
  DashboardOutlined, 
  SettingOutlined,
  SafetyCertificateOutlined
} from '@ant-design/icons-vue'
import { useAppStore } from '@/stores/modules/app'

const props = withDefaults(defineProps<{
  collapsed: boolean
  routes?: AppRouteRecordRaw[]
}>(), {
  routes: undefined
})

const emit = defineEmits<{
  (e: 'menu-click'): void
}>()

const route = useRoute()
const appStore = useAppStore()
const permissionStore = usePermissionStore()
const selectedKeys = ref<string[]>([])
const openKeys = ref<string[]>([])

// 获取动态路由数据
const dynamicRoutes = computed(() => {
  return props.routes !== undefined ? props.routes : permissionStore.routes
})

// 是否显示硬编码的首页（如果是分栏布局且传递了特定的 routes，或者不在根路由，则可能不需要。简单起见，如果传了 routes 就不显示，除非需要）
const showHome = computed(() => {
  if (appStore.layout === 'columns' && props.routes !== undefined) {
    return false
  }
  return true
})

// 动态获取图标
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
  if (r.name && r.name.trim() !== '') {
    return r.name
  }
  // 最后使用 meta.title 和索引的组合
  const title = r.meta?.title || 'unnamed'
  return `${parentKey}_${title}_${idx}`
}

const findOpenKeys = (
  routes: AppRouteRecordRaw[],
  targetPath: string,
  parentKeys: string[] = [],
  parentKey = 'root'
): string[] | null => {
  for (let i = 0; i < routes.length; i++) {
    const r = routes[i]
    const key = getRouteKey(r, i, parentKey)

    if (r.path === targetPath) {
      return parentKeys
    }

    if (r.children && r.children.length > 0) {
      const res = findOpenKeys(r.children, targetPath, [...parentKeys, key], key)
      if (res) return res
    }
  }
  return null
}

const updateMenuState = () => {
  selectedKeys.value = [route.path]
  
  if (!props.collapsed) {
    const keys = findOpenKeys(dynamicRoutes.value, route.path)
    openKeys.value = keys || []
  } else {
    openKeys.value = []
  }
}

// 监听路由变化，并确保在下一次 DOM 更新后选中
watch(
  () => route.path,
  () => {
    setTimeout(() => {
      updateMenuState()
    }, 0)
  },
  { immediate: true }
)

watch(
  () => props.collapsed,
  (val) => {
    if (val) {
      openKeys.value = []
    } else {
      updateMenuState()
    }
  }
)

onMounted(() => {
  // 如果 permissionStore 中的 routes 加载完毕，再次执行
  watch(() => permissionStore.isRoutesLoaded, (loaded) => {
    if (loaded) {
      setTimeout(() => {
        updateMenuState()
      }, 50)
    }
  }, { immediate: true })
})
</script>

<style scoped>
.menu-container {
  height: calc(100vh - 64px);
  display: flex;
  flex-direction: column;
  background: transparent;
  position: relative;
  overflow: hidden;
}

/* 侧边栏右上角的环境光晕装饰 */
.sidebar-decoration {
  position: absolute;
  top: -50px;
  right: -50px;
  width: 150px;
  height: 150px;
  background: radial-gradient(circle, var(--app-primary-color-light) 0%, rgba(255,255,255,0) 70%);
  border-radius: 50%;
  pointer-events: none;
  z-index: 0;
}

.sidebar-menu {
  flex: 1;
  border-right: none;
  background: transparent;
  padding: 12px 0;
  overflow-y: auto;
  overflow-x: hidden;
  z-index: 1;
}

/* 优雅的悬浮滚动条 */
.sidebar-menu::-webkit-scrollbar {
  width: 4px;
}
.sidebar-menu::-webkit-scrollbar-thumb {
  background: transparent;
  border-radius: 4px;
  transition: background 0.3s;
}
.sidebar-menu:hover::-webkit-scrollbar-thumb {
  background: rgba(148, 163, 184, 0.3);
}

/* ================= 分组标题 ================= */
.menu-group-title {
  padding: 16px 24px 8px;
  font-size: 12px;
  font-weight: 600;
  color: #94a3b8;
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: opacity 0.3s;
}

/* ================= 基础容器样式 ================= */
:deep(.ant-menu-item),
:deep(.ant-menu-submenu-title) {
  border-radius: 12px !important;
  margin-top: 4px !important;
  margin-bottom: 4px !important;
  height: 50px !important;
  line-height: 50px !important;
  transition: all 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
  overflow: hidden;
}

/* 展开状态留白 */
.sidebar-menu:not(.is-collapsed) :deep(.ant-menu-item),
.sidebar-menu:not(.is-collapsed) :deep(.ant-menu-submenu-title) {
  margin-inline: 16px !important;
  width: calc(100% - 32px) !important;
}

/* 折叠状态居中 */
.sidebar-menu.is-collapsed :deep(.ant-menu-item),
.sidebar-menu.is-collapsed :deep(.ant-menu-submenu-title) {
  margin-inline: 12px !important;
  width: calc(100% - 24px) !important;
  padding: 0 calc(50% - 16px) !important;
}

/* ================= 字体与排版 ================= */
.menu-title {
  font-weight: 600;
  font-size: 14px;
}

:deep(.ant-menu-item),
:deep(.ant-menu-submenu-title) {
  color: #475569;
}

:deep(.ant-menu-title-content) {
  display: flex;
  align-items: center;
  width: 100%;
}
:deep(.ant-menu-title-content a) {
  color: inherit;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

/* ================= 装饰元素 (Badge / Tag) ================= */
.tag-new {
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 4px;
  background: linear-gradient(135deg, #f59e0b 0%, #ea580c 100%);
  color: white;
  font-weight: bold;
  margin-left: auto;
  line-height: 1;
  box-shadow: 0 2px 4px rgba(245, 158, 11, 0.3);
}

/* ================= 图标容器 (拟物化玻璃态) ================= */
.icon-wrapper {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 34px;
  height: 34px;
  border-radius: 10px;
  margin-right: 6px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
}

/* 未选中的玻璃质感 */
.glass-icon {
  background: rgba(241, 245, 249, 0.8);
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow: inset 0 2px 4px rgba(255, 255, 255, 0.8),
              0 1px 2px rgba(0, 0, 0, 0.02);
  color: #64748b;
}

:deep(.ant-menu-item .anticon),
:deep(.ant-menu-submenu-title .anticon) {
  font-size: 16px;
  transition: all 0.3s;
}

.sub-icon {
  font-size: 15px !important;
  opacity: 0.6;
}

/* 折叠时去除背景，回归极简 */
.sidebar-menu.is-collapsed .icon-wrapper {
  background: transparent;
  border: none;
  box-shadow: none;
  width: auto;
  height: auto;
}

/* ================= 交互状态 (亮色菜单) ================= */

/* 1. 悬浮态 (平稳过渡) */
:deep(.ant-menu-light .ant-menu-item:not(.ant-menu-item-selected):hover),
:deep(.ant-menu-light .ant-menu-submenu-title:hover) {
  color: #0f172a !important;
  background-color: #f1f5f9 !important;
}

:deep(.ant-menu-light .ant-menu-item:not(.ant-menu-item-selected):hover .glass-icon),
:deep(.ant-menu-light .ant-menu-submenu-title:hover .glass-icon) {
  background: #ffffff;
  color: var(--app-primary-color);
  box-shadow: inset 0 2px 4px rgba(255, 255, 255, 1),
              0 2px 6px var(--app-primary-color-light);
}

/* ================= 交互状态 (暗色菜单) ================= */

/* 1. 悬浮态 (平稳过渡) */
:deep(.ant-menu-dark .ant-menu-item:not(.ant-menu-item-selected):hover),
:deep(.ant-menu-dark .ant-menu-submenu-title:hover) {
  color: #ffffff !important;
  background-color: rgba(255, 255, 255, 0.08) !important;
}

:deep(.ant-menu-dark .ant-menu-item:not(.ant-menu-item-selected):hover .glass-icon),
:deep(.ant-menu-dark .ant-menu-submenu-title:hover .glass-icon) {
  background: rgba(255, 255, 255, 0.1);
  color: #ffffff;
  box-shadow: inset 0 1px 1px rgba(255, 255, 255, 0.2);
  border-color: rgba(255, 255, 255, 0.2);
}

/* 暗色菜单的基础文字和图标颜色 */
:deep(.ant-menu-dark .ant-menu-item),
:deep(.ant-menu-dark .ant-menu-submenu-title) {
  color: #94a3b8;
}

:deep(.ant-menu-dark .glass-icon) {
  background: rgba(255, 255, 255, 0.04);
  border: 1px solid rgba(255, 255, 255, 0.05);
  box-shadow: none;
  color: #94a3b8;
}

/* ================= 选中态 (亮暗通用，苹果风格高亮) ================= */
:deep(.ant-menu-item-selected) {
  color: #ffffff !important;
  background: linear-gradient(135deg, var(--app-primary-color-gradient-end) 0%, var(--app-primary-color) 100%) !important;
  font-weight: 600;
  box-shadow: 0 8px 16px -4px var(--app-primary-color-light),
              inset 0 1px 0 rgba(255, 255, 255, 0.2);
}

:deep(.ant-menu-item-selected .icon-wrapper) {
  background: rgba(255, 255, 255, 0.15);
  border: 1px solid rgba(255, 255, 255, 0.1);
  color: #ffffff;
  box-shadow: inset 0 2px 4px rgba(255, 255, 255, 0.2);
}

:deep(.ant-menu-item-selected .anticon) {
  color: #ffffff !important;
  transform: scale(1.1);
}

:deep(.ant-menu-item-selected .sub-icon) {
  opacity: 1;
}

/* ================= 子菜单微调 ================= */
:deep(.ant-menu-sub.ant-menu-inline) {
  background: transparent !important;
  border: none !important;
  padding-bottom: 8px;
}

:deep(.ant-menu-sub.ant-menu-inline .ant-menu-item) {
  height: 44px !important;
  line-height: 44px !important;
  border-radius: 8px !important;
}

:deep(.ant-menu-light .ant-menu-sub.ant-menu-inline .ant-menu-item:not(.ant-menu-item-selected):hover) {
  transform: none;
  background-color: #f1f5f9 !important;
  color: var(--app-primary-color) !important;
}

:deep(.ant-menu-dark .ant-menu-sub.ant-menu-inline .ant-menu-item:not(.ant-menu-item-selected):hover) {
  transform: none;
  background-color: rgba(255, 255, 255, 0.08) !important;
  color: #ffffff !important;
}



/* ================= 底部升级卡片 (点睛之笔) ================= */
.sidebar-footer {
  padding: 16px;
  background: #ffffff;
  border-top: 1px solid rgba(0,0,0,0.02);
  z-index: 2;
}

.upgrade-card {
  background: linear-gradient(145deg, #f8fafc 0%, #f1f5f9 100%);
  border: 1px solid rgba(255,255,255,0.8);
  border-radius: 16px;
  padding: 16px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0,0,0,0.03);
  transition: transform 0.3s;
}

.upgrade-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(0,0,0,0.06);
}

/* 卡片光效 */
.upgrade-card::after {
  content: '';
  position: absolute;
  top: -50%;
  right: -50%;
  width: 100px;
  height: 100px;
  background: radial-gradient(circle, rgba(245, 158, 11, 0.15) 0%, rgba(255,255,255,0) 70%);
  border-radius: 50%;
}

.upgrade-icon {
  width: 32px;
  height: 32px;
  border-radius: 10px;
  background: linear-gradient(135deg, #fbbf24 0%, #d97706 100%);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  margin-bottom: 12px;
  box-shadow: 0 4px 8px rgba(217, 119, 6, 0.3);
}

.upgrade-content h4 {
  margin: 0 0 4px 0;
  font-size: 14px;
  font-weight: 700;
  color: #1e293b;
}

.upgrade-content p {
  margin: 0 0 12px 0;
  font-size: 12px;
  color: #64748b;
}

.upgrade-btn {
  width: 100%;
  border-radius: 8px;
  background: #0f172a;
  border: none;
  font-weight: 600;
  box-shadow: 0 2px 4px rgba(15, 23, 42, 0.2);
}

.upgrade-btn:hover {
  background: #1e293b;
}
</style>
