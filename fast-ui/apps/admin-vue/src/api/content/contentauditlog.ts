import request from '@/utils/axios.ts'

export interface ContentAuditLogVo {
  id?: number
  contentId?: number
  auditStatus?: number
  reason?: string
  auditBy?: number
  auditTime?: string
}

export interface ContentAuditLogQuery {
  page: number
  pageSize: number
}

export interface ContentAuditLogCreate extends Omit<ContentAuditLogVo, 'id'> {}

export interface ContentAuditLogUpdate extends ContentAuditLogCreate {
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

export const getContentAuditLogPage = (data: ContentAuditLogQuery) => {
  return request<ResultVo<PageVo<ContentAuditLogVo>>>({
    url: '/content/auditLog/page',
    method: 'POST',
    data,
  })
}

export const getContentAuditLogById = (id: number) => {
  return request<ResultVo<ContentAuditLogVo>>({
    url: `/content/auditLog/${id}`,
    method: 'GET',
  })
}

export const createContentAuditLog = (data: ContentAuditLogCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/auditLog',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentAuditLog = (data: ContentAuditLogUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/auditLog',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentAuditLog = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/auditLog/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentAuditLog = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/auditLog/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentAuditLogList = () => {
  return request<ResultVo<ContentAuditLogVo[]>>({
    url: '/content/auditLog/list',
    method: 'GET',
  })
}

export const getContentAuditLogSelectAll = () => {
  return request<ResultVo<ContentAuditLogVo[]>>({
    url: '/content/auditLog/selectAll',
    method: 'GET',
  })
}

