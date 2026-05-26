import request from '@/utils/axios.ts'

export interface ContentCommentLikeVo {
  id?: number
  contentId?: number
  commentId?: number
  userId?: number
  action?: number
}

export interface ContentCommentLikeQuery {
  page: number
  pageSize: number
}

export interface ContentCommentLikeCreate extends Omit<ContentCommentLikeVo, 'id'> {}

export interface ContentCommentLikeUpdate extends ContentCommentLikeCreate {
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

export const getContentCommentLikePage = (data: ContentCommentLikeQuery) => {
  return request<ResultVo<PageVo<ContentCommentLikeVo>>>({
    url: '/content/commentLike/page',
    method: 'POST',
    data,
  })
}

export const getContentCommentLikeById = (id: number) => {
  return request<ResultVo<ContentCommentLikeVo>>({
    url: `/content/commentLike/${id}`,
    method: 'GET',
  })
}

export const createContentCommentLike = (data: ContentCommentLikeCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/commentLike',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentCommentLike = (data: ContentCommentLikeUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/commentLike',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentCommentLike = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/commentLike/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentCommentLike = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/commentLike/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentCommentLikeList = () => {
  return request<ResultVo<ContentCommentLikeVo[]>>({
    url: '/content/commentLike/list',
    method: 'GET',
  })
}

export const getContentCommentLikeSelectAll = () => {
  return request<ResultVo<ContentCommentLikeVo[]>>({
    url: '/content/commentLike/selectAll',
    method: 'GET',
  })
}

