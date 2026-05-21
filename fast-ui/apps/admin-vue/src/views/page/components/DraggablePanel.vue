<script setup lang="ts">
import { ref } from 'vue'

interface Props {
  title?: string
  width?: number
  collapsed?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  title: '节点编辑区',
  width: 300,
  collapsed: false
})

const emit = defineEmits<{
  'update:collapsed': [value: boolean]
}>()

// 面板状态
const isCollapsed = ref(props.collapsed)
const isFloating = ref(true)
const panelPosition = ref({ 
  x: typeof window !== 'undefined' ? window.innerWidth - props.width - 20 : 0, 
  y: 20 
})
const isDragging = ref(false)
const dragOffset = ref({ x: 0, y: 0 })

// 切换收起/展开
const toggleCollapse = () => {
  isCollapsed.value = !isCollapsed.value
  emit('update:collapsed', isCollapsed.value)
}

// 切换浮动模式
const toggleFloating = (event: MouseEvent) => {
  event.stopPropagation()
  isFloating.value = !isFloating.value
  if (isFloating.value) {
    // 初始化位置到右上角
    panelPosition.value = {
      x: window.innerWidth - props.width - 20,
      y: 20
    }
  }
}

// 拖拽相关
const startDrag = (event: MouseEvent) => {
  if (!isFloating.value) return
  
  event.preventDefault()
  isDragging.value = true

  // 直接从 DOM 读取当前实际位置，避免用 state 有偏差
  const el = (event.currentTarget as HTMLElement).closest('.draggable-panel') as HTMLElement
  const rect = el.getBoundingClientRect()
  dragOffset.value = {
    x: event.clientX - rect.left,
    y: event.clientY - rect.top
  }
  
  window.addEventListener('mousemove', onDrag)
  window.addEventListener('mouseup', stopDrag)
}

const onDrag = (event: MouseEvent) => {
  if (!isDragging.value) return
  
  const newX = event.clientX - dragOffset.value.x
  const newY = event.clientY - dragOffset.value.y
  
  // 限制在可视区域内
  const maxX = window.innerWidth - props.width
  const maxY = window.innerHeight - 40
  
  panelPosition.value = {
    x: Math.max(0, Math.min(newX, maxX)),
    y: Math.max(0, Math.min(newY, maxY))
  }
}

const stopDrag = () => {
  isDragging.value = false
  window.removeEventListener('mousemove', onDrag)
  window.removeEventListener('mouseup', stopDrag)
}
</script>

<template>
  <div v-if="!isCollapsed">
    <!-- 浮动面板 -->
    <div 
      v-if="isFloating"
      class="draggable-panel"
      :style="{
        width: width + 'px',
        position: 'fixed',
        left: panelPosition.x + 'px',
        top: panelPosition.y + 'px',
        zIndex: 1000
      }"
    >
      <div class="panel-header" @mousedown="startDrag">
        <span class="panel-title">{{ title }}</span>
        <div class="panel-actions">
          <button class="action-btn" @click="toggleFloating" :title="isFloating ? '嵌入' : '浮动'">
            ⊡
          </button>
          <button class="action-btn" @click="toggleCollapse" title="收起">
            ✕
          </button>
        </div>
      </div>
      <div class="panel-content">
        <slot></slot>
      </div>
    </div>

    <!-- 内嵌面板 -->
    <div 
      v-else
      class="draggable-panel embedded"
      :style="{ width: width + 'px' }"
    >
      <div class="panel-header">
        <span class="panel-title">{{ title }}</span>
        <div class="panel-actions">
          <button class="action-btn" @click="toggleFloating" :title="isFloating ? '嵌入' : '浮动'">
            ⊞
          </button>
          <button class="action-btn" @click="toggleCollapse" title="收起">
            ✕
          </button>
        </div>
      </div>
      <div class="panel-content">
        <slot></slot>
      </div>
    </div>
  </div>

  <!-- 展开按钮 -->
  <button 
    v-else
    class="expand-btn"
    @click="toggleCollapse"
    :title="`展开${title}`"
  >
    {{ title }}
  </button>
</template>

<style scoped>
.draggable-panel {
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
  border: 1px solid #e8e8e8;
}

.draggable-panel.embedded {
  flex-shrink: 0;
  height: 100%;
}

.draggable-panel:not(.embedded) {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  border-radius: 4px;
  max-height: calc(100vh - 40px);
}

.panel-header {
  height: 40px;
  padding: 0 16px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-weight: 600;
  font-size: 14px;
  border-bottom: 1px solid #e8e8e8;
  background-color: #fafafa;
  cursor: default;
  user-select: none;
}

.draggable-panel:not(.embedded) .panel-header {
  cursor: move;
  border-radius: 4px 4px 0 0;
}

.panel-title {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.panel-actions {
  display: flex;
  gap: 4px;
  flex-shrink: 0;
}

.action-btn {
  width: 24px;
  height: 24px;
  border: none;
  background: transparent;
  cursor: pointer;
  border-radius: 4px;
  font-size: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.2s;
}

.action-btn:hover {
  background-color: #e8e8e8;
}

.panel-content {
  flex: 1;
  overflow: auto;
  padding: 12px;
}

.expand-btn {
  position: fixed;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  padding: 12px 16px;
  background-color: #ffffff;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.2s;
  z-index: 999;
}

.expand-btn:hover {
  background-color: #f5f5f5;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}
</style>
