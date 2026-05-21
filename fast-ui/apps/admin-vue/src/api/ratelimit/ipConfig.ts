import request from '@/utils/axios.ts'
import type { PageVo, ResultVo } from './apiConfig'

// ==================== VO 类型定义 ====================

export interface IpRateLimitConfigVo {
  id?: number
  appCode: string
  ipAddress?: string
  ipType?: 'ALL' | 'SINGLE' | 'SEGMENT' | string
  maxRequests: number
  timeWindow: number
  burstCapacity?: number
  enabled: boolean
}

export interface IpRateLimitConfigQuery {
  page: number
  pageSize: number
  appCode?: string
  ipAddress?: string
  ipType?: string
  enabled?: boolean
}

export interface IpRateLimitConfigCreate {
  appCode: string
  ipAddress?: string
  ipType?: string
  maxRequests: number
  timeWindow: number
  burstCapacity?: number
  enabled: boolean
}

export interface IpRateLimitConfigUpdate extends IpRateLimitConfigCreate {
  id: number
}

// ==================== API 请求函数 ====================

// 分页查询IP限流配置
export const getIpRateLimitConfigPage = (data: IpRateLimitConfigQuery) => {
  return request<ResultVo<PageVo<IpRateLimitConfigVo[]>>>({
    url: '/ratelimit/ip-config/page',
    method: 'POST',
    data,
  })
}

// 根据ID查询IP限流配置
export const getIpRateLimitConfigById = (id: number) => {
  return request<ResultVo<IpRateLimitConfigVo>>({
    url: `/ratelimit/ip-config/${id}`,
    method: 'GET',
  })
}

// 根据IP地址查询配置
export const getIpRateLimitConfigByIp = (ipAddress: string) => {
  return request<ResultVo<IpRateLimitConfigVo>>({
    url: `/ratelimit/ip-config/ip/${ipAddress}`,
    method: 'GET',
  })
}

// 创建IP限流配置
export const createIpRateLimitConfig = (data: IpRateLimitConfigCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/ip-config',
    method: 'POST',
    data,
    headers: {
      'x-request-id': requestId
    }
  })
}

// 更新IP限流配置
export const updateIpRateLimitConfig = (data: IpRateLimitConfigUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/ip-config',
    method: 'PUT',
    data,
    headers: {
      'x-request-id': requestId
    }
  })
}

// 删除IP限流配置
export const deleteIpRateLimitConfig = (id: number) => {
  return request<ResultVo<object>>({
    url: `/ratelimit/ip-config/${id}`,
    method: 'DELETE',
  })
}

// 批量删除IP限流配置
export const batchDeleteIpRateLimitConfig = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/ip-config/batch',
    method: 'DELETE',
    data: ids,
  })
}
