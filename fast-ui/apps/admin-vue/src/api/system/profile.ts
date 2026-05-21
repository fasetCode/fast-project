import request from '@/utils/axios.ts'

export interface UserProfileVo {
  id: number
  username: string
  nickname: string
  avatar: string
  department: string
  post: string
  phone: string
  email: string
  roles: string[]
  gender: number
  remark: string
}

export interface UserProfileUpdate {
  nickname: string
  phone: string
  email: string
  gender: number
  remark: string
  avatar: string
}

export interface UserProfilePwdUpdate {
  oldPassword?: string
  newPassword?: string
}

export interface UserGrowthAccountVo {
  growthValue: number
  integral: number
  level: {
    id: number
    title: string
    icon: string
    growthValue: number
    background: string
    color: string
    description: string
  }
  nextLevel?: {
    id: number
    title: string
    icon: string
    growthValue: number
    background: string
    color: string
    description: string
  }
}

/**
 * 获取个人信息
 */
export const getProfile = () => {
  return request<UserProfileVo>({
    url: '/sys/profile',
    method: 'GET'
  })
}

/**
 * 获取个人成长账户信息
 */
export const getGrowthAccount = () => {
  return request<UserGrowthAccountVo>({
    url: '/sys/profile/growth',
    method: 'GET'
  })
}

/**
 * 修改个人基本信息
 */
export const updateProfile = (data: UserProfileUpdate, requestId?: string) => {
  return request<object>({
    url: '/sys/profile',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

/**
 * 修改个人密码
 */
export const updateProfilePassword = (data: UserProfilePwdUpdate, requestId?: string) => {
  return request<object>({
    url: '/sys/profile/password',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}