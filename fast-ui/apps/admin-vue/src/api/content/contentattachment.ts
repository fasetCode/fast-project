import request from '@/utils/axios.ts'

export interface ContentAttachmentVo {
  id?: number
  contentId?: number
  fileId?: number
  url?: string
  name?: string
  mimeType?: string
  size?: number
  sort?: number
}

export interface ContentAttachmentQuery {
  page: number
  pageSize: number
}

export interface ContentAttachmentCreate extends Omit<ContentAttachmentVo, 'id'> {}

export interface ContentAttachmentUpdate extends ContentAttachmentCreate {
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

export const getContentAttachmentPage = (data: ContentAttachmentQuery) => {
  return request<ResultVo<PageVo<ContentAttachmentVo>>>({
    url: '/content/attachment/page',
    method: 'POST',
    data,
  })
}

export const getContentAttachmentById = (id: number) => {
  return request<ResultVo<ContentAttachmentVo>>({
    url: `/content/attachment/${id}`,
    method: 'GET',
  })
}

export const createContentAttachment = (data: ContentAttachmentCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/attachment',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentAttachment = (data: ContentAttachmentUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/attachment',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentAttachment = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/attachment/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentAttachment = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/attachment/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentAttachmentList = () => {
  return request<ResultVo<ContentAttachmentVo[]>>({
    url: '/content/attachment/list',
    method: 'GET',
  })
}

export const getContentAttachmentSelectAll = () => {
  return request<ResultVo<ContentAttachmentVo[]>>({
    url: '/content/attachment/selectAll',
    method: 'GET',
  })
}

