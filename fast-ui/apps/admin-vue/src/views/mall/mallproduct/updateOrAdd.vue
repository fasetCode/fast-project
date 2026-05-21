<template>
  <a-modal v-model:open="visible" :title="isEdit ? '编辑商品' : '新增商品'" width="1100px"
           :destroy-on-close="true" @cancel="handleCancel" @ok="handleSubmit" :confirm-loading="submitLoading"
           class="product-edit-modal" :bodyStyle="{padding: '16px 18px', maxHeight: '72vh', overflowY: 'auto'}">
    <a-form ref="formRef" :model="formData" layout="vertical" :rules="rules">
      <a-tabs v-model:activeKey="activeTabKey" class="product-tabs" :animated="false">
        <a-tab-pane key="basic" tab="基础信息">
          <div class="tab-pane">
            <a-row :gutter="16">
              <a-col :span="12">
                <a-form-item label="商品名称" name="name">
                  <a-input v-model:value="formData.name" placeholder="必填"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="副标题" name="subTitle">
                  <a-input v-model:value="formData.subTitle" placeholder="选填"/>
                </a-form-item>
              </a-col>

              <a-col :span="8">
                <a-form-item label="店铺" name="shopId">
                  <ShopPicker
                    v-model="formData.shopId"
                    :initial-shop="selectedShop"
                    placeholder="请选择店铺"
                    @change="handleShopChange"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="分类" name="categoryId">
                  <CategoryTreePicker v-model="formData.categoryId" placeholder="请选择分类"/>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="品牌" name="brandId">
                  <BrandPicker
                    v-model="formData.brandId"
                    :initial-brand="selectedBrand"
                    placeholder="请选择品牌"
                    @change="handleBrandChange"
                  />
                </a-form-item>
              </a-col>

              <a-col :span="12">
                <a-form-item label="商品编号" name="productSn">
                  <a-input v-model:value="formData.productSn" placeholder="选填，留空可自动生成"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="状态" name="status">
                  <a-select v-model:value="formData.status">
                    <a-select-option v-for="item in productStatusOptions" :key="item.value" :value="item.value">
                      {{ item.label }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>

              <a-col :span="8">
                <a-form-item label="新品" name="isNew">
                  <a-select v-model:value="formData.isNew">
                    <a-select-option v-for="item in yesNoOptions" :key="item.value" :value="item.value">
                      {{ item.label }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="热销" name="isHot">
                  <a-select v-model:value="formData.isHot">
                    <a-select-option v-for="item in yesNoOptions" :key="item.value" :value="item.value">
                      {{ item.label }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="推荐" name="isRecommend">
                  <a-select v-model:value="formData.isRecommend">
                    <a-select-option v-for="item in yesNoOptions" :key="item.value" :value="item.value">
                      {{ item.label }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>

              <a-col :span="12">
                <a-form-item label="排序" name="sort">
                  <a-input-number v-model:value="formData.sort" :min="0" style="width:100%"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="关键字" name="keywords">
                  <a-input v-model:value="formData.keywords" placeholder="选填，用逗号分隔"/>
                </a-form-item>
              </a-col>
            </a-row>
          </div>
        </a-tab-pane>

        <a-tab-pane key="price" tab="价格库存">
          <div class="tab-pane">
            <a-row :gutter="16">
              <a-col :span="8">
                <a-form-item label="售价" name="price">
                  <a-input-number v-model:value="formData.price" :min="0" :precision="2" style="width:100%"/>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="原价" name="originalPrice">
                  <a-input-number v-model:value="formData.originalPrice" :min="0" :precision="2" style="width:100%"/>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="成本价" name="costPrice">
                  <a-input-number v-model:value="formData.costPrice" :min="0" :precision="2" style="width:100%"/>
                </a-form-item>
              </a-col>

              <a-col :span="8">
                <a-form-item label="库存" name="stock">
                  <a-input-number v-model:value="formData.stock" :min="0" style="width:100%"/>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="销量" name="sales">
                  <a-input-number v-model:value="formData.sales" :min="0" style="width:100%"/>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="单位" name="unit">
                  <a-input v-model:value="formData.unit" placeholder="如：件/盒/袋"/>
                </a-form-item>
              </a-col>

              <a-col :span="8">
                <a-form-item label="重量(kg)" name="weight">
                  <a-input-number v-model:value="formData.weight" :min="0" :precision="2" style="width:100%"/>
                </a-form-item>
              </a-col>
            </a-row>
          </div>
        </a-tab-pane>

        <a-tab-pane key="content" tab="图文内容">
          <div class="tab-pane">
            <a-row :gutter="16">
              <a-col :span="12">
                <a-form-item label="主图" name="mainImage">
                  <ImageUpload v-model="formData.mainImage" value-type="id" :limit="1"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="相册图片" name="albumImages">
                  <ImageUpload v-model="albumImageList" value-type="id" :limit="9"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label="详情" name="detail">
                  <TipTapEditor v-model="formData.detail" placeholder="请输入商品详情" :min-height="260"/>
                </a-form-item>
              </a-col>
            </a-row>
          </div>
        </a-tab-pane>

        <a-tab-pane key="sku" tab="SKU规格">
          <div class="tab-pane">
            <div class="section-title">
              <span>SKU 列表</span>
              <span class="section-tip">在此处维护规格库存，提交时会随商品一起保存</span>
            </div>
            <div class="sku-toolbar">
              <a-space>
                <a-button type="primary" size="small" @click="addSkuRow">
                  <PlusOutlined/>
                  添加SKU
                </a-button>
                <span class="sku-count">共 {{ activeSkuList.length }} 个SKU</span>
              </a-space>
            </div>
            <a-table
              :columns="skuColumns"
              :data-source="activeSkuList"
              :pagination="false"
              :scroll="{ x: 1100 }"
              size="small"
              row-key="rowKey"
              class="sku-table"
            >
              <template #bodyCell="{ column, record, index }">
                <template v-if="column.key === 'specText'">
                  <a-input v-model:value="record.specText" placeholder="如:颜色:红色;尺码:M" size="small"/>
                </template>
                <template v-else-if="column.key === 'skuSn'">
                  <a-input v-model:value="record.skuSn" placeholder="留空自动生成" size="small"/>
                </template>
                <template v-else-if="column.key === 'price'">
                  <a-input-number v-model:value="record.price" :min="0" :precision="2" size="small" style="width:100%"/>
                </template>
                <template v-else-if="column.key === 'originalPrice'">
                  <a-input-number v-model:value="record.originalPrice" :min="0" :precision="2" size="small" style="width:100%"/>
                </template>
                <template v-else-if="column.key === 'stock'">
                  <a-input-number v-model:value="record.stock" :min="0" size="small" style="width:100%"/>
                </template>
                <template v-else-if="column.key === 'sales'">
                  <a-input-number v-model:value="record.sales" :min="0" size="small" style="width:100%"/>
                </template>
                <template v-else-if="column.key === 'status'">
                  <a-select v-model:value="record.status" size="small" style="width:100%">
                    <a-select-option :value="1">启用</a-select-option>
                    <a-select-option :value="2">禁用</a-select-option>
                  </a-select>
                </template>
                <template v-else-if="column.key === 'image'">
                  <ImageUpload v-model="record.image" value-type="id" :limit="1"/>
                </template>
                <template v-else-if="column.key === 'action'">
                  <a-button type="link" size="small" @click="openSkuDetail(record)">更多</a-button>
                  <a-button type="link" size="small" danger @click="removeSkuRow(record)">删除</a-button>
                </template>
              </template>
            </a-table>
          </div>
        </a-tab-pane>
      </a-tabs>
    </a-form>

    <a-modal
      v-model:open="skuDetailVisible"
      title="SKU 详情设置"
      width="800px"
      :destroy-on-close="true"
      @ok="confirmSkuDetail"
      @cancel="cancelSkuDetail"
      class="sku-detail-modal"
      :bodyStyle="{padding: '16px 18px'}"
    >
      <a-form layout="vertical" :model="skuDetail">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="SKU编号">
              <a-input v-model:value="skuDetail.skuSn"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="条形码">
              <a-input v-model:value="skuDetail.barcode"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="规格描述">
              <a-input v-model:value="skuDetail.specText"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="规格属性JSON">
              <a-textarea v-model:value="skuDetail.specs" :rows="2"
                          placeholder='[{"name":"颜色","value":"红色"}]'/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="SKU主图">
              <ImageUpload v-model="skuDetail.image" value-type="id" :limit="1"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="支付类型">
              <a-select v-model:value="skuDetail.payType" allow-clear placeholder="默认随SPU">
                <a-select-option v-for="item in payTypeOptions" :key="item.value" :value="item.value">
                  {{ item.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="售价">
              <a-input-number v-model:value="skuDetail.price" :min="0" :precision="2" style="width:100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="原价">
              <a-input-number v-model:value="skuDetail.originalPrice" :min="0" :precision="2" style="width:100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="成本价">
              <a-input-number v-model:value="skuDetail.costPrice" :min="0" :precision="2" style="width:100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="积分价">
              <a-input-number v-model:value="skuDetail.pointsPrice" :min="0" style="width:100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="赠送积分">
              <a-input-number v-model:value="skuDetail.giftPoints" :min="0" style="width:100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="锁定库存">
              <a-input-number v-model:value="skuDetail.lockStock" :min="0" style="width:100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="重量(kg)">
              <a-input-number v-model:value="skuDetail.weight" :min="0" :precision="3" style="width:100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="体积(m³)">
              <a-input-number v-model:value="skuDetail.volume" :min="0" :precision="3" style="width:100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="排序">
              <a-input-number v-model:value="skuDetail.sort" :min="0" style="width:100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="扩展字段">
              <a-textarea v-model:value="skuDetail.extra" :rows="2" placeholder="JSON 字符串"/>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </a-modal>
</template>

<script setup lang="ts">
import {ref, reactive, computed} from 'vue'
import {message} from 'ant-design-vue'
import {PlusOutlined} from '@ant-design/icons-vue'
import type {FormInstance} from 'ant-design-vue'
import type {MallProductCreate, MallProductUpdate} from '@/api/mall/mallproduct.ts'
import type {MallShopVo} from '@/api/mall/mallshop.ts'
import type {MallProductBrandVo} from '@/api/mall/mallproductbrand.ts'
import type {MallProductSkuCreate, MallProductSkuUpdate, MallProductSkuVo} from '@/api/mall/mallproductsku.ts'
import {
  createProduct,
  updateProduct,
  getProductById
} from '@/api/mall/mallproduct.ts'
import {
  createSku,
  updateSku,
  deleteSku,
  getSkuByProduct
} from '@/api/mall/mallproductsku.ts'
import {getRequestId} from '@/utils/idUtils.ts'
import {getDictData} from '@/utils/dict.ts'
import ImageUpload from '@/components/ImageUpload/index.vue'
import TipTapEditor from '@/components/TipTapEditor/index.vue'
import ShopPicker from '@/views/mall/components/ShopPicker.vue'
import CategoryTreePicker from '@/views/mall/components/CategoryTreePicker.vue'
import BrandPicker from '@/views/mall/components/BrandPicker.vue'

const emit = defineEmits<{
  (e: 'success'): void
}>()

const visible = ref(false)
const isEdit = ref(false)
const submitLoading = ref(false)
const formRef = ref<FormInstance>()
const selectedShop = ref<MallShopVo | undefined>(undefined)
const selectedBrand = ref<MallProductBrandVo | undefined>(undefined)
const activeTabKey = ref('basic')

const productStatusOptions = computed(() => (getDictData('mall_product_status') || []).map((d: any) => ({
  value: Number(d.value),
  label: d.label
})))

const yesNoOptions = computed(() => (getDictData('yes_no') || []).map((d: any) => ({
  value: Number(d.value),
  label: d.label
})))

const payTypeOptions = computed(() => (getDictData('mall_pay_type') || []).map((d: any) => ({
  value: Number(d.value),
  label: d.label
})))

const handleShopChange = (_: string | undefined, shop?: MallShopVo) => {
  selectedShop.value = shop
}

const handleBrandChange = (_: string | undefined, brand?: MallProductBrandVo) => {
  selectedBrand.value = brand
}

const toStringId = (val: unknown): string | undefined => {
  if (val === null || val === undefined || val === '') return undefined
  if (typeof val === 'string') return val
  if (typeof val === 'number' || typeof val === 'bigint') return String(val)
  if (typeof val === 'object') {
    const str = (val as any).toString?.()
    return str ? String(str) : undefined
  }
  return undefined
}

// 相册图片：表单存储为逗号分隔字符串，组件需要数组形式
const albumImageList = computed<string[]>({
  get() {
    if (!formData.albumImages) return []
    return String(formData.albumImages)
      .split(',')
      .map(s => s.trim())
      .filter(Boolean)
  },
  set(val: string[]) {
    formData.albumImages = (val || []).filter(Boolean).join(',')
  }
})

type MallProductFormData = Omit<MallProductCreate, 'shopId' | 'categoryId' | 'brandId'> & {
  id?: string
  shopId?: string
  categoryId?: string
  brandId?: string
}

const defaultForm = (): MallProductFormData => ({
  id: undefined,
  name: '',
  subTitle: '',
  shopId: undefined,
  categoryId: undefined,
  brandId: undefined,
  productSn: '',
  mainImage: '',
  albumImages: '',
  detail: '',
  price: 0,
  originalPrice: 0,
  costPrice: 0,
  stock: 0,
  sales: 0,
  unit: '',
  weight: 0,
  status: 1,
  isNew: 0,
  isHot: 0,
  isRecommend: 0,
  sort: 0,
  keywords: ''
})

const formData = reactive<MallProductFormData>(defaultForm())

const rules = {
  name: [{required: true, message: '请输入商品名称', trigger: 'blur'}]
}

// ===== SKU 列表管理 =====
type SkuRow = Omit<MallProductSkuCreate, 'productId' | 'shopId'> & {
  id?: string
  productId?: string
  shopId?: string
  rowKey: string
  _deleted?: boolean
}

let skuRowSeq = 0
const skuList = ref<SkuRow[]>([])
const deletedSkuIds = ref<string[]>([])

const activeSkuList = computed(() => skuList.value.filter(s => !s._deleted))

const skuColumns = [
  {title: '规格描述', key: 'specText', dataIndex: 'specText', width: 200},
  {title: 'SKU编号', key: 'skuSn', dataIndex: 'skuSn', width: 160},
  {title: '主图', key: 'image', dataIndex: 'image', width: 110},
  {title: '售价', key: 'price', dataIndex: 'price', width: 120},
  {title: '原价', key: 'originalPrice', dataIndex: 'originalPrice', width: 120},
  {title: '库存', key: 'stock', dataIndex: 'stock', width: 100},
  {title: '销量', key: 'sales', dataIndex: 'sales', width: 100},
  {title: '状态', key: 'status', dataIndex: 'status', width: 100},
  {title: '操作', key: 'action', width: 130, fixed: 'right' as const, align: 'center' as const}
]

const newSkuRow = (): SkuRow => ({
  rowKey: `new-${++skuRowSeq}`,
  id: undefined,
  productId: undefined,
  shopId: formData.shopId,
  skuSn: '',
  barcode: '',
  specText: '',
  specs: '',
  image: '',
  payType: undefined,
  price: 0,
  originalPrice: 0,
  costPrice: 0,
  pointsPrice: 0,
  giftPoints: 0,
  stock: 0,
  lockStock: 0,
  sales: 0,
  weight: 0,
  volume: 0,
  sort: 0,
  status: 1,
  extra: ''
})

const addSkuRow = () => {
  skuList.value.push(newSkuRow())
}

const removeSkuRow = (record: SkuRow) => {
  if (record.id) {
    // 已存在的 SKU，标记删除并记录ID
    record._deleted = true
    deletedSkuIds.value.push(record.id)
  } else {
    // 未保存的新 SKU，直接从列表移除
    const idx = skuList.value.findIndex(s => s.rowKey === record.rowKey)
    if (idx > -1) skuList.value.splice(idx, 1)
  }
}

// SKU 详情弹窗
const skuDetailVisible = ref(false)
const skuDetail = reactive<SkuRow>(newSkuRow())
let editingSkuRowKey: string | null = null

const openSkuDetail = (record: SkuRow) => {
  editingSkuRowKey = record.rowKey
  Object.assign(skuDetail, record)
  skuDetailVisible.value = true
}

const confirmSkuDetail = () => {
  if (!editingSkuRowKey) {
    skuDetailVisible.value = false
    return
  }
  const idx = skuList.value.findIndex(s => s.rowKey === editingSkuRowKey)
  if (idx > -1) {
    skuList.value[idx] = {...skuList.value[idx], ...skuDetail}
  }
  skuDetailVisible.value = false
  editingSkuRowKey = null
}

const cancelSkuDetail = () => {
  skuDetailVisible.value = false
  editingSkuRowKey = null
}

const resetSkuState = () => {
  skuList.value = []
  deletedSkuIds.value = []
}

const loadSkuList = async (productId: string) => {
  try {
    const res: any = await getSkuByProduct(productId)
    if (res.code === 200) {
      const list: MallProductSkuVo[] = res.data || []
      skuList.value = list.map(item => ({
        rowKey: `exist-${toStringId(item.id) || ''}`,
        id: toStringId(item.id),
        productId: toStringId(item.productId),
        shopId: toStringId(item.shopId),
        skuSn: item.skuSn ?? '',
        barcode: item.barcode ?? '',
        specText: item.specText ?? '',
        specs: item.specs ?? '',
        image: item.image ?? '',
        payType: item.payType,
        price: item.price ?? 0,
        originalPrice: item.originalPrice ?? 0,
        costPrice: item.costPrice ?? 0,
        pointsPrice: item.pointsPrice ?? 0,
        giftPoints: item.giftPoints ?? 0,
        stock: item.stock ?? 0,
        lockStock: item.lockStock ?? 0,
        sales: item.sales ?? 0,
        weight: item.weight ?? 0,
        volume: item.volume ?? 0,
        sort: item.sort ?? 0,
        status: item.status ?? 1,
        extra: item.extra ?? ''
      }))
    }
  } catch (e) {
    message.warning('加载SKU列表失败')
  }
}

// ===== 打开/编辑 =====
const openAdd = () => {
  isEdit.value = false
  resetForm()
  resetSkuState()
  activeTabKey.value = 'basic'
  visible.value = true
}

const openEdit = async (id: number | string) => {
  isEdit.value = true
  visible.value = true
  resetSkuState()
  activeTabKey.value = 'basic'
  const sid = toStringId(id)!
  await loadEditData(sid)
  await loadSkuList(sid)
}

const loadEditData = async (id: string) => {
  try {
    const res: any = await getProductById(id)
    if (res.code === 200) {
      const data = res.data || {}
      Object.assign(formData, {
        id: toStringId(data.id),
        name: data.name ?? '',
        subTitle: data.subTitle ?? '',
        shopId: toStringId(data.shopId ?? data.shop?.id),
        categoryId: toStringId(data.categoryId ?? data.category?.id),
        brandId: toStringId(data.brandId ?? data.brand?.id),
        productSn: data.productSn ?? '',
        mainImage: data.mainImage ?? '',
        albumImages: data.albumImages ?? '',
        detail: data.detail ?? '',
        price: data.price ?? 0,
        originalPrice: data.originalPrice ?? 0,
        costPrice: data.costPrice ?? 0,
        stock: data.stock ?? 0,
        sales: data.sales ?? 0,
        unit: data.unit ?? '',
        weight: data.weight ?? 0,
        status: data.status ?? 1,
        isNew: data.isNew ?? 0,
        isHot: data.isHot ?? 0,
        isRecommend: data.isRecommend ?? 0,
        sort: data.sort ?? 0,
        keywords: data.keywords ?? ''
      })
      selectedShop.value = data.shop || (data.shopId || data.shopName ? {id: data.shopId, name: data.shopName} : undefined)
      selectedBrand.value = data.brand || (data.brandId || data.brandName ? {id: data.brandId, name: data.brandName} : undefined)
    }
  } catch (error) {
    message.error('加载数据失败')
  }
}

const resetForm = () => {
  Object.assign(formData, defaultForm())
  selectedShop.value = undefined
  selectedBrand.value = undefined
  formRef.value?.resetFields()
}

// ===== 提交 =====
const persistSkus = async (productId: string) => {
  const shopId = formData.shopId
  // 1) 删除标记的 SKU
  for (const id of deletedSkuIds.value) {
    try {
      await deleteSku(id)
    } catch (e) {
      console.warn('删除 SKU 失败', id, e)
    }
  }
  // 2) 新增/更新 SKU
  for (const sku of skuList.value) {
    if (sku._deleted) continue
    const payload: any = {
      productId: productId,
      shopId: sku.shopId || shopId,
      skuSn: sku.skuSn,
      barcode: sku.barcode,
      specText: sku.specText,
      specs: sku.specs,
      image: sku.image,
      payType: sku.payType,
      price: sku.price,
      originalPrice: sku.originalPrice,
      costPrice: sku.costPrice,
      pointsPrice: sku.pointsPrice,
      giftPoints: sku.giftPoints,
      stock: sku.stock,
      lockStock: sku.lockStock,
      sales: sku.sales,
      weight: sku.weight,
      volume: sku.volume,
      sort: sku.sort,
      status: sku.status,
      extra: sku.extra
    }
    try {
      if (sku.id) {
        await updateSku({...payload, id: sku.id} as MallProductSkuUpdate, getRequestId())
      } else {
        await createSku(payload as MallProductSkuCreate, getRequestId())
      }
    } catch (e) {
      console.warn('保存 SKU 失败', sku, e)
      throw e
    }
  }
}

const handleSubmit = async () => {
  const fieldTabMap: Record<string, string> = {
    name: 'basic',
    subTitle: 'basic',
    shopId: 'basic',
    categoryId: 'basic',
    brandId: 'basic',
    productSn: 'basic',
    status: 'basic',
    isNew: 'basic',
    isHot: 'basic',
    isRecommend: 'basic',
    sort: 'basic',
    keywords: 'basic',
    price: 'price',
    originalPrice: 'price',
    costPrice: 'price',
    stock: 'price',
    sales: 'price',
    unit: 'price',
    weight: 'price',
    mainImage: 'content',
    albumImages: 'content',
    detail: 'content'
  }

  try {
    await formRef.value?.validate()
  } catch (e: any) {
    const firstField = e?.errorFields?.[0]?.name?.[0]
    if (firstField && fieldTabMap[firstField]) {
      activeTabKey.value = fieldTabMap[firstField]
    }
    return
  }

  submitLoading.value = true
  try {
    const reqId = getRequestId()
    let productId: string | undefined = formData.id !== undefined ? toStringId(formData.id) : undefined
    if (isEdit.value) {
      const res: any = await updateProduct(formData as MallProductUpdate, reqId)
      if (res.code !== 200) {
        return
      }
    } else {
      const res: any = await createProduct({...formData}, reqId)
      if (res.code !== 200) {
        return
      }
      productId = toStringId(res.data)
    }

    if (!productId) {
      message.success(isEdit.value ? '更新成功' : '创建成功')
      visible.value = false
      emit('success')
      return
    }

    if (skuList.value.length > 0 || deletedSkuIds.value.length > 0) {
      try {
        await persistSkus(productId)
      } catch (e) {
        message.warning('商品已保存，但部分 SKU 保存失败，请稍后在SKU列表中手动核对')
      }
    }

    message.success(isEdit.value ? '更新成功' : '创建成功')
    visible.value = false
    emit('success')
  } finally {
    submitLoading.value = false
  }
}

const handleCancel = () => {
  visible.value = false
  setTimeout(() => {
    resetForm()
    resetSkuState()
    activeTabKey.value = 'basic'
  }, 300)
}

defineExpose({
  openAdd,
  openEdit
})
</script>

<style scoped>
.product-tabs :deep(.ant-tabs-nav) {
  margin: 0 0 12px;
}

.tab-pane {
  padding: 2px 2px 10px;
}

.section-title {
  display: flex;
  align-items: baseline;
  gap: 12px;
  margin: 4px 0 12px;
  padding-left: 8px;
  border-left: 3px solid #1677ff;
  font-size: 14px;
  font-weight: 600;
  color: rgba(0, 0, 0, 0.85);
}

.section-tip {
  font-size: 12px;
  font-weight: normal;
  color: rgba(0, 0, 0, 0.45);
}

.sku-toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.sku-count {
  font-size: 12px;
  color: rgba(0, 0, 0, 0.55);
}

.sku-table :deep(.ant-table) {
  border-radius: 12px;
  overflow: hidden;
}

.sku-table :deep(.ant-table-thead > tr > th) {
  background: rgba(22, 119, 255, 0.06);
}

.sku-table :deep(.ant-table-tbody > tr > td) {
  padding: 6px 8px;
}

.product-edit-modal :deep(.ant-form-item) {
  margin-bottom: 12px;
}

.product-edit-modal :deep(.ant-form-item-label > label) {
  color: rgba(0, 0, 0, 0.75);
}

.product-edit-modal :deep(.ant-modal-footer) {
  padding: 12px 18px;
}
</style>
