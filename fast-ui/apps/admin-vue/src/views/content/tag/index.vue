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
            <div class="tab active">标签列表 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增标签
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
          :scroll="{ x: 1000 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'status'">
              <a-tag :color="record.status === 1 ? 'green' : 'red'">{{ getDictLabel('status', record.status) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'color'">
              <a-tag v-if="record.color" :color="record.color">{{ record.color }}</a-tag>
              <span v-else class="text-secondary">-</span>
            </template>
            <template v-else-if="column.key === 'image'">
              <a-image v-if="record.image" :src="resolveImage(record.image)" :width="40" :height="40" />
              <span v-else class="text-secondary">-</span>
            </template>
            <template v-else-if="column.key === 'icon'">
              <a-image v-if="record.icon" :src="resolveImage(record.icon)" :width="40" :height="40" />
              <span v-else class="text-secondary">-</span>
            </template>
            <template v-else-if="column.key === 'displayType'">
              <a-tag color="blue">{{ getDictLabel('content_tag_display_type', record.displayType) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑标签' : '新增标签'" width="700px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="12"><a-form-item label="标签名称" name="name"><a-input v-model:value="formData.name" /></a-form-item></a-col>
          <a-col :span="12">
            <a-form-item label="颜色" name="color">
              <a-input v-model:value="formData.color" placeholder="#1890ff 或 red">
                <template #suffix>
                  <span
                    v-if="formData.color"
                    :style="{ display: 'inline-block', width: '14px', height: '14px', borderRadius: '2px', border: '1px solid #d9d9d9', background: formData.color }"
                  />
                </template>
              </a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12"><a-form-item label="图标" name="icon"><ImageUpload v-model="formData.icon" value-type="id" :limit="1" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="图片" name="image"><ImageUpload v-model="formData.image" value-type="id" :limit="1" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="展示类型" name="displayType">
            <a-select v-model:value="formData.displayType" allow-clear>
              <a-select-option v-for="item in displayTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="12"><a-form-item label="状态" name="status">
            <a-select v-model:value="formData.status">
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
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
import type { ContentTagVo, ContentTagQuery, ContentTagCreate, ContentTagUpdate } from '@/api/content/contenttag'
import { getContentTagPage, getContentTagById, createContentTag, updateContentTag, deleteContentTag, batchDeleteContentTag } from '@/api/content/contenttag'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'
import ImageUpload from '@/components/ImageUpload/index.vue'
import { getFileUrl } from '@/api/file/fileupload'

const resolveImage = (val: any) => {
  if (!val) return ''
  const str = String(val)
  if (str.startsWith('http') || str.startsWith('/')) return str
  return getFileUrl(val)
}

const statusOptions = computed(() => (getDictData('status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const displayTypeOptions = computed(() => (getDictData('content_tag_display_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 90 },
  { title: '名称', dataIndex: 'name', key: 'name', width: 180 },
  { title: '颜色', dataIndex: 'color', key: 'color', width: 120 },
  { title: '图片', dataIndex: 'image', key: 'image', width: 90 },
  { title: '图标', dataIndex: 'icon', key: 'icon', width: 90 },
  { title: '展示类型', dataIndex: 'displayType', key: 'displayType', width: 140 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<ContentTagVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<ContentTagQuery>({
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
type ContentTagFormData = ContentTagCreate & { id?: number }
const formData = reactive<ContentTagFormData>({
  id: undefined,
  name: '',
  color: '',
  icon: '',
  image: '',
  displayType: undefined,
  status: 1,
})

const rules = {
  name: [{ required: true, message: '请输入标签名称', trigger: 'blur' }],
}

const loadData = async () => {
  loading.value = true
  try {
    const params: ContentTagQuery = { ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize }
    const res: any = await getContentTagPage(params)
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
  Object.assign(formData, { id: undefined, name: '', color: '', icon: '', image: '', displayType: undefined, status: 1 })
  modalVisible.value = true
}

const handleEdit = async (record: ContentTagVo) => {
  isEdit.value = true
  const res: any = await getContentTagById(record.id!)
  if (res.code === 200) {
    Object.assign(formData, {
      ...res.data,
      image: res.data?.image ? String(res.data.image) : '',
      icon: res.data?.icon ? String(res.data.icon) : '',
    })
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const payload = {
      ...formData,
      image: formData.image ? String(formData.image) : '',
      icon: formData.icon ? String(formData.icon) : '',
    }
    const res: any = isEdit.value
      ? await updateContentTag(payload as ContentTagUpdate, reqId)
      : await createContentTag(payload, reqId)
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

const handleDeleteOne = (record: ContentTagVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `确定删除标签「${record.name || record.id}」？`,
    okType: 'danger',
    centered: true,
    onOk: async () => {
      const res: any = await deleteContentTag(record.id!)
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
      const res: any = await batchDeleteContentTag(selectedRowKeys.value)
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
