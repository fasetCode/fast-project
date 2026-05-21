<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">商品ID</span>
            <a-input-number v-model:value="queryParams.productId" placeholder="商品ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">店铺ID</span>
            <a-input-number v-model:value="queryParams.shopId" placeholder="店铺ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">SKU编号</span>
            <a-input v-model:value="queryParams.skuSn" placeholder="输入搜索..." class="elegant-input" allow-clear />
          </div>
          <div class="filter-item">
            <span class="filter-label">状态</span>
            <a-select v-model:value="queryParams.status" placeholder="全部状态" class="elegant-select" :bordered="false" allow-clear>
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
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
            <div class="tab active">全部SKU <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增SKU
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
          :scroll="{ x: 1500 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'image'">
              <a-image v-if="record.image" :src="record.image" :width="40" :height="40" />
              <span v-else class="text-secondary">-</span>
            </template>
            <template v-else-if="column.key === 'status'">
              <a-tag :color="record.status === 1 ? 'green' : 'red'">{{ getDictLabel('status', record.status) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑SKU' : '新增SKU'" width="900px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="8"><a-form-item label="商品ID" name="productId"><a-input-number v-model:value="formData.productId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="店铺ID" name="shopId"><a-input-number v-model:value="formData.shopId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="SKU编号" name="skuSn"><a-input v-model:value="formData.skuSn" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="条码" name="barcode"><a-input v-model:value="formData.barcode" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="规格描述" name="specText"><a-input v-model:value="formData.specText" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="规格JSON" name="specs"><a-input v-model:value="formData.specs" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="图片" name="image"><a-input v-model:value="formData.image" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="支付类型" name="payType">
            <a-select v-model:value="formData.payType" style="width:100%">
              <a-select-option v-for="item in payTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="售价" name="price"><a-input-number v-model:value="formData.price" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="原价" name="originalPrice"><a-input-number v-model:value="formData.originalPrice" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="成本价" name="costPrice"><a-input-number v-model:value="formData.costPrice" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="积分价" name="pointsPrice"><a-input-number v-model:value="formData.pointsPrice" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="赠送积分" name="giftPoints"><a-input-number v-model:value="formData.giftPoints" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="库存" name="stock"><a-input-number v-model:value="formData.stock" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="锁定库存" name="lockStock"><a-input-number v-model:value="formData.lockStock" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="销量" name="sales"><a-input-number v-model:value="formData.sales" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="重量" name="weight"><a-input-number v-model:value="formData.weight" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="体积" name="volume"><a-input-number v-model:value="formData.volume" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="排序" name="sort"><a-input-number v-model:value="formData.sort" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="状态" name="status">
            <a-select v-model:value="formData.status">
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="24"><a-form-item label="扩展" name="extra"><a-textarea v-model:value="formData.extra" :rows="2" /></a-form-item></a-col>
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
import type { MallProductSkuVo, MallProductSkuQuery, MallProductSkuCreate, MallProductSkuUpdate } from '@/api/mall/mallproductsku.ts'
import { getSkuPage, createSku, updateSku, deleteSku, batchDeleteSku, getSkuById } from '@/api/mall/mallproductsku.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const statusOptions = computed(() => (getDictData('status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const payTypeOptions = computed(() => (getDictData('mall_pay_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '商品ID', dataIndex: 'productId', key: 'productId', width: 100 },
  { title: '店铺ID', dataIndex: 'shopId', key: 'shopId', width: 100 },
  { title: 'SKU编号', dataIndex: 'skuSn', key: 'skuSn', width: 140 },
  { title: '图片', dataIndex: 'image', key: 'image', width: 80 },
  { title: '规格', dataIndex: 'specText', key: 'specText', width: 160 },
  { title: '售价', dataIndex: 'price', key: 'price', width: 100 },
  { title: '库存', dataIndex: 'stock', key: 'stock', width: 80 },
  { title: '销量', dataIndex: 'sales', key: 'sales', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<MallProductSkuVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallProductSkuQuery>({ page: 1, pageSize: 10 })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const defaultForm = (): MallProductSkuCreate & { id?: number } => ({ id: undefined, productId: 0, shopId: 0, skuSn: '', barcode: '', specText: '', specs: '', image: '', payType: 0, price: 0, originalPrice: 0, costPrice: 0, pointsPrice: 0, giftPoints: 0, stock: 0, lockStock: 0, sales: 0, weight: 0, volume: 0, sort: 0, status: 1, extra: '' })
const formData = reactive<MallProductSkuCreate & { id?: number }>(defaultForm())
const rules = { skuSn: [{ required: true, message: '请输入SKU编号', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getSkuPage({ ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize })
    if (res.code === 200) { dataSource.value = res.data?.data || []; pagination.total = res.data?.total || 0 }
  } finally { loading.value = false }
}
const handleTableChange = (pag: any) => { pagination.current = pag.current; pagination.pageSize = pag.pageSize; loadData() }
const reset = () => { Object.assign(queryParams, { productId: undefined, shopId: undefined, skuSn: '', status: undefined }); pagination.current = 1; loadData() }
const onSelectChange = (keys: number[]) => { selectedRowKeys.value = keys }
const handleAdd = () => { isEdit.value = false; Object.assign(formData, defaultForm()); modalVisible.value = true }
const handleEdit = async (r: MallProductSkuVo) => { isEdit.value = true; const res: any = await getSkuById(r.id!); if (res.code === 200) Object.assign(formData, res.data); modalVisible.value = true }
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const res: any = isEdit.value ? await updateSku(formData as MallProductSkuUpdate, reqId) : await createSku({ ...formData }, reqId)
    if (res.code === 200) { message.success(isEdit.value ? '更新成功' : '创建成功'); modalVisible.value = false; loadData() }
  } finally { submitLoading.value = false }
}
const handleCancel = () => { modalVisible.value = false; setTimeout(() => formRef.value?.resetFields(), 300) }
const handleDeleteOne = (r: MallProductSkuVo) => {
  Modal.confirm({ title: '确认删除', content: `确定删除SKU「${r.skuSn}」？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await deleteSku(r.id!); if (res.code === 200) { message.success('删除成功'); loadData() } } })
}
const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({ title: '批量删除', content: `确定删除 ${selectedRowKeys.value.length} 项？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await batchDeleteSku(selectedRowKeys.value); if (res.code === 200) { message.success('删除成功'); selectedRowKeys.value = []; loadData() } } })
}
onMounted(() => { loadData() })
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
