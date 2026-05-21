<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">订单号</span>
            <a-input v-model:value="queryParams.orderNo" placeholder="输入搜索..." class="elegant-input" allow-clear>
              <template #prefix><SearchOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">用户ID</span>
            <a-input-number v-model:value="queryParams.userId" placeholder="用户ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">店铺ID</span>
            <a-input-number v-model:value="queryParams.shopId" placeholder="店铺ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">状态</span>
            <a-select v-model:value="queryParams.status" placeholder="全部" class="elegant-select" :bordered="false" allow-clear>
              <a-select-option v-for="item in orderStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
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
            <div class="tab active">全部订单 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增订单
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
            <template v-if="column.key === 'status'">
              <a-tag :color="statusColor(record.status)">{{ getDictLabel('mall_order_status', record.status) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑订单' : '新增订单'" width="900px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="12"><a-form-item label="订单号" name="orderNo"><a-input v-model:value="formData.orderNo" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="用户ID" name="userId"><a-input-number v-model:value="formData.userId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="店铺ID" name="shopId"><a-input-number v-model:value="formData.shopId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="总金额" name="totalAmount"><a-input-number v-model:value="formData.totalAmount" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="支付金额" name="payAmount"><a-input-number v-model:value="formData.payAmount" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="运费" name="freightAmount"><a-input-number v-model:value="formData.freightAmount" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="优惠金额" name="discountAmount"><a-input-number v-model:value="formData.discountAmount" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="商品总数" name="totalQuantity"><a-input-number v-model:value="formData.totalQuantity" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="种类数" name="itemKindCount"><a-input-number v-model:value="formData.itemKindCount" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="支付类型" name="payType">
            <a-select v-model:value="formData.payType" style="width:100%">
              <a-select-option v-for="item in payChannelOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="来源类型" name="sourceType">
            <a-select v-model:value="formData.sourceType" style="width:100%">
              <a-select-option v-for="item in orderSourceOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="状态" name="status">
            <a-select v-model:value="formData.status">
              <a-select-option v-for="item in orderStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="收货人" name="receiverName"><a-input v-model:value="formData.receiverName" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="收货电话" name="receiverPhone"><a-input v-model:value="formData.receiverPhone" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="邮编" name="receiverPostalCode"><a-input v-model:value="formData.receiverPostalCode" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="省" name="receiverProvince"><a-input v-model:value="formData.receiverProvince" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="市" name="receiverCity"><a-input v-model:value="formData.receiverCity" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="区" name="receiverDistrict"><a-input v-model:value="formData.receiverDistrict" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="详细地址" name="receiverAddress"><a-input v-model:value="formData.receiverAddress" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="物流公司" name="expressCompany"><a-input v-model:value="formData.expressCompany" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="物流单号" name="expressNo"><a-input v-model:value="formData.expressNo" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="支付流水" name="payTradeNo"><a-input v-model:value="formData.payTradeNo" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="用户备注" name="userRemark"><a-textarea v-model:value="formData.userRemark" :rows="2" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="商家备注" name="shopRemark"><a-textarea v-model:value="formData.shopRemark" :rows="2" /></a-form-item></a-col>
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
import type { MallOrderVo, MallOrderQuery, MallOrderCreate, MallOrderUpdate } from '@/api/mall/mallorder.ts'
import { getOrderPage, createOrder, updateOrder, deleteOrder, batchDeleteOrder, getOrderById } from '@/api/mall/mallorder.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const orderStatusOptions = computed(() => (getDictData('mall_order_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const payChannelOptions = computed(() => (getDictData('mall_pay_channel') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const orderSourceOptions = computed(() => (getDictData('mall_order_source') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const statusLabel = (s?: number) => getDictLabel('mall_order_status', s ?? 0)
const statusColor = (s?: number) => ({ 0: 'orange', 1: 'blue', 2: 'cyan', 3: 'green', 4: 'red', 5: 'volcano', 6: 'purple' } as any)[s ?? 0] || 'default'

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '订单号', dataIndex: 'orderNo', key: 'orderNo', width: 200 },
  { title: '用户ID', dataIndex: 'userId', key: 'userId', width: 100 },
  { title: '店铺ID', dataIndex: 'shopId', key: 'shopId', width: 100 },
  { title: '总金额', dataIndex: 'totalAmount', key: 'totalAmount', width: 100 },
  { title: '支付金额', dataIndex: 'payAmount', key: 'payAmount', width: 100 },
  { title: '商品数', dataIndex: 'totalQuantity', key: 'totalQuantity', width: 80 },
  { title: '收货人', dataIndex: 'receiverName', key: 'receiverName', width: 120 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<MallOrderVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallOrderQuery>({ page: 1, pageSize: 10 })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const defaultForm = (): MallOrderCreate & { id?: number } => ({ id: undefined, orderNo: '', userId: 0, shopId: 0, totalAmount: 0, payAmount: 0, freightAmount: 0, discountAmount: 0, totalQuantity: 0, itemKindCount: 0, itemsSnapshot: '', payType: 0, sourceType: 0, status: 0, receiverName: '', receiverPhone: '', receiverProvince: '', receiverCity: '', receiverDistrict: '', receiverAddress: '', receiverPostalCode: '', expressCompany: '', expressNo: '', payTradeNo: '', userRemark: '', shopRemark: '' })
const formData = reactive<MallOrderCreate & { id?: number }>(defaultForm())
const rules = { orderNo: [{ required: true, message: '请输入订单号', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getOrderPage({ ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize })
    if (res.code === 200) { dataSource.value = res.data?.data || []; pagination.total = res.data?.total || 0 }
  } finally { loading.value = false }
}
const handleTableChange = (pag: any) => { pagination.current = pag.current; pagination.pageSize = pag.pageSize; loadData() }
const reset = () => { Object.assign(queryParams, { orderNo: '', userId: undefined, shopId: undefined, status: undefined, payType: undefined, sourceType: undefined, startTime: undefined, endTime: undefined }); pagination.current = 1; loadData() }
const onSelectChange = (keys: number[]) => { selectedRowKeys.value = keys }
const handleAdd = () => { isEdit.value = false; Object.assign(formData, defaultForm()); modalVisible.value = true }
const handleEdit = async (r: MallOrderVo) => { isEdit.value = true; const res: any = await getOrderById(r.id!); if (res.code === 200) Object.assign(formData, res.data); modalVisible.value = true }
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const res: any = isEdit.value ? await updateOrder(formData as MallOrderUpdate, reqId) : await createOrder({ ...formData }, reqId)
    if (res.code === 200) { message.success(isEdit.value ? '更新成功' : '创建成功'); modalVisible.value = false; loadData() }
  } finally { submitLoading.value = false }
}
const handleCancel = () => { modalVisible.value = false; setTimeout(() => formRef.value?.resetFields(), 300) }
const handleDeleteOne = (r: MallOrderVo) => {
  Modal.confirm({ title: '确认删除', content: `确定删除订单「${r.orderNo}」？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await deleteOrder(r.id!); if (res.code === 200) { message.success('删除成功'); loadData() } } })
}
const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({ title: '批量删除', content: `确定删除 ${selectedRowKeys.value.length} 项？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await batchDeleteOrder(selectedRowKeys.value); if (res.code === 200) { message.success('删除成功'); selectedRowKeys.value = []; loadData() } } })
}
onMounted(() => { loadData() })
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
