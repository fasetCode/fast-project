<template>
  <div class="user-config-container">
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
            <span class="filter-label">用户ID</span>
            <UserSelect 
              v-model:value="queryParams.userId" 
              placeholder="输入用户搜索..." 
            />
          </div>
          <div class="filter-item">
            <span class="filter-label">名单类型</span>
            <a-select 
              v-model:value="queryParams.listType" 
              placeholder="选择类型" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option value="BLACK">黑名单</a-select-option>
              <a-select-option value="WHITE">白名单</a-select-option>
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
            <div class="tab active">用户黑白名单配置 <span class="badge">{{ pagination.total }}</span></div>
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
              <!-- 用户ID列 -->
              <template v-if="column.key === 'userId'">
                <div class="user-cell">
                  <div class="user-icon">
                    <UserOutlined />
                  </div>
                  <span class="user-id">{{ record.userId }}</span>
                </div>
              </template>

              <!-- 名单类型列 -->
              <template v-else-if="column.key === 'listType'">
                <a-tag size="small" :color="record.listType === 'BLACK' ? 'red' : 'green'">
                  {{ record.listType === 'BLACK' ? '黑名单' : '白名单' }}
                </a-tag>
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
            <h2>{{ isEdit ? '编辑用户黑白名单配置' : '创建用户黑白名单配置' }}</h2>
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
            <a-col :span="12">
              <a-form-item name="appCode">
                <template #label><span class="form-label">应用代码</span></template>
                <a-input v-model:value="formData.appCode" placeholder="请输入应用代码">
                  <template #prefix><AppstoreOutlined /></template>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="listType">
                <template #label><span class="form-label">名单类型</span></template>
                <a-select v-model:value="formData.listType" placeholder="选择类型">
                  <a-select-option value="BLACK">黑名单</a-select-option>
                  <a-select-option value="WHITE">白名单</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="userId">
                <template #label><span class="form-label">选择用户</span></template>
                <UserSelect v-model:value="formData.userId" placeholder="搜索并选择用户" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="limitMsg">
                <template #label><span class="form-label">限制提示信息</span></template>
                <a-input v-model:value="formData.limitMsg" placeholder="触发黑名单时的提示语" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="remark">
                <template #label><span class="form-label">备注说明</span></template>
                <a-textarea v-model:value="formData.remark" placeholder="请输入备注" :rows="2" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
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
import UserSelect from '@/components/UserSelect/index.vue'
import {
  SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, 
  EditOutlined, UserOutlined, CheckCircleFilled, 
  MinusCircleFilled, LoadingOutlined, PlusCircleOutlined,
  AppstoreOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { UserBlackWhiteListVo, UserBlackWhiteListQuery, UserBlackWhiteListCreate, UserBlackWhiteListUpdate } from '@/api/ratelimit/userBwConfig'
import { 
  getUserBwConfigPage, createUserBwConfig, updateUserBwConfig, 
  deleteUserBwConfig, batchDeleteUserBwConfig, getUserBwConfigById 
} from '@/api/ratelimit/userBwConfig'
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
  canAdd.value = hasButtonPermission('admin:ratelimit:user-bw-config:add')
  canUpdate.value = hasButtonPermission('admin:ratelimit:user-bw-config:update')
  canDelete.value = hasButtonPermission('admin:ratelimit:user-bw-config:delete')
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
  { title: '应用代码', dataIndex: 'appCode', key: 'appCode', width: 150 },
  { title: '用户ID', dataIndex: 'userId', key: 'userId', width: 150 },
  { title: '名单类型', dataIndex: 'listType', key: 'listType', width: 100 },
  { title: '提示信息', dataIndex: 'limitMsg', key: 'limitMsg', width: 200 },
  { title: '备注', dataIndex: 'remark', key: 'remark', width: 150 },
  { title: '状态', dataIndex: 'enabled', key: 'enabled', width: 120 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<UserBlackWhiteListVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<UserBlackWhiteListQuery>({
  page: 0,
  pageSize: 10,
  appCode: '',
  userId: undefined,
  listType: undefined,
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
type FormDataType = UserBlackWhiteListCreate & { id?: number }
const formData = reactive<FormDataType>({
  id: undefined,
  appCode: '',
  userId: null,
  listType: 'BLACK',
  limitMsg: '',
  enabled: true,
  remark: ''
})

const rules = {
  appCode: [{ required: false, message: '请输入应用代码', trigger: 'blur' }],
  userId: [{ required: true, message: '请选择用户', trigger: 'change' }],
  listType: [{ required: true, message: '请选择名单类型', trigger: 'change' }],
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      userId: queryParams.userId ? Number(queryParams.userId) : undefined,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getUserBwConfigPage(params)
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
  queryParams.userId = undefined
  queryParams.listType = undefined
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
    id: undefined, appCode: '', userId: null, listType: 'BLACK', 
    limitMsg: '', enabled: true, remark: ''
  })
  modalVisible.value = true
}

const handleEdit = async (record: UserBlackWhiteListVo) => {
  isEdit.value = true
  requestId.value = getRequestId()
  try {
    const res = await getUserBwConfigById(record.id!)
    if (res.code === 200) {
      const data = res.data
      Object.assign(formData, {
        ...data,
      })
    }
  } catch (error) {
    console.error('获取用户黑白名单配置详情失败:', error)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true

    const submitData: any = {
      ...formData,
      userId: formData.userId ? Number(formData.userId) : null
    }

    if (isEdit.value) {
      const res = await updateUserBwConfig(submitData as UserBlackWhiteListUpdate, requestId.value)
      if (res.code === 200) {
        message.success({ content: '用户黑白名单配置更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      delete submitData.id
      const res = await createUserBwConfig(submitData as UserBlackWhiteListCreate, requestId.value)
      if (res.code === 200) {
        message.success({ content: '用户黑白名单配置创建成功', class: 'custom-message' })
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

const handleDeleteOne = (record: UserBlackWhiteListVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除该用户黑白名单配置，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteUserBwConfig(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个用户黑白名单配置，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteUserBwConfig(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: UserBlackWhiteListVo) => {
  const newEnabled = !record.enabled
  requestId.value = getRequestId()
  const res = await updateUserBwConfig({ ...record, enabled: newEnabled } as UserBlackWhiteListUpdate, requestId.value)
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

.user-cell {
  display: flex;
  align-items: center;
  gap: 8px;
}

.user-icon {
  width: 28px;
  height: 28px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  color: white;
  background: linear-gradient(135deg, #10b981 0%, #34d399 100%);
}

.user-id {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 13px;
  color: #475569;
}

</style>