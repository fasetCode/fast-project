<template>
  <div class="fileconfig-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">配置类型</span>
            <a-select 
              v-model:value="queryParams.type" 
              placeholder="选择类型" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option v-for="item in getDictData('file_config_type')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
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
          <div class="filter-item">
            <span class="filter-label">描述</span>
            <a-input 
              v-model:value="queryParams.description" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><FileTextOutlined class="text-secondary" /></template>
            </a-input>
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
            <div class="tab active">全部文件配置 <span class="badge">{{ pagination.total }}</span></div>
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
            :scroll="{ x: 1100 }"
            @change="handleTableChange"
            class="elegant-table"
          >
            <template #bodyCell="{ column, record }">
              <!-- 存储路径列 -->
              <template v-if="column.key === 'storagePath'">
                <a-tooltip :title="record.storagePath">
                  <span class="text-code">{{ record.storagePath }}</span>
                </a-tooltip>
              </template>

              <!-- 访问域名列 -->
              <template v-else-if="column.key === 'accessDomain'">
                <span class="text-link">{{ record.accessDomain }}</span>
              </template>

              <!-- 类型列 -->
              <template v-else-if="column.key === 'type'">
                <a-tag :color="getTypeColor(record.type)">{{ getDictLabel('file_config_type', record.type) || record.type || '未知' }}</a-tag>
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

              <!-- 远程URL列 -->
              <template v-else-if="column.key === 'remoteUrl'">
                <a-tooltip :title="record.remoteUrl">
                  <span class="text-link">{{ record.remoteUrl || '-' }}</span>
                </a-tooltip>
              </template>

              <!-- 描述列 -->
              <template v-else-if="column.key === 'description'">
                <span class="text-secondary">{{ record.description || '-' }}</span>
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
            <h2>{{ isEdit ? '编辑文件配置' : '创建新文件配置' }}</h2>
            <p>请填写以下必填信息以完成文件存储配置设置</p>
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
              <a-form-item name="type">
                <template #label><span class="form-label">存储类型</span></template>
                <a-select v-model:value="formData.type" placeholder="选择存储类型" :disabled="isEdit">
                  <a-select-option v-for="item in getDictData('file_config_type')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="status">
                <template #label><span class="form-label">配置状态</span></template>
                <StatusCardSelect 
                  v-model:value="formData.status"
                  :options="statusOptions"
                />
              </a-form-item>
            </a-col>
            <a-col :span="24" v-if="formData.type === 'localFileStorageStrategy'">
              <a-form-item name="storagePath">
                <template #label><span class="form-label">存储路径</span></template>
                <a-input v-model:value="formData.storagePath" placeholder="如：/data/files 或 http://oss.example.com">
                  <template #prefix><FolderOutlined /></template>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="accessDomain">
                <template #label><span class="form-label">访问域名</span></template>
                <a-input v-model:value="formData.accessDomain" placeholder="如：https://files.example.com">
                  <template #prefix><GlobalOutlined /></template>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24" v-if="formData.type === 'remote'">
              <a-form-item name="remoteUrl">
                <template #label><span class="form-label">远程上传URL</span></template>
                <a-input v-model:value="formData.remoteUrl" placeholder="远程服务器上传接口地址">
                  <template #prefix><CloudUploadOutlined /></template>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24" v-if="formData.type === 'remote'">
              <a-form-item name="remoteToken">
                <template #label><span class="form-label">远程访问凭证</span></template>
                <a-input-password v-model:value="formData.remoteToken" placeholder="远程服务器访问凭证或Token">
                  <template #prefix><SafetyOutlined /></template>
                </a-input-password>
              </a-form-item>
            </a-col>
            <a-col :span="24" v-if="formData.type !== 'localFileStorageStrategy'">
              <a-form-item name="config">
                <template #label><span class="form-label">存储配置 (JSON)</span></template>
                <div class="editor-container">
                  <codemirror
                    v-model="formData.config"
                    placeholder="请输入JSON格式的配置..."
                    :style="{ height: '200px', width: '100%' }"
                    :autofocus="false"
                    :indent-with-tab="true"
                    :tab-size="2"
                    :extensions="extensions"
                  />
                </div>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="description">
                <template #label><span class="form-label">配置描述</span></template>
                <a-textarea v-model:value="formData.description" placeholder="请输入配置描述信息" :rows="3" />
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
  EditOutlined, FileTextOutlined, CheckCircleFilled, 
  MinusCircleFilled, LoadingOutlined, PlusCircleOutlined,
  FolderOutlined, GlobalOutlined, CloudUploadOutlined, SafetyOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { FileConfigVo, FileConfigQuery, FileConfigCreate, FileConfigUpdate } from '@/api/file/fileconfig.ts'
import { 
  getFileConfigPage, createFileConfig, updateFileConfig, 
  deleteFileConfig, batchDeleteFileConfig, getFileConfigById 
} from '@/api/file/fileconfig.ts'
import StatusCardSelect from '@/components/StatusCardSelect/index.vue'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'
import { Codemirror } from 'vue-codemirror'
import { json } from '@codemirror/lang-json'
import { oneDark } from '@codemirror/theme-one-dark'

const permissionStore = usePermissionStore()
const extensions = [json(), oneDark]

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('admin:file:config:add')
  canUpdate.value = hasButtonPermission('admin:file:config:update')
  canDelete.value = hasButtonPermission('admin:file:config:delete')
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

const getTypeColor = (type: string | undefined) => {
  if (type === 'local') return 'blue'
  if (type === 'remote') return 'purple'
  if (type === 'aliyun') return 'orange'
  if (type === 'tencent') return 'cyan'
  if (type === 'qiniu') return 'green'
  if (type === 'minio') return 'pink'
  return 'default'
}

// 表格列定义
const columns = [
  { title: '配置类型', dataIndex: 'type', key: 'type', width: 100 },
  { title: '存储路径', dataIndex: 'storagePath', key: 'storagePath', width: 200 },
  { title: '访问域名', dataIndex: 'accessDomain', key: 'accessDomain', width: 180 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '远程URL', dataIndex: 'remoteUrl', key: 'remoteUrl', width: 180 },
  { title: '描述', dataIndex: 'description', key: 'description', width: 150 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<FileConfigVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<FileConfigQuery>({
  page: 1,
  pageSize: 10,
  type: undefined,
  status: undefined,
  description: '',
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
type FileConfigFormData = FileConfigCreate & { id?: number }
const formData = reactive<FileConfigFormData>({
  id: undefined,
  storagePath: '',
  accessDomain: '',
  status: 1,
  type: 'localFileStorageStrategy',
  description: '',
  remoteUrl: '',
  remoteToken: '',
  config: '',
})

const rules = {
  type: [{ required: true, message: '请选择存储类型', trigger: 'change' }],
  storagePath: [{ required: true, message: '请输入存储路径', trigger: 'blur' }],
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getFileConfigPage(params)
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
  queryParams.type = undefined
  queryParams.status = undefined
  queryParams.description = ''
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined, storagePath: '', accessDomain: '', status: 1, 
    type: 'localFileStorageStrategy', description: '', remoteUrl: '', remoteToken: '', config: ''
  })
  modalVisible.value = true
}

const handleEdit = async (record: FileConfigVo) => {
  isEdit.value = true
  try {
    const res = await getFileConfigById(record.id!)
    if (res.code === 200) {
      Object.assign(formData, res.data)
    }
  } catch (error) {
    console.error('获取文件配置详情失败:', error)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true

    if (isEdit.value) {
      const res = await updateFileConfig(formData as FileConfigUpdate)
      if (res.code === 200) {
        message.success({ content: '文件配置更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData: FileConfigCreate = { ...(formData as any) }
      delete (submitData as any).id
      const res = await createFileConfig(submitData)
      if (res.code === 200) {
        message.success({ content: '文件配置创建成功', class: 'custom-message' })
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

const handleDeleteOne = (record: FileConfigVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除该文件配置，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteFileConfig(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个文件配置，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteFileConfig(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: FileConfigVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  const res = await updateFileConfig({ ...record, status: newStatus } as FileConfigUpdate)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '文件配置已启用' : '文件配置已禁用')
  }
}

onMounted(() => {
  loadData()
  loadButtonPermissions()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.text-link {
  color: #6366f1;
  font-size: 13px;
}

:global(html.dark) .text-link {
  color: #818cf8;
}

.editor-container {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  overflow: hidden;
}

:global(html.dark) .editor-container {
  border-color: #374151;
}
</style>
