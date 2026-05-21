<template>
  <div class="status-cards">
    <div 
      v-for="option in options" 
      :key="option.value"
      class="status-card" 
      :class="{ active: value === option.value }"
      @click="handleSelect(option.value)"
    >
      <div class="sc-icon" :class="option.type">
        <component :is="option.icon" />
      </div>
      <div class="sc-text">
        <strong>{{ option.title }}</strong>
        <span>{{ option.desc }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
export interface StatusOption {
  value: number | string;
  title: string;
  desc: string;
  icon: any; // Vue component
  type: 'success' | 'error' | 'warning' | 'info' | 'default';
}

const props = defineProps<{
  value: number | string | undefined;
  options: StatusOption[];
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
.status-cards {
  display: flex;
  gap: 16px;
  width: 100%;
}
.status-card {
  flex: 1;
  border: 2px solid #e2e8f0;
  border-radius: 16px;
  padding: 16px;
  display: flex;
  align-items: flex-start;
  gap: 12px;
  cursor: pointer;
  transition: all 0.3s;
  background: white;
}
.status-card:hover {
  border-color: #cbd5e1;
}
.status-card.active {
  border-color: var(--app-primary-color);
  background: var(--app-primary-color-light);
}
.sc-icon {
  font-size: 20px;
  margin-top: 2px;
}
.sc-icon.success { color: #10b981; }
.sc-icon.error { color: #ef4444; }
.sc-icon.warning { color: #f59e0b; }
.sc-icon.info { color: var(--app-primary-color); }
.sc-icon.default { color: #64748b; }

.sc-text {
  display: flex;
  flex-direction: column;
}
.sc-text strong {
  color: #0f172a;
  font-size: 14px;
}
.sc-text span {
  color: #64748b;
  font-size: 12px;
  margin-top: 2px;
}
</style>
