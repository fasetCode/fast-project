<template>
  <div class="fileinfo-container">
    <a-row :gutter="24" class="main-layout-row">
      <!-- 左侧：文件列表区 -->
      <a-col :xs="24" :lg="18" class="left-content">
        <!-- 悬浮式高级搜索区 -->
        <div class="filter-bar modern-filter-bar">
          <a-form :model="queryParams" class="elegant-form">
            <div class="filter-grid">
              <div class="filter-item">
                <span class="filter-label">文件名</span>
                <a-input 
                  v-model:value="queryParams.fileName" 
                  placeholder="输入搜索..." 
                  class="elegant-input"
                  allow-clear
                >
                  <template #prefix><FileOutlined class="text-secondary" /></template>
                </a-input>
              </div>
              <div class="filter-item">
                <span class="filter-label">文件类型</span>
                <a-input 
                  v-model:value="queryParams.fileType" 
                  placeholder="如：jpg, png, pdf" 
                  class="elegant-input"
                  allow-clear
                >
                  <template #prefix><TagOutlined class="text-secondary" /></template>
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
                <a-button class="icon-btn refresh-btn" @click="reset()" shape="circle" :loading="loading" title="重置">
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
        <div class="data-board modern-data-board">
          <div class="board-toolbar">
            <div class="toolbar-left">
              <div class="board-tabs">
                <div class="tab active">全部文件 <span class="badge">{{ pagination.total }}</span></div>
              </div>
            </div>
            <div class="toolbar-right">
              <span class="selected-count" v-if="selectedRowKeys.length > 0">已选 {{ selectedRowKeys.length }} 项</span>
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

          <div class="file-grid-wrapper">
            <a-skeleton active :loading="loading && dataSource.length === 0" :paragraph="{ rows: 10 }">
              <div v-if="dataSource.length > 0" class="file-grid">
                <div v-for="record in dataSource" :key="record.id" class="file-item-card" :class="{ 'is-selected': selectedRowKeys.includes(record.id!) }">
                  <!-- 复选框 -->
                  <div class="file-checkbox">
                    <a-checkbox 
                      :checked="selectedRowKeys.includes(record.id!)"
                      @change="(e) => handleSingleSelect(record.id!, e.target.checked)"
                    />
                  </div>
                  
                  <!-- 操作按钮悬浮层 -->
                  <div class="file-actions-overlay">
                    <div class="action-btn-circle view" @click="handleDetail(record)" title="详情">
                      <EyeOutlined />
                    </div>
                    <div v-if="canDelete" class="action-btn-circle delete" @click="handleDeleteOne(record)" title="删除">
                      <DeleteOutlined />
                    </div>
                  </div>

                  <!-- 文件图标区 -->
                  <div class="file-icon-area" :style="{ background: getFileColor(record.fileType) + '10' }">
                    <!-- 如果是图片直接显示缩略图，否则显示图标 -->
                    <div
                      v-if="isImage(record.fileType) && getFileAccessUrl(record.id)"
                      class="file-image-preview"
                      :style="{ backgroundImage: `url(${getFileAccessUrl(record.id)})` }"
                    ></div>
                    <component v-else :is="getFileIcon(record.fileType)" :style="{ color: getFileColor(record.fileType) }" class="file-main-icon" />
                    
                    <!-- 状态小圆点 -->
                    <div class="file-status-dot" :class="record.status === 1 ? 'is-active' : 'is-disabled'" :title="record.status === 1 ? '已启用' : '已禁用'"></div>
                  </div>

                  <!-- 文件信息区 -->
                  <div class="file-info-area">
                    <div class="file-name" :title="record.fileName">{{ record.fileName }}</div>
                    <div class="file-meta">
                      <span class="file-type-tag" :style="{ color: getFileColor(record.fileType), borderColor: getFileColor(record.fileType) + '40', background: getFileColor(record.fileType) + '10' }">
                        {{ record.fileType?.toUpperCase() || '未知' }}
                      </span>
                      <span class="file-size">{{ formatFileSize(record.fileSize) }}</span>
                    </div>
                  </div>
                </div>
              </div>
              <a-empty v-else description="暂无文件数据" class="empty-data" />
              
              <!-- 分页器 -->
              <div class="grid-pagination" v-if="dataSource.length > 0">
                <a-pagination
                  v-model:current="pagination.current"
                  v-model:pageSize="pagination.pageSize"
                  :total="pagination.total"
                  :show-total="pagination.showTotal"
                  show-size-changer
                  @change="handleTableChange"
                  class="elegant-pagination"
                />
              </div>
            </a-skeleton>
          </div>
        </div>
      </a-col>

      <!-- 右侧：统计分析区 -->
      <a-col :xs="24" :lg="6" class="right-sidebar">
        <div class="stats-board" v-if="typeStats.length > 0">
          <div class="section-title">
            <PieChartOutlined class="title-icon" /> 存储空间分析
          </div>
          <div class="stats-list">
            <div v-for="(item, index) in typeStats" :key="index" class="stat-card modern-card-compact">
              <div class="stat-header-compact">
                <div class="stat-icon-wrapper-compact" :style="{ background: `linear-gradient(135deg, ${getFileColor(item.name)}20, ${getFileColor(item.name)}10)` }">
                  <component :is="getFileIcon(item.name)" :style="{ color: getFileColor(item.name) }" class="stat-icon-compact" />
                </div>
                <div class="stat-title-compact">{{ item.name ? item.name.toUpperCase() : '未知' }}</div>
              </div>
              
              <div class="stat-body-compact">
                <div class="stat-metrics-row-compact">
                  <div class="stat-metric-compact">
                    <span class="metric-label-compact">数量</span>
                    <div class="metric-value-wrapper-compact">
                      <span class="metric-value-compact">{{ item.platformRatio }}<span class="metric-unit-compact">%</span></span>
                    </div>
                  </div>
                  
                  <div class="stat-divider-vertical-compact"></div>
                  
                  <div class="stat-metric-compact">
                    <span class="metric-label-compact">空间</span>
                    <div class="metric-value-wrapper-compact">
                      <span class="metric-value-compact">
                        {{ item.fileSpace && item.fileSpace >= 1024 ? (item.fileSpace / 1024).toFixed(2) : item.fileSpace }}
                        <span class="metric-unit-compact">{{ item.fileSpace && item.fileSpace >= 1024 ? 'G' : 'M' }}</span>
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </a-col>
    </a-row>

    <!-- 详情弹窗 -->
    <a-modal
      v-model:open="modalVisible"
      :title="null"
      :footer="null"
      width="650px"
      :destroy-on-close="true"
      class="glass-modal"
      centered
      @cancel="handleCancel"
    >
      <div class="modal-inner">
        <div class="modal-header">
          <div class="modal-icon view-icon">
            <EyeOutlined />
          </div>
          <div class="modal-titles">
            <h2>文件详情</h2>
            <p>查看文件详细信息</p>
          </div>
        </div>

        <a-descriptions bordered :column="2" class="detail-descriptions">
          <a-descriptions-item label="文件名" :span="2">{{ formData.fileName }}</a-descriptions-item>
          <a-descriptions-item label="文件类型">
            <a-tag color="blue">{{ formData.fileType || '-' }}</a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="文件大小">
            <a-tag color="cyan">{{ formatFileSize(formData.fileSize) }}</a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="文件状态">
            <span :class="formData.status === 1 ? 'text-success' : 'text-error'">
              <component :is="formData.status === 1 ? CheckCircleFilled : MinusCircleFilled" />
              {{ getDictLabel('status', formData.status) || '未知' }}
            </span>
          </a-descriptions-item>
          <a-descriptions-item label="存储位置">
            <a-tag :color="formData.fileStorage === 'local' ? 'blue' : 'purple'">
              {{ formData.fileStorage === 'local' ? '本地' : '远程' }}
            </a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="文件MD5" :span="2">
            <span class="text-code">{{ formData.fileMd5 || '-' }}</span>
          </a-descriptions-item>
          <a-descriptions-item label="访问路径" :span="2">
            <a :href="getFileAccessUrl(formData.id)" target="_blank" v-if="getFileAccessUrl(formData.id)" class="text-link">
              {{ getFileAccessUrl(formData.id) }}
            </a>
            <span v-else>-</span>
          </a-descriptions-item>
        </a-descriptions>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handleCancel">关闭</a-button>
        </div>
      </div>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, h, computed } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  SearchOutlined, ReloadOutlined, DeleteOutlined, 
  FileOutlined, TagOutlined, CheckCircleFilled, 
  MinusCircleFilled, LoadingOutlined, EyeOutlined,
  SafetyOutlined, LinkOutlined, PictureOutlined, 
  FilePdfOutlined, FileExcelOutlined, FileWordOutlined,
  FileZipOutlined, FileTextOutlined, PieChartOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { FileInfoVo, FileInfoQuery, FileInfoCreate, FileInfoUpdate, FileTypeStat } from '@/api/file/fileinfo.ts'
import { 
  getFileInfoPage, updateFileInfo, 
  deleteFileInfo, batchDeleteFileInfo, getFileInfoById, getFileTypeStats
} from '@/api/file/fileinfo.ts'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'

const permissionStore = usePermissionStore()

const buildFileRedirectUrl = (id: string | number) => `${import.meta.env.VITE_API_BASE_URL}/file/getUrl/${id}`

const getFileAccessUrl = (id?: number) => {
  if (!id) return ''
  return buildFileRedirectUrl(id)
}

// 按钮权限控制
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canDelete.value = hasButtonPermission('admin:file:info:delete')
}

// 自定义表格加载指示器
const customLoadingIndicator = h(LoadingOutlined, {
  style: {
    fontSize: '32px',
    color: '#6366f1',
  },
  spin: true,
})

// 判断是否为图片类型
const isImage = (fileType: string | undefined) => {
  if (!fileType) return false
  const type = fileType.toLowerCase()
  return ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp', 'svg'].includes(type)
}

// 根据文件类型返回对应的图标
const getFileIcon = (fileType: string | undefined) => {
  if (!fileType) return FileTextOutlined
  const type = fileType.toLowerCase()
  if (['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp', 'svg'].includes(type)) return PictureOutlined
  if (['pdf'].includes(type)) return FilePdfOutlined
  if (['xls', 'xlsx', 'csv'].includes(type)) return FileExcelOutlined
  if (['doc', 'docx'].includes(type)) return FileWordOutlined
  if (['zip', 'rar', '7z', 'tar', 'gz'].includes(type)) return FileZipOutlined
  return FileTextOutlined
}

// 根据文件类型返回对应的颜色
const getFileColor = (fileType: string | undefined) => {
  if (!fileType) return '#6366f1'
  const type = fileType.toLowerCase()
  if (['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp', 'svg'].includes(type)) return '#ec4899'
  if (['pdf'].includes(type)) return '#ef4444'
  if (['xls', 'xlsx', 'csv'].includes(type)) return '#10b981'
  if (['doc', 'docx'].includes(type)) return '#3b82f6'
  if (['zip', 'rar', '7z', 'tar', 'gz'].includes(type)) return '#f59e0b'
  return '#6366f1'
}

// 格式化文件大小
const formatFileSize = (size: number | undefined) => {
  if (!size) return '0 B'
  const units = ['B', 'KB', 'MB', 'GB', 'TB']
  let index = 0
  let fileSize = size
  while (fileSize >= 1024 && index < units.length - 1) {
    fileSize /= 1024
    index++
  }
  return `${fileSize.toFixed(2)} ${units[index]}`
}

// 表格列定义
const columns = [
  { title: '文件名', dataIndex: 'fileName', key: 'fileName', width: 200 },
  { title: '文件大小', dataIndex: 'fileSize', key: 'fileSize', width: 100 },
  { title: '类型', dataIndex: 'fileType', key: 'fileType', width: 80 },
  { title: 'MD5', dataIndex: 'fileMd5', key: 'fileMd5', width: 120 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '存储位置', dataIndex: 'fileStorage', key: 'fileStorage', width: 100 },
  { title: '访问路径', dataIndex: 'accessPath', key: 'accessPath', width: 180 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const dataSource = ref<FileInfoVo[]>([])
const selectedRowKeys = ref<number[]>([])
const typeStats = ref<FileTypeStat[]>([])

const queryParams = reactive<FileInfoQuery>({
  page: 1,
  pageSize: 100,
  fileName: '',
  fileType: '',
  status: undefined,
  fileMd5: '',
})

const pagination = reactive({
  current: 1,
  pageSize: 100,
  total: 0,
  showSizeChanger: true,
  pageSizeOptions: ['20', '50', '100', '200'],
  showTotal: (total: number) => `共 ${total} 条数据`,
})

const modalVisible = ref(false)
const formData = reactive<FileInfoVo>({
  id: undefined,
  fileName: '',
  fileSize: 0,
  fileType: '',
  fileMd5: '',
  status: 1,
  fileStorage: 'local',
  accessPath: '',
})

const loadTypeStats = async () => {
  try {
    const res = await getFileTypeStats()
    if (res.code === 200) {
      typeStats.value = res.data || []
    }
  } catch (error) {
    console.error('获取文件类型统计失败:', error)
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
    const res = await getFileInfoPage(params)
    if (res.code === 200) {
      dataSource.value = res.data?.data || []
      pagination.total = res.data?.total || 0
    }
  } finally {
    loading.value = false
  }
}

const handleTableChange = (current: number, pageSize: number) => {
  pagination.current = current
  pagination.pageSize = pageSize
  loadData()
}

const reset = () => {
  queryParams.fileName = ''
  queryParams.fileType = ''
  queryParams.status = undefined
  queryParams.fileMd5 = ''
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleSingleSelect = (id: number, checked: boolean) => {
  if (checked) {
    selectedRowKeys.value.push(id)
  } else {
    selectedRowKeys.value = selectedRowKeys.value.filter(key => key !== id)
  }
}

const handleDetail = async (record: FileInfoVo) => {
  try {
    const res = await getFileInfoById(record.id!)
    if (res.code === 200) {
      Object.assign(formData, res.data)
    }
  } catch (error) {
    console.error('获取文件信息详情失败:', error)
  }
  modalVisible.value = true
}

const handleCancel = () => {
  modalVisible.value = false
}

const handleDeleteOne = (record: FileInfoVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除文件「${record.fileName}」，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteFileInfo(record.id!)
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
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个文件，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteFileInfo(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: FileInfoVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  const res = await updateFileInfo({ ...record, status: newStatus } as FileInfoUpdate)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '文件已启用' : '文件已禁用')
  }
}

onMounted(() => {
  loadData()
  loadTypeStats()
  loadButtonPermissions()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.text-link {
  color: #6366f1;
  font-size: 13px;
  cursor: pointer;
}

:global(html.dark) .text-link {
  color: #818cf8;
}

.main-layout-row {
  align-items: flex-start;
}

.left-content {
  display: flex;
  flex-direction: column;
}

.right-sidebar {
  position: sticky;
  top: 24px;
}

.stats-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  max-height: calc(100vh - 120px);
  overflow-y: auto;
  padding-right: 8px;
}

/* 隐藏滚动条但保留功能 */
.stats-list::-webkit-scrollbar {
  width: 6px;
}
.stats-list::-webkit-scrollbar-thumb {
  background: var(--border-color-split, #e5e7eb);
  border-radius: 3px;
}

/* 高级统计卡片样式 */
.stats-board {
  margin-bottom: 0;
  animation: slideDown 0.5s ease-out;
  background: var(--component-background, #ffffff);
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px -4px rgba(0, 0, 0, 0.03);
  border: 1px solid rgba(0, 0, 0, 0.04);
}

/* --- 文件网格视图样式 --- */
.file-grid-wrapper {
  padding: 16px 0;
}

.file-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
  gap: 16px;
  margin-bottom: 24px;
}

.file-item-card {
  background: var(--component-background, #ffffff);
  border-radius: 12px;
  border: 1px solid rgba(0, 0, 0, 0.04);
  box-shadow: 0 2px 12px -4px rgba(0, 0, 0, 0.03);
  overflow: hidden;
  position: relative;
  transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  display: flex;
  flex-direction: column;
}

.file-item-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px -8px rgba(99, 102, 241, 0.15), 0 0 0 1px rgba(99, 102, 241, 0.2);
}

.file-item-card.is-selected {
  border-color: #6366f1;
  box-shadow: 0 0 0 2px rgba(99, 102, 241, 0.2);
}

.file-checkbox {
  position: absolute;
  top: 12px;
  left: 12px;
  z-index: 10;
  opacity: 0;
  transform: scale(0.8);
  transition: all 0.3s ease;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 6px;
  padding: 2px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
  backdrop-filter: blur(4px);
}

.file-item-card:hover .file-checkbox,
.file-checkbox :deep(.ant-checkbox-checked),
.file-item-card.is-selected .file-checkbox {
  opacity: 1;
  transform: scale(1);
}

.file-actions-overlay {
  position: absolute;
  top: 12px;
  right: 12px;
  z-index: 10;
  display: flex;
  flex-direction: column;
  gap: 8px;
  opacity: 0;
  transform: translateX(10px);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.file-item-card:hover .file-actions-overlay {
  opacity: 1;
  transform: translateX(0);
}

.action-btn-circle {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(8px);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #4b5563;
  font-size: 15px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  border: 1px solid rgba(255,255,255,0.5);
  transition: all 0.25s cubic-bezier(0.34, 1.56, 0.64, 1);
}

.action-btn-circle:hover {
  transform: scale(1.15) rotate(5deg);
  background: #ffffff;
}

.action-btn-circle.view:hover { color: #6366f1; box-shadow: 0 4px 12px rgba(99,102,241,0.2); }
.action-btn-circle.delete:hover { color: #ef4444; box-shadow: 0 4px 12px rgba(239,68,68,0.2); }

.file-icon-area {
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.file-icon-area::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 40%;
  background: linear-gradient(to top, rgba(0,0,0,0.03), transparent);
  pointer-events: none;
}

.file-main-icon {
  font-size: 40px;
  transition: all 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  filter: drop-shadow(0 4px 8px rgba(0,0,0,0.1));
}

.file-item-card:hover .file-main-icon {
  transform: scale(1.15) translateY(-5px);
}

.file-image-preview {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  transition: transform 0.7s cubic-bezier(0.2, 0.8, 0.2, 1);
}

.file-item-card:hover .file-image-preview {
  transform: scale(1.08);
}

.file-status-dot {
  position: absolute;
  bottom: 16px;
  right: 16px;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  border: 2px solid #fff;
  box-shadow: 0 2px 6px rgba(0,0,0,0.15);
  z-index: 5;
}

.file-status-dot.is-active { background-color: #10b981; }
.file-status-dot.is-disabled { background-color: #ef4444; }

.file-info-area {
  padding: 10px 12px;
  background: var(--component-background, #ffffff);
  border-top: 1px solid rgba(0,0,0,0.03);
  position: relative;
  z-index: 2;
}

.file-name {
  font-size: 13px;
  font-weight: 500;
  color: var(--text-color, #1f2937);
  margin-bottom: 6px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  letter-spacing: 0.2px;
}

.file-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.file-type-tag {
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 4px;
  border: 1px solid transparent;
  font-weight: 600;
  letter-spacing: 0.5px;
  box-shadow: 0 1px 2px rgba(0,0,0,0.02);
}

.file-size {
  font-size: 11px;
  font-weight: 500;
  color: var(--text-color-secondary, #6b7280);
  background: var(--component-background-secondary, #f3f4f6);
  padding: 2px 6px;
  border-radius: 4px;
}

.empty-data {
  padding: 80px 0;
}

.grid-pagination {
  display: flex;
  justify-content: center;
  padding-top: 24px;
  margin-bottom: 24px;
}

:global(html.dark) .file-item-card {
  border-color: #303030;
}

:global(html.dark) .file-info-area {
  border-top-color: rgba(255,255,255,0.05);
}

:global(html.dark) .action-btn-circle {
  background: rgba(30, 30, 30, 0.8);
  color: #aaa;
}

:global(html.dark) .file-status-dot {
  border-color: #1f1f1f;
}

/* 隐藏原有表格相关样式变量 */

.modern-filter-bar {
  background: var(--component-background, #ffffff);
  border-radius: 16px;
  padding: 20px 24px;
  box-shadow: 0 4px 20px -4px rgba(0, 0, 0, 0.03);
  border: 1px solid rgba(0, 0, 0, 0.04);
  margin-bottom: 24px;
}

.modern-data-board {
  background: var(--component-background, #ffffff);
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px -4px rgba(0, 0, 0, 0.03);
  border: 1px solid rgba(0, 0, 0, 0.04);
}

.toolbar-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.selected-count {
  font-size: 13px;
  color: #6366f1;
  background: rgba(99, 102, 241, 0.1);
  padding: 4px 12px;
  border-radius: 12px;
  font-weight: 500;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(5px); }
  to { opacity: 1; transform: translateY(0); }
}

:global(html.dark) .modern-filter-bar,
:global(html.dark) .modern-data-board,
:global(html.dark) .stats-board {
  background: #1f2937;
  border-color: rgba(255, 255, 255, 0.05);
  box-shadow: 0 4px 20px -4px rgba(0, 0, 0, 0.2);
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-color, #1f2937);
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.title-icon {
  color: #6366f1;
  font-size: 20px;
}

.modern-card-compact {
  background: var(--component-background-secondary, #fafafa);
  border-radius: 8px;
  padding: 8px 12px;
  box-shadow: none;
  border: 1px solid rgba(0, 0, 0, 0.03);
  transition: all 0.2s ease;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.modern-card-compact:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.stat-header-compact {
  display: flex;
  align-items: center;
  gap: 8px;
  min-width: 80px;
}

.stat-icon-wrapper-compact {
  width: 24px;
  height: 24px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-icon-compact {
  font-size: 12px;
}

.stat-title-compact {
  font-size: 13px;
  font-weight: 600;
  color: var(--text-color, #111827);
}

.stat-body-compact {
  flex: 1;
}

.stat-metrics-row-compact {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 12px;
}

.stat-metric-compact {
  display: flex;
  align-items: center;
  gap: 4px;
}

.metric-label-compact {
  font-size: 12px;
  color: var(--text-color-secondary, #6b7280);
}

.metric-value-wrapper-compact {
  display: flex;
  align-items: baseline;
}

.metric-value-compact {
  font-size: 14px;
  font-weight: 700;
  color: var(--text-color, #111827);
  font-family: 'Inter', sans-serif;
}

.metric-unit-compact {
  font-size: 10px;
  color: var(--text-color-secondary, #9ca3af);
  margin-left: 2px;
}

.stat-divider-vertical-compact {
  width: 1px;
  height: 12px;
  background: var(--border-color-split, #e5e7eb);
}

@keyframes slideDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

:global(html.dark) .modern-card-compact {
  background: #1f2937;
  border-color: rgba(255, 255, 255, 0.05);
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.2);
}

:global(html.dark) .stat-divider-vertical-compact {
  background: #4b5563;
}

.detail-descriptions {
  margin-top: 16px;
  margin-bottom: 24px;
}

:deep(.ant-descriptions-item-label) {
  width: 120px;
  text-align: right;
  color: var(--text-color-secondary, #6b7280);
}
</style>
