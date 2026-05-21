<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">退款单号</span>
            <a-input v-model:value="queryParams.refundNo" placeholder="输入搜索..." class="elegant-input" allow-clear>
              <template #prefix><SearchOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">订单号</span>
            <a-input v-model:value="queryParams.orderNo" placeholder="输入搜索..." class="elegant-input" allow-clear />
          </div>
          <div class="filter-item">
            <span class="filter-label">用户ID</span>
            <a-input-number v-model:value="queryParams.userId" placeholder="用户ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">退款类型</span>
            <a-select v-model:value="queryParams.refundType" placeholder="全部" class="elegant-select" :bordered="false" allow-clear>
              <a-select-option v-for="item in refundTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">退款状态</span>
            <a-select v-model:value="queryParams.refundStatus" placeholder="全部" class="elegant-select" :bordered="false" allow-clear style="width:100%">
              <a-select-option v-for="item in refundApplyStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
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
            <div class="tab active">退款列表 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增退款
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
            <template v-if="column.key === 'refundType'">
              <a-tag :color="record.refundType === 2 ? 'orange' : 'blue'">{{ getDictLabel('mall_refund_type', record.refundType) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'refundStatus'">
              <a-tag>{{ getDictLabel('mall_refund_apply_status', record.refundStatus) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑退款' : '新增退款'" width="900px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="12"><a-form-item label="退款单号" name="refundNo"><a-input v-model:value="formData.refundNo" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="订单号" name="orderNo"><a-input v-model:value="formData.orderNo" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="订单ID" name="orderId"><a-input-number v-model:value="formData.orderId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="明细ID" name="orderItemId"><a-input-number v-model:value="formData.orderItemId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="用户ID" name="userId"><a-input-number v-model:value="formData.userId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="店铺ID" name="shopId"><a-input-number v-model:value="formData.shopId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="退款类型" name="refundType">
            <a-select v-model:value="formData.refundType">
              <a-select-option v-for="item in refundTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="6"><a-form-item label="退款状态" name="refundStatus">
            <a-select v-model:value="formData.refundStatus" placeholder="请选择">
              <a-select-option v-for="item in refundApplyStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="6"><a-form-item label="退款金额" name="refundAmount"><a-input-number v-model:value="formData.refundAmount" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="退款积分" name="refundPoints"><a-input-number v-model:value="formData.refundPoints" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="退款数量" name="refundQuantity"><a-input-number v-model:value="formData.refundQuantity" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="18"><a-form-item label="退款原因" name="reason"><a-input v-model:value="formData.reason" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="详细说明" name="description"><a-textarea v-model:value="formData.description" :rows="2" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="凭证" name="evidence"><a-textarea v-model:value="formData.evidence" :rows="2" placeholder="多图URL以逗号分隔" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="商家回复" name="shopReplyText"><a-textarea v-model:value="formData.shopReplyText" :rows="2" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="拒绝理由" name="shopRejectReason"><a-textarea v-model:value="formData.shopRejectReason" :rows="2" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="退货物流公司" name="returnExpressCompany"><a-input v-model:value="formData.returnExpressCompany" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="退货物流单号" name="returnExpressNo"><a-input v-model:value="formData.returnExpressNo" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="支付退款流水" name="payRefundTradeNo"><a-input v-model:value="formData.payRefundTradeNo" /></a-form-item></a-col>
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
import type { MallOrderRefundVo, MallOrderRefundQuery, MallOrderRefundCreate, MallOrderRefundUpdate } from '@/api/mall/mallorderrefund.ts'
import { getOrderRefundPage, createOrderRefund, updateOrderRefund, deleteOrderRefund, batchDeleteOrderRefund, getOrderRefundById } from '@/api/mall/mallorderrefund.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const refundTypeOptions = computed(() => (getDictData('mall_refund_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const refundApplyStatusOptions = computed(() => (getDictData('mall_refund_apply_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '退款单号', dataIndex: 'refundNo', key: 'refundNo', width: 200 },
  { title: '订单号', dataIndex: 'orderNo', key: 'orderNo', width: 200 },
  { title: '用户ID', dataIndex: 'userId', key: 'userId', width: 100 },
  { title: '退款类型', dataIndex: 'refundType', key: 'refundType', width: 110 },
  { title: '状态', dataIndex: 'refundStatus', key: 'refundStatus', width: 100 },
  { title: '金额', dataIndex: 'refundAmount', key: 'refundAmount', width: 100 },
  { title: '原因', dataIndex: 'reason', key: 'reason', width: 200, ellipsis: true },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<MallOrderRefundVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallOrderRefundQuery>({ page: 1, pageSize: 10 })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const defaultForm = (): MallOrderRefundCreate & { id?: number } => ({ id: undefined, refundNo: '', orderId: 0, orderNo: '', orderItemId: 0, userId: 0, shopId: 0, refundType: 1, refundStatus: 0, refundAmount: 0, refundPoints: 0, refundQuantity: 0, reason: '', description: '', evidence: '', shopReplyText: '', shopRejectReason: '', returnExpressCompany: '', returnExpressNo: '', payRefundTradeNo: '', extra: '' })
const formData = reactive<MallOrderRefundCreate & { id?: number }>(defaultForm())
const rules = { refundNo: [{ required: true, message: '请输入退款单号', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getOrderRefundPage({ ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize })
    if (res.code === 200) { dataSource.value = res.data?.data || []; pagination.total = res.data?.total || 0 }
  } finally { loading.value = false }
}
const handleTableChange = (pag: any) => { pagination.current = pag.current; pagination.pageSize = pag.pageSize; loadData() }
const reset = () => { Object.assign(queryParams, { refundNo: '', orderNo: '', orderId: undefined, userId: undefined, shopId: undefined, refundType: undefined, refundStatus: undefined, startTime: undefined, endTime: undefined }); pagination.current = 1; loadData() }
const onSelectChange = (keys: number[]) => { selectedRowKeys.value = keys }
const handleAdd = () => { isEdit.value = false; Object.assign(formData, defaultForm()); modalVisible.value = true }
const handleEdit = async (r: MallOrderRefundVo) => { isEdit.value = true; const res: any = await getOrderRefundById(r.id!); if (res.code === 200) Object.assign(formData, res.data); modalVisible.value = true }
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const res: any = isEdit.value ? await updateOrderRefund(formData as MallOrderRefundUpdate, reqId) : await createOrderRefund({ ...formData }, reqId)
    if (res.code === 200) { message.success(isEdit.value ? '更新成功' : '创建成功'); modalVisible.value = false; loadData() }
  } finally { submitLoading.value = false }
}
const handleCancel = () => { modalVisible.value = false; setTimeout(() => formRef.value?.resetFields(), 300) }
const handleDeleteOne = (r: MallOrderRefundVo) => {
  Modal.confirm({ title: '确认删除', content: `确定删除退款「${r.refundNo}」？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await deleteOrderRefund(r.id!); if (res.code === 200) { message.success('删除成功'); loadData() } } })
}
const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({ title: '批量删除', content: `确定删除 ${selectedRowKeys.value.length} 项？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await batchDeleteOrderRefund(selectedRowKeys.value); if (res.code === 200) { message.success('删除成功'); selectedRowKeys.value = []; loadData() } } })
}
onMounted(() => { loadData() })
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
