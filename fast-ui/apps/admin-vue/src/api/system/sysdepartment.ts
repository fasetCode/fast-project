import request from '@/utils/axios.ts'

export interface SysDepartmentVo {
  id?: number
  name: string
  parentId?: number | null
  sort?: number
  leader?: string
  phone?: string
  email?: string
  status?: number
  children?: SysDepartmentVo[]
}

export interface SysDepartmentQuery {
  page: number
  pageSize: number
  name?: string
  status?: number
}

export interface SysDepartmentCreate {
  name: string
  parentId?: number | null
  sort?: number
  leader?: string
  phone?: string
  email?: string
  status?: number
}

export interface SysDepartmentUpdate extends SysDepartmentCreate {
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

export const getDepartmentPage = (data: SysDepartmentQuery) => {
  return request<ResultVo<PageVo<SysDepartmentVo>>>({
    url: '/sys/department/page',
    method: 'POST',
    data,
  })
}

export const getDepartmentById = (id: number) => {
  return request<ResultVo<SysDepartmentVo>>({
    url: `/sys/department/${id}`,
    method: 'GET',
  })
}

export const createDepartment = (data: SysDepartmentCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/department',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateDepartment = (data: SysDepartmentUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/department',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteDepartment = (id: number) => {
  return request<ResultVo<object>>({
    url: `/sys/department/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteDepartment = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/sys/department/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getDepartmentTree = () => {
  return request<ResultVo<SysDepartmentVo[]>>({
    url: '/sys/department/tree',
    method: 'GET',
  })
}

export const getDepartmentSelectAll = () => {
  return request<ResultVo<SysDepartmentVo[]>>({
    url: '/sys/department/selectAll',
    method: 'GET',
  })
}
