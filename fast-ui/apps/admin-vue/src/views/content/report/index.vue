<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
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
            <div class="tab active">举报列表 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增举报
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
          :scroll="{ x: 1400 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'targetType'">
              <a-tag color="blue">{{ getDictLabel('content_report_target_type', record.targetType) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'reasonType'">
              <a-tag color="purple">{{ getDictLabel('content_report_reason_type', record.reasonType) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'status'">
              <a-tag :color="record.status === 1 ? 'green' : 'default'">{{ getDictLabel('content_report_status', record.status) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑举报' : '新增举报'" width="900px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="8"><a-form-item label="目标类型" name="targetType">
            <a-select v-model:value="formData.targetType" allow-clear>
              <a-select-option v-for="item in targetTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="目标ID" name="targetId"><a-input-number v-model:value="formData.targetId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="举报人ID" name="reportBy"><a-input-number v-model:value="formData.reportBy" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="原因类型" name="reasonType">
            <a-select v-model:value="formData.reasonType" allow-clear>
              <a-select-option v-for="item in reasonTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="状态" name="status">
            <a-select v-model:value="formData.status" allow-clear>
              <a-select-option v-for="item in reportStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="处理人ID" name="handleBy"><a-input-number v-model:value="formData.handleBy" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="原因描述" name="reason"><a-textarea v-model:value="formData.reason" :rows="4" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="IP" name="ip"><a-input v-model:value="formData.ip" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="User-Agent" name="userAgent"><a-input v-model:value="formData.userAgent" /></a-form-item></a-col>
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
import type { ContentReportVo, ContentReportQuery, ContentReportCreate, ContentReportUpdate } from '@/api/content/contentreport'
import { getContentReportPage, getContentReportById, createContentReport, updateContentReport, deleteContentReport, batchDeleteContentReport } from '@/api/content/contentreport'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const targetTypeOptions = computed(() => (getDictData('content_report_target_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const reasonTypeOptions = computed(() => (getDictData('content_report_reason_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const reportStatusOptions = computed(() => (getDictData('content_report_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 90 },
  { title: '目标类型', dataIndex: 'targetType', key: 'targetType', width: 140 },
  { title: '目标ID', dataIndex: 'targetId', key: 'targetId', width: 120 },
  { title: '举报人ID', dataIndex: 'reportBy', key: 'reportBy', width: 120 },
  { title: '原因类型', dataIndex: 'reasonType', key: 'reasonType', width: 140 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '处理人ID', dataIndex: 'handleBy', key: 'handleBy', width: 120 },
  { title: '处理时间', dataIndex: 'handleTime', key: 'handleTime', width: 180 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<ContentReportVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<ContentReportQuery>({
  page: 1,
  pageSize: 10,
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条数据`,
})

const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
type ContentReportFormData = ContentReportCreate & { id?: number }
const formData = reactive<ContentReportFormData>({
  id: undefined,
  targetType: undefined,
  targetId: undefined,
  reportBy: undefined,
  reasonType: undefined,
  reason: '',
  status: undefined,
  handleBy: undefined,
  handleTime: '',
  ip: '',
  userAgent: '',
})

const rules = {
  targetType: [{ required: true, message: '请选择目标类型', trigger: 'change' }],
  targetId: [{ required: true, message: '请输入目标ID', trigger: 'blur' }],
}

const loadData = async () => {
  loading.value = true
  try {
    const params: ContentReportQuery = { ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize }
    const res: any = await getContentReportPage(params)
    if (res.code === 200) {
      dataSource.value = res.data?.data || []
      pagination.total = res.data?.total || 0
    }
  } finally {
    loading.value = false
  }
}

const handleTableChange = (pag: any) => {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  loadData()
}

const reset = () => {
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(formData, { id: undefined, targetType: undefined, targetId: undefined, reportBy: undefined, reasonType: undefined, reason: '', status: undefined, handleBy: undefined, handleTime: '', ip: '', userAgent: '' })
  modalVisible.value = true
}

const handleEdit = async (record: ContentReportVo) => {
  isEdit.value = true
  const res: any = await getContentReportById(record.id!)
  if (res.code === 200) {
    Object.assign(formData, res.data)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const payload = { ...formData }
    const res: any = isEdit.value
      ? await updateContentReport(payload as ContentReportUpdate, reqId)
      : await createContentReport(payload, reqId)
    if (res.code === 200) {
      message.success(isEdit.value ? '更新成功' : '创建成功')
      modalVisible.value = false
      loadData()
    }
  } finally {
    submitLoading.value = false
  }
}

const handleCancel = () => {
  modalVisible.value = false
  setTimeout(() => formRef.value?.resetFields(), 300)
}

const handleDeleteOne = (record: ContentReportVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `确定删除举报「${record.id}」？`,
    okType: 'danger',
    centered: true,
    onOk: async () => {
      const res: any = await deleteContentReport(record.id!)
      if (res.code === 200) {
        message.success('删除成功')
        loadData()
      }
    },
  })
}

const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({
    title: '批量删除',
    content: `确定删除 ${selectedRowKeys.value.length} 项？`,
    okType: 'danger',
    centered: true,
    onOk: async () => {
      const res: any = await batchDeleteContentReport(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('删除成功')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>

