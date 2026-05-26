<template>
  <div class="content-crud-page">
    <a-card :bordered="false">
      <template #title>
        <div class="page-title">{{ title }}</div>
      </template>
      <template #extra>
        <a-space>
          <a-input v-model:value="keyword" placeholder="关键字过滤（前端）" allow-clear style="width: 220px" />
          <a-button @click="loadData" :loading="loading">刷新</a-button>
          <a-button v-if="canAdd" type="primary" @click="openAdd">新增</a-button>
          <a-button v-if="canDelete" danger :disabled="selectedRowKeys.length === 0" @click="batchDelete">
            批量删除
          </a-button>
        </a-space>
      </template>

      <a-table
        row-key="id"
        :data-source="filteredData"
        :columns="columns"
        :loading="loading"
        :pagination="pagination"
        :row-selection="rowSelection"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button v-if="canUpdate" type="link" @click="openEdit(record)">编辑</a-button>
              <a-button v-if="canDelete" type="link" danger @click="deleteOne(record)">删除</a-button>
            </a-space>
          </template>

          <template v-else>
            <span v-if="dictFields[column.key]">
              {{ getDictLabel(dictFields[column.key], record[column.key]) || record[column.key] }}
            </span>
            <span v-else>
              {{ formatValue(record[column.key]) }}
            </span>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal v-model:open="modalOpen" :title="modalTitle" @ok="submit" :confirm-loading="submitLoading" width="720px">
      <a-form ref="formRef" :model="formModel" layout="vertical">
        <a-row :gutter="16">
          <a-col v-for="field in editableFields" :key="field" :span="fieldSpan(field)">
            <a-form-item :label="fieldLabels[field] || field" :name="field">
              <a-select
                v-if="dictFields[field]"
                v-model:value="formModel[field]"
                allow-clear
                style="width: 100%"
              >
                <a-select-option v-for="d in getDictData(dictFields[field])" :key="d.value" :value="castDictValue(d.value)">
                  {{ d.label }}
                </a-select-option>
              </a-select>
              <a-switch v-else-if="isBooleanField(field)" v-model:checked="formModel[field]" />
              <a-input-number
                v-else-if="isNumberField(field)"
                v-model:value="formModel[field]"
                style="width: 100%"
                :min="0"
              />
              <a-textarea v-else-if="isTextareaField(field)" v-model:value="formModel[field]" :rows="4" />
              <a-input v-else v-model:value="formModel[field]" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { Modal, message } from 'ant-design-vue'
import type { FormInstance, TableColumnType } from 'ant-design-vue'
import { createCrudApi } from '@/api/content/crud'
import type { PageQuery } from '@/api/content/crud'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'

type RecordAny = Record<string, any>

const props = withDefaults(
  defineProps<{
    title: string
    baseUrl: string
    permissionPrefix?: string
    dictFields?: Record<string, string>
    hiddenFields?: string[]
    textareaFields?: string[]
  }>(),
  {
    permissionPrefix: '',
    dictFields: () => ({}),
    hiddenFields: () => [],
    textareaFields: () => [],
  }
)

const permissionStore = usePermissionStore()

const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

const loadButtonPermissions = async () => {
  if (!props.permissionPrefix) return
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission(`${props.permissionPrefix}:add`)
  canUpdate.value = hasButtonPermission(`${props.permissionPrefix}:update`)
  canDelete.value = hasButtonPermission(`${props.permissionPrefix}:delete`)
}

const api = createCrudApi<RecordAny>(props.baseUrl)

const loading = ref(false)
const submitLoading = ref(false)
const keyword = ref('')

const dataSource = ref<RecordAny[]>([])

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
})

const selectedRowKeys = ref<(string | number)[]>([])
const rowSelection = computed(() => ({
  selectedRowKeys: selectedRowKeys.value,
  onChange: (keys: (string | number)[]) => (selectedRowKeys.value = keys),
}))

const baseEntityFields = new Set(['id', 'createBy', 'createTime', 'updateBy', 'updateTime', 'deleted'])

const effectiveHiddenFields = computed(() => new Set([...(props.hiddenFields || [])]))

const fieldLabels = computed<Record<string, string>>(() => {
  const m: Record<string, string> = {}
  return m
})

const inferFields = (rows: RecordAny[]) => {
  const keys = new Set<string>()
  rows.forEach((r) => Object.keys(r || {}).forEach((k) => keys.add(k)))
  const list = Array.from(keys)
    .filter((k) => !effectiveHiddenFields.value.has(k))
    .sort((a, b) => (a === 'id' ? -1 : b === 'id' ? 1 : a.localeCompare(b)))
  return list
}

const allFields = ref<string[]>([])

const columns = computed<TableColumnType[]>(() => {
  const cols: TableColumnType[] = []
  const fields = allFields.value.length ? allFields.value : ['id']
  fields.forEach((f) => {
    cols.push({
      title: fieldLabels.value[f] || f,
      key: f,
      dataIndex: f,
      ellipsis: true,
    } as TableColumnType)
  })
  cols.push({
    title: '操作',
    key: 'action',
    fixed: 'right',
    width: 140,
  } as TableColumnType)
  return cols
})

const filteredData = computed(() => {
  const k = keyword.value?.trim()
  if (!k) return dataSource.value
  const lower = k.toLowerCase()
  return dataSource.value.filter((r) =>
    Object.values(r || {}).some((v) => String(v ?? '').toLowerCase().includes(lower))
  )
})

const handleTableChange = (pager: any) => {
  pagination.current = pager.current
  pagination.pageSize = pager.pageSize
  loadData()
}

const loadData = async () => {
  loading.value = true
  try {
    const query: PageQuery = { page: pagination.current - 1, pageSize: pagination.pageSize }
    const res = await api.page(query)
    const list = res?.data?.data || []
    pagination.total = res?.data?.total || 0
    dataSource.value = list
    allFields.value = inferFields(list)
  } finally {
    loading.value = false
  }
}

const modalOpen = ref(false)
const isEdit = ref(false)
const modalTitle = computed(() => (isEdit.value ? `编辑${props.title}` : `新增${props.title}`))
const formRef = ref<FormInstance>()
const formModel = reactive<RecordAny>({})

const editableFields = computed(() =>
  allFields.value.filter((f) => !baseEntityFields.has(f) && f !== 'action')
)

const resetForm = () => {
  Object.keys(formModel).forEach((k) => delete formModel[k])
}

const openAdd = () => {
  isEdit.value = false
  resetForm()
  editableFields.value.forEach((f) => {
    formModel[f] = undefined
  })
  modalOpen.value = true
}

const openEdit = (record: RecordAny) => {
  isEdit.value = true
  resetForm()
  Object.keys(record || {}).forEach((k) => {
    if (!baseEntityFields.has(k) && k !== 'action') {
      formModel[k] = record[k]
    }
  })
  formModel.id = record.id
  modalOpen.value = true
}

const submit = async () => {
  submitLoading.value = true
  try {
    if (isEdit.value) {
      await api.update(formModel)
    } else {
      await api.create(formModel)
    }
    message.success('操作成功')
    modalOpen.value = false
    await loadData()
  } finally {
    submitLoading.value = false
  }
}

const deleteOne = (record: RecordAny) => {
  Modal.confirm({
    title: '确认删除？',
    onOk: async () => {
      await api.delete(Number(record.id))
      message.success('删除成功')
      await loadData()
    },
  })
}

const batchDelete = () => {
  Modal.confirm({
    title: '确认批量删除？',
    onOk: async () => {
      await api.batchDelete(selectedRowKeys.value.map((x) => Number(x)))
      selectedRowKeys.value = []
      message.success('删除成功')
      await loadData()
    },
  })
}

const fieldSpan = (field: string) => {
  if (isTextareaField(field)) return 24
  return 12
}

const isBooleanField = (field: string) => {
  return typeof formModel[field] === 'boolean'
}

const isNumberField = (field: string) => {
  const v = formModel[field]
  if (typeof v === 'number') return true
  return /Id$/.test(field) || /Count$/.test(field) || field === 'sort' || field.endsWith('Status') || field.endsWith('Type')
}

const isTextareaField = (field: string) => {
  if (props.textareaFields.includes(field)) return true
  return (
    field === 'content' ||
    field === 'contentHtml' ||
    field === 'reason' ||
    field === 'summary' ||
    field === 'config'
  )
}

const castDictValue = (val: string) => {
  const n = Number(val)
  if (!Number.isNaN(n) && String(n) === val) return n
  return val
}

const formatValue = (val: any) => {
  if (val === null || val === undefined) return '-'
  if (typeof val === 'boolean') return val ? '是' : '否'
  return String(val)
}

onMounted(async () => {
  await loadButtonPermissions()
  await loadData()
})
</script>

<style scoped>
.page-title {
  font-weight: 600;
}
</style>

