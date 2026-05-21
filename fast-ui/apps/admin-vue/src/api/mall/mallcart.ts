import request from '@/utils/axios.ts'

export interface MallCartVo {
  id?: number
  userId?: number
  shopId?: number
  productId?: number
  skuId?: number
  productSnapshot?: string
  skuSnapshot?: string
  price?: number
  quantity?: number
  selected?: number
}

export interface MallCartQuery {
  page: number
  pageSize: number
  userId?: number
  shopId?: number
  productId?: number
  skuId?: number
  selected?: number
}

export interface MallCartCreate {
  userId?: number
  shopId?: number
  productId?: number
  skuId?: number
  productSnapshot?: string
  skuSnapshot?: string
  price?: number
  quantity?: number
  selected?: number
}

export interface MallCartUpdate extends MallCartCreate {
  id: number
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

export const getCartPage = (data: MallCartQuery) => {
  return request<ResultVo<PageVo<MallCartVo>>>({
    url: '/mall/cart/page',
    method: 'POST',
    data,
  })
}

export const getCartById = (id: number) => {
  return request<ResultVo<MallCartVo>>({
    url: `/mall/cart/${id}`,
    method: 'GET',
  })
}

export const createCart = (data: MallCartCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/cart',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateCart = (data: MallCartUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/cart',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteCart = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/cart/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteCart = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/cart/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getCartList = () => {
  return request<ResultVo<MallCartVo[]>>({
    url: '/mall/cart/list',
    method: 'GET',
  })
}

export const getCartByUser = (userId: number) => {
  return request<ResultVo<MallCartVo[]>>({
    url: `/mall/cart/byUser/${userId}`,
    method: 'GET',
  })
}
