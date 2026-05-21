<template>
  <div class="syspost-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">验证码</span>
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
            <span class="filter-label">发送目标</span>
            <a-input 
              v-model:value="queryParams.target" 
              placeholder="手机号/邮箱..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><UserOutlined class="text-secondary" /></template>
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
              <a-select-option v-for="item in verificationStatusOptions" :key="item.value" :value="item.value">
                <div class="status-option">
                  <span :class="'dot dot-' + item.type"></span>
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
            <div class="tab active">全部验证码 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleSend()">
            <SendOutlined /> 发送验证码
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
            :scroll="{ x: 1000 }"
            @change="handleTableChange"
            class="elegant-table"
          >
            <template #bodyCell="{ column, record }">
              <!-- 验证码列 -->
              <template v-if="column.key === 'code'">
                <div class="user-profile">
                  <div class="avatar-box" :style="{ background: getAvatarColor(record.id) }">
                    {{ record.code?.charAt(0).toUpperCase() }}
                  </div>
                  <div class="user-info">
                    <span class="user-name font-mono">{{ record.code }}</span>
                  </div>
                </div>
              </template>

              <!-- 状态列 -->
              <template v-else-if="column.key === 'status'">
                <a-tag :color="getStatusInfo(record.status).color">
                  {{ getStatusInfo(record.status).label }}
                </a-tag>
              </template>

              <!-- 过期时间列 -->
              <template v-else-if="column.key === 'expireTime'">
                <span>{{ formatExpireTime(record.expireTime) }}</span>
              </template>

              <!-- 操作列 -->
              <template v-else-if="column.key === 'action'">
                <div class="action-group">
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

    <!-- 发送验证码弹窗 -->
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
          <div class="modal-icon add-icon">
            <SendOutlined />
          </div>
          <div class="modal-titles">
            <h2>发送验证码</h2>
            <p>请填写以下信息以发送验证码</p>
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
              <a-form-item name="target">
                <template #label><span class="form-label">发送目标</span></template>
                <a-input v-model:value="formData.target" placeholder="手机号或邮箱" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="configId">
                <template #label><span class="form-label">配置</span></template>
                <a-select v-model:value="formData.configId" placeholder="请选择配置" allow-clear>
                  <a-select-option v-for="item in configList" :key="normalizeId(item.id)" :value="normalizeId(item.id)">
                    {{ item.title }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="businessData">
                <template #label><span class="form-label">业务数据</span></template>
                <a-textarea v-model:value="formData.businessData" placeholder="请输入业务数据(JSON)" :rows="3" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="code">
                <template #label><span class="form-label">验证码</span></template>
                <a-input v-model:value="formData.code" placeholder="6位数字" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="expireTime">
                <template #label><span class="form-label">过期时间(毫秒)</span></template>
                <a-input-number v-model:value="formData.expireTime" placeholder="如: 300000(5分钟)" :min="0" style="width: 100%" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handleCancel">取消</a-button>
          <a-button type="primary" class="gradient-btn-lg" @click="handleSubmit" :loading="submitLoading">
            确认发送
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
  EditOutlined, UserOutlined, KeyOutlined, SendOutlined,
  LoadingOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { MessageVerificationCodeVo, MessageVerificationCodeQuery, MessageVerificationCodeCreate } from '@/api/message/messagerecificationcode.ts'
import { getMessageVerificationCodePage, sendMessageVerificationCode, deleteMessageVerificationCode, batchDeleteMessageVerificationCode, getMessageVerificationCodeById } from '@/api/message/messagerecificationcode.ts'
import { getMessageConfigSelectAll } from '@/api/message/messageconfig.ts'
import { getRequestId } from "@/utils/idUtils.ts";
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'

interface IdTitleVo {
  id?: number | string
  title?: string
}

const appStore = useAppStore()
const permissionStore = usePermissionStore()

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 验证码状态选项
const verificationStatusOptions = [
  { value: 1, label: '有效', type: 'success', color: 'success' },
  { value: 2, label: '已使用', type: 'warning', color: 'orange' },
  { value: 3, label: '已过期', type: 'error', color: 'error' }
]

const getStatusInfo = (status: number | undefined) => {
  return verificationStatusOptions.find(opt => opt.value === status) || { label: '未知', color: 'default' }
}

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('admin:message:verificationCode:add')
  canUpdate.value = hasButtonPermission('admin:message:verificationCode:update')
  canDelete.value = hasButtonPermission('admin:message:verificationCode:delete')
}

// 配置列表
const configList = ref<IdTitleVo[]>([])
const loadConfigList = async () => {
  try {
    const res = await getMessageConfigSelectAll()
    if (res.code === 200) {
      configList.value = res.data || []
    }
  } catch (error) {
    console.error('加载配置列表失败:', error)
  }
}

const normalizeId = (value: unknown) => {
  if (value === null || value === undefined || value === '') return undefined
  return String(value)
}

const formatExpireTime = (expireTime: number | undefined) => {
  if (!expireTime) return '-'
  const now = Date.now()
  const remaining = expireTime - now
  if (remaining <= 0) return '已过期'
  const minutes = Math.floor(remaining / 60000)
  const seconds = Math.floor((remaining % 60000) / 1000)
  return `${minutes}分${seconds}秒`
}

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
  { title: '验证码', dataIndex: 'code', key: 'code', width: 150 },
  { title: '发送目标', dataIndex: 'target', key: 'target', width: 150 },
  { title: '配置', dataIndex: 'configId', key: 'configId', width: 120 },
  { title: '业务数据', dataIndex: 'businessData', key: 'businessData', ellipsis: true },
  { title: '过期时间', dataIndex: 'expireTime', key: 'expireTime', width: 120 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 80, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const requestId = ref<string>('')
const dataSource = ref<MessageVerificationCodeVo[]>([])
const selectedRowKeys = ref<number[]>([])

type MessageVerificationCodeQueryForm = Omit<MessageVerificationCodeQuery, 'configId'> & { configId?: string }
type MessageVerificationCodeFormData = Omit<MessageVerificationCodeCreate, 'configId'> & { configId?: string }

const queryParams = reactive<MessageVerificationCodeQueryForm>({
  page: 1,
  pageSize: 10,
  code: '',
  target: '',
  configId: undefined,
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
const formData = reactive<MessageVerificationCodeFormData>({
  target: '',
  configId: undefined,
  code: '',
  businessData: '',
  expireTime: 300000,
  status: 0,
})

const rules = {
  target: [{ required: true, message: '请输入发送目标', trigger: 'blur' }],
  code: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
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
      configId: normalizeId(queryParams.configId),
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getMessageVerificationCodePage(params)
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
  queryParams.code = ''
  queryParams.target = ''
  queryParams.configId = undefined
  queryParams.status = undefined
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleSend = () => {
  Object.assign(formData, {
    target: '', configId: undefined, code: '', businessData: '', expireTime: 300000, status: 0,
  })
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    requestId.value = getRequestId();

    const submitData: MessageVerificationCodeCreate = {
      ...(formData as any),
      configId: normalizeId(formData.configId) as any,
    }
    const res = await sendMessageVerificationCode(submitData, requestId.value)
    if (res.code === 200) {
      message.success({ content: '验证码发送成功', class: 'custom-message' })
      modalVisible.value = false
      loadData()
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

const handleDeleteOne = (record: MessageVerificationCodeVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除该验证码，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteMessageVerificationCode(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个验证码，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteMessageVerificationCode(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

onMounted(() => {
  loadData()
  loadButtonPermissions()
  loadConfigList()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.font-mono {
  font-family: 'Monaco', 'Menlo', monospace;
  letter-spacing: 2px;
}

:global(html.dark) .font-mono {
  color: #e2e8f0;
}
</style>