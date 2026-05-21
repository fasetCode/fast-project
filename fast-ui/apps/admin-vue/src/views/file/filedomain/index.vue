<template>
  <div class="filedomain-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">所属配置</span>
            <a-select 
              v-model:value="queryParams.configId" 
              placeholder="选择配置" 
              class="elegant-select"
              :bordered="false"
              allow-clear
              :options="configOptions"
            />
          </div>
          <div class="filter-item">
            <span class="filter-label">域名</span>
            <a-input 
              v-model:value="queryParams.domain" 
              placeholder="输入域名搜索..." 
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
            <div class="tab active">全部域名 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增域名
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
            :scroll="{ x: 900 }"
            @change="handleTableChange"
            class="elegant-table"
          >
            <template #bodyCell="{ column, record }">
              <!-- 域名列 -->
              <template v-if="column.key === 'domain'">
                <div class="domain-cell">
                  <div class="domain-icon">
                    <GlobalOutlined />
                  </div>
                  <span class="text-link">{{ record.domain }}</span>
                </div>
              </template>

              <!-- 所属配置列 -->
              <template v-else-if="column.key === 'configId'">
                <a-tag color="blue">{{ getConfigName(record.configId) }}</a-tag>
              </template>

              <!-- 状态列 -->
              <template v-else-if="column.key === 'status'">
                <div class="status-toggle" :class="{ 'is-active': record.status === 1 }">
                  <a-switch
                    :checked="record.status === 1"
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
      width="560px"
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
            <h2>{{ isEdit ? '编辑文件域名' : '创建新域名' }}</h2>
            <p>请填写以下必填信息以完成域名配置</p>
          </div>
        </div>

        <a-form
          ref="formRef"
          :model="formData"
          layout="vertical"
          :rules="rules"
          class="glass-form"
        >
          <a-row :gutter="24">
            <a-col :span="24">
              <a-form-item name="configId">
                <template #label><span class="form-label">所属配置</span></template>
                <a-select 
                  v-model:value="formData.configId" 
                  placeholder="选择所属文件配置"
                  :options="configOptions"
                  show-search
                  :filter-option="filterConfigOption"
                />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="domain">
                <template #label><span class="form-label">域名</span></template>
                <a-input v-model:value="formData.domain" placeholder="如：https://files.example.com">
                  <template #prefix><GlobalOutlined /></template>
                </a-input>
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
import { ref, reactive, onMounted, h, computed } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, 
  EditOutlined, CheckCircleFilled, MinusCircleFilled, 
  LoadingOutlined, PlusCircleOutlined, GlobalOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { FileDomainVo, FileDomainQuery, FileDomainCreate, FileDomainUpdate } from '@/api/file/filedomain.ts'
import { 
  getFileDomainPage, createFileDomain, updateFileDomain, 
  deleteFileDomain, batchDeleteFileDomain, getFileDomainById 
} from '@/api/file/filedomain.ts'
import type { FileConfigVo } from '@/api/file/fileconfig.ts'
import { getFileConfigPage } from '@/api/file/fileconfig.ts'
import StatusCardSelect from '@/components/StatusCardSelect/index.vue'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'

const permissionStore = usePermissionStore()

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('admin:file:domain:add')
  canUpdate.value = hasButtonPermission('admin:file:domain:update')
  canDelete.value = hasButtonPermission('admin:file:domain:delete')
}

// 配置选项
const configOptions = ref<{ value: number; label: string }[]>([])
const loadConfigOptions = async () => {
  try {
    const res = await getFileConfigPage({ page: 0, pageSize: 1000 })
    if (res.code === 200 && res.data?.data) {
      configOptions.value = res.data.data.map((item: FileConfigVo) => ({
        value: item.id!,
        label: `${item.type === 'local' ? '本地' : '远程'} - ${item.description || item.accessDomain || '未命名'}`
      }))
    }
  } catch (error) {
    console.error('加载配置选项失败:', error)
  }
}

// 获取配置名称
const getConfigName = (configId?: number) => {
  if (!configId) return '-'
  const config = configOptions.value.find(item => item.value === configId)
  return config?.label || `配置 #${configId}`
}

// 配置选择过滤器
const filterConfigOption = (input: string, option: any) => {
  return option.label.toLowerCase().includes(input.toLowerCase())
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
  { title: '域名', dataIndex: 'domain', key: 'domain', width: 300 },
  { title: '所属配置', dataIndex: 'configId', key: 'configId', width: 200 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<FileDomainVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<FileDomainQuery>({
  page: 1,
  pageSize: 10,
  configId: undefined,
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
type FileDomainFormData = FileDomainCreate & { id?: number }
const formData = reactive<FileDomainFormData>({
  id: undefined,
  configId: undefined,
  domain: '',
  status: 1,
})

const rules = {
  configId: [{ required: true, message: '请选择所属配置', trigger: 'change' }],
  domain: [{ required: true, message: '请输入域名', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getFileDomainPage(params)
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
  queryParams.configId = undefined
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
    id: undefined, configId: undefined, domain: '', status: 1,
  })
  modalVisible.value = true
}

const handleEdit = async (record: FileDomainVo) => {
  isEdit.value = true
  try {
    const res = await getFileDomainById(record.id!)
    if (res.code === 200) {
      Object.assign(formData, res.data)
    }
  } catch (error) {
    console.error('获取文件域名详情失败:', error)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true

    if (isEdit.value) {
      const res = await updateFileDomain(formData as FileDomainUpdate)
      if (res.code === 200) {
        message.success({ content: '域名更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData: FileDomainCreate = { ...(formData as any) }
      delete (submitData as any).id
      const res = await createFileDomain(submitData)
      if (res.code === 200) {
        message.success({ content: '域名创建成功', class: 'custom-message' })
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

const handleDeleteOne = (record: FileDomainVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除域名「${record.domain}」，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteFileDomain(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个域名，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteFileDomain(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: FileDomainVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  const res = await updateFileDomain({ ...record, status: newStatus } as FileDomainUpdate)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '域名已启用' : '域名已禁用')
  }
}

onMounted(() => {
  loadData()
  loadConfigOptions()
  loadButtonPermissions()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.domain-cell {
  display: flex;
  align-items: center;
  gap: 12px;
}

.domain-icon {
  width: 36px;
  height: 36px;
  border-radius: 8px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 18px;
}

.text-link {
  color: #6366f1;
  font-size: 14px;
  font-weight: 500;
}

:global(html.dark) .text-link {
  color: #818cf8;
}
</style>
