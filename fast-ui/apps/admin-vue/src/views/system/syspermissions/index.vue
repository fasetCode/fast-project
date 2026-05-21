<template>
  <div class="syspermissions-container">
    <!-- 主体数据区 -->
    <div class="data-board">
      <div class="board-toolbar">
        <div class="toolbar-left">
          <div class="board-title">权限管理</div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增权限
          </a-button>
          <a-button v-if="canAdd" class="pill-btn batch-pill" @click="handleBatchAdd()">
            <PlusOutlined /> 批量添加
          </a-button>
        </div>
      </div>

      <div class="tree-wrapper">
        <a-spin :spinning="loading">
          <a-tree
            :tree-data="treeData"
            :show-line="{ showLeafIcon: false }"
            :selectable="false"
            :default-expand-all="true"
            :block-node="true"
          >
            <template #title="{ title, dataRef }">
              <div class="tree-node">
                <span class="tree-icon" :class="getTreeIconClass(dataRef.type)">
                  <FolderOutlined v-if="dataRef.type === 0" />
                  <MenuOutlined v-else-if="dataRef.type === 1" />
                  <ApiOutlined v-else />
                </span>
                <span class="tree-title">{{ title }}</span>
                <span class="tree-type-badge" :class="getTypeClass(dataRef.type)">{{ getTypeName(dataRef.type) }}</span>
                <span class="tree-actions">
                  <EditOutlined v-if="canUpdate" class="action-icon" @click.stop="handleEdit({ id: dataRef.id, title })" />
                  <PlusOutlined v-if="canAdd" class="action-icon" @click.stop="handleAddChild({ id: dataRef.id, title })" title="添加子级" />
                  <FieldNumberOutlined v-if="canAdd" class="action-icon batch-icon" @click.stop="handleBatchAddChild({ id: dataRef.id, title })" title="批量添加" />
                  <DeleteOutlined v-if="canDelete" class="action-icon delete" @click.stop="handleDeleteOne({ id: dataRef.id, title })" />
                </span>
              </div>
            </template>
          </a-tree>
        </a-spin>
      </div>
    </div>

    <!-- 弹窗 -->
    <a-modal
      v-model:open="modalVisible"
      :title="null"
      :footer="null"
      width="700px"
      :destroy-on-close="true"
      class="glass-modal"
      centered
      @cancel="handleCancel"
    >
      <div class="modal-inner">
        <div class="modal-header">
          <div class="modal-icon" :class="isEdit ? 'edit-icon' : 'add-icon'">
            <EditOutlined v-if="isEdit" />
            <PlusCircleOutlined v-else />
          </div>
          <div class="modal-titles">
            <h2>{{ isEdit ? '编辑权限信息' : '创建新权限' }}</h2>
            <p>请填写以下必填信息以完成权限设置</p>
          </div>
        </div>

        <a-form
          ref="formRef"
          :model="formData"
          layout="vertical"
          :rules="dynamicRules"
          class="glass-form"
        >
          <div class="form-section-title">基本信息</div>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item name="parentId">
                <template #label><span class="form-label">上级权限</span></template>
                <a-tree-select
                  v-model:value="formData.parentId"
                  :tree-data="treeDataSimple"
                  :replace="{ title: 'title', value: 'value', children: 'children' }"
                  placeholder="选择上级权限"
                  allow-clear
                  tree-default-expand-all
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="title">
                <template #label><span class="form-label">权限名称</span></template>
                <a-input v-model:value="formData.title" placeholder="如：用户管理"/>
              </a-form-item>
            </a-col>
            <a-col :span="12" v-if="formData.type !== 0">
              <a-form-item name="code">
                <template #label><span class="form-label">权限编码</span></template>
                <a-input v-model:value="formData.code" placeholder="如：system:user" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="type">
                <template #label><span class="form-label">权限类型</span></template>
                <a-select v-model:value="formData.type" placeholder="请选择类型" @change="handleTypeChange">
                  <a-select-option :value="0">目录</a-select-option>
                  <a-select-option :value="1">菜单</a-select-option>
                  <a-select-option :value="2">按钮</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="sort">
                <template #label><span class="form-label">排序</span></template>
                <a-input-number v-model:value="formData.sort" :min="0" placeholder="数字越小越靠前" style="width: 100%" />
              </a-form-item>
            </a-col>
            <a-col :span="24" v-if="formData.type !== 0">
              <a-form-item name="url">
                <template #label><span class="form-label">权限路径</span></template>
                <a-input v-model:value="formData.url" placeholder="/system/user" />
              </a-form-item>
            </a-col>
            <a-col :span="24" v-if="formData.type === 1">
              <a-form-item name="component">
                <template #label><span class="form-label">组件路径</span></template>
                <div style="display: flex; gap: 8px;">
                  <a-input v-model:value="formData.component" placeholder="views/system/sysuser/index.vue" />
                  <a-button class="glass-btn" style="height: auto; padding: 0 16px; border-radius: 6px;" @click="generateComponentPath">自动生成</a-button>
                </div>
              </a-form-item>
            </a-col>
            <a-col :span="12" v-if="formData.type === 1">
              <a-form-item name="componentName">
                <template #label><span class="form-label">组件名称</span></template>
                <a-input v-model:value="formData.componentName" placeholder="如：SysUser" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="icon">
                <template #label><span class="form-label">图标</span></template>
                <IconSelector v-model:value="formData.icon" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="status">
                <template #label><span class="form-label">权限状态</span></template>
                <StatusCardSelect 
                  v-model:value="formData.status"
                  :options="statusOptions"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handleCancel">取消</a-button>
          <a-button type="primary" class="gradient-btn-lg" @click="handleSubmit" :loading="submitLoading">
            {{ isEdit ? '保存修改' : '确认创建' }}
          </a-button>
        </div>
      </div>
    </a-modal>

    <!-- 批量添加弹窗 -->
    <a-modal
      v-model:open="batchModalVisible"
      :title="null"
      :footer="null"
      width="900px"
      :destroy-on-close="true"
      class="glass-modal"
      centered
      @cancel="handleBatchCancel"
    >
      <div class="modal-inner">
        <div class="modal-header">
          <div class="modal-icon add-icon">
            <PlusCircleOutlined />
          </div>
          <div class="modal-titles">
            <h2>批量添加权限</h2>
            <p>通过表格方式快速添加多个权限，每行一个</p>
          </div>
        </div>

        <div class="batch-common-fields">
          <a-space>
            <span class="batch-label">上级权限：</span>
            <a-tree-select
              v-model:value="batchParentId"
              :tree-data="treeData"
              :replace="{ title: 'title', value: 'value', children: 'children' }"
              placeholder="选择上级权限（可选）"
              allow-clear
              tree-default-expand-all
              style="width: 300px"
            />
          </a-space>
        </div>

        <div class="batch-table-wrapper">
          <a-table
            :columns="batchColumns"
            :data-source="batchData"
            :pagination="false"
            size="small"
            bordered
            class="batch-table"
          >
            <template #bodyCell="{ column, index }">
              <template v-if="column.key === 'title'">
                <a-input v-model:value="batchData[index].title" placeholder="权限名称" />
              </template>
              <template v-else-if="column.key === 'code'">
                <a-input v-model:value="batchData[index].code" placeholder="权限编码" />
              </template>
              <template v-else-if="column.key === 'componentName'">
                <a-input v-model:value="batchData[index].componentName" placeholder="组件名" />
              </template>
              <template v-else-if="column.key === 'type'">
                <a-select v-model:value="batchData[index].type" placeholder="类型" style="width: 100%">
                  <a-select-option :value="0">目录</a-select-option>
                  <a-select-option :value="1">菜单</a-select-option>
                  <a-select-option :value="2">按钮</a-select-option>
                </a-select>
              </template>
              <template v-else-if="column.key === 'sort'">
                <a-input-number v-model:value="batchData[index].sort" :min="0" style="width: 100%" />
              </template>
              <template v-else-if="column.key === 'action'">
                <DeleteOutlined class="action-icon delete" @click="handleRemoveRow(index)" />
              </template>
            </template>
          </a-table>
          <div class="batch-actions">
            <a-button type="dashed" class="add-row-btn" @click="handleAddRow">
              <PlusOutlined /> 添加一行
            </a-button>
          </div>
        </div>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handleBatchCancel">取消</a-button>
          <a-button type="primary" class="gradient-btn-lg" @click="handleBatchSubmit" :loading="batchSubmitLoading">
            确认创建 ({{ batchData.length }})
          </a-button>
        </div>
      </div>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { message, Modal, Space } from 'ant-design-vue'
import {
  PlusOutlined, DeleteOutlined, 
  EditOutlined, CheckCircleFilled, 
  MinusCircleFilled, PlusCircleOutlined,
  FolderOutlined, MenuOutlined, ApiOutlined,
  FieldNumberOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { SysPermissionsVo, SysPermissionsCreate, SysPermissionsUpdate } from '@/api/system/syspermissions.ts'
import { createPermissions, updatePermissions, deletePermissions, getPermissionsTree, getPermissionsById } from '@/api/system/syspermissions.ts'
import { getRequestId } from "@/utils/idUtils.ts";
import StatusCardSelect from '@/components/StatusCardSelect/index.vue'
import IconSelector from '@/components/IconSelector/index.vue'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData } from '@/utils/dict'

const permissionStore = usePermissionStore()

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('admin:system:permissions:add')
  canUpdate.value = hasButtonPermission('admin:system:permissions:update')
  canDelete.value = hasButtonPermission('admin:system:permissions:delete')
}

type SysPermissionsFormData = Omit<SysPermissionsCreate, 'parentId'> & {
  id?: string
  parentId?: string
}

const statusOptions = computed(() => {
  const statusData = getDictData('status') || []
  return statusData.map((item: any) => ({
    value: Number(item.value),
    title: item.label,
    icon: item.value === '1' ? CheckCircleFilled : MinusCircleFilled,
    type: item.value === '1' ? 'success' as const : 'error' as const
  }))
})

const loading = ref(false)
const submitLoading = ref(false)
const requestId = ref<string>('')
const treeData = ref<any[]>([])

const treeDataSimple = computed(() => {
  const data = treeData.value
  if (!isEdit.value || !formData.id) return data
  return filterNode(data, formData.id)
})

const filterNode = (nodes: any[], excludeId: string): any[] => {
  return nodes
    .filter(node => node.id !== excludeId)
    .map(node => ({
      ...node,
      children: node.children ? filterNode(node.children, excludeId) : undefined
    }))
}

const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()

// 批量添加相关
const batchModalVisible = ref(false)
const batchSubmitLoading = ref(false)
const batchData = ref<any[]>([])
const batchParentId = ref<any>(undefined)

const batchColumns = [
  { title: '权限名称', key: 'title', width: 160 },
  { title: '权限编码', key: 'code', width: 160 },
  { title: '组件名', key: 'componentName', width: 140 },
  { title: '类型', key: 'type', width: 90 },
  { title: '排序', key: 'sort', width: 70 },
  { title: '操作', key: 'action', width: 60, align: 'center' as const }
]

const initBatchRow = () => ({
  parentId: undefined as any,
  title: '',
  code: '',
  componentName: '',
  type: 2,
  sort: 0,
})

const handleBatchAdd = () => {
  batchParentId.value = undefined
  batchData.value = [initBatchRow()]
  batchModalVisible.value = true
}

const handleBatchAddChild = (record: { id: string; title: string }) => {
  batchParentId.value = String(record.id)
  batchData.value = [initBatchRow()]
  batchModalVisible.value = true
}

const handleAddRow = () => {
  batchData.value.push(initBatchRow())
}

const handleRemoveRow = (index: number) => {
  batchData.value.splice(index, 1)
}

const handleBatchCancel = () => {
  batchModalVisible.value = false
  batchData.value = []
  batchParentId.value = undefined
}

const handleBatchSubmit = async () => {
  const validData = batchData.value.filter(item => item.title && item.code)
  if (validData.length === 0) {
    message.warning('请至少填写完整的权限名称和编码')
    return
  }

  try {
    batchSubmitLoading.value = true
    let successCount = 0
    let failCount = 0

    for (const item of validData) {
      try {
        const submitData: SysPermissionsCreate = {
          ...item,
          url: '',
          component: '',
          icon: '',
          status: 0,
          applicationId: undefined,
          applicationCode: '',
          parentId: toApiParentId(batchParentId.value),
        }
        requestId.value = getRequestId();
        const res = await createPermissions(submitData, requestId.value)
        if (res.code === 200) {
          successCount++
        } else {
          failCount++
        }
      } catch (error) {
        failCount++
      }
    }

    if (successCount > 0) {
      message.success({ content: `成功创建 ${successCount} 个权限${failCount > 0 ? `，失败 ${failCount} 个` : ''}`, class: 'custom-message' })
    } else {
      message.error('创建失败')
    }

    batchModalVisible.value = false
    loadData()
  } catch (error) {
    console.error(error)
  } finally {
    batchSubmitLoading.value = false
  }
}

const formData = reactive<SysPermissionsFormData>({
  id: undefined,
  title: '',
  code: '',
  url: '',
  type: 0,
  component: '',
  icon: '',
  status: 0,
  parentId: undefined as any,
  componentName: '',
  sort: 0,
  applicationId: undefined,
  applicationCode: '',
})

const toTreeValue = (v: unknown): string | undefined => {
  if (v === undefined || v === null || v === '') return undefined
  return String(v)
}

const toApiParentId = (v: unknown): string | null => {
  if (v === undefined || v === null || v === '') return null
  return String(v)
}

const getRules = () => {
  const baseRules = [
    { required: true, message: '请输入权限名称', trigger: 'blur' }
  ]
  if (formData.type !== 0) {
    baseRules.push({ required: true, message: '请输入权限编码', trigger: 'blur' })
  }
  return { title: baseRules.slice(0, 1), code: baseRules.length > 1 ? baseRules.slice(1) : [] }
}

const dynamicRules = computed(() => getRules())

const getTypeClass = (type: number | undefined) => {
  if (type === 0) return 'type-directory'
  if (type === 1) return 'type-menu'
  if (type === 2) return 'type-button'
  return 'type-unknown'
}

const getTypeName = (type: number | undefined) => {
  if (type === 0) return '目录'
  if (type === 1) return '菜单'
  if (type === 2) return '按钮'
  return '未知'
}

const getTreeIconClass = (type: number | undefined) => {
  if (type === 0) return 'icon-directory'
  if (type === 1) return 'icon-menu'
  if (type === 2) return 'icon-button'
  return 'icon-default'
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPermissionsTree()
    if (res.code === 200) {
      treeData.value = transformTreeData(res.data || [])
    }
  } finally {
    loading.value = false
  }
}

const transformTreeData = (data: SysPermissionsVo[]): any[] => {
  return data.map(item => ({
    key: String(item.id),
    value: String(item.id),
    title: item.title,
    type: item.type,
    icon: item.icon,
    id: item.id,
    parentId: item.parentId !== undefined && item.parentId !== null ? String(item.parentId) : undefined,
    children: item.children ? transformTreeData(item.children) : undefined
  }))
}

const handleTypeChange = () => {
  if (formData.type === 0) {
    formData.code = ''
    formData.url = ''
    formData.component = ''
  }
}

const generateComponentPath = () => {
  if (!formData.url) {
    message.warning('请先输入权限路径')
    return
  }
  let path = formData.url
  if (path.startsWith('/')) {
    path = path.slice(1)
  }
  formData.component = `views/${path}/index`
  
  // 自动生成组件名称：将路径转换为 PascalCase
  // 例如：system/sysuser -> SystemSysuser -> SystemSysuserView
  const parts = path.split('/')
  const pascalCase = parts.map(part => {
    // 处理短横线连接的词，如：sys-user -> SysUser
    return part.split('-').map(word => 
      word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()
    ).join('')
  }).join('')
  formData.componentName = pascalCase
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined, title: '', code: '', url: '', type: 0,
    component: '', icon: '', status: 0, sort: 0, parentId: undefined as any,
  })
  modalVisible.value = true
}

const handleAddChild = (record: { id: string; title: string }) => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined, title: '', code: '', url: '', type: 1,
    component: '', icon: '', status: 0, sort: 0, parentId: String(record.id),
  })
  modalVisible.value = true
}

const handleEdit = async (record: { id: string; title?: string }) => {
  isEdit.value = true
  const res = await getPermissionsById(record.id!)
  if (res.code === 200) {
    const editData: any = { ...res.data }
    editData.parentId = toTreeValue(editData.parentId)
    Object.assign(formData, editData)
  }
  modalVisible.value = true
}

const findNode = (nodes: any[], id: number): SysPermissionsVo | null => {
  for (const node of nodes) {
    if (node.id === id) return node
    if (node.children) {
      const found = findNode(node.children, id)
      if (found) return found
    }
  }
  return null
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    requestId.value = getRequestId();
    if (isEdit.value) {
      const submitData: SysPermissionsUpdate = {
        ...(formData as any),
        id: formData.id as string,
        parentId: toApiParentId(formData.parentId),
      }
      const res = await updatePermissions(submitData, requestId.value)
      if (res.code === 200) {
        message.success({ content: '权限更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData: SysPermissionsCreate = {
        ...(formData as any),
        parentId: toApiParentId(formData.parentId),
      }
      delete (submitData as any).id
      const res = await createPermissions(submitData, requestId.value)
      if (res.code === 200) {
        message.success({ content: '权限创建成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    }
  } catch (error) {
    console.error(error)
  } finally {
    submitLoading.value = false
  }
}

const handleCancel = () => {
  modalVisible.value = false
  setTimeout(() => { formRef.value?.resetFields() }, 300)
}

const handleDeleteOne = (record: { id: string; title: string }) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除权限「${record.title}」，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deletePermissions(record.id)
      if (res.code === 200) {
        message.success('已彻底删除')
        loadData()
      }
    },
  })
}

onMounted(() => {
  loadData()
  loadButtonPermissions()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.board-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

:global(html.dark) .board-title {
  color: #f1f5f9;
}

.tree-wrapper {
  padding: 16px;
 
  border-radius: 8px;
}

:global(html.dark) .tree-wrapper {
  background: transparent;
}

.tree-node {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 4px 8px;
  border-radius: 4px;
  transition: background 0.2s;
}

 

:global(html.dark) .tree-node:hover {
  background: #1e293b;
}

:global(html.dark) .tree-title {
  color: #e2e8f0;
}

.tree-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
  height: 20px;
  border-radius: 4px;
}

.icon-directory {
  color: #52c41a;
}

.icon-menu {
  color: var(--app-primary-color);
}

.icon-button {
  color: #fa8c16;
}

.tree-type-badge {
  font-size: 11px;
  padding: 1px 6px;
  border-radius: 3px;
}

.type-directory {
  background: #f6ffed;
  color: #52c41a;
}

:global(html.dark) .type-directory {
  background: rgba(82, 196, 26, 0.2);
}

.type-menu {
  background: var(--app-primary-color-light);
  color: var(--app-primary-color);
}

:global(html.dark) .type-menu {
  background: rgba(99, 102, 241, 0.2);
}

.type-button {
  background: #fff7e6;
  color: #fa8c16;
}

:global(html.dark) .type-button {
  background: rgba(250, 140, 22, 0.2);
}

.type-unknown {
  background: #f5f5f5;
  color: #999;
}

:global(html.dark) .type-unknown {
  background: #334155;
  color: #94a3b8;
}

.tree-actions {
  margin-left: 8px;
  display: none;
}

.tree-node:hover .tree-actions {
  display: inline-flex;
  gap: 8px;
}

.action-icon {
  cursor: pointer;
  color: #6366f1;
}

.action-icon.batch-icon {
  color: #52c41a;
}

.action-icon.delete {
  color: #ff4d4f;
}

.icon-btn.primary-text {
  color: var(--app-primary-color);
  background: var(--app-primary-color-light);
}
.icon-btn.primary-text:hover {
  background: var(--app-primary-color);
  color: white;
}

.batch-pill {
  background: #fff;
  border: 1px solid var(--app-primary-color);
  color: var(--app-primary-color);
}

:global(html.dark) .batch-pill {
  background: transparent;
  border: 1px solid var(--app-primary-color);
  color: var(--app-primary-color);
}

.batch-pill:hover {
  background: var(--app-primary-color);
  color: #fff;
}

.batch-common-fields {
  margin-bottom: 16px;
  padding: 12px 16px;
  background: var(--app-primary-color-light);
  border-radius: 6px;
}

:global(html.dark) .batch-common-fields {
  background: rgba(99, 102, 241, 0.1);
}

.batch-label {
  font-weight: 500;
  color: #333;
}

:global(html.dark) .batch-label {
  color: #e2e8f0;
}

.batch-table-wrapper {
  margin: 20px 0;
}

.batch-table :deep(.ant-table) {
  background: transparent;
}

.batch-actions {
  margin-top: 12px;
  display: flex;
  justify-content: flex-start;
}

.add-row-btn {
  border-style: dashed;
  color: var(--app-primary-color);
}

.add-row-btn:hover {
  color: var(--app-primary-color);
  border-color: var(--app-primary-color);
}
</style>
