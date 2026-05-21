<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">用户ID</span>
            <a-input-number v-model:value="queryParams.userId" placeholder="用户ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">店铺ID</span>
            <a-input-number v-model:value="queryParams.shopId" placeholder="店铺ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">商品ID</span>
            <a-input-number v-model:value="queryParams.productId" placeholder="商品ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">SKU ID</span>
            <a-input-number v-model:value="queryParams.skuId" placeholder="SKU ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-actions">
            <a-button class="icon-btn refresh-btn" @click="reset()" shape="circle" :loading="loading">
              <template #icon v-if="!loading"><ReloadOutlined /></template>
            </a-button>
            <a-button type="primary" class="gradient-btn" @click="loadData()" :loading="loading">
              <template #icon v-if="!loading"><SearchOutlined /></template>
              {{ loading ? '检索中...' : '检索' }}
            </a-button>
          </div>
        </div>
      </a-form>
    </div>

    <div class="data-board">
      <div class="board-toolbar">
        <div class="toolbar-left">
          <div class="board-tabs">
            <div class="tab active">全部购物车 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增
          </a-button>
          <a-button danger class="pill-btn danger-pill" :disabled="selectedRowKeys.length === 0" @click="handleDelete()">
            <DeleteOutlined /> 批量删除
          </a-button>
        </div>
      </div>

      <div class="table-wrapper">
        <a-table
          :columns="columns"
          :data-source="dataSource"
          :loading="loading"
          :pagination="pagination"
          :row-selection="{ selectedRowKeys, onChange: onSelectChange }"
          row-key="id"
          size="middle"
          :scroll="{ x: 1200 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'selected'">
              <a-tag :color="record.selected === 1 ? 'green' : 'default'">{{ getDictLabel('yes_no', record.selected) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑购物车' : '新增购物车'" width="700px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="12"><a-form-item label="用户ID" name="userId"><a-input-number v-model:value="formData.userId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="店铺ID" name="shopId"><a-input-number v-model:value="formData.shopId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="商品ID" name="productId"><a-input-number v-model:value="formData.productId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="SKU ID" name="skuId"><a-input-number v-model:value="formData.skuId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="单价" name="price"><a-input-number v-model:value="formData.price" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="数量" name="quantity"><a-input-number v-model:value="formData.quantity" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="是否选中" name="selected">
            <a-select v-model:value="formData.selected">
              <a-select-option v-for="item in yesNoOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="24"><a-form-item label="商品快照" name="productSnapshot"><a-textarea v-model:value="formData.productSnapshot" :rows="2" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="SKU快照" name="skuSnapshot"><a-textarea v-model:value="formData.skuSnapshot" :rows="2" /></a-form-item></a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { message, Modal } from 'ant-design-vue'
import { SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { MallCartVo, MallCartQuery, MallCartCreate, MallCartUpdate } from '@/api/mall/mallcart.ts'
import { getCartPage, createCart, updateCart, deleteCart, batchDeleteCart, getCartById } from '@/api/mall/mallcart.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const yesNoOptions = computed(() => (getDictData('yes_no') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '用户ID', dataIndex: 'userId', key: 'userId', width: 100 },
  { title: '店铺ID', dataIndex: 'shopId', key: 'shopId', width: 100 },
  { title: '商品ID', dataIndex: 'productId', key: 'productId', width: 100 },
  { title: 'SKU ID', dataIndex: 'skuId', key: 'skuId', width: 100 },
  { title: '单价', dataIndex: 'price', key: 'price', width: 100 },
  { title: '数量', dataIndex: 'quantity', key: 'quantity', width: 80 },
  { title: '已选', dataIndex: 'selected', key: 'selected', width: 100 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<MallCartVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallCartQuery>({ page: 1, pageSize: 10 })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const defaultForm = (): MallCartCreate & { id?: number } => ({ id: undefined, userId: 0, shopId: 0, productId: 0, skuId: 0, productSnapshot: '', skuSnapshot: '', price: 0, quantity: 1, selected: 1 })
const formData = reactive<MallCartCreate & { id?: number }>(defaultForm())
const rules = { userId: [{ required: true, message: '请输入用户ID', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getCartPage({ ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize })
    if (res.code === 200) { dataSource.value = res.data?.data || []; pagination.total = res.data?.total || 0 }
  } finally { loading.value = false }
}
const handleTableChange = (pag: any) => { pagination.current = pag.current; pagination.pageSize = pag.pageSize; loadData() }
const reset = () => { Object.assign(queryParams, { userId: undefined, shopId: undefined, productId: undefined, skuId: undefined, selected: undefined }); pagination.current = 1; loadData() }
const onSelectChange = (keys: number[]) => { selectedRowKeys.value = keys }
const handleAdd = () => { isEdit.value = false; Object.assign(formData, defaultForm()); modalVisible.value = true }
const handleEdit = async (r: MallCartVo) => { isEdit.value = true; const res: any = await getCartById(r.id!); if (res.code === 200) Object.assign(formData, res.data); modalVisible.value = true }
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const res: any = isEdit.value ? await updateCart(formData as MallCartUpdate, reqId) : await createCart({ ...formData }, reqId)
    if (res.code === 200) { message.success(isEdit.value ? '更新成功' : '创建成功'); modalVisible.value = false; loadData() }
  } finally { submitLoading.value = false }
}
const handleCancel = () => { modalVisible.value = false; setTimeout(() => formRef.value?.resetFields(), 300) }
const handleDeleteOne = (r: MallCartVo) => {
  Modal.confirm({ title: '确认删除', content: `确定删除该购物车记录？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await deleteCart(r.id!); if (res.code === 200) { message.success('删除成功'); loadData() } } })
}
const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({ title: '批量删除', content: `确定删除 ${selectedRowKeys.value.length} 项？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await batchDeleteCart(selectedRowKeys.value); if (res.code === 200) { message.success('删除成功'); selectedRowKeys.value = []; loadData() } } })
}
onMounted(() => { loadData() })
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
