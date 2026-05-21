<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">品牌名称</span>
            <a-input v-model:value="queryParams.name" placeholder="输入搜索..." class="elegant-input" allow-clear>
              <template #prefix><SearchOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">品牌编码</span>
            <a-input v-model:value="queryParams.code" placeholder="输入搜索..." class="elegant-input" allow-clear>
              <template #prefix><KeyOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">首字母</span>
            <a-input v-model:value="queryParams.firstLetter" placeholder="输入搜索..." class="elegant-input" allow-clear />
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
            <div class="tab active">全部品牌 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增品牌
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
          :scroll="{ x: 1000 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'logo'">
              <a-image v-if="record.logo" :src="resolveLogo(record.logo)" :width="40" :height="40" />
              <span v-else class="text-secondary">-</span>
            </template>
            <template v-else-if="column.key === 'status'">
              <a-tag :color="record.status === 1 ? 'green' : 'red'">{{ getDictLabel('status', record.status) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'showInHome'">
              <a-tag :color="record.showInHome === 1 ? 'blue' : 'default'">{{ getDictLabel('yes_no', record.showInHome) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑品牌' : '新增品牌'" width="700px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="12"><a-form-item label="品牌名称" name="name"><a-input v-model:value="formData.name" placeholder="请输入品牌名称" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="品牌编码" name="code"><a-input v-model:value="formData.code" placeholder="请输入品牌编码" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="LOGO" name="logo"><ImageUpload v-model="formData.logo" value-type="id" :limit="1" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="首字母" name="firstLetter"><a-input v-model:value="formData.firstLetter" placeholder="请输入首字母" :maxlength="1" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="排序" name="sort"><a-input-number v-model:value="formData.sort" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="首页展示" name="showInHome">
            <a-select v-model:value="formData.showInHome">
              <a-select-option v-for="item in yesNoOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="12"><a-form-item label="状态" name="status">
            <a-select v-model:value="formData.status">
              <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="24"><a-form-item label="描述" name="description"><a-textarea v-model:value="formData.description" :rows="3" /></a-form-item></a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { message, Modal } from 'ant-design-vue'
import { SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, EditOutlined, KeyOutlined } from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { MallProductBrandVo, MallProductBrandQuery, MallProductBrandCreate, MallProductBrandUpdate } from '@/api/mall/mallproductbrand.ts'
import { getBrandPage, createBrand, updateBrand, deleteBrand, batchDeleteBrand, getBrandById } from '@/api/mall/mallproductbrand.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'
import ImageUpload from '@/components/ImageUpload/index.vue'
import { getFileUrl } from '@/api/file/fileupload'

const resolveLogo = (val: any) => {
  if (!val) return ''
  const str = String(val)
  if (str.startsWith('http') || str.startsWith('/')) return str
  return getFileUrl(val)
}

const statusOptions = computed(() => (getDictData('status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const yesNoOptions = computed(() => (getDictData('yes_no') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: '品牌名称', dataIndex: 'name', key: 'name', width: 160 },
  { title: '编码', dataIndex: 'code', key: 'code', width: 120 },
  { title: 'LOGO', dataIndex: 'logo', key: 'logo', width: 80 },
  { title: '首字母', dataIndex: 'firstLetter', key: 'firstLetter', width: 80 },
  { title: '排序', dataIndex: 'sort', key: 'sort', width: 80 },
  { title: '首页展示', dataIndex: 'showInHome', key: 'showInHome', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<MallProductBrandVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallProductBrandQuery>({ page: 1, pageSize: 10 })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const formData = reactive<MallProductBrandCreate & { id?: number }>({ id: undefined, name: '', code: '', logo: '', firstLetter: '', description: '', sort: 0, showInHome: 0, status: 1 })
const rules = { name: [{ required: true, message: '请输入品牌名称', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getBrandPage({ ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize })
    if (res.code === 200) { dataSource.value = res.data?.data || []; pagination.total = res.data?.total || 0 }
  } finally { loading.value = false }
}
const handleTableChange = (pag: any) => { pagination.current = pag.current; pagination.pageSize = pag.pageSize; loadData() }
const reset = () => { Object.assign(queryParams, { name: '', code: '', firstLetter: '', status: undefined }); pagination.current = 1; loadData() }
const onSelectChange = (keys: number[]) => { selectedRowKeys.value = keys }
const handleAdd = () => { isEdit.value = false; Object.assign(formData, { id: undefined, name: '', code: '', logo: '', firstLetter: '', description: '', sort: 0, showInHome: 0, status: 1 }); modalVisible.value = true }
const handleEdit = async (r: MallProductBrandVo) => { isEdit.value = true; const res: any = await getBrandById(r.id!); if (res.code === 200) Object.assign(formData, res.data); modalVisible.value = true }
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const res: any = isEdit.value ? await updateBrand(formData as MallProductBrandUpdate, reqId) : await createBrand({ ...formData }, reqId)
    if (res.code === 200) { message.success(isEdit.value ? '更新成功' : '创建成功'); modalVisible.value = false; loadData() }
  } finally { submitLoading.value = false }
}
const handleCancel = () => { modalVisible.value = false; setTimeout(() => formRef.value?.resetFields(), 300) }
const handleDeleteOne = (r: MallProductBrandVo) => {
  Modal.confirm({ title: '确认删除', content: `确定删除品牌「${r.name}」？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await deleteBrand(r.id!); if (res.code === 200) { message.success('删除成功'); loadData() } } })
}
const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({ title: '批量删除', content: `确定删除 ${selectedRowKeys.value.length} 项？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await batchDeleteBrand(selectedRowKeys.value); if (res.code === 200) { message.success('删除成功'); selectedRowKeys.value = []; loadData() } } })
}
onMounted(() => { loadData() })
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
