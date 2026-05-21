<script setup lang="ts">
import { ref } from 'vue'
import PageComponentLibrary from './PageComponentLibrary.vue'
import PageNodeTree from './PageNodeTree.vue'
import type { ComponentDef, CanvasNode } from './types'

// ── Props / Emits ─────────────────────────────────────────────────────────────
const props = defineProps<{
  specificComponents?: ComponentDef[]
  nodes: CanvasNode[]
  selectedNodeId?: string | null
}>()

const emit = defineEmits<{
  'update:selectedNodeId': [id: string | null]
  'deleteNode': [id: string]
  'moveNode': [nodeId: string, parentId: string | null, index: number | null]
}>()

// ── Active tab ────────────────────────────────────────────────────────────────
type PanelTab = 'components' | 'structure'
const activeTab = ref<PanelTab>('components')

const tabs: { key: PanelTab; label: string }[] = [
  { key: 'components', label: '组件库' },
  { key: 'structure',  label: '页面结构' },
]
</script>

<template>
  <div class="lib-panel">
    <!-- Tab bar -->
    <div class="lib-panel-tabs">
      <button
        v-for="tab in tabs"
        :key="tab.key"
        :class="['lib-panel-tab', { active: activeTab === tab.key }]"
        @click="activeTab = tab.key"
      >
        {{ tab.label }}
      </button>
    </div>

    <!-- Component library -->
    <div v-show="activeTab === 'components'" class="lib-panel-body">
      <PageComponentLibrary :specific-components="specificComponents" />
    </div>

    <!-- Canvas node tree -->
    <div v-show="activeTab === 'structure'" class="lib-panel-body">
      <PageNodeTree
        :nodes="nodes"
        :selected-node-id="selectedNodeId"
        @update:selected-node-id="id => emit('update:selectedNodeId', id)"
        @delete-node="id => emit('deleteNode', id)"
        @move-node="(nodeId, parentId, index) => emit('moveNode', nodeId, parentId, index)"
      />
    </div>
  </div>
</template>

<style scoped>
.lib-panel {
  display: flex;
  flex-direction: column;
  height: 100%;
  overflow: hidden;
}

/* ── Tabs ──────────────────────────────────────────────────────────────────── */
.lib-panel-tabs {
  display: flex;
  flex-shrink: 0;
  border-bottom: 1px solid #f0f0f0;
  background: #fafafa;
}

.lib-panel-tab {
  flex: 1;
  padding: 7px 4px;
  border: none;
  background: transparent;
  cursor: pointer;
  font-size: 12px;
  color: #8c8c8c;
  border-bottom: 2px solid transparent;
  transition: all 0.15s;
  white-space: nowrap;
}
.lib-panel-tab:hover  { color: #1677ff; background: #f0f8ff; }
.lib-panel-tab.active { color: #1677ff; border-bottom-color: #1677ff; background: #e6f4ff; font-weight: 600; }

/* ── Body ──────────────────────────────────────────────────────────────────── */
.lib-panel-body {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>
