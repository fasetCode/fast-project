import request from '@/utils/axios.ts'

export interface SysUsersVo {
  id?: number
  username: string
  nickname: string
  email?: string
  phone?: string
  gender?: number
  status?: number
  departmentId?: string
  postId?: string
  avatar?: string | number
}

export interface SysUsersDetailVo extends SysUsersVo {
  departmentId?: string
  postId?: string
  roleIds?: string[]
  roles?: { id: string; title: string }[]
}

export interface SysUsersQuery {
  page: number
  pageSize: number
  username?: string
  nickname?: string
  email?: string
  phone?: string
  gender?: number
  status?: number
  departmentId?: string
  postId?: string
}

export interface SysUsersCreate {
  username: string
  password?: string
  nickname: string
  email?: string
  phone?: string
  gender?: number
  status?: number
  departmentId?: string
  postId?: string
  roleIds?: string[]
  avatar?: string | number
}

export interface SysUserUpdate extends SysUsersCreate {
  id: number
}

export interface SysUserPasswordUpdate {
  id: number
  newPassword: string
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

export const getUsersPage = (data: SysUsersQuery) => {
  return request<ResultVo<PageVo<SysUsersVo>>>({
    url: '/sys/users/page',
    method: 'POST',
    data,
  })
}

export const getUserById = (id: number) => {
  return request<ResultVo<SysUsersDetailVo>>({
    url: `/sys/users/${id}`,
    method: 'GET',
  })
}

export const createUser = (data: SysUsersCreate, requestId: string) => {
  return request<ResultVo<object>>({
    url: '/sys/users',
    method: 'POST',
    data,
    headers: { "x-request-id": requestId }
  })
}

export const searchUsers = (keyword: string) => {
  return request<ResultVo<SysUsersVo[]>>({
    url: '/sys/users/search',
    method: 'GET',
    params: { keyword }
  })
}

export const updateUser = (data: SysUserUpdate, requestId: string) => {
  return request<ResultVo<object>>({
    url: '/sys/users',
    method: 'PUT',
    data,
    headers: { "x-request-id": requestId }
  })
}

export const deleteUser = (id: number) => {
  return request<ResultVo<object>>({
    url: `/sys/users/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteUser = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/sys/users/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const updateUserPassword = (data: SysUserPasswordUpdate, requestId: string) => {
  return request<ResultVo<object>>({
    url: '/sys/users/password',
    method: 'PUT',
    data,
    headers: { "x-request-id": requestId }
  })
}
