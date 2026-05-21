<template>
  <div class="api-config-container">
    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">应用代码</span>
            <a-input 
              v-model:value="queryParams.appCode" 
              placeholder="输入应用代码..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><AppstoreOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">API路径</span>
            <a-input 
              v-model:value="queryParams.apiPath" 
              placeholder="输入API路径..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><ApiOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">HTTP方法</span>
            <a-select 
              v-model:value="queryParams.httpMethod" 
              placeholder="选择方法" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option value="GET">GET</a-select-option>
              <a-select-option value="POST">POST</a-select-option>
              <a-select-option value="PUT">PUT</a-select-option>
              <a-select-option value="DELETE">DELETE</a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">限流维度</span>
            <a-select 
              v-model:value="queryParams.limitDimension" 
              placeholder="选择维度" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option value="IP">IP地址</a-select-option>
              <a-select-option value="USER">用户</a-select-option>
              <a-select-option value="GLOBAL">全局</a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">状态</span>
            <a-select 
              v-model:value="queryParams.enabled" 
              placeholder="全部状态" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option :value="true">
                <div class="status-option">
                  <span class="dot dot-success"></span> 启用
                </div>
              </a-select-option>
              <a-select-option :value="false">
                <div class="status-option">
                  <span class="dot dot-error"></span> 禁用
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
            <div class="tab active">API限流配置 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增配置
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
              <!-- API路径列 -->
              <template v-if="column.key === 'apiPath'">
                <div class="api-path-cell">
                  <a-tag :color="getMethodColor(record.httpMethod)" size="small">{{ record.httpMethod }}</a-tag>
                  <span class="api-path">{{ record.apiPath }}</span>
                </div>
              </template>

              <!-- 限流维度列 -->
              <template v-else-if="column.key === 'limitDimension'">
                <a-tag :color="getDimensionColor(record.limitDimension)">{{ getDimensionName(record.limitDimension) }}</a-tag>
              </template>

              <!-- 限流策略列 -->
              <template v-else-if="column.key === 'strategy'">
                <div class="strategy-info">
                  <span class="strategy-value">{{ record.maxRequests }}</span>
                  <span class="strategy-unit">次 / {{ record.timeWindow }}秒</span>
                </div>
              </template>

              <!-- 状态列 -->
              <template v-else-if="column.key === 'enabled'">
                <div class="status-toggle" :class="{ 'is-active': record.enabled }">
                  <a-switch
                    :checked="record.enabled"
                    size="small"
                    @change="handleStatusChange(record)"
                  />
                  <span class="status-text" :class="record.enabled ? 'text-success' : 'text-error'">
                    {{ record.enabled ? '启用' : '禁用' }}
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
            <h2>{{ isEdit ? '编辑API限流配置' : '创建API限流配置' }}</h2>
            <p>请填写以下必填信息以完成配置</p>
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
              <a-form-item name="appCode">
                <template #label><span class="form-label">应用代码</span></template>
                <a-input v-model:value="formData.appCode" placeholder="请输入应用代码">
                  <template #prefix><AppstoreOutlined /></template>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="apiPath">
                <template #label><span class="form-label">API路径</span></template>
                <a-input v-model:value="formData.apiPath" placeholder="如：/api/user/list">
                  <template #prefix><ApiOutlined /></template>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="httpMethod">
                <template #label><span class="form-label">HTTP方法</span></template>
                <a-select v-model:value="formData.httpMethod" placeholder="选择方法">
                  <a-select-option value="GET">GET</a-select-option>
                  <a-select-option value="POST">POST</a-select-option>
                  <a-select-option value="PUT">PUT</a-select-option>
                  <a-select-option value="DELETE">DELETE</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="limitDimension">
                <template #label><span class="form-label">限流维度</span></template>
                <a-select v-model:value="formData.limitDimension" placeholder="选择维度">
                  <a-select-option value="IP">IP地址</a-select-option>
                  <a-select-option value="USER">用户</a-select-option>
                  <a-select-option value="GLOBAL">全局</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="maxRequests">
                <template #label><span class="form-label">最大请求次数</span></template>
                <a-input-number v-model:value="formData.maxRequests" :min="1" style="width: 100%" placeholder="如：100" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="timeWindow">
                <template #label><span class="form-label">时间窗口(秒)</span></template>
                <a-input-number v-model:value="formData.timeWindow" :min="1" style="width: 100%" placeholder="如：60" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="enabled">
                <template #label><span class="form-label">配置状态</span></template>
                <a-switch v-model:checked="formData.enabled" />
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
import { ref, reactive, onMounted, h } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, 
  EditOutlined, ApiOutlined, CheckCircleFilled, 
  MinusCircleFilled, LoadingOutlined, PlusCircleOutlined,
  AppstoreOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { ApiRateLimitConfigVo, ApiRateLimitConfigQuery, ApiRateLimitConfigCreate, ApiRateLimitConfigUpdate } from '@/api/ratelimit/apiConfig'
import { 
  getApiRateLimitConfigPage, createApiRateLimitConfig, updateApiRateLimitConfig, 
  deleteApiRateLimitConfig, batchDeleteApiRateLimitConfig, getApiRateLimitConfigById 
} from '@/api/ratelimit/apiConfig'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getRequestId } from '@/utils/idUtils.ts'

const permissionStore = usePermissionStore()
const requestId = ref<string>('')

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('admin:ratelimit:api-config:add')
  canUpdate.value = hasButtonPermission('admin:ratelimit:api-config:update')
  canDelete.value = hasButtonPermission('admin:ratelimit:api-config:delete')
}

// 自定义表格加载指示器
const customLoadingIndicator = h(LoadingOutlined, {
  style: {
    fontSize: '32px',
    color: '#6366f1',
  },
  spin: true,
})

// 获取HTTP方法颜色
const getMethodColor = (method: string | undefined) => {
  const colors: Record<string, string> = {
    'GET': 'blue',
    'POST': 'green',
    'PUT': 'orange',
    'DELETE': 'red',
  }
  return colors[method || ''] || 'default'
}

// 获取限流维度颜色
const getDimensionColor = (dimension: string | undefined) => {
  if (dimension === 'IP') return 'blue'
  if (dimension === 'USER') return 'purple'
  if (dimension === 'GLOBAL') return 'orange'
  return 'default'
}

// 获取限流维度名称
const getDimensionName = (dimension: string | undefined) => {
  if (dimension === 'IP') return 'IP地址'
  if (dimension === 'USER') return '用户'
  if (dimension === 'GLOBAL') return '全局'
  return '未知'
}

// 表格列定义
const columns = [
  { title: '应用代码', dataIndex: 'appCode', key: 'appCode', width: 120 },
  { title: 'API路径', dataIndex: 'apiPath', key: 'apiPath', width: 230 },
  { title: '限流维度', dataIndex: 'limitDimension', key: 'limitDimension', width: 120 },
  { title: '限流策略', key: 'strategy', width: 150 },
  { title: '状态', dataIndex: 'enabled', key: 'enabled', width: 120 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<ApiRateLimitConfigVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<ApiRateLimitConfigQuery>({
  page: 0,
  pageSize: 10,
  appCode: '',
  apiPath: '',
  httpMethod: undefined,
  limitDimension: undefined,
  enabled: undefined,
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
type FormDataType = ApiRateLimitConfigCreate & { id?: number }
const formData = reactive<FormDataType>({
  id: undefined,
  appCode: '',
  apiPath: '',
  httpMethod: 'GET',
  maxRequests: 100,
  timeWindow: 60,
  limitDimension: 'IP',
  enabled: true,
})

const rules = {
  appCode: [{ required: true, message: '请输入应用代码', trigger: 'blur' }],
  apiPath: [{ required: true, message: '请输入API路径', trigger: 'blur' }],
  httpMethod: [{ required: true, message: '请选择HTTP方法', trigger: 'change' }],
  limitDimension: [{ required: true, message: '请选择限流维度', trigger: 'change' }],
  maxRequests: [{ required: true, message: '请输入最大请求次数', trigger: 'blur' }],
  timeWindow: [{ required: true, message: '请输入时间窗口', trigger: 'blur' }],
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getApiRateLimitConfigPage(params)
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
  queryParams.appCode = ''
  queryParams.apiPath = ''
  queryParams.httpMethod = undefined
  queryParams.limitDimension = undefined
  queryParams.enabled = undefined
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleAdd = () => {
  isEdit.value = false
  requestId.value = getRequestId()
  Object.assign(formData, {
    id: undefined, appCode: '', apiPath: '', httpMethod: 'GET', maxRequests: 100, 
    timeWindow: 60, limitDimension: 'IP', enabled: true,
  })
  modalVisible.value = true
}

const handleEdit = async (record: ApiRateLimitConfigVo) => {
  isEdit.value = true
  requestId.value = getRequestId()
  try {
    const res = await getApiRateLimitConfigById(record.id!)
    if (res.code === 200) {
      Object.assign(formData, res.data)
    }
  } catch (error) {
    console.error('获取API限流配置详情失败:', error)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true

    if (isEdit.value) {
      const res = await updateApiRateLimitConfig(formData as ApiRateLimitConfigUpdate, requestId.value)
      if (res.code === 200) {
        message.success({ content: 'API限流配置更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData: ApiRateLimitConfigCreate = { ...(formData as any) }
      delete (submitData as any).id
      const res = await createApiRateLimitConfig(submitData, requestId.value)
      if (res.code === 200) {
        message.success({ content: 'API限流配置创建成功', class: 'custom-message' })
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

const handleDeleteOne = (record: ApiRateLimitConfigVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除该API限流配置，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteApiRateLimitConfig(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个API限流配置，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteApiRateLimitConfig(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: ApiRateLimitConfigVo) => {
  const newEnabled = !record.enabled
  requestId.value = getRequestId()
  const res = await updateApiRateLimitConfig({ ...record, enabled: newEnabled } as ApiRateLimitConfigUpdate, requestId.value)
  if (res.code === 200) {
    record.enabled = newEnabled
    message.success(newEnabled ? '配置已启用' : '配置已禁用')
  }
}

onMounted(() => {
  loadData()
  loadButtonPermissions()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.api-path-cell {
  display: flex;
  align-items: center;
  gap: 8px;
}

.api-path {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 13px;
  color: #475569;
}

.strategy-info {
  display: flex;
  align-items: center;
  gap: 4px;
}

.strategy-value {
  font-weight: 600;
  color: #6366f1;
}

.strategy-unit {
  font-size: 12px;
  color: #94a3b8;
}
</style>
