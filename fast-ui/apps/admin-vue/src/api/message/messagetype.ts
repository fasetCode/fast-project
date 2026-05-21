import request from '@/utils/axios.ts'

export interface MessageTypeVo {
  id?: number
  title?: string
  description?: string
  status?: number
  code?: string
}

export interface MessageTypeQuery {
  page: number
  pageSize: number
  title?: string
  code?: string
  status?: number
}

export interface MessageTypeCreate {
  title?: string
  description?: string
  status?: number
  code?: string
}

export interface MessageTypeUpdate extends MessageTypeCreate {
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

export const getMessageTypePage = (data: MessageTypeQuery) => {
  return request<ResultVo<PageVo<MessageTypeVo>>>({
    url: '/message/type/page',
    method: 'POST',
    data,
  })
}

export const getMessageTypeById = (id: number) => {
  return request<ResultVo<MessageTypeVo>>({
    url: `/message/type/${id}`,
    method: 'GET',
  })
}

export const createMessageType = (data: MessageTypeCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/message/type',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateMessageType = (data: MessageTypeUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/message/type',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteMessageType = (id: number) => {
  return request<ResultVo<object>>({
    url: `/message/type/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteMessageType = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/message/type/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getMessageTypeSelectAll = () => {
  return request<ResultVo<IdTitleVo[]>>({
    url: '/message/type/selectAll',
    method: 'GET',
  })
}