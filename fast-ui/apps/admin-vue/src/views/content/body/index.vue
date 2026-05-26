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
            <div class="tab active">正文列表 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增正文
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
            <template v-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑正文' : '新增正文'" width="900px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="8"><a-form-item label="内容ID" name="contentId"><a-input-number v-model:value="formData.contentId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="格式" name="format"><a-input v-model:value="formData.format" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="字数" name="wordCount"><a-input-number v-model:value="formData.wordCount" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="阅读时长(分钟)" name="readingTime"><a-input-number v-model:value="formData.readingTime" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="正文内容" name="content"><a-textarea v-model:value="formData.content" :rows="6" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="正文HTML" name="contentHtml"><a-textarea v-model:value="formData.contentHtml" :rows="6" /></a-form-item></a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message, Modal } from 'ant-design-vue'
import { SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { ContentBodyVo, ContentBodyQuery, ContentBodyCreate, ContentBodyUpdate } from '@/api/content/contentbody'
import { getContentBodyPage, getContentBodyById, createContentBody, updateContentBody, deleteContentBody, batchDeleteContentBody } from '@/api/content/contentbody'
import { getRequestId } from '@/utils/idUtils.ts'

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 90 },
  { title: '内容ID', dataIndex: 'contentId', key: 'contentId', width: 110 },
  { title: '格式', dataIndex: 'format', key: 'format', width: 120 },
  { title: '字数', dataIndex: 'wordCount', key: 'wordCount', width: 100 },
  { title: '阅读时长', dataIndex: 'readingTime', key: 'readingTime', width: 110 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<ContentBodyVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<ContentBodyQuery>({
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
type ContentBodyFormData = ContentBodyCreate & { id?: number }
const formData = reactive<ContentBodyFormData>({
  id: undefined,
  contentId: undefined,
  format: '',
  content: '',
  contentHtml: '',
  wordCount: 0,
  readingTime: 0,
})

const rules = {
  contentId: [{ required: true, message: '请输入内容ID', trigger: 'blur' }],
}

const loadData = async () => {
  loading.value = true
  try {
    const params: ContentBodyQuery = { ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize }
    const res: any = await getContentBodyPage(params)
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
  Object.assign(formData, { id: undefined, contentId: undefined, format: '', content: '', contentHtml: '', wordCount: 0, readingTime: 0 })
  modalVisible.value = true
}

const handleEdit = async (record: ContentBodyVo) => {
  isEdit.value = true
  const res: any = await getContentBodyById(record.id!)
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
      ? await updateContentBody(payload as ContentBodyUpdate, reqId)
      : await createContentBody(payload, reqId)
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

const handleDeleteOne = (record: ContentBodyVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `确定删除正文「${record.id}」？`,
    okType: 'danger',
    centered: true,
    onOk: async () => {
      const res: any = await deleteContentBody(record.id!)
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
      const res: any = await batchDeleteContentBody(selectedRowKeys.value)
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

