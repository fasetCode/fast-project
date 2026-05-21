<template>
  <div class="syspost-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">用户ID</span>
            <a-input 
              v-model:value="queryParams.userId" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><SearchOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">积分值</span>
            <a-input 
              v-model:value="queryParams.growthValue" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><SearchOutlined class="text-secondary" /></template>
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
              <a-select-option v-for="item in statusFilterOptions" :key="item.value" :value="item.value">
                <div class="status-option">
                  <span :class="item.value === 1 ? 'dot dot-success' : 'dot dot-error'"></span>
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
            <div class="tab active">用户积分账户 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增
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
            :loading="{ spinning: loading, tip: '数据加载中...' }"
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
            <!-- 状态列 -->
            <template v-if="column.key === 'status'">
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
            <h2>{{ isEdit ? '编辑信息' : '创建新数据' }}</h2>
            <p>请填写以下必填信息</p>
          </div>
        </div>

        <a-form
          ref="formRef"
          :model="formData"
          layout="vertical"
          class="glass-form"
        >
          <div class="form-section-title">基本信息</div>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item name="userId">
                <template #label><span class="form-label">用户ID</span></template>
                <a-input-number v-model:value="formData.userId" style="width: 100%" placeholder="请输入" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="growthValue">
                <template #label><span class="form-label">积分值</span></template>
                <a-input-number v-model:value="formData.growthValue" style="width: 100%" placeholder="请输入" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="status">
                <template #label><span class="form-label">状态</span></template>
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
import { ref, reactive, onMounted, computed } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, 
  EditOutlined, PlusCircleOutlined, CheckCircleFilled, MinusCircleFilled
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { UserIntegralAccountVo, UserIntegralAccountQuery, UserIntegralAccountCreate, UserIntegralAccountUpdate } from '@/api/userGrowth/userintegralaccount.ts'
import { getUserIntegralAccountPage, createUserIntegralAccount, updateUserIntegralAccount, deleteUserIntegralAccount, batchDeleteUserIntegralAccount, getUserIntegralAccountById } from '@/api/userGrowth/userintegralaccount.ts'
import { getRequestId } from "@/utils/idUtils.ts"
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'
import StatusCardSelect from '@/components/StatusCardSelect/index.vue'

const permissionStore = usePermissionStore()

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('usergrowth:userintegralaccount:add')
  canUpdate.value = hasButtonPermission('usergrowth:userintegralaccount:update')
  canDelete.value = hasButtonPermission('usergrowth:userintegralaccount:delete')
}

// 状态筛选选项（表格筛选用）
const statusFilterOptions = computed(() => {
  const statusData = getDictData('status') || []
  return statusData.map((item: any) => ({
    value: Number(item.value),
    label: item.label
  }))
})

// 从字典获取状态选项（表单用）
const statusOptions = computed(() => {
  const statusData = getDictData('status') || []
  return statusData.map((item: any) => ({
    value: Number(item.value),
    title: item.label,
    icon: item.value === '1' ? CheckCircleFilled : MinusCircleFilled,
    type: item.value === '1' ? 'success' as const : 'error' as const
  }))
})

// 表格列定义
const columns = [
  { title: '用户ID', dataIndex: 'userId', key: 'userId' },
  { title: '积分值', dataIndex: 'growthValue', key: 'growthValue' },
  { title: '状态', dataIndex: 'status', key: 'status' },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const requestId = ref<string>('')
const dataSource = ref<UserIntegralAccountVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<UserIntegralAccountQuery>({
  page: 1,
  pageSize: 10,
  userId: undefined,
  growthValue: undefined,
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
type FormData = UserIntegralAccountCreate & { id?: number }
const formData = reactive<FormData>({
  id: undefined,
  userId: undefined,
  growthValue: undefined,
  status: 1,
})

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getUserIntegralAccountPage(params)
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
  Object.assign(queryParams, {
  userId: undefined,
  growthValue: undefined,
  status: undefined,
  })
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleStatusChange = async (record: UserIntegralAccountVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  requestId.value = getRequestId();
  const res = await updateUserIntegralAccount({ ...record, status: newStatus } as UserIntegralAccountUpdate, requestId.value)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '状态已恢复正常' : '状态已被冻结')
  }
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined,
  userId: undefined,
  growthValue: undefined,
  status: 1,
  })
  modalVisible.value = true
}

const handleEdit = async (record: UserIntegralAccountVo) => {
  isEdit.value = true
  try {
    const res = await getUserIntegralAccountById(record.id!)
    if (res.code === 200) {
      Object.assign(formData, res.data)
    }
  } catch (error) {
    console.error('获取详情失败:', error)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    requestId.value = getRequestId()

    if (isEdit.value) {
      const res = await updateUserIntegralAccount(formData as UserIntegralAccountUpdate, requestId.value)
      if (res.code === 200) {
        message.success({ content: '更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData: UserIntegralAccountCreate = { ...(formData as any) }
      delete (submitData as any).id
      const res = await createUserIntegralAccount(submitData, requestId.value)
      if (res.code === 200) {
        message.success({ content: '创建成功', class: 'custom-message' })
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

const handleDeleteOne = (record: UserIntegralAccountVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除该条数据，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteUserIntegralAccount(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 条数据，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteUserIntegralAccount(selectedRowKeys.value)
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
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
