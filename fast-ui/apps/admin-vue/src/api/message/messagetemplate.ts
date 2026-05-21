import request from '@/utils/axios.ts'

export interface MessageTemplateVo {
  id?: number
  code?: string
  title?: string
  configId?: number | string
  description?: string
  status?: number
  content?: string
  typeId?: number
}

export interface MessageTemplateQuery {
  page: number
  pageSize: number
  title?: string
  code?: string
  typeId?: number
  status?: number
}

export interface MessageTemplateCreate {
  code?: string
  title?: string
  configId?: number | string
  description?: string
  status?: number
  content?: string
  typeId?: number
}

export interface MessageTemplateUpdate extends MessageTemplateCreate {
  id: number
}

export interface IdTitleVo {
  id?: number
  title?: string
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

export const getMessageTemplatePage = (data: MessageTemplateQuery) => {
  return request<ResultVo<PageVo<MessageTemplateVo>>>({
    url: '/message/template/page',
    method: 'POST',
    data,
  })
}

export const getMessageTemplateById = (id: number) => {
  return request<ResultVo<MessageTemplateVo>>({
    url: `/message/template/${id}`,
    method: 'GET',
  })
}

export const createMessageTemplate = (data: MessageTemplateCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/message/template',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateMessageTemplate = (data: MessageTemplateUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/message/template',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteMessageTemplate = (id: number) => {
  return request<ResultVo<object>>({
    url: `/message/template/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteMessageTemplate = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/message/template/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getMessageTemplateSelectAll = () => {
  return request<ResultVo<MessageTemplateVo[]>>({
    url: '/message/template/selectAll',
    method: 'GET',
  })
}