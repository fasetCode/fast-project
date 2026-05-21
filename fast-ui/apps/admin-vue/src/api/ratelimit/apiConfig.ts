import request from '@/utils/axios.ts'

// ==================== VO 类型定义 ====================

export interface ApiRateLimitConfigVo {
  id?: number
  appCode: string
  apiPath: string
  httpMethod: string
  maxRequests: number
  timeWindow: number
  limitDimension: 'IP' | 'USER' | 'GLOBAL' | string
  enabled: boolean
}

export interface ApiRateLimitConfigQuery {
  page: number
  pageSize: number
  appCode?: string
  apiPath?: string
  httpMethod?: string
  limitDimension?: string
  enabled?: boolean
}

export interface ApiRateLimitConfigCreate {
  appCode: string
  apiPath: string
  httpMethod: string
  maxRequests: number
  timeWindow: number
  limitDimension: string
  enabled: boolean
}

export interface ApiRateLimitConfigUpdate extends ApiRateLimitConfigCreate {
  id: number
}

// ==================== 通用响应类型 ====================

export interface PageVo<T> {
  data: T[]
  total: number
}

export interface ResultVo<T> {
  code: number
  data: T
  msg: string
}

// ==================== API 请求函数 ====================

// 分页查询API限流配置
export const getApiRateLimitConfigPage = (data: ApiRateLimitConfigQuery) => {
  return request<ResultVo<PageVo<ApiRateLimitConfigVo[]>>>({
    url: '/ratelimit/api-config/page',
    method: 'POST',
    data,
  })
}

// 根据ID查询API限流配置
export const getApiRateLimitConfigById = (id: number) => {
  return request<ResultVo<ApiRateLimitConfigVo>>({
    url: `/ratelimit/api-config/${id}`,
    method: 'GET',
  })
}

// 创建API限流配置
export const createApiRateLimitConfig = (data: ApiRateLimitConfigCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/api-config',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

// 更新API限流配置
export const updateApiRateLimitConfig = (data: ApiRateLimitConfigUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/api-config',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

// 删除API限流配置
export const deleteApiRateLimitConfig = (id: number) => {
  return request<ResultVo<object>>({
    url: `/ratelimit/api-config/${id}`,
    method: 'DELETE',
  })
}

// 批量删除API限流配置
export const batchDeleteApiRateLimitConfig = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/api-config/batch',
    method: 'DELETE',
    data: ids,
  })
}
