<template>
  <div class="shop-picker">
    <div class="shop-picker__group">
      <a-input
        class="shop-picker__input"
        :value="displayText"
        :placeholder="placeholder"
        readonly
        :disabled="disabled"
        @click="openModal"
      >
        <template #prefix>
          <ShopOutlined />
        </template>
      </a-input>
      <a-button class="shop-picker__action" :disabled="disabled" @click="openModal">
        <template #icon><SearchOutlined /></template>
        选择
      </a-button>
      <a-button
        v-if="toStringId(modelValue) !== undefined && !disabled"
        class="shop-picker__action"
        danger
        @click="handleClear"
      >
        <template #icon><CloseOutlined /></template>
      </a-button>
    </div>

    <a-modal
      v-model:open="visible"
      title="选择店铺"
      width="900px"
      :destroy-on-close="true"
      :ok-button-props="{ disabled: tempSelectedId === undefined || tempSelectedId === null }"
      @ok="handleConfirm"
      @cancel="handleCancel"
    >
      <a-form :model="queryParams" layout="inline" style="margin-bottom:12px">
        <a-form-item label="店铺名称">
          <a-input
            v-model:value="queryParams.name"
            placeholder="店铺名称"
            allow-clear
            style="width:180px"
            @press-enter="handleSearch"
          />
        </a-form-item>
        <a-form-item label="店铺编码">
          <a-input
            v-model:value="queryParams.code"
            placeholder="店铺编码"
            allow-clear
            style="width:180px"
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
import { ref, reactive, computed, watch } from 'vue'
import { message } from 'ant-design-vue'
import { ShopOutlined, SearchOutlined, ReloadOutlined, CloseOutlined } from '@ant-design/icons-vue'
import {
  getShopPage,
  type MallShopVo,
  type MallShopQuery
} from '@/api/mall/mallshop.ts'

interface Props {
  modelValue?: number | string | bigint | object | null
  placeholder?: string
  disabled?: boolean
  initialShop?: MallShopVo | null
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: undefined,
  placeholder: '请选择店铺',
  disabled: false,
  initialShop: null
})

const emit = defineEmits<{
  (e: 'update:modelValue', value: string | undefined): void
  (e: 'change', value: string | undefined, shop?: MallShopVo): void
}>()

// 后端 Long ID 可能超过 JS 安全整数，统一按字符串处理
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
const dataSource = ref<MallShopVo[]>([])
const tempSelectedId = ref<string | undefined>(undefined)
const tempSelectedShop = ref<MallShopVo | undefined>(undefined)
const displayShop = ref<MallShopVo | undefined>(undefined)

const queryParams = reactive<MallShopQuery>({
  page: 1,
  pageSize: 10,
  name: undefined,
  code: undefined,
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
    width: 80,
    customRender: ({ record }: { record: MallShopVo }) => toStringId(record.id) ?? ''
  },
  { title: '店铺名称', dataIndex: 'name', key: 'name' },
  { title: '店铺编码', dataIndex: 'code', key: 'code' },
  { title: '联系人', dataIndex: 'contactName', key: 'contactName' },
  { title: '联系电话', dataIndex: 'contactPhone', key: 'contactPhone' },
  { title: '状态', dataIndex: 'status', key: 'status', width: 80 }
]

const getRowKey = (record: MallShopVo) => toStringId(record.id) ?? ''

const displayText = computed(() => {
  const id = toStringId(props.modelValue)
  if (id === undefined) return ''
  if (displayShop.value && displayShop.value.id != null) {
    const dispId = toStringId(displayShop.value.id) ?? displayShop.value.id
    const name = displayShop.value.name
    return name ? `${name} (ID: ${dispId})` : `ID: ${dispId}`
  }
  return `ID: ${id}`
})

const rowSelection = computed(() => ({
  type: 'radio' as const,
  selectedRowKeys: tempSelectedId.value !== undefined ? [tempSelectedId.value] : [],
  onChange: (keys: (string | number)[], rows: MallShopVo[]) => {
    tempSelectedId.value = keys.length > 0 ? String(keys[0]) : undefined
    tempSelectedShop.value = rows[0]
  }
}))

const customRow = (record: MallShopVo) => ({
  onClick: () => {
    if (record.id != null) {
      tempSelectedId.value = toStringId(record.id)
      tempSelectedShop.value = record
    }
  }
})

const loadData = async () => {
  loading.value = true
  try {
    // 后端分页从 0 开始，Antd Table current 从 1 开始，这里需要 -1
    const res: any = await getShopPage({
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize
    })
    if (res.code === 200) {
      dataSource.value = res.data?.data || []
      pagination.total = res.data?.total || 0
    }
  } catch (err) {
    console.error('Load shop list error:', err)
  } finally {
    loading.value = false
  }
}

const openModal = () => {
  if (props.disabled) return
  visible.value = true
  tempSelectedId.value = toStringId(props.modelValue)
  tempSelectedShop.value = displayShop.value
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
  if (tempSelectedId.value === undefined || tempSelectedId.value === null) {
    message.warning('请先选择店铺')
    return
  }
  displayShop.value = tempSelectedShop.value
  emit('update:modelValue', tempSelectedId.value)
  emit('change', tempSelectedId.value, tempSelectedShop.value)
  visible.value = false
}

const handleCancel = () => {
  visible.value = false
}

const handleClear = () => {
  displayShop.value = undefined
  tempSelectedId.value = undefined
  tempSelectedShop.value = undefined
  emit('update:modelValue', undefined)
  emit('change', undefined, undefined)
}

watch(
  () => props.modelValue,
  (val) => {
    const stringVal = toStringId(val)
    if (!stringVal) {
      displayShop.value = undefined
      return
    }
    if (displayShop.value && toStringId(displayShop.value.id) === stringVal) return
    const initShop = props.initialShop
    if (initShop && toStringId(initShop.id) === stringVal) {
      displayShop.value = initShop
    }
  },
  { immediate: false }
)

watch(
  () => props.initialShop,
  (val) => {
    const id = toStringId(props.modelValue)
    if (!id) return
    if (val && toStringId(val.id) === id) {
      displayShop.value = val
    }
  },
  { immediate: true }
)
</script>

<style scoped>
.shop-picker {
  width: 100%;
}

.shop-picker__group {
  display: flex;
  width: 100%;
}

.shop-picker__input {
  flex: 1 1 0;
  min-width: 0;
  border-start-end-radius: 0;
  border-end-end-radius: 0;
}

.shop-picker__action {
  flex: none;
  margin-left: -1px;
  border-radius: 0;
}

.shop-picker__action:last-child {
  border-start-end-radius: 6px;
  border-end-end-radius: 6px;
}
</style>
