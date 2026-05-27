<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { message } from 'ant-design-vue'
import type { FormInstance } from 'ant-design-vue'
import type { ContentInfoCreate, ContentInfoUpdate } from '@/api/content/contentinfo'
import { createContentInfo, getContentInfoById, updateContentInfo } from '@/api/content/contentinfo'
import type { ContentCategoryTreeVo } from '@/api/content/contentcategory'
import { getContentCategoryTree } from '@/api/content/contentcategory'
import type { ContentTagVo } from '@/api/content/contenttag'
import { getContentTagSelectAll } from '@/api/content/contenttag'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData } from '@/utils/dict.ts'
import ImageUpload from '@/components/ImageUpload/index.vue'
import TipTapEditor from '@/components/TipTapEditor/index.vue'

export interface AddOrUpdateRef {
  openForAdd: () => void
  openForEdit: (id: number) => Promise<void>
}

type ContentInfoFormData = ContentInfoCreate & { id?: number }
type TreeSelectNode = { title: string; value: string; children?: TreeSelectNode[] }

const emit = defineEmits<{
  success: []
}>()

const modalVisible = ref(false)
const isEdit = ref(false)
const submitLoading = ref(false)
const formRef = ref<FormInstance>()
const activeTab = ref('base')

const statusOptions = computed(() => (getDictData('status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const publishStatusOptions = computed(() => (getDictData('content_publish_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const auditStatusOptions = computed(() => (getDictData('content_audit_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const formData = reactive<ContentInfoFormData>({
  id: undefined,
  title: '',
  summary: '',
  cover: '',
  format: 'html',
  content: '',
  contentHtml: '',
  wordCount: 0,
  readingTime: 0,
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

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
}

const getDefaultFormData = (): ContentInfoFormData => ({
  id: undefined,
  title: '',
  summary: '',
  cover: '',
  format: 'html',
  content: '',
  contentHtml: '',
  wordCount: 0,
  readingTime: 0,
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

const resetFormData = () => {
  Object.assign(formData, getDefaultFormData())
  selectedCategoryIds.value = []
  selectedTagIds.value = []
}

const toIdString = (value: any): string | undefined => {
  if (value === null || value === undefined || value === '') return undefined
  if (typeof value === 'string') return value
  if (typeof value === 'number' || typeof value === 'bigint') return String(value)
  if (typeof value === 'object' && typeof value.toString === 'function') {
    const str = value.toString()
    if (str && str !== '[object Object]') return str
  }
  return String(value)
}

const normalizeCategoryTree = (nodes: any[] | undefined): ContentCategoryTreeVo[] => {
  if (!nodes?.length) return []
  return nodes.map((node) => ({
    ...node,
    id: toIdString(node.id),
    parentId: toIdString(node.parentId),
    children: normalizeCategoryTree(node.children),
  }))
}

const buildCategoryTreeSelect = (nodes: ContentCategoryTreeVo[] | undefined): TreeSelectNode[] => {
  if (!nodes?.length) return []
  return nodes.map((node) => ({
    title: node.name || `${node.id}`,
    value: String(node.id),
    children: buildCategoryTreeSelect(node.children),
  }))
}

const normalizeCategoryIds = (value: any): number[] => {
  if (!Array.isArray(value)) return []
  return value
    .map((item) => Number(item))
    .filter((item) => Number.isFinite(item))
}

const normalizeTagIds = (value: any): number[] => {
  if (!Array.isArray(value)) return []
  return value
    .map((item) => Number(item))
    .filter((item) => Number.isFinite(item))
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

const getReadingTime = (wordCount: number) => {
  if (!wordCount || wordCount <= 0) return 0
  return Math.max(1, Math.ceil(wordCount / 500))
}

const loadMeta = async () => {
  const [categoryRes, tagRes] = await Promise.all([getContentCategoryTree(), getContentTagSelectAll()])
  if (categoryRes.code === 200) {
    categoryTree.value = normalizeCategoryTree(categoryRes.data || [])
  }
  if (tagRes.code === 200) {
    tagList.value = tagRes.data || []
  }
}

const openForAdd = () => {
  isEdit.value = false
  activeTab.value = 'base'
  resetFormData()
  modalVisible.value = true
}

const openForEdit = async (id: number) => {
  isEdit.value = true
  activeTab.value = 'base'
  const res: any = await getContentInfoById(id)
  if (res.code !== 200) return

  Object.assign(formData, {
    ...getDefaultFormData(),
    ...res.data,
    categoryIds: normalizeCategoryIds(res.data?.categoryIds),
    tagIds: normalizeTagIds(res.data?.tagIds),
  })
  selectedCategoryIds.value = normalizeCategoryIds(formData.categoryIds).map((item) => String(item))
  selectedTagIds.value = normalizeTagIds(formData.tagIds)
  modalVisible.value = true
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true

    const payload: any = { ...formData }
    payload.categoryIds = normalizeCategoryIds(selectedCategoryIds.value)
    payload.tagIds = normalizeTagIds(selectedTagIds.value)
    payload.format = payload.format || 'html'
    payload.contentHtml = payload.contentHtml || ''
    payload.content = stripHtml(payload.contentHtml)
    payload.wordCount = payload.content.length
    payload.readingTime = getReadingTime(payload.wordCount)

    const requestId = getRequestId()
    const res: any = isEdit.value
      ? await updateContentInfo(payload as ContentInfoUpdate, requestId)
      : await createContentInfo(payload as ContentInfoCreate, requestId)

    if (res.code === 200) {
      message.success(isEdit.value ? '更新成功' : '创建成功')
      modalVisible.value = false
      emit('success')
    }
  } finally {
    submitLoading.value = false
  }
}

const handleCancel = () => {
  modalVisible.value = false
  setTimeout(() => {
    formRef.value?.resetFields()
    resetFormData()
  }, 300)
}

defineExpose<AddOrUpdateRef>({
  openForAdd,
  openForEdit,
})

onMounted(() => {
  loadMeta()
})
</script>

<template>
  <a-modal
    v-model:open="modalVisible"
    :title="isEdit ? '编辑内容' : '新增内容'"
    width="100%"
    wrap-class-name="content-info-fullscreen-modal"
    :destroy-on-close="true"
    @cancel="handleCancel"
    @ok="handleSubmit"
    :confirm-loading="submitLoading"
  >
    <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="base" tab="基础信息">
          <a-row :gutter="16">
            <a-col :span="24"><a-form-item label="标题" name="title"><a-input v-model:value="formData.title" placeholder="请输入标题" /></a-form-item></a-col>
            <a-col :span="24"><a-form-item label="摘要" name="summary"><a-textarea v-model:value="formData.summary" :rows="3" /></a-form-item></a-col>
            <a-col :span="12"><a-form-item label="封面" name="cover"><ImageUpload v-model="formData.cover" value-type="id" :limit="1" /></a-form-item></a-col>
            <a-col :span="12"><a-form-item label="作者名称" name="authorName"><a-input v-model:value="formData.authorName" /></a-form-item></a-col>
            <a-col :span="12"><a-form-item label="作者ID" name="authorId"><a-input-number v-model:value="formData.authorId" :min="0" style="width:100%" /></a-form-item></a-col>
            <a-col :span="12"><a-form-item label="来源" name="source"><a-input v-model:value="formData.source" /></a-form-item></a-col>
            <a-col :span="24"><a-form-item label="来源链接" name="sourceUrl"><a-input v-model:value="formData.sourceUrl" /></a-form-item></a-col>
          </a-row>
        </a-tab-pane>

        <a-tab-pane key="meta" tab="分类与标签">
          <a-row :gutter="16">
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
          </a-row>
        </a-tab-pane>

        <a-tab-pane key="settings" tab="发布设置">
          <a-row :gutter="16">
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
          </a-row>
        </a-tab-pane>

        <a-tab-pane key="content" tab="正文">
          <a-row :gutter="16">
            <a-col :span="24"><a-form-item label="正文" name="contentHtml">
              <TipTapEditor v-model="formData.contentHtml" placeholder="请输入正文" :min-height="460" />
            </a-form-item></a-col>
          </a-row>
        </a-tab-pane>
      </a-tabs>
    </a-form>
  </a-modal>
</template>

<style>
.content-info-fullscreen-modal,
.content-info-fullscreen-modal .ant-modal-wrap {
  overflow-x: hidden;
}

.content-info-fullscreen-modal .ant-modal {
  top: 0;
  width: 100% !important;
  max-width: 100% !important;
  height: 100vh;
  margin: 0;
  padding-bottom: 0;
}

.content-info-fullscreen-modal .ant-modal-content {
  height: 100vh;
  display: flex;
  flex-direction: column;
  border-radius: 0;
  overflow: hidden;
}

.content-info-fullscreen-modal .ant-modal-body {
  flex: 1;
  overflow: auto;
  overflow-x: hidden;
}

.content-info-fullscreen-modal .ant-modal-body .ant-tabs-nav {
  margin-bottom: 12px;
}
</style>
