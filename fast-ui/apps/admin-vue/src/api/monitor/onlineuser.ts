import request from '@/utils/axios.ts'

export interface OnlineUserVo {
  token?: string
  userId?: number
  username?: string
  nickname?: string
  email?: string
  phone?: string
  /** 登录IP */
  ip?: string
  /** 登录地点 */
  address?: string
  /** 浏览器 */
  browser?: string
  /** 设备 */
  device?: string
  loginTime?: number
  expireTime?: number
}

export interface OnlineUserQuery {
  page: number
  pageSize: number
  username?: string
  nickname?: string
}

export interface OnlineStatisticsVo {
  onlineCount: number
  uniqueUserCount: number
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

// 获取在线用户分页列表
export const getOnlineUserPage = (params: OnlineUserQuery) => {
  return request<ResultVo<PageVo<OnlineUserVo[]>>>({
    url: '/sys/online/users/page',
    method: 'GET',
    params,
  })
}

// 获取在线用户统计
export const getOnlineUserStatistics = () => {
  return request<ResultVo<OnlineStatisticsVo>>({
    url: '/sys/online/users/statistics',
    method: 'GET',
  })
}

// 踢出单个用户
export const kickoutOnlineUser = (token: string) => {
  return request<ResultVo<object>>({
    url: `/sys/online/users/kickout/${token}`,
    method: 'DELETE',
  })
}

// 批量踢出用户
export const batchKickoutOnlineUser = (tokens: string[]) => {
  return request<ResultVo<object>>({
    url: '/sys/online/users/kickout/batch',
    method: 'POST',
    data: tokens,
  })
}
