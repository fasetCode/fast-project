<template>
  <div class="slowquery-container">

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">SQL内容</span>
            <a-input 
              v-model:value="queryParams.sqlContent" 
              placeholder="搜索SQL..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><CodeOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">MD5摘要</span>
            <a-input 
              v-model:value="queryParams.sqlMd5" 
              placeholder="搜索MD5..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><FileTextOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">严重程度</span>
            <a-select 
              v-model:value="queryParams.severity" 
              placeholder="选择程度" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option value="警告">警告 (200-500ms)</a-select-option>
              <a-select-option value="严重">严重 (500-1000ms)</a-select-option>
              <a-select-option value="严重警告">严重警告 (>1000ms)</a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">业务模块</span>
            <a-input 
              v-model:value="queryParams.module" 
              placeholder="输入模块..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><AppstoreOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">处理状态</span>
            <a-select 
              v-model:value="queryParams.status" 
              placeholder="选择状态" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option value="需要处理">需要处理</a-select-option>
              <a-select-option value="不需要处理">不需要处理</a-select-option>
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
            <div class="tab active">慢查询日志 <span class="badge">{{ pagination.total }}</span></div>
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
            :scroll="{ x: 1200 }"
            @change="handleTableChange"
            class="elegant-table"
          >
            <template #bodyCell="{ column, record }">
              <!-- SQL内容列 -->
              <template v-if="column.key === 'sqlContent'">
                <a-tooltip :title="record.sqlContent">
                  <div class="sql-content-cell">
                    {{ record.sqlContent }}
                  </div>
                </a-tooltip>
              </template>

              <!-- 耗时列 -->
              <template v-else-if="column.key === 'executionTime'">
                <div class="time-cost" :class="getTimeCostClass(record.executionTime)">
                  <ClockCircleOutlined />
                  <span>{{ record.executionTime }}ms</span>
                </div>
              </template>

              <!-- 触发次数列 -->
              <template v-else-if="column.key === 'triggerCount'">
                <a-badge :count="record.triggerCount" :overflow-count="9999" :number-style="{ backgroundColor: '#6366f1' }" />
              </template>

              <!-- 状态列 -->
              <template v-else-if="column.key === 'status'">
                <a-tag :color="record.status === '需要处理' ? 'orange' : 'green'">
                  {{ record.status }}
                </a-tag>
              </template>

              <!-- 严重程度列 -->
              <template v-else-if="column.key === 'severity'">
                <a-tag :color="getSeverityColor(record.severity)" class="log-tag">
                  {{ record.severity }}
                </a-tag>
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

    <!-- 详情弹窗 -->
    <a-modal
      v-model:open="detailModalVisible"
      :title="null"
      :footer="null"
      width="850px"
      :destroy-on-close="true"
      class="professional-modal"
      centered
      @cancel="handleDetailCancel"
    >
      <div class="modal-content-wrapper" v-if="currentRecord">
        <!-- 头部：核心指标 -->
        <div class="modal-premium-header" :class="getSeverityIconClass(currentRecord.severity)">
          <div class="header-main">
            <div class="header-icon-box">
              <WarningFilled v-if="currentRecord.severity === '警告'" />
              <AlertFilled v-else />
            </div>
            <div class="header-info">
              <div class="header-title-row">
                <span class="main-title">慢查询详细分析</span>
                <a-tag :color="getSeverityColor(currentRecord.severity)" class="severity-pill">
                  {{ currentRecord.severity }}
                </a-tag>
              </div>
              <div class="header-subtitle">
                <CalendarOutlined /> {{ formatDateTime(currentRecord.createTime) }}
              </div>
            </div>
          </div>
          <div class="header-stats">
            <div class="stat-item trigger-stat">
              <span class="stat-label">触发次数</span>
              <span class="stat-value">{{ currentRecord.triggerCount }}</span>
            </div>
            <div class="stat-item" :class="getTimeCostClass(currentRecord.executionTime)">
              <span class="stat-label">最近耗时</span>
              <span class="stat-value">{{ currentRecord.executionTime }}<small>ms</small></span>
            </div>
          </div>
        </div>

        <div class="modal-body-scroll">
          <!-- 信息网格 -->
          <div class="info-grid-section">
            <div class="grid-card">
              <div class="grid-card-item">
                <span class="item-label">业务模块</span>
                <span class="item-value"><a-tag color="blue">{{ currentRecord.module || 'JPA' }}</a-tag></span>
              </div>
              <div class="grid-card-item">
                <span class="item-label">记录ID</span>
                <span class="item-value monospace">{{ currentRecord.id }}</span>
              </div>
              <div class="grid-card-item">
                <span class="item-label">MD5 摘要</span>
                <span class="item-value monospace md5">{{ currentRecord.sqlMd5 }}</span>
              </div>
              <div class="grid-card-item">
                <span class="item-label">处理状态</span>
                <span class="item-value">
                  <a-tag :color="currentRecord.status === '需要处理' ? 'orange' : 'green'">{{ currentRecord.status }}</a-tag>
                </span>
              </div>
              <div class="grid-card-item full-width">
                <span class="item-label">备注说明</span>
                <span class="item-value">{{ currentRecord.remark || '暂无备注' }}</span>
              </div>
            </div>
          </div>

          <!-- SQL 区域 -->
          <div class="sql-section">
            <div class="section-top">
              <div class="section-title">
                <CodeOutlined /> 完整 SQL 语句
              </div>
              <a-button type="link" size="small" class="copy-link" @click="copySql(currentRecord.sqlContent)">
                <template #icon>
                  <CheckOutlined v-if="copied" />
                  <CopyOutlined v-else />
                </template>
                {{ copied ? '已复制' : '复制 SQL' }}
              </a-button>
            </div>
            <div class="sql-code-container">
              <pre class="sql-viewer"><code>{{ currentRecord.sqlContent }}</code></pre>
            </div>
          </div>
        </div>

        <div class="modal-footer-pro">
          <div class="footer-left">
            <span class="tip-text" v-if="currentRecord.executionTime > 1000">建议优化：此查询已超过 1s，可能存在索引缺失或全表扫描。</span>
          </div>
          <div class="footer-actions">
            <a-button class="pro-btn-secondary" @click="handleDetailCancel">取消</a-button>
            <a-button v-if="canDelete" danger type="primary" class="pro-btn-danger" @click="handleDetailDelete">
              <DeleteOutlined /> 删除记录
            </a-button>
          </div>
        </div>
      </div>
    </a-modal>

    <!-- 编辑弹窗 -->
    <a-modal
      v-model:open="editModalVisible"
      title="编辑慢查询标识"
      @ok="submitEdit"
      centered
      class="glass-modal"
    >
      <div class="glass-form mt-4">
        <a-form layout="vertical">
          <a-form-item label="处理状态">
            <div class="custom-radio-group">
              <div 
                class="radio-item" 
                :class="{ active: editForm.status === '需要处理' }" 
                @click="editForm.status = '需要处理'"
              >需要处理</div>
              <div 
                class="radio-item" 
                :class="{ active: editForm.status === '不需要处理' }" 
                @click="editForm.status = '不需要处理'"
              >不需要处理</div>
            </div>
          </a-form-item>
          <a-form-item label="备注说明">
            <a-textarea 
              v-model:value="editForm.remark" 
              placeholder="请输入备注..." 
              :rows="4" 
              class="elegant-input"
              style="border: 1px solid #e2e8f0 !important; padding: 8px !important; border-radius: 8px !important;"
            />
          </a-form-item>
        </a-form>
      </div>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, h } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  SearchOutlined, ReloadOutlined, DeleteOutlined, EyeOutlined,
  CodeOutlined, ClockCircleOutlined, CalendarOutlined, LoadingOutlined,
  AppstoreOutlined, FileTextOutlined, WarningFilled, AlertFilled,
  CopyOutlined, CheckOutlined, EditOutlined
} from '@ant-design/icons-vue'
import { getSlowQueryLogPage, deleteSlowQueryLog, updateSlowQueryLog } from '@/api/system/slowquery'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'

const permissionStore = usePermissionStore()

// 按钮权限控制
const canDelete = ref(false)
const canUpdate = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canDelete.value = hasButtonPermission('monitor:slow-query:delete')
  canUpdate.value = hasButtonPermission('monitor:slow-query:update')
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
  { title: 'SQL内容', dataIndex: 'sqlContent', key: 'sqlContent', ellipsis: true },
  { title: '耗时', dataIndex: 'executionTime', key: 'executionTime', width: 120, sorter: true },
  { title: '触发次数', dataIndex: 'triggerCount', key: 'triggerCount', width: 100, align: 'center' },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120, align: 'center' },
  { title: '程度', dataIndex: 'severity', key: 'severity', width: 120 },
  { title: '模块', dataIndex: 'module', key: 'module', width: 100 },
  { title: '最后记录', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'operation', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const dataSource = ref<any[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive({
  page: 1,
  pageSize: 10,
  sqlContent: '',
  sqlMd5: '',
  severity: undefined,
  module: '',
  status: undefined,
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
const currentRecord = ref<any | null>(null)
const copied = ref(false)

// 编辑弹窗
const editModalVisible = ref(false)
const editForm = reactive({
  id: undefined,
  status: '需要处理',
  remark: '',
})

const handleEdit = (record: any) => {
  editForm.id = record.id
  editForm.status = record.status || '需要处理'
  editForm.remark = record.remark || ''
  editModalVisible.value = true
}

const submitEdit = async () => {
  const res = await updateSlowQueryLog(editForm)
  if (res.code === 200) {
    message.success('修改成功')
    editModalVisible.value = false
    loadData()
  }
}

const copySql = async (text: string) => {
  try {
    await navigator.clipboard.writeText(text)
    copied.value = true
    message.success('SQL 已复制到剪贴板')
    setTimeout(() => {
      copied.value = false
    }, 2000)
  } catch (err) {
    message.error('复制失败')
  }
}

// 获取耗时样式类
const getTimeCostClass = (time: number) => {
  if (time >= 1000) return 'slow-danger'
  if (time >= 500) return 'slow-warning'
  return 'slow-info'
}

// 获取严重程度颜色
const getSeverityColor = (severity: string) => {
  switch (severity) {
    case '严重警告': return '#7f1d1d'
    case '严重': return '#ef4444'
    case '警告': return '#f59e0b'
    default: return 'blue'
  }
}

// 获取详情图标类
const getSeverityIconClass = (severity: string) => {
  switch (severity) {
    case '严重警告': return 'severe-icon'
    case '严重': return 'critical-icon'
    case '警告': return 'warning-icon'
    default: return ''
  }
}

// 格式化日期时间
const formatDateTime = (date: string | undefined) => {
  if (!date) return '-'
  const d = new Date(date)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getSlowQueryLogPage(params)
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
  queryParams.sqlContent = ''
  queryParams.sqlMd5 = ''
  queryParams.severity = undefined
  queryParams.module = ''
  queryParams.status = undefined
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleView = (record: any) => {
  currentRecord.value = record
  detailModalVisible.value = true
}

const handleDeleteOne = (record: any) => {
  Modal.confirm({
    title: '删除确认',
    content: `确定删除该慢查询记录吗？`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteSlowQueryLog([record.id!])
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
  handleDeleteOne(currentRecord.value)
  detailModalVisible.value = false
}

const handleDelete = () => {
  if (selectedRowKeys.value.length === 0) return
  Modal.confirm({
    title: '批量删除确认',
    content: `将删除选中的 ${selectedRowKeys.value.length} 条记录，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteSlowQueryLog(selectedRowKeys.value)
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

.sql-content-cell {
  max-width: 400px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
  color: #475569;
}

.time-cost {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  font-weight: 600;
}

.slow-info { color: #f59e0b; }
.slow-warning { color: #ef4444; }
.slow-danger { color: #7f1d1d; }

.log-tag {
  font-size: 12px;
  padding: 2px 10px;
  border-radius: 4px;
  color: white;
  border: none;
}

/* 详情弹窗专业化重塑 */
:deep(.professional-modal) .ant-modal-content {
  border-radius: 16px;
  padding: 0;
  overflow: hidden;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
  border: 1px solid #e2e8f0;
}

.modal-content-wrapper {
  display: flex;
  flex-direction: column;
  background: #fff;
}

.modal-premium-header {
  padding: 24px 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #f1f5f9;
}

.modal-premium-header.warning-icon { border-top: 4px solid #f59e0b; }
.modal-premium-header.critical-icon { border-top: 4px solid #ef4444; }
.modal-premium-header.severe-icon { border-top: 4px solid #991b1b; }

.header-main {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header-icon-box {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.warning-icon .header-icon-box { background: #fffbeb; color: #f59e0b; }
.critical-icon .header-icon-box { background: #fef2f2; color: #ef4444; }
.severe-icon .header-icon-box { background: #fef2f2; color: #991b1b; }

.header-info {
  display: flex;
  flex-direction: column;
}

.header-title-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

.main-title {
  font-size: 18px;
  font-weight: 700;
  color: #0f172a;
}

.severity-pill {
  border-radius: 20px;
  padding: 0 12px;
  font-weight: 600;
  border: none;
}

.header-subtitle {
  font-size: 13px;
  color: #64748b;
  margin-top: 4px;
}

.header-stats {
  display: flex;
  gap: 16px;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  padding: 8px 16px;
  border-radius: 12px;
  background: #f8fafc;
}

.stat-label {
  font-size: 12px;
  color: #94a3b8;
  font-weight: 500;
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  line-height: 1.2;
}

.stat-value small {
  font-size: 14px;
  margin-left: 2px;
}

.slow-info .stat-value { color: #f59e0b; }
.slow-warning .stat-value { color: #ef4444; }
.slow-danger .stat-value { color: #7f1d1d; }

.modal-body-scroll {
  padding: 24px 32px;
  max-height: 60vh;
  overflow-y: auto;
}

.info-grid-section {
  margin-bottom: 24px;
}

.grid-card {
  background: #f8fafc;
  border-radius: 12px;
  padding: 20px;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  border: 1px solid #f1f5f9;
}

.grid-card-item {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.grid-card-item.full-width {
  grid-column: span 3;
}

.item-label {
  font-size: 12px;
  font-weight: 600;
  color: #94a3b8;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.item-value {
  font-size: 14px;
  color: #334155;
  font-weight: 500;
}

.item-value.monospace {
  font-family: 'JetBrains Mono', 'Fira Code', 'Monaco', monospace;
  font-size: 13px;
}

.item-value.md5 {
  color: #64748b;
  word-break: break-all;
}

.sql-section {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.section-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.section-title {
  font-size: 15px;
  font-weight: 700;
  color: #334155;
  display: flex;
  align-items: center;
  gap: 8px;
}

.copy-link {
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 4px;
}

.sql-code-container {
  background: #1e293b;
  border-radius: 12px;
  padding: 20px;
  border: 1px solid #334155;
  box-shadow: inset 0 2px 4px rgba(0,0,0,0.1);
}

.sql-viewer {
  margin: 0;
  font-family: 'JetBrains Mono', 'Fira Code', 'Monaco', monospace;
  font-size: 13px;
  line-height: 1.6;
  color: #38bdf8;
  white-space: pre-wrap;
  word-break: break-all;
}

.modal-footer-pro {
  padding: 20px 32px;
  background: #f8fafc;
  border-top: 1px solid #f1f5f9;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.tip-text {
  font-size: 13px;
  color: #ef4444;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 6px;
}

.footer-actions {
  display: flex;
  gap: 12px;
}

.pro-btn-secondary {
  border-radius: 8px;
  height: 38px;
  padding: 0 20px;
  font-weight: 600;
  border-color: #e2e8f0;
}

.pro-btn-danger {
  border-radius: 8px;
  height: 38px;
  padding: 0 20px;
  font-weight: 600;
  box-shadow: 0 4px 6px -1px rgba(239, 68, 68, 0.2);
}

/* 暗黑模式适配 */
html.dark :deep(.professional-modal) .ant-modal-content {
  background: #1e293b;
  border-color: #334155;
}

html.dark .modal-premium-header {
  border-bottom-color: #334155;
}

html.dark .main-title { color: #f1f5f9; }
html.dark .stat-item { background: #0f172a; }
html.dark .grid-card { background: #0f172a; border-color: #334155; }
html.dark .item-value { color: #cbd5e1; }
html.dark .section-title { color: #e2e8f0; }
html.dark .modal-footer-pro { background: #0f172a; border-top-color: #334155; }
html.dark .pro-btn-secondary { background: #1e293b; border-color: #334155; color: #cbd5e1; }
</style>
