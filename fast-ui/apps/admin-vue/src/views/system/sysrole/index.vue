<template>
  <div class="sysrole-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">角色名称</span>
            <a-input 
              v-model:value="queryParams.title" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><UserOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">角色编码</span>
            <a-input 
              v-model:value="queryParams.code" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><KeyOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">状态</span>
            <a-select 
              v-model:value="queryParams.status" 
              placeholder="全部状态" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option v-for="item in getDictData('status')" :key="item.value" :value="Number(item.value)">
                <div class="status-option">
                  <span :class="item.value === '1' ? 'dot dot-success' : 'dot dot-error'"></span>
                  {{ item.label }}
                </div>
              </a-select-option>
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

    <!-- 主体数据区 -->
    <div class="data-board">
      <div class="board-toolbar">
        <div class="toolbar-left">
          <div class="board-tabs">
            <div class="tab active">全部角色 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增角色
          </a-button>
          <a-button 
            v-if="canDelete"
            danger 
            class="pill-btn danger-pill" 
            :class="{ 'is-disabled': selectedRowKeys.length === 0 }"
            :disabled="selectedRowKeys.length === 0" 
            @click="handleDelete()"
          >
            <DeleteOutlined /> 批量删除
          </a-button>
        </div>
      </div>

      <div class="table-wrapper">
        <a-skeleton active :loading="loading && dataSource.length === 0" :paragraph="{ rows: 10 }">
          <a-table
            :columns="columns"
            :data-source="dataSource"
            :loading="{ spinning: loading, tip: '数据加载中...', indicator: customLoadingIndicator }"
            :pagination="{
              ...pagination,
              className: 'elegant-pagination'
            }"
          :row-selection="{ selectedRowKeys, onChange: onSelectChange }"
          row-key="id"
          size="middle"
          :scroll="{ x: 800 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <!-- 角色名称列 -->
            <template v-if="column.key === 'title'">
              <div class="user-profile">
                <div class="avatar-box" :style="{ background: getAvatarColor(record.id) }">
                  {{ record.title.charAt(0).toUpperCase() }}
                </div>
                <div class="user-info">
                  <span class="user-name">{{ record.title }}</span>
                </div>
              </div>
            </template>
            
            <!-- 角色编码列 -->
            <template v-else-if="column.key === 'code'">
              <span class="text-code">{{ record.code }}</span>
            </template>

            <!-- 应用列 -->
            <template v-else-if="column.key === 'applicationCode'">
              <span class="text-muted" v-if="!record.applicationCode">--</span>
              <span v-else>{{ record.applicationCode }}</span>
            </template>

            <!-- 状态列 -->
            <template v-else-if="column.key === 'status'">
              <div class="status-toggle" :class="{ 'is-active': record.status === 1 }">
                <a-switch
                  :checked="record.status === 2"
                  size="small"
                  @change="handleStatusChange(record)"
                />
                <span class="status-text" :class="record.status === 1 ? 'text-success' : 'text-error'">
                  {{ getDictLabel('status', record.status) || '未知' }}
                </span>
              </div>
            </template>

            <!-- 操作列 -->
            <template v-else-if="column.key === 'action'">
              <div class="action-group">
                <div v-if="canUpdate" class="action-btn edit" @click="handleEdit(record)" title="编辑">
                  <EditOutlined />
                </div>
                <div v-if="canDelete" class="action-btn delete" @click="handleDeleteOne(record)" title="删除">
                  <DeleteOutlined />
                </div>
              </div>
            </template>
          </template>
          </a-table>
        </a-skeleton>
      </div>
    </div>

    <!-- 极简苹果风弹窗 -->
    <a-modal
      v-model:open="modalVisible"
      :title="null"
      :footer="null"
      width="600px"
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
            <h2>{{ isEdit ? '编辑角色信息' : '创建新角色' }}</h2>
            <p>请填写以下必填信息以完成角色设置</p>
          </div>
        </div>

        <a-form
          ref="formRef"
          :model="formData"
          layout="vertical"
          :rules="rules"
          class="glass-form"
        >
          <div class="form-section-title">基本信息</div>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item name="title">
                <template #label><span class="form-label">角色名称</span></template>
                <a-input v-model:value="formData.title" placeholder="如：管理员" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="code">
                <template #label><span class="form-label">角色编码</span></template>
                <a-input v-model:value="formData.code" placeholder="如：admin" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="status">
                <template #label><span class="form-label">角色状态</span></template>
                <StatusCardSelect 
                  v-model:value="formData.status"
                  :options="statusOptions"
                />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="permissionIds">
                <template #label><span class="form-label">权限分配</span></template>
                <div class="permissions-tree">
                  <a-tree
                    :checked-keys="formData.permissionIds"
                    :tree-data="permissionsTree"
                    :field-names="{ children: 'children', title: 'title', key: 'id' }"
                    checkable
                    :default-expand-all="true"
                    check-strictly
                    :selected-keys="[]"
                    @check="handlePermissionsCheck"
                    @select="handleNodeSelect"
                  />
                </div>
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, h, computed } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, 
  EditOutlined, UserOutlined, KeyOutlined, CheckCircleFilled, 
  MinusCircleFilled, LoadingOutlined, PlusCircleOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { SysRoleVo, SysRoleQuery, SysRoleCreate, SysRoleUpdate } from '@/api/system/sysrole.ts'
import type { SysPermissionsVo } from '@/api/system/syspermissions.ts'
import { getRolesPage, createRole, updateRole, deleteRole, batchDeleteRole, getRoleById } from '@/api/system/sysrole.ts'
import { getPermissionsTree } from '@/api/system/syspermissions.ts'
import { getRequestId } from "@/utils/idUtils.ts";
import StatusCardSelect from '@/components/StatusCardSelect/index.vue'
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'

const appStore = useAppStore()
const permissionStore = usePermissionStore()

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('admin:system:role:add')
  canUpdate.value = hasButtonPermission('admin:system:role:update')
  canDelete.value = hasButtonPermission('admin:system:role:delete')
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

// 自定义表格加载指示器
const customLoadingIndicator = h(LoadingOutlined, {
  style: {
    fontSize: '32px',
    color: '#6366f1',
  },
  spin: true,
})

// 表格列定义
const columns = [
  { title: '角色名称', dataIndex: 'title', key: 'title', width: 180 },
  { title: '角色编码', dataIndex: 'code', key: 'code', width: 150 },
  { title: '所属应用', dataIndex: 'applicationCode', key: 'applicationCode', width: 150 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const requestId = ref<string>('')
const dataSource = ref<SysRoleVo[]>([])
const selectedRowKeys = ref<string[]>([])

const queryParams = reactive<SysRoleQuery>({
  page: 1,
  pageSize: 10,
  title: '',
  code: '',
  status: undefined,
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
type SysRoleFormData = SysRoleCreate & { id?: string }
const formData = reactive<SysRoleFormData>({
  id: undefined,
  title: '',
  code: '',
  status: 0,
  applicationId: undefined,
  applicationCode: '',
  permissionIds: [],
})

const permissionsTree = ref<SysPermissionsVo[]>([])

const normalizeTreePermissions = (permissions: any): string[] => {
  const rawKeys = Array.isArray(permissions)
    ? permissions
    : Array.isArray(permissions?.checked)
      ? permissions.checked
      : []

  return rawKeys
    .map((key: any) => String(key))
    .filter((key: string) => key !== 'undefined' && key !== 'null' && key !== '')
}

const normalizeSubmitPermissions = (permissions: any): string[] => {
  return normalizeTreePermissions(permissions)
}

const handlePermissionsCheck = (checkedKeys: any) => {
  formData.permissionIds = normalizeTreePermissions(checkedKeys) as any
}

// 获取节点及其所有子节点的 ID
const getAllNodeIds = (node: any, ids: string[] = []) => {
  if (!node) return ids
  const id = node.id || node.key
  if (id !== undefined && id !== null && id !== '') {
    ids.push(String(id))
  }
  const children = node.children || []
  if (Array.isArray(children)) {
    children.forEach((child: any) => {
      getAllNodeIds(child, ids)
    })
  }
  return ids
}

// 点击树节点文字时触发全选/取消全选该节点及子节点
const handleNodeSelect = (_selectedKeys: any, e: any) => {
  const nodeData = e.node.dataRef || e.node
  if (!nodeData) return
  
  const nodeIds = getAllNodeIds(nodeData)
  const currentCheckedKeys = new Set(formData.permissionIds || [])
  const isChecked = currentCheckedKeys.has(String(nodeData.id))
  
  if (isChecked) {
    // 如果当前节点已勾选，则取消该节点及所有子节点的勾选
    nodeIds.forEach(id => currentCheckedKeys.delete(id))
  } else {
    // 否则全选该节点及其所有子节点
    nodeIds.forEach(id => currentCheckedKeys.add(id))
  }
  
  formData.permissionIds = Array.from(currentCheckedKeys)
}

const convertTreeIdsToString = (tree: any[]): any[] => {
  return tree.map(node => ({
    ...node,
    id: String(node.id),
    children: node.children ? convertTreeIdsToString(node.children) : []
  }))
}

const loadPermissions = async () => {
  try {
    const res = await getPermissionsTree()
    if (res.code === 200) {
      permissionsTree.value = convertTreeIdsToString(res.data || [])
    }
  } catch (error) {
    console.error('加载权限树失败:', error)
  }
}

const rules = {
  title: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  code: [{ required: true, message: '请输入角色编码', trigger: 'blur' }],
}

// 辅助函数
const colors = [appStore.themeColor, '#10b981', '#8b5cf6', '#ec4899', '#ef4444', '#f59e0b', '#eab308']
const getAvatarColor = (id: string | undefined) => {
  if (!id) return colors[0]
  let hash = 0
  for (let i = 0; i < id.length; i++) {
    hash = id.charCodeAt(i) + ((hash << 5) - hash)
  }
  return colors[Math.abs(hash) % colors.length]
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getRolesPage(params)
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
  queryParams.title = ''
  queryParams.code = ''
  queryParams.status = undefined
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: string[]) => {
  selectedRowKeys.value = keys
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined, title: '', code: '', status: 0,
    permissionIds: [],
  } as any)
  modalVisible.value = true
}

const handleEdit = async (record: SysRoleVo) => {
  isEdit.value = true
  try {
    const res = await getRoleById(record.id!)
    if (res.code === 200) {
      const roleData = res.data
      Object.assign(formData, { 
        ...roleData,
        permissionIds: normalizeTreePermissions(roleData.permissionIds),
      })
    }
  } catch (error) {
    console.error('获取角色详情失败:', error)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    requestId.value = getRequestId();

    const submitData = {
      ...formData,
      permissionIds: normalizeSubmitPermissions(formData.permissionIds)
    }
    
    if (isEdit.value) {
      const res = await updateRole(submitData as SysRoleUpdate, requestId.value)
      if (res.code === 200) {
        message.success({ content: '角色更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      delete (submitData as any).id
      const res = await createRole(submitData as SysRoleCreate, requestId.value)
      if (res.code === 200) {
        message.success({ content: '角色创建成功', class: 'custom-message' })
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

const handleDeleteOne = (record: SysRoleVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除角色「${record.title}」，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteRole(record.id!)
      if (res.code === 200) {
        message.success('已彻底删除')
        loadData()
      }
    },
  })
}

const handleDelete = () => {
  if (selectedRowKeys.value.length === 0) return
  Modal.confirm({
    title: '批量删除确认',
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个角色，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteRole(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: SysRoleVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  requestId.value = getRequestId();
  const res = await updateRole({ ...record, status: newStatus } as SysRoleUpdate, requestId.value)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '角色已启用' : '角色已禁用')
  }
}

onMounted(() => {
  loadData()
  loadPermissions()
  loadButtonPermissions()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

/* 局部微调适应新主题 */


.text-code {
  font-family: 'Monaco', 'Menlo', monospace;
  background: #f1f5f9;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 13px;
  color: #475569;
}

.permissions-tree {
  max-height: 300px;
  overflow-y: auto;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  padding: 8px;
}
</style>
