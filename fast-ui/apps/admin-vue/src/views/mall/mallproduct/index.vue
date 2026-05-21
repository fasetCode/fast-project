<template>
  <div class="mall-container">
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">商品名称</span>
            <a-input v-model:value="queryParams.name" placeholder="输入搜索..." class="elegant-input" allow-clear>
              <template #prefix>
                <SearchOutlined class="text-secondary"/>
              </template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">店铺</span>
            <ShopPicker v-model="queryParams.shopId" placeholder="请选择店铺" />
          </div>
          <div class="filter-item">
            <span class="filter-label">分类</span>
            <CategoryTreePicker v-model="queryParams.categoryId" placeholder="请选择分类" />
          </div>
          <div class="filter-item">
            <span class="filter-label">品牌</span>
            <BrandPicker v-model="queryParams.brandId" placeholder="请选择品牌" />
          </div>
          <div class="filter-item">
            <span class="filter-label">状态</span>
            <a-select v-model:value="queryParams.status" placeholder="全部状态" class="elegant-select" :bordered="false"
                      allow-clear>
              <a-select-option v-for="item in productStatusOptions" :key="item.value" :value="item.value">{{
                  item.label
                }}
              </a-select-option>
            </a-select>
          </div>
          <div class="filter-actions">
            <a-button class="icon-btn refresh-btn" @click="reset()" shape="circle" :loading="loading">
              <template #icon v-if="!loading">
                <ReloadOutlined/>
              </template>
            </a-button>
            <a-button type="primary" class="gradient-btn" @click="loadData()" :loading="loading">
              <template #icon v-if="!loading">
                <SearchOutlined/>
              </template>
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
            <div class="tab active">全部商品 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined/>
            新增商品
          </a-button>
          <a-button danger class="pill-btn danger-pill" :disabled="selectedRowKeys.length === 0"
                    @click="handleDelete()">
            <DeleteOutlined/>
            批量删除
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
            <template v-if="column.key === 'productInfo'">
              <div class="product-cell">
                <ImageDisplay :value="record.mainImage" valueType="id" :width="64" :height="64" class="product-thumb" />
                <div class="product-main">
                  <div class="product-name-row">
                    <span class="product-name">{{ record.name || '-' }}</span>
                    <a-tag v-if="record.status === 1" color="green">上架中</a-tag>
                    <a-tag v-else color="default">已停用</a-tag>
                  </div>
                  <div v-if="record.subTitle" class="product-subtitle">{{ record.subTitle }}</div>
                  <div class="product-meta">
                    <span>编号：{{ record.productSn || '-' }}</span>
                    <span>ID：{{ record.id }}</span>
                  </div>
                </div>
              </div>
            </template>
            <template v-else-if="column.key === 'belonging'">
              <div class="stack-info">
                <div class="stack-item">
                  <span class="stack-label">店铺</span>
                  <span class="stack-value">{{ record.shopName || '-' }}</span>
                </div>
                <div class="stack-item">
                  <span class="stack-label">分类</span>
                  <span class="stack-value">{{ record.categoryName || '-' }}</span>
                </div>
                <div class="stack-item">
                  <span class="stack-label">品牌</span>
                  <span class="stack-value">{{ record.brandName || '-' }}</span>
                </div>
              </div>
            </template>
            <template v-else-if="column.key === 'priceStock'">
              <div class="stack-info">
                <div class="price-row">
                  <span class="price-main">¥{{ formatMoney(record.price) }}</span>
                  <span v-if="record.originalPrice !== undefined && record.originalPrice !== null" class="price-original">
                    ¥{{ formatMoney(record.originalPrice) }}
                  </span>
                </div>
                <div class="stack-item">
                  <span class="stack-label">库存</span>
                  <span class="stack-value">{{ record.stock ?? 0 }} {{ record.unit || '件' }}</span>
                </div>
                <div class="stack-item">
                  <span class="stack-label">销量</span>
                  <span class="stack-value">{{ record.sales ?? 0 }}</span>
                </div>
              </div>
            </template>
            <template v-else-if="column.key === 'featureTags'">
              <div class="tag-group">
                <a-tag :color="record.isNew === 1 ? 'blue' : 'default'">{{ record.isNew === 1 ? '新品' : '非新品' }}</a-tag>
                <a-tag :color="record.isHot === 1 ? 'orange' : 'default'">{{ record.isHot === 1 ? '热销' : '普通' }}</a-tag>
                <a-tag :color="record.isRecommend === 1 ? 'purple' : 'default'">{{ record.isRecommend === 1 ? '推荐' : '未推荐' }}</a-tag>
                <a-tag :color="record.status === 1 ? 'green' : 'red'">
                  {{ getDictLabel('mall_product_status', record.status) }}
                </a-tag>
              </div>
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button type="link" size="small" @click="handleEdit(record)">
                <EditOutlined/>
                编辑
              </a-button>
              <a-button type="link" size="small" danger @click="handleDeleteOne(record)">
                <DeleteOutlined/>
                删除
              </a-button>
            </template>
          </template>
        </a-table>
      </div>
    </div>

    <update-or-add ref="updateOrAddRef" @success="loadData" />
  </div>
</template>

<script setup lang="ts">
import {ref, reactive, computed, onMounted} from 'vue'
import {message, Modal} from 'ant-design-vue'
import {SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, EditOutlined} from '@ant-design/icons-vue'
import type {MallProductVo, MallProductQuery} from '@/api/mall/mallproduct.ts'
import {
  getProductPage,
  deleteProduct,
  batchDeleteProduct
} from '@/api/mall/mallproduct.ts'
import {getDictData, getDictLabel} from '@/utils/dict.ts'
import ImageDisplay from '@/components/ImageDisplay/index.vue'
import UpdateOrAdd from './updateOrAdd.vue'
import ShopPicker from '@/views/mall/components/ShopPicker.vue'
import CategoryTreePicker from '@/views/mall/components/CategoryTreePicker.vue'
import BrandPicker from '@/views/mall/components/BrandPicker.vue'

const productStatusOptions = computed(() => (getDictData('mall_product_status') || []).map((d: any) => ({
  value: Number(d.value),
  label: d.label
})))

const formatMoney = (value?: number) => {
  if (value === undefined || value === null || Number.isNaN(Number(value))) return '0.00'
  return Number(value).toFixed(2)
}

const columns = [
  {title: '商品信息', key: 'productInfo', width: 360},
  {title: '归属信息', key: 'belonging', width: 230},
  {title: '价格库存', key: 'priceStock', width: 190},
  {title: '商品标签', key: 'featureTags', width: 240},
  {title: '操作', key: 'action', width: 160, fixed: 'right' as const, align: 'center' as const},
]

const loading = ref(false)
const dataSource = ref<MallProductVo[]>([])
const selectedRowKeys = ref<number[]>([])
const queryParams = reactive<MallProductQuery>({page: 1, pageSize: 10})
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (t: number) => `共 ${t} 条`
})
const updateOrAddRef = ref()

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getProductPage({...queryParams, page: pagination.current - 1, pageSize: pagination.pageSize})
    if (res.code === 200) {
      dataSource.value = res.data?.data || [];
      pagination.total = res.data?.total || 0
    }
  } finally {
    loading.value = false
  }
}
const handleTableChange = (pag: any) => {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  loadData()
}
const reset = () => {
  Object.assign(queryParams, {
    name: '',
    shopId: undefined,
    categoryId: undefined,
    brandId: undefined,
    status: undefined
  });
  pagination.current = 1;
  loadData()
}
const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}
const handleAdd = () => {
  updateOrAddRef.value?.openAdd()
}
const handleEdit = (r: MallProductVo) => {
  updateOrAddRef.value?.openEdit(r.id!)
}
const handleDeleteOne = (r: MallProductVo) => {
  Modal.confirm({
    title: '确认删除',
    content: `确定删除商品「${r.name}」？`,
    okType: 'danger',
    centered: true,
    onOk: async () => {
      const res: any = await deleteProduct(r.id!);
      if (res.code === 200) {
        message.success('删除成功');
        loadData()
      }
    }
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
      const res: any = await batchDeleteProduct(selectedRowKeys.value);
      if (res.code === 200) {
        message.success('删除成功');
        selectedRowKeys.value = [];
        loadData()
      }
    }
  })
}
onMounted(() => {
  loadData()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.product-cell {
  display: flex;
  align-items: center;
  gap: 14px;
}

.product-thumb {
  flex-shrink: 0;
}

.product-main {
  min-width: 0;
}

.product-name-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 4px;
}

.product-name {
  font-size: 14px;
  font-weight: 600;
  color: #0f172a;
}

.product-subtitle {
  color: #64748b;
  font-size: 12px;
  margin-bottom: 6px;
  line-height: 1.5;
}

.product-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  font-size: 12px;
  color: #94a3b8;
}

.stack-info {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.stack-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
}

.stack-label {
  color: #94a3b8;
  min-width: 36px;
}

.stack-value {
  color: #334155;
  font-weight: 500;
}

.price-row {
  display: flex;
  align-items: baseline;
  gap: 10px;
}

.price-main {
  color: #ef4444;
  font-size: 18px;
  font-weight: 700;
}

.price-original {
  color: #94a3b8;
  font-size: 12px;
  text-decoration: line-through;
}

.tag-group {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}
</style>
