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
              placeholder="输入标题搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><TeamOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">接收人</span>
            <a-input 
              v-model:value="queryParams.receiver" 
              placeholder="输入接收人搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><TeamOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">消息类型</span>
            <a-select 
              v-model:value="queryParams.messageType" 
              placeholder="全部类型" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option v-for="item in getDictData('message_record_type')" :key="item.value" :value="item.value">
                {{ item.label }}
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
              <a-select-option v-for="item in getDictData('message_record_status')" :key="item.value" :value="item.value">
                {{ item.label }}
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
            <div class="tab active">消息记录 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
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
              <template v-if="column.key === 'title'">
                <div class="user-profile">
                  <div class="avatar-box" :style="{ background: getAvatarColor(record.id) }">
                    {{ record.title?.charAt(0).toUpperCase() || 'M' }}
                  </div>
                  <div class="user-info">
                    <span class="user-name">{{ record.title }}</span>
                  </div>
                </div>
              </template>
              
              <template v-else-if="column.key === 'messageType'">
                <span class="text-code">{{ getDictLabel('message_record_type', record.messageType) || record.messageType }}</span>
              </template>

              <template v-else-if="column.key === 'status'">
                <span class="status-text text-code" :class="record.status === 'SUCCESS' ? 'text-success' : 'text-error'">
                  {{ getDictLabel('message_record_status', record.status) || record.status }}
                </span>
              </template>

              <!-- 操作列 -->
              <template v-else-if="column.key === 'action'">
                <div class="action-group">
                  <div class="action-btn view" @click="handleDetail(record)" title="详情">
                    <EyeOutlined />
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

    <!-- 极简苹果风弹窗 - 详情 -->
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
          <div class="modal-icon edit-icon">
            <EyeOutlined />
          </div>
          <div class="modal-titles">
            <h2>消息记录详情</h2>
            <p>查看消息发送的详细信息</p>
          </div>
        </div>

        <a-descriptions bordered :column="1" size="small" class="glass-descriptions">
          <a-descriptions-item label="消息标题">{{ detailData.title }}</a-descriptions-item>
          <a-descriptions-item label="接收人">{{ detailData.receiver }}</a-descriptions-item>
          <a-descriptions-item label="消息类型">{{ getDictLabel('message_record_type', detailData.messageType) || detailData.messageType }}</a-descriptions-item>
          <a-descriptions-item label="发送状态">
            {{ getDictLabel('message_record_status', detailData.status) || detailData.status }}
          </a-descriptions-item>
          <a-descriptions-item label="用户类型">{{ detailData.userType }}</a-descriptions-item>
          <a-descriptions-item label="发送时间">{{ detailData.createTime }}</a-descriptions-item>
          <a-descriptions-item label="消息内容">
            <div style="white-space: pre-wrap; max-height: 200px; overflow-y: auto;">{{ detailData.content }}</div>
          </a-descriptions-item>
        </a-descriptions>

        <div class="modal-footer-custom" style="margin-top: 24px;">
          <a-button class="glass-btn" @click="handleCancel">关闭</a-button>
        </div>
      </div>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, h } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  SearchOutlined, ReloadOutlined, DeleteOutlined, 
  TeamOutlined, EyeOutlined, LoadingOutlined
} from '@ant-design/icons-vue'
import type { MessageRecordVo, MessageRecordQuery } from '@/api/message/messagerecord.ts'
import { getMessageRecordPage, deleteMessageRecord, batchDeleteMessageRecord, getMessageRecordById } from '@/api/message/messagerecord.ts'
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'

const appStore = useAppStore()
const permissionStore = usePermissionStore()

// 按钮权限控制
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canDelete.value = hasButtonPermission('admin:message:record:delete')
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
  { title: '标题', dataIndex: 'title', key: 'title', width: 200 },
  { title: '接收人', dataIndex: 'receiver', key: 'receiver', width: 150 },
  { title: '消息类型', dataIndex: 'messageType', key: 'messageType', width: 120 },
  { title: '发送状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '发送时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const dataSource = ref<MessageRecordVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<MessageRecordQuery>({
  page: 1,
  pageSize: 10,
  title: '',
  receiver: '',
  messageType: undefined,
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
const detailData = ref<MessageRecordVo>({})

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
    const res = await getMessageRecordPage(params)
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
  queryParams.receiver = ''
  queryParams.messageType = undefined
  queryParams.status = undefined
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleDetail = async (record: MessageRecordVo) => {
  try {
    const res = await getMessageRecordById(record.id!)
    if (res.code === 200) {
      detailData.value = res.data
      modalVisible.value = true
    }
  } catch (error) {
    console.error('获取记录详情失败:', error)
  }
}

const handleCancel = () => {
  modalVisible.value = false
}

const handleDeleteOne = (record: MessageRecordVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除记录「${record.title}」，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteMessageRecord(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个记录，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteMessageRecord(selectedRowKeys.value)
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

.glass-descriptions :deep(.ant-descriptions-item-label) {
  width: 120px;
  background-color: rgba(248, 250, 252, 0.5);
  font-weight: 500;
}
:global(html.dark) .glass-descriptions :deep(.ant-descriptions-item-label) {
  background-color: rgba(30, 41, 59, 0.5);
}
</style>
