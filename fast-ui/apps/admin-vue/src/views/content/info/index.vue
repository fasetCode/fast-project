<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">标题</span>
            <a-input
              v-model:value="queryParams.title"
              class="elegant-input"
              allow-clear
              placeholder="请输入标题"
            />
          </div>
          <div class="filter-item">
            <span class="filter-label">发布人</span>
            <UserPicker v-model:value="queryParams.authorId" placeholder="请选择发布人" />
          </div>
          <div class="filter-item">
            <span class="filter-label">审核人</span>
            <UserPicker v-model:value="queryParams.auditBy" placeholder="请选择审核人" />
          </div>
          <div class="filter-item">
            <span class="filter-label">分类</span>
            <a-tree-select
              v-model:value="queryParams.categoryIds"
              :tree-data="categoryTreeData"
              :field-names="{ label: 'title', value: 'value', children: 'children' }"
              multiple
              allow-clear
              tree-default-expand-all
              class="elegant-select"
              placeholder="选择分类"
            />
          </div>
          <div class="filter-item">
            <span class="filter-label">标签</span>
            <a-select v-model:value="queryParams.tagIds" mode="multiple" allow-clear class="elegant-select" placeholder="选择标签">
              <a-select-option v-for="item in tagSelectOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
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

    <div class="data-board">
      <div class="board-toolbar">
        <div class="toolbar-left">
          <div class="board-tabs">
            <div class="tab active">全部内容 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增内容
          </a-button>
          <a-button danger class="pill-btn danger-pill" :disabled="selectedRowKeys.length === 0" @click="handleDelete()">
            <DeleteOutlined /> 批量删除
          </a-button>
        </div>
      </div>

      <div class="table-wrapper">
        <a-table
          :columns="columns"
          :data-source="dataSource"
          :loading="loading"
          :pagination="pagination"
          :row-selection="{ selectedRowKeys, onChange: onSelectChange }"
          row-key="id"
          size="middle"
          :scroll="{ x: 1280 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'title'">
              <div class="content-cell">
                <div class="content-cover">
                  <a-image
                    v-if="record.cover"
                    :src="resolveImage(record.cover)"
                    :width="56"
                    :height="56"
                    class="cover-image"
                  />
                  <div v-else class="cover-placeholder">无封面</div>
                </div>
                <div class="content-main">
                  <div class="title-main">{{ record.title || '-' }}</div>
                  <div class="content-meta">
                    <span>作者：{{ record.authorName || '-' }}</span>
                    <span>ID：{{ record.id || '-' }}</span>
                  </div>
                </div>
              </div>
            </template>
            <template v-else-if="column.key === 'taxonomy'">
              <div class="info-stack">
                <div class="info-block">
                  <span class="info-label">分类</span>
                  <div v-if="getCategoryNames(record.categoryIds).length" class="tag-group">
                    <a-tag v-for="name in getCategoryNames(record.categoryIds)" :key="name" class="info-tag">
                      {{ name }}
                    </a-tag>
                  </div>
                  <span v-else class="text-secondary">-</span>
                </div>
                <div class="info-block">
                  <span class="info-label">标签</span>
                  <div v-if="getTagNames(record.tagIds).length" class="tag-group">
                    <a-tag v-for="t in getTagNames(record.tagIds)" :key="t" class="info-tag info-tag--light">
                      {{ t }}
                    </a-tag>
                  </div>
                  <span v-else class="text-secondary">-</span>
                </div>
              </div>
            </template>
            <template v-else-if="column.key === 'workflow'">
              <div class="status-stack">
                <div class="status-line">
                  <span class="info-label">状态</span>
                  <a-tag :color="record.status === 1 ? 'green' : 'red'" class="status-tag">
                    {{ getDictLabel('status', record.status) }}
                  </a-tag>
                </div>
                <div class="status-line">
                  <span class="info-label">发布</span>
                  <a-tag color="blue" class="status-tag">{{ getDictLabel('content_publish_status', record.publishStatus) }}</a-tag>
                </div>
                <div class="status-line">
                  <span class="info-label">审核</span>
                  <a-tag color="purple" class="status-tag">{{ getDictLabel('content_audit_status', record.auditStatus) }}</a-tag>
                </div>
              </div>
            </template>
            <template v-else-if="column.key === 'metrics'">
              <div class="metrics-stack">
                <div class="metric-card">
                  <span class="metric-value">{{ record.viewCount ?? 0 }}</span>
                  <span class="metric-label">浏览</span>
                </div>
                <div class="metric-card">
                  <span class="metric-value">{{ record.commentCount ?? 0 }}</span>
                  <span class="metric-label">评论</span>
                </div>
              </div>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <AddOrUpdate ref="addOrUpdateRef" @success="loadData" />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { message, Modal } from 'ant-design-vue'
import { SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons-vue'
import type { ContentInfoVo, ContentInfoQuery } from '@/api/content/contentinfo'
import { getContentInfoPage, deleteContentInfo, batchDeleteContentInfo } from '@/api/content/contentinfo'
import type { ContentCategoryTreeVo } from '@/api/content/contentcategory'
import { getContentCategoryTree } from '@/api/content/contentcategory'
import type { ContentTagVo } from '@/api/content/contenttag'
import { getContentTagSelectAll } from '@/api/content/contenttag'
import { getFileUrl } from '@/api/file/fileupload'
import { getDictLabel } from '@/utils/dict.ts'
import AddOrUpdate from './AddOrUpdate.vue'
import type { AddOrUpdateRef } from './AddOrUpdate.vue'
import UserPicker from '@/components/UserPicker/index.vue'

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 90 },
  { title: '内容信息', dataIndex: 'title', key: 'title', width: 380 },
  { title: '分类与标签', key: 'taxonomy', width: 300 },
  { title: '状态信息', key: 'workflow', width: 220 },
  { title: '数据概览', key: 'metrics', width: 160, align: 'center' as const },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const dataSource = ref<ContentInfoVo[]>([])
const selectedRowKeys = ref<number[]>([])
const addOrUpdateRef = ref<AddOrUpdateRef>()

const resolveImage = (val: any) => {
  if (!val) return ''
  const str = String(val)
  if (str.startsWith('http') || str.startsWith('/')) return str
  return getFileUrl(val)
}

const queryParams = reactive<ContentInfoQuery>({
  page: 1,
  pageSize: 10,
  title: '',
  authorId: undefined,
  auditBy: undefined,
  categoryIds: [],
  tagIds: [],
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条数据`,
})

const categoryTree = ref<ContentCategoryTreeVo[]>([])

type TreeSelectNode = { title: string; value: string; children?: TreeSelectNode[] }

const buildCategoryTreeSelect = (nodes: ContentCategoryTreeVo[] | undefined): TreeSelectNode[] => {
  if (!nodes?.length) return []
  return nodes.map((node) => ({
    title: node.name || `${node.id}`,
    value: String(node.id),
    children: buildCategoryTreeSelect(node.children),
  }))
}

const categoryTreeData = computed<TreeSelectNode[]>(() => buildCategoryTreeSelect(categoryTree.value))

const tagList = ref<ContentTagVo[]>([])

const tagSelectOptions = computed(() =>
  (tagList.value || [])
    .filter((t) => t && t.id !== undefined && t.id !== null)
    .map((t) => ({ value: Number(t.id), label: t.name || String(t.id) }))
)

const toIdString = (value: any): string | undefined => {
  if (value === null || value === undefined || value === '') return undefined
  if (typeof value === 'string') return value
  if (typeof value === 'number' || typeof value === 'bigint') return String(value)
  if (typeof value === 'object' && typeof value.toString === 'function') {
    const s = value.toString()
    if (s && s !== '[object Object]') return s
  }
  return String(value)
}

const normalizeCategoryTree = (nodes: any[] | undefined): ContentCategoryTreeVo[] => {
  if (!nodes?.length) return []
  return nodes.map((n) => ({
    ...n,
    id: toIdString(n.id),
    parentId: toIdString(n.parentId),
    children: normalizeCategoryTree(n.children),
  }))
}

const normalizeCategoryIds = (val: any): number[] => {
  if (!Array.isArray(val)) return []
  return val
    .map((item) => Number(item))
    .filter((item) => Number.isFinite(item))
}

const getCategoryNames = (categoryIds: any): string[] => {
  const ids = normalizeCategoryIds(categoryIds)
  if (!ids.length) return []
  return ids.map((id) => {
    const idStr = String(id)
    const found = findCategoryNodeById(categoryTree.value, idStr)
    return found?.name || idStr
  })
}

const findCategoryNodeById = (nodes: ContentCategoryTreeVo[] | undefined, id: string): ContentCategoryTreeVo | undefined => {
  if (!nodes?.length) return undefined
  for (const n of nodes) {
    if (String(n.id) === id) return n
    const child = findCategoryNodeById(n.children, id)
    if (child) return child
  }
  return undefined
}

const normalizeTagIds = (val: any): number[] => {
  if (!Array.isArray(val)) return []
  return val
    .map((item) => Number(item))
    .filter((item) => Number.isFinite(item))
}

const normalizeLong = (val: any): number | undefined => {
  if (val === null || val === undefined || val === '') return undefined
  const num = Number(val)
  return Number.isFinite(num) ? num : undefined
}

const getTagNames = (val: any): string[] => {
  const ids = normalizeTagIds(val)
  if (!ids.length) return []
  const map = new Map((tagList.value || []).map((t) => [Number(t.id), t.name || String(t.id)]))
  return ids.map((id) => map.get(id) || String(id))
}

const loadMeta = async () => {
  const [catRes, tagRes] = await Promise.all([getContentCategoryTree(), getContentTagSelectAll()])
  if (catRes.code === 200) {
    categoryTree.value = normalizeCategoryTree(catRes.data || [])
  }
  if (tagRes.code === 200) {
    tagList.value = tagRes.data || []
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const params: ContentInfoQuery = {
      ...queryParams,
      authorId: normalizeLong(queryParams.authorId),
      auditBy: normalizeLong(queryParams.auditBy),
      categoryIds: normalizeCategoryIds(queryParams.categoryIds),
      tagIds: normalizeTagIds(queryParams.tagIds),
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res: any = await getContentInfoPage(params)
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
  pagination.current = 1
  queryParams.title = ''
  queryParams.authorId = undefined
  queryParams.auditBy = undefined
  queryParams.categoryIds = []
  queryParams.tagIds = []
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleAdd = () => {
  addOrUpdateRef.value?.openForAdd()
}

const handleEdit = (record: ContentInfoVo) => {
  if (!record.id) return
  addOrUpdateRef.value?.openForEdit(record.id)
}

const handleDeleteOne = (record: ContentInfoVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `确定删除内容「${record.title || record.id}」？`,
    okType: 'danger',
    centered: true,
    onOk: async () => {
      const res: any = await deleteContentInfo(record.id!)
      if (res.code === 200) {
        message.success('删除成功')
        loadData()
      }
    },
  })
}

const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({
    title: '批量删除',
    content: `确定删除 ${selectedRowKeys.value.length} 项？`,
    okType: 'danger',
    centered: true,
    onOk: async () => {
      const res: any = await batchDeleteContentInfo(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('删除成功')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

onMounted(() => {
  loadMeta().finally(() => loadData())
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.content-cell {
  display: flex;
  align-items: center;
  gap: 14px;
  min-width: 0;
}

.content-cover {
  flex: 0 0 auto;
}

.content-main {
  display: flex;
  flex-direction: column;
  gap: 6px;
  min-width: 0;
}

.title-main {
  color: #1f2937;
  font-weight: 600;
  line-height: 1.5;
  word-break: break-word;
}

.content-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 8px 12px;
  color: #94a3b8;
  font-size: 12px;
  line-height: 1.4;
}

.cover-image :deep(img) {
  border-radius: 12px;
  object-fit: cover;
  box-shadow: 0 6px 20px rgba(15, 23, 42, 0.12);
}

.cover-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 56px;
  height: 56px;
  border: 1px dashed #cbd5e1;
  border-radius: 12px;
  background: #f8fafc;
  color: #94a3b8;
  font-size: 12px;
}

.info-stack {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.info-block {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.info-label {
  color: #64748b;
  font-size: 12px;
  font-weight: 600;
  line-height: 1;
}

.tag-group {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.info-tag {
  margin-inline-end: 0;
  padding-inline: 10px;
  border-radius: 999px;
}

.info-tag--light {
  background: #f8fafc;
  border-color: #dbeafe;
  color: #2563eb;
}

.status-stack {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.status-line {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
}

.status-tag {
  margin-inline-end: 0;
  padding-inline: 10px;
  border-radius: 999px;
}

.metrics-stack {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.metric-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 8px 10px;
  border-radius: 12px;
  background: #f8fafc;
  border: 1px solid #e2e8f0;
}

.metric-value {
  color: #0f172a;
  font-size: 16px;
  font-weight: 700;
  line-height: 1;
}

.metric-label {
  color: #64748b;
  font-size: 12px;
}

.table-actions {
  display: flex;
  align-items: center;
  gap: 4px;
  justify-content: center;
  flex-wrap: nowrap;
}

.action-btn {
  padding-inline: 6px;
}
</style>
