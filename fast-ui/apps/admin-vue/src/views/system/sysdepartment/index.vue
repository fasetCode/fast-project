<template>
  <div class="sysdepartment-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">部门名称</span>
            <a-input 
              v-model:value="queryParams.name" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><TeamOutlined class="text-secondary" /></template>
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
            <div class="tab active">全部部门 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增部门
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
            :scroll="{ x: 1000 }"
            @change="handleTableChange"
            class="elegant-table"
            :default-expand-all-rows="true"
          >
            <template #bodyCell="{ column, record }">
              <!-- 部门名称列 -->
              <template v-if="column.key === 'name'">
                <div class="user-profile">
                  <div class="avatar-box" :style="{ background: getAvatarColor(record.id) }">
                    <TeamOutlined />
                  </div>
                  <div class="user-info">
                    <span class="user-name">{{ record.name }}</span>
                  </div>
                </div>
              </template>

              <!-- 负责人列 -->
              <template v-else-if="column.key === 'leader'">
                <span class="text-muted" v-if="!record.leader">--</span>
                <span v-else>{{ record.leader }}</span>
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
                  <div v-if="canAdd" class="action-btn add" @click="handleAddChild(record)" title="添加子部门">
                    <PlusOutlined />
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

    <!-- 弹窗 -->
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
            <h2>{{ isEdit ? '编辑部门信息' : '创建新部门' }}</h2>
            <p>请填写以下必填信息以完成部门设置</p>
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
            <a-col :span="24">
              <a-form-item name="parentId">
                <template #label><span class="form-label">上级部门</span></template>
                <a-tree-select
                  v-model:value="formData.parentId"
                  :tree-data="treeDataForSelect"
                  placeholder="选择上级部门（不选则为顶级部门）"
                  allow-clear
                  tree-default-expand-all
                  :field-names="{ children: 'children', label: 'name', value: 'id' }"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="name">
                <template #label><span class="form-label">部门名称</span></template>
                <a-input v-model:value="formData.name" placeholder="如：研发部"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="sort">
                <template #label><span class="form-label">排序</span></template>
                <a-input-number v-model:value="formData.sort" :min="0" placeholder="数字越小越靠前" style="width: 100%" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="leader">
                <template #label><span class="form-label">负责人</span></template>
                <a-input v-model:value="formData.leader" placeholder="负责人姓名" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="phone">
                <template #label><span class="form-label">联系电话</span></template>
                <a-input v-model:value="formData.phone" placeholder="手机号" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="email">
                <template #label><span class="form-label">邮箱</span></template>
                <a-input v-model:value="formData.email" placeholder="邮箱地址" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="status">
                <template #label><span class="form-label">部门状态</span></template>
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, h, computed } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, 
  EditOutlined, TeamOutlined, CheckCircleFilled, 
  MinusCircleFilled, LoadingOutlined, PlusCircleOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { SysDepartmentVo, SysDepartmentCreate, SysDepartmentUpdate, SysDepartmentQuery } from '@/api/system/sysdepartment.ts'
import { getDepartmentPage, createDepartment, updateDepartment, deleteDepartment, getDepartmentTree, getDepartmentById } from '@/api/system/sysdepartment.ts'
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
  canAdd.value = hasButtonPermission('admin:system:department:add')
  canUpdate.value = hasButtonPermission('admin:system:department:update')
  canDelete.value = hasButtonPermission('admin:system:department:delete')
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
  { title: '部门名称', dataIndex: 'name', key: 'name', width: 200 },
  { title: '负责人', dataIndex: 'leader', key: 'leader', width: 120 },
  { title: '联系电话', dataIndex: 'phone', key: 'phone', width: 130 },
  { title: '邮箱', dataIndex: 'email', key: 'email', width: 180 },
  { title: '排序', dataIndex: 'sort', key: 'sort', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '操作', key: 'action', width: 150, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const requestId = ref<string>('')
const dataSource = ref<SysDepartmentVo[]>([])
const treeData = ref<any[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<SysDepartmentQuery>({
  page: 1,
  pageSize: 10,
  name: '',
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

type SysDepartmentFormData = SysDepartmentCreate & {
  id?: number
  parentId?: string | number | null
}

const formData = reactive<SysDepartmentFormData>({
  id: undefined,
  name: '',
  parentId: undefined,
  sort: 0,
  leader: '',
  phone: '',
  email: '',
  status: 0,
})

// TreeSelect需要的数据格式，添加"顶级部门"选项
const treeDataForSelect = computed(() => {
  let data = treeData.value
  // 编辑时过滤掉自身及子节点
  if (isEdit.value && formData.id) {
    data = filterNode(data, formData.id as number)
  }
  // 添加顶级部门选项
  return [
    { id: 0, name: '顶级部门', children: data }
  ]
})

const filterNode = (nodes: any[], excludeId: number): any[] => {
  return nodes
    .filter(node => node.id !== excludeId)
    .map(node => ({
      ...node,
      children: node.children ? filterNode(node.children, excludeId) : undefined
    }))
}

const rules = {
  name: [{ required: true, message: '请输入部门名称', trigger: 'blur' }],
}

// 辅助函数
const colors = [appStore.themeColor, '#10b981', '#8b5cf6', '#ec4899', '#ef4444', '#f59e0b', '#eab308']
const getAvatarColor = (id: number | undefined) => {
  if (!id) return colors[0]
  return colors[id % colors.length]
}

const loadData = async () => {
  loading.value = true
  try {
    // 获取树形数据用于表格展示
    const res = await getDepartmentTree()
    if (res.code === 200) {
      treeData.value = transformTreeData(res.data || [])
      // 扁平化为表格数据（保持树形结构，a-table会自动处理）
      dataSource.value = treeData.value
      pagination.total = countNodes(treeData.value)
    }
  } finally {
    loading.value = false
  }
}

// 统计节点总数
const countNodes = (nodes: any[]): number => {
  let count = nodes.length
  for (const node of nodes) {
    if (node.children && node.children.length > 0) {
      count += countNodes(node.children)
    }
  }
  return count
}

// 转换树形数据
const transformTreeData = (data: SysDepartmentVo[]): any[] => {
  return data.map(item => ({
    key: item.id,
    ...item,
    children: item.children ? transformTreeData(item.children) : undefined
  }))
}

const handleTableChange = (pag: any) => {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  loadData()
}

const reset = () => {
  queryParams.name = ''
  queryParams.status = undefined
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined, name: '', parentId: undefined, sort: 0,
    leader: '', phone: '', email: '', status: 0,
  })
  modalVisible.value = true
}

const handleAddChild = (record: SysDepartmentVo) => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined, name: '', parentId: String(record.id), sort: 0,
    leader: '', phone: '', email: '', status: 0,
  })
  modalVisible.value = true
}

// 转换parentId：TreeSelect返回"0"表示顶级部门，需要转为null
const convertParentId = (value: string | number | undefined | null): number | null => {
  if (value === undefined || value === null || value === '' || value === '0') {
    return null
  }
  return value as number
}

const handleEdit = async (record: SysDepartmentVo) => {
  isEdit.value = true
  const res = await getDepartmentById(record.id!)
  if (res.code === 200) {
    const data = { ...res.data }
    // parentId转字符串给TreeSelect使用
    if (data.parentId) {
      data.parentId = String(data.parentId)
    }
    Object.assign(formData, data)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    requestId.value = getRequestId();

    // 转换parentId：顶级部门(0/"0"/undefined)转为null
    const submitParentId = convertParentId(formData.parentId)

    if (isEdit.value) {
      const submitData: SysDepartmentUpdate = {
        ...formData,
        id: formData.id!,
        parentId: submitParentId,
      }
      const res = await updateDepartment(submitData, requestId.value)
      if (res.code === 200) {
        message.success({ content: '部门更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData: SysDepartmentCreate = { ...formData }
      delete (submitData as any).id
      submitData.parentId = submitParentId
      const res = await createDepartment(submitData, requestId.value)
      if (res.code === 200) {
        message.success({ content: '部门创建成功', class: 'custom-message' })
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

const handleDeleteOne = (record: SysDepartmentVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除部门「${record.name}」，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteDepartment(record.id!)
      if (res.code === 200) {
        message.success('已彻底删除')
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: SysDepartmentVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  requestId.value = getRequestId();
  const res = await updateDepartment({ ...record, status: newStatus } as SysDepartmentUpdate, requestId.value)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '部门已启用' : '部门已禁用')
  }
}

onMounted(() => {
  loadData()
  loadButtonPermissions()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.text-code {
  font-family: 'Monaco', 'Menlo', monospace;
  background: #f1f5f9;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 13px;
  color: #475569;
}

:global(html.dark) .text-code {
  background: #334155;
  color: #e2e8f0;
}

.action-btn.add {
  color: #10b981;
}
</style>