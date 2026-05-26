import request from '@/utils/axios.ts'

export interface ContentViewLogVo {
  id?: number
  contentId?: number
  userId?: number
  ip?: string
  userAgent?: string
  referer?: string
}

export interface ContentViewLogQuery {
  page: number
  pageSize: number
}

export interface ContentViewLogCreate extends Omit<ContentViewLogVo, 'id'> {}

export interface ContentViewLogUpdate extends ContentViewLogCreate {
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

export const getContentViewLogPage = (data: ContentViewLogQuery) => {
  return request<ResultVo<PageVo<ContentViewLogVo>>>({
    url: '/content/viewLog/page',
    method: 'POST',
    data,
  })
}

export const getContentViewLogById = (id: number) => {
  return request<ResultVo<ContentViewLogVo>>({
    url: `/content/viewLog/${id}`,
    method: 'GET',
  })
}

export const createContentViewLog = (data: ContentViewLogCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/viewLog',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentViewLog = (data: ContentViewLogUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/viewLog',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentViewLog = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/viewLog/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentViewLog = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/viewLog/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentViewLogList = () => {
  return request<ResultVo<ContentViewLogVo[]>>({
    url: '/content/viewLog/list',
    method: 'GET',
  })
}

export const getContentViewLogSelectAll = () => {
  return request<ResultVo<ContentViewLogVo[]>>({
    url: '/content/viewLog/selectAll',
    method: 'GET',
  })
}

