<template>
  <div class="syspost-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">模版标题</span>
            <a-input 
              v-model:value="queryParams.title" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><TeamOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">模版类型</span>
            <a-select 
              v-model:value="queryParams.typeId" 
              placeholder="全部类型" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option v-for="item in typeList" :key="normalizeId(item.id)" :value="normalizeId(item.id)">
                {{ item.title }}
              </a-select-option>
            </a-select>
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
            <div class="tab active">全部模版 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增模版
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
              <!-- 模版标题列 -->
              <template v-if="column.key === 'title'">
                <div class="user-profile">
                  <div class="avatar-box" :style="{ background: getAvatarColor(record.id) }">
                    {{ record.title?.charAt(0).toUpperCase() }}
                  </div>
                  <div class="user-info">
                    <span class="user-name">{{ record.title }}</span>
                  </div>
                </div>
              </template>
              
              <!-- 模版类型列 -->
              <template v-else-if="column.key === 'typeId'">
                <span>{{ getTypeName(record.typeId) }}</span>
              </template>

              <!-- 配置列 -->
              <template v-else-if="column.key === 'configId'">
                <span>{{ getConfigName(record.configId) }}</span>
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
            <h2>{{ isEdit ? '编辑消息模版' : '创建新模版' }}</h2>
            <p>请填写以下必填信息以完成模版设置</p>
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
                <template #label><span class="form-label">模版标题</span></template>
                <a-input v-model:value="formData.title" placeholder="如：验证码模版" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="code">
                <template #label><span class="form-label">模版代码</span></template>
                <a-input v-model:value="formData.code" placeholder="如：verify_code" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="typeId">
                <template #label><span class="form-label">模版类型</span></template>
                <a-select v-model:value="formData.typeId" placeholder="请选择模版类型" allow-clear>
                  <a-select-option v-for="item in typeList" :key="normalizeId(item.id)" :value="normalizeId(item.id)">
                    {{ item.title }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="status">
                <template #label><span class="form-label">状态</span></template>
                <StatusCardSelect 
                  v-model:value="formData.status"
                  :options="statusOptions"
                />
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
              <a-form-item name="content">
                <template #label><span class="form-label">模版内容</span></template>
                <a-textarea v-model:value="formData.content" placeholder="请输入模版内容" :rows="5" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="description">
                <template #label><span class="form-label">描述</span></template>
                <a-textarea v-model:value="formData.description" placeholder="请输入描述信息" :rows="3" />
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
  EditOutlined, TeamOutlined, KeyOutlined, CheckCircleFilled, 
  MinusCircleFilled, LoadingOutlined, PlusCircleOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { MessageTemplateVo, MessageTemplateQuery, MessageTemplateCreate, MessageTemplateUpdate } from '@/api/message/messagetemplate.ts'
import { getMessageTemplatePage, createMessageTemplate, updateMessageTemplate, deleteMessageTemplate, batchDeleteMessageTemplate, getMessageTemplateById, getMessageTemplateSelectAll } from '@/api/message/messagetemplate.ts'
import { getMessageTypeSelectAll } from '@/api/message/messagetype.ts'
import { getMessageConfigSelectAll } from '@/api/message/messageconfig.ts'
import { getRequestId } from "@/utils/idUtils.ts";
import StatusCardSelect from '@/components/StatusCardSelect/index.vue'
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

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('admin:message:template:add')
  canUpdate.value = hasButtonPermission('admin:message:template:update')
  canDelete.value = hasButtonPermission('admin:message:template:delete')
}

// 类型列表
const typeList = ref<IdTitleVo[]>([])
const loadTypeList = async () => {
  try {
    const res = await getMessageTypeSelectAll()
    if (res.code === 200) {
      typeList.value = res.data || []
    }
  } catch (error) {
    console.error('加载类型列表失败:', error)
  }
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
const getTypeName = (typeId: unknown) => {
  const normalizedTypeId = normalizeId(typeId)
  if (!normalizedTypeId) return '-'
  const item = typeList.value.find(t => normalizeId(t.id) === normalizedTypeId)
  return item?.title || '-'
}

const getConfigName = (configId: unknown) => {
  const normalizedConfigId = normalizeId(configId)
  if (!normalizedConfigId) return '-'
  const item = configList.value.find(t => normalizeId(t.id) === normalizedConfigId)
  return item?.title || '-'
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
  { title: '模版标题', dataIndex: 'title', key: 'title', width: 180 },
  { title: '模版代码', dataIndex: 'code', key: 'code', width: 130 },
  { title: '模版类型', dataIndex: 'typeId', key: 'typeId', width: 120 },
  { title: '配置', dataIndex: 'configId', key: 'configId', width: 120 },
  { title: '描述', dataIndex: 'description', key: 'description', ellipsis: true },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const requestId = ref<string>('')
const dataSource = ref<MessageTemplateVo[]>([])
const selectedRowKeys = ref<number[]>([])

type MessageTemplateQueryForm = Omit<MessageTemplateQuery, 'typeId'> & { typeId?: string }
type MessageTemplateFormData = Omit<MessageTemplateCreate, 'typeId'> & { id?: number; typeId?: string }

const queryParams = reactive<MessageTemplateQueryForm>({
  page: 1,
  pageSize: 10,
  title: '',
  typeId: undefined,
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
const formData = reactive<MessageTemplateFormData>({
  id: undefined,
  title: '',
  code: '',
  typeId: undefined,
  configId: undefined,
  content: '',
  description: '',
  status: 0,
})

const rules = {
  title: [{ required: true, message: '请输入模版标题', trigger: 'blur' }],
  code: [{ required: true, message: '请输入模版代码', trigger: 'blur' }],
  typeId: [{ required: true, message: '请选择模版类型', trigger: 'change' }],
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
      typeId: normalizeId(queryParams.typeId),
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getMessageTemplatePage(params)
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
  queryParams.typeId = undefined
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
    id: undefined, title: '', code: '', typeId: undefined, configId: undefined, content: '', description: '', status: 0,
  })
  modalVisible.value = true
}

const handleEdit = async (record: MessageTemplateVo) => {
  isEdit.value = true
  try {
    const res = await getMessageTemplateById(record.id!)
    if (res.code === 200) {
      Object.assign(formData, {
        ...res.data,
        typeId: normalizeId(res.data?.typeId),
        configId: normalizeId(res.data?.configId),
      })
    }
  } catch (error) {
    console.error('获取模版详情失败:', error)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    requestId.value = getRequestId();

    if (isEdit.value) {
      const submitData: MessageTemplateUpdate = {
        ...(formData as any),
        typeId: normalizeId(formData.typeId) as any,
        configId: normalizeId(formData.configId) as any,
      }
      const res = await updateMessageTemplate(submitData, requestId.value)
      if (res.code === 200) {
        message.success({ content: '模版更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData: MessageTemplateCreate = {
        ...(formData as any),
        typeId: normalizeId(formData.typeId) as any,
        configId: normalizeId(formData.configId) as any,
      }
      delete (submitData as any).id
      const res = await createMessageTemplate(submitData, requestId.value)
      if (res.code === 200) {
        message.success({ content: '模版创建成功', class: 'custom-message' })
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

const handleDeleteOne = (record: MessageTemplateVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除模版「${record.title}」，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteMessageTemplate(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个模版，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteMessageTemplate(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: MessageTemplateVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  requestId.value = getRequestId();
  const res = await updateMessageTemplate({ ...record, status: newStatus } as MessageTemplateUpdate, requestId.value)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '模版已启用' : '模版已禁用')
  }
}

onMounted(() => {
  loadData()
  loadButtonPermissions()
  loadTypeList()
  loadConfigList()
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

:global(html.dark) .text-code {
  background: #334155;
  color: #e2e8f0;
}
</style>
