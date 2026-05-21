<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { message, Modal } from 'ant-design-vue'
import { EditOutlined, DeleteOutlined, PlusOutlined } from '@ant-design/icons-vue'
import {
  getPageConfigList,
  createPageConfig,
  updatePageConfig,
  deletePageConfig,
  type PageConfigListVo,
  type PageConfigCreate,
  type PageConfigUpdate
} from '@/api/page/pageconfig.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import {getPageApplicationById, type PageApplicationVo} from '@/api/page/pageapplication.ts'

const props = defineProps<{
  applicationId: string
}>()

const emit = defineEmits<{
  (e: 'select', page: PageConfigListVo): void
  (e: 'applicationType', value: string): void
  (e: 'selectTypeCode',value: string): void
}>()

// ---- list state ----
const pageList = ref<PageConfigListVo[]>([])
const loading = ref(false)
const selectedPageId = ref<string | null>(null)
const application = ref<PageApplicationVo>()

const loadPageList = async () => {
  if (!props.applicationId) return
  loading.value = true
  try {
    const resApplication = await getPageApplicationById(props.applicationId)
    application.value = resApplication.data
    emit('applicationType', application.value?.typeId ?? '')
    emit('selectTypeCode',application.value.typeCode)
    console.log(JSON.stringify(application.value))
    const res = await getPageConfigList(props.applicationId)
    pageList.value = res.data || []
  } catch (error) {
    message.error('加载页面列表失败')
  } finally {
    loading.value = false
  }
}

const handleSelect = (page: PageConfigListVo) => {
  selectedPageId.value = page.id || null
  emit('select', page)
}

// ---- modal state ----
const modalVisible = ref(false)
const modalTitle = ref('')
const isEdit = ref(false)
const submitting = ref(false)
const requestId = ref('')

const formData = ref<PageConfigCreate & { id?: string }>({
  title: '',
  pathUrl: '',
  status: 1,
  version: '1.0.0',
  applicationId: undefined
})

const resetForm = () => {
  formData.value = {
    title: '',
    pathUrl: '',
    status: 1,
    version: '1.0.0',
    applicationId: props.applicationId
  }
}

const handleAdd = () => {
  isEdit.value = false
  modalTitle.value = '新增页面'
  resetForm()
  requestId.value = getRequestId()
  modalVisible.value = true
}

const handleEdit = (page: PageConfigListVo) => {
  isEdit.value = true
  modalTitle.value = '编辑页面'
  formData.value = {
    id: page.id,
    title: page.title,
    pathUrl: page.pathUrl,
    status: page.status,
    version: page.version,
    applicationId: page.applicationId
  }
  requestId.value = getRequestId()
  modalVisible.value = true
}

const handleDelete = (page: PageConfigListVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `确定要删除页面 "${page.pathUrl}" 吗？`,
    okText: '确定',
    cancelText: '取消',
    onOk: async () => {
      try {
        await deletePageConfig(page.id!)
        message.success('删除成功')
        if (selectedPageId.value === page.id) {
          selectedPageId.value = null
        }
        await loadPageList()
      } catch (error) {
        message.error('删除失败')
      }
    }
  })
}

const handleSubmit = async () => {
  if (!formData.value.pathUrl) {
    message.error('请输入请求地址')
    return
  }
  submitting.value = true
  try {
    if (isEdit.value && formData.value.id) {
      const updateData: PageConfigUpdate = {
        id: formData.value.id,
        title: formData.value.title,
        pathUrl: formData.value.pathUrl,
        status: formData.value.status,
        version: formData.value.version,
        applicationId: formData.value.applicationId
      }
      await updatePageConfig(updateData, requestId.value)
      message.success('修改成功')
    } else {
      const createData: PageConfigCreate = {
        title: formData.value.title,
        pathUrl: formData.value.pathUrl,
        status: formData.value.status,
        version: formData.value.version,
        applicationId: formData.value.applicationId
      }
      await createPageConfig(createData, requestId.value)
      message.success('新增成功')
    }
    modalVisible.value = false
    await loadPageList()
  } catch (error) {
    message.error(isEdit.value ? '修改失败' : '新增失败')
  } finally {
    submitting.value = false
  }
}

// ---- utils ----
const getStatusText = (status: number) => status === 1 ? '正常' : '停用'
const getStatusClass = (status: number) => status === 1 ? 'status-normal' : 'status-disabled'

const getFileUrl = (fileId: string) => {
  if (!fileId) return ''
  return `/api/file/getUrl/${fileId}`
}

const handleIconError = (e: Event) => {
  const img = e.target as HTMLImageElement
  img.style.display = 'none'
}

watch(() => props.applicationId, () => loadPageList())
onMounted(() => loadPageList())
</script>

<template>
  <div class="page-list-container">
    <div v-if="application" class="application-header">
      <div class="application-info">
        <div class="application-title-row">
          <img 
            v-if="application.icon"
            :src="getFileUrl(application.icon)"
            class="application-icon"
            alt="应用图标"
            @error="handleIconError"
          />
          <h3 class="application-title">{{ application.title || '未命名应用' }}</h3>
        </div>
        <div class="application-meta">
          <span v-if="application.code" class="meta-item">
            <span class="meta-label">编码:</span>
            <span class="meta-value">{{ application.code }}</span>
          </span>
          <span v-if="application.typeName" class="meta-item">
            <span class="meta-label">类型:</span>
            <span class="meta-value">{{ application.typeName }}</span>
          </span>
        </div>
      </div>
    </div>
    <div class="panel-header">
      <div class="panel-header-content">
        <h3 class="panel-title">页面列表</h3>
        <a-button type="primary" size="small" @click="handleAdd">
          <template #icon><PlusOutlined /></template>
          新增
        </a-button>
      </div>
    </div>
    <div class="panel-content">
      <a-spin :spinning="loading">
        <div v-if="pageList.length === 0" class="empty-state">
          <p>暂无页面配置</p>
        </div>
        <div v-else class="page-list">
          <div
            v-for="page in pageList"
            :key="page.id"
            class="page-item"
            :class="{ 'active': selectedPageId === page.id }"
            @click="handleSelect(page)"
          >
            <div class="page-item-header">
              <div class="page-title-row">
                <span class="page-title" :title="page.title">{{ page.title || '未命名' }}</span>
              </div>
              <div class="page-actions">
                <a-button
                  type="text"
                  size="small"
                  class="action-btn"
                  @click.stop="handleEdit(page)"
                >
                  <template #icon><EditOutlined /></template>
                </a-button>
                <a-button
                  type="text"
                  size="small"
                  danger
                  class="action-btn"
                  @click.stop="handleDelete(page)"
                >
                  <template #icon><DeleteOutlined /></template>
                </a-button>
              </div>
            </div>
            <div class="page-item-footer">
              <span class="page-path" :title="page.pathUrl">{{ page.pathUrl }}</span> 
            </div>
            <div>
                <span class="page-version">v{{ page.version }}</span>
            </div>
            <div class="page-item-footer">
              <span :class="['page-status', getStatusClass(page.status || 0)]">
                {{ getStatusText(page.status || 0) }}
              </span>
            </div>
          </div>
        </div>
      </a-spin>
    </div>
  </div>

  <!-- 新增/编辑弹窗 -->
  <a-modal
    v-model:open="modalVisible"
    :title="modalTitle"
    @ok="handleSubmit"
    @cancel="modalVisible = false"
    :confirm-loading="submitting"
    ok-text="确定"
    cancel-text="取消"
  >
    <a-form :model="formData" layout="vertical" class="page-form">
      <a-form-item label="标题" required>
        <a-input
          v-model:value="formData.title"
          placeholder="请输入页面标题"
          allow-clear
        />
      </a-form-item>
      <a-form-item label="请求地址" required>
        <a-input
          v-model:value="formData.pathUrl"
          placeholder="请输入请求地址，如：/home"
          allow-clear
        />
      </a-form-item>
      <a-form-item label="版本号">
        <a-input
          v-model:value="formData.version"
          placeholder="请输入版本号，如：1.0.0"
          allow-clear
        />
      </a-form-item>
      <a-form-item label="状态">
        <a-select v-model:value="formData.status">
          <a-select-option :value="1">正常</a-select-option>
          <a-select-option :value="2">停用</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<style scoped>
.page-list-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.application-header {
  padding: 16px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #ffffff;
  border-bottom: 1px solid #e8e8e8;
}

.application-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.application-title-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

.application-icon {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  object-fit: cover;
  background-color: rgba(255, 255, 255, 0.2);
  flex-shrink: 0;
}

.application-title {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  color: #ffffff;
}

.application-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  font-size: 12px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 4px;
}

.meta-label {
  opacity: 0.85;
}

.meta-value {
  font-weight: 500;
}

.meta-value.status-normal {
  color: #52c41a;
  background-color: rgba(255, 255, 255, 0.9);
  padding: 2px 8px;
  border-radius: 10px;
}

.meta-value.status-disabled {
  color: #ff4d4f;
  background-color: rgba(255, 255, 255, 0.9);
  padding: 2px 8px;
  border-radius: 10px;
}


.panel-header {
  padding: 12px 16px;
  border-bottom: 1px solid #e8e8e8;
  background-color: #fafafa;
}

.panel-header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.panel-title {
  margin: 0;
  font-size: 14px;
  font-weight: 600;
  color: #262626;
}

.panel-content {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
}

.empty-state {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 200px;
  color: #8c8c8c;
  font-size: 14px;
}

.page-list {
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.page-item {
  padding: 8px;
  background-color: #ffffff;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s;
}

.page-item:hover {
  border-color: #1890ff;
  box-shadow: 0 2px 8px rgba(24, 144, 255, 0.15);
}

.page-item.active {
  border-color: #1890ff;
  background-color: #e6f7ff;
}

.page-item-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 3px;
  gap: 4px;
}

.page-title-row {
  flex: 1;
  min-width: 0;
}

.page-title {
  font-size: 13px;
  font-weight: 600;
  color: #262626;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.page-path {
  font-size: 12px;
  color: #8c8c8c;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  flex: 1;
  min-width: 0;
}

.page-actions {
  display: flex;
  align-items: center;
  gap: 4px;
  opacity: 0;
  transition: opacity 0.2s;
  flex-shrink: 0;
}

.page-item:hover .page-actions {
  opacity: 1;
}

.action-btn {
  padding: 4px;
  height: auto;
}

.page-item-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 12px;
  margin-top: 2px;
}

.page-version {
  color: #8c8c8c;
  font-size: 12px;
}

.page-status {
  padding: 1px 6px;
  border-radius: 8px;
  font-size: 11px;
}

.status-normal {
  background-color: #f6ffed;
  color: #52c41a;
}

.status-disabled {
  background-color: #fff2f0;
  color: #ff4d4f;
}

.page-form {
  padding-top: 8px;
}
</style>
