<template>
  <a-select
    v-model:value="selectedValue"
    show-search
    :placeholder="placeholder"
    :filter-option="false"
    :not-found-content="loading ? undefined : '未找到用户'"
    @search="handleSearch"
    @change="handleChange"
    allow-clear
    class="user-select"
  >
    <template v-if="loading" #notFoundContent>
      <a-spin size="small" />
    </template>
    <a-select-option
      v-for="user in userList"
      :key="normalizeId(user.id)"
      :value="normalizeId(user.id)"
    >
      <div class="user-option">
        <span class="username">{{ user.username }}</span>
        <span class="nickname" v-if="user.nickname">({{ user.nickname }})</span>
      </div>
    </a-select-option>
  </a-select>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { searchUsers } from '@/api/system/sysusers.ts'
import type { SysUsersVo } from '@/api/system/sysusers.ts'

const props = defineProps<{
  value: string | number | null | undefined
  placeholder?: string
}>()

const emit = defineEmits<{
  (e: 'update:value', value: string | number | null | undefined): void
  (e: 'change', value: string | number | null | undefined): void
}>()

const selectedValue = ref(props.value)
const userList = ref<SysUsersVo[]>([])
const loading = ref(false)
let timer: any = null

const normalizeId = (value: unknown): string | undefined => {
  if (value === null || value === undefined || value === '') {
    return undefined
  }
  return String(value)
}

watch(() => props.value, (val) => {
  selectedValue.value = normalizeId(val)
}, { immediate: true })

const fetchUser = async (keyword: string) => {
  if (!keyword) {
    userList.value = []
    return
  }
  loading.value = true
  try {
    const res = await searchUsers(keyword)
    if (res.code === 200) {
      userList.value = res.data || []
    }
  } catch (err) {
    console.error('Fetch users error:', err)
  } finally {
    loading.value = false
  }
}

const handleSearch = (val: string) => {
  if (timer) clearTimeout(timer)
  timer = setTimeout(() => {
    fetchUser(val)
  }, 500)
}

const handleChange = (val: any) => {
  const nextValue = normalizeId(val)
  emit('update:value', nextValue)
  emit('change', nextValue)
}
</script>

<style scoped>
.user-select {
  width: 100%;
}
.user-option {
  display: flex;
  gap: 8px;
  align-items: center;
}
.username {
  font-weight: 500;
}
.nickname {
  color: #94a3b8;
  font-size: 12px;
}
</style>
