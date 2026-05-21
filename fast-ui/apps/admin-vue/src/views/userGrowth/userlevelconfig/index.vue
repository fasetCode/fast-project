<template>
  <div class="syspost-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">标题</span>
            <a-input 
              v-model:value="queryParams.title" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><SearchOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">图标</span>
            <a-input 
              v-model:value="queryParams.icon" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><SearchOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">所需成长值</span>
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
            <div class="tab active">用户等级配置 <span class="badge">{{ pagination.total }}</span></div>
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
            <!-- 描述列 -->
            <template v-else-if="column.key === 'description'">
              <span :title="getPlainText(record.description)">
                {{ getDescriptionPreview(record.description) || '--' }}
              </span>
            </template>
            <!-- 操作列 -->
            <template v-else-if="column.key === 'action'">
              <div class="action-group">
                <div class="action-btn edit" @click="handleView(record)" title="详情">
                  <EyeOutlined />
                </div>
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
      width="860px"
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
              <a-form-item name="title">
                <template #label><span class="form-label">标题</span></template>
                <a-input v-model:value="formData.title" placeholder="请输入" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="icon">
                <template #label><span class="form-label">图标</span></template>
                <a-input v-model:value="formData.icon" placeholder="请输入" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="growthValue">
                <template #label><span class="form-label">所需成长值</span></template>
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
            <a-col :span="12">
              <a-form-item name="background">
                <template #label><span class="form-label">背景</span></template>
                <a-input v-model:value="formData.background" placeholder="请输入" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="color">
                <template #label><span class="form-label">颜色</span></template>
                <a-input v-model:value="formData.color" placeholder="请输入" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="description">
                <template #label><span class="form-label">描述</span></template>
                <TipTapEditor v-model="formData.description" placeholder="请输入等级描述内容" :min-height="220" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="description">
                <template #label><span class="form-label">描述</span></template>
                <a-textarea v-model:value="formData.description" placeholder="请输入" />              </a-form-item>
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

    <a-modal
      v-model:open="detailVisible"
      :title="null"
      :footer="null"
      width="920px"
      :destroy-on-close="true"
      class="glass-modal"
      centered
      @cancel="handleDetailCancel"
    >
      <div class="modal-inner">
        <div class="modal-header">
          <div class="modal-icon edit-icon">
            <EyeOutlined />
          </div>
          <div class="modal-titles">
            <h2>查看详情</h2>
            <p>浏览当前用户等级配置的完整信息</p>
          </div>
        </div>

        <a-skeleton active :loading="detailLoading" :paragraph="{ rows: 8 }">
          <a-row :gutter="[24, 20]">
            <a-col :span="12">
              <div class="form-section-title">基本信息</div>
              <a-descriptions :column="1" size="small" bordered>
                <a-descriptions-item label="标题">{{ detailData.title || '--' }}</a-descriptions-item>
                <a-descriptions-item label="图标">{{ detailData.icon || '--' }}</a-descriptions-item>
                <a-descriptions-item label="所需成长值">{{ detailData.growthValue ?? '--' }}</a-descriptions-item>
                <a-descriptions-item label="状态">
                  {{ getDictLabel('status', detailData.status) || '--' }}
                </a-descriptions-item>
                <a-descriptions-item label="背景">{{ detailData.background || '--' }}</a-descriptions-item>
                <a-descriptions-item label="颜色">{{ detailData.color || '--' }}</a-descriptions-item>
              </a-descriptions>
            </a-col>
            <a-col :span="12">
              <div class="form-section-title">描述内容</div>
              <div class="detail-rich-wrapper">
                <TipTapViewer :content="detailData.description" show-empty empty-text="暂无描述内容" />
              </div>
            </a-col>
          </a-row>
        </a-skeleton>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handleDetailCancel">关闭</a-button>
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
  EditOutlined, PlusCircleOutlined, CheckCircleFilled, MinusCircleFilled, EyeOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { UserLevelConfigVo, UserLevelConfigQuery, UserLevelConfigCreate, UserLevelConfigUpdate } from '@/api/userGrowth/userlevelconfig.ts'
import { getUserLevelConfigPage, createUserLevelConfig, updateUserLevelConfig, deleteUserLevelConfig, batchDeleteUserLevelConfig, getUserLevelConfigById } from '@/api/userGrowth/userlevelconfig.ts'
import { getRequestId } from "@/utils/idUtils.ts"
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'
import StatusCardSelect from '@/components/StatusCardSelect/index.vue'
import TipTapEditor from '@/components/TipTapEditor/index.vue'
import TipTapViewer from '@/components/TipTapEditor/viewer.vue'

const permissionStore = usePermissionStore()

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('usergrowth:userlevelconfig:add')
  canUpdate.value = hasButtonPermission('usergrowth:userlevelconfig:update')
  canDelete.value = hasButtonPermission('usergrowth:userlevelconfig:delete')
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

const getPlainText = (html?: string) => {
  if (!html) return ''
  return html
    .replace(/<[^>]+>/g, ' ')
    .replace(/&nbsp;/gi, ' ')
    .replace(/&amp;/gi, '&')
    .replace(/&lt;/gi, '<')
    .replace(/&gt;/gi, '>')
    .replace(/\s+/g, ' ')
    .trim()
}

const getDescriptionPreview = (html?: string) => {
  const text = getPlainText(html)
  if (!text) return ''
  return text.length > 30 ? `${text.slice(0, 30)}...` : text
}

// 表格列定义
const columns = [
  { title: '标题', dataIndex: 'title', key: 'title' },
  { title: '图标', dataIndex: 'icon', key: 'icon' },
  { title: '所需成长值', dataIndex: 'growthValue', key: 'growthValue' },
  { title: '状态', dataIndex: 'status', key: 'status' },
  { title: '背景', dataIndex: 'background', key: 'background' },
  { title: '颜色', dataIndex: 'color', key: 'color' },
  { title: '描述', dataIndex: 'description', key: 'description' },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const requestId = ref<string>('')
const dataSource = ref<UserLevelConfigVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<UserLevelConfigQuery>({
  page: 1,
  pageSize: 10,
  title: '',
  icon: '',
  growthValue: undefined,
  status: undefined,
  background: '',
  color: '',
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
const detailVisible = ref(false)
const detailLoading = ref(false)
const formRef = ref<FormInstance>()
type FormData = UserLevelConfigCreate & { id?: number }
const formData = reactive<FormData>({
  id: undefined,
  title: '',
  icon: '',
  growthValue: undefined,
  status: 1,
  background: '',
  color: '',
  description: '',
})
const detailData = reactive<Partial<UserLevelConfigVo>>({
  id: undefined,
  title: '',
  icon: '',
  growthValue: undefined,
  status: undefined,
  background: '',
  color: '',
  description: '',
})

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getUserLevelConfigPage(params)
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
  title: '',
  icon: '',
  growthValue: undefined,
  status: undefined,
  background: '',
  color: '',
  description: '',
  })
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleStatusChange = async (record: UserLevelConfigVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  requestId.value = getRequestId();
  const res = await updateUserLevelConfig({ ...record, status: newStatus } as UserLevelConfigUpdate, requestId.value)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '状态已恢复正常' : '状态已被冻结')
  }
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined,
  title: '',
  icon: '',
  growthValue: undefined,
  status: 1,
  background: '',
  color: '',
  description: '',
  })
  modalVisible.value = true
}

const handleEdit = async (record: UserLevelConfigVo) => {
  isEdit.value = true
  try {
    const res = await getUserLevelConfigById(record.id!)
    if (res.code === 200) {
      Object.assign(formData, res.data)
    }
  } catch (error) {
    console.error('获取详情失败:', error)
  }
  modalVisible.value = true
}

const handleView = async (record: UserLevelConfigVo) => {
  detailVisible.value = true
  detailLoading.value = true
  try {
    const res = await getUserLevelConfigById(record.id!)
    if (res.code === 200) {
      Object.assign(detailData, res.data || {})
    }
  } catch (error) {
    console.error('获取详情失败:', error)
  } finally {
    detailLoading.value = false
  }
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    requestId.value = getRequestId()

    if (isEdit.value) {
      const res = await updateUserLevelConfig(formData as UserLevelConfigUpdate, requestId.value)
      if (res.code === 200) {
        message.success({ content: '更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData: UserLevelConfigCreate = { ...(formData as any) }
      delete (submitData as any).id
      const res = await createUserLevelConfig(submitData, requestId.value)
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

const handleDetailCancel = () => {
  detailVisible.value = false
}

const handleDeleteOne = (record: UserLevelConfigVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除该条数据，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteUserLevelConfig(record.id!)
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
      const res = await batchDeleteUserLevelConfig(selectedRowKeys.value)
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

.detail-rich-wrapper {
  min-height: 360px;
  padding: 16px;
  border: 1px solid rgba(148, 163, 184, 0.18);
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.82);
  backdrop-filter: blur(10px);
  overflow: auto;
}
</style>
