import request from '@/utils/axios.ts'
import type { PageVo, ResultVo } from './apiConfig'

// ==================== VO 类型定义 ====================

export interface IpBlackWhiteListVo {
  id?: number
  appCode: string
  ipAddress: string
  listType: 'BLACK' | 'WHITE' | string
  limitMsg?: string
  enabled: boolean
  remark?: string
}

export interface IpBlackWhiteListQuery {
  page: number
  pageSize: number
  appCode?: string
  ipAddress?: string
  listType?: string
  enabled?: boolean
}

export interface IpBlackWhiteListCreate {
  appCode: string
  ipAddress: string
  listType: string
  limitMsg?: string
  enabled: boolean
  remark?: string
}

export interface IpBlackWhiteListUpdate extends IpBlackWhiteListCreate {
  id: number
}

// ==================== API 请求函数 ====================

// 分页查询IP黑白名单配置
export const getIpBwConfigPage = (data: IpBlackWhiteListQuery) => {
  return request<ResultVo<PageVo<IpBlackWhiteListVo[]>>>({
    url: '/ratelimit/ip-bw-config/page',
    method: 'POST',
    data,
  })
}

// 根据ID查询IP黑白名单配置
export const getIpBwConfigById = (id: number) => {
  return request<ResultVo<IpBlackWhiteListVo>>({
    url: `/ratelimit/ip-bw-config/${id}`,
    method: 'GET',
  })
}

// 创建IP黑白名单配置
export const createIpBwConfig = (data: IpBlackWhiteListCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/ip-bw-config',
    method: 'POST',
    data,
    headers: {
      'x-request-id': requestId
    }
  })
}

// 更新IP黑白名单配置
export const updateIpBwConfig = (data: IpBlackWhiteListUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/ip-bw-config',
    method: 'PUT',
    data,
    headers: {
      'x-request-id': requestId
    }
  })
}

// 删除IP黑白名单配置
export const deleteIpBwConfig = (id: number) => {
  return request<ResultVo<object>>({
    url: `/ratelimit/ip-bw-config/${id}`,
    method: 'DELETE',
  })
}

// 批量删除IP黑白名单配置
export const batchDeleteIpBwConfig = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/ip-bw-config/batch',
    method: 'DELETE',
    data: ids,
  })
}
