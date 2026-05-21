<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">分类名称</span>
            <a-input v-model:value="queryParams.name" placeholder="输入搜索..." class="elegant-input" allow-clear>
              <template #prefix><SearchOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">父级分类</span>
            <a-tree-select
              v-model:value="queryParams.parentId"
              :tree-data="treeDataForSelect"
              placeholder="选择父级分类"
              allow-clear
              tree-default-expand-all
              class="elegant-input"
              style="width:100%"
            />
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
            <div class="tab active">全部分类 <span class="badge">{{ pagination.total }}</span></div>
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
          :row-selection="{ selectedRowKeys, onChange: onSelectChange }"
          row-key="id"
          size="middle"
          :scroll="{ x: 1100 }"
          class="elegant-table"
          :pagination="false"
          :default-expand-all-rows="true"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'image'">
              <a-image v-if="record.image" :src="resolveImage(record.image)" :width="40" :height="40" />
              <span v-else class="text-secondary">-</span>
            </template>
            <template v-else-if="column.key === 'status'">
              <a-tag :color="record.status === 1 ? 'green' : 'red'">{{ getDictLabel('status', record.status) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'showInHome'">
              <a-tag :color="record.showInHome === 1 ? 'blue' : 'default'">{{ getDictLabel('yes_no', record.showInHome) }}</a-tag>
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
          <a-col :span="12"><a-form-item label="分类名称" name="name"><a-input v-model:value="formData.name" placeholder="请输入分类名称" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="父级分类" name="parentId">
            <a-tree-select
              v-model:value="formData.parentId"
              :tree-data="formTreeData"
              placeholder="选择父级分类（不选则为顶级）"
              allow-clear
              tree-default-expand-all
              style="width:100%"
            />
          </a-form-item></a-col>
          <a-col :span="12"><a-form-item label="层级" name="level"><a-input-number v-model:value="formData.level" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="排序" name="sort"><a-input-number v-model:value="formData.sort" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="图标" name="icon"><ImageUpload v-model="formData.icon" value-type="id" :limit="1" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="图片" name="image"><ImageUpload v-model="formData.image" value-type="id" :limit="1" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="首页展示" name="showInHome">
            <a-select v-model:value="formData.showInHome">
              <a-select-option v-for="item in yesNoOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="12"><a-form-item label="状态" name="status">
            <a-select v-model:value="formData.status">
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="24"><a-form-item label="描述" name="description"><TipTapEditor v-model="formData.description" placeholder="请输入分类描述" :min-height="220" /></a-form-item></a-col>
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
import type { MallProductCategoryVo, MallProductCategoryQuery, MallProductCategoryCreate, MallProductCategoryUpdate } from '@/api/mall/mallproductcategory.ts'
import { createCategory, updateCategory, deleteCategory, batchDeleteCategory, getCategoryById, getCategoryTree } from '@/api/mall/mallproductcategory.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'
import ImageUpload from '@/components/ImageUpload/index.vue'
import TipTapEditor from '@/components/TipTapEditor/index.vue'
import { getFileUrl } from '@/api/file/fileupload'

const resolveImage = (val: any) => {
  if (!val) return ''
  const str = String(val)
  if (str.startsWith('http') || str.startsWith('/')) return str
  return getFileUrl(val)
}

const statusOptions = computed(() => (getDictData('status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const yesNoOptions = computed(() => (getDictData('yes_no') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '分类名称', dataIndex: 'name', key: 'name', width: 160 },
  { title: '父级ID', dataIndex: 'parentId', key: 'parentId', width: 100 },
  { title: '层级', dataIndex: 'level', key: 'level', width: 80 },
  { title: '图片', dataIndex: 'image', key: 'image', width: 80 },
  { title: '排序', dataIndex: 'sort', key: 'sort', width: 80 },
  { title: '首页展示', dataIndex: 'showInHome', key: 'showInHome', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<MallProductCategoryVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallProductCategoryQuery>({ page: 1, pageSize: 10 })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const formData = reactive<(MallProductCategoryCreate & { id?: number }) & { parentId?: string | number }>({ id: undefined, name: '', parentId: undefined, level: 1, icon: '', image: '', sort: 0, showInHome: 0, status: 1, description: '' })
const rules = { name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }] }

const categoryTree = ref<MallProductCategoryVo[]>([])

const normalizeParentId = (parentId?: string | number | null) => {
  if (parentId === undefined || parentId === null || parentId === '' || parentId === '0' || parentId === 0) {
    return 0
  }
  return parentId
}
type CategoryTreeSelectNode = {
  label: string
  value: string
  children?: CategoryTreeSelectNode[]
}

const filterNode = (nodes: MallProductCategoryVo[], excludeId: number): MallProductCategoryVo[] => {
  return nodes
    .filter(n => n.id !== excludeId)
    .map(n => ({ ...n, children: n.children ? filterNode(n.children, excludeId) : undefined }))
}

const toTreeSelectData = (nodes: MallProductCategoryVo[]): CategoryTreeSelectNode[] => {
  return nodes.map(node => ({
    label: node.name || '',
    value: String(node.id),
    children: node.children?.length ? toTreeSelectData(node.children) : undefined,
  }))
}

const treeDataForSelect = computed<CategoryTreeSelectNode[]>(() => [{
  label: '顶级分类',
  value: '0',
  children: toTreeSelectData(categoryTree.value),
}])

const formTreeData = computed<CategoryTreeSelectNode[]>(() => {
  let data = categoryTree.value
  if (isEdit.value && formData.id) {
    data = filterNode(categoryTree.value, formData.id as number)
  }
  return toTreeSelectData(data)
})

const cloneTree = (nodes: MallProductCategoryVo[]): MallProductCategoryVo[] => {
  return nodes.map(node => ({
    ...node,
    children: node.children ? cloneTree(node.children) : undefined,
  }))
}

const findChildrenByParentId = (nodes: MallProductCategoryVo[], parentId: number): MallProductCategoryVo[] | null => {
  if (parentId === 0) {
    return cloneTree(nodes)
  }
  for (const node of nodes) {
    if (node.id === parentId) {
      return cloneTree(node.children || [])
    }
    if (node.children?.length) {
      const found = findChildrenByParentId(node.children, parentId)
      if (found !== null) {
        return found
      }
    }
  }
  return null
}

const filterTreeByQuery = (nodes: MallProductCategoryVo[]): MallProductCategoryVo[] => {
  const keyword = queryParams.name?.trim().toLowerCase()
  const status = queryParams.status

  return nodes.reduce<MallProductCategoryVo[]>((result, node) => {
    const children = node.children ? filterTreeByQuery(node.children) : undefined
    const matchedName = !keyword || (node.name || '').toLowerCase().includes(keyword)
    const matchedStatus = status === undefined || node.status === status

    if ((matchedName && matchedStatus) || (children && children.length > 0)) {
      result.push({
        ...node,
        children: children?.length ? children : undefined,
      })
    }
    return result
  }, [])
}

const countNodes = (nodes: MallProductCategoryVo[]): number => {
  return nodes.reduce((total, node) => total + 1 + countNodes(node.children || []), 0)
}

const applyTableData = (tree: MallProductCategoryVo[]) => {
  const parentId = queryParams.parentId
  const baseTree = parentId === undefined || parentId === null
    ? cloneTree(tree)
    : (findChildrenByParentId(tree, parentId) || [])
  dataSource.value = filterTreeByQuery(baseTree)
  pagination.total = countNodes(dataSource.value)
}

const loadCategoryTree = async () => {
  const res: any = await getCategoryTree()
  if (res.code === 200) {
    categoryTree.value = res.data || []
    applyTableData(categoryTree.value)
  }
}

const loadData = async () => {
  loading.value = true
  try {
    await loadCategoryTree()
  } finally { loading.value = false }
}
const reset = () => { Object.assign(queryParams, { name: '', parentId: undefined, status: undefined }); loadData() }
const onSelectChange = (keys: number[]) => { selectedRowKeys.value = keys }
const handleAdd = () => { isEdit.value = false; Object.assign(formData, { id: undefined, name: '', parentId: undefined, level: 1, icon: '', image: '', sort: 0, showInHome: 0, status: 1, description: '' }); modalVisible.value = true }
const handleEdit = async (r: MallProductCategoryVo) => {
  isEdit.value = true
  const res: any = await getCategoryById(r.id!)
  if (res.code === 200) {
    Object.assign(formData, { ...res.data, parentId: res.data?.parentId && res.data.parentId > 0 ? String(res.data.parentId) : undefined })
  }
  modalVisible.value = true
}
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const payload = { ...formData, parentId: normalizeParentId(formData.parentId) }
    const res: any = isEdit.value ? await updateCategory(payload as MallProductCategoryUpdate, reqId) : await createCategory(payload, reqId)
    if (res.code === 200) { message.success(isEdit.value ? '更新成功' : '创建成功'); modalVisible.value = false; loadData() }
  } finally { submitLoading.value = false }
}
const handleCancel = () => { modalVisible.value = false; setTimeout(() => formRef.value?.resetFields(), 300) }
const handleDeleteOne = (r: MallProductCategoryVo) => {
  Modal.confirm({ title: '确认删除', content: `确定删除分类「${r.name}」？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await deleteCategory(r.id!); if (res.code === 200) { message.success('删除成功'); loadData() } } })
}
const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({ title: '批量删除', content: `确定删除 ${selectedRowKeys.value.length} 项？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await batchDeleteCategory(selectedRowKeys.value); if (res.code === 200) { message.success('删除成功'); selectedRowKeys.value = []; loadData() } } })
}
onMounted(() => { loadData() })
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
