<template>
  <div class="onlineuser-container">
    <!-- 统计卡片区域 -->
    <div class="stats-bar">
      <div class="stat-card">
        <div class="stat-icon online">
          <UserOutlined />
        </div>
        <div class="stat-info">
          <span class="stat-label">在线会话</span>
          <span class="stat-value">{{ statistics.onlineCount || 0 }}</span>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon unique">
          <TeamOutlined />
        </div>
        <div class="stat-info">
          <span class="stat-label">在线用户</span>
          <span class="stat-value">{{ statistics.uniqueUserCount || 0 }}</span>
        </div>
      </div>
      <div class="stat-card refresh" @click="refreshStatistics">
        <div class="stat-icon refresh-icon">
          <ReloadOutlined :spin="statsLoading" />
        </div>
        <div class="stat-info">
          <span class="stat-label">刷新统计</span>
          <span class="stat-value">点击刷新</span>
        </div>
      </div>
    </div>

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
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
            <span class="filter-label">昵称</span>
            <a-input 
              v-model:value="queryParams.nickname" 
              placeholder="输入昵称..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><IdcardOutlined class="text-secondary" /></template>
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
            <div class="tab active">在线用户 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button 
            v-if="canKickout"
            danger 
            class="pill-btn danger-pill" 
            :class="{ 'is-disabled': selectedRowKeys.length === 0 }"
            :disabled="selectedRowKeys.length === 0" 
            @click="handleBatchKickout()"
          >
            <LogoutOutlined /> 批量踢出
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
            row-key="token"
            size="middle"
            :scroll="{ x: 1200 }"
            @change="handleTableChange"
            class="elegant-table"
          >
            <template #bodyCell="{ column, record }">
              <!-- 用户名列 -->
              <template v-if="column.key === 'username'">
                <div class="user-profile">
                  <div class="avatar-box" :style="{ background: getAvatarColor(record.userId) }">
                    {{ record.username?.charAt(0)?.toUpperCase() || '?' }}
                  </div>
                  <div class="user-info">
                    <span class="user-name">{{ record.username }}</span>
                    <span class="user-id">ID: {{ record.userId }}</span>
                  </div>
                </div>
              </template>

              <!-- 昵称列 -->
              <template v-else-if="column.key === 'nickname'">
                <span class="text-strong">{{ record.nickname || '--' }}</span>
              </template>

              <!-- 联系方式列 -->
              <template v-else-if="column.key === 'contact'">
                <div class="contact-info">
                  <div class="contact-item" v-if="record.phone">
                    <MobileOutlined class="text-secondary" /> {{ record.phone }}
                  </div>
                  <div class="contact-item" v-if="record.email">
                    <MailOutlined class="text-secondary" /> {{ record.email }}
                  </div>
                  <span class="text-muted" v-if="!record.phone && !record.email">暂无联系方式</span>
                </div>
              </template>

              <!-- IP列 -->
              <template v-else-if="column.key === 'ip'">
                <div class="ip-info">
                  <GlobalOutlined />
                  <span>{{ record.ip || '-' }}</span>
                </div>
              </template>

              <!-- 地址列 -->
              <template v-else-if="column.key === 'address'">
                <a-tag v-if="record.address" color="blue" size="small">{{ record.address }}</a-tag>
                <span v-else class="text-muted">-</span>
              </template>

              <!-- 浏览器列 -->
              <template v-else-if="column.key === 'browser'">
                <div class="browser-info">
                  <span v-if="record.browser" class="browser-tag">{{ record.browser }}</span>
                  <span v-else class="text-muted">-</span>
                </div>
              </template>

              <!-- 设备列 -->
              <template v-else-if="column.key === 'device'">
                <div class="device-info">
                  <span v-if="record.device" class="device-tag">{{ record.device }}</span>
                  <span v-else class="text-muted">-</span>
                </div>
              </template>

              <!-- 登录时间列 -->
              <template v-else-if="column.key === 'loginTime'">
                <div class="time-cell">
                  <CalendarOutlined />
                  <span>{{ formatDateTime(record.loginTime) }}</span>
                </div>
              </template>

              <!-- 剩余时间列 -->
              <template v-else-if="column.key === 'expireTime'">
                <div class="expire-time" :class="getExpireTimeClass(record.expireTime)">
                  <ClockCircleOutlined />
                  <span>{{ formatExpireTime(record.expireTime) }}</span>
                </div>
              </template>

              <!-- 操作列 -->
              <template v-else-if="column.key === 'action'">
                <div class="action-group">
                  <div v-if="canKickout" class="action-btn kickout" @click="handleKickout(record)" title="强制下线">
                    <LogoutOutlined />
                  </div>
                </div>
              </template>
            </template>
          </a-table>
        </a-skeleton>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, h } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  SearchOutlined, ReloadOutlined, UserOutlined, IdcardOutlined,
  MobileOutlined, MailOutlined, CalendarOutlined, ClockCircleOutlined,
  LogoutOutlined, TeamOutlined, LoadingOutlined, GlobalOutlined
} from '@ant-design/icons-vue'
import type { OnlineUserVo, OnlineStatisticsVo } from '@/api/monitor/onlineuser.ts'
import { getOnlineUserPage, getOnlineUserStatistics, kickoutOnlineUser, batchKickoutOnlineUser } from '@/api/monitor/onlineuser.ts'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { useAppStore } from '@/stores/modules/app'

const appStore = useAppStore()
const permissionStore = usePermissionStore()

// 按钮权限控制
const canKickout = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canKickout.value = hasButtonPermission('admin:monitor:online:kickout')
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
  { title: '用户名', dataIndex: 'username', key: 'username', width: 160 },
  { title: '昵称', dataIndex: 'nickname', key: 'nickname', width: 100 },
  { title: '联系方式', key: 'contact', width: 180 },
  { title: '登录IP', dataIndex: 'ip', key: 'ip', width: 120 },
  { title: '登录地点', dataIndex: 'address', key: 'address', width: 100 },
  { title: '浏览器', dataIndex: 'browser', key: 'browser', width: 120 },
  { title: '设备', dataIndex: 'device', key: 'device', width: 100 },
  { title: '登录时间', dataIndex: 'loginTime', key: 'loginTime', width: 150 },
  { title: '剩余有效期', dataIndex: 'expireTime', key: 'expireTime', width: 100 },
  { title: '操作', key: 'action', width: 80, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const statsLoading = ref(false)
const dataSource = ref<OnlineUserVo[]>([])
const selectedRowKeys = ref<string[]>([])
const statistics = reactive<OnlineStatisticsVo>({
  onlineCount: 0,
  uniqueUserCount: 0,
})

const queryParams = reactive({
  page: 0,
  pageSize: 10,
  username: '',
  nickname: '',
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条数据`,
})

// 头像颜色
const colors = [appStore.themeColor, '#10b981', '#8b5cf6', '#ec4899', '#ef4444', '#f59e0b', '#eab308', '#06b6d4']
const getAvatarColor = (id: number | undefined) => {
  if (!id) return colors[0]
  return colors[id % colors.length]
}

// 格式化日期时间
const formatDateTime = (timestamp: number | undefined) => {
  if (!timestamp) return '-'
  const d = new Date(timestamp)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const hour = String(d.getHours()).padStart(2, '0')
  const minute = String(d.getMinutes()).padStart(2, '0')
  const second = String(d.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hour}:${minute}:${second}`
}

// 格式化剩余时间
const formatExpireTime = (seconds: number | undefined) => {
  if (!seconds || seconds <= 0) return '已过期'
  const hours = Math.floor(seconds / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  if (hours > 0) {
    return `${hours}小时${minutes}分`
  }
  return `${minutes}分钟`
}

// 获取剩余时间样式
const getExpireTimeClass = (seconds: number | undefined) => {
  if (!seconds || seconds <= 0) return 'expired'
  if (seconds < 300) return 'warning' // 小于5分钟
  return 'normal'
}

// 加载统计数据
const loadStatistics = async () => {
  statsLoading.value = true
  try {
    const res = await getOnlineUserStatistics()
    if (res.code === 200 && res.data) {
      statistics.onlineCount = res.data.onlineCount
      statistics.uniqueUserCount = res.data.uniqueUserCount
    }
  } finally {
    statsLoading.value = false
  }
}

// 刷新统计
const refreshStatistics = () => {
  loadStatistics()
  loadData()
  message.success('刷新成功')
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getOnlineUserPage(params)
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
  queryParams.username = ''
  queryParams.nickname = ''
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: string[]) => {
  selectedRowKeys.value = keys
}

// 踢出单个用户
const handleKickout = (record: OnlineUserVo) => {
  Modal.confirm({
    title: '强制下线确认',
    content: `确定要将用户「${record.username}」强制下线吗？`,
    okText: '确认踢出',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      if (!record.token) return
      const res = await kickoutOnlineUser(record.token)
      if (res.code === 200) {
        message.success('用户已被强制下线')
        loadData()
        loadStatistics()
      }
    },
  })
}

// 批量踢出
const handleBatchKickout = () => {
  if (selectedRowKeys.value.length === 0) return
  Modal.confirm({
    title: '批量踢出确认',
    content: `确定要将选中的 ${selectedRowKeys.value.length} 个用户强制下线吗？`,
    okText: '全部踢出',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchKickoutOnlineUser(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量踢出成功')
        selectedRowKeys.value = []
        loadData()
        loadStatistics()
      }
    },
  })
}

onMounted(() => {
  loadData()
  loadStatistics()
  loadButtonPermissions()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

/* 统计卡片 */
.stats-bar {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
  margin-bottom: 20px;
}

.stat-card {
  background: rgba(255, 255, 255, 0.8);
  border-radius: 16px;
  padding: 20px 24px;
  display: flex;
  align-items: center;
  gap: 16px;
  border: 1px solid rgba(226, 232, 240, 0.6);
  transition: all 0.3s ease;
}

:global(html.dark) .stat-card {
  background: rgba(30, 41, 59, 0.6);
  border-color: rgba(51, 65, 85, 0.6);
}

.stat-card.refresh {
  cursor: pointer;
}

.stat-card.refresh:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(99, 102, 241, 0.15);
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.stat-icon.online {
  background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%);
  color: white;
}

.stat-icon.unique {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: white;
}

.stat-icon.refresh-icon {
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
  color: white;
}

.stat-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-label {
  font-size: 13px;
  color: #64748b;
  font-weight: 500;
}

:global(html.dark) .stat-label {
  color: #94a3b8;
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
}

:global(html.dark) .stat-value {
  color: #f1f5f9;
}

/* 用户资料 */
.user-profile {
  display: flex;
  align-items: center;
  gap: 12px;
}

.avatar-box {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 600;
  color: white;
}

.user-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.user-name {
  font-size: 14px;
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

/* 联系方式 */
.contact-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.contact-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 12px;
  color: #475569;
}

:global(html.dark) .contact-item {
  color: #cbd5e1;
}

/* 时间显示 */
.time-cell {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #64748b;
}

:global(html.dark) .time-cell {
  color: #94a3b8;
}

/* 剩余时间 */
.expire-time {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  font-weight: 500;
}

.expire-time.normal {
  color: #10b981;
}

.expire-time.warning {
  color: #f59e0b;
}

.expire-time.expired {
  color: #ef4444;
}

/* 操作按钮 */
.action-btn.kickout {
  background: rgba(239, 68, 68, 0.1);
  color: #ef4444;
}

.action-btn.kickout:hover {
  background: #ef4444;
  color: white;
}

/* IP 信息 */
.ip-info {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #0ea5e9;
  font-family: 'Monaco', 'Menlo', monospace;
}

/* 浏览器标签 */
.browser-tag {
  display: inline-flex;
  align-items: center;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
  background: rgba(99, 102, 241, 0.1);
  color: #6366f1;
}

/* 设备标签 */
.device-tag {
  display: inline-flex;
  align-items: center;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
  background: rgba(16, 185, 129, 0.1);
  color: #10b981;
}
</style>
