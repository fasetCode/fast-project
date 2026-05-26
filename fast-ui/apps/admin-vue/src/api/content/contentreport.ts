import request from '@/utils/axios.ts'

export interface ContentReportVo {
  id?: number
  targetType?: number
  targetId?: number
  reportBy?: number
  reasonType?: number
  reason?: string
  status?: number
  handleBy?: number
  handleTime?: string
  ip?: string
  userAgent?: string
}

export interface ContentReportQuery {
  page: number
  pageSize: number
}

export interface ContentReportCreate extends Omit<ContentReportVo, 'id'> {}

export interface ContentReportUpdate extends ContentReportCreate {
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

export const getContentReportPage = (data: ContentReportQuery) => {
  return request<ResultVo<PageVo<ContentReportVo>>>({
    url: '/content/report/page',
    method: 'POST',
    data,
  })
}

export const getContentReportById = (id: number) => {
  return request<ResultVo<ContentReportVo>>({
    url: `/content/report/${id}`,
    method: 'GET',
  })
}

export const createContentReport = (data: ContentReportCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/report',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentReport = (data: ContentReportUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/report',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentReport = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/report/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentReport = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/report/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentReportList = () => {
  return request<ResultVo<ContentReportVo[]>>({
    url: '/content/report/list',
    method: 'GET',
  })
}

export const getContentReportSelectAll = () => {
  return request<ResultVo<ContentReportVo[]>>({
    url: '/content/report/selectAll',
    method: 'GET',
  })
}

