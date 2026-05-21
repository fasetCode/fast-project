<template>
  <a-drawer
    v-model:open="visible"
    title="项目配置"
    placement="right"
    :width="320"
    :class="['app-settings-drawer', { dark: appStore.isDark }]"
    :rootClassName="appStore.isDark ? 'dark-drawer-root' : ''"
  >
    <div class="settings-container">
      <!-- 主题模式 -->
      <div class="setting-section">
        <h3 class="section-title">主题模式</h3>
        <div class="theme-mode-list">
          <div 
            class="theme-mode-item" 
            :class="{ active: !appStore.isDark }" 
            @click="appStore.toggleDark(false)"
          >
            <div class="theme-mode-icon light">
              <div class="theme-mode-header"></div>
              <div class="theme-mode-body"></div>
            </div>
            <span>亮色模式</span>
          </div>
          <div 
            class="theme-mode-item" 
            :class="{ active: appStore.isDark }" 
            @click="appStore.toggleDark(true)"
          >
            <div class="theme-mode-icon dark">
              <div class="theme-mode-header"></div>
              <div class="theme-mode-body"></div>
            </div>
            <span>暗黑模式</span>
          </div>
        </div>
      </div>

      <a-divider />

      <!-- 主题色 -->
      <div class="setting-section">
        <h3 class="section-title">主题颜色</h3>
        <div class="color-list">
          <div
            v-for="c in themeColors"
            :key="c.value"
            class="color-item"
            :style="{ backgroundColor: c.value }"
            @click="appStore.themeColor = c.value"
          >
            <CheckOutlined v-if="appStore.themeColor === c.value" class="color-check" />
          </div>
        </div>
      </div>

      <a-divider />

      <!-- 布局模式 -->
      <div class="setting-section">
        <h3 class="section-title">布局模式</h3>
        <div class="theme-mode-list">
          <div 
            class="theme-mode-item" 
            :class="{ active: appStore.layout === 'vertical' }" 
            @click="appStore.layout = 'vertical'"
          >
            <div class="theme-mode-icon layout-vertical">
              <div class="layout-sider"></div>
              <div class="layout-main">
                <div class="layout-header"></div>
                <div class="layout-body"></div>
              </div>
            </div>
            <span>经典布局</span>
          </div>
          <div 
            class="theme-mode-item" 
            :class="{ active: appStore.layout === 'horizontal' }" 
            @click="appStore.layout = 'horizontal'"
          >
            <div class="theme-mode-icon layout-horizontal">
              <div class="layout-header"></div>
              <div class="layout-body"></div>
            </div>
            <span>横向布局</span>
          </div>
          <div 
            class="theme-mode-item" 
            :class="{ active: appStore.layout === 'columns' }" 
            @click="appStore.layout = 'columns'"
          >
            <div class="theme-mode-icon layout-columns">
              <div class="layout-sider-narrow"></div>
              <div class="layout-sider"></div>
              <div class="layout-main">
                <div class="layout-header"></div>
                <div class="layout-body"></div>
              </div>
            </div>
            <span>分栏布局</span>
          </div>
        </div>
      </div>

      <a-divider />

      <!-- 侧边栏风格 -->
      <div class="setting-section">
        <h3 class="section-title">侧边栏风格</h3>
        <div class="theme-mode-list">
          <div 
            class="theme-mode-item" 
            :class="{ active: appStore.siderTheme === 'light' }" 
            @click="appStore.siderTheme = 'light'"
          >
            <div class="theme-mode-icon sider-light">
              <div class="theme-mode-sider"></div>
              <div class="theme-mode-body"></div>
            </div>
            <span>亮色菜单</span>
          </div>
          <div 
            class="theme-mode-item" 
            :class="{ active: appStore.siderTheme === 'dark' }" 
            @click="appStore.siderTheme = 'dark'"
          >
            <div class="theme-mode-icon sider-dark">
              <div class="theme-mode-sider"></div>
              <div class="theme-mode-body"></div>
            </div>
            <span>暗色菜单</span>
          </div>
        </div>
      </div>

      <a-divider />

      <!-- 界面显示 -->
      <div class="setting-section">
        <h3 class="section-title">界面显示</h3>
        <div class="setting-item">
          <span>显示顶栏标签页</span>
          <a-switch v-model:checked="appStore.showTabs" />
        </div>
        <div class="setting-item">
          <span>显示面包屑导航</span>
          <a-switch v-model:checked="appStore.showBreadcrumb" />
        </div>
        <div class="setting-item">
          <span>紧凑模式 (减少留白)</span>
          <a-switch v-model:checked="appStore.compactMode" />
        </div>
      </div>

      <a-divider />

      <!-- 特殊模式 -->
      <div class="setting-section">
        <h3 class="section-title">特殊模式</h3>
        <div class="setting-item">
          <span>色弱模式</span>
          <a-switch v-model:checked="appStore.colorWeak" @change="appStore.updateHtmlClass" />
        </div>
        <div class="setting-item">
          <span>灰色模式</span>
          <a-switch v-model:checked="appStore.colorGray" @change="appStore.updateHtmlClass" />
        </div>
      </div>
    </div>
  </a-drawer>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { CheckOutlined } from '@ant-design/icons-vue'
import { useAppStore } from '@/stores/modules/app'

const appStore = useAppStore()
const visible = ref(false)

const open = () => {
  visible.value = true
}

const themeColors = [
  { name: 'Indigo', value: '#6366f1' },
  { name: 'Blue', value: '#1677ff' },
  { name: 'Green', value: '#10b981' },
  { name: 'Cyan', value: '#06b6d4' },
  { name: 'Purple', value: '#8b5cf6' },
  { name: 'Pink', value: '#ec4899' },
  { name: 'Red', value: '#ef4444' },
  { name: 'Orange', value: '#f59e0b' },
]

defineExpose({
  open
})
</script>

<style scoped>
.app-settings-drawer :deep(.ant-drawer-content) {
  background: #ffffff;
}

html.dark .app-settings-drawer :deep(.ant-drawer-content) {
  background: #0f172a;
  color: #e2e8f0;
}

html.dark .app-settings-drawer :deep(.ant-drawer-header) {
  background: #0f172a;
  border-bottom: 1px solid #1e293b;
}

html.dark .app-settings-drawer :deep(.ant-drawer-title) {
  color: #e2e8f0;
}

html.dark .app-settings-drawer :deep(.ant-drawer-close) {
  color: #94a3b8;
}
.settings-container {
  display: flex;
  flex-direction: column;
}

.section-title {
  font-size: 14px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 16px;
  margin-top: 0;
}

html.dark .section-title {
  color: #e2e8f0;
}

.theme-mode-list {
  display: flex;
  gap: 16px;
}

.theme-mode-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.theme-mode-item span {
  font-size: 12px;
  color: #64748b;
}

html.dark .theme-mode-item span {
  color: #94a3b8;
}

.theme-mode-icon {
  width: 60px;
  height: 45px;
  border-radius: 6px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  border: 2px solid transparent;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  transition: all 0.2s;
}

html.dark .theme-mode-icon {
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5);
  border-color: #334155;
}

.theme-mode-item.active .theme-mode-icon {
  border-color: var(--app-primary-color);
}

.theme-mode-icon.light { background: #f8fafc; }
.theme-mode-icon.light .theme-mode-header { background: #ffffff; height: 12px; border-bottom: 1px solid #e2e8f0; }
.theme-mode-icon.light .theme-mode-body { flex: 1; }

.theme-mode-icon.dark { background: #0f172a; }
.theme-mode-icon.dark .theme-mode-header { background: #1e293b; height: 12px; border-bottom: 1px solid #334155; }
.theme-mode-icon.dark .theme-mode-body { flex: 1; }

.theme-mode-icon.sider-light { flex-direction: row; background: #f8fafc; }
.theme-mode-icon.sider-light .theme-mode-sider { width: 16px; background: #ffffff; border-right: 1px solid #e2e8f0; }
.theme-mode-icon.sider-light .theme-mode-body { flex: 1; background: #f8fafc; }

.theme-mode-icon.sider-dark { flex-direction: row; background: #f8fafc; }
.theme-mode-icon.sider-dark .theme-mode-sider { width: 16px; background: #1e293b; }
.theme-mode-icon.sider-dark .theme-mode-body { flex: 1; background: #f8fafc; }

html.dark .theme-mode-icon.sider-light { background: #0f172a; }
html.dark .theme-mode-icon.sider-light .theme-mode-sider { background: #ffffff; border-right-color: #e2e8f0; }
html.dark .theme-mode-icon.sider-light .theme-mode-body { background: #0f172a; }

html.dark .theme-mode-icon.sider-dark { background: #0f172a; }
html.dark .theme-mode-icon.sider-dark .theme-mode-sider { background: #1e293b; }
html.dark .theme-mode-icon.sider-dark .theme-mode-body { background: #0f172a; }

/* 布局模式图标 */
.layout-vertical { flex-direction: row; background: #f8fafc; }
.layout-vertical .layout-sider { width: 16px; background: #1e293b; }
.layout-vertical .layout-main { flex: 1; display: flex; flex-direction: column; }
.layout-vertical .layout-header { height: 10px; background: #ffffff; border-bottom: 1px solid #e2e8f0; }
.layout-vertical .layout-body { flex: 1; background: #f8fafc; }

.layout-horizontal { flex-direction: column; background: #f8fafc; }
.layout-horizontal .layout-header { height: 14px; background: #1e293b; }
.layout-horizontal .layout-body { flex: 1; background: #f8fafc; }

.layout-columns { flex-direction: row; background: #f8fafc; }
.layout-columns .layout-sider-narrow { width: 10px; background: #0f172a; }
.layout-columns .layout-sider { width: 14px; background: #ffffff; border-right: 1px solid #e2e8f0; }
.layout-columns .layout-main { flex: 1; display: flex; flex-direction: column; }
.layout-columns .layout-header { height: 10px; background: #ffffff; border-bottom: 1px solid #e2e8f0; }
.layout-columns .layout-body { flex: 1; background: #f8fafc; }

html.dark .layout-vertical { background: #0f172a; }
html.dark .layout-vertical .layout-sider { background: #1e293b; }
html.dark .layout-vertical .layout-header { background: #1e293b; border-color: #334155; }
html.dark .layout-vertical .layout-body { background: #0f172a; }

html.dark .layout-horizontal { background: #0f172a; }
html.dark .layout-horizontal .layout-header { background: #1e293b; }
html.dark .layout-horizontal .layout-body { background: #0f172a; }

html.dark .layout-columns { background: #0f172a; }
html.dark .layout-columns .layout-sider-narrow { background: #0f172a; border-right: 1px solid #334155; }
html.dark .layout-columns .layout-sider { background: #1e293b; border-color: #334155; }
html.dark .layout-columns .layout-header { background: #1e293b; border-color: #334155; }
html.dark .layout-columns .layout-body { background: #0f172a; }

.color-list {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.color-item {
  width: 24px;
  height: 24px;
  border-radius: 4px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  transition: transform 0.2s;
}

.color-item:hover {
  transform: scale(1.1);
}

.setting-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16px;
  font-size: 14px;
  color: #334155;
}

html.dark .setting-item {
  color: #e2e8f0;
}

.setting-item:last-child {
  margin-bottom: 0;
}
</style>
