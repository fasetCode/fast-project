<template>

  <div class="sysdict-container">
    <!-- 左侧：字典类型列表 -->
    <div class="dict-type-panel">
      <div class="panel-header">
        <div class="panel-title">字典类型</div>
        <a-button v-if="canTypeAdd" type="primary" class="pill-btn primary-pill" size="small" @click="handleAddType">
          <PlusOutlined /> 新增
        </a-button>
      </div>

      <div class="type-search">
        <a-input-search
          v-model:value="typeSearchText"
          placeholder="搜索字典类型..."
          allow-clear
          @search="loadDictTypes"
          class="elegant-input"
        />
      </div>

      <div class="type-list">
        <a-spin :spinning="typeLoading">
          <div
            v-for="item in dictTypeList"
            :key="item.id"
            class="type-item"
            :class="{ active: selectedTypeId === item.id }"
            @click="handleSelectType(item)"
          >
            <div class="type-info">
              <FileTextOutlined class="type-icon" />
              <div class="type-content">
                <div class="type-name">{{ item.name }}</div>
                <div class="type-code">{{ item.type }}</div>
              </div>
            </div>
            <div class="type-actions" @click.stop>
              <EditOutlined v-if="canTypeUpdate" class="action-icon" @click="handleEditType(item)" />
              <DeleteOutlined v-if="canTypeDelete" class="action-icon delete" @click="handleDeleteType(item)" />
            </div>
          </div>
          <a-empty v-if="dictTypeList.length === 0 && !typeLoading" :description="'暂无字典类型'" />
        </a-spin>
      </div>
    </div>

    <!-- 右侧：字典数据列表 -->
    <div class="dict-data-panel">
      <div class="board-toolbar" style="padding: 16px 24px; border-bottom: 1px solid #e5e7eb;">
        <div class="toolbar-left">
          <div class="board-tabs">
            <div class="tab active">
              字典数据
              <span v-if="selectedTypeName" class="badge">{{ selectedTypeName }}</span>
            </div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-input-search
            v-model:value="dataSearchText"
            placeholder="搜索字典标签或值..."
            allow-clear
            @search="loadDictData"
            class="elegant-input"
            style="width: 240px; margin-right: 12px;"
          />
          <a-button
            v-if="canDataAdd"
            type="primary"
            class="pill-btn primary-pill"
            :disabled="!selectedTypeId"
            @click="handleAddData"
          >
            <PlusOutlined /> 新增数据
          </a-button>
        </div>
      </div>

      <div class="data-table table-wrapper" style="padding: 16px 24px;">
        <a-spin :spinning="dataLoading">
          <a-table
            :columns="dataColumns"
            :data-source="dictDataList"
            :pagination="false"
            size="middle"
            row-key="id"
            class="elegant-table"
          >
            <template #bodyCell="{ column, record }">
              <template v-if="column.key === 'label'">
                <div class="dict-label">
                  <TagOutlined class="label-icon text-secondary" />
                  <span class="text-strong" style="margin-left: 8px;">{{ record.label }}</span>
                </div>
              </template>
              <template v-else-if="column.key === 'value'">
                <span class="text-code">{{ record.value }}</span>
              </template>
              <template v-else-if="column.key === 'status'">
                <div class="status-toggle" :class="{ 'is-active': record.status === 1 }">
                  <a-switch
                    :checked="record.status === 2"
                    size="small"
                    @change="handleStatusChange(record)"
                  />
                  <span class="status-text" :class="record.status === 1 ? 'text-success' : 'text-error'">
                    {{ getDictLabel('status', record.status) || '未知' }}
                  </span>
                </div>
              </template>
              <template v-else-if="column.key === 'action'">
                <div class="action-group">
                  <div v-if="canDataUpdate" class="action-btn edit" @click="handleEditData(record)" title="编辑">
                    <EditOutlined />
                  </div>
                  <div v-if="canDataDelete" class="action-btn delete" @click="handleDeleteData(record)" title="删除">
                    <DeleteOutlined />
                  </div>
                </div>
              </template>
            </template>
          </a-table>
          <a-empty v-if="dictDataList.length === 0 && !dataLoading" :description="'请选择字典类型或暂无数据'" />
        </a-spin>
      </div>
    </div>

    <!-- 字典类型弹窗 -->
    <a-modal
      v-model:open="typeModalVisible"
      :title="null"
      :footer="null"
      width="500px"
      :destroy-on-close="true"
      class="glass-modal"
      centered
      @cancel="handleTypeCancel"
    >
      <div class="modal-inner">
        <div class="modal-header">
          <div class="modal-icon" :class="isEditType ? 'edit-icon' : 'add-icon'">
            <EditOutlined v-if="isEditType" />
            <PlusCircleOutlined v-else />
          </div>
          <div class="modal-titles">
            <h2>{{ isEditType ? '编辑字典类型' : '创建新字典类型' }}</h2>
            <p>请填写以下必填信息以完成设置</p>
          </div>
        </div>

        <a-form
          ref="typeFormRef"
          :model="typeFormData"
          layout="vertical"
          :rules="typeRules"
          class="glass-form"
        >
          <div class="form-section-title">基本信息</div>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item name="name">
                <template #label><span class="form-label">字典名称</span></template>
                <a-input v-model:value="typeFormData.name" placeholder="如：用户状态" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="type">
                <template #label><span class="form-label">字典类型</span></template>
                <a-input v-model:value="typeFormData.type" placeholder="如：user_status" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="status">
                <template #label><span class="form-label">状态</span></template>
                <StatusCardSelect
                  v-model:value="typeFormData.status"
                  :options="statusOptions"
                />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="remark">
                <template #label><span class="form-label">备注</span></template>
                <a-textarea v-model:value="typeFormData.remark" placeholder="请输入备注" :rows="2" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handleTypeCancel">取消</a-button>
          <a-button type="primary" class="gradient-btn-lg" @click="handleTypeSubmit" :loading="typeSubmitLoading">
            {{ isEditType ? '保存修改' : '确认创建' }}
          </a-button>
        </div>
      </div>
    </a-modal>

    <!-- 字典数据弹窗 -->
    <a-modal
      v-model:open="dataModalVisible"
      :title="null"
      :footer="null"
      width="500px"
      :destroy-on-close="true"
      class="glass-modal"
      centered
      @cancel="handleDataCancel"
    >
      <div class="modal-inner">
        <div class="modal-header">
          <div class="modal-icon" :class="isEditData ? 'edit-icon' : 'add-icon'">
            <EditOutlined v-if="isEditData" />
            <PlusCircleOutlined v-else />
          </div>
          <div class="modal-titles">
            <h2>{{ isEditData ? '编辑字典数据' : '创建新字典数据' }}</h2>
            <p>请填写以下必填信息以完成设置</p>
          </div>
        </div>

        <a-form
          ref="dataFormRef"
          :model="dataFormData"
          layout="vertical"
          :rules="dataRules"
          class="glass-form"
        >
          <div class="form-section-title">基本信息</div>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item name="label">
                <template #label><span class="form-label">字典标签</span></template>
                <a-input v-model:value="dataFormData.label" placeholder="如：正常" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="value">
                <template #label><span class="form-label">字典值</span></template>
                <a-input v-model:value="dataFormData.value" placeholder="如：0" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="sort">
                <template #label><span class="form-label">排序</span></template>
                <a-input-number v-model:value="dataFormData.sort" :min="0" style="width: 100%" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="status">
                <template #label><span class="form-label">状态</span></template>
                <StatusCardSelect
                  v-model:value="dataFormData.status"
                  :options="statusOptions"
                />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="remark">
                <template #label><span class="form-label">备注</span></template>
                <a-textarea v-model:value="dataFormData.remark" placeholder="请输入备注" :rows="2" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handleDataCancel">取消</a-button>
          <a-button type="primary" class="gradient-btn-lg" @click="handleDataSubmit" :loading="dataSubmitLoading">
            {{ isEditData ? '保存修改' : '确认创建' }}
          </a-button>
        </div>
      </div>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed, nextTick } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  PlusOutlined, EditOutlined, DeleteOutlined, 
  FileTextOutlined, TagOutlined, PlusCircleOutlined,
  CheckCircleFilled, MinusCircleFilled
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { SysDictTypeVo, SysDictTypeCreate, SysDictTypeUpdate } from '@/api/system/sysdicttype.ts'
import type { SysDictDataVo, SysDictDataCreate, SysDictDataUpdate } from '@/api/system/sysdictdata.ts'
import { 
  getDictTypeList, getDictTypeSelectAll, createDictType, 
  updateDictType, deleteDictType 
} from '@/api/system/sysdicttype.ts'
import { 
  getDictDataPage, createDictData, 
  updateDictData, deleteDictData 
} from '@/api/system/sysdictdata.ts'
import { getRequestId } from "@/utils/idUtils.ts";
import StatusCardSelect from '@/components/StatusCardSelect/index.vue'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'

const permissionStore = usePermissionStore()

// 按钮权限控制
const canTypeAdd = ref(false)
const canTypeUpdate = ref(false)
const canTypeDelete = ref(false)
const canDataAdd = ref(false)
const canDataUpdate = ref(false)
const canDataDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canTypeAdd.value = hasButtonPermission('admin:system:dict:type:add')
  canTypeUpdate.value = hasButtonPermission('admin:system:dict:type:update')
  canTypeDelete.value = hasButtonPermission('admin:system:dict:type:delete')
  canDataAdd.value = hasButtonPermission('admin:system:dict:data:add')
  canDataUpdate.value = hasButtonPermission('admin:system:dict:data:update')
  canDataDelete.value = hasButtonPermission('admin:system:dict:data:delete')
}

const statusOptions = computed(() => {
  const statusData = getDictData('status') || []
  return statusData.map((item: any) => ({
    value: Number(item.value),
    title: item.label,
    icon: item.value === '1' ? CheckCircleFilled : MinusCircleFilled,
    type: item.value === '1' ? 'success' as const : 'error' as const
  }))
})

// 字典类型相关
const typeLoading = ref(false)
const typeSearchText = ref('')
const requestId = ref<string>('')
const dictTypeList = ref<SysDictTypeVo[]>([])
const selectedTypeId = ref<number | null>(null)
const selectedTypeName = ref('')

const typeModalVisible = ref(false)
const isEditType = ref(false)
const typeFormRef = ref<FormInstance>()
const typeSubmitLoading = ref(false)
const typeFormData = reactive<SysDictTypeCreate & { id?: number }>({
  id: undefined,
  name: '',
  type: '',
  status: 0,
  remark: '',
})

const typeRules = {
  name: [{ required: true, message: '请输入字典名称', trigger: 'blur' }],
  type: [{ required: true, message: '请输入字典类型', trigger: 'blur' }],
}

// 字典数据相关
const dataLoading = ref(false)
const dataSearchText = ref('')
const dictDataList = ref<SysDictDataVo[]>([])

const dataModalVisible = ref(false)
const isEditData = ref(false)
const dataFormRef = ref<FormInstance>()
const dataSubmitLoading = ref(false)
const dataFormData = reactive<SysDictDataCreate & { id?: number }>({
  id: undefined,
  label: '',
  value: '',
  dictTypeId: undefined,
  sort: 0,
  status: 0,
  remark: '',
})

const dataRules = {
  label: [{ required: true, message: '请输入字典标签', trigger: 'blur' }],
  value: [{ required: true, message: '请输入字典值', trigger: 'blur' }],
}

// 表格列定义
const dataColumns = [
  { title: '字典标签', dataIndex: 'label', key: 'label', width: 150 },
  { title: '字典值', dataIndex: 'value', key: 'value', width: 120 },
  { title: '排序', dataIndex: 'sort', key: 'sort', width: 60 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '备注', dataIndex: 'remark', key: 'remark', width: 150 },
  { title: '操作', key: 'action', width: 80, align: 'center' as const },
]

// 加载字典类型列表
const loadDictTypes = async () => {
  typeLoading.value = true
  try {
    const res = await getDictTypeList()
    if (res.code === 200) {
      let list = res.data || []
      // 客户端过滤搜索
      if (typeSearchText.value) {
        const search = typeSearchText.value.toLowerCase()
        list = list.filter((item: SysDictTypeVo) => 
          item.name?.toLowerCase().includes(search) || 
          item.type?.toLowerCase().includes(search)
        )
      }
      dictTypeList.value = list
    }
  } finally {
    typeLoading.value = false
  }
}

// 选择字典类型
const handleSelectType = (item: SysDictTypeVo) => {
  selectedTypeId.value = item.id || null
  selectedTypeName.value = item.name || ''
  loadDictData()
}

// 加载字典数据
const loadDictData = async () => {
  if (!selectedTypeId.value) {
    dictDataList.value = []
    return
  }
  dataLoading.value = true
  try {
    const res = await getDictDataPage({
      page: 0,
      pageSize: 100,
      dictTypeId: selectedTypeId.value,
      label: dataSearchText.value || undefined,
      value: dataSearchText.value || undefined,
    })
    if (res.code === 200) {
      dictDataList.value = res.data?.data || []
    }
  } finally {
    dataLoading.value = false
  }
}

// 字典类型操作
const handleAddType = () => {
  isEditType.value = false
  Object.assign(typeFormData, { id: undefined, name: '', type: '', status: 0, remark: '' })
  typeModalVisible.value = true
}

const handleEditType = async (record: SysDictTypeVo) => {
  isEditType.value = true
  Object.assign(typeFormData, { ...record })
  typeModalVisible.value = true
}

const handleTypeSubmit = async () => {
  try {
    await typeFormRef.value?.validate()
    typeSubmitLoading.value = true
    requestId.value = getRequestId();
    if (isEditType.value) {
      const res = await updateDictType(typeFormData as SysDictTypeUpdate, requestId.value)
      if (res.code === 200) {
        message.success('字典类型更新成功')
        typeModalVisible.value = false
        loadDictTypes()
      }
    } else {
      const res = await createDictType(typeFormData as SysDictTypeCreate, requestId.value)
      if (res.code === 200) {
        message.success('字典类型创建成功')
        typeModalVisible.value = false
        loadDictTypes()
      }
    }
  } finally {
    typeSubmitLoading.value = false
  }
}

const handleTypeCancel = () => {
  typeModalVisible.value = false
  nextTick(() => { typeFormRef.value?.resetFields() })
}

const handleDeleteType = (record: SysDictTypeVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `将删除字典类型「${record.name}」，同时删除该类型下的所有字典数据，确定继续？`,
    okText: '确认删除',
    okType: 'danger',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteDictType(record.id!)
      if (res.code === 200) {
        message.success('删除成功')
        if (selectedTypeId.value === record.id) {
          selectedTypeId.value = null
          selectedTypeName.value = ''
          dictDataList.value = []
        }
        loadDictTypes()
      }
    },
  })
}

// 字典数据操作
const handleAddData = () => {
  if (!selectedTypeId.value) {
    message.warning('请先选择字典类型')
    return
  }
  isEditData.value = false
  Object.assign(dataFormData, { 
    id: undefined, label: '', value: '', 
    dictTypeId: selectedTypeId.value, sort: 0, status: 0, remark: '' 
  })
  dataModalVisible.value = true
}

const handleEditData = (record: SysDictDataVo) => {
  isEditData.value = true
  Object.assign(dataFormData, { ...record })
  dataModalVisible.value = true
}

const handleDataSubmit = async () => {
  try {
    await dataFormRef.value?.validate()
    dataSubmitLoading.value = true
    requestId.value = getRequestId();
    if (isEditData.value) {
      const res = await updateDictData(dataFormData as SysDictDataUpdate, requestId.value)
      if (res.code === 200) {
        message.success('字典数据更新成功')
        dataModalVisible.value = false
        loadDictData()
      }
    } else {
      const res = await createDictData(dataFormData as SysDictDataCreate, requestId.value)
      if (res.code === 200) {
        message.success('字典数据创建成功')
        dataModalVisible.value = false
        loadDictData()
      }
    }
  } finally {
    dataSubmitLoading.value = false
  }
}

const handleDataCancel = () => {
  dataModalVisible.value = false
  nextTick(() => { dataFormRef.value?.resetFields() })
}

const handleDeleteData = (record: SysDictDataVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `将删除字典数据「${record.label}」，确定继续？`,
    okText: '确认删除',
    okType: 'danger',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteDictData(record.id!)
      if (res.code === 200) {
        message.success('删除成功')
        loadDictData()
      }
    },
  })
}

const handleStatusChange = async (record: SysDictDataVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  requestId.value = getRequestId();
  const res = await updateDictData({ ...record, status: newStatus } as SysDictDataUpdate, requestId.value)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '已启用' : '已禁用')
  }
}

onMounted(() => {
  loadDictTypes()
  loadButtonPermissions()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.sysdict-container {
  display: flex;
  height: calc(100vh - 180px);
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
}

:global(html.dark) .sysdict-container {
  background: #1e293b;
}

/* 左侧面板 */
.dict-type-panel {
  width: 300px;
  border-right: 1px solid #e5e7eb;
  display: flex;
  flex-direction: column;
  background: #fafafa;
}

:global(html.dark) .dict-type-panel {
  background: #0f172a;
  border-right-color: #334155;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  border-bottom: 1px solid #e5e7eb;
}

:global(html.dark) .panel-header {
  border-bottom-color: #334155;
}

.panel-title {
  font-weight: 600;
  font-size: 14px;
  color: #333;
}

:global(html.dark) .panel-title {
  color: #e2e8f0;
}

.selected-type-name {
  font-weight: 400;
  color: #6366f1;
  font-size: 13px;
}

.add-btn {
  height: 28px;
  font-size: 12px;
}

.type-search {
  padding: 12px;
  border-bottom: 1px solid #e5e7eb;
}

:global(html.dark) .type-search {
  border-bottom-color: #334155;
}

.type-list {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
}

.type-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 12px;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;
  margin-bottom: 4px;
}

.type-item:hover {
  background: #e5e7eb;
}

:global(html.dark) .type-item:hover {
  background: #334155;
}

.type-item.active {
  background: var(--app-primary-color);
  color: #fff;
}

:global(html.dark) .type-item.active {
  background: var(--app-primary-color);
}

.type-item.active .type-code,
:global(html.dark) .type-item.active .type-code {
  color: rgba(255,255,255,0.7);
}

.type-info {
  display: flex;
  align-items: center;
  gap: 10px;
  overflow: hidden;
}

.type-icon {
  font-size: 16px;
  color: var(--app-primary-color);
}

.type-item.active .type-icon {
  color: #fff;
}

.type-content {
  overflow: hidden;
}

.type-name {
  font-weight: 500;
  font-size: 13px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.type-code {
  font-size: 11px;
  color: #64748b;
  font-family: 'Monaco', 'Menlo', monospace;
}

.type-actions {
  display: none;
  gap: 8px;
}

.type-item:hover .type-actions {
  display: flex;
}

.type-item.active .type-actions {
  display: flex;
}

.action-icon {
  cursor: pointer;
  font-size: 13px;
  color: inherit;
}

.action-icon.delete:hover {
  color: #ff4d4f;
}

/* 右侧面板 */
.dict-data-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.dict-label {
  display: flex;
  align-items: center;
  gap: 8px;
}

.label-icon {
  color: var(--app-primary-color);
}

.text-code {
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
  background: #f1f5f9;
  padding: 2px 8px;
  border-radius: 4px;
  color: #475569;
}

:global(html.dark) .text-code {
  background: #334155;
  color: #e2e8f0;
}

/* 弹窗样式 */
.modal-inner {
  padding: 0;
}

.modal-header {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px 24px;
  border-bottom: 1px solid #e5e7eb;
}

:global(html.dark) .modal-header {
  border-bottom-color: #334155;
}

.modal-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.add-icon {
  background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%);
  color: #fff;
}

.edit-icon {
  background: linear-gradient(135deg, #10b981 0%, #34d399 100%);
  color: #fff;
}

.modal-titles h2 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
}

:global(html.dark) .modal-titles h2 {
  color: #e2e8f0;
}

.modal-titles p {
  margin: 4px 0 0;
  font-size: 13px;
  color: #64748b;
}

.glass-form {
  padding: 20px 24px;
}

.modal-footer-custom {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding: 16px 24px;
  border-top: 1px solid #e5e7eb;
}

:global(html.dark) .modal-footer-custom {
  border-top-color: #334155;
}
</style>
