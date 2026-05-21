import request from '@/utils/axios.ts'

export interface UserIntegralAccountVo {
  id?: number
  userId?: number
  growthValue?: number
  status?: number
}

export interface UserIntegralAccountQuery {
  page: number
  pageSize: number
  userId?: number
  growthValue?: number
  status?: number
}

export interface UserIntegralAccountCreate {
  userId?: number
  growthValue?: number
  status?: number
}

export interface UserIntegralAccountUpdate extends UserIntegralAccountCreate {
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

export const getUserIntegralAccountPage = (data: UserIntegralAccountQuery) => {
  return request<ResultVo<PageVo<UserIntegralAccountVo>>>({
    url: '/usergrowth/userintegralaccount/page',
    method: 'POST',
    data,
  })
}

export const getUserIntegralAccountById = (id: number) => {
  return request<ResultVo<UserIntegralAccountVo>>({
    url: `/usergrowth/userintegralaccount/${id}`,
    method: 'GET',
  })
}

export const createUserIntegralAccount = (data: UserIntegralAccountCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userintegralaccount',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateUserIntegralAccount = (data: UserIntegralAccountUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userintegralaccount',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteUserIntegralAccount = (id: number) => {
  return request<ResultVo<object>>({
    url: `/usergrowth/userintegralaccount/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteUserIntegralAccount = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/usergrowth/userintegralaccount/batch',
    method: 'DELETE',
    data: ids,
  })
}
