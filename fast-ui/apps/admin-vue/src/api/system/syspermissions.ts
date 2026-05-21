import request from '@/utils/axios.ts'

export interface SysPermissionsVo {
  id?: string
  title: string
  code: string
  url?: string
  type?: number
  component?: string
  icon?: string
  status?: number
  parentId?: string
  componentName?: string
  sort?: number
  applicationId?: number
  applicationCode?: string
  children?: SysPermissionsVo[]
}

export interface SysPermissionsQuery {
  page: number
  pageSize: number
  title?: string
  code?: string
  type?: number
  status?: number
  parentId?: string
  applicationId?: number
}

export interface SysPermissionsCreate {
  title: string
  code: string
  url?: string
  type?: number
  component?: string
  icon?: string
  status?: number
  parentId?: string | null
  componentName?: string
  sort?: number
  applicationId?: number
  applicationCode?: string
}

export interface SysPermissionsUpdate extends SysPermissionsCreate {
  id: string
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

export const getPermissionsPage = (data: SysPermissionsQuery) => {
  return request<ResultVo<PageVo<SysPermissionsVo>>>({
    url: '/sys/permissions/page',
    method: 'POST',
    data,
  })
}

export const getPermissionsById = (id: string) => {
  return request<ResultVo<SysPermissionsVo>>({
    url: `/sys/permissions/${id}`,
    method: 'GET',
  })
}

export const createPermissions = (data: SysPermissionsCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/permissions',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updatePermissions = (data: SysPermissionsUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/permissions',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deletePermissions = (id: string) => {
  return request<ResultVo<object>>({
    url: `/sys/permissions/${id}`,
    method: 'DELETE',
  })
}

export const batchDeletePermissions = (ids: string[]) => {
  return request<ResultVo<object>>({
    url: '/sys/permissions/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getPermissionsTree = () => {
  return request<ResultVo<SysPermissionsVo[]>>({
    url: '/sys/permissions/tree',
    method: 'GET',
  })
}
