<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">订单ID</span>
            <a-input-number v-model:value="queryParams.orderId" placeholder="订单ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">订单号</span>
            <a-input v-model:value="queryParams.orderNo" placeholder="输入搜索..." class="elegant-input" allow-clear />
          </div>
          <div class="filter-item">
            <span class="filter-label">操作动作</span>
            <a-select v-model:value="queryParams.action" placeholder="全部" class="elegant-input" allow-clear style="width:100%">
              <a-select-option v-for="item in actionOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">操作类型</span>
            <a-select v-model:value="queryParams.operatorType" placeholder="全部" class="elegant-input" allow-clear style="width:100%">
              <a-select-option v-for="item in operatorTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
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
            <div class="tab active">订单日志 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增日志
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
          :scroll="{ x: 1300 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'actionField'">
              <a-tag color="blue">{{ getDictLabel('mall_order_log_action', record.action) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'fromStatus'">
              <a-tag>{{ getDictLabel('mall_order_status', record.fromStatus) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'toStatus'">
              <a-tag color="green">{{ getDictLabel('mall_order_status', record.toStatus) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑日志' : '新增日志'" width="700px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="12"><a-form-item label="订单ID" name="orderId"><a-input-number v-model:value="formData.orderId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="订单号" name="orderNo"><a-input v-model:value="formData.orderNo" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="操作类型" name="operatorType">
            <a-select v-model:value="formData.operatorType" placeholder="请选择">
              <a-select-option v-for="item in operatorTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="操作人ID" name="operatorId"><a-input-number v-model:value="formData.operatorId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="操作人" name="operatorName"><a-input v-model:value="formData.operatorName" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="动作" name="action">
            <a-select v-model:value="formData.action" placeholder="请选择">
              <a-select-option v-for="item in actionOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="原状态" name="fromStatus">
            <a-select v-model:value="formData.fromStatus" placeholder="请选择">
              <a-select-option v-for="item in orderStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="新状态" name="toStatus">
            <a-select v-model:value="formData.toStatus" placeholder="请选择">
              <a-select-option v-for="item in orderStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="12"><a-form-item label="IP" name="ip"><a-input v-model:value="formData.ip" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="来源类型" name="sourceType">
            <a-select v-model:value="formData.sourceType" placeholder="请选择">
              <a-select-option v-for="item in orderSourceOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="24"><a-form-item label="内容" name="content"><a-textarea v-model:value="formData.content" :rows="2" /></a-form-item></a-col>
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
import type { MallOrderLogVo, MallOrderLogQuery, MallOrderLogCreate, MallOrderLogUpdate } from '@/api/mall/mallorderlog.ts'
import { getOrderLogPage, createOrderLog, updateOrderLog, deleteOrderLog, batchDeleteOrderLog, getOrderLogById } from '@/api/mall/mallorderlog.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const operatorTypeOptions = computed(() => (getDictData('mall_log_operator_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const actionOptions = computed(() => (getDictData('mall_order_log_action') || []).map((d: any) => ({ value: d.value, label: d.label })))
const orderStatusOptions = computed(() => (getDictData('mall_order_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const orderSourceOptions = computed(() => (getDictData('mall_order_source') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '订单ID', dataIndex: 'orderId', key: 'orderId', width: 100 },
  { title: '订单号', dataIndex: 'orderNo', key: 'orderNo', width: 200 },
  { title: '操作人', dataIndex: 'operatorName', key: 'operatorName', width: 140 },
  { title: '动作', dataIndex: 'action', key: 'actionField', width: 140 },
  { title: '原状态', dataIndex: 'fromStatus', key: 'fromStatus', width: 80 },
  { title: '新状态', dataIndex: 'toStatus', key: 'toStatus', width: 80 },
  { title: '内容', dataIndex: 'content', key: 'content', width: 200, ellipsis: true },
  { title: 'IP', dataIndex: 'ip', key: 'ip', width: 120 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<MallOrderLogVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallOrderLogQuery>({ page: 1, pageSize: 10 })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const defaultForm = (): MallOrderLogCreate & { id?: number } => ({ id: undefined, orderId: 0, orderNo: '', operatorType: 0, operatorId: 0, operatorName: '', action: '', fromStatus: 0, toStatus: 0, content: '', ip: '', sourceType: 0, extra: '' })
const formData = reactive<MallOrderLogCreate & { id?: number }>(defaultForm())
const rules = { orderNo: [{ required: true, message: '请输入订单号', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getOrderLogPage({ ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize })
    if (res.code === 200) { dataSource.value = res.data?.data || []; pagination.total = res.data?.total || 0 }
  } finally { loading.value = false }
}
const handleTableChange = (pag: any) => { pagination.current = pag.current; pagination.pageSize = pag.pageSize; loadData() }
const reset = () => { Object.assign(queryParams, { orderId: undefined, orderNo: '', operatorType: undefined, action: '', sourceType: undefined }); pagination.current = 1; loadData() }
const onSelectChange = (keys: number[]) => { selectedRowKeys.value = keys }
const handleAdd = () => { isEdit.value = false; Object.assign(formData, defaultForm()); modalVisible.value = true }
const handleEdit = async (r: MallOrderLogVo) => { isEdit.value = true; const res: any = await getOrderLogById(r.id!); if (res.code === 200) Object.assign(formData, res.data); modalVisible.value = true }
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const res: any = isEdit.value ? await updateOrderLog(formData as MallOrderLogUpdate, reqId) : await createOrderLog({ ...formData }, reqId)
    if (res.code === 200) { message.success(isEdit.value ? '更新成功' : '创建成功'); modalVisible.value = false; loadData() }
  } finally { submitLoading.value = false }
}
const handleCancel = () => { modalVisible.value = false; setTimeout(() => formRef.value?.resetFields(), 300) }
const handleDeleteOne = (r: MallOrderLogVo) => {
  Modal.confirm({ title: '确认删除', content: `确定删除该日志？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await deleteOrderLog(r.id!); if (res.code === 200) { message.success('删除成功'); loadData() } } })
}
const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({ title: '批量删除', content: `确定删除 ${selectedRowKeys.value.length} 项？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await batchDeleteOrderLog(selectedRowKeys.value); if (res.code === 200) { message.success('删除成功'); selectedRowKeys.value = []; loadData() } } })
}
onMounted(() => { loadData() })
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
