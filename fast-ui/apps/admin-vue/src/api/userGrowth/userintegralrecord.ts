import request from '@/utils/axios.ts'

export interface UserIntegralRecordVo {
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

export interface UserIntegralRecordQuery {
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

export interface UserIntegralRecordCreate {
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

export interface UserIntegralRecordUpdate extends UserIntegralRecordCreate {
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

export const getUserIntegralRecordPage = (data: UserIntegralRecordQuery) => {
  return request<ResultVo<PageVo<UserIntegralRecordVo>>>({
    url: '/usergrowth/userintegralrecord/page',
    method: 'POST',
    data,
  })
}

export const getUserIntegralRecordById = (id: number) => {
  return request<ResultVo<UserIntegralRecordVo>>({
    url: `/usergrowth/userintegralrecord/${id}`,
    method: 'GET',
  })
}

export const createUserIntegralRecord = (data: UserIntegralRecordCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userintegralrecord',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateUserIntegralRecord = (data: UserIntegralRecordUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userintegralrecord',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteUserIntegralRecord = (id: number) => {
  return request<ResultVo<object>>({
    url: `/usergrowth/userintegralrecord/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteUserIntegralRecord = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userintegralrecord/batch',
    method: 'DELETE',
    data: ids,
  })
}
