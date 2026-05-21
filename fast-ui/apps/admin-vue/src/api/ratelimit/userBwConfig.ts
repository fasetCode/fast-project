import request from '@/utils/axios.ts'
import type { PageVo, ResultVo } from './apiConfig'

// ==================== VO 类型定义 ====================

export interface UserBlackWhiteListVo {
  id?: number
  appCode: string
  userId: number
  listType: 'BLACK' | 'WHITE' | string
  limitMsg?: string
  enabled: boolean
  remark?: string
}

export interface UserBlackWhiteListQuery {
  page: number
  pageSize: number
  appCode?: string
  userId?: number
  listType?: string
  enabled?: boolean
}

export interface UserBlackWhiteListCreate {
  appCode: string
  userId: number | null
  listType: string
  limitMsg?: string
  enabled: boolean
  remark?: string
}

export interface UserBlackWhiteListUpdate extends UserBlackWhiteListCreate {
  id: number
}

// ==================== API 请求函数 ====================

// 分页查询用户黑白名单配置
export const getUserBwConfigPage = (data: UserBlackWhiteListQuery) => {
  return request<ResultVo<PageVo<UserBlackWhiteListVo[]>>>({
    url: '/ratelimit/user-bw-config/page',
    method: 'POST',
    data,
  })
}

// 根据ID查询用户黑白名单配置
export const getUserBwConfigById = (id: number) => {
  return request<ResultVo<UserBlackWhiteListVo>>({
    url: `/ratelimit/user-bw-config/${id}`,
    method: 'GET',
  })
}

// 创建用户黑白名单配置
export const createUserBwConfig = (data: UserBlackWhiteListCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/user-bw-config',
    method: 'POST',
    data,
    headers: {
      'x-request-id': requestId
    }
  })
}

// 更新用户黑白名单配置
export const updateUserBwConfig = (data: UserBlackWhiteListUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/user-bw-config',
    method: 'PUT',
    data,
    headers: {
      'x-request-id': requestId
    }
  })
}

// 删除用户黑白名单配置
export const deleteUserBwConfig = (id: number) => {
  return request<ResultVo<object>>({
    url: `/ratelimit/user-bw-config/${id}`,
    method: 'DELETE',
  })
}

// 批量删除用户黑白名单配置
export const batchDeleteUserBwConfig = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/ratelimit/user-bw-config/batch',
    method: 'DELETE',
    data: ids,
  })
}
