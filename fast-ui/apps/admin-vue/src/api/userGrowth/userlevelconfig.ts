import request from '@/utils/axios.ts'

export interface UserLevelConfigVo {
  id?: number
  title?: string
  icon?: string
  growthValue?: number
  status?: number
  background?: string
  color?: string
  description?: string
}

export interface UserLevelConfigQuery {
  page: number
  pageSize: number
  title?: string
  icon?: string
  growthValue?: number
  status?: number
  background?: string
  color?: string
  description?: string
}

export interface UserLevelConfigCreate {
  title?: string
  icon?: string
  growthValue?: number
  status?: number
  background?: string
  color?: string
  description?: string
}

export interface UserLevelConfigUpdate extends UserLevelConfigCreate {
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

export const getUserLevelConfigPage = (data: UserLevelConfigQuery) => {
  return request<ResultVo<PageVo<UserLevelConfigVo>>>({
    url: '/usergrowth/userlevelconfig/page',
    method: 'POST',
    data,
  })
}

export const getUserLevelConfigById = (id: number) => {
  return request<ResultVo<UserLevelConfigVo>>({
    url: `/usergrowth/userlevelconfig/${id}`,
    method: 'GET',
  })
}

export const createUserLevelConfig = (data: UserLevelConfigCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userlevelconfig',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateUserLevelConfig = (data: UserLevelConfigUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userlevelconfig',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteUserLevelConfig = (id: number) => {
  return request<ResultVo<object>>({
    url: `/usergrowth/userlevelconfig/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteUserLevelConfig = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userlevelconfig/batch',
    method: 'DELETE',
    data: ids,
  })
}
