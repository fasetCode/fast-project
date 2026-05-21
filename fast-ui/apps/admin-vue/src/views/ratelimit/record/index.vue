<template>
  <div class="record-container">
    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">应用编码</span>
            <a-input 
              v-model:value="queryParams.appCode" 
              placeholder="应用编码..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><AppstoreOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">限流Key</span>
            <a-input 
              v-model:value="queryParams.limitKey" 
              placeholder="限流Key..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><KeyOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">限流类型</span>
            <a-select 
              v-model:value="queryParams.limitType" 
              placeholder="选择类型" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option value="IP">IP限流</a-select-option>
              <a-select-option value="USER">用户限流</a-select-option>
              <a-select-option value="API">API限流</a-select-option>
              <a-select-option value="GLOBAL">全局限流</a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">目标值</span>
            <a-input 
              v-model:value="queryParams.targetValue" 
              placeholder="目标值..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><UserOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">请求地址</span>
            <a-input 
              v-model:value="queryParams.url" 
              placeholder="请求地址..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><LinkOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">请求IP</span>
            <a-input 
              v-model:value="queryParams.ip" 
              placeholder="请求IP..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><GlobalOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">用户ID</span>
            <a-input-number 
              v-model:value="queryParams.userId" 
              placeholder="用户ID" 
              class="elegant-input-number"
              style="width: 100%"
            />
          </div>
          <div class="filter-item wide">
            <span class="filter-label">触发时间</span>
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
            <div class="tab active">限流记录 <span class="badge">{{ pagination.total }}</span></div>
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
            :scroll="{ x: 1300 }"
            @change="handleTableChange"
            class="elegant-table"
          >
            <template #bodyCell="{ column, record }">
              <!-- 应用编码列 -->
              <template v-if="column.key === 'appCode'">
                <a-tag color="cyan">{{ record.appCode }}</a-tag>
              </template>

              <!-- 限流Key列 -->
              <template v-else-if="column.key === 'limitKey'">
                <div class="limit-key-cell">
                  <a-tooltip :title="record.limitKey">
                    <span class="limit-key-text">{{ record.limitKey }}</span>
                  </a-tooltip>
                </div>
              </template>

              <!-- 限流类型列 -->
              <template v-else-if="column.key === 'limitType'">
                <a-tag :color="getLimitTypeColor(record.limitType)">{{ getLimitTypeName(record.limitType) }}</a-tag>
              </template>

              <!-- 请求方法列 -->
              <template v-else-if="column.key === 'method'">
                <a-tag :color="record.method === 'GET' ? 'green' : 'blue'">{{ record.method }}</a-tag>
              </template>

              <!-- 请求地址列 -->
              <template v-else-if="column.key === 'url'">
                <a-tooltip :title="record.url">
                  <span class="text-code url-text">{{ record.url }}</span>
                </a-tooltip>
              </template>

              <!-- 请求IP列 -->
              <template v-else-if="column.key === 'ip'">
                <span class="text-code">{{ record.ip }}</span>
              </template>

              <!-- 用户ID列 -->
              <template v-else-if="column.key === 'userId'">
                <span v-if="record.userId" class="text-code">{{ record.userId }}</span>
                <span v-else class="text-secondary">-</span>
              </template>

              <!-- 触发时间列 -->
              <template v-else-if="column.key === 'createTime'">
                <div class="window-time">
                  <ClockCircleOutlined />
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
      class="elegant-detail-modal"
      centered
      @cancel="handleDetailCancel"
    >
      <div class="detail-modal-content" v-if="currentRecord">
        <!-- 头部装饰 -->
        <div class="detail-header" :class="getLimitTypeColor(currentRecord.limitType)">
          <div class="header-main">
            <div class="status-badge">
              <StopOutlined />
              <span>INTERCEPTED</span>
            </div>
            <h2 class="title">限流拦截详情</h2>
            <div class="meta-tags">
              <a-tag color="rgba(255,255,255,0.2)" class="glass-tag">{{ currentRecord.appCode }}</a-tag>
              <a-tag color="rgba(255,255,255,0.2)" class="glass-tag">{{ getLimitTypeName(currentRecord.limitType) }}</a-tag>
            </div>
          </div>
          <div class="header-id">#{{ currentRecord.id }}</div>
        </div>

        <div class="detail-body">
          <!-- 核心指标 -->
          <div class="info-section">
            <div class="section-title">
              <InfoCircleOutlined /> 基础信息
            </div>
            <a-descriptions :column="2" bordered size="small" class="custom-descriptions">
              <a-descriptions-item label="限流Key" :span="2">
                <span class="mono-text primary">{{ currentRecord.limitKey }}</span>
              </a-descriptions-item>
              <a-descriptions-item label="请求地址" :span="2">
                <a-tag :color="currentRecord.method === 'GET' ? 'green' : 'blue'" class="method-tag">{{ currentRecord.method }}</a-tag>
                <span class="mono-text">{{ currentRecord.url }}</span>
              </a-descriptions-item>
              <a-descriptions-item label="请求来源 IP">
                <span class="mono-text">{{ currentRecord.ip }}</span>
              </a-descriptions-item>
              <a-descriptions-item label="用户 ID">
                <span class="mono-text">{{ currentRecord.userId || '未登录' }}</span>
              </a-descriptions-item>
              <a-descriptions-item label="拦截时间">
                <span class="mono-text">{{ formatDateTime(currentRecord.createTime) }}</span>
              </a-descriptions-item>
              <a-descriptions-item label="限流原因">
                <span class="text-danger">{{ currentRecord.limitReason || '触发阈值限制' }}</span>
              </a-descriptions-item>
            </a-descriptions>
          </div>

          <!-- 请求报文 -->
          <div class="info-section">
            <div class="section-title">
              <CodeOutlined /> 报文详情
            </div>
            <a-tabs v-model:activeKey="activeTab" class="custom-tabs">
              <a-tab-pane key="headers" tab="Headers">
                <div class="code-container">
                  <div class="code-header">
                    <span class="dot red"></span>
                    <span class="dot yellow"></span>
                    <span class="dot green"></span>
                    <span class="lang">JSON</span>
                  </div>
                  <pre class="code-block">{{ formatJson(currentRecord.headers) }}</pre>
                </div>
              </a-tab-pane>
              <a-tab-pane key="params" tab="Query Params">
                <div class="code-container">
                  <div class="code-header">
                    <span class="dot red"></span>
                    <span class="dot yellow"></span>
                    <span class="dot green"></span>
                    <span class="lang">URL PARAMS</span>
                  </div>
                  <div class="params-list" v-if="currentRecord.queryParams">
                    <div v-for="(val, key) in parseQueryParams(currentRecord.queryParams)" :key="key" class="param-item">
                      <span class="p-key">{{ key }}:</span>
                      <span class="p-val">{{ val }}</span>
                    </div>
                  </div>
                  <div class="empty-params" v-else>无查询参数</div>
                </div>
              </a-tab-pane>
            </a-tabs>
          </div>
        </div>

        <div class="detail-footer">
          <a-button @click="handleDetailCancel" class="close-btn">关闭</a-button>
          <a-button v-if="canDelete" danger type="primary" @click="handleDetailDelete" class="delete-btn">
            <DeleteOutlined /> 删除记录
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
  FileTextOutlined, ClockCircleOutlined, CheckCircleOutlined,
  LoadingOutlined, KeyOutlined, UserOutlined, StopOutlined,
  AppstoreOutlined, LinkOutlined, GlobalOutlined, InfoCircleOutlined,
  CodeOutlined
} from '@ant-design/icons-vue'
import type { RateLimitRecordVo, RateLimitRecordQuery } from '@/api/ratelimit/record'
import { 
  getRateLimitRecordPage, deleteRateLimitRecord, batchDeleteRateLimitRecord, getRateLimitRecordById 
} from '@/api/ratelimit/record'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'

const permissionStore = usePermissionStore()

// 按钮权限控制
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canDelete.value = hasButtonPermission('admin:ratelimit:record:delete')
}

// 自定义表格加载指示器
const customLoadingIndicator = h(LoadingOutlined, {
  style: {
    fontSize: '32px',
    color: '#6366f1',
  },
  spin: true,
})

// 获取限流类型颜色
const getLimitTypeColor = (type: string | undefined) => {
  const colors: Record<string, string> = {
    'IP': 'blue',
    'USER': 'purple',
    'API': 'orange',
    'GLOBAL': 'red',
  }
  return colors[type || ''] || 'default'
}

// 获取限流类型名称
const getLimitTypeName = (type: string | undefined) => {
  const names: Record<string, string> = {
    'IP': 'IP限流',
    'USER': '用户限流',
    'API': 'API限流',
    'GLOBAL': '全局限流',
  }
  return names[type || ''] || '未知'
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

// 表格列定义
const columns = [
  { title: '应用', dataIndex: 'appCode', key: 'appCode', width: 120 },
  { title: '限流Key', dataIndex: 'limitKey', key: 'limitKey', width: 200 },
  { title: '限流类型', dataIndex: 'limitType', key: 'limitType', width: 100 },
  { title: '请求方法', dataIndex: 'method', key: 'method', width: 90 },
  { title: '请求地址', dataIndex: 'url', key: 'url', width: 250 },
  { title: '请求IP', dataIndex: 'ip', key: 'ip', width: 130 },
  { title: '用户ID', dataIndex: 'userId', key: 'userId', width: 120 },
  { title: '触发时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'operation', width: 100, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const dataSource = ref<RateLimitRecordVo[]>([])
const selectedRowKeys = ref<number[]>([])
const dateRange = ref<[Dayjs, Dayjs] | null>(null)

const queryParams = reactive<RateLimitRecordQuery>({
  page: 0,
  pageSize: 10,
  appCode: '',
  limitKey: '',
  limitType: undefined,
  targetValue: '',
  url: '',
  ip: '',
  userId: undefined,
  createTimeBegin: undefined,
  createTimeEnd: undefined,
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
const currentRecord = ref<RateLimitRecordVo | null>(null)
const activeTab = ref('headers')

// 格式化 JSON
const formatJson = (jsonStr: string | undefined) => {
  if (!jsonStr) return '无'
  try {
    const obj = JSON.parse(jsonStr)
    return JSON.stringify(obj, null, 2)
  } catch (e) {
    return jsonStr
  }
}

// 解析 Query Params
const parseQueryParams = (queryStr: string | undefined) => {
  if (!queryStr) return {}
  const params: Record<string, string> = {}
  const pairs = queryStr.split('&')
  for (const pair of pairs) {
    const [key, val] = pair.split('=')
    if (key) {
      params[decodeURIComponent(key)] = decodeURIComponent(val || '')
    }
  }
  return params
}

// 日期范围变化
const handleDateChange = (dates: [Dayjs, Dayjs] | null) => {
  if (dates) {
    queryParams.createTimeBegin = dates[0].format('YYYY-MM-DD HH:mm:ss')
    queryParams.createTimeEnd = dates[1].format('YYYY-MM-DD HH:mm:ss')
  } else {
    queryParams.createTimeBegin = undefined
    queryParams.createTimeEnd = undefined
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
    const res = await getRateLimitRecordPage(params)
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
  queryParams.limitKey = ''
  queryParams.limitType = undefined
  queryParams.targetValue = ''
  queryParams.url = ''
  queryParams.ip = ''
  queryParams.userId = undefined
  queryParams.createTimeBegin = undefined
  queryParams.createTimeEnd = undefined
  dateRange.value = null
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleView = async (record: RateLimitRecordVo) => {
  try {
    const res = await getRateLimitRecordById(record.id!)
    if (res.code === 200) {
      currentRecord.value = res.data
      detailModalVisible.value = true
    }
  } catch (error) {
    console.error('获取详情失败:', error)
  }
}

const handleDeleteOne = (record: RateLimitRecordVo) => {
  Modal.confirm({
    title: '删除确认',
    content: `确定删除该限流记录吗？`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteRateLimitRecord(record.id!)
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
    content: '确定删除该限流记录吗？此操作无法撤销。',
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteRateLimitRecord(currentRecord.value!.id!)
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
    content: `将删除选中的 ${selectedRowKeys.value.length} 条限流记录，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteRateLimitRecord(selectedRowKeys.value)
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

.limit-key-cell {
  max-width: 200px;
}

.limit-key-text {
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

.url-text {
  max-width: 250px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: inline-block;
}

.json-content {
  background: rgba(15, 23, 42, 0.05);
  border-radius: 8px;
  padding: 12px;
  margin-top: 8px;
  border: 1px solid rgba(148, 163, 184, 0.1);
}

.json-content pre {
  margin: 0;
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
  color: #334155;
  white-space: pre-wrap;
  word-break: break-all;
}

.text-error {
  color: #ef4444;
  font-weight: 500;
}

.text-secondary {
  color: #94a3b8;
}

.text-code {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 13px;
  color: #475569;
}

.window-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.window-time {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 12px;
  color: #64748b;
}

/* 详情弹窗样式升级 */
:deep(.elegant-detail-modal) {
  .ant-modal-content {
    padding: 0;
    overflow: hidden;
    border-radius: 16px;
    box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25);
  }
}

.detail-modal-content {
  display: flex;
  flex-direction: column;
}

.detail-header {
  padding: 30px 40px;
  color: white;
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  background-size: 200% 200%;
  animation: gradient 15s ease infinite;

  &.blue { background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%); }
  &.purple { background: linear-gradient(135deg, #8b5cf6 0%, #6d28d9 100%); }
  &.orange { background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%); }
  &.red { background: linear-gradient(135deg, #ef4444 0%, #b91c1c 100%); }
  &.default { background: linear-gradient(135deg, #64748b 0%, #334155 100%); }

  .header-main {
    .status-badge {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      background: rgba(0, 0, 0, 0.2);
      padding: 4px 10px;
      border-radius: 20px;
      font-size: 10px;
      font-weight: 800;
      letter-spacing: 1px;
      margin-bottom: 12px;
    }
    .title {
      color: white;
      margin: 0 0 12px 0;
      font-size: 24px;
      font-weight: 700;
    }
    .meta-tags {
      display: flex;
      gap: 8px;
      .glass-tag {
        border: none;
        backdrop-filter: blur(4px);
        font-weight: 500;
      }
    }
  }

  .header-id {
    font-family: 'Monaco', monospace;
    font-size: 20px;
    opacity: 0.3;
    font-weight: 900;
  }
}

.detail-body {
  padding: 30px 40px;
  background: #fcfcfd;

  .info-section {
    margin-bottom: 30px;
    &:last-child { margin-bottom: 0; }

    .section-title {
      font-size: 15px;
      font-weight: 600;
      color: #1e293b;
      margin-bottom: 16px;
      display: flex;
      align-items: center;
      gap: 8px;
      
      .anticon {
        color: #6366f1;
      }
    }
  }
}

.custom-descriptions {
  background: white;
  border-radius: 12px;
  overflow: hidden;

  :deep(.ant-descriptions-item-label) {
    background: #f8fafc;
    width: 140px;
    color: #64748b;
    font-weight: 500;
  }
  :deep(.ant-descriptions-item-content) {
    color: #1e293b;
  }
}

.mono-text {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 13px;
  word-break: break-all;
  &.primary { color: #6366f1; font-weight: 600; }
}

.method-tag {
  font-weight: 800;
  border: none;
  font-size: 11px;
  padding: 0 8px;
}

.text-danger { color: #ef4444; font-weight: 600; }

.custom-tabs {
  :deep(.ant-tabs-nav) {
    margin-bottom: 12px;
    &::before { border-bottom: 1px solid #e2e8f0; }
  }
}

.code-container {
  background: #1e293b;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);

  .code-header {
    background: #0f172a;
    padding: 10px 16px;
    display: flex;
    align-items: center;
    gap: 6px;

    .dot {
      width: 10px;
      height: 10px;
      border-radius: 50%;
      &.red { background: #ff5f56; }
      &.yellow { background: #ffbd2e; }
      &.green { background: #27c93f; }
    }
    .lang {
      margin-left: auto;
      color: #64748b;
      font-size: 10px;
      font-weight: 700;
      letter-spacing: 1px;
    }
  }

  .code-block {
    margin: 0;
    padding: 20px;
    color: #e2e8f0;
    font-family: 'Monaco', 'Menlo', monospace;
    font-size: 13px;
    line-height: 1.6;
    max-height: 300px;
    overflow-y: auto;
    white-space: pre-wrap;
    word-break: break-all;

    &::-webkit-scrollbar { width: 6px; }
    &::-webkit-scrollbar-thumb { background: #475569; border-radius: 3px; }
  }

  .params-list {
    padding: 15px 20px;
    display: flex;
    flex-direction: column;
    gap: 8px;
    max-height: 300px;
    overflow-y: auto;

    .param-item {
      display: flex;
      gap: 10px;
      font-size: 13px;
      .p-key { color: #94a3b8; font-weight: 600; min-width: 80px; }
      .p-val { color: #f1f5f9; font-family: monospace; word-break: break-all; }
    }
  }

  .empty-params {
    padding: 40px;
    text-align: center;
    color: #64748b;
    font-style: italic;
  }
}

.detail-footer {
  padding: 20px 40px;
  background: white;
  border-top: 1px solid #f1f5f9;
  display: flex;
  justify-content: flex-end;
  gap: 12px;

  .close-btn {
    border-radius: 8px;
    height: 38px;
    padding: 0 24px;
    font-weight: 500;
  }
  .delete-btn {
    border-radius: 8px;
    height: 38px;
    padding: 0 20px;
    font-weight: 600;
    box-shadow: 0 4px 6px -1px rgba(239, 68, 68, 0.2);
  }
}

@keyframes gradient {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}
</style>
