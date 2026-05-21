import request from '@/utils/axios.ts'

export interface MessageVerificationCodeVo {
  id?: number
  code?: string
  target?: string
  configId?: number | string
  status?: number
  businessData?: string
  expireTime?: number
}

export interface MessageVerificationCodeQuery {
  page: number
  pageSize: number
  code?: string
  target?: string
  configId?: number
  status?: number
}

export interface MessageVerificationCodeCreate {
  code?: string
  target?: string
  configId?: number | string
  status?: number
  businessData?: string
  expireTime?: number
}

export interface MessageVerificationCodeUpdate extends MessageVerificationCodeCreate {
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

export const getMessageVerificationCodePage = (data: MessageVerificationCodeQuery) => {
  return request<ResultVo<PageVo<MessageVerificationCodeVo>>>({
    url: '/message/verificationCode/page',
    method: 'POST',
    data,
  })
}

export const getMessageVerificationCodeById = (id: number) => {
  return request<ResultVo<MessageVerificationCodeVo>>({
    url: `/message/verificationCode/${id}`,
    method: 'GET',
  })
}

export const sendMessageVerificationCode = (data: MessageVerificationCodeCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/message/verificationCode/send',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteMessageVerificationCode = (id: number) => {
  return request<ResultVo<object>>({
    url: `/message/verificationCode/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteMessageVerificationCode = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/message/verificationCode/batch',
    method: 'DELETE',
    data: ids,
  })
}