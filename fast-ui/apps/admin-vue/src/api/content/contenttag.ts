import request from '@/utils/axios.ts'

export interface ContentTagVo {
  id?: number
  name?: string
  color?: string
  icon?: string
  image?: string
  displayType?: number
  status?: number
}

export interface ContentTagQuery {
  page: number
  pageSize: number
}

export interface ContentTagCreate extends Omit<ContentTagVo, 'id'> {}

export interface ContentTagUpdate extends ContentTagCreate {
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

export const getContentTagPage = (data: ContentTagQuery) => {
  return request<ResultVo<PageVo<ContentTagVo>>>({
    url: '/content/tag/page',
    method: 'POST',
    data,
  })
}

export const getContentTagById = (id: number) => {
  return request<ResultVo<ContentTagVo>>({
    url: `/content/tag/${id}`,
    method: 'GET',
  })
}

export const createContentTag = (data: ContentTagCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/tag',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentTag = (data: ContentTagUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/tag',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentTag = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/tag/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentTag = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/tag/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentTagList = () => {
  return request<ResultVo<ContentTagVo[]>>({
    url: '/content/tag/list',
    method: 'GET',
  })
}

export const getContentTagSelectAll = () => {
  return request<ResultVo<ContentTagVo[]>>({
    url: '/content/tag/selectAll',
    method: 'GET',
  })
}

