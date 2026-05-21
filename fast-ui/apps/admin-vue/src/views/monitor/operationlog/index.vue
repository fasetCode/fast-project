<template>
  <div class="operationlog-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">日志描述</span>
            <a-input 
              v-model:value="queryParams.description" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><FileTextOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">日志类型</span>
            <a-select 
              v-model:value="queryParams.type" 
              placeholder="选择类型" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option v-for="(label, value) in LogTypeLabel" :key="value" :value="value">
                {{ label }}
              </a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">操作方法</span>
            <a-select 
              v-model:value="queryParams.action" 
              placeholder="选择操作" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option v-for="(label, value) in LogActionLabel" :key="value" :value="value">
                {{ label }}
              </a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">IP地址</span>
            <a-input 
              v-model:value="queryParams.ip" 
              placeholder="输入IP..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><GlobalOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">请求URL</span>
            <a-input 
              v-model:value="queryParams.url" 
              placeholder="输入URL..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><LinkOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">执行状态</span>
            <a-select 
              v-model:value="queryParams.success" 
              placeholder="全部状态" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option :value="true">
                <div class="status-option">
                  <span class="dot dot-success"></span> 成功
                </div>
              </a-select-option>
              <a-select-option :value="false">
                <div class="status-option">
                  <span class="dot dot-error"></span> 失败
                </div>
              </a-select-option>
            </a-select>
          </div>
          <div class="filter-item wide">
            <span class="filter-label">时间范围</span>
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
            <div class="tab active">全部操作日志 <span class="badge">{{ pagination.total }}</span></div>
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
            :scroll="{ x: 1400 }"
            @change="handleTableChange"
            class="elegant-table"
          >
            <template #bodyCell="{ column, record }">
              <!-- 日志描述列 -->
              <template v-if="column.key === 'description'">
                <div class="log-desc">
                  <span class="desc-text">{{ record.description || '-' }}</span>
                </div>
              </template>

              <!-- 日志类型列 -->
              <template v-else-if="column.key === 'type'">
                <a-tag :color="LogTypeColor[record.type]" class="log-tag">
                  {{ LogTypeLabel[record.type] || record.type }}
                </a-tag>
              </template>

              <!-- 操作方法列 -->
              <template v-else-if="column.key === 'action'">
                <a-tag :color="LogActionColor[record.action]" class="log-tag">
                  {{ LogActionLabel[record.action] || record.action }}
                </a-tag>
              </template>

              <!-- 请求URL列 -->
              <template v-else-if="column.key === 'url'">
                <div class="url-wrap">
                  <a-tag :color="HttpMethodColor[record.httpMethod] || 'default'" size="small" class="method-tag">
                    {{ record.httpMethod }}
                  </a-tag>
                  <a-tooltip :title="record.url">
                    <span class="url-text">{{ record.url }}</span>
                  </a-tooltip>
                </div>
              </template>

              <!-- 类名方法名列 -->
              <template v-else-if="column.key === 'classMethod'">
                <div class="class-method">
                  <div class="class-name">{{ record.className?.split('.').pop() }}</div>
                  <div class="method-name">{{ record.methodName }}</div>
                </div>
              </template>

              <!-- 执行时间列 -->
              <template v-else-if="column.key === 'timeCost'">
                <div class="time-cost" :class="getTimeCostClass(record.timeCost)">
                  <ClockCircleOutlined />
                  <span>{{ record.timeCost }}ms</span>
                </div>
              </template>

              <!-- 状态列 -->
              <template v-else-if="column.key === 'success'">
                <div class="status-tag" :class="record.success ? 'success' : 'error'">
                  <CheckCircleFilled v-if="record.success" />
                  <CloseCircleFilled v-else />
                  <span>{{ record.success ? '成功' : '失败' }}</span>
                </div>
              </template>

              <!-- 创建时间列 -->
              <template v-else-if="column.key === 'createTime'">
                <div class="time-cell">
                  <CalendarOutlined />
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
          <div class="modal-icon" :class="currentRecord.success ? 'success-icon' : 'error-icon'">
            <CheckCircleFilled v-if="currentRecord.success" />
            <CloseCircleFilled v-else />
          </div>
          <div class="modal-titles">
            <h2>操作日志详情</h2>
            <p>
              <a-tag :color="LogTypeColor[currentRecord.type]" class="header-tag">
                {{ LogTypeLabel[currentRecord.type] }}
              </a-tag>
              <a-tag :color="LogActionColor[currentRecord.action]" class="header-tag">
                {{ LogActionLabel[currentRecord.action] }}
              </a-tag>
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
            <div class="glass-item">
              <span class="glass-label">日志ID</span>
              <span class="glass-value id-value">{{ currentRecord.id }}</span>
            </div>
            <div class="glass-item">
              <span class="glass-label">执行状态</span>
              <div class="status-badge" :class="currentRecord.success ? 'success' : 'error'">
                <CheckCircleFilled v-if="currentRecord.success" />
                <CloseCircleFilled v-else />
                <span>{{ currentRecord.success ? '执行成功' : '执行失败' }}</span>
              </div>
            </div>
            <div class="glass-item wide">
              <span class="glass-label">日志描述</span>
              <span class="glass-value desc">{{ currentRecord.description || '-' }}</span>
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
              <a-tag :color="HttpMethodColor[currentRecord.httpMethod] || 'default'" class="method-badge">
                {{ currentRecord.httpMethod }}
              </a-tag>
            </div>
            <div class="glass-item">
              <span class="glass-label">执行耗时</span>
              <span class="glass-value time" :class="getTimeCostClass(currentRecord.timeCost)">
                <ClockCircleOutlined /> {{ currentRecord.timeCost }}ms
              </span>
            </div>
            <div class="glass-item wide">
              <span class="glass-label">请求URL</span>
              <span class="glass-value url">{{ currentRecord.url }}</span>
            </div>
            <div class="glass-item wide">
              <span class="glass-label">类名</span>
              <span class="glass-value code">{{ currentRecord.className }}</span>
            </div>
            <div class="glass-item">
              <span class="glass-label">方法名</span>
              <span class="glass-value code">{{ currentRecord.methodName }}</span>
            </div>
            <div class="glass-item">
              <span class="glass-label">IP地址</span>
              <span class="glass-value ip">
                <GlobalOutlined /> {{ currentRecord.ip }}
              </span>
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

        <!-- 响应结果 -->
        <div class="glass-section" v-if="currentRecord.responseData">
          <div class="section-header">
            <FileDoneOutlined class="section-icon" />
            <span class="section-name">响应结果</span>
          </div>
          <div class="code-panel">
            <pre class="code-content">{{ formatJson(currentRecord.responseData) }}</pre>
          </div>
        </div>

        <!-- 错误信息 -->
        <div class="glass-section error-section" v-if="currentRecord.errorMsg">
          <div class="section-header">
            <CloseCircleOutlined class="section-icon" />
            <span class="section-name">错误信息</span>
          </div>
          <div class="code-panel error">
            <pre class="code-content error">{{ currentRecord.errorMsg }}</pre>
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
              <span class="glass-label">创建人ID</span>
              <span class="glass-value">{{ currentRecord.createBy || '-' }}</span>
            </div>
            <div class="glass-item">
              <span class="glass-label">创建时间</span>
              <span class="glass-value time-full">
                <CalendarOutlined /> {{ formatDateTime(currentRecord.createTime) }}
              </span>
            </div>
          </div>
        </div>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handleDetailCancel">关闭</a-button>
          <a-button v-if="canDelete" danger class="danger-glass-btn" @click="handleDetailDelete">
            <DeleteOutlined /> 删除此日志
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
  CheckCircleFilled, CloseCircleFilled, CloseCircleOutlined, CalendarOutlined, LoadingOutlined,
  CodeOutlined, FileDoneOutlined
} from '@ant-design/icons-vue'
import type { OperationLogVo, OperationLogQuery } from '@/api/logs/operationlog.ts'
import { getOperationLogPage, deleteOperationLog, batchDeleteOperationLog, getOperationLogById } from '@/api/logs/operationlog.ts'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { LogType, LogAction, LogTypeLabel, LogActionLabel, LogTypeColor, LogActionColor, HttpMethodColor } from '@/enums/logs'

const permissionStore = usePermissionStore()

// 按钮权限控制
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canDelete.value = hasButtonPermission('admin:logs:operation:delete')
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
  { title: '日志描述', dataIndex: 'description', key: 'description', width: 180 },
  { title: '类型', dataIndex: 'type', key: 'type', width: 100 },
  { title: '操作', dataIndex: 'action', key: 'action', width: 80 },
  { title: '请求URL', dataIndex: 'url', key: 'url', width: 250 },
  { title: '类/方法', dataIndex: 'classMethod', key: 'classMethod', width: 150 },
  { title: '耗时', dataIndex: 'timeCost', key: 'timeCost', width: 90 },
  { title: '状态', dataIndex: 'success', key: 'success', width: 80 },
  { title: 'IP地址', dataIndex: 'ip', key: 'ip', width: 130 },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 160 },
  { title: '操作', key: 'operation', width: 100, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const dataSource = ref<OperationLogVo[]>([])
const selectedRowKeys = ref<number[]>([])
const dateRange = ref<[Dayjs, Dayjs] | null>(null)

const queryParams = reactive<OperationLogQuery>({
  page: 1,
  pageSize: 10,
  description: '',
  type: undefined,
  action: undefined,
  ip: '',
  url: '',
  success: undefined,
  startTime: undefined,
  endTime: undefined,
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
const currentRecord = ref<OperationLogVo | null>(null)

// 获取耗时样式类
const getTimeCostClass = (time: number | undefined) => {
  if (!time) return ''
  if (time < 100) return 'fast'
  if (time < 500) return 'normal'
  return 'slow'
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
    queryParams.startTime = formatDateTime(dates[0].toISOString())
    queryParams.endTime = formatDateTime(dates[1].toISOString())
  } else {
    queryParams.startTime = undefined
    queryParams.endTime = undefined
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
    const res = await getOperationLogPage(params)
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
  queryParams.description = ''
  queryParams.type = undefined
  queryParams.action = undefined
  queryParams.ip = ''
  queryParams.url = ''
  queryParams.success = undefined
  queryParams.startTime = undefined
  queryParams.endTime = undefined
  dateRange.value = null
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleView = async (record: OperationLogVo) => {
  try {
    const res = await getOperationLogById(record.id!)

    if (res.code === 200) {
      currentRecord.value = res.data
      detailModalVisible.value = true
    }
  } catch (error) {
    console.error('获取日志详情失败:', error)
  }
}

const handleDeleteOne = (record: OperationLogVo) => {
  Modal.confirm({
    title: '删除确认',
    content: `确定删除该操作日志吗？`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteOperationLog(record.id!)
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
    content: '确定删除该操作日志吗？此操作无法撤销。',
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteOperationLog(currentRecord.value!.id!)
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
    content: `将删除选中的 ${selectedRowKeys.value.length} 条操作日志，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteOperationLog(selectedRowKeys.value)
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

.log-tag {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 4px;
}

.method-tag {
  font-size: 11px;
  font-weight: 600;
  padding: 0 6px;
  height: 20px;
  line-height: 20px;
}

.url-wrap {
  display: flex;
  align-items: center;
  gap: 8px;
}

.url-text {
  display: inline-block;
  max-width: 180px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #64748b;
  font-size: 12px;
}

:global(html.dark) .url-text {
  color: #94a3b8;
}

.class-method {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.class-name {
  font-size: 12px;
  color: #64748b;
  font-weight: 500;
}

.method-name {
  font-size: 11px;
  color: #94a3b8;
  font-family: 'Monaco', 'Menlo', monospace;
}

.time-cost {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  font-weight: 500;
}

.time-cost.fast {
  color: #10b981;
}

.time-cost.normal {
  color: #f59e0b;
}

.time-cost.slow {
  color: #ef4444;
}

.status-tag {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  font-weight: 500;
}

.status-tag.success {
  color: #10b981;
}

.status-tag.error {
  color: #ef4444;
}

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
.detail-content {
  max-height: 600px;
  overflow-y: auto;
}

.detail-section {
  margin-bottom: 20px;
}

.section-title {
  font-size: 14px;
  font-weight: 600;
  color: #334155;
  margin-bottom: 12px;
  padding-bottom: 8px;
  border-bottom: 1px solid #e2e8f0;
}

:global(html.dark) .section-title {
  color: #e2e8f0;
  border-bottom-color: #334155;
}

.detail-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px 20px;
}

.detail-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.detail-item.wide {
  grid-column: span 2;
}

.detail-item .label {
  font-size: 12px;
  color: #64748b;
  white-space: nowrap;
}

:global(html.dark) .detail-item .label {
  color: #94a3b8;
}

.detail-item .value {
  font-size: 13px;
  color: #334155;
  font-weight: 500;
}

:global(html.dark) .detail-item .value {
  color: #e2e8f0;
}

.detail-item .value.url {
  font-size: 12px;
  color: #6366f1;
  word-break: break-all;
}

.detail-item .value.code {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
  background: #f1f5f9;
  padding: 2px 8px;
  border-radius: 4px;
}

:global(html.dark) .detail-item .value.code {
  background: #334155;
}

.code-block {
  background: #1e293b;
  color: #e2e8f0;
  padding: 12px;
  border-radius: 8px;
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
  line-height: 1.6;
  overflow-x: auto;
  white-space: pre-wrap;
  word-break: break-word;
  margin: 0;
}

.code-block.error {
  background: #fef2f2;
  color: #dc2626;
  border: 1px solid #fecaca;
}

:global(html.dark) .code-block.error {
  background: #451a1a;
  border-color: #7f1d1d;
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

.modal-icon.success-icon {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: white;
}

.modal-icon.error-icon {
  background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
  color: white;
}

.header-tag {
  font-size: 12px;
  padding: 2px 10px;
  border-radius: 4px;
  margin-right: 8px;
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

.glass-section.error-section {
  background: rgba(254, 242, 242, 0.8);
  border-color: rgba(254, 202, 202, 0.6);
}

:global(html.dark) .glass-section.error-section {
  background: rgba(69, 26, 26, 0.4);
  border-color: rgba(127, 29, 29, 0.4);
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

.glass-value.desc {
  font-size: 14px;
  line-height: 1.5;
}

.glass-value.url {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
  color: #6366f1;
  background: rgba(99, 102, 241, 0.08);
  padding: 6px 12px;
  border-radius: 6px;
}

.glass-value.code {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
  background: rgba(30, 41, 59, 0.05);
  padding: 4px 10px;
  border-radius: 6px;
  color: #475569;
}

:global(html.dark) .glass-value.code {
  background: rgba(30, 41, 59, 0.5);
  color: #cbd5e1;
}

.glass-value.ip {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #0ea5e9;
}

.glass-value.time {
  display: flex;
  align-items: center;
  gap: 6px;
}

.glass-value.time.fast {
  color: #10b981;
}

.glass-value.time.normal {
  color: #f59e0b;
}

.glass-value.time.slow {
  color: #ef4444;
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

/* 状态徽章 */
.status-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  width: fit-content;
}

.status-badge.success {
  background: rgba(16, 185, 129, 0.1);
  color: #059669;
}

.status-badge.error {
  background: rgba(239, 68, 68, 0.1);
  color: #dc2626;
}

:global(html.dark) .status-badge.success {
  background: rgba(16, 185, 129, 0.2);
  color: #34d399;
}

:global(html.dark) .status-badge.error {
  background: rgba(239, 68, 68, 0.2);
  color: #f87171;
}

/* 方法标签 */
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

.code-panel.error {
  background: #451a1a;
  border: 1px solid #7f1d1d;
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

.code-content.error {
  color: #fca5a5;
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
</style>
