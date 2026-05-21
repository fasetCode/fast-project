<template>
  <a-select 
    :value="modelValue" 
    @update:value="handleUpdate"
    placeholder="请选择类型" 
    allow-clear
    :loading="loading"
  >
    <a-select-option v-for="type in typeOptions" :key="type.id" :value="type.id">
      {{ type.title }}
    </a-select-option>
  </a-select>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getPageTypeList } from '@/api/page/pagetype.ts'

interface TypeOption {
  id: string
  title: string
}

const props = defineProps<{
  modelValue?: string
}>()

const emit = defineEmits<{
  'update:modelValue': [value: string | undefined]
}>()

const loading = ref(false)
const typeOptions = ref<TypeOption[]>([])

// 加载类型选项
const loadTypeOptions = async () => {
  loading.value = true
  try {
    const res = await getPageTypeList()
    if (res.code === 200) {
      typeOptions.value = (res.data || []).map(item => ({
        id: String(item.id),
        title: item.title
      }))
    }
  } catch (error) {
    console.error('加载类型列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleUpdate = (value: string | undefined) => {
  emit('update:modelValue', value)
}

onMounted(() => {
  loadTypeOptions()
})
</script>
