import request from '@/utils/axios.ts'
import type { LogType, LogAction } from '@/enums/logs'

export interface OperationLogVo {
  id?: number
  description?: string
  type?: LogType
  action?: LogAction
  requestParams?: string
  responseData?: string
  timeCost?: number
  ip?: string
  url?: string
  httpMethod?: string
  className?: string
  methodName?: string
  success?: boolean
  errorMsg?: string
  createBy?: number
  createTime?: string
}

export interface OperationLogQuery {
  page: number
  pageSize: number
  description?: string
  type?: LogType
  action?: LogAction
  ip?: string
  url?: string
  success?: boolean
  createBy?: number
  startTime?: string
  endTime?: string
}

export interface OperationLogCreate {
  description?: string
  type?: LogType
  action?: LogAction
  requestParams?: string
  responseData?: string
  timeCost?: number
  ip?: string
  url?: string
  httpMethod?: string
  className?: string
  methodName?: string
  success?: boolean
  errorMsg?: string
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

export const getOperationLogPage = (data: OperationLogQuery) => {
  return request<ResultVo<PageVo<OperationLogVo>>>({
    url: '/logs/operation/page',
    method: 'POST',
    data,
  })
}

export const getOperationLogById = (id: number) => {
  return request<ResultVo<OperationLogVo>>({
    url: `/logs/operation/${id}`,
    method: 'GET',
  })
}



export const deleteOperationLog = (id: number) => {
  return request<ResultVo<object>>({
    url: `/logs/operation/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteOperationLog = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/logs/operation/batch',
    method: 'DELETE',
    data: ids,
  })
}
