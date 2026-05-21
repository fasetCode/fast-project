import request from '@/utils/axios.ts'

export interface MessageRecordVo {
  id?: number
  configId?: number
  receiver?: string
  content?: string
  status?: string
  title?: string
  messageType?: string
  operatorId?: number
  userType?: string
  createTime?: string
}

export interface MessageRecordQuery {
  page: number
  pageSize: number
  title?: string
  receiver?: string
  messageType?: string
  status?: string
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

export const getMessageRecordPage = (data: MessageRecordQuery) => {
  return request<ResultVo<PageVo<MessageRecordVo>>>({
    url: '/message/record/page',
    method: 'POST',
    data,
  })
}

export const getMessageRecordById = (id: number) => {
  return request<ResultVo<MessageRecordVo>>({
    url: `/message/record/${id}`,
    method: 'GET',
  })
}

export const deleteMessageRecord = (id: number) => {
  return request<ResultVo<object>>({
    url: `/message/record/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteMessageRecord = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/message/record/batch',
    method: 'DELETE',
    data: ids,
  })
}
