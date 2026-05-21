<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">订单ID</span>
            <a-input-number v-model:value="queryParams.orderId" placeholder="订单ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">订单号</span>
            <a-input v-model:value="queryParams.orderNo" placeholder="输入搜索..." class="elegant-input" allow-clear />
          </div>
          <div class="filter-item">
            <span class="filter-label">用户ID</span>
            <a-input-number v-model:value="queryParams.userId" placeholder="用户ID" class="elegant-input" style="width:100%" />
          </div>
          <div class="filter-item">
            <span class="filter-label">商品ID</span>
            <a-input-number v-model:value="queryParams.productId" placeholder="商品ID" class="elegant-input" style="width:100%" />
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
            <div class="tab active">订单明细 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增明细
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
            <template v-if="column.key === 'commented'">
              <a-tag :color="record.commented === 1 ? 'green' : 'default'">{{ getDictLabel('yes_no', record.commented) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'refundStatus'">
              <a-tag :color="record.refundStatus === 0 ? 'default' : 'orange'">{{ getDictLabel('mall_item_refund_status', record.refundStatus) }}</a-tag>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)"><EditOutlined /> 编辑</a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)"><DeleteOutlined /> 删除</a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <a-modal v-model:open="modalVisible" :title="isEdit ? '编辑明细' : '新增明细'" width="900px" :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading">
      <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
        <a-row :gutter="16">
          <a-col :span="6"><a-form-item label="订单ID" name="orderId"><a-input-number v-model:value="formData.orderId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="12"><a-form-item label="订单号" name="orderNo"><a-input v-model:value="formData.orderNo" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="用户ID" name="userId"><a-input-number v-model:value="formData.userId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="店铺ID" name="shopId"><a-input-number v-model:value="formData.shopId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="商品ID" name="productId"><a-input-number v-model:value="formData.productId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="SKU ID" name="skuId"><a-input-number v-model:value="formData.skuId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="分类ID" name="categoryId"><a-input-number v-model:value="formData.categoryId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="品牌ID" name="brandId"><a-input-number v-model:value="formData.brandId" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="单价" name="price"><a-input-number v-model:value="formData.price" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="原价" name="originalPrice"><a-input-number v-model:value="formData.originalPrice" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="数量" name="quantity"><a-input-number v-model:value="formData.quantity" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="支付类型" name="payType">
            <a-select v-model:value="formData.payType" placeholder="请选择">
              <a-select-option v-for="item in payTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="6"><a-form-item label="积分价" name="pointsPrice"><a-input-number v-model:value="formData.pointsPrice" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="积分金额" name="pointsAmount"><a-input-number v-model:value="formData.pointsAmount" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="赠送积分" name="giftPointsAmount"><a-input-number v-model:value="formData.giftPointsAmount" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="配送方式" name="deliveryType">
            <a-select v-model:value="formData.deliveryType" placeholder="请选择">
              <a-select-option v-for="item in deliveryTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="6"><a-form-item label="虚拟类型" name="virtualType">
            <a-select v-model:value="formData.virtualType" placeholder="请选择">
              <a-select-option v-for="item in virtualTypeOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="6"><a-form-item label="优惠金额" name="discountAmount"><a-input-number v-model:value="formData.discountAmount" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="总金额" name="totalAmount"><a-input-number v-model:value="formData.totalAmount" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="退款状态" name="refundStatus">
            <a-select v-model:value="formData.refundStatus" placeholder="请选择">
              <a-select-option v-for="item in itemRefundStatusOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="6"><a-form-item label="退款金额" name="refundAmount"><a-input-number v-model:value="formData.refundAmount" :min="0" :precision="2" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="退款数量" name="refundQuantity"><a-input-number v-model:value="formData.refundQuantity" :min="0" style="width:100%" /></a-form-item></a-col>
          <a-col :span="6"><a-form-item label="是否评价" name="commented">
            <a-select v-model:value="formData.commented">
              <a-select-option v-for="item in yesNoOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item></a-col>
          <a-col :span="24"><a-form-item label="备注" name="remark"><a-textarea v-model:value="formData.remark" :rows="2" /></a-form-item></a-col>
          <a-col :span="24"><a-form-item label="扩展" name="extra"><a-textarea v-model:value="formData.extra" :rows="2" /></a-form-item></a-col>
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
import type { MallOrderItemVo, MallOrderItemQuery, MallOrderItemCreate, MallOrderItemUpdate } from '@/api/mall/mallorderitem.ts'
import { getOrderItemPage, createOrderItem, updateOrderItem, deleteOrderItem, batchDeleteOrderItem, getOrderItemById } from '@/api/mall/mallorderitem.ts'
import { getRequestId } from '@/utils/idUtils.ts'
import { getDictData, getDictLabel } from '@/utils/dict.ts'

const payTypeOptions = computed(() => (getDictData('mall_pay_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const deliveryTypeOptions = computed(() => (getDictData('mall_delivery_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const virtualTypeOptions = computed(() => (getDictData('mall_virtual_type') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const itemRefundStatusOptions = computed(() => (getDictData('mall_item_refund_status') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))
const yesNoOptions = computed(() => (getDictData('yes_no') || []).map((d: any) => ({ value: Number(d.value), label: d.label })))

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '订单号', dataIndex: 'orderNo', key: 'orderNo', width: 200 },
  { title: '商品ID', dataIndex: 'productId', key: 'productId', width: 100 },
  { title: 'SKU ID', dataIndex: 'skuId', key: 'skuId', width: 100 },
  { title: '单价', dataIndex: 'price', key: 'price', width: 100 },
  { title: '数量', dataIndex: 'quantity', key: 'quantity', width: 80 },
  { title: '总额', dataIndex: 'totalAmount', key: 'totalAmount', width: 100 },
  { title: '退款', dataIndex: 'refundStatus', key: 'refundStatus', width: 100 },
  { title: '评价', dataIndex: 'commented', key: 'commented', width: 80 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<MallOrderItemVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallOrderItemQuery>({ page: 1, pageSize: 10 })
const pagination = reactive({ current: 1, pageSize: 10, total: 0, showSizeChanger: true, showTotal: (t: number) => `共 ${t} 条` })
const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const defaultForm = (): MallOrderItemCreate & { id?: number } => ({ id: undefined, orderId: 0, orderNo: '', userId: 0, shopId: 0, productId: 0, skuId: 0, categoryId: 0, brandId: 0, productSnapshot: '', skuSnapshot: '', price: 0, originalPrice: 0, payType: 0, pointsPrice: 0, pointsAmount: 0, giftPointsAmount: 0, deliveryType: 0, virtualType: 0, quantity: 1, discountAmount: 0, totalAmount: 0, refundStatus: 0, refundAmount: 0, refundQuantity: 0, commented: 0, remark: '', extra: '' })
const formData = reactive<MallOrderItemCreate & { id?: number }>(defaultForm())
const rules = { orderNo: [{ required: true, message: '请输入订单号', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getOrderItemPage({ ...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize })
    if (res.code === 200) { dataSource.value = res.data?.data || []; pagination.total = res.data?.total || 0 }
  } finally { loading.value = false }
}
const handleTableChange = (pag: any) => { pagination.current = pag.current; pagination.pageSize = pag.pageSize; loadData() }
const reset = () => { Object.assign(queryParams, { orderId: undefined, orderNo: '', userId: undefined, shopId: undefined, productId: undefined, skuId: undefined, refundStatus: undefined, commented: undefined }); pagination.current = 1; loadData() }
const onSelectChange = (keys: number[]) => { selectedRowKeys.value = keys }
const handleAdd = () => { isEdit.value = false; Object.assign(formData, defaultForm()); modalVisible.value = true }
const handleEdit = async (r: MallOrderItemVo) => { isEdit.value = true; const res: any = await getOrderItemById(r.id!); if (res.code === 200) Object.assign(formData, res.data); modalVisible.value = true }
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    const reqId = getRequestId()
    const res: any = isEdit.value ? await updateOrderItem(formData as MallOrderItemUpdate, reqId) : await createOrderItem({ ...formData }, reqId)
    if (res.code === 200) { message.success(isEdit.value ? '更新成功' : '创建成功'); modalVisible.value = false; loadData() }
  } finally { submitLoading.value = false }
}
const handleCancel = () => { modalVisible.value = false; setTimeout(() => formRef.value?.resetFields(), 300) }
const handleDeleteOne = (r: MallOrderItemVo) => {
  Modal.confirm({ title: '确认删除', content: `确定删除该明细？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await deleteOrderItem(r.id!); if (res.code === 200) { message.success('删除成功'); loadData() } } })
}
const handleDelete = () => {
  if (!selectedRowKeys.value.length) return
  Modal.confirm({ title: '批量删除', content: `确定删除 ${selectedRowKeys.value.length} 项？`, okType: 'danger', centered: true, onOk: async () => { const res: any = await batchDeleteOrderItem(selectedRowKeys.value); if (res.code === 200) { message.success('删除成功'); selectedRowKeys.value = []; loadData() } } })
}
onMounted(() => { loadData() })
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
