<template>
  <div class="brand-picker">
    <div class="brand-picker__group">
      <a-input
        class="brand-picker__input"
        :value="displayText"
        :placeholder="placeholder"
        readonly
        :disabled="disabled"
        @click="openModal"
      >
        <template #prefix>
          <TagsOutlined />
        </template>
      </a-input>
      <a-button class="brand-picker__action" :disabled="disabled" @click="openModal">
        <template #icon><SearchOutlined /></template>
        选择
      </a-button>
      <a-button
        v-if="toStringId(modelValue) !== undefined && !disabled"
        class="brand-picker__action"
        danger
        @click="handleClear"
      >
        <template #icon><CloseOutlined /></template>
      </a-button>
    </div>

    <a-modal
      v-model:open="visible"
      title="选择品牌"
      width="900px"
      :destroy-on-close="true"
      :ok-button-props="{ disabled: !tempSelectedId }"
      @ok="handleConfirm"
      @cancel="handleCancel"
    >
      <a-form :model="queryParams" layout="inline" style="margin-bottom:12px">
        <a-form-item label="品牌名称">
          <a-input
            v-model:value="queryParams.name"
            placeholder="品牌名称"
            allow-clear
            style="width:180px"
            @press-enter="handleSearch"
          />
        </a-form-item>
        <a-form-item label="品牌编码">
          <a-input
            v-model:value="queryParams.code"
            placeholder="品牌编码"
            allow-clear
            style="width:180px"
            @press-enter="handleSearch"
          />
        </a-form-item>
        <a-form-item label="首字母">
          <a-input
            v-model:value="queryParams.firstLetter"
            placeholder="首字母"
            allow-clear
            style="width:120px"
            @press-enter="handleSearch"
          />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="handleSearch" :loading="loading">
            <template #icon><SearchOutlined /></template>
            查询
          </a-button>
          <a-button style="margin-left:8px" @click="handleReset">
            <template #icon><ReloadOutlined /></template>
            重置
          </a-button>
        </a-form-item>
      </a-form>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        :row-selection="rowSelection"
        :custom-row="customRow"
        :row-key="getRowKey"
        size="small"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === 1 ? 'green' : 'red'">
              {{ record.status === 1 ? '正常' : '冻结' }}
            </a-tag>
          </template>
        </template>
      </a-table>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue'
import { message } from 'ant-design-vue'
import { TagsOutlined, SearchOutlined, ReloadOutlined, CloseOutlined } from '@ant-design/icons-vue'
import {
  getBrandPage,
  type MallProductBrandVo,
  type MallProductBrandQuery
} from '@/api/mall/mallproductbrand.ts'

interface Props {
  modelValue?: string | number | bigint | object | null
  placeholder?: string
  disabled?: boolean
  initialBrand?: MallProductBrandVo | null
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: undefined,
  placeholder: '请选择品牌',
  disabled: false,
  initialBrand: null
})

const emit = defineEmits<{
  (e: 'update:modelValue', value: string | undefined): void
  (e: 'change', value: string | undefined, brand?: MallProductBrandVo): void
}>()

const toStringId = (val: unknown): string | undefined => {
  if (val === null || val === undefined || val === '') return undefined
  if (typeof val === 'string') return val
  if (typeof val === 'number' || typeof val === 'bigint') return String(val)
  if (typeof val === 'object') {
    const str = (val as any).toString?.()
    return str ? String(str) : undefined
  }
  return undefined
}

const visible = ref(false)
const loading = ref(false)
const dataSource = ref<MallProductBrandVo[]>([])
const tempSelectedId = ref<string | undefined>(undefined)
const tempSelectedBrand = ref<MallProductBrandVo | undefined>(undefined)
const displayBrand = ref<MallProductBrandVo | undefined>(undefined)

const queryParams = reactive<MallProductBrandQuery>({
  page: 1,
  pageSize: 10,
  name: undefined,
  code: undefined,
  firstLetter: undefined,
  status: undefined
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
  pageSizeOptions: ['10', '20', '50'],
  showTotal: (total: number) => `共 ${total} 条`
})

const columns = [
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    width: 100,
    customRender: ({ record }: { record: MallProductBrandVo }) => toStringId(record.id) ?? ''
  },
  { title: '品牌名称', dataIndex: 'name', key: 'name' },
  { title: '品牌编码', dataIndex: 'code', key: 'code' },
  { title: '首字母', dataIndex: 'firstLetter', key: 'firstLetter', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 80 }
]

const getRowKey = (record: MallProductBrandVo) => toStringId(record.id) ?? ''

const displayText = computed(() => {
  const id = toStringId(props.modelValue)
  if (!id) return ''
  if (displayBrand.value?.id != null) {
    const dispId = toStringId(displayBrand.value.id)
    const name = displayBrand.value.name
    return name ? `${name} (ID: ${dispId})` : `ID: ${dispId}`
  }
  return `ID: ${id}`
})

const rowSelection = computed(() => ({
  type: 'radio' as const,
  selectedRowKeys: tempSelectedId.value ? [tempSelectedId.value] : [],
  onChange: (keys: (string | number)[], rows: MallProductBrandVo[]) => {
    tempSelectedId.value = keys.length > 0 ? String(keys[0]) : undefined
    tempSelectedBrand.value = rows[0]
  }
}))

const customRow = (record: MallProductBrandVo) => ({
  onClick: () => {
    const id = toStringId(record.id)
    if (id) {
      tempSelectedId.value = id
      tempSelectedBrand.value = record
    }
  }
})

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getBrandPage({
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize
    })
    if (res.code === 200) {
      dataSource.value = res.data?.data || []
      pagination.total = res.data?.total || 0
    }
  } catch (err) {
    console.error('Load brand list error:', err)
  } finally {
    loading.value = false
  }
}

const openModal = () => {
  if (props.disabled) return
  visible.value = true
  tempSelectedId.value = toStringId(props.modelValue)
  tempSelectedBrand.value = displayBrand.value
  pagination.current = 1
  loadData()
}

const handleSearch = () => {
  pagination.current = 1
  loadData()
}

const handleReset = () => {
  queryParams.name = undefined
  queryParams.code = undefined
  queryParams.firstLetter = undefined
  queryParams.status = undefined
  pagination.current = 1
  loadData()
}

const handleTableChange = (pag: any) => {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  loadData()
}

const handleConfirm = () => {
  if (!tempSelectedId.value) {
    message.warning('请先选择品牌')
    return
  }
  displayBrand.value = tempSelectedBrand.value
  emit('update:modelValue', tempSelectedId.value)
  emit('change', tempSelectedId.value, tempSelectedBrand.value)
  visible.value = false
}

const handleCancel = () => {
  visible.value = false
}

const handleClear = () => {
  displayBrand.value = undefined
  tempSelectedId.value = undefined
  tempSelectedBrand.value = undefined
  emit('update:modelValue', undefined)
  emit('change', undefined, undefined)
}

watch(
  () => props.modelValue,
  (val) => {
    const strVal = toStringId(val)
    if (!strVal) {
      displayBrand.value = undefined
      return
    }
    if (displayBrand.value && toStringId(displayBrand.value.id) === strVal) return
    const initBrand = props.initialBrand
    if (initBrand && toStringId(initBrand.id) === strVal) {
      displayBrand.value = initBrand
    }
  },
  { immediate: false }
)

watch(
  () => props.initialBrand,
  (val) => {
    const id = toStringId(props.modelValue)
    if (!id) return
    if (val && toStringId(val.id) === id) {
      displayBrand.value = val
    }
  },
  { immediate: true }
)
</script>

<style scoped>
.brand-picker {
  width: 100%;
}

.brand-picker__group {
  display: flex;
  width: 100%;
}

.brand-picker__input {
  flex: 1 1 0;
  min-width: 0;
  border-start-end-radius: 0;
  border-end-end-radius: 0;
}

.brand-picker__action {
  flex: none;
  margin-left: -1px;
  border-radius: 0;
}

.brand-picker__action:last-child {
  border-start-end-radius: 6px;
  border-end-end-radius: 6px;
}
</style>
