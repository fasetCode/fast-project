import request from '@/utils/axios.ts'

export interface ContentLikeVo {
  id?: number
  contentId?: number
  userId?: number
}

export interface ContentLikeQuery {
  page: number
  pageSize: number
}

export interface ContentLikeCreate extends Omit<ContentLikeVo, 'id'> {}

export interface ContentLikeUpdate extends ContentLikeCreate {
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

export const getContentLikePage = (data: ContentLikeQuery) => {
  return request<ResultVo<PageVo<ContentLikeVo>>>({
    url: '/content/like/page',
    method: 'POST',
    data,
  })
}

export const getContentLikeById = (id: number) => {
  return request<ResultVo<ContentLikeVo>>({
    url: `/content/like/${id}`,
    method: 'GET',
  })
}

export const createContentLike = (data: ContentLikeCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/like',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentLike = (data: ContentLikeUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/like',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentLike = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/like/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentLike = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/like/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentLikeList = () => {
  return request<ResultVo<ContentLikeVo[]>>({
    url: '/content/like/list',
    method: 'GET',
  })
}

export const getContentLikeSelectAll = () => {
  return request<ResultVo<ContentLikeVo[]>>({
    url: '/content/like/selectAll',
    method: 'GET',
  })
}

