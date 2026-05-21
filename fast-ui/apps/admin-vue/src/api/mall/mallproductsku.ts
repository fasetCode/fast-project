import request from '@/utils/axios.ts'

export interface MallProductSkuVo {
  id?: number | string
  productId?: number | string
  shopId?: number | string
  skuSn?: string
  barcode?: string
  specText?: string
  specs?: string
  image?: string
  payType?: number
  price?: number
  originalPrice?: number
  costPrice?: number
  pointsPrice?: number
  giftPoints?: number
  stock?: number
  lockStock?: number
  sales?: number
  weight?: number
  volume?: number
  sort?: number
  status?: number
  extra?: string
}

export interface MallProductSkuQuery {
  page: number
  pageSize: number
  productId?: number | string
  shopId?: number | string
  skuSn?: string
  status?: number
}

export interface MallProductSkuCreate {
  productId?: number | string
  shopId?: number | string
  skuSn?: string
  barcode?: string
  specText?: string
  specs?: string
  image?: string
  payType?: number
  price?: number
  originalPrice?: number
  costPrice?: number
  pointsPrice?: number
  giftPoints?: number
  stock?: number
  lockStock?: number
  sales?: number
  weight?: number
  volume?: number
  sort?: number
  status?: number
  extra?: string
}

export interface MallProductSkuUpdate extends MallProductSkuCreate {
  id: number | string
}

export interface PageVo<T> {
  data: T[]
  total: number
}

export interface ResultVo<T> {
  code: number
  data: T
  msg: string
}

export const getSkuPage = (data: MallProductSkuQuery) => {
  return request<ResultVo<PageVo<MallProductSkuVo>>>({
    url: '/mall/sku/page',
    method: 'POST',
    data,
  })
}

export const getSkuById = (id: number | string) => {
  return request<ResultVo<MallProductSkuVo>>({
    url: `/mall/sku/${id}`,
    method: 'GET',
  })
}

export const createSku = (data: MallProductSkuCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/sku',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateSku = (data: MallProductSkuUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/sku',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteSku = (id: number | string) => {
  return request<ResultVo<object>>({
    url: `/mall/sku/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteSku = (ids: (number | string)[]) => {
  return request<ResultVo<object>>({
    url: '/mall/sku/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getSkuList = () => {
  return request<ResultVo<MallProductSkuVo[]>>({
    url: '/mall/sku/list',
    method: 'GET',
  })
}

export const getSkuByProduct = (productId: number | string) => {
  return request<ResultVo<MallProductSkuVo[]>>({
    url: `/mall/sku/byProduct/${productId}`,
    method: 'GET',
  })
}

export const getSkuByShop = (shopId: number | string) => {
  return request<ResultVo<MallProductSkuVo[]>>({
    url: `/mall/sku/byShop/${shopId}`,
    method: 'GET',
  })
}
