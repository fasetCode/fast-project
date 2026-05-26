<template>
  <a-space :size="8" style="width: 100%">
    <input v-model="colorPickerValue" type="color" :disabled="disabled" class="color-picker" />
    <a-input v-model:value="model" :placeholder="placeholder" :disabled="disabled" allow-clear />
  </a-space>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = withDefaults(defineProps<{ value?: string; placeholder?: string; disabled?: boolean }>(), {
  value: '',
  placeholder: '',
  disabled: false,
})

const emit = defineEmits<{
  (e: 'update:value', value: string): void
  (e: 'change', value: string): void
}>()

const model = computed({
  get: () => props.value ?? '',
  set: (val: string) => {
    emit('update:value', val)
    emit('change', val)
  },
})

const isHexColor = (val: string) => /^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$/.test(val)

const colorPickerValue = computed({
  get: () => (isHexColor(model.value) ? model.value : '#1677ff'),
  set: (val: string) => {
    model.value = val
  },
})
</script>

<style scoped>
.color-picker {
  width: 32px;
  height: 32px;
  padding: 0;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  background: transparent;
  cursor: pointer;
}
.color-picker:disabled {
  cursor: not-allowed;
  opacity: 0.6;
}
</style>
