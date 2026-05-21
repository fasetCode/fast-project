<script setup lang="ts">
// Simple three-panel layout: component library | canvas | property editor
// No DraggablePanel dependency needed.
defineProps<{
  componentLibWidth?: number
  propertyPanelWidth?: number
}>()
</script>

<template>
  <div class="editor-layout">
    <!-- Left: component library -->
    <div
      class="panel panel-library"
      :style="{ width: (componentLibWidth ?? 220) + 'px' }"
    >
      <slot name="component-list" />
    </div>

    <!-- Center: canvas -->
    <div class="panel panel-canvas">
      <slot name="page-editor" />
    </div>

    <!-- Right: property editor -->
    <div
      class="panel panel-props"
      :style="{ width: (propertyPanelWidth ?? 280) + 'px' }"
    >
      <div class="props-panel-title">属性编辑器</div>
      <div class="props-panel-body">
        <slot name="node-editor" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.editor-layout {
  display: flex;
  flex: 1;
  height: 100%;
  overflow: hidden;
  gap: 1px;
  background: #e8e8e8;
}

.panel {
  display: flex;
  flex-direction: column;
  background: #fff;
  overflow: hidden;
}

.panel-library { flex-shrink: 0; }
.panel-canvas  { flex: 1; min-width: 400px; }
.panel-props   { flex-shrink: 0; }

.props-panel-title {
  padding: 8px 12px;
  font-size: 12px;
  font-weight: 600;
  color: #8c8c8c;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  border-bottom: 1px solid #f0f0f0;
  flex-shrink: 0;
  background: #fafafa;
}

.props-panel-body {
  flex: 1;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}
</style>
