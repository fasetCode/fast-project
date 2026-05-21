import request from '@/utils/axios.ts'
import type { PageVo, ResultVo } from './apiConfig'

// ==================== VO 类型定义 ====================

export interface GlobalRateLimitConfigVo {
  id?: number
  appCode: string
  maxRequests: number
  timeWindow: number
  burstCapacity: number
  enabled: boolean
}

export interface GlobalRateLimitConfigQuery {
  page: number
  pageSize: number
  appCode?: string
  enabled?: boolean
}

export interface GlobalRateLimitConfigCreate {
  appCode: string
  maxRequests: number
  timeWindow: number
  burstCapacity: number
  enabled: boolean
}

export interface GlobalRateLimitConfigUpdate extends GlobalRateLimitConfigCreate {
  id: number
}

// ==================== API 请求函数 ====================

// 分页查询全局限流配置
export const getGlobalRateLimitConfigPage = (data: GlobalRateLimitConfigQuery) => {
  return request<ResultVo<PageVo<GlobalRateLimitConfigVo[]>>>({
    url: '/ratelimit/global-config/page',
    method: 'POST',
    data,
  })
}

// 根据ID查询全局限流配置
export const getGlobalRateLimitConfigById = (id: number) => {
  return request<ResultVo<GlobalRateLimitConfigVo>>({
    url: `/ratelimit/global-config/${id}`,
    method: 'GET',
  })
}

// 获取当前启用的全局限流配置
export const getGlobalRateLimitConfigEnabled = () => {
  return request<ResultVo<GlobalRateLimitConfigVo>>({
    url: '/ratelimit/global-config/enabled',
    method: 'GET',
  })
}

// 创建全局限流配置
export const createGlobalRateLimitConfig = (data: GlobalRateLimitConfigCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/global-config',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

// 更新全局限流配置
export const updateGlobalRateLimitConfig = (data: GlobalRateLimitConfigUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/global-config',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

// 删除全局限流配置
export const deleteGlobalRateLimitConfig = (id: number) => {
  return request<ResultVo<object>>({
    url: `/ratelimit/global-config/${id}`,
    method: 'DELETE',
  })
}

// 批量删除全局限流配置
export const batchDeleteGlobalRateLimitConfig = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/global-config/batch',
    method: 'DELETE',
    data: ids,
  })
}
