<template>
  <div class="tabs-bar">
    <a-tabs
      v-model:activeKey="activeKey"
      type="editable-card"
      hide-add
      class="tabs"
      @change="onChange"
      @edit="onEdit"
    >
      <a-tab-pane v-for="t in tabs" :key="t.key" :closable="t.closable">
        <template #tab>
          <a-dropdown :trigger="['contextmenu']">
            <span class="tab-title" @contextmenu="onContextMenu($event)">
              {{ t.title }}
            </span>
            <template #overlay>
              <a-menu @click="(menuInfo: any) => handleMenuClick(menuInfo.key, t)">
                <a-menu-item key="refresh" :disabled="activeKey !== t.key">
                  <ReloadOutlined /> 重新加载
                </a-menu-item>
                <a-menu-item key="close" :disabled="!t.closable">
                  <CloseOutlined /> 关闭当前
                </a-menu-item>
                <a-menu-divider />
                <a-menu-item key="close-left" :disabled="isFirst(t)">
                  <VerticalRightOutlined /> 关闭左侧
                </a-menu-item>
                <a-menu-item key="close-right" :disabled="isLast(t)">
                  <VerticalLeftOutlined /> 关闭右侧
                </a-menu-item>
                <a-menu-divider />
                <a-menu-item key="close-other" :disabled="tabs.length <= 1">
                  <CloseSquareOutlined /> 关闭其他
                </a-menu-item>
                <a-menu-item key="close-all" :disabled="tabs.length <= 1">
                  <MinusSquareOutlined /> 关闭全部
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </template>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, inject } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  ReloadOutlined,
  CloseOutlined,
  VerticalRightOutlined,
  VerticalLeftOutlined,
  CloseSquareOutlined,
  MinusSquareOutlined,
} from '@ant-design/icons-vue'

type TabItem = {
  key: string
  path: string
  title: string
  closable: boolean
}

const STORAGE_KEY = 'admin_vue_tabs'

const router = useRouter()
const route = useRoute()
const reload = inject('reload') as () => void

const homeTab: TabItem = {
  key: '/',
  path: '/',
  title: '首页',
  closable: false,
}

const tabs = ref<TabItem[]>([homeTab])
const activeKey = ref<string>(route.fullPath || route.path || '/')

const normalizeRoute = () => {
  const title = route.meta?.title
  const showLink = route.meta?.showLink
  const isNotFound =
    route.name === 'NotFound' ||
    route.meta?.title === '404' ||
    String(route.path || '').includes(':pathMatch')

  if (!title || showLink === false || isNotFound) return null

  const key = String(route.fullPath || route.path)
  return {
    key,
    path: String(route.fullPath || route.path),
    title: String(title),
    closable: key !== '/',
  } satisfies TabItem
}

const persist = () => {
  const payload = {
    tabs: tabs.value,
    activeKey: activeKey.value,
  }
  sessionStorage.setItem(STORAGE_KEY, JSON.stringify(payload))
}

const restore = () => {
  try {
    const raw = sessionStorage.getItem(STORAGE_KEY)
    if (!raw) return
    const parsed = JSON.parse(raw) as { tabs?: TabItem[]; activeKey?: string }
    if (Array.isArray(parsed.tabs) && parsed.tabs.length > 0) {
      const merged = [homeTab]
      for (const t of parsed.tabs) {
        if (!t || typeof t.key !== 'string') continue
        if (t.key === '/') continue
        merged.push({
          key: t.key,
          path: typeof t.path === 'string' ? t.path : t.key,
          title: typeof t.title === 'string' ? t.title : t.key,
          closable: t.key !== '/',
        })
      }
      tabs.value = merged
    }
    if (typeof parsed.activeKey === 'string') {
      activeKey.value = parsed.activeKey
    }
  } catch {
    sessionStorage.removeItem(STORAGE_KEY)
  }
}

restore()

watch(
  () => route.fullPath,
  () => {
    const next = normalizeRoute()
    if (!next) {
      activeKey.value = String(route.fullPath || route.path || '/')
      persist()
      return
    }
    
    activeKey.value = next.key
    const exists = tabs.value.some((t) => t.key === next.key)
    if (!exists) tabs.value.push(next)
    persist()
  },
  { immediate: true }
)

const findTab = (key: string) => tabs.value.find((t) => t.key === key)

const onChange = (key: string) => {
  const t = findTab(key)
  if (!t) return
  router.push(t.path)
}

const onEdit = (targetKey: string | MouseEvent, action: 'add' | 'remove') => {
  if (action !== 'remove') return
  const key = String(targetKey)
  removeTab(key)
}

const removeTab = (key: string) => {
  const idx = tabs.value.findIndex((t) => t.key === key)
  if (idx <= 0) return

  const isActive = activeKey.value === key
  tabs.value.splice(idx, 1)

  if (isActive) {
    const fallback = tabs.value[idx - 1] || tabs.value[0] || homeTab
    activeKey.value = fallback.key
    router.push(fallback.path)
  }
  persist()
}

const isFirst = (t: TabItem) => {
  const idx = tabs.value.findIndex((item) => item.key === t.key)
  return idx <= 1 // index 0 is always homeTab, so index 1 is the first closable tab
}

const isLast = (t: TabItem) => {
  const idx = tabs.value.findIndex((item) => item.key === t.key)
  return idx === tabs.value.length - 1
}

const onContextMenu = (e: MouseEvent) => {
  e.preventDefault()
}

const handleMenuClick = (key: string | number, t: TabItem) => {
  const targetIdx = tabs.value.findIndex((item) => item.key === t.key)
  if (targetIdx === -1) return

  switch (key) {
    case 'refresh':
      if (activeKey.value === t.key) {
        if (reload) {
          reload()
        }
      }
      break
    case 'close':
      removeTab(t.key)
      break
    case 'close-left':
      // Remove tabs from index 1 to targetIdx-1
      if (targetIdx > 1) {
        tabs.value.splice(1, targetIdx - 1)
        if (!tabs.value.some((item) => item.key === activeKey.value)) {
          activeKey.value = t.key
          router.push(t.path)
        }
        persist()
      }
      break
    case 'close-right':
      if (targetIdx < tabs.value.length - 1) {
        tabs.value.splice(targetIdx + 1)
        if (!tabs.value.some((item) => item.key === activeKey.value)) {
          activeKey.value = t.key
          router.push(t.path)
        }
        persist()
      }
      break
    case 'close-other':
      tabs.value = [homeTab]
      if (t.key !== homeTab.key) {
        tabs.value.push(t)
      }
      if (activeKey.value !== t.key) {
        activeKey.value = t.key
        router.push(t.path)
      }
      persist()
      break
    case 'close-all':
      tabs.value = [homeTab]
      if (activeKey.value !== homeTab.key) {
        activeKey.value = homeTab.key
        router.push(homeTab.path)
      }
      persist()
      break
  }
}
</script>

<style scoped>
.tabs-bar {
  padding: 8px 16px 0;
  background: #ffffff;
  border-bottom: 1px solid #f1f5f9;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.02);
}

html.dark .tabs-bar {
  background: #1e293b;
  border-bottom-color: #334155;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
}

html.dark :deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab:hover) {
  background: #334155;
}

html.dark :deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab-active) {
  background: rgba(255, 255, 255, 0.08);
  border-color: #334155;
}

html.dark :deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab-btn) {
  color: #94a3b8;
}

.tabs {
  --tabs-radius: 8px;
}

:deep(.ant-tabs-nav) {
  margin: 0 !important;
}

:deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab) {
  border-radius: var(--tabs-radius) var(--tabs-radius) 0 0;
  border: 1px solid transparent;
  border-bottom: none;
  background: transparent;
  transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
  margin-right: 4px;
  padding: 6px 16px;
  position: relative;
}

:deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab:hover) {
  background: #f8fafc;
  color: var(--app-primary-color);
}

:deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab-active) {
  background: var(--app-primary-color-light);
  border-color: #e0e7ff;
  border-bottom: none;
  position: relative;
}

:deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab-active::before) {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: var(--app-primary-color);
  border-radius: 0;
}

:deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab-btn) {
  color: #64748b;
  font-weight: 500;
  font-size: 13px;
  transition: color 0.2s;
}

:deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab:hover .ant-tabs-tab-btn) {
  color: var(--app-primary-color);
}

:deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab-active .ant-tabs-tab-btn) {
  color: var(--app-primary-color);
  font-weight: 600;
}

:deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab-remove) {
  margin-left: 6px;
  border-radius: 4px;
  color: #94a3b8;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}

:deep(.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab-remove:hover) {
  background: rgba(239, 68, 68, 0.1);
  color: #ef4444;
}
</style>
