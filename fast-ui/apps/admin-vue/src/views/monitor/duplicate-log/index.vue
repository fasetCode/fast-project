<template>
  <div class="duplicate-log-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">请求ID</span>
            <a-input 
              v-model:value="queryParams.requestId" 
              placeholder="输入请求ID..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><KeyOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">幂等前缀</span>
            <a-input 
              v-model:value="queryParams.prefix" 
              placeholder="输入前缀..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><TagOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">请求URL</span>
            <a-input 
              v-model:value="queryParams.requestUrl" 
              placeholder="输入URL..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><LinkOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">请求方法</span>
            <a-select 
              v-model:value="queryParams.requestMethod" 
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
            <span class="filter-label">用户名</span>
            <a-input 
              v-model:value="queryParams.username" 
              placeholder="输入用户名..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><UserOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">IP地址</span>
            <a-input 
              v-model:value="queryParams.ipAddress" 
              placeholder="输入IP..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><GlobalOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">操作标题</span>
            <a-input 
              v-model:value="queryParams.title" 
              placeholder="输入标题..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><FileTextOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item wide">
            <span class="filter-label">首次请求时间</span>
            <a-range-picker 
              v-model:value="dateRange" 
              class="elegant-picker"
              :show-time="{ format: 'HH:mm' }"
              format="YYYY-MM-DD HH:mm"
              @change="handleDateChange"
            />
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
            <div class="tab active">重复提交记录 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canDelete" danger class="pill-btn danger-pill" :class="{ 'is-disabled': selectedRowKeys.length === 0 }" :disabled="selectedRowKeys.length === 0" @click="handleDelete()">
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
            :scroll="{ x: 1500 }"
            @change="handleTableChange"
            class="elegant-table"
          >
            <template #bodyCell="{ column, record }">
              <!-- 请求ID列 -->
              <template v-if="column.key === 'requestId'">
                <div class="request-id-cell">
                  <a-tooltip :title="record.requestId">
                    <span class="request-id-text">{{ record.requestId }}</span>
                  </a-tooltip>
                </div>
              </template>

              <!-- 前缀列 -->
              <template v-else-if="column.key === 'prefix'">
                <a-tag v-if="record.prefix" color="purple" class="prefix-tag">{{ record.prefix }}</a-tag>
                <span v-else class="text-muted">-</span>
              </template>

              <!-- 请求URL列 -->
              <template v-else-if="column.key === 'requestUrl'">
                <div class="url-wrap">
                  <a-tag :color="getMethodColor(record.requestMethod)" size="small" class="method-tag">
                    {{ record.requestMethod || 'UNKNOWN' }}
                  </a-tag>
                  <a-tooltip :title="record.requestUrl">
                    <span class="url-text">{{ record.requestUrl }}</span>
                  </a-tooltip>
                </div>
              </template>

              <!-- 用户信息列 -->
              <template v-else-if="column.key === 'userInfo'">
                <div class="user-info-cell" v-if="record.username">
                  <div class="user-name">{{ record.username }}</div>
                  <div class="user-id">ID: {{ record.userId }}</div>
                </div>
                <span v-else class="text-muted">-</span>
              </template>

              <!-- IP地址列 -->
              <template v-else-if="column.key === 'ipAddress'">
                <div class="ip-info">
                  <GlobalOutlined />
                  <span>{{ record.ipAddress || '-' }}</span>
                </div>
              </template>

              <!-- 标题列 -->
              <template v-else-if="column.key === 'title'">
                <span class="title-text">{{ record.title || '-' }}</span>
              </template>

              <!-- 重复次数列 -->
              <template v-else-if="column.key === 'duplicateCount'">
                <div class="count-badge" :class="getCountClass(record.duplicateCount)">
                  <span class="count-value">{{ record.duplicateCount || 0 }}</span>
                  <span class="count-label">次</span>
                </div>
              </template>

              <!-- 首次请求时间列 -->
              <template v-else-if="column.key === 'firstRequestTime'">
                <div class="time-cell">
                  <CalendarOutlined />
                  <span>{{ formatDateTime(record.firstRequestTime) }}</span>
                </div>
              </template>

              <!-- 最后重复时间列 -->
              <template v-else-if="column.key === 'lastDuplicateTime'">
                <div class="time-cell">
                  <ClockCircleOutlined />
                  <span>{{ formatDateTime(record.lastDuplicateTime) }}</span>
                </div>
              </template>

              <!-- 创建时间列 -->
              <template v-else-if="column.key === 'createTime'">
                <div class="time-cell">
                  <span>{{ formatDateTime(record.createTime) }}</span>
                </div>
              </template>

              <!-- 操作列 -->
              <template v-else-if="column.key === 'operation'">
                <div class="action-group">
                  <div class="action-btn view" @click="handleView(record)" title="查看详情">
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

    <!-- 详情弹窗 -->
    <a-modal
      v-model:open="detailModalVisible"
      :title="null"
      :footer="null"
      width="850px"
      :destroy-on-close="true"
      class="glass-modal"
      centered
      @cancel="handleDetailCancel"
    >
      <div class="modal-inner" v-if="currentRecord">
        <div class="modal-header">
          <div class="modal-icon warning-icon">
            <ExclamationCircleOutlined />
          </div>
          <div class="modal-titles">
            <h2>重复提交记录详情</h2>
            <p>
              <a-tag :color="getMethodColor(currentRecord.requestMethod)" class="header-tag">
                {{ currentRecord.requestMethod || 'UNKNOWN' }}
              </a-tag>
              <span class="record-id">ID: {{ currentRecord.id }}</span>
            </p>
          </div>
        </div>

        <!-- 基本信息 -->
        <div class="glass-section">
          <div class="section-header">
            <FileTextOutlined class="section-icon" />
            <span class="section-name">基本信息</span>
          </div>
          <div class="glass-grid">
            <div class="glass-item wide">
              <span class="glass-label">请求ID</span>
              <span class="glass-value id-value">{{ currentRecord.requestId }}</span>
            </div>
            <div class="glass-item">
              <span class="glass-label">幂等前缀</span>
              <a-tag v-if="currentRecord.prefix" color="purple" class="prefix-badge">{{ currentRecord.prefix }}</a-tag>
              <span v-else class="glass-value">-</span>
            </div>
            <div class="glass-item">
              <span class="glass-label">操作标题</span>
              <span class="glass-value">{{ currentRecord.title || '-' }}</span>
            </div>
            <div class="glass-item">
              <span class="glass-label">重复次数</span>
              <span class="glass-value count" :class="getCountClass(currentRecord.duplicateCount)">
                {{ currentRecord.duplicateCount || 0 }} 次
              </span>
            </div>
            <div class="glass-item wide">
              <span class="glass-label">提示消息</span>
              <span class="glass-value message">{{ currentRecord.message || '-' }}</span>
            </div>
          </div>
        </div>

        <!-- 请求信息 -->
        <div class="glass-section">
          <div class="section-header">
            <GlobalOutlined class="section-icon" />
            <span class="section-name">请求信息</span>
          </div>
          <div class="glass-grid">
            <div class="glass-item">
              <span class="glass-label">请求方法</span>
              <a-tag :color="getMethodColor(currentRecord.requestMethod)" class="method-badge">
                {{ currentRecord.requestMethod || 'UNKNOWN' }}
              </a-tag>
            </div>
            <div class="glass-item">
              <span class="glass-label">IP地址</span>
              <span class="glass-value ip">
                <GlobalOutlined /> {{ currentRecord.ipAddress || '-' }}
              </span>
            </div>
            <div class="glass-item wide">
              <span class="glass-label">请求URL</span>
              <span class="glass-value url">{{ currentRecord.requestUrl }}</span>
            </div>
            <div class="glass-item wide">
              <span class="glass-label">User-Agent</span>
              <span class="glass-value ua">{{ currentRecord.userAgent || '-' }}</span>
            </div>
          </div>
        </div>

        <!-- 用户信息 -->
        <div class="glass-section">
          <div class="section-header">
            <UserOutlined class="section-icon" />
            <span class="section-name">用户信息</span>
          </div>
          <div class="glass-grid">
            <div class="glass-item">
              <span class="glass-label">用户ID</span>
              <span class="glass-value">{{ currentRecord.userId || '-' }}</span>
            </div>
            <div class="glass-item">
              <span class="glass-label">用户名</span>
              <span class="glass-value">{{ currentRecord.username || '-' }}</span>
            </div>
          </div>
        </div>

        <!-- 请求参数 -->
        <div class="glass-section" v-if="currentRecord.requestParams">
          <div class="section-header">
            <CodeOutlined class="section-icon" />
            <span class="section-name">请求参数</span>
          </div>
          <div class="code-panel">
            <pre class="code-content">{{ formatJson(currentRecord.requestParams) }}</pre>
          </div>
        </div>

        <!-- 时间信息 -->
        <div class="glass-section">
          <div class="section-header">
            <ClockCircleOutlined class="section-icon" />
            <span class="section-name">时间信息</span>
          </div>
          <div class="glass-grid">
            <div class="glass-item">
              <span class="glass-label">首次请求时间</span>
              <span class="glass-value time-full">
                <CalendarOutlined /> {{ formatDateTime(currentRecord.firstRequestTime) }}
              </span>
            </div>
            <div class="glass-item">
              <span class="glass-label">最后重复时间</span>
              <span class="glass-value time-full">
                <ClockCircleOutlined /> {{ formatDateTime(currentRecord.lastDuplicateTime) }}
              </span>
            </div>
            <div class="glass-item">
              <span class="glass-label">记录创建时间</span>
              <span class="glass-value time-full">{{ formatDateTime(currentRecord.createTime) }}</span>
            </div>
            <div class="glass-item">
              <span class="glass-label">记录更新时间</span>
              <span class="glass-value time-full">{{ formatDateTime(currentRecord.updateTime) }}</span>
            </div>
          </div>
        </div>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handleDetailCancel">关闭</a-button>
          <a-button v-if="canDelete" danger class="danger-glass-btn" @click="handleDetailDelete">
            <DeleteOutlined /> 删除此记录
          </a-button>
        </div>
      </div>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, h } from 'vue'
import { message, Modal } from 'ant-design-vue'
import type { Dayjs } from 'ant-design-vue/es/time-picker'
import {
  SearchOutlined, ReloadOutlined, DeleteOutlined, EyeOutlined,
  FileTextOutlined, GlobalOutlined, LinkOutlined, ClockCircleOutlined,
  CalendarOutlined, LoadingOutlined, KeyOutlined, TagOutlined,
  UserOutlined, ExclamationCircleOutlined, CodeOutlined
} from '@ant-design/icons-vue'
import type { IdempotentDuplicateLogVo, IdempotentDuplicateLogQuery } from '@/api/idempotent/duplicateLog'
import { getDuplicateLogPage, deleteDuplicateLog, batchDeleteDuplicateLog, getDuplicateLogById } from '@/api/idempotent/duplicateLog'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'

const permissionStore = usePermissionStore()

// 按钮权限控制
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canDelete.value = hasButtonPermission('admin:idempotent:duplicate-log:delete')
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
  { title: '请求ID', dataIndex: 'requestId', key: 'requestId', width: 200 },
  { title: '前缀', dataIndex: 'prefix', key: 'prefix', width: 100 },
  { title: '请求URL', dataIndex: 'requestUrl', key: 'requestUrl', width: 280 },
  { title: '用户', dataIndex: 'userInfo', key: 'userInfo', width: 120 },
  { title: 'IP地址', dataIndex: 'ipAddress', key: 'ipAddress', width: 130 },
  { title: '标题', dataIndex: 'title', key: 'title', width: 150 },
  { title: '重复次数', dataIndex: 'duplicateCount', key: 'duplicateCount', width: 90 },
  { title: '首次请求', dataIndex: 'firstRequestTime', key: 'firstRequestTime', width: 160 },
  { title: '最后重复', dataIndex: 'lastDuplicateTime', key: 'lastDuplicateTime', width: 160 },
  { title: '操作', key: 'operation', width: 100, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const dataSource = ref<IdempotentDuplicateLogVo[]>([])
const selectedRowKeys = ref<number[]>([])
const dateRange = ref<[Dayjs, Dayjs] | null>(null)

const queryParams = reactive<IdempotentDuplicateLogQuery>({
  page: 0,
  pageSize: 10,
  requestId: '',
  prefix: '',
  requestUrl: '',
  requestMethod: undefined,
  userId: undefined,
  username: '',
  ipAddress: '',
  title: '',
  firstRequestTimeStart: undefined,
  firstRequestTimeEnd: undefined,
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条数据`,
})

// 详情弹窗
const detailModalVisible = ref(false)
const currentRecord = ref<IdempotentDuplicateLogVo | null>(null)

// HTTP方法颜色
const getMethodColor = (method: string | undefined) => {
  const colors: Record<string, string> = {
    'GET': 'blue',
    'POST': 'green',
    'PUT': 'orange',
    'DELETE': 'red',
  }
  return colors[method || ''] || 'default'
}

// 获取重复次数样式类
const getCountClass = (count: number | undefined) => {
  if (!count) return ''
  if (count < 5) return 'low'
  if (count < 20) return 'medium'
  return 'high'
}

// 格式化日期时间
const formatDateTime = (date: string | undefined) => {
  if (!date) return '-'
  const d = new Date(date)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const hour = String(d.getHours()).padStart(2, '0')
  const minute = String(d.getMinutes()).padStart(2, '0')
  const second = String(d.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hour}:${minute}:${second}`
}

// 格式化 JSON
const formatJson = (json: string | undefined) => {
  if (!json) return '{}'
  try {
    return JSON.stringify(JSON.parse(json), null, 2)
  } catch {
    return json
  }
}

// 日期范围变化
const handleDateChange = (dates: [Dayjs, Dayjs] | null) => {
  if (dates) {
    queryParams.firstRequestTimeStart = formatDateTime(dates[0].toISOString())
    queryParams.firstRequestTimeEnd = formatDateTime(dates[1].toISOString())
  } else {
    queryParams.firstRequestTimeStart = undefined
    queryParams.firstRequestTimeEnd = undefined
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getDuplicateLogPage(params)
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
  queryParams.requestId = ''
  queryParams.prefix = ''
  queryParams.requestUrl = ''
  queryParams.requestMethod = undefined
  queryParams.userId = undefined
  queryParams.username = ''
  queryParams.ipAddress = ''
  queryParams.title = ''
  queryParams.firstRequestTimeStart = undefined
  queryParams.firstRequestTimeEnd = undefined
  dateRange.value = null
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleView = async (record: IdempotentDuplicateLogVo) => {
  try {
    const res = await getDuplicateLogById(record.id!)
    if (res.code === 200) {
      currentRecord.value = res.data
      detailModalVisible.value = true
    }
  } catch (error) {
    console.error('获取详情失败:', error)
  }
}

const handleDeleteOne = (record: IdempotentDuplicateLogVo) => {
  Modal.confirm({
    title: '删除确认',
    content: `确定删除该重复提交记录吗？`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteDuplicateLog(record.id!)
      if (res.code === 200) {
        message.success('删除成功')
        loadData()
      }
    },
  })
}

const handleDetailCancel = () => {
  detailModalVisible.value = false
  currentRecord.value = null
}

const handleDetailDelete = () => {
  if (!currentRecord.value) return
  Modal.confirm({
    title: '删除确认',
    content: '确定删除该重复提交记录吗？此操作无法撤销。',
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteDuplicateLog(currentRecord.value!.id!)
      if (res.code === 200) {
        message.success('删除成功')
        detailModalVisible.value = false
        currentRecord.value = null
        loadData()
      }
    },
  })
}

const handleDelete = () => {
  if (selectedRowKeys.value.length === 0) return
  Modal.confirm({
    title: '批量删除确认',
    content: `将删除选中的 ${selectedRowKeys.value.length} 条重复提交记录，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteDuplicateLog(selectedRowKeys.value)
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

/* 请求ID */
.request-id-cell {
  max-width: 180px;
}

.request-id-text {
  display: inline-block;
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
  color: #6366f1;
  background: rgba(99, 102, 241, 0.08);
  padding: 2px 8px;
  border-radius: 4px;
}

/* 前缀标签 */
.prefix-tag {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 4px;
}

/* URL包裹 */
.url-wrap {
  display: flex;
  align-items: center;
  gap: 8px;
}

.method-tag {
  font-size: 11px;
  font-weight: 600;
  padding: 0 6px;
  height: 20px;
  line-height: 20px;
}

.url-text {
  display: inline-block;
  max-width: 200px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #64748b;
  font-size: 12px;
}

:global(html.dark) .url-text {
  color: #94a3b8;
}

/* 用户信息 */
.user-info-cell {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.user-name {
  font-size: 13px;
  font-weight: 600;
  color: #334155;
}

:global(html.dark) .user-name {
  color: #e2e8f0;
}

.user-id {
  font-size: 11px;
  color: #94a3b8;
}

/* IP信息 */
.ip-info {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 12px;
  color: #0ea5e9;
  font-family: 'Monaco', 'Menlo', monospace;
}

/* 标题文本 */
.title-text {
  font-size: 13px;
  color: #475569;
  max-width: 140px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: inline-block;
}

:global(html.dark) .title-text {
  color: #cbd5e1;
}

/* 重复次数徽章 */
.count-badge {
  display: inline-flex;
  align-items: center;
  gap: 2px;
  padding: 2px 10px;
  border-radius: 12px;
  font-weight: 600;
  width: fit-content;
}

.count-badge.low {
  background: rgba(16, 185, 129, 0.1);
  color: #059669;
}

.count-badge.medium {
  background: rgba(245, 158, 11, 0.1);
  color: #d97706;
}

.count-badge.high {
  background: rgba(239, 68, 68, 0.1);
  color: #dc2626;
}

:global(html.dark) .count-badge.low {
  background: rgba(16, 185, 129, 0.2);
  color: #34d399;
}

:global(html.dark) .count-badge.medium {
  background: rgba(245, 158, 11, 0.2);
  color: #fbbf24;
}

:global(html.dark) .count-badge.high {
  background: rgba(239, 68, 68, 0.2);
  color: #f87171;
}

.count-value {
  font-size: 14px;
}

.count-label {
  font-size: 11px;
}

/* 时间显示 */
.time-cell {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #64748b;
}

:global(html.dark) .time-cell {
  color: #94a3b8;
}

/* 详情弹窗样式 */
.modal-icon {
  width: 56px;
  height: 56px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
}

.modal-icon.warning-icon {
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
  color: white;
}

.record-id {
  font-size: 12px;
  color: #94a3b8;
  font-family: 'Monaco', 'Menlo', monospace;
}

/* 玻璃拟态区块 */
.glass-section {
  background: rgba(248, 250, 252, 0.8);
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 16px;
  border: 1px solid rgba(226, 232, 240, 0.6);
}

:global(html.dark) .glass-section {
  background: rgba(30, 41, 59, 0.6);
  border-color: rgba(51, 65, 85, 0.6);
}

.section-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(226, 232, 240, 0.6);
}

:global(html.dark) .section-header {
  border-bottom-color: rgba(51, 65, 85, 0.6);
}

.section-icon {
  font-size: 16px;
  color: #6366f1;
}

:global(html.dark) .section-icon {
  color: #818cf8;
}

.section-name {
  font-size: 14px;
  font-weight: 600;
  color: #1e293b;
}

:global(html.dark) .section-name {
  color: #f1f5f9;
}

/* 玻璃网格布局 */
.glass-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px 24px;
}

.glass-item {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.glass-item.wide {
  grid-column: span 2;
}

.glass-label {
  font-size: 12px;
  color: #64748b;
  font-weight: 500;
}

:global(html.dark) .glass-label {
  color: #94a3b8;
}

.glass-value {
  font-size: 13px;
  color: #334155;
  font-weight: 600;
  word-break: break-all;
}

:global(html.dark) .glass-value {
  color: #e2e8f0;
}

.glass-value.id-value {
  font-family: 'Monaco', 'Menlo', monospace;
  background: rgba(99, 102, 241, 0.1);
  color: #6366f1;
  padding: 4px 10px;
  border-radius: 6px;
  display: inline-block;
  width: fit-content;
}

.glass-value.count {
  font-weight: 700;
}

.glass-value.count.low {
  color: #10b981;
}

.glass-value.count.medium {
  color: #f59e0b;
}

.glass-value.count.high {
  color: #ef4444;
}

.glass-value.message {
  font-size: 13px;
  line-height: 1.5;
  color: #64748b;
}

.glass-value.url {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
  color: #6366f1;
  background: rgba(99, 102, 241, 0.08);
  padding: 6px 12px;
  border-radius: 6px;
}

.glass-value.ua {
  font-size: 11px;
  color: #64748b;
  background: rgba(30, 41, 59, 0.05);
  padding: 4px 10px;
  border-radius: 6px;
  font-family: 'Monaco', 'Menlo', monospace;
}

:global(html.dark) .glass-value.ua {
  background: rgba(30, 41, 59, 0.5);
  color: #94a3b8;
}

.glass-value.ip {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #0ea5e9;
}

.glass-value.time-full {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #64748b;
  font-weight: 500;
}

:global(html.dark) .glass-value.time-full {
  color: #94a3b8;
}

/* 标签徽章 */
.prefix-badge {
  font-size: 12px;
  padding: 2px 10px;
  border-radius: 4px;
  width: fit-content;
}

.method-badge {
  font-size: 12px;
  font-weight: 600;
  padding: 2px 10px;
  border-radius: 4px;
  width: fit-content;
}

/* 代码面板 */
.code-panel {
  background: #1e293b;
  border-radius: 10px;
  overflow: hidden;
}

.code-content {
  margin: 0;
  padding: 16px;
  color: #e2e8f0;
  font-family: 'Monaco', 'Menlo', 'Consolas', monospace;
  font-size: 12px;
  line-height: 1.6;
  overflow-x: auto;
  white-space: pre-wrap;
  word-break: break-word;
  max-height: 300px;
  overflow-y: auto;
}

/* 底部按钮 */
.modal-footer-custom {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 24px;
  padding-top: 20px;
  border-top: 1px solid rgba(226, 232, 240, 0.6);
}

:global(html.dark) .modal-footer-custom {
  border-top-color: rgba(51, 65, 85, 0.6);
}

.glass-btn {
  padding: 8px 24px;
  border-radius: 8px;
  font-weight: 500;
}

.danger-glass-btn {
  padding: 8px 24px;
  border-radius: 8px;
  font-weight: 500;
}

/* 筛选区时间范围选择器 */
.filter-item.wide {
  grid-column: span 2;
}

.elegant-picker {
  width: 100%;
}

:global(.elegant-picker .ant-picker-input > input) {
  font-size: 13px;
}
</style>
