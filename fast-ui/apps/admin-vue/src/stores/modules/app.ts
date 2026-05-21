import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'
import { watch } from 'vue'

export const useAppStore = defineStore('app', () => {
  // 使用 @vueuse/core 的 useStorage 自动处理 localStorage 持久化
  const isDark = useStorage('app_is_dark', false)
  const themeColor = useStorage('app_theme_color', '#6366f1')
  
  // 布局模式：vertical | horizontal | columns
  const layout = useStorage<'vertical' | 'horizontal' | 'columns'>('app_layout', 'vertical')
  
  // 侧边栏风格：light | dark
  const siderTheme = useStorage<'light' | 'dark'>('app_sider_theme', 'light')
  
  // 色弱模式
  const colorWeak = useStorage('app_color_weak', false)
  // 灰色模式 (通常用于哀悼日等)
  const colorGray = useStorage('app_color_gray', false)
  
  // 紧凑模式
  const compactMode = useStorage('app_compact_mode', false)
  
  // 标签页显示/隐藏
  const showTabs = useStorage('app_show_tabs', true)
  // 面包屑显示/隐藏
  const showBreadcrumb = useStorage('app_show_breadcrumb', true)

  const toggleDark = (val?: boolean) => {
    isDark.value = val ?? !isDark.value
    updateHtmlClass()
  }

  const updateHtmlClass = () => {
    const html = document.documentElement
    
    // 暗黑模式
    if (isDark.value) {
      html.classList.add('dark')
    } else {
      html.classList.remove('dark')
    }

    // 色弱模式
    if (colorWeak.value) {
      html.classList.add('color-weak')
    } else {
      html.classList.remove('color-weak')
    }

    // 灰色模式
    if (colorGray.value) {
      html.classList.add('color-gray')
    } else {
      html.classList.remove('color-gray')
    }
  }

  // 初始化时调用一次
  updateHtmlClass()

  // 动态修改 CSS 变量
  const updateCssVariables = () => {
    const html = document.documentElement
    html.style.setProperty('--app-primary-color', themeColor.value)
    
    // 简单计算一个稍微深一点的颜色作为 hover 态，或浅一点的作为背景
    // 为了不引入额外的颜色计算库，这里采用透明度混合的简易方案
    html.style.setProperty('--app-primary-color-hover', `${themeColor.value}e6`) // 90% 透明度
    html.style.setProperty('--app-primary-color-light', `${themeColor.value}1a`) // 10% 透明度
    html.style.setProperty('--app-primary-color-gradient-end', `${themeColor.value}cc`) // 80% 透明度
  }

  // 监听主题色变化，同步更新 CSS 变量
  watch(themeColor, () => {
    updateCssVariables()
  }, { immediate: true })

  return {
    isDark,
    themeColor,
    layout,
    siderTheme,
    colorWeak,
    colorGray,
    compactMode,
    showTabs,
    showBreadcrumb,
    toggleDark,
    updateHtmlClass
  }
})
