import request from '@/utils/axios.ts'

export interface MessageConfigVo {
  id?: number
  title?: string
  type?: string
  config?: string
  description?: string
  status?: number
}

export interface MessageConfigQuery {
  page: number
  pageSize: number
  title?: string
  type?: string
  status?: number
}

export interface MessageConfigCreate {
  title?: string
  type?: string
  config?: string
  description?: string
  status?: number
}

export interface MessageConfigUpdate extends MessageConfigCreate {
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

export const getMessageConfigPage = (data: MessageConfigQuery) => {
  return request<ResultVo<PageVo<MessageConfigVo>>>({
    url: '/message/config/page',
    method: 'POST',
    data,
  })
}

export const getMessageConfigById = (id: number) => {
  return request<ResultVo<MessageConfigVo>>({
    url: `/message/config/${id}`,
    method: 'GET',
  })
}

export const createMessageConfig = (data: MessageConfigCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/message/config',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateMessageConfig = (data: MessageConfigUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/message/config',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteMessageConfig = (id: number) => {
  return request<ResultVo<object>>({
    url: `/message/config/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteMessageConfig = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/message/config/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getMessageConfigSelectAll = () => {
  return request<ResultVo<IdTitleVo[]>>({
    url: '/message/config/selectAll',
    method: 'GET',
  })
}