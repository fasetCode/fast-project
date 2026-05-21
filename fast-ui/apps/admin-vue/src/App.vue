<script setup lang="ts">
import { computed } from 'vue'
import { ConfigProvider, theme } from 'ant-design-vue'
import zhCN from 'ant-design-vue/es/locale/zh_CN'
import { useAppStore } from '@/stores/modules/app'

const appStore = useAppStore()

const themeConfig = computed(() => {
  const algorithms = []
  if (appStore.isDark) {
    algorithms.push(theme.darkAlgorithm)
  } else {
    algorithms.push(theme.defaultAlgorithm)
  }
  if (appStore.compactMode) {
    algorithms.push(theme.compactAlgorithm)
  }

  return {
    algorithm: algorithms,
    token: {
      colorPrimary: appStore.themeColor,
      colorSuccess: '#10b981',
      colorWarning: '#f59e0b',
      colorError: '#ef4444',
      colorInfo: appStore.themeColor,
      borderRadius: 8,
      wireframe: false,
      fontFamily: '"Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif',
    }
  }
})
</script>

<template>
  <ConfigProvider :locale="zhCN" :theme="themeConfig">
    <router-view />
  </ConfigProvider>
</template>
