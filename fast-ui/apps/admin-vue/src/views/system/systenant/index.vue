<template>
  <div class="systenant-container">
    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">租户名称</span>
            <a-input 
              v-model:value="queryParams.name" 
              placeholder="输入名称..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><ShopOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">联系人</span>
            <a-input 
              v-model:value="queryParams.contactName" 
              placeholder="输入联系人..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><UserOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">域名</span>
            <a-input 
              v-model:value="queryParams.domain" 
              placeholder="输入域名..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><GlobalOutlined class="text-secondary" /></template>
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
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">
                <div class="status-option">
                  <span :class="item.value === 0 ? 'dot dot-success' : 'dot dot-error'"></span>
                  {{ item.title }}
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
            <div class="tab active">租户列表 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增租户
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
            :scroll="{ x: 1200 }"
            @change="handleTableChange"
            class="elegant-table"
          >
            <template #bodyCell="{ column, record }">
              <!-- 租户名称列 -->
              <template v-if="column.key === 'name'">
                <div class="user-profile">
                  <div class="avatar-box" :style="{ background: getAvatarColor(record.id) }">
                    <ShopOutlined />
                  </div>
                  <div class="user-info">
                    <span class="user-name">{{ record.name }}</span>
                    <span class="user-id">ID: {{ record.id }}</span>
                  </div>
                </div>
              </template>
            
              <!-- 域名列 -->
              <template v-else-if="column.key === 'domain'">
                <span class="text-code">{{ record.domain || '-' }}</span>
              </template>

              <!-- 状态列 -->
              <template v-else-if="column.key === 'status'">
                <div class="status-toggle" :class="{ 'is-active': record.status === 0 }">
                  <a-switch
                    :checked="record.status === 0"
                    size="small"
                    @change="handleStatusChange(record)"
                  />
                  <span class="status-text" :class="record.status === 0 ? 'text-success' : 'text-error'">
                    {{ record.status === 0 ? '正常' : '停用' }}
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

    <!-- 弹窗 -->
    <a-modal
      v-model:open="modalVisible"
      :title="null"
      :footer="null"
      width="650px"
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
            <h2>{{ isEdit ? '编辑租户' : '创建新租户' }}</h2>
            <p>请填写以下必填信息以完成租户设置</p>
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
              <a-form-item name="name">
                <template #label><span class="form-label">租户名称</span></template>
                <a-input v-model:value="formData.name" placeholder="请输入租户名称"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="domain">
                <template #label><span class="form-label">租户域名</span></template>
                <a-input v-model:value="formData.domain" placeholder="如：tenant1.example.com" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="contactName">
                <template #label><span class="form-label">联系人</span></template>
                <a-input v-model:value="formData.contactName" placeholder="请输入联系人姓名" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="contactPhone">
                <template #label><span class="form-label">联系电话</span></template>
                <a-input v-model:value="formData.contactPhone" placeholder="请输入联系电话" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="adminId">
                <template #label><span class="form-label">租户管理员</span></template>
                <UserSelect v-model:value="formData.adminId" placeholder="输入账号搜索管理员..." />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="accountCount">
                <template #label><span class="form-label">账号额度</span></template>
                <a-input-number v-model:value="formData.accountCount" :min="1" style="width: 100%" placeholder="允许创建的最大用户数" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="expireTime">
                <template #label><span class="form-label">过期时间</span></template>
                <a-date-picker v-model:value="formData.expireTime" show-time value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="status">
                <template #label><span class="form-label">租户状态</span></template>
                <StatusCardSelect 
                  v-model:value="formData.status"
                  :options="statusCardOptions"
                />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="remark">
                <template #label><span class="form-label">备注</span></template>
                <a-textarea v-model:value="formData.remark" placeholder="请输入备注信息" :rows="3" />
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
  EditOutlined, ShopOutlined, UserOutlined, GlobalOutlined,
  CheckCircleFilled, MinusCircleFilled, LoadingOutlined, PlusCircleOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { SysTenantVo, SysTenantQuery, SysTenantCreate, SysTenantUpdate } from '@/api/system/systenant.ts'
import { getTenantPage, createTenant, updateTenant, deleteTenant, batchDeleteTenant, getTenantById } from '@/api/system/systenant.ts'
import { getRequestId } from "@/utils/idUtils.ts";
import { StatusCardSelect, UserSelect } from '@/components'
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'

const appStore = useAppStore()
const permissionStore = usePermissionStore()

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('admin:system:tenant:add')
  canUpdate.value = hasButtonPermission('admin:system:tenant:update')
  canDelete.value = hasButtonPermission('admin:system:tenant:delete')
}

const statusOptions = [
  { value: 0, title: '正常', icon: CheckCircleFilled, type: 'success' as const },
  { value: 1, title: '停用', icon: MinusCircleFilled, type: 'error' as const }
]

const statusCardOptions = computed(() => statusOptions)

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
  { title: '租户名称', dataIndex: 'name', key: 'name', width: 220, fixed: 'left' as const },
  { title: '域名', dataIndex: 'domain', key: 'domain', width: 200 },
  { title: '联系人', dataIndex: 'contactName', key: 'contactName', width: 120 },
  { title: '联系电话', dataIndex: 'contactPhone', key: 'contactPhone', width: 150 },
  { title: '账号额度', dataIndex: 'accountCount', key: 'accountCount', width: 100 },
  { title: '过期时间', dataIndex: 'expireTime', key: 'expireTime', width: 180 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const requestId = ref<string>('')
const dataSource = ref<SysTenantVo[]>([])
const selectedRowKeys = ref<number[]>([])

const normalizeLongId = (value: unknown): string | undefined => {
  if (value === null || value === undefined || value === '') {
    return undefined
  }
  return String(value)
}

const queryParams = reactive<SysTenantQuery>({
  page: 1,
  pageSize: 10,
  name: '',
  contactName: '',
  domain: '',
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
const formData = reactive<SysTenantUpdate>({
  id: 0,
  name: '',
  domain: '',
  contactName: '',
  contactPhone: '',
  adminId: undefined,
  accountCount: 100,
  expireTime: '',
  status: 0,
  remark: '',
})

const rules = {
  name: [{ required: true, message: '请输入租户名称', trigger: 'blur' }],
  domain: [{ required: true, message: '请输入租户域名', trigger: 'blur' }],
  contactName: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
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
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getTenantPage(params)
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
  queryParams.name = ''
  queryParams.contactName = ''
  queryParams.domain = ''
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
    id: 0, name: '', domain: '', contactName: '', contactPhone: '', adminId: undefined, accountCount: 100, expireTime: '', status: 0, remark: '',
  })
  modalVisible.value = true
}

const handleEdit = async (record: SysTenantVo) => {
  isEdit.value = true
  try {
    const res = await getTenantById(record.id!)
    if (res.code === 200) {
      Object.assign(formData, res.data)
      formData.adminId = normalizeLongId(res.data?.adminId)
    }
  } catch (error) {
    console.error('获取租户详情失败:', error)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    requestId.value = getRequestId();

    if (isEdit.value) {
      const res = await updateTenant(formData, requestId.value)
      if (res.code === 200) {
        message.success('租户更新成功')
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData = { ...formData }
      delete (submitData as any).id
      const res = await createTenant(submitData, requestId.value)
      if (res.code === 200) {
        message.success('租户创建成功')
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

const handleDeleteOne = (record: SysTenantVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除租户「${record.name}」，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteTenant(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个租户，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteTenant(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: SysTenantVo) => {
  const newStatus = record.status === 0 ? 1 : 0
  requestId.value = getRequestId();
  const res = await updateTenant({ ...record, status: newStatus } as SysTenantUpdate, requestId.value)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 0 ? '租户已启用' : '租户已停用')
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
</style>
