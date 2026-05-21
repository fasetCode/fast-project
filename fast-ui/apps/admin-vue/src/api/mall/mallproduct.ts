import request from '@/utils/axios.ts'

export interface MallProductVo {
  id?: number | string
  name?: string
  subTitle?: string
  shopId?: number | string
  shopName?: string
  categoryId?: number | string
  categoryName?: string
  brandId?: number | string
  brandName?: string
  productSn?: string
  mainImage?: string
  albumImages?: string
  detail?: string
  price?: number
  originalPrice?: number
  costPrice?: number
  stock?: number
  sales?: number
  unit?: string
  weight?: number
  status?: number
  isNew?: number
  isHot?: number
  isRecommend?: number
  sort?: number
  keywords?: string
}

export interface MallProductQuery {
  page: number
  pageSize: number
  name?: string
  shopId?: number | string
  categoryId?: number | string
  brandId?: number | string
  status?: number
  isNew?: number
  isHot?: number
  isRecommend?: number
}

export interface MallProductCreate {
  name?: string
  subTitle?: string
  shopId?: number | string
  categoryId?: number | string
  brandId?: number | string
  productSn?: string
  mainImage?: string
  albumImages?: string
  detail?: string
  price?: number
  originalPrice?: number
  costPrice?: number
  stock?: number
  sales?: number
  unit?: string
  weight?: number
  status?: number
  isNew?: number
  isHot?: number
  isRecommend?: number
  sort?: number
  keywords?: string
}

export interface MallProductUpdate extends MallProductCreate {
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

export const getProductPage = (data: MallProductQuery) => {
  return request<ResultVo<PageVo<MallProductVo>>>({
    url: '/mall/product/page',
    method: 'POST',
    data,
  })
}

export const getProductById = (id: number | string) => {
  return request<ResultVo<MallProductVo>>({
    url: `/mall/product/${id}`,
    method: 'GET',
  })
}

export const createProduct = (data: MallProductCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/product',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateProduct = (data: MallProductUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/product',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteProduct = (id: number | string) => {
  return request<ResultVo<object>>({
    url: `/mall/product/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteProduct = (ids: (number | string)[]) => {
  return request<ResultVo<object>>({
    url: '/mall/product/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getProductList = () => {
  return request<ResultVo<MallProductVo[]>>({
    url: '/mall/product/list',
    method: 'GET',
  })
}

export const getProductSelectAll = () => {
  return request<ResultVo<MallProductVo[]>>({
    url: '/mall/product/selectAll',
    method: 'GET',
  })
}
