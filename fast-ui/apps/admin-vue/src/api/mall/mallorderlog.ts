import request from '@/utils/axios.ts'

export interface MallOrderLogVo {
  id?: number
  orderId?: number
  orderNo?: string
  operatorType?: number
  operatorId?: number
  operatorName?: string
  action?: string
  fromStatus?: number
  toStatus?: number
  content?: string
  ip?: string
  sourceType?: number
  extra?: string
}

export interface MallOrderLogQuery {
  page: number
  pageSize: number
  orderId?: number
  orderNo?: string
  operatorType?: number
  action?: string
  sourceType?: number
}

export interface MallOrderLogCreate {
  orderId?: number
  orderNo?: string
  operatorType?: number
  operatorId?: number
  operatorName?: string
  action?: string
  fromStatus?: number
  toStatus?: number
  content?: string
  ip?: string
  sourceType?: number
  extra?: string
}

export interface MallOrderLogUpdate extends MallOrderLogCreate {
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

export const getOrderLogPage = (data: MallOrderLogQuery) => {
  return request<ResultVo<PageVo<MallOrderLogVo>>>({
    url: '/mall/orderLog/page',
    method: 'POST',
    data,
  })
}

export const getOrderLogById = (id: number) => {
  return request<ResultVo<MallOrderLogVo>>({
    url: `/mall/orderLog/${id}`,
    method: 'GET',
  })
}

export const createOrderLog = (data: MallOrderLogCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/orderLog',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateOrderLog = (data: MallOrderLogUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/orderLog',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteOrderLog = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/orderLog/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteOrderLog = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/orderLog/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getOrderLogList = () => {
  return request<ResultVo<MallOrderLogVo[]>>({
    url: '/mall/orderLog/list',
    method: 'GET',
  })
}
