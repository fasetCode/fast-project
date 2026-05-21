import request from '@/utils/axios.ts'

export interface PageApplicationVo {
  id?: number
  title: string
  code?: string
  status?: number
  icon?: string
  typeId?: string
  typeName?: string,
  typeCode?: string
}

export interface PageApplicationQuery {
  page: number
  pageSize: number
  title?: string
  code?: string
  status?: number
  typeId?: string
}

export interface PageApplicationCreate {
  title: string
  code?: string
  status?: number
  icon?: string
  typeId?: string
}

export interface PageApplicationUpdate extends PageApplicationCreate {
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

export const getPageApplicationPage = (data: PageApplicationQuery) => {
  return request<ResultVo<PageVo<PageApplicationVo>>>({
    url: '/page/application/page',
    method: 'POST',
    data,
  })
}

export const getPageApplicationById = (id: string) => {
  return request<ResultVo<PageApplicationVo>>({
    url: `/page/application/${id}`,
    method: 'GET',
  })
}

export const createPageApplication = (data: PageApplicationCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/application',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updatePageApplication = (data: PageApplicationUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/application',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deletePageApplication = (id: number) => {
  return request<ResultVo<object>>({
    url: `/page/application/${id}`,
    method: 'DELETE',
  })
}

export const batchDeletePageApplication = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/page/application/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getPageApplicationList = () => {
  return request<ResultVo<PageApplicationVo[]>>({
    url: '/page/application/list',
    method: 'GET',
  })
}

export const getPageApplicationSelectAll = () => {
  return request<ResultVo<PageApplicationVo[]>>({
    url: '/page/application/selectAll',
    method: 'GET',
  })
}
