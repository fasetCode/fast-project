import request from '@/utils/axios.ts'

export interface PageComponentVo {
  id?: number
  title: string
  icon?: string
  typeId?: string
  typeName?: string
  code?: string
  status?: number
  sort?: number
}

export interface PageComponentQuery {
  page: number
  pageSize: number
  title?: string
  code?: string
  status?: number
  typeId?: string
}

export interface PageComponentCreate {
  title: string
  icon?: string
  typeId?: string
  code?: string
  status?: number
  sort?: number
}

export interface PageComponentUpdate extends PageComponentCreate {
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

export const getPageComponentPage = (data: PageComponentQuery) => {
  return request<ResultVo<PageVo<PageComponentVo>>>({
    url: '/page/component/page',
    method: 'POST',
    data,
  })
}

export const getPageComponentById = (id: number) => {
  return request<ResultVo<PageComponentVo>>({
    url: `/page/component/${id}`,
    method: 'GET',
  })
}

export const createPageComponent = (data: PageComponentCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/component',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updatePageComponent = (data: PageComponentUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/component',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deletePageComponent = (id: number) => {
  return request<ResultVo<object>>({
    url: `/page/component/${id}`,
    method: 'DELETE',
  })
}

export const batchDeletePageComponent = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/page/component/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getPageComponentList = () => {
  return request<ResultVo<PageComponentVo[]>>({
    url: '/page/component/list',
    method: 'GET',
  })
}

export const getPageComponentSelectAll = () => {
  return request<ResultVo<PageComponentVo[]>>({
    url: '/page/component/selectAll',
    method: 'GET',
  })
}
