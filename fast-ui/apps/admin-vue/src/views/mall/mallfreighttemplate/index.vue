<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">模板名称</span>
            <a-input v-model:value="queryParams.name" placeholder="输入搜索..." class="elegant-input" allow-clear>
              <template #prefix><SearchOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">店铺ID</span>
            <a-input-number v-model:value="queryParams.shopId" placeholder="店铺ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">计费类型</span>
            <a-select v-model:value="queryParams.chargeType" placeholder="全部" class="elegant-select" :bordered="false" allow-clear>
              <a-select-option v-for="item in chargeTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">状态</span>
            <a-select v-model:value="queryParams.status" placeholder="全部状态" class="elegant-select" :bordered="false" allow-clear>
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
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
            <div class="tab active">运费模板 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增模板
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
          :scroll="{ x: 1300 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'chargeType'">
              <a-tag>{{ getDictLabel('mall_freight_charge_type', record.chargeType) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'isDefault'">
              <a-tag :color="record.isDefault === 1 ? 'gold' : 'default'">{{ record.isDefault === 1 ? '默认' : '-' }}</a-tag>
            </template>
            <template v-else-if="column.key === 'status'">
              <a-tag :color="record.status === 1 ? 'green' : 'red'">{{ getDictLabel('status', record.status) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑模板' : '新增模板'" width="900px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="12"><a-form-item label="模板名称" name="name"><a-input v-model:value="formData.name" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="店铺ID" name="shopId"><a-input-number v-model:value="formData.shopId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="计费类型" name="chargeType">
            <a-select v-model:value="formData.chargeType">
              <a-select-option v-for="item in chargeTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="包邮类型" name="freeType">
            <a-select v-model:value="formData.freeType">
              <a-select-option v-for="item in freeTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="是否默认" name="isDefault">
            <a-select v-model:value="formData.isDefault">
              <a-select-option v-for="item in yesNoOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="8"><a-form-item label="包邮金额" name="freeAmount"><a-input-number v-model:value="formData.freeAmount" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="包邮件数" name="freeQuantity"><a-input-number v-model:value="formData.freeQuantity" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="包邮重量" name="freeWeight"><a-input-number v-model:value="formData.freeWeight" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="首件/重" name="defaultFirstUnit"><a-input-number v-model:value="formData.defaultFirstUnit" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="首费" name="defaultFirstFee"><a-input-number v-model:value="formData.defaultFirstFee" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="续件/重" name="defaultAdditionalUnit"><a-input-number v-model:value="formData.defaultAdditionalUnit" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="续费" name="defaultAdditionalFee"><a-input-number v-model:value="formData.defaultAdditionalFee" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="排序" name="sort"><a-input-number v-model:value="formData.sort" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="状态" name="status">
            <a-select v-model:value="formData.status">
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="24"><a-form-item label="区域规则JSON" name="regionRules"><a-textarea v-model:value="formData.regionRules" :rows="2" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="不发货区域" name="excludeRegions"><a-textarea v-model:value="formData.excludeRegions" :rows="2" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="包邮区域" name="freeRegionRules"><a-textarea v-model:value="formData.freeRegionRules" :rows="2" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="备注" name="remark"><a-textarea v-model:value="formData.remark" :rows="2" /></a-form-item></a-col>
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
import type { MallFreightTemplateVo, MallFreightTemplateQuery, MallFreightTemplateCreate, MallFreightTemplateUpdate } from '@/api/mall/mallfreighttemplate.ts'
import { getFreightTemplatePage, createFreightTemplate, updateFreightTemplate, deleteFreightTemplate, batchDeleteFreightTemplate, getFreightTemplateById } from '@/api/mall/mallfreighttemplate.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const chargeTypeOptions = computed(() => (getDictData('mall_freight_charge_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const freeTypeOptions = computed(() => (getDictData('mall_freight_free_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const statusOptions = computed(() => (getDictData('status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const yesNoOptions = computed(() => (getDictData('yes_no') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '模板名称', dataIndex: 'name', key: 'name', width: 180 },
  { title: '店铺ID', dataIndex: 'shopId', key: 'shopId', width: 100 },
  { title: '计费类型', dataIndex: 'chargeType', key: 'chargeType', width: 100 },
  { title: '默认', dataIndex: 'isDefault', key: 'isDefault', width: 80 },
  { title: '排序', dataIndex: 'sort', key: 'sort', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<MallFreightTemplateVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallFreightTemplateQuery>({ page: 1, pageSize: 10 })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const defaultForm = (): MallFreightTemplateCreate & { id?: number } => ({ id: undefined, name: '', shopId: 0, chargeType: 1, freeType: 0, freeAmount: 0, freeQuantity: 0, freeWeight: 0, defaultFirstUnit: 1, defaultFirstFee: 0, defaultAdditionalUnit: 1, defaultAdditionalFee: 0, regionRules: '', excludeRegions: '', freeRegionRules: '', isDefault: 0, status: 1, sort: 0, remark: '' })
const formData = reactive<MallFreightTemplateCreate & { id?: number }>(defaultForm())
const rules = { name: [{ required: true, message: '请输入模板名称', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getFreightTemplatePage({ ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize })
    if (res.code === 200) { dataSource.value = res.data?.data || []; pagination.total = res.data?.total || 0 }
  } finally { loading.value = false }
}
const handleTableChange = (pag: any) => { pagination.current = pag.current; pagination.pageSize = pag.pageSize; loadData() }
const reset = () => { Object.assign(queryParams, { name: '', shopId: undefined, chargeType: undefined, status: undefined, isDefault: undefined }); pagination.current = 1; loadData() }
const onSelectChange = (keys: number[]) => { selectedRowKeys.value = keys }
const handleAdd = () => { isEdit.value = false; Object.assign(formData, defaultForm()); modalVisible.value = true }
const handleEdit = async (r: MallFreightTemplateVo) => { isEdit.value = true; const res: any = await getFreightTemplateById(r.id!); if (res.code === 200) Object.assign(formData, res.data); modalVisible.value = true }
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const res: any = isEdit.value ? await updateFreightTemplate(formData as MallFreightTemplateUpdate, reqId) : await createFreightTemplate({ ...formData }, reqId)
    if (res.code === 200) { message.success(isEdit.value ? '更新成功' : '创建成功'); modalVisible.value = false; loadData() }
  } finally { submitLoading.value = false }
}
const handleCancel = () => { modalVisible.value = false; setTimeout(() => formRef.value?.resetFields(), 300) }
const handleDeleteOne = (r: MallFreightTemplateVo) => {
  Modal.confirm({ title: '确认删除', content: `确定删除模板「${r.name}」？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await deleteFreightTemplate(r.id!); if (res.code === 200) { message.success('删除成功'); loadData() } } })
}
const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({ title: '批量删除', content: `确定删除 ${selectedRowKeys.value.length} 项？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await batchDeleteFreightTemplate(selectedRowKeys.value); if (res.code === 200) { message.success('删除成功'); selectedRowKeys.value = []; loadData() } } })
}
onMounted(() => { loadData() })
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
