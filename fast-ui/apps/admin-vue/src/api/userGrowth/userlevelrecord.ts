import request from '@/utils/axios.ts'

export interface UserLevelRecordVo {
  id?: number
  userId?: number
  beforeGrowthValue?: number
  afterGrowthValue?: number
  changeValue?: number
  description?: string
  status?: number
  type?: number
  businessId?: number
  businessName?: string
  bizType?: string
}

export interface UserLevelRecordQuery {
  page: number
  pageSize: number
  userId?: number
  beforeGrowthValue?: number
  afterGrowthValue?: number
  changeValue?: number
  description?: string
  status?: number
  type?: number
  businessId?: number
  businessName?: string
  bizType?: string
}

export interface UserLevelRecordCreate {
  userId?: number
  beforeGrowthValue?: number
  afterGrowthValue?: number
  changeValue?: number
  description?: string
  status?: number
  type?: number
  businessId?: number
  businessName?: string
  bizType?: string
}

export interface UserLevelRecordUpdate extends UserLevelRecordCreate {
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

export const getUserLevelRecordPage = (data: UserLevelRecordQuery) => {
  return request<ResultVo<PageVo<UserLevelRecordVo>>>({
    url: '/usergrowth/userlevelrecord/page',
    method: 'POST',
    data,
  })
}

export const getUserLevelRecordById = (id: number) => {
  return request<ResultVo<UserLevelRecordVo>>({
    url: `/usergrowth/userlevelrecord/${id}`,
    method: 'GET',
  })
}

export const createUserLevelRecord = (data: UserLevelRecordCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userlevelrecord',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateUserLevelRecord = (data: UserLevelRecordUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userlevelrecord',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteUserLevelRecord = (id: number) => {
  return request<ResultVo<object>>({
    url: `/usergrowth/userlevelrecord/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteUserLevelRecord = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userlevelrecord/batch',
    method: 'DELETE',
    data: ids,
  })
}
