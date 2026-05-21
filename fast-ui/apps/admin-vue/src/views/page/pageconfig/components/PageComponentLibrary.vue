<script setup lang="ts">
import { ref, computed } from 'vue'
import { SearchOutlined } from '@ant-design/icons-vue'
import ImageDisplay from '@/components/ImageDisplay/index.vue'
import {
  LAYOUT_COMPONENTS,
  BASIC_COMPONENTS,
  MEDIA_COMPONENTS,
  DRAG_KEY_COMPONENT,
  type ComponentDef,
} from './types'

const props = defineProps<{
  specificComponents?: ComponentDef[]
}>()

// ── Search + active tab ───────────────────────────────────────────────────────
const search   = ref('')
const activeTab = ref<'layout' | 'basic' | 'media' | 'specific'>('layout')

type TabKey = 'layout' | 'basic' | 'media' | 'specific'

const tabs: { key: TabKey; label: string; emoji: string }[] = [
  { key: 'layout',   label: '布局', emoji: '▦' },
  { key: 'basic',    label: '基础', emoji: 'T' },
  { key: 'media',    label: '媒体', emoji: '🖼' },
  { key: 'specific', label: '特定', emoji: '🧩' },
]

const filterList = (list: ComponentDef[]) => {
  const q = search.value.trim().toLowerCase()
  return q ? list.filter(c => c.title.toLowerCase().includes(q) || c.code.toLowerCase().includes(q)) : list
}

const tabComponents = computed<ComponentDef[]>(() => {
  const map: Record<TabKey, ComponentDef[]> = {
    layout:   LAYOUT_COMPONENTS,
    basic:    BASIC_COMPONENTS,
    media:    MEDIA_COMPONENTS,
    specific: props.specificComponents ?? [],
  }
  return filterList(map[activeTab.value])
})

// ── When searching, flatten all tabs ─────────────────────────────────────────
const searchResults = computed<ComponentDef[]>(() => {
  if (!search.value.trim()) return []
  return filterList([
    ...LAYOUT_COMPONENTS,
    ...BASIC_COMPONENTS,
    ...MEDIA_COMPONENTS,
    ...(props.specificComponents ?? []),
  ])
})

const isSearching = computed(() => search.value.trim().length > 0)

// ── Drag ──────────────────────────────────────────────────────────────────────
const handleDragStart = (e: DragEvent, comp: ComponentDef) => {
  if (!e.dataTransfer) return
  e.dataTransfer.effectAllowed = 'copy'
  e.dataTransfer.setData(DRAG_KEY_COMPONENT, JSON.stringify(comp))
}
</script>

<template>
  <div class="comp-library">
    <!-- Search -->
    <div class="lib-search">
      <a-input
        v-model:value="search"
        placeholder="搜索组件…"
        allow-clear
        size="small"
      >
        <template #prefix><SearchOutlined style="color:#bfbfbf" /></template>
      </a-input>
    </div>

    <!-- Tabs (hidden when searching) -->
    <div v-if="!isSearching" class="lib-tabs">
      <button
        v-for="tab in tabs"
        :key="tab.key"
        :class="['lib-tab', { active: activeTab === tab.key }]"
        @click="activeTab = tab.key"
      >
        <span class="tab-emoji">{{ tab.emoji }}</span>
        <span class="tab-label">{{ tab.label }}</span>
        <span v-if="tab.key === 'specific' && (specificComponents?.length ?? 0) > 0" class="tab-badge">
          {{ specificComponents!.length }}
        </span>
      </button>
    </div>

    <!-- Search results -->
    <div v-if="isSearching" class="lib-grid">
      <div v-if="searchResults.length === 0" class="lib-empty">无匹配结果</div>
      <div
        v-for="comp in searchResults"
        :key="comp.code"
        class="comp-item"
        :class="`comp-cat-${comp.category}`"
        draggable="true"
        @dragstart="handleDragStart($event, comp)"
        :title="`${comp.title} (${comp.code})`"
      >
        <span class="comp-icon">{{ comp.iconEmoji ?? '□' }}</span>
        <span class="comp-title">{{ comp.title }}</span>
      </div>
    </div>

    <!-- Tab content -->
    <div v-else class="lib-grid">
      <div v-if="tabComponents.length === 0" class="lib-empty">
        {{ activeTab === 'specific' ? '暂无特定组件' : '暂无组件' }}
      </div>

      <template v-if="activeTab !== 'specific'">
        <div
          v-for="comp in tabComponents"
          :key="comp.code"
          class="comp-item"
          :class="`comp-cat-${comp.category}`"
          draggable="true"
          @dragstart="handleDragStart($event, comp)"
          :title="`${comp.title}\n${comp.code}`"
        >
          <span class="comp-icon">{{ comp.iconEmoji ?? '□' }}</span>
          <span class="comp-title">{{ comp.title }}</span>
        </div>
      </template>

      <!-- Specific: with image icon support -->
      <template v-else>
        <div
          v-for="comp in tabComponents"
          :key="comp.code"
          class="comp-item comp-item--specific"
          draggable="true"
          @dragstart="handleDragStart($event, comp)"
          :title="`${comp.title}\n${comp.code}`"
        >
          <div v-if="comp.icon" class="comp-specific-icon">
            <ImageDisplay :value="comp.icon" :width="32" :height="32" value-type="id" shape="square" />
          </div>
          <span v-else class="comp-icon">🧩</span>
          <span class="comp-title">{{ comp.title }}</span>
        </div>
      </template>
    </div>
  </div>
</template>

<style scoped>
.comp-library {
  display: flex;
  flex-direction: column;
  height: 100%;
  overflow: hidden;
}

/* ── Search ─────────────────────────────────────────────────────────────── */
.lib-search {
  padding: 8px;
  border-bottom: 1px solid #f0f0f0;
  flex-shrink: 0;
}

/* ── Tabs ───────────────────────────────────────────────────────────────── */
.lib-tabs {
  display: flex;
  border-bottom: 1px solid #f0f0f0;
  flex-shrink: 0;
}

.lib-tab {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
  padding: 6px 2px;
  border: none;
  background: transparent;
  cursor: pointer;
  font-size: 11px;
  color: #8c8c8c;
  border-bottom: 2px solid transparent;
  transition: all 0.15s;
  position: relative;
}
.lib-tab:hover       { color: #1677ff; background: #f0f8ff; }
.lib-tab.active      { color: #1677ff; border-bottom-color: #1677ff; background: #e6f4ff; }

.tab-emoji { font-size: 14px; }
.tab-label { font-size: 10px; }

.tab-badge {
  position: absolute;
  top: 2px;
  right: 4px;
  min-width: 14px;
  height: 14px;
  line-height: 14px;
  font-size: 9px;
  background: #ff4d4f;
  color: #fff;
  border-radius: 7px;
  text-align: center;
  padding: 0 3px;
}

/* ── Grid ───────────────────────────────────────────────────────────────── */
.lib-grid {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 6px;
  align-content: start;
}

.lib-empty {
  grid-column: 1 / -1;
  text-align: center;
  color: #bfbfbf;
  font-size: 12px;
  padding: 24px 0;
}

/* ── Component item ─────────────────────────────────────────────────────── */
.comp-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  padding: 8px 4px 6px;
  border: 1px solid #e8e8e8;
  border-radius: 6px;
  background: #fafafa;
  cursor: grab;
  user-select: none;
  transition: all 0.15s;
  min-height: 60px;
  justify-content: center;
}
.comp-item:hover  { border-color: #91caff; background: #e6f4ff; }
.comp-item:active { cursor: grabbing; opacity: 0.7; }

.comp-cat-layout   { border-left: 3px solid #722ed1; }
.comp-cat-basic    { border-left: 3px solid #1677ff; }
.comp-cat-media    { border-left: 3px solid #389e0d; }
.comp-cat-specific { border-left: 3px solid #d46b08; }

.comp-cat-layout:hover   { border-color: #b37feb; background: #f9f0ff; }
.comp-cat-basic:hover    { border-color: #91caff; background: #e6f4ff; }
.comp-cat-media:hover    { border-color: #95de64; background: #f6ffed; }
.comp-cat-specific:hover { border-color: #ffd591; background: #fff7e6; }

.comp-icon  { font-size: 18px; line-height: 1; }
.comp-title {
  font-size: 11px;
  color: #333;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;
}

.comp-item--specific { flex-direction: row; gap: 6px; padding: 6px 8px; justify-content: flex-start; }
.comp-item--specific .comp-title { text-align: left; }

.comp-specific-icon { flex-shrink: 0; }
</style>
