import request from '@/utils/axios.ts'

export interface MallShopVo {
  id?: string | number
  name?: string
  code?: string
  logo?: string
  banner?: string
  description?: string
  ownerId?: string | number
  contactName?: string
  contactPhone?: string
  contactEmail?: string
  address?: string
  status?: number
  sort?: number
  remark?: string
}

export interface MallShopQuery {
  page: number
  pageSize: number
  name?: string
  code?: string
  status?: number
}

export interface MallShopCreate {
  name?: string
  code?: string
  logo?: string
  banner?: string
  description?: string
  ownerId?: string | number
  contactName?: string
  contactPhone?: string
  contactEmail?: string
  address?: string
  status?: number
  sort?: number
  remark?: string
}

export interface MallShopUpdate extends MallShopCreate {
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

export const getShopPage = (data: MallShopQuery) => {
  return request<ResultVo<PageVo<MallShopVo>>>({
    url: '/mall/shop/page',
    method: 'POST',
    data,
  })
}

export const getShopById = (id: string | number) => {
  return request<ResultVo<MallShopVo>>({
    url: `/mall/shop/${id}`,
    method: 'GET',
  })
}

export const createShop = (data: MallShopCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/shop',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateShop = (data: MallShopUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/shop',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteShop = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/shop/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteShop = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/shop/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getShopList = () => {
  return request<ResultVo<MallShopVo[]>>({
    url: '/mall/shop/list',
    method: 'GET',
  })
}

export const getShopSelectAll = () => {
  return request<ResultVo<MallShopVo[]>>({
    url: '/mall/shop/selectAll',
    method: 'GET',
  })
}
