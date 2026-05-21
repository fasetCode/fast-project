<template>
  <div class="pagecomponent-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">组件标题</span>
            <a-input 
              v-model:value="queryParams.title" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><AppstoreAddOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">组件编码</span>
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
            <div class="tab active">全部组件 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增组件
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
            <!-- 组件标题列 -->
            <template v-if="column.key === 'title'">
              <div class="user-profile">
                <div class="avatar-box" :style="{ background: getAvatarColor(record.id) }">
                  {{ record.title.charAt(0).toUpperCase() }}
                </div>
                <div class="user-info">
                  <span class="user-name">{{ record.title }}</span>
                </div>
              </div>
            </template>
            
            <!-- 组件编码列 -->
            <template v-else-if="column.key === 'code'">
              <span class="text-code">{{ record.code }}</span>
            </template>

            <!-- 类型名称列 -->
            <template v-else-if="column.key === 'typeName'">
              <a-tag color="blue">{{ record.typeName }}</a-tag>
            </template>

               <!-- 图标列 -->
            <template v-else-if="column.key === 'icon'">
              <ImageDisplay :value="record.icon" valueType="id" :width="40" :height="40" />
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
            <h2>{{ isEdit ? '编辑组件信息' : '创建新组件' }}</h2>
            <p>请填写以下必填信息以完成组件设置</p>
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
                <template #label><span class="form-label">组件标题</span></template>
                <a-input v-model:value="formData.title" placeholder="如：轮播图组件" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="code">
                <template #label><span class="form-label">组件编码</span></template>
                <a-input v-model:value="formData.code" placeholder="如：carousel" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="icon">
                <template #label><span class="form-label">图标</span></template>
                <ImageUpload v-model="formData.icon" :limit="1" valueType="id" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="typeId">
                <template #label><span class="form-label">类型</span></template>
                <TypeSelect v-model="formData.typeId" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="sort">
                <template #label><span class="form-label">排序</span></template>
                <a-input-number v-model:value="formData.sort" :min="0" placeholder="数字越小越靠前" style="width: 100%" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="status">
                <template #label><span class="form-label">组件状态</span></template>
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
  EditOutlined, AppstoreAddOutlined, KeyOutlined, CheckCircleFilled, 
  MinusCircleFilled, LoadingOutlined, PlusCircleOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { PageComponentVo, PageComponentQuery, PageComponentCreate, PageComponentUpdate } from '@/api/page/pagecomponent.ts'
import { getPageComponentPage, createPageComponent, updatePageComponent, deletePageComponent, batchDeletePageComponent, getPageComponentById } from '@/api/page/pagecomponent.ts'
import { getRequestId } from "@/utils/idUtils.ts";
import StatusCardSelect from '@/components/StatusCardSelect/index.vue'
import TypeSelect from '../components/TypeSelect.vue'
import ImageUpload from '@/components/ImageUpload/index.vue'
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'
import ImageDisplay from '@/components/ImageDisplay/index.vue'

const appStore = useAppStore()
const permissionStore = usePermissionStore()

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('admin:page:component:add')
  canUpdate.value = hasButtonPermission('admin:page:component:update')
  canDelete.value = hasButtonPermission('admin:page:component:delete')
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
  { title: '组件标题', dataIndex: 'title', key: 'title', width: 180 },
  { title: '组件编码', dataIndex: 'code', key: 'code', width: 150 },
  { title: '图标', dataIndex: 'icon', key: 'icon', width: 100 },
  { title: '类型', dataIndex: 'typeName', key: 'typeName', width: 120 },
  { title: '排序', dataIndex: 'sort', key: 'sort', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const requestId = ref<string>('')
const dataSource = ref<PageComponentVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<PageComponentQuery>({
  page: 1,
  pageSize: 10,
  title: '',
  code: '',
  status: undefined,
  typeId: undefined,
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
type PageComponentFormData = PageComponentCreate & { id?: number }
const formData = reactive<PageComponentFormData>({
  id: undefined,
  title: '',
  icon: '',
  typeId: undefined as string | undefined,
  code: '',
  status: 0,
  sort: 0,
})

const rules = {
  title: [{ required: true, message: '请输入组件标题', trigger: 'blur' }],
  code: [{ required: true, message: '请输入组件编码', trigger: 'blur' }],
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
    const res = await getPageComponentPage(params)
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
  queryParams.code = ''
  queryParams.status = undefined
  queryParams.typeId = undefined
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined, title: '', icon: '', typeId: undefined, code: '', status: 0, sort: 0,
  })
  modalVisible.value = true
}

const handleEdit = async (record: PageComponentVo) => {
  isEdit.value = true
  try {
    const res = await getPageComponentById(record.id!)
    if (res.code === 200) {
      const data = res.data
      // Convert Long-type IDs to strings to prevent precision loss
      if (data.typeId) {
        data.typeId = String(data.typeId)
      }
      Object.assign(formData, data)
    }
  } catch (error) {
    console.error('获取组件详情失败:', error)
  }
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    requestId.value = getRequestId();

    if (isEdit.value) {
      const res = await updatePageComponent(formData as PageComponentUpdate, requestId.value)
      if (res.code === 200) {
        message.success({ content: '组件更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData: PageComponentCreate = { ...(formData as any) }
      delete (submitData as any).id
      const res = await createPageComponent(submitData, requestId.value)
      if (res.code === 200) {
        message.success({ content: '组件创建成功', class: 'custom-message' })
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

const handleDeleteOne = (record: PageComponentVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除组件「${record.title}」，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deletePageComponent(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个组件，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeletePageComponent(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: PageComponentVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  requestId.value = getRequestId();
  const res = await updatePageComponent({ ...record, status: newStatus } as PageComponentUpdate, requestId.value)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '组件已启用' : '组件已禁用')
  }
}

onMounted(() => {
  loadData()
  loadButtonPermissions()
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
