<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
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
          :scroll="{ x: 1700 }"
          @change="handleTableChange"
          class="elegant-table"
        >
           <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'cover'">
              <a-image v-if="record.cover" :src="resolveImage(record.cover)" :width="44" :height="44" />
              <span v-else class="text-secondary">-</span>
            </template>
            <template v-else-if="column.key === 'categoryIds'">
              <template v-if="getCategoryNames(record.categoryIds).length">
                <a-tag v-for="name in getCategoryNames(record.categoryIds)" :key="name">{{ name }}</a-tag>
              </template>
              <span v-else class="text-secondary">-</span>
            </template>
            <template v-else-if="column.key === 'tagIds'">
              <template v-if="getTagNames(record.tagIds).length">
                <a-tag v-for="t in getTagNames(record.tagIds)" :key="t">{{ t }}</a-tag>
              </template>
              <span v-else class="text-secondary">-</span>
            </template>
            <template v-else-if="column.key === 'status'">
              <a-tag :color="record.status === 1 ? 'green' : 'red'">{{ getDictLabel('status', record.status) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'publishStatus'">
              <a-tag color="blue">{{ getDictLabel('content_publish_status', record.publishStatus) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'auditStatus'">
              <a-tag color="purple">{{ getDictLabel('content_audit_status', record.auditStatus) }}</a-tag>
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
import { ref, reactive, onMounted } from 'vue'
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

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 90 },
  { title: '标题', dataIndex: 'title', key: 'title', width: 260 },
  { title: '封面', dataIndex: 'cover', key: 'cover', width: 80 },
  { title: '作者', dataIndex: 'authorName', key: 'authorName', width: 140 },
  { title: '分类', dataIndex: 'categoryIds', key: 'categoryIds', width: 220 },
  { title: '标签', dataIndex: 'tagIds', key: 'tagIds', width: 220 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '发布状态', dataIndex: 'publishStatus', key: 'publishStatus', width: 120 },
  { title: '审核状态', dataIndex: 'auditStatus', key: 'auditStatus', width: 120 },
  { title: '浏览数', dataIndex: 'viewCount', key: 'viewCount', width: 100 },
  { title: '评论数', dataIndex: 'commentCount', key: 'commentCount', width: 100 },
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
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条数据`,
})

const categoryTree = ref<ContentCategoryTreeVo[]>([])

const tagList = ref<ContentTagVo[]>([])

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
</style>
