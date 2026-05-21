<script setup lang="ts">
import { computed } from 'vue'
import {
  ShoppingCartOutlined,
  ProfileOutlined,
  ShopOutlined,
} from '@ant-design/icons-vue'

interface NavItem {
  label: string
  link?: string
  linkType?: 'internal' | 'external'
  target?: '_blank' | '_self'
  icon?: 'cart' | 'order' | 'shop'
  count?: number
  highlight?: boolean
  color?: string
}

interface TopInformationBarConfig {
  slogan?: string
  bgColor?: string
  textColor?: string
  linkColor?: string
  highlightColor?: string
  height?: number
  fontSize?: number
  paddingX?: number
  navItems?: NavItem[]
}

const props = withDefaults(defineProps<{ config: TopInformationBarConfig }>(), {
  config: () => ({}),
})

const cfg = computed(() => ({
  slogan:         props.config.slogan         ?? '鲜花网，订花行业优惠平台！',
  bgColor:        props.config.bgColor        ?? '#f7f7f7',
  textColor:      props.config.textColor      ?? '#666666',
  linkColor:      props.config.linkColor      ?? '#888888',
  highlightColor: props.config.highlightColor ?? '#ff4d6d',
  height:         props.config.height         ?? 36,
  fontSize:       props.config.fontSize       ?? 12,
  paddingX:       props.config.paddingX       ?? 24,
  navItems:       Array.isArray(props.config.navItems) && props.config.navItems.length > 0
    ? props.config.navItems
    : ([
        { label: '亲, 请登录', highlight: true },
        { label: '免费注册' },
        { label: '购物车', icon: 'cart', count: 3, highlight: true },
        { label: '我的订单', icon: 'order' },
        { label: '商户后台', icon: 'shop' },
      ] as NavItem[]),
}))

const barStyle = computed(() => ({
  backgroundColor: cfg.value.bgColor,
  color: cfg.value.textColor,
  height: `${cfg.value.height}px`,
  lineHeight: `${cfg.value.height}px`,
  fontSize: `${cfg.value.fontSize}px`,
  padding: `0 ${cfg.value.paddingX}px`,
}))

const itemStyle = (item: NavItem) => ({
  color: item.color || (item.highlight ? cfg.value.highlightColor : cfg.value.linkColor),
})

const itemTarget = (item: NavItem) => {
  if (item.target) return item.target
  if (item.linkType === 'external') return '_blank'
  return '_self'
}

const itemRel = (item: NavItem) => {
  return item.linkType === 'external' ? 'noopener noreferrer' : undefined
}
</script>

<template>
  <div class="top-info-bar" :style="barStyle">
    <div class="top-info-bar__left">
      <span class="slogan">{{ cfg.slogan }}</span>
    </div>
    <div class="top-info-bar__right">
      <a
        v-for="(item, idx) in cfg.navItems"
        :key="idx"
        class="nav-item"
        :href="item.link || 'javascript:;'"
        :target="itemTarget(item)"
        :rel="itemRel(item)"
        :style="itemStyle(item)"
      >
        <ShoppingCartOutlined v-if="item.icon === 'cart'" class="nav-icon" />
        <ProfileOutlined      v-else-if="item.icon === 'order'" class="nav-icon" />
        <ShopOutlined         v-else-if="item.icon === 'shop'"  class="nav-icon" />
        <span class="nav-label">
          {{ item.label }}<template v-if="item.count !== undefined"> ({{ item.count }}件)</template>
        </span>
      </a>
    </div>
  </div>
</template>

<style scoped>
.top-info-bar {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-sizing: border-box;
  border-bottom: 1px solid #ececec;
  user-select: none;
}

.top-info-bar__left {
  display: flex;
  align-items: center;
}

.slogan {
  font-weight: 400;
}

.top-info-bar__right {
  display: flex;
  align-items: center;
  gap: 24px;
}

.nav-item {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  text-decoration: none;
  transition: opacity 0.15s;
  cursor: pointer;
}

.nav-item:hover {
  opacity: 0.75;
}

.nav-icon {
  font-size: 14px;
}

.nav-label {
  white-space: nowrap;
}
</style>
