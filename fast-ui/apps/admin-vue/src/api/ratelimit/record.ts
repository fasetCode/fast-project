import request from '@/utils/axios.ts'
import type { PageVo, ResultVo } from './apiConfig'

// ==================== VO 类型定义 ====================

export interface RateLimitRecordVo {
  id?: number
  appCode: string
  limitKey: string
  limitType: 'IP' | 'USER' | 'API' | 'GLOBAL' | string
  targetValue: string
  method: string
  url: string
  ip: string
  userId?: number
  headers: string
  queryParams: string
  limitReason: string
  createTime: string
}

export interface RateLimitRecordQuery {
  page: number
  pageSize: number
  appCode?: string
  limitKey?: string
  limitType?: string
  targetValue?: string
  url?: string
  ip?: string
  userId?: number
  createTimeBegin?: string
  createTimeEnd?: string
}

export interface RateLimitRecordCreate {
  appCode: string
  limitKey: string
  limitType: string
  targetValue: string
  method: string
  url: string
  ip: string
  userId?: number
  headers: string
  queryParams: string
  limitReason: string
}

export interface RateLimitRecordUpdate extends RateLimitRecordCreate {
  id: number
}

// ==================== API 请求函数 ====================

// 分页查询限流记录
export const getRateLimitRecordPage = (data: RateLimitRecordQuery) => {
  return request<ResultVo<PageVo<RateLimitRecordVo[]>>>({
    url: '/ratelimit/record/page',
    method: 'POST',
    data,
  })
}

// 根据ID查询限流记录
export const getRateLimitRecordById = (id: number) => {
  return request<ResultVo<RateLimitRecordVo>>({
    url: `/ratelimit/record/${id}`,
    method: 'GET',
  })
}

// 创建限流记录
export const createRateLimitRecord = (data: RateLimitRecordCreate) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/record',
    method: 'POST',
    data,
  })
}

// 更新限流记录
export const updateRateLimitRecord = (data: RateLimitRecordUpdate) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/record',
    method: 'PUT',
    data,
  })
}

// 删除限流记录
export const deleteRateLimitRecord = (id: number) => {
  return request<ResultVo<object>>({
    url: `/ratelimit/record/${id}`,
    method: 'DELETE',
  })
}

// 批量删除限流记录
export const batchDeleteRateLimitRecord = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/record/batch',
    method: 'DELETE',
    data: ids,
  })
}
