<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">门店名称</span>
            <a-input v-model:value="queryParams.name" placeholder="输入搜索..." class="elegant-input" allow-clear />
          </div>
          <div class="filter-item">
            <span class="filter-label">店铺ID</span>
            <a-input-number v-model:value="queryParams.shopId" placeholder="店铺ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">省份</span>
            <a-input v-model:value="queryParams.province" placeholder="输入搜索..." class="elegant-input" allow-clear />
          </div>
          <div class="filter-item">
            <span class="filter-label">城市</span>
            <a-input v-model:value="queryParams.city" placeholder="输入搜索..." class="elegant-input" allow-clear />
          </div>
          <div class="filter-item">
            <span class="filter-label">状态</span>
            <a-select v-model:value="queryParams.status" placeholder="全部" class="elegant-input" allow-clear style="width:100%">
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
            <div class="tab active">自提门店 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增门店
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
          :scroll="{ x: 1500 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'status'">
              <a-tag :color="record.status === 1 ? 'green' : 'red'">{{ getDictLabel('status', record.status) }}</a-tag>
            </template>
            <template v-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑门店' : '新增门店'" width="800px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="12"><a-form-item label="店铺ID" name="shopId"><a-input-number v-model:value="formData.shopId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="门店名称" name="name"><a-input v-model:value="formData.name" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="门店编码" name="code"><a-input v-model:value="formData.code" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="联系人" name="contactName"><a-input v-model:value="formData.contactName" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="联系电话" name="contactPhone"><a-input v-model:value="formData.contactPhone" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="邮编" name="postalCode"><a-input v-model:value="formData.postalCode" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="省份" name="province"><a-input v-model:value="formData.province" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="城市" name="city"><a-input v-model:value="formData.city" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="区县" name="district"><a-input v-model:value="formData.district" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="省份编码" name="provinceCode"><a-input v-model:value="formData.provinceCode" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="城市编码" name="cityCode"><a-input v-model:value="formData.cityCode" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="区县编码" name="districtCode"><a-input v-model:value="formData.districtCode" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="详细地址" name="detailAddress"><a-input v-model:value="formData.detailAddress" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="经度" name="longitude"><a-input-number v-model:value="formData.longitude" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="纬度" name="latitude"><a-input-number v-model:value="formData.latitude" style="width:100%" /></a-form-item></a-col>
          <a-col :span="8"><a-form-item label="排序" name="sort"><a-input-number v-model:value="formData.sort" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="营业时间" name="openTime"><a-input v-model:value="formData.openTime" placeholder="如 09:00-21:00" /></a-form-item></a-col>
          <a-col :span="12">
            <a-form-item label="状态" name="status">
              <a-select v-model:value="formData.status" placeholder="请选择" style="width:100%">
                <a-select-option v-for="item in statusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="24"><a-form-item label="自提说明" name="pickupNotice"><a-textarea v-model:value="formData.pickupNotice" :rows="2" /></a-form-item></a-col>
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
import type { MallPickupShopVo, MallPickupShopQuery, MallPickupShopCreate, MallPickupShopUpdate } from '@/api/mall/mallpickupshop.ts'
import { getPickupShopPage, createPickupShop, updatePickupShop, deletePickupShop, batchDeletePickupShop, getPickupShopById } from '@/api/mall/mallpickupshop.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const statusOptions = computed(() => (getDictData('status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '门店名称', dataIndex: 'name', key: 'name', width: 180 },
  { title: '门店编码', dataIndex: 'code', key: 'code', width: 140 },
  { title: '联系人', dataIndex: 'contactName', key: 'contactName', width: 120 },
  { title: '电话', dataIndex: 'contactPhone', key: 'contactPhone', width: 140 },
  { title: '省', dataIndex: 'province', key: 'province', width: 100 },
  { title: '市', dataIndex: 'city', key: 'city', width: 100 },
  { title: '区县', dataIndex: 'district', key: 'district', width: 100 },
  { title: '地址', dataIndex: 'detailAddress', key: 'detailAddress', width: 200, ellipsis: true },
  { title: '排序', dataIndex: 'sort', key: 'sort', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<MallPickupShopVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallPickupShopQuery>({ page: 1, pageSize: 10 })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const defaultForm = (): MallPickupShopCreate & { id?: number } => ({ id: undefined, shopId: 0, name: '', code: '', contactName: '', contactPhone: '', province: '', provinceCode: '', city: '', cityCode: '', district: '', districtCode: '', detailAddress: '', postalCode: '', longitude: 0, latitude: 0, openTime: '', pickupNotice: '', sort: 0, status: 1, remark: '' })
const formData = reactive<MallPickupShopCreate & { id?: number }>(defaultForm())
const rules = { name: [{ required: true, message: '请输入门店名称', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getPickupShopPage({ ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize })
    if (res.code === 200) { dataSource.value = res.data?.data || []; pagination.total = res.data?.total || 0 }
  } finally { loading.value = false }
}
const handleTableChange = (pag: any) => { pagination.current = pag.current; pagination.pageSize = pag.pageSize; loadData() }
const reset = () => { Object.assign(queryParams, { name: '', shopId: undefined, province: '', city: '', status: undefined }); pagination.current = 1; loadData() }
const onSelectChange = (keys: number[]) => { selectedRowKeys.value = keys }
const handleAdd = () => { isEdit.value = false; Object.assign(formData, defaultForm()); modalVisible.value = true }
const handleEdit = async (r: MallPickupShopVo) => { isEdit.value = true; const res: any = await getPickupShopById(r.id!); if (res.code === 200) Object.assign(formData, res.data); modalVisible.value = true }
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const res: any = isEdit.value ? await updatePickupShop(formData as MallPickupShopUpdate, reqId) : await createPickupShop({ ...formData }, reqId)
    if (res.code === 200) { message.success(isEdit.value ? '更新成功' : '创建成功'); modalVisible.value = false; loadData() }
  } finally { submitLoading.value = false }
}
const handleCancel = () => { modalVisible.value = false; setTimeout(() => formRef.value?.resetFields(), 300) }
const handleDeleteOne = (r: MallPickupShopVo) => {
  Modal.confirm({ title: '确认删除', content: `确定删除该门店？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await deletePickupShop(r.id!); if (res.code === 200) { message.success('删除成功'); loadData() } } })
}
const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({ title: '批量删除', content: `确定删除 ${selectedRowKeys.value.length} 项？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await batchDeletePickupShop(selectedRowKeys.value); if (res.code === 200) { message.success('删除成功'); selectedRowKeys.value = []; loadData() } } })
}
onMounted(() => { loadData() })
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
