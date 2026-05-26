import request from '@/utils/axios.ts'

export interface ContentBodyVo {
  id?: number
  contentId?: number
  format?: string
  content?: string
  contentHtml?: string
  wordCount?: number
  readingTime?: number
}

export interface ContentBodyQuery {
  page: number
  pageSize: number
}

export interface ContentBodyCreate extends Omit<ContentBodyVo, 'id'> {}

export interface ContentBodyUpdate extends ContentBodyCreate {
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

export const getContentBodyPage = (data: ContentBodyQuery) => {
  return request<ResultVo<PageVo<ContentBodyVo>>>({
    url: '/content/body/page',
    method: 'POST',
    data,
  })
}

export const getContentBodyById = (id: number) => {
  return request<ResultVo<ContentBodyVo>>({
    url: `/content/body/${id}`,
    method: 'GET',
  })
}

export const createContentBody = (data: ContentBodyCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/body',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentBody = (data: ContentBodyUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/body',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentBody = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/body/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentBody = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/body/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentBodyList = () => {
  return request<ResultVo<ContentBodyVo[]>>({
    url: '/content/body/list',
    method: 'GET',
  })
}

export const getContentBodySelectAll = () => {
  return request<ResultVo<ContentBodyVo[]>>({
    url: '/content/body/selectAll',
    method: 'GET',
  })
}

