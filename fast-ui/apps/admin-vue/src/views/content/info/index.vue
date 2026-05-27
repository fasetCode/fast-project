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

    <a-modal
      v-model:open="modalVisible"
      :title="isEdit ? '编辑内容' : '新增内容'"
      width="100vw"
      wrap-class-name="content-info-fullscreen-modal"
      :destroy-on-close="true"
      @cancel="handleCancel"
      @ok="handleSubmit"
      :confirm-loading="submitLoading"
    >
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="24"><a-form-item label="标题" name="title"><a-input v-model:value="formData.title" placeholder="请输入标题" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="摘要" name="summary"><a-textarea v-model:value="formData.summary" :rows="3" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="封面" name="cover"><ImageUpload v-model="formData.cover" value-type="id" :limit="1" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="作者名称" name="authorName"><a-input v-model:value="formData.authorName" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="作者ID" name="authorId"><a-input-number v-model:value="formData.authorId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="分类" name="categoryIds">
            <a-tree-select
              v-model:value="selectedCategoryIds"
              :tree-data="categoryTreeData"
              :field-names="{ label: 'title', value: 'value', children: 'children' }"
              multiple
              allow-clear
              tree-default-expand-all
              style="width:100%"
              placeholder="请选择分类"
            />
          </a-form-item></a-col>
          <a-col :span="12"><a-form-item label="标签" name="tagIds">
            <a-select v-model:value="selectedTagIds" mode="multiple" allow-clear placeholder="请选择标签">
              <a-select-option v-for="item in tagSelectOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="12"><a-form-item label="来源" name="source"><a-input v-model:value="formData.source" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="来源链接" name="sourceUrl"><a-input v-model:value="formData.sourceUrl" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="置顶" name="topFlag"><a-switch v-model:checked="formData.topFlag" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="推荐" name="recommendFlag"><a-switch v-model:checked="formData.recommendFlag" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="允许评论" name="allowComment"><a-switch v-model:checked="formData.allowComment" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="状态" name="status">
            <a-select v-model:value="formData.status" allow-clear>
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="发布状态" name="publishStatus">
            <a-select v-model:value="formData.publishStatus" allow-clear>
              <a-select-option v-for="item in publishStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="审核状态" name="auditStatus">
            <a-select v-model:value="formData.auditStatus" allow-clear>
              <a-select-option v-for="item in auditStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="24"><a-form-item label="正文" name="contentHtml">
            <TipTapEditor v-model="revisionHtml" placeholder="请输入正文" :min-height="420" />
          </a-form-item></a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { message, Modal } from 'ant-design-vue'
import { SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { ContentInfoVo, ContentInfoQuery, ContentInfoCreate, ContentInfoUpdate } from '@/api/content/contentinfo'
import { getContentInfoPage, getContentInfoById, createContentInfo, updateContentInfo, deleteContentInfo, batchDeleteContentInfo } from '@/api/content/contentinfo'
import type { ContentCategoryTreeVo } from '@/api/content/contentcategory'
import { getContentCategoryTree } from '@/api/content/contentcategory'
import type { ContentTagVo } from '@/api/content/contenttag'
import { getContentTagSelectAll } from '@/api/content/contenttag'
import { createContentRevision, getContentRevisionLatest } from '@/api/content/contentrevision'
import { getFileUrl } from '@/api/file/fileupload'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'
import ImageUpload from '@/components/ImageUpload/index.vue'
import TipTapEditor from '@/components/TipTapEditor/index.vue'

const statusOptions = computed(() => (getDictData('status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const publishStatusOptions = computed(() => (getDictData('content_publish_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const auditStatusOptions = computed(() => (getDictData('content_audit_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

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
const submitLoading = ref(false)
const dataSource = ref<ContentInfoVo[]>([])
const selectedRowKeys = ref<number[]>([])

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

const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
type ContentInfoFormData = ContentInfoCreate & { id?: number }
const formData = reactive<ContentInfoFormData>({
  id: undefined,
  title: '',
  summary: '',
  cover: '',
  categoryIds: [],
  tagIds: [],
  authorId: undefined,
  authorName: '',
  source: '',
  sourceUrl: '',
  topFlag: false,
  recommendFlag: false,
  allowComment: true,
  status: 1,
  publishStatus: undefined,
  auditStatus: undefined,
})

type TreeSelectNode = { title: string; value: string; children?: TreeSelectNode[] }
const categoryTree = ref<ContentCategoryTreeVo[]>([])
const categoryTreeData = computed<TreeSelectNode[]>(() => buildCategoryTreeSelect(categoryTree.value))
const selectedCategoryIds = ref<string[]>([])

const tagList = ref<ContentTagVo[]>([])
const tagSelectOptions = computed(() =>
  (tagList.value || [])
    .filter((t) => t && t.id !== undefined && t.id !== null)
    .map((t) => ({ value: Number(t.id), label: t.name || String(t.id) }))
)
const selectedTagIds = ref<number[]>([])

const revisionHtml = ref('')
const originRevisionHtml = ref('')
const latestRevisionVersion = ref(0)

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
}

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

const buildCategoryTreeSelect = (nodes: ContentCategoryTreeVo[] | undefined): TreeSelectNode[] => {
  if (!nodes?.length) return []
  return nodes.map((n) => ({
    title: n.name || `${n.id}`,
    value: String(n.id),
    children: buildCategoryTreeSelect(n.children),
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

const stripHtml = (html: string) => {
  if (!html) return ''
  return html
    .replace(/<style[^>]*>[\s\S]*?<\/style>/gi, '')
    .replace(/<script[^>]*>[\s\S]*?<\/script>/gi, '')
    .replace(/<[^>]+>/g, ' ')
    .replace(/&nbsp;/gi, ' ')
    .replace(/\s+/g, ' ')
    .trim()
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
  isEdit.value = false
  Object.assign(formData, {
    id: undefined,
    title: '',
    summary: '',
    cover: '',
    categoryIds: [],
    tagIds: [],
    authorId: undefined,
    authorName: '',
    source: '',
    sourceUrl: '',
    topFlag: false,
    recommendFlag: false,
    allowComment: true,
    status: 1,
    publishStatus: undefined,
    auditStatus: undefined,
  })
  selectedCategoryIds.value = []
  selectedTagIds.value = []
  revisionHtml.value = ''
  originRevisionHtml.value = ''
  latestRevisionVersion.value = 0
  modalVisible.value = true
}

const handleEdit = async (record: ContentInfoVo) => {
  isEdit.value = true
  const res: any = await getContentInfoById(record.id!)
  if (res.code === 200) {
    Object.assign(formData, {
      ...res.data,
      categoryIds: normalizeCategoryIds(res.data?.categoryIds),
      tagIds: normalizeTagIds(res.data?.tagIds),
    })
  }
  selectedCategoryIds.value = normalizeCategoryIds(formData.categoryIds).map((item) => String(item))
  selectedTagIds.value = normalizeTagIds(formData.tagIds)

  const revRes: any = await getContentRevisionLatest(record.id!)
  if (revRes.code === 200 && revRes.data) {
    revisionHtml.value = revRes.data.contentHtml || revRes.data.content || ''
    originRevisionHtml.value = revisionHtml.value
    latestRevisionVersion.value = Number(revRes.data.version || 0)
  } else {
    revisionHtml.value = ''
    originRevisionHtml.value = ''
    latestRevisionVersion.value = 0
  }
  modalVisible.value = true
}

const saveRevisionIfNeeded = async (contentId: number, requestId: string) => {
  const html = revisionHtml.value || ''
  const changed = html !== (originRevisionHtml.value || '')
  const shouldSave = isEdit.value ? changed : Boolean(html.trim())
  if (!shouldSave) return

  const text = stripHtml(html)
  const res: any = await createContentRevision(
    {
      contentId,
      version: (latestRevisionVersion.value || 0) + 1,
      format: 'html',
      content: text,
      contentHtml: html,
      wordCount: text.length,
    },
    requestId
  )

  if (res.code !== 200) {
    throw new Error(res.msg || '保存正文失败')
  }
  originRevisionHtml.value = html
  latestRevisionVersion.value = (latestRevisionVersion.value || 0) + 1
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const payload: any = { ...formData }
    payload.categoryIds = normalizeCategoryIds(selectedCategoryIds.value)
    payload.tagIds = normalizeTagIds(selectedTagIds.value)

    const res: any = isEdit.value
      ? await updateContentInfo(payload as ContentInfoUpdate, reqId)
      : await createContentInfo(payload as ContentInfoCreate, reqId)
    if (res.code === 200) {
      const contentId = isEdit.value ? Number(formData.id) : Number(res.data)
      await saveRevisionIfNeeded(contentId, reqId)
      message.success(isEdit.value ? '更新成功' : '创建成功')
      modalVisible.value = false
      loadData()
    }
  } finally {
    submitLoading.value = false
  }
}

const handleCancel = () => {
  modalVisible.value = false
  setTimeout(() => formRef.value?.resetFields(), 300)
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

<style>
.content-info-fullscreen-modal .ant-modal {
  top: 0;
  width: 100vw !important;
  max-width: 100vw;
  height: 100vh;
  margin: 0;
  padding-bottom: 0;
}

.content-info-fullscreen-modal .ant-modal-content {
  height: 100vh;
  display: flex;
  flex-direction: column;
  border-radius: 0;
}

.content-info-fullscreen-modal .ant-modal-body {
  flex: 1;
  overflow: auto;
}
</style>
