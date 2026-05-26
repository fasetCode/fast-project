<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form class="elegant-form">
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
            <div class="tab active">分类列表 <span class="badge">{{ totalCount }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增分类
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
          :pagination="false"
          :row-selection="{ selectedRowKeys, onChange: onSelectChange }"
          row-key="id"
          size="middle"
          :scroll="{ x: 1000 }"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'status'">
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

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑分类' : '新增分类'" width="700px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="12"><a-form-item label="分类名称" name="name"><a-input v-model:value="formData.name" /></a-form-item></a-col>
          <a-col :span="12">
            <a-form-item label="父级分类" name="parentId">
              <a-tree-select
                v-model:value="formData.parentId"
                :tree-data="parentTreeData"
                :field-names="{ label: 'title', value: 'value', children: 'children' }"
                allow-clear
                tree-default-expand-all
                style="width:100%"
                placeholder="请选择父级分类（不选为顶级）"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12"><a-form-item label="排序" name="sort"><a-input-number v-model:value="formData.sort" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="状态" name="status">
            <a-select v-model:value="formData.status">
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="24"><a-form-item label="备注" name="remark"><a-textarea v-model:value="formData.remark" :rows="3" /></a-form-item></a-col>
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
import type { ContentCategoryTreeVo, ContentCategoryCreate, ContentCategoryUpdate } from '@/api/content/contentcategory'
import { getContentCategoryTree, getContentCategoryById, createContentCategory, updateContentCategory, deleteContentCategory, batchDeleteContentCategory } from '@/api/content/contentcategory'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const statusOptions = computed(() => (getDictData('status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 90 },
  { title: '分类名称', dataIndex: 'name', key: 'name', width: 200 },
  { title: '父级ID', dataIndex: 'parentId', key: 'parentId', width: 110 },
  { title: '排序', dataIndex: 'sort', key: 'sort', width: 90 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<ContentCategoryTreeVo[]>([])
const selectedRowKeys = ref<number[]>([])

const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
type ContentCategoryFormData = ContentCategoryCreate & { id?: number }
const formData = reactive<ContentCategoryFormData>({
  id: undefined,
  name: '',
  parentId: 0,
  sort: 0,
  status: 1,
  remark: '',
})

const rules = {
  name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
}

const countTree = (nodes: ContentCategoryTreeVo[] | undefined): number => {
  if (!nodes?.length) return 0
  let count = 0
  for (const n of nodes) {
    count += 1 + countTree(n.children)
  }
  return count
}

const totalCount = computed(() => countTree(dataSource.value))

type TreeSelectNode = { title: string; value: number; children?: TreeSelectNode[] }
const buildTreeSelect = (nodes: ContentCategoryTreeVo[] | undefined): TreeSelectNode[] => {
  if (!nodes?.length) return []
  return nodes.map((n) => ({
    title: n.name || `${n.id}`,
    value: Number(n.id),
    children: buildTreeSelect(n.children),
  }))
}

const parentTreeData = computed<TreeSelectNode[]>(() => [
  { title: '顶级分类', value: 0, children: buildTreeSelect(dataSource.value) },
])

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getContentCategoryTree()
    if (res.code === 200) {
      dataSource.value = res.data || []
    }
  } finally {
    loading.value = false
  }
}

const reset = () => {
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(formData, { id: undefined, name: '', parentId: 0, sort: 0, status: 1, remark: '' })
  modalVisible.value = true
}

const handleEdit = async (record: ContentCategoryTreeVo) => {
  isEdit.value = true
  const res: any = await getContentCategoryById(record.id!)
  if (res.code === 200) {
    Object.assign(formData, { ...res.data, parentId: res.data?.parentId ?? 0 })
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const payload = { ...formData }
    if (payload.parentId === 0) payload.parentId = undefined
    const res: any = isEdit.value
      ? await updateContentCategory(payload as ContentCategoryUpdate, reqId)
      : await createContentCategory(payload, reqId)
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

const handleDeleteOne = (record: ContentCategoryVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `确定删除分类「${record.name || record.id}」？`,
    okType: 'danger',
    centered: true,
    onOk: async () => {
      const res: any = await deleteContentCategory(record.id!)
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
      const res: any = await batchDeleteContentCategory(selectedRowKeys.value)
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
