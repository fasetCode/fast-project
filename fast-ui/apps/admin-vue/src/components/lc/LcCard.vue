<script setup lang="ts">
const props = withDefaults(defineProps<{
  title?: string
  bgColor?: string
  borderRadius?: number
  bordered?: boolean
  shadow?: boolean
  padding?: number
}>(), {
  title: '',
  bgColor: '#fff',
  borderRadius: 8,
  bordered: true,
  shadow: false,
  padding: 16,
})

defineEmits<{
  dropEmpty: [event: DragEvent]
  dragOverEmpty: [event: DragEvent]
}>()
</script>

<template>
  <div
    class="lc-card"
    :style="{
      backgroundColor: bgColor,
      borderRadius: `${borderRadius}px`,
      border: bordered ? '1px solid #e8e8e8' : 'none',
      boxShadow: shadow ? '0 2px 12px rgba(0,0,0,0.1)' : 'none',
      overflow: 'hidden',
    }"
  >
    <div
      v-if="title"
      class="lc-card-header"
    >{{ title }}</div>
    <div
      class="lc-card-body"
      :style="{ padding: `${padding}px` }"
    >
      <slot />
      <div
        v-if="$slots.default === undefined"
        class="lc-card-empty"
        @dragover="$emit('dragOverEmpty', $event)"
        @drop.stop="$emit('dropEmpty', $event)"
      >↓ 拖入子组件</div>
    </div>
  </div>
</template>

<style scoped>
.lc-card {
  overflow: hidden;
}

.lc-card-header {
  padding: 10px 16px;
  font-size: 14px;
  font-weight: 600;
  color: #262626;
  border-bottom: 1px solid #f0f0f0;
}

.lc-card-body {
  min-height: 48px;
}

.lc-card-empty {
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

.lc-card-empty:hover {
  border-color: #91caff;
  background: #f0f8ff;
  color: #1677ff;
}
</style>
