import request from '@/utils/axios.ts'

export interface UserLevelAccountVo {
  id?: number
  userId?: number
  growthValue?: number
  status?: number
  avatar?: string
  avatarFrame?: string
}

export interface UserLevelAccountQuery {
  page: number
  pageSize: number
  userId?: number
  growthValue?: number
  status?: number
  avatar?: string
  avatarFrame?: string
}

export interface UserLevelAccountCreate {
  userId?: number
  growthValue?: number
  status?: number
  avatar?: string
  avatarFrame?: string
}

export interface UserLevelAccountUpdate extends UserLevelAccountCreate {
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

export const getUserLevelAccountPage = (data: UserLevelAccountQuery) => {
  return request<ResultVo<PageVo<UserLevelAccountVo>>>({
    url: '/usergrowth/userlevelaccount/page',
    method: 'POST',
    data,
  })
}

export const getUserLevelAccountById = (id: number) => {
  return request<ResultVo<UserLevelAccountVo>>({
    url: `/usergrowth/userlevelaccount/${id}`,
    method: 'GET',
  })
}

export const createUserLevelAccount = (data: UserLevelAccountCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userlevelaccount',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateUserLevelAccount = (data: UserLevelAccountUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userlevelaccount',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteUserLevelAccount = (id: number) => {
  return request<ResultVo<object>>({
    url: `/usergrowth/userlevelaccount/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteUserLevelAccount = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userlevelaccount/batch',
    method: 'DELETE',
    data: ids,
  })
}
