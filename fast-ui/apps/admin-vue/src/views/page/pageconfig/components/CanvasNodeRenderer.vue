<script lang="ts">
export default { name: 'CanvasNodeRenderer' }
</script>

<script setup lang="ts">
import { inject, computed, ref } from 'vue'
import type { CanvasNode } from './types'
import { DRAG_KEY_NODE, DRAG_KEY_COMPONENT, findComponentDef, createNode } from './types'
import type { CanvasContext } from './canvas-context'
import { CANVAS_CONTEXT_KEY } from './canvas-context'
import { getFileUrl } from '@/api/file/fileupload'
import LcButton from "@/components/lc/LcButton.vue";
import LcCard from "@/components/lc/LcCard.vue";
import TipTapViewer from '@/components/TipTapEditor/viewer.vue'
import AntShow from '../ui/antdesignvue/show/index.vue'

const props = defineProps<{
  node: CanvasNode
  parentId?: string | null
  index: number
}>()

const ctx = inject<CanvasContext>(CANVAS_CONTEXT_KEY)!

const isSelected  = computed(() => ctx.selectedId.value === props.node.id)
const isDragging  = computed(() => ctx.draggingId.value === props.node.id)
const typeCode    = computed(() => ctx.typeCode?.value ?? '')
const def         = computed(() => findComponentDef(props.node.type))
const isContainer = computed(() => def.value?.canHaveChildren ?? false)
const children    = computed(() => props.node.children ?? [])

// ── Drop state ────────────────────────────────────────────────────────────────
const dropSide = ref<'before' | 'after' | 'inside' | null>(null)

const calcSide = (e: DragEvent): 'before' | 'after' | 'inside' => {
  const rect  = (e.currentTarget as HTMLElement).getBoundingClientRect()
  const ratio = (e.clientY - rect.top) / rect.height
  if (isContainer.value && ratio > 0.25 && ratio < 0.75) return 'inside'
  return ratio < 0.5 ? 'before' : 'after'
}

const handleDragOver = (e: DragEvent) => {
  if (ctx.draggingId.value === props.node.id) return
  e.preventDefault()
  e.stopPropagation()
  if (e.dataTransfer) {
    e.dataTransfer.dropEffect = ctx.draggingId.value !== null ? 'move' : 'copy'
  }
  dropSide.value = calcSide(e)
}

const handleDragLeave = (e: DragEvent) => {
  if (!(e.currentTarget as HTMLElement).contains(e.relatedTarget as Node)) {
    dropSide.value = null
  }
}

const resolveAndDrop = (e: DragEvent, parentId: string | null, index: number | null) => {
  const nodeId       = e.dataTransfer?.getData(DRAG_KEY_NODE)
  const componentRaw = e.dataTransfer?.getData(DRAG_KEY_COMPONENT)
  if (nodeId) {
    ctx.moveNode(nodeId, parentId, index)
  } else if (componentRaw) {
    try {
      const defRaw = JSON.parse(componentRaw)
      const defObj = findComponentDef(defRaw.code) ?? defRaw
      ctx.insertNode(createNode(defObj), parentId, index)
    } catch { /* ignore */ }
  }
}

const handleDrop = (e: DragEvent) => {
  e.preventDefault()
  e.stopPropagation()
  const side = dropSide.value ?? 'after'
  dropSide.value = null
  if (side === 'inside') {
    resolveAndDrop(e, props.node.id, null)
  } else {
    const insertIndex = side === 'before' ? props.index : props.index + 1
    resolveAndDrop(e, props.parentId ?? null, insertIndex)
  }
}

const handleContainerEmptyDrop = (e: DragEvent) => {
  e.preventDefault()
  e.stopPropagation()
  resolveAndDrop(e, props.node.id, null)
}

const handleContainerEmptyDragOver = (e: DragEvent) => {
  if (ctx.draggingId.value === props.node.id) return
  e.preventDefault()
  e.stopPropagation()
  if (e.dataTransfer) {
    e.dataTransfer.dropEffect = ctx.draggingId.value !== null ? 'move' : 'copy'
  }
}

// ── Drag node ─────────────────────────────────────────────────────────────────
const handleDragStart = (e: DragEvent) => {
  e.stopPropagation()
  if (e.dataTransfer) {
    e.dataTransfer.effectAllowed = 'move'
    e.dataTransfer.setData(DRAG_KEY_NODE, props.node.id)
  }
  setTimeout(() => { ctx.draggingId.value = props.node.id }, 0)
}

const handleDragEnd = () => { ctx.draggingId.value = null }

// 解析图片 src：如果是纯数字 ID，通过 getFileUrl 构造链接
const resolveImageSrc = (src: any): string => {
  if (!src) return ''
  const s = String(src)
  if (s.startsWith('http') || s.startsWith('/') || s.includes('/')) return s
  return getFileUrl(src)
}


</script>

<template>
  <div
    class="cnr-wrapper"
    :style="node.css"
    :class="{
      'is-selected': isSelected,
      'is-dragging': isDragging,
      'drop-before': dropSide === 'before',
      'drop-after':  dropSide === 'after',
      'drop-inside': dropSide === 'inside',
    }"
    @click.stop="ctx.selectNode(node.id)"
    @dragover="handleDragOver"
    @dragleave="handleDragLeave"
    @drop="handleDrop"
  >
    <!-- ── Floating badge (top-left, visible on hover/select only) ─────── -->
    <div class="cnr-badge">
      <span
        class="cnr-handle"
        draggable="true"
        @dragstart.stop="handleDragStart"
        @dragend.stop="handleDragEnd"
        @click.stop
        title="拖拽排序"
      >⠿</span>
      <span class="cnr-name">{{ def?.iconEmoji ?? '□' }} {{ node.label }}</span>
    </div>
    <!-- ── Delete button (top-right) ──────────────────────────────────── -->
    <button class="cnr-del" @click.stop="ctx.deleteNode(node.id)" title="删除">✕</button>

    <!-- ── Rendered component ────────────────────────────────────────── -->

    <!-- lc-heading -->
    <component
      v-if="node.type === 'lc-heading'"
      :is="node.props.level || 'h2'"
      class="lc-heading"
      :style="{ color: node.props.color, textAlign: node.props.align }"
    >{{ node.props.content }}</component>

    <!-- lc-text -->
    <p
      v-else-if="node.type === 'lc-text'"
      class="lc-text"
      :style="{
        fontSize:   `${node.props.fontSize}px`,
        color:      node.props.color,
        fontWeight: String(node.props.fontWeight),
        textAlign:  node.props.align,
        lineHeight: node.props.lineHeight,
      }"
    >{{ node.props.content }}</p>

    <!-- lc-button -->
    <div v-else-if="node.type === 'lc-button'">
      <LcButton v-bind="node.props" >{{node.props.label}}</LcButton>
    </div>

    <!-- lc-tag -->
    <div v-else-if="node.type === 'lc-tag'" class="lc-tag-wrap">
      <span
        class="lc-tag"
        :style="{
          color:           node.props.color,
          backgroundColor: node.props.bgColor,
          borderColor:     node.props.borderColor,
        }"
      >{{ node.props.content }}</span>
    </div>

    <!-- lc-link -->
    <div v-else-if="node.type === 'lc-link'" class="lc-link-wrap">
      <a
        class="lc-link"
        :href="node.props.href"
        :style="{ color: node.props.color, textDecoration: node.props.underline ? 'underline' : 'none' }"
        @click.prevent
      >{{ node.props.text }}</a>
    </div>

    <!-- lc-richtext -->
    <div v-else-if="node.type === 'lc-richtext'" :style="{ minHeight: `${node.props.minHeight ?? 120}px` }">
      <TipTapViewer :content="node.props.content" />
    </div>
    

    <!-- lc-image -->
    <div v-else-if="node.type === 'lc-image'" class="lc-image-wrap">
      <img
        v-if="node.props.src"
        :src="resolveImageSrc(node.props.src)"
        :alt="node.props.alt"
        :style="{
          width:        `${node.props.width}%`,
          height:       node.props.height ? `${node.props.height}px` : 'auto',
          objectFit:    node.props.objectFit,
          borderRadius: `${node.props.borderRadius}px`,
          display:      'block',
        }"
      />
      <div v-else class="lc-image-placeholder">
        <span style="font-size:32px;opacity:.4">🖼</span>
        <span>图片（未设置地址）</span>
      </div>
    </div>

    <!-- lc-divider -->
    <div
      v-else-if="node.type === 'lc-divider'"
      :style="{ margin: `${node.props.margin}px 0` }"
    >
      <div v-if="node.props.text" style="text-align:center;font-size:12px;color:#8c8c8c;margin-bottom:4px">
        {{ node.props.text }}
      </div>
      <hr :style="{ border: 'none', borderTop: `1px solid ${node.props.color}`, margin: 0 }" />
    </div>

    <!-- lc-spacer -->
    <div
      v-else-if="node.type === 'lc-spacer'"
      class="lc-spacer"
      :style="{ height: `${node.props.height}px` }"
    ><span class="lc-spacer-label">{{ node.props.height }}px</span></div>

    <!-- lc-grid (container) -->
    <div
      v-else-if="node.type === 'lc-grid'"
      :style="{
        display:             'grid',
        gridTemplateColumns: `repeat(${node.props.columns}, 1fr)`,
        gap:                 `${node.props.gap}px`,
        padding:             `${node.props.padding}px`,
        backgroundColor:     node.props.bgColor !== 'transparent' ? node.props.bgColor : undefined,
      }"
    >
      <CanvasNodeRenderer
        v-for="(child, ci) in children"
        :key="child.id"
        :node="child"
        :parent-id="node.id"
        :index="ci"
      />
      <div
        v-if="children.length === 0"
        class="lc-empty-zone"
        style="grid-column:1/-1"
        @dragover="handleContainerEmptyDragOver"
        @drop.stop="handleContainerEmptyDrop"
      >↓ 拖入子组件</div>
    </div>

    <!-- lc-flex (container) -->
    <div
      v-else-if="node.type === 'lc-flex'"
      :style="{
        display:         'flex',
        flexDirection:   node.props.direction,
        gap:             `${node.props.gap}px`,
        flexWrap:        node.props.wrap ? 'wrap' : 'nowrap',
        alignItems:      node.props.align,
        padding:         `${node.props.padding}px`,
        backgroundColor: node.props.bgColor !== 'transparent' ? node.props.bgColor : undefined,
        minHeight:       '60px',
      }"
    >
      <CanvasNodeRenderer
        v-for="(child, ci) in children"
        :key="child.id"
        :node="child"
        :parent-id="node.id"
        :index="ci"
      />
      <div
        v-if="children.length === 0"
        class="lc-empty-zone"
        @dragover="handleContainerEmptyDragOver"
        @drop.stop="handleContainerEmptyDrop"
      >↓ 拖入子组件</div>
    </div>

    <!-- lc-card (container) -->
    <LcCard
      v-else-if="node.type === 'lc-card'"
      v-bind="node.props"
      @drag-over-empty="handleContainerEmptyDragOver"
      @drop-empty="handleContainerEmptyDrop"
    >
      <CanvasNodeRenderer
        v-for="(child, ci) in children"
        :key="child.id"
        :node="child"
        :parent-id="node.id"
        :index="ci"
      />
    </LcCard>

    <!-- lc-div (custom container) -->
    <div
      v-else-if="node.type === 'lc-div'"
      :style="{
        display:         node.props.display,
        flexDirection:   node.props.display === 'flex' ? node.props.flexDirection   : undefined,
        flexWrap:        node.props.display === 'flex' ? (node.props.flexWrap ? 'wrap' : 'nowrap') : undefined,
        alignItems:      node.props.display === 'flex' ? node.props.alignItems      : undefined,
        justifyContent:  node.props.display === 'flex' ? node.props.justifyContent  : undefined,
        gap:             node.props.gap          ? `${node.props.gap}px`          : undefined,
        padding:         `${node.props.padding ?? 0}px`,
        minHeight:       `${node.props.minHeight ?? 60}px`,
        backgroundColor: node.props.bgColor !== 'transparent' ? node.props.bgColor : undefined,
        borderRadius:    node.props.borderRadius ? `${node.props.borderRadius}px` : undefined,
      }"
    >
      <CanvasNodeRenderer
        v-for="(child, ci) in children"
        :key="child.id"
        :node="child"
        :parent-id="node.id"
        :index="ci"
      />
      <div
        v-if="children.length === 0"
        class="lc-empty-zone"
        @dragover="handleContainerEmptyDragOver"
        @drop.stop="handleContainerEmptyDrop"
      >↓ 拖入子组件</div>
    </div>

    <!-- lc-holy-grail (圣杯 / 双飞翼) -->
    <div
      v-else-if="node.type === 'lc-holy-grail'"
      class="lc-holy-grail"
      :style="{
        gridTemplateColumns: `${node.props.leftWidth}px 1fr ${node.props.rightWidth}px`,
        gridTemplateRows:    `${node.props.headerHeight}px 1fr ${node.props.footerHeight}px`,
        gap:                 `${node.props.gap}px`,
        backgroundColor:     node.props.bgColor !== 'transparent' ? node.props.bgColor : undefined,
      }"
    >
      <!-- header: children[0] -->
      <div class="lc-hg-area" style="grid-area:header">
        <CanvasNodeRenderer v-if="children[0]" :node="children[0]" :parent-id="node.id" :index="0" />
        <div v-else class="lc-area-empty">↓ 头部</div>
      </div>
      <!-- left: children[1] -->
      <div class="lc-hg-area" style="grid-area:left;overflow:auto">
        <CanvasNodeRenderer v-if="children[1]" :node="children[1]" :parent-id="node.id" :index="1" />
        <div v-else class="lc-area-empty">↓ 左栏</div>
      </div>
      <!-- main: children[2] -->
      <div class="lc-hg-area" :style="{ gridArea: 'main', overflow: 'auto', minHeight: `${node.props.mainMinH}px` }">
        <CanvasNodeRenderer v-if="children[2]" :node="children[2]" :parent-id="node.id" :index="2" />
        <div v-else class="lc-area-empty">↓ 主内容</div>
      </div>
      <!-- right: children[3] -->
      <div class="lc-hg-area" style="grid-area:right;overflow:auto">
        <CanvasNodeRenderer v-if="children[3]" :node="children[3]" :parent-id="node.id" :index="3" />
        <div v-else class="lc-area-empty">↓ 右栏</div>
      </div>
      <!-- footer: children[4] -->
      <div class="lc-hg-area" style="grid-area:footer">
        <CanvasNodeRenderer v-if="children[4]" :node="children[4]" :parent-id="node.id" :index="4" />
        <div v-else class="lc-area-empty">↓ 底部</div>
      </div>
    </div>

    <!-- lc-sticky-footer -->
    <div
      v-else-if="node.type === 'lc-sticky-footer'"
      :style="{
        display:         'flex',
        flexDirection:   'column',
        minHeight:       `${node.props.minHeight}px`,
        gap:             node.props.gap ? `${node.props.gap}px` : undefined,
        backgroundColor: node.props.bgColor !== 'transparent' ? node.props.bgColor : undefined,
      }"
    >
      <!-- content: children[0] -->
      <div style="flex:1;overflow:auto;min-height:0">
        <CanvasNodeRenderer v-if="children[0]" :node="children[0]" :parent-id="node.id" :index="0" />
        <div v-else class="lc-area-empty">↓ 主内容区</div>
      </div>
      <!-- footer: children[1] -->
      <div :style="{ height: `${node.props.footerHeight}px`, flexShrink: '0', overflow: 'hidden' }">
        <CanvasNodeRenderer v-if="children[1]" :node="children[1]" :parent-id="node.id" :index="1" />
        <div v-else class="lc-area-empty">↓ Footer 区</div>
      </div>
    </div>

    <!-- lc-equal-height -->
    <div
      v-else-if="node.type === 'lc-equal-height'"
      :style="{
        display:         'flex',
        flexDirection:   'row',
        alignItems:      'stretch',
        gap:             `${node.props.gap}px`,
        padding:         `${node.props.padding ?? 0}px`,
        minHeight:       '60px',
        backgroundColor: node.props.bgColor !== 'transparent' ? node.props.bgColor : undefined,
      }"
    >
      <CanvasNodeRenderer
        v-for="(child, ci) in children"
        :key="child.id"
        :node="child"
        :parent-id="node.id"
        :index="ci"
      />
      <div
        v-if="children.length === 0"
        class="lc-empty-zone"
        @dragover="handleContainerEmptyDragOver"
        @drop.stop="handleContainerEmptyDrop"
      >↓ 拖入子组件（等高排列）</div>
    </div>

    <!-- ant-design-vue: carousel 走马灯 -->
    <div
      v-else-if="typeCode === 'ant_design_vue'"
    >
      <AntShow :config="node.props" :type="node.type" />
    </div>

    <!-- Specific / unknown -->
    <div v-else>
      <div>
        自定义组件
      </div>
    </div>

    <!-- Drop-inside overlay (containers) -->
    <div v-if="dropSide === 'inside'" class="drop-inside-overlay">放入容器</div>
  </div>
</template>

<style scoped>
/* ── Wrapper ──────────────────────────────────────────────────────────────── */
.cnr-wrapper {
  position: relative;
  border-radius: 4px;
  margin-bottom: 0;
  cursor: pointer;
  transition: box-shadow 0.12s;
}

.cnr-wrapper::before,
.cnr-wrapper::after {
  content: '';
  position: absolute;
  pointer-events: none;
}

/* Hover overlay: no layout impact, so gap=0 stays flush */
.cnr-wrapper::before {
  inset: 0;
  border: 2px solid transparent;
  border-radius: inherit;
  transition: border-color 0.12s;
}

/* Hover: subtle dashed hint */
.cnr-wrapper:hover::before { border-color: rgba(22, 119, 255, 0.45); }

/* Selected: solid blue outline */
.is-selected {
  box-shadow: 0 0 0 3px rgba(22, 119, 255, 0.12);
}

.is-selected::before { border-color: #1677ff; }

/* Dragging */
.is-dragging { opacity: 0.4; }

/* Drag insert indicators */
.drop-before::after,
.drop-after::after {
  left: 0;
  right: 0;
  height: 2px;
  background: #1677ff;
  border-radius: 999px;
  z-index: 10;
}

.drop-before::after { top: 0; }
.drop-after::after  { bottom: 0; }

/* ── Floating badge (top-left corner, hidden by default) ─────────────────── */
.cnr-badge {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 60;
  display: flex;
  align-items: center;
  gap: 3px;
  height: 20px;
  padding: 0 6px 0 4px;
  background: #1677ff;
  border-radius: 0 0 4px 0;
  opacity: 0;
  pointer-events: none;
  transition: opacity 0.1s;
  white-space: nowrap;
}

/* ── Delete button (top-right corner, hidden by default) ─────────────────── */
.cnr-del {
  position: absolute;
  top: 0;
  right: 0;
  z-index: 60;
  width: 20px;
  height: 20px;
  border: none;
  background: rgba(0, 0, 0, 0.45);
  border-radius: 0 0 0 4px;
  color: #fff;
  cursor: pointer;
  font-size: 11px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
  opacity: 0;
  pointer-events: none;
  transition: opacity 0.1s;
}

/* Show badge + delete on hover or selection */
.cnr-wrapper:hover .cnr-badge,
.cnr-wrapper:hover .cnr-del,
.is-selected .cnr-badge,
.is-selected .cnr-del {
  opacity: 1;
  pointer-events: auto;
}

.cnr-del:hover { background: #ff4d4f; }

/* Drag handle inside badge */
.cnr-handle {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.8);
  cursor: grab;
  user-select: none;
  padding: 0 1px;
  line-height: 1;
}
.cnr-handle:active { cursor: grabbing; }

.cnr-name {
  font-size: 10px;
  font-weight: 600;
  color: #fff;
  max-width: 120px;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* ── Drop inside overlay ──────────────────────────────────────────────────── */
.drop-inside-overlay {
  position: absolute;
  inset: 0;
  background: rgba(22, 119, 255, 0.08);
  border: 2px dashed #1677ff;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  pointer-events: none;
  z-index: 20;
  font-size: 12px;
  font-weight: 600;
  color: #1677ff;
}

/* ── Component styles ─────────────────────────────────────────────────────── */
.lc-heading { margin: 0; word-break: break-word; }
.lc-text    { margin: 0; white-space: pre-wrap; word-break: break-word; }
.lc-button-wrap { }
.lc-tag-wrap    { }
.lc-link-wrap   { }


.lc-tag {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
  border: 1px solid;
}
.lc-link { font-size: 14px; }
.lc-image-wrap { line-height: 0; }

.lc-image-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
  height: 160px;
  background: #f5f5f5;
  border: 1.5px dashed #d9d9d9;
  border-radius: 4px;
  color: #aaa;
  font-size: 12px;
}

.lc-spacer {
  display: flex;
  align-items: center;
  justify-content: center;
  background: repeating-linear-gradient(45deg, #f5f5f5, #f5f5f5 4px, #fafafa 4px, #fafafa 8px);
  border: 1px dashed #e0e0e0;
  border-radius: 4px;
}
.lc-spacer-label { font-size: 10px; color: #ccc; user-select: none; }

/* ── 圣杯布局 ───────────────────────────────────────────────────────────────────── */
.lc-holy-grail {
  display: grid;
  grid-template-areas:
    "header header header"
    "left   main   right"
    "footer footer footer";
  width: 100%;
}
.lc-hg-area {
  overflow: hidden;
}

/* ── 区域占位符 ───────────────────────────────────────────────────────────────────── */
.lc-area-empty {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  min-height: 40px;
  border: 1.5px dashed #d9d9d9;
  border-radius: 4px;
  color: #c0c0c0;
  font-size: 12px;
  user-select: none;
  background: repeating-linear-gradient(45deg, #fafafa, #fafafa 4px, #f5f5f5 4px, #f5f5f5 8px);
}

/* Empty zone for containers */
.lc-empty-zone {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 56px;
  border: 1.5px dashed #d9d9d9;
  border-radius: 4px;
  color: #c0c0c0;
  font-size: 12px;
  cursor: copy;
  transition: border-color 0.15s, background 0.15s, color 0.15s;
}
.lc-empty-zone:hover {
  border-color: #91caff;
  background: #f0f8ff;
  color: #1677ff;
}


</style>
