<script setup lang="ts">
import { computed } from 'vue'
import {
  PlusOutlined,
  DeleteOutlined,
  ArrowUpOutlined,
  ArrowDownOutlined,
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

interface TopInformationBarProps {
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

const props = withDefaults(defineProps<{ modelValue: TopInformationBarProps }>(), {
  modelValue: () => ({}),
})

const emit = defineEmits<{
  (e: 'update:modelValue', v: TopInformationBarProps): void
}>()

const cfg = computed(() => ({
  slogan:         props.modelValue.slogan         ?? '鲜花网，订花行业优惠平台！',
  bgColor:        props.modelValue.bgColor        ?? '#f7f7f7',
  textColor:      props.modelValue.textColor      ?? '#666666',
  linkColor:      props.modelValue.linkColor      ?? '#888888',
  highlightColor: props.modelValue.highlightColor ?? '#ff4d6d',
  height:         props.modelValue.height         ?? 36,
  fontSize:       props.modelValue.fontSize       ?? 12,
  paddingX:       props.modelValue.paddingX       ?? 24,
  navItems:       Array.isArray(props.modelValue.navItems) ? props.modelValue.navItems : [],
}))

const set = (key: keyof TopInformationBarProps, value: any) => {
  emit('update:modelValue', { ...props.modelValue, [key]: value })
}

const setNavItems = (items: NavItem[]) => set('navItems', items)

const addNavItem = () => {
  setNavItems([...cfg.value.navItems, { label: '新导航项', linkType: 'internal', target: '_self' }])
}

const removeNavItem = (idx: number) => {
  const list = cfg.value.navItems.slice()
  list.splice(idx, 1)
  setNavItems(list)
}

const moveNavItem = (idx: number, delta: number) => {
  const list = cfg.value.navItems.slice()
  const target = idx + delta
  if (target < 0 || target >= list.length) return
  const [item] = list.splice(idx, 1)
  list.splice(target, 0, item)
  setNavItems(list)
}

const updateNavItem = (idx: number, key: keyof NavItem, value: any) => {
  const list = cfg.value.navItems.slice()
  list[idx] = { ...list[idx], [key]: value }
  setNavItems(list)
}
</script>

<template>
  <div class="top-info-bar-form">
    <!-- ── 基础设置 ── -->
    <div class="form-group-label">基础设置</div>

    <div class="form-row">
      <label class="form-label">标语文案</label>
      <a-input
        :value="cfg.slogan"
        size="small"
        class="form-ctrl"
        @change="(e: Event) => set('slogan', (e.target as HTMLInputElement).value)"
      />
    </div>

    <div class="form-row">
      <label class="form-label">高度(px)</label>
      <a-input-number
        :value="cfg.height"
        :min="24"
        :max="80"
        :step="2"
        size="small"
        class="form-ctrl"
        @change="(v: number | string) => set('height', v)"
      />
    </div>

    <div class="form-row">
      <label class="form-label">字号(px)</label>
      <a-input-number
        :value="cfg.fontSize"
        :min="10"
        :max="18"
        :step="1"
        size="small"
        class="form-ctrl"
        @change="(v: number | string) => set('fontSize', v)"
      />
    </div>

    <div class="form-row">
      <label class="form-label">水平内距</label>
      <a-input-number
        :value="cfg.paddingX"
        :min="0"
        :max="100"
        :step="4"
        size="small"
        class="form-ctrl"
        @change="(v: number | string) => set('paddingX', v)"
      />
    </div>

    <!-- ── 颜色设置 ── -->
    <div class="form-group-label">颜色设置</div>

    <div class="form-row">
      <label class="form-label">背景色</label>
      <div class="prop-color-row">
        <input
          type="color"
          class="prop-color-swatch"
          :value="cfg.bgColor"
          @input="(e: Event) => set('bgColor', (e.target as HTMLInputElement).value)"
        />
        <a-input
          :value="cfg.bgColor"
          size="small"
          class="form-ctrl"
          @change="(e: Event) => set('bgColor', (e.target as HTMLInputElement).value)"
        />
      </div>
    </div>

    <div class="form-row">
      <label class="form-label">文字色</label>
      <div class="prop-color-row">
        <input
          type="color"
          class="prop-color-swatch"
          :value="cfg.textColor"
          @input="(e: Event) => set('textColor', (e.target as HTMLInputElement).value)"
        />
        <a-input
          :value="cfg.textColor"
          size="small"
          class="form-ctrl"
          @change="(e: Event) => set('textColor', (e.target as HTMLInputElement).value)"
        />
      </div>
    </div>

    <div class="form-row">
      <label class="form-label">链接色</label>
      <div class="prop-color-row">
        <input
          type="color"
          class="prop-color-swatch"
          :value="cfg.linkColor"
          @input="(e: Event) => set('linkColor', (e.target as HTMLInputElement).value)"
        />
        <a-input
          :value="cfg.linkColor"
          size="small"
          class="form-ctrl"
          @change="(e: Event) => set('linkColor', (e.target as HTMLInputElement).value)"
        />
      </div>
    </div>

    <div class="form-row">
      <label class="form-label">高亮色</label>
      <div class="prop-color-row">
        <input
          type="color"
          class="prop-color-swatch"
          :value="cfg.highlightColor"
          @input="(e: Event) => set('highlightColor', (e.target as HTMLInputElement).value)"
        />
        <a-input
          :value="cfg.highlightColor"
          size="small"
          class="form-ctrl"
          @change="(e: Event) => set('highlightColor', (e.target as HTMLInputElement).value)"
        />
      </div>
    </div>

    <!-- ── 导航项 ── -->
    <div class="form-group-label nav-header">
      <span>导航项 ({{ cfg.navItems.length }})</span>
      <a-button size="small" type="link" @click="addNavItem">
        <plus-outlined /> 添加
      </a-button>
    </div>

    <div v-if="cfg.navItems.length === 0" class="form-empty">
      暂无导航项，点击"添加"创建
    </div>

    <div
      v-for="(item, idx) in cfg.navItems"
      :key="idx"
      class="nav-card"
    >
      <div class="nav-card-head">
        <span class="nav-index">#{{ idx + 1 }}</span>
        <div class="nav-actions">
          <a-button size="small" type="text" :disabled="idx === 0" title="上移" @click="moveNavItem(idx, -1)">
            <arrow-up-outlined />
          </a-button>
          <a-button size="small" type="text" :disabled="idx === cfg.navItems.length - 1" title="下移" @click="moveNavItem(idx, 1)">
            <arrow-down-outlined />
          </a-button>
          <a-button size="small" type="text" danger title="删除" @click="removeNavItem(idx)">
            <delete-outlined />
          </a-button>
        </div>
      </div>

      <div class="form-row">
        <label class="form-label">文字</label>
        <a-input
          :value="item.label || ''"
          size="small"
          class="form-ctrl"
          placeholder="导航文字"
          @change="(e: Event) => updateNavItem(idx, 'label', (e.target as HTMLInputElement).value)"
        />
      </div>

      <div class="form-row">
        <label class="form-label">图标</label>
        <a-select
          :value="item.icon || ''"
          size="small"
          class="form-ctrl"
          allow-clear
          placeholder="无"
          @change="(v: string) => updateNavItem(idx, 'icon', v || undefined)"
        >
          <a-select-option value="cart">购物车</a-select-option>
          <a-select-option value="order">订单</a-select-option>
          <a-select-option value="shop">商铺</a-select-option>
        </a-select>
      </div>

      <div class="form-row">
        <label class="form-label">跳转地址</label>
        <a-input
          :value="item.link || ''"
          size="small"
          class="form-ctrl"
          placeholder="链接地址"
          @change="(e: Event) => updateNavItem(idx, 'link', (e.target as HTMLInputElement).value)"
        />
      </div>

      <div class="form-row">
        <label class="form-label">链接类型</label>
        <a-select
          :value="item.linkType || 'internal'"
          size="small"
          class="form-ctrl"
          @change="(v: string) => updateNavItem(idx, 'linkType', v)"
        >
          <a-select-option value="internal">本站</a-select-option>
          <a-select-option value="external">站外</a-select-option>
        </a-select>
      </div>

      <div class="form-row">
        <label class="form-label">打开方式</label>
        <a-select
          :value="item.target || '_self'"
          size="small"
          class="form-ctrl"
          @change="(v: string) => updateNavItem(idx, 'target', v)"
        >
          <a-select-option value="_self">当前标签</a-select-option>
          <a-select-option value="_blank">新标签</a-select-option>
        </a-select>
      </div>

      <div class="form-row">
        <label class="form-label">文字颜色</label>
        <div class="prop-color-row">
          <input
            type="color"
            class="prop-color-swatch"
            :value="item.color || cfg.linkColor"
            @input="(e: Event) => updateNavItem(idx, 'color', (e.target as HTMLInputElement).value)"
          />
          <a-input
            :value="item.color || ''"
            size="small"
            class="form-ctrl"
            placeholder="默认继承全局"
            @change="(e: Event) => updateNavItem(idx, 'color', (e.target as HTMLInputElement).value || undefined)"
          />
        </div>
      </div>

      <div class="form-row">
        <label class="form-label">高亮</label>
        <a-switch
          :checked="item.highlight ?? false"
          size="small"
          @change="(v: boolean) => updateNavItem(idx, 'highlight', v)"
        />
      </div>

      <div class="form-row">
        <label class="form-label">数量角标</label>
        <a-input-number
          :value="item.count ?? null"
          :min="0"
          :max="9999"
          size="small"
          class="form-ctrl"
          placeholder="无"
          @change="(v: number | string | null) => updateNavItem(idx, 'count', v === null || v === '' ? undefined : Number(v))"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.top-info-bar-form {
  display: flex;
  flex-direction: column;
}

.form-group-label {
  font-size: 11px;
  font-weight: 600;
  color: #8c8c8c;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  margin: 4px 0 10px;
}

.nav-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 12px;
}

.form-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}
.form-row:last-child { margin-bottom: 0; }

.form-label {
  width: 72px;
  flex-shrink: 0;
  font-size: 12px;
  color: #555;
}

.form-ctrl { flex: 1; min-width: 0; }

.form-empty {
  font-size: 11px;
  color: #bfbfbf;
  text-align: center;
  padding: 12px 0;
  border: 1px dashed #f0f0f0;
  border-radius: 4px;
}

.nav-card {
  border: 1px solid #f0f0f0;
  border-radius: 4px;
  padding: 8px;
  margin-bottom: 10px;
  background: #fafafa;
}

.nav-card-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
}
.nav-index {
  font-size: 11px;
  font-weight: 600;
  color: #1677ff;
}
.nav-actions {
  display: flex;
  gap: 0;
}

.prop-color-row {
  display: flex;
  align-items: center;
  gap: 5px;
  flex: 1;
}
.prop-color-swatch {
  width: 28px;
  height: 28px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  padding: 1px;
  cursor: pointer;
  flex-shrink: 0;
}
</style>
