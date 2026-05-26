import request from '@/utils/axios.ts'

export interface ContentRevisionVo {
  id?: number
  contentId?: number
  version?: number
  editorId?: number
  reason?: string
  format?: string
  content?: string
  contentHtml?: string
  wordCount?: number
}

export interface ContentRevisionQuery {
  page: number
  pageSize: number
}

export interface ContentRevisionCreate extends Omit<ContentRevisionVo, 'id'> {}

export interface ContentRevisionUpdate extends ContentRevisionCreate {
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

export const getContentRevisionPage = (data: ContentRevisionQuery) => {
  return request<ResultVo<PageVo<ContentRevisionVo>>>({
    url: '/content/revision/page',
    method: 'POST',
    data,
  })
}

export const getContentRevisionById = (id: number) => {
  return request<ResultVo<ContentRevisionVo>>({
    url: `/content/revision/${id}`,
    method: 'GET',
  })
}

export const createContentRevision = (data: ContentRevisionCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/revision',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentRevision = (data: ContentRevisionUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/revision',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentRevision = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/revision/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentRevision = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/revision/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentRevisionList = () => {
  return request<ResultVo<ContentRevisionVo[]>>({
    url: '/content/revision/list',
    method: 'GET',
  })
}

export const getContentRevisionSelectAll = () => {
  return request<ResultVo<ContentRevisionVo[]>>({
    url: '/content/revision/selectAll',
    method: 'GET',
  })
}

