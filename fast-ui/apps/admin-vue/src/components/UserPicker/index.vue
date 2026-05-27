<template>
  <div class="user-picker">
    <a-input
      :value="displayText"
      :placeholder="placeholder"
      readonly
      allow-clear
      @click="openModal"
      @change="handleClear"
    >
      <template #addonAfter>
        <a-button type="primary" @click="openModal" :disabled="disabled">选择</a-button>
      </template>
    </a-input>

    <a-modal
      v-model:open="modalOpen"
      :title="multiple ? '选择用户（多选）' : '选择用户'"
      width="860px"
      :destroy-on-close="true"
      @ok="handleOk"
      @cancel="handleCancel"
      :ok-button-props="{ disabled: selectedRowKeys.length === 0 }"
    >
      <div class="toolbar">
        <a-input-search
          v-model:value="keyword"
          placeholder="输入用户名/昵称搜索"
          allow-clear
          @search="handleSearch"
          @change="handleKeywordChange"
        />
      </div>

      <a-table
        row-key="id"
        :data-source="userList"
        :columns="columns"
        :loading="loading"
        :pagination="pagination"
        :row-selection="rowSelection"
        @change="handleTableChange"
        size="middle"
      />
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue'
import request from '@/utils/axios'

type ModelValue = number | number[] | null | undefined

interface UserPickerVo {
  id?: number
  username: string
  nickname: string
  email?: string
  phone?: string
  avatar?: string
}

interface PageVo<T> {
  data: T[]
  total: number
}

interface ResultVo<T> {
  code: number
  data: T
  msg: string
}

const getPickerPage = (data: { page: number; pageSize: number; keyword?: string }) => {
  return request<ResultVo<PageVo<UserPickerVo>>>({
    url: '/sys/users/picker/page',
    method: 'POST',
    data,
  })
}

const getPickerById = (id: number) => {
  return request<ResultVo<UserPickerVo>>({
    url: `/sys/users/picker/${id}`,
    method: 'GET',
  })
}

const props = withDefaults(defineProps<{
  value: ModelValue
  multiple?: boolean
  placeholder?: string
  disabled?: boolean
  limit?: number
}>(), {
  multiple: false,
  placeholder: '请选择用户',
  disabled: false,
  limit: 10,
})

const emit = defineEmits<{
  (e: 'update:value', value: ModelValue): void
  (e: 'change', value: ModelValue): void
  (e: 'select', value: UserPickerVo | UserPickerVo[] | null): void
}>()

const modalOpen = ref(false)
const keyword = ref('')
const userList = ref<UserPickerVo[]>([])
const total = ref(0)
const loading = ref(false)

const query = reactive({
  page: 1,
  pageSize: props.limit,
})

const selectedRowKeys = ref<(string | number)[]>([])
const selectedStore = reactive<Record<string, UserPickerVo>>({})

const columns = [
  { title: '用户名', dataIndex: 'username', key: 'username', width: 180 },
  { title: '昵称', dataIndex: 'nickname', key: 'nickname', width: 180 },
  { title: '邮箱', dataIndex: 'email', key: 'email' },
  { title: '手机号', dataIndex: 'phone', key: 'phone', width: 140 },
]

const normalizeIds = (val: ModelValue): number[] => {
  if (Array.isArray(val)) {
    return val.map((v) => Number(v)).filter((v) => Number.isFinite(v))
  }
  const id = Number(val)
  return Number.isFinite(id) ? [id] : []
}

const syncSelectedFromProps = async () => {
  const ids = normalizeIds(props.value)
  selectedRowKeys.value = props.multiple ? ids : ids.slice(0, 1)

  const needFetch = ids.filter((id) => !selectedStore[String(id)])
  if (!needFetch.length) return

  const resList = await Promise.all(
    needFetch.slice(0, 20).map(async (id) => {
      try {
        const res = await getPickerById(id)
        if (res.code === 200) return res.data
      } catch {
        return undefined
      }
      return undefined
    })
  )
  resList.filter(Boolean).forEach((u) => {
    if (u?.id !== undefined && u?.id !== null) {
      selectedStore[String(u.id)] = u
    }
  })
}

watch(() => props.value, () => {
  syncSelectedFromProps()
}, { immediate: true })

const selectedUsers = computed(() => {
  return selectedRowKeys.value
    .map((k) => selectedStore[String(k)])
    .filter(Boolean)
})

const displayText = computed(() => {
  if (!selectedUsers.value.length) return ''
  if (!props.multiple) {
    const user = selectedUsers.value[0]
    return user.nickname ? `${user.username}(${user.nickname})` : user.username
  }
  return selectedUsers.value
    .map((u) => (u.nickname ? `${u.username}(${u.nickname})` : u.username))
    .join(', ')
})

const pagination = computed(() => ({
  current: query.page,
  pageSize: query.pageSize,
  total: total.value,
  showSizeChanger: true,
  showTotal: (t: number) => `共 ${t} 条`,
}))

const rowSelection = computed(() => ({
  type: props.multiple ? 'checkbox' : 'radio',
  selectedRowKeys: selectedRowKeys.value,
  onChange: (keys: (string | number)[], rows: UserPickerVo[]) => {
    const nextKeys = props.multiple ? keys : keys.slice(0, 1)
    rows.forEach((row) => {
      if (row?.id !== undefined && row?.id !== null) {
        selectedStore[String(row.id)] = row
      }
    })
    nextKeys.forEach((k) => {
      const key = String(k)
      if (selectedStore[key]) return
      const found = userList.value.find((u) => String(u.id) === key)
      if (found) selectedStore[key] = found
    })
    Object.keys(selectedStore).forEach((k) => {
      if (!nextKeys.map(String).includes(k)) {
        delete selectedStore[k]
      }
    })
    selectedRowKeys.value = nextKeys
  },
}))

const fetchPage = async () => {
  loading.value = true
  try {
    const res = await getPickerPage({
      page: Math.max(0, query.page - 1),
      pageSize: query.pageSize,
      keyword: keyword.value || undefined,
    })
    if (res.code === 200) {
      userList.value = res.data?.data || []
      total.value = res.data?.total || 0
    }
  } finally {
    loading.value = false
  }
}

watch(modalOpen, (open) => {
  if (open) fetchPage()
})

const openModal = () => {
  if (props.disabled) return
  modalOpen.value = true
}

const handleSearch = () => {
  query.page = 1
  fetchPage()
}

const handleKeywordChange = () => {
  if (keyword.value) return
  query.page = 1
  fetchPage()
}

const handleTableChange = (pag: any) => {
  query.page = pag?.current || 1
  query.pageSize = pag?.pageSize || props.limit
  fetchPage()
}

const handleOk = () => {
  if (!selectedRowKeys.value.length) return

  if (props.multiple) {
    const ids = selectedRowKeys.value.map((k) => Number(k)).filter((v) => Number.isFinite(v))
    emit('update:value', ids)
    emit('change', ids)
    emit('select', selectedUsers.value)
  } else {
    const id = Number(selectedRowKeys.value[0])
    const nextValue = Number.isFinite(id) ? id : undefined
    emit('update:value', nextValue)
    emit('change', nextValue)
    emit('select', selectedUsers.value[0] || null)
  }
  modalOpen.value = false
}

const handleCancel = () => {
  modalOpen.value = false
}

const handleClear = (e: any) => {
  if (e?.target?.value) return
  Object.keys(selectedStore).forEach((k) => delete selectedStore[k])
  selectedRowKeys.value = []
  emit('update:value', props.multiple ? [] : undefined)
  emit('change', props.multiple ? [] : undefined)
  emit('select', null)
}
</script>

<style scoped>
.toolbar {
  margin-bottom: 12px;
}
</style>
