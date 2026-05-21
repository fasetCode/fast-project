<script setup lang="ts">
import { ref, provide, computed, watch } from 'vue'
import type { CanvasNode } from './types'
import { DRAG_KEY_COMPONENT, DRAG_KEY_NODE, findComponentDef, createNode } from './types'
import CanvasNodeRenderer from './CanvasNodeRenderer.vue'
import type { CanvasContext } from './canvas-context'
import { CANVAS_CONTEXT_KEY } from './canvas-context'

// ── Props / Emits ─────────────────────────────────────────────────────────────
const props = withDefaults(defineProps<{
  nodes:          CanvasNode[]
  selectedNodeId: string | null
  typeCode?:      string
}>(), {
  typeCode: '',
})

const emit = defineEmits<{
  (e: 'update:nodes',         nodes: CanvasNode[]): void
  (e: 'update:selectedNodeId', id: string | null):  void
  (e: 'save'):                                       void
  (e: 'publish'):                                    void
  (e: 'publishManage'):                              void
  (e: 'undo'):                                       void
  (e: 'redo'):                                       void
  (e: 'canUndoRedo', canUndo: boolean, canRedo: boolean): void
}>()

// ── Preview mode ──────────────────────────────────────────────────────────────
type PreviewMode = 'pc' | 'mobile'
const previewMode = ref<PreviewMode>('pc')

// ── Drag state ────────────────────────────────────────────────────────────────
const selectedId = ref<string | null>(props.selectedNodeId)
const draggingId = ref<string | null>(null)

// Keep selectedId in sync with prop
watch(() => props.selectedNodeId, v => { selectedId.value = v })

// ── Tree helpers ──────────────────────────────────────────────────────────────

/** Deep-clone nodes for history/immutability */
const cloneNodes = (list: CanvasNode[]): CanvasNode[] =>
  JSON.parse(JSON.stringify(list))

/** Find node by id anywhere in tree; returns [node, parent array, index] */
const findNode = (
  list: CanvasNode[],
  id: string,
): [CanvasNode, CanvasNode[], number] | null => {
  for (let i = 0; i < list.length; i++) {
    if (list[i].id === id) return [list[i], list, i]
    if (list[i].children?.length) {
      const found = findNode(list[i].children!, id)
      if (found) return found
    }
  }
  return null
}

/** Get children array for a given parentId (null = root) */
const getChildren = (list: CanvasNode[], parentId: string | null): CanvasNode[] | null => {
  if (parentId === null) return list
  const res = findNode(list, parentId)
  if (!res) return null
  const [node] = res
  if (!node.children) node.children = []
  return node.children
}

const pushNodes = (newNodes: CanvasNode[]) => {
  emit('update:nodes', newNodes)
}

// ── Context operations ────────────────────────────────────────────────────────

const selectNode = (id: string) => {
  selectedId.value = id
  emit('update:selectedNodeId', id)
}

const deleteNode = (id: string) => {
  const list = cloneNodes(props.nodes)
  const res  = findNode(list, id)
  if (!res) return
  const [, parent, index] = res
  parent.splice(index, 1)
  if (selectedId.value === id) {
    selectedId.value = null
    emit('update:selectedNodeId', null)
  }
  pushNodes(list)
}

const insertNode = (node: CanvasNode, parentId: string | null, index: number | null) => {
  const list     = cloneNodes(props.nodes)
  const children = getChildren(list, parentId)
  if (!children) return
  if (index === null) {
    children.push(node)
  } else {
    children.splice(index, 0, node)
  }
  pushNodes(list)
  // Auto-select newly added node
  selectedId.value = node.id
  emit('update:selectedNodeId', node.id)
}

const moveNode = (nodeId: string, parentId: string | null, index: number | null) => {
  if (nodeId === parentId) return
  const list = cloneNodes(props.nodes)
  // Remove from current location
  const res = findNode(list, nodeId)
  if (!res) return
  const [node, srcParent, srcIndex] = res
  srcParent.splice(srcIndex, 1)
  // Insert at new location
  const children = getChildren(list, parentId)
  if (!children) return
  if (index === null) {
    children.push(node)
  } else {
    // adjust index if we removed from the same parent before the target
    const adjustedIndex = (srcParent === children && srcIndex < index)
      ? index - 1
      : index
    children.splice(Math.max(0, adjustedIndex), 0, node)
  }
  pushNodes(list)
}

// ── Provide context ───────────────────────────────────────────────────────────
const typeCodeRef = ref<string>(props.typeCode ?? '')
watch(() => props.typeCode, v => { typeCodeRef.value = v ?? '' })

const context: CanvasContext = {
  selectedId,
  draggingId,
  typeCode: typeCodeRef,
  selectNode,
  deleteNode,
  insertNode,
  moveNode,
}
provide(CANVAS_CONTEXT_KEY, context)

// ── Root drop zone (from component library or node reorder) ───────────────────
const rootDragOver = ref(false)

const handleRootDragOver = (e: DragEvent) => {
  e.preventDefault()
  if (e.dataTransfer) {
    // Match effectAllowed: library drag uses 'copy', node reorder uses 'move'
    e.dataTransfer.dropEffect = draggingId.value !== null ? 'move' : 'copy'
  }
  if (draggingId.value === null) rootDragOver.value = true
}

const handleRootDragLeave = (e: DragEvent) => {
  if (!(e.currentTarget as HTMLElement).contains(e.relatedTarget as Node)) {
    rootDragOver.value = false
  }
}

const handleRootDrop = (e: DragEvent) => {
  e.preventDefault()
  rootDragOver.value = false
  const componentRaw = e.dataTransfer?.getData(DRAG_KEY_COMPONENT)
  const nodeId       = e.dataTransfer?.getData(DRAG_KEY_NODE)
  if (componentRaw) {
    try {
      const defRaw = JSON.parse(componentRaw)
      const def    = findComponentDef(defRaw.code) ?? defRaw
      insertNode(createNode(def), null, null)
    } catch { /* ignore */ }
  } else if (nodeId) {
    moveNode(nodeId, null, null)
  }
}
</script>

<template>
  <div class="canvas-container">
    <!-- ── Toolbar ─────────────────────────────────────────────────────── -->
    <div class="canvas-toolbar">
      <div class="toolbar-left">
        <!-- Preview toggle -->
        <div class="preview-toggle">
          <button
            :class="['tog-btn', { active: previewMode === 'pc' }]"
            @click="previewMode = 'pc'"
            title="PC 预览"
          >🖥 PC</button>
          <button
            :class="['tog-btn', { active: previewMode === 'mobile' }]"
            @click="previewMode = 'mobile'"
            title="手机预览"
          >📱 手机</button>
        </div>
      </div>

      <div class="toolbar-center">
        <span class="node-count">{{ nodes.length }} 个根组件</span>
      </div>

      <div class="toolbar-right">
        <button class="tb-btn" title="撤销 (Ctrl+Z)" @click="$emit('undo')">↩ 撤销</button>
        <button class="tb-btn" title="重做 (Ctrl+Y)" @click="$emit('redo')">↪ 重做</button>
        <button class="tb-btn tb-btn-manage" title="发布管理" @click="$emit('publishManage')">📋 发布管理</button>
        <button class="tb-btn tb-btn-publish" title="发布" @click="$emit('publish')">🚀 发布</button>
        <button class="tb-btn tb-btn-save" title="保存" @click="$emit('save')">💾 保存</button>
      </div>
    </div>

    <!-- ── Canvas scroll area ─────────────────────────────────────────── -->
    <div :class="['canvas-scroll', `scroll-${previewMode}`]">
      <div
        :class="['canvas-viewport', `viewport-${previewMode}`, { 'root-drag-over': rootDragOver }]"
        @dragover="handleRootDragOver"
        @dragleave="handleRootDragLeave"
        @drop="handleRootDrop"
        @click.self="() => { selectedId = null; $emit('update:selectedNodeId', null) }"
      >
        <!-- Mobile status bar -->
        <div v-if="previewMode === 'mobile'" class="mobile-status-bar">
          <span class="status-notch"></span>
        </div>

        <!-- Empty state -->
        <div v-if="nodes.length === 0" class="canvas-empty">
          <div class="empty-icon">📦</div>
          <p class="empty-tip">从左侧拖拽组件到此处开始搭建页面</p>
        </div>

        <!-- Node list -->
        <div class="nodes-area" v-else>
          <CanvasNodeRenderer
            v-for="(node, index) in nodes"
            :key="node.id"
            :node="node"
            :parent-id="null"
            :index="index"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.canvas-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: #e8eaed;
}

/* ── Toolbar ───────────────────────────────────────────────────────────── */
.canvas-toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 6px 14px;
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
  flex-shrink: 0;
  gap: 8px;
}

.toolbar-left, .toolbar-right { display: flex; align-items: center; gap: 6px; }
.toolbar-center { flex: 1; display: flex; justify-content: center; }

.preview-toggle {
  display: flex;
  gap: 2px;
  background: #f0f0f0;
  border-radius: 6px;
  padding: 2px;
}

.tog-btn {
  padding: 3px 10px;
  font-size: 12px;
  border: none;
  background: transparent;
  border-radius: 4px;
  cursor: pointer;
  color: #666;
  transition: all 0.15s;
  white-space: nowrap;
}
.tog-btn.active {
  background: #fff;
  color: #1677ff;
  box-shadow: 0 1px 4px rgba(0,0,0,0.12);
  font-weight: 600;
}

.node-count { font-size: 12px; color: #8c8c8c; }

.tb-btn {
  padding: 3px 10px;
  font-size: 12px;
  border: 1px solid #d9d9d9;
  background: #fff;
  border-radius: 5px;
  cursor: pointer;
  color: #555;
  transition: all 0.15s;
}
.tb-btn:hover           { border-color: #1677ff; color: #1677ff; }
.tb-btn-manage          { border-color: #722ed1; color: #722ed1; }
.tb-btn-manage:hover    { background: #722ed1; border-color: #722ed1; color: #fff; }
.tb-btn-publish         { background: #52c41a; border-color: #52c41a; color: #fff; font-weight: 600; }
.tb-btn-publish:hover   { background: #73d13d; border-color: #73d13d; }
.tb-btn-save            { background: #1677ff; border-color: #1677ff; color: #fff; font-weight: 600; }
.tb-btn-save:hover      { background: #4096ff; border-color: #4096ff; }

/* ── Scroll area ───────────────────────────────────────────────────────── */
.canvas-scroll {
  flex: 1;
  overflow: auto;
}
.scroll-pc     { background: #e8eaed; }
.scroll-mobile { background: #d0d0d0; display: flex; justify-content: center; align-items: flex-start; padding: 24px 0 60px; }

/* ── Viewport ──────────────────────────────────────────────────────────── */
.canvas-viewport {
  position: relative;
  transition: box-shadow 0.15s;
}

.viewport-pc {
  width: 100%;
  min-height: 100%;
  background: #fff;
  padding: 16px;
  box-sizing: border-box;
}

.viewport-mobile {
  width: 375px;
  min-height: 667px;
  background: #fff;
  border-radius: 36px;
  box-shadow:
    0 0 0 10px #1c1c1e,
    0 0 0 12px #3a3a3c,
    0 24px 48px rgba(0,0,0,0.45);
  overflow: hidden;
  padding: 50px 12px 30px;
  box-sizing: border-box;
  flex-shrink: 0;
}

.canvas-viewport.root-drag-over {
  box-shadow: inset 0 0 0 2px #1677ff;
}
.viewport-mobile.root-drag-over {
  box-shadow:
    0 0 0 10px #1c1c1e,
    0 0 0 12px #1677ff,
    0 24px 48px rgba(0,0,0,0.45);
}

/* Mobile status bar */
.mobile-status-bar {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 44px;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid #f0f0f0;
}
.status-notch {
  width: 120px;
  height: 20px;
  background: #1c1c1e;
  border-radius: 0 0 14px 14px;
  display: block;
}

/* ── Empty state ────────────────────────────────────────────────────────── */
.canvas-empty {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 400px;
  border: 2px dashed #d9d9d9;
  border-radius: 8px;
  color: #bfbfbf;
  gap: 12px;
  transition: border-color 0.15s, background 0.15s;
  cursor: copy;
}
.canvas-viewport.root-drag-over .canvas-empty {
  border-color: #1677ff;
  background: #f0f8ff;
  color: #1677ff;
}
.empty-icon { font-size: 48px; opacity: 0.5; }
.empty-tip  { font-size: 13px; margin: 0; }

.nodes-area { display: flex; flex-direction: column; }
</style>
