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
            <div class="tab active">审核记录 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增记录
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
          :scroll="{ x: 1100 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'auditStatus'">
              <a-tag color="purple">{{ getDictLabel('content_audit_status', record.auditStatus) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑记录' : '新增记录'" width="900px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="8"><a-form-item label="内容ID" name="contentId"><a-input-number v-model:value="formData.contentId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="审核状态" name="auditStatus">
            <a-select v-model:value="formData.auditStatus" allow-clear>
              <a-select-option v-for="item in auditStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="审核人ID" name="auditBy"><a-input-number v-model:value="formData.auditBy" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="原因/备注" name="reason"><a-textarea v-model:value="formData.reason" :rows="4" /></a-form-item></a-col>
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
import type { ContentAuditLogVo, ContentAuditLogQuery, ContentAuditLogCreate, ContentAuditLogUpdate } from '@/api/content/contentauditlog'
import { getContentAuditLogPage, getContentAuditLogById, createContentAuditLog, updateContentAuditLog, deleteContentAuditLog, batchDeleteContentAuditLog } from '@/api/content/contentauditlog'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const auditStatusOptions = computed(() => (getDictData('content_audit_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 90 },
  { title: '内容ID', dataIndex: 'contentId', key: 'contentId', width: 110 },
  { title: '审核状态', dataIndex: 'auditStatus', key: 'auditStatus', width: 140 },
  { title: '审核人ID', dataIndex: 'auditBy', key: 'auditBy', width: 120 },
  { title: '审核时间', dataIndex: 'auditTime', key: 'auditTime', width: 180 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<ContentAuditLogVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<ContentAuditLogQuery>({
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
type ContentAuditLogFormData = ContentAuditLogCreate & { id?: number }
const formData = reactive<ContentAuditLogFormData>({
  id: undefined,
  contentId: undefined,
  auditStatus: undefined,
  reason: '',
  auditBy: undefined,
  auditTime: '',
})

const rules = {
  contentId: [{ required: true, message: '请输入内容ID', trigger: 'blur' }],
}

const loadData = async () => {
  loading.value = true
  try {
    const params: ContentAuditLogQuery = { ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize }
    const res: any = await getContentAuditLogPage(params)
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
  Object.assign(formData, { id: undefined, contentId: undefined, auditStatus: undefined, reason: '', auditBy: undefined, auditTime: '' })
  modalVisible.value = true
}

const handleEdit = async (record: ContentAuditLogVo) => {
  isEdit.value = true
  const res: any = await getContentAuditLogById(record.id!)
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
      ? await updateContentAuditLog(payload as ContentAuditLogUpdate, reqId)
      : await createContentAuditLog(payload, reqId)
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

const handleDeleteOne = (record: ContentAuditLogVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `确定删除记录「${record.id}」？`,
    okType: 'danger',
    centered: true,
    onOk: async () => {
      const res: any = await deleteContentAuditLog(record.id!)
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
      const res: any = await batchDeleteContentAuditLog(selectedRowKeys.value)
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

