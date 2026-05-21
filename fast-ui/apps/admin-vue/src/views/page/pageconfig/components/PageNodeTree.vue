<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { DeleteOutlined } from '@ant-design/icons-vue'
import { Modal } from 'ant-design-vue'
import type { CanvasNode } from './types'

// ── Props / Emits ─────────────────────────────────────────────────────────────
const props = defineProps<{
  nodes: CanvasNode[]
  selectedNodeId?: string | null
}>()

const emit = defineEmits<{
  'update:selectedNodeId': [id: string | null]
  'deleteNode': [id: string]
  'moveNode': [nodeId: string, parentId: string | null, index: number | null]
}>()

// ── Hover state ───────────────────────────────────────────────────────────────
const hoverId = ref<string | null>(null)

// ── Delete ────────────────────────────────────────────────────────────────────
const handleDelete = (e: MouseEvent, nodeId: string, nodeTitle: string) => {
  e.stopPropagation()
  Modal.confirm({
    title: '删除节点',
    content: `确定删除节点「${nodeTitle}」及其所有子节点？`,
    okText: '删除',
    okType: 'danger',
    cancelText: '取消',
    onOk: () => emit('deleteNode', nodeId),
  })
}

// ── Drag-and-drop ────────────────────────────────────────────────────────────
const findParentAndIndex = (
  list: CanvasNode[],
  id: string,
  parentId: string | null = null,
): { parentId: string | null; index: number } | null => {
  for (let i = 0; i < list.length; i++) {
    if (list[i].id === id) return { parentId, index: i }
    if (list[i].children?.length) {
      const found = findParentAndIndex(list[i].children!, id, list[i].id)
      if (found) return found
    }
  }
  return null
}

const onDrop = (info: any) => {
  const dragKey = String(info.dragNode.key)
  const dropKey = String(info.node.key)
  if (dragKey === dropKey) return

  if (!info.dropToGap) {
    // Drop onto a node → append as last child of that node
    emit('moveNode', dragKey, dropKey, null)
    return
  }

  // Drop into gap → insert before/after the target in its parent
  const dropPos = (info.node.pos as string).split('-')
  const relPos  = info.dropPosition - Number(dropPos[dropPos.length - 1])
  const target  = findParentAndIndex(props.nodes, dropKey)
  if (!target) return
  const insertIndex = relPos < 0 ? target.index : target.index + 1
  emit('moveNode', dragKey, target.parentId, insertIndex)
}

// ── Tree data conversion ──────────────────────────────────────────────────────
interface TreeItem {
  key: string
  title: string
  isLeaf?: boolean
  children?: TreeItem[]
}

const toTree = (nodes: CanvasNode[]): TreeItem[] =>
  nodes.map(n => ({
    key: n.id,
    title: n.label || n.type,
    isLeaf: !n.children?.length,
    children: n.children?.length ? toTree(n.children) : undefined,
  }))

const treeData = computed(() => toTree(props.nodes))

// ── Always keep all nodes expanded ───────────────────────────────────────────
const getAllKeys = (nodes: CanvasNode[]): string[] =>
  nodes.flatMap(n => [n.id, ...(n.children?.length ? getAllKeys(n.children) : [])])

const expandedKeys = ref<string[]>([])
watch(
  () => props.nodes,
  newNodes => { expandedKeys.value = getAllKeys(newNodes) },
  { immediate: true, deep: true },
)

const selectedKeys = computed<string[]>(() =>
  props.selectedNodeId ? [props.selectedNodeId] : [],
)

// ── Select handler ────────────────────────────────────────────────────────────
const handleSelect = (keys: (string | number)[]) => {
  emit('update:selectedNodeId', keys.length ? String(keys[0]) : null)
}
</script>

<template>
  <div class="node-tree">
    <!-- Empty state -->
    <div v-if="!nodes.length" class="tree-empty">画布暂无节点</div>

    <!-- Tree -->
    <a-tree
      v-else
      :tree-data="treeData"
      :selected-keys="selectedKeys"
      v-model:expanded-keys="expandedKeys"
      block-node
      draggable
      class="canvas-tree"
      @select="handleSelect"
      @drop="onDrop"
    >
      <template #title="{ title, key }">
        <div
          class="tree-node-row"
          @mouseenter="hoverId = String(key)"
          @mouseleave="hoverId = null"
        >
          <span class="tree-node-label">{{ title }}</span>
          <button
            v-show="hoverId === String(key)"
            class="tree-node-del"
            title="删除节点"
            @click="handleDelete($event, String(key), String(title))"
          >
            <DeleteOutlined />
          </button>
        </div>
      </template>
    </a-tree>
  </div>
</template>

<style scoped>
.node-tree {
  flex: 1;
  overflow-y: auto;
  padding: 4px 0;
}

.tree-empty {
  text-align: center;
  color: #bfbfbf;
  font-size: 12px;
  padding: 24px 8px;
}

.canvas-tree {
  font-size: 12px;
}

.tree-node-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  gap: 4px;
}

.tree-node-label {
  flex: 1;
  font-size: 12px;
  color: #333;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.tree-node-del {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 18px;
  height: 18px;
  padding: 0;
  border: none;
  border-radius: 3px;
  background: transparent;
  color: #ff4d4f;
  cursor: pointer;
  font-size: 11px;
  transition: background 0.15s;
}
.tree-node-del:hover { background: #fff1f0; }

/* Tighten default ANT tree item height */
:deep(.ant-tree-node-content-wrapper) {
  line-height: 24px;
  min-height: 24px;
}
</style>
