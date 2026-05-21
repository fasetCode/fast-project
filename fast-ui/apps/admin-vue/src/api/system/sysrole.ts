import request from '@/utils/axios.ts'

export interface SysRoleVo {
  id?: string
  title: string
  code: string
  status?: number
  applicationId?: number | null
  applicationCode?: string
  permissionIds?: string[]
}

export interface SysRoleQuery {
  page: number
  pageSize: number
  title?: string
  code?: string
  status?: number
  applicationId?: number
  applicationCode?: string
}

export interface SysRoleCreate {
  title: string
  code: string
  status?: number
  applicationId?: number
  applicationCode?: string
  permissionIds?: string[]
}

export interface SysRoleUpdate extends SysRoleCreate {
  id: string | number
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

export const getRolesPage = (data: SysRoleQuery) => {
  return request<ResultVo<PageVo<SysRoleVo>>>({
    url: '/sys/role/page',
    method: 'POST',
    data,
  })
}

export const getRoleById = (id: string) => {
  return request<ResultVo<SysRoleVo>>({
    url: `/sys/role/${id}`,
    method: 'GET',
  })
}

export const createRole = (data: SysRoleCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/role',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateRole = (data: SysRoleUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/role',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteRole = (id: string) => {
  return request<ResultVo<object>>({
    url: `/sys/role/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteRole = (ids: string[]) => {
  return request<ResultVo<object>>({
    url: '/sys/role/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getRoleSelectAll = () => {
  return request<ResultVo<{ value: number; label: string }[]>>({
    url: '/sys/role/selectAll',
    method: 'GET',
  })
}
