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
            <div class="tab active">全部内容 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增内容
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
            <template v-if="column.key === 'status'">
              <a-tag :color="record.status === 1 ? 'green' : 'red'">{{ getDictLabel('status', record.status) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'publishStatus'">
              <a-tag color="blue">{{ getDictLabel('content_publish_status', record.publishStatus) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'auditStatus'">
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

    <a-modal
      v-model:open="modalVisible"
      :title="isEdit ? '编辑内容' : '新增内容'"
      width="100vw"
      wrap-class-name="content-info-fullscreen-modal"
      :destroy-on-close="true"
      @cancel="handleCancel"
      @ok="handleSubmit"
      :confirm-loading="submitLoading"
    >
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="24"><a-form-item label="标题" name="title"><a-input v-model:value="formData.title" placeholder="请输入标题" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="摘要" name="summary"><a-textarea v-model:value="formData.summary" :rows="3" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="作者名称" name="authorName"><a-input v-model:value="formData.authorName" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="作者ID" name="authorId"><a-input-number v-model:value="formData.authorId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="分类ID" name="categoryId"><a-input-number v-model:value="formData.categoryId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="标签" name="tags"><a-input v-model:value="formData.tags" placeholder="逗号分隔" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="来源" name="source"><a-input v-model:value="formData.source" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="来源链接" name="sourceUrl"><a-input v-model:value="formData.sourceUrl" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="置顶" name="topFlag"><a-switch v-model:checked="formData.topFlag" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="推荐" name="recommendFlag"><a-switch v-model:checked="formData.recommendFlag" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="允许评论" name="allowComment"><a-switch v-model:checked="formData.allowComment" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="状态" name="status">
            <a-select v-model:value="formData.status" allow-clear>
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="发布状态" name="publishStatus">
            <a-select v-model:value="formData.publishStatus" allow-clear>
              <a-select-option v-for="item in publishStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="审核状态" name="auditStatus">
            <a-select v-model:value="formData.auditStatus" allow-clear>
              <a-select-option v-for="item in auditStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
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
import type { ContentInfoVo, ContentInfoQuery, ContentInfoCreate, ContentInfoUpdate } from '@/api/content/contentinfo'
import { getContentInfoPage, getContentInfoById, createContentInfo, updateContentInfo, deleteContentInfo, batchDeleteContentInfo } from '@/api/content/contentinfo'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const statusOptions = computed(() => (getDictData('status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const publishStatusOptions = computed(() => (getDictData('content_publish_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const auditStatusOptions = computed(() => (getDictData('content_audit_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 90 },
  { title: '标题', dataIndex: 'title', key: 'title', width: 260 },
  { title: '作者', dataIndex: 'authorName', key: 'authorName', width: 140 },
  { title: '分类ID', dataIndex: 'categoryId', key: 'categoryId', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '发布状态', dataIndex: 'publishStatus', key: 'publishStatus', width: 120 },
  { title: '审核状态', dataIndex: 'auditStatus', key: 'auditStatus', width: 120 },
  { title: '浏览数', dataIndex: 'viewCount', key: 'viewCount', width: 100 },
  { title: '评论数', dataIndex: 'commentCount', key: 'commentCount', width: 100 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<ContentInfoVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<ContentInfoQuery>({
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
type ContentInfoFormData = ContentInfoCreate & { id?: number }
const formData = reactive<ContentInfoFormData>({
  id: undefined,
  title: '',
  summary: '',
  cover: '',
  categoryId: undefined,
  tags: '',
  authorId: undefined,
  authorName: '',
  source: '',
  sourceUrl: '',
  topFlag: false,
  recommendFlag: false,
  allowComment: true,
  status: 1,
  publishStatus: undefined,
  auditStatus: undefined,
})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
}

const loadData = async () => {
  loading.value = true
  try {
    const params: ContentInfoQuery = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res: any = await getContentInfoPage(params)
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
  Object.assign(formData, {
    id: undefined,
    title: '',
    summary: '',
    cover: '',
    categoryId: undefined,
    tags: '',
    authorId: undefined,
    authorName: '',
    source: '',
    sourceUrl: '',
    topFlag: false,
    recommendFlag: false,
    allowComment: true,
    status: 1,
    publishStatus: undefined,
    auditStatus: undefined,
  })
  modalVisible.value = true
}

const handleEdit = async (record: ContentInfoVo) => {
  isEdit.value = true
  const res: any = await getContentInfoById(record.id!)
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
      ? await updateContentInfo(payload as ContentInfoUpdate, reqId)
      : await createContentInfo(payload, reqId)
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

const handleDeleteOne = (record: ContentInfoVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `确定删除内容「${record.title || record.id}」？`,
    okType: 'danger',
    centered: true,
    onOk: async () => {
      const res: any = await deleteContentInfo(record.id!)
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
      const res: any = await batchDeleteContentInfo(selectedRowKeys.value)
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

<style>
.content-info-fullscreen-modal .ant-modal {
  top: 0;
  width: 100vw !important;
  max-width: 100vw;
  height: 100vh;
  margin: 0;
  padding-bottom: 0;
}

.content-info-fullscreen-modal .ant-modal-content {
  height: 100vh;
  display: flex;
  flex-direction: column;
  border-radius: 0;
}

.content-info-fullscreen-modal .ant-modal-body {
  flex: 1;
  overflow: auto;
}
</style>
