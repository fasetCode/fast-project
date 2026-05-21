import request from '@/utils/axios.ts'

export interface PageTypeVo {
  id?: string
  title: string
  code?: string
  status?: number
}

export interface PageTypeQuery {
  page: number
  pageSize: number
  title?: string
  code?: string
  status?: number
}

export interface PageTypeCreate {
  title: string
  code?: string
  status?: number
}

export interface PageTypeUpdate extends PageTypeCreate {
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

export const getPageTypePage = (data: PageTypeQuery) => {
  return request<ResultVo<PageVo<PageTypeVo>>>({
    url: '/page/type/page',
    method: 'POST',
    data,
  })
}

export const getPageTypeById = (id: number) => {
  return request<ResultVo<PageTypeVo>>({
    url: `/page/type/${id}`,
    method: 'GET',
  })
}

export const createPageType = (data: PageTypeCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/type',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updatePageType = (data: PageTypeUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/type',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deletePageType = (id: number) => {
  return request<ResultVo<object>>({
    url: `/page/type/${id}`,
    method: 'DELETE',
  })
}

export const batchDeletePageType = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/page/type/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getPageTypeList = () => {
  return request<ResultVo<PageTypeVo[]>>({
    url: '/page/type/list',
    method: 'GET',
  })
}

export const getPageTypeSelectAll = () => {
  return request<ResultVo<PageTypeVo[]>>({
    url: '/page/type/selectAll',
    method: 'GET',
  })
}
