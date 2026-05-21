<template>
  <div class="custom-radio-group">
    <div 
      v-for="option in options" 
      :key="option.value"
      class="radio-item" 
      :class="{ active: value === option.value }" 
      @click="handleSelect(option.value)"
    >
      <component v-if="option.icon" :is="option.icon" />
      {{ option.label }}
    </div>
  </div>
</template>

<script setup lang="ts">
export interface SegmentedOption {
  value: number | string;
  label: string;
  icon?: any; // Vue component
}

const props = defineProps<{
  value: number | string | undefined;
  options: SegmentedOption[];
}>()

const emit = defineEmits<{
  (e: 'update:value', value: number | string): void;
  (e: 'change', value: number | string): void;
}>()

const handleSelect = (val: number | string) => {
  emit('update:value', val)
  emit('change', val)
}
</script>

<style scoped>
/* 自定义单选组 */
.custom-radio-group {
  display: flex;
  gap: 8px;
  background: #f8fafc;
  padding: 4px;
  border-radius: 12px;
  border: 1px solid #e2e8f0;
}
.radio-item {
  flex: 1;
  text-align: center;
  padding: 6px 0;
  border-radius: 8px;
  font-size: 13px;
  font-weight: 600;
  color: #64748b;
  cursor: pointer;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
}
.radio-item.active {
  background: white;
  color: #0f172a;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
}
</style>
