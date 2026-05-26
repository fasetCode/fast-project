import request from '@/utils/axios.ts'

export interface ContentCommentVo {
  id?: number
  contentId?: number
  parentId?: number
  rootId?: number
  userId?: number
  nickname?: string
  avatar?: string
  content?: string
  status?: number
  likeCount?: number
  ip?: string
  userAgent?: string
  replyToUserId?: number
  replyToCommentId?: number
}

export interface ContentCommentQuery {
  page: number
  pageSize: number
}

export interface ContentCommentCreate extends Omit<ContentCommentVo, 'id'> {}

export interface ContentCommentUpdate extends ContentCommentCreate {
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

export const getContentCommentPage = (data: ContentCommentQuery) => {
  return request<ResultVo<PageVo<ContentCommentVo>>>({
    url: '/content/comment/page',
    method: 'POST',
    data,
  })
}

export const getContentCommentById = (id: number) => {
  return request<ResultVo<ContentCommentVo>>({
    url: `/content/comment/${id}`,
    method: 'GET',
  })
}

export const createContentComment = (data: ContentCommentCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/comment',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentComment = (data: ContentCommentUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/comment',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentComment = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/comment/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentComment = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/comment/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentCommentList = () => {
  return request<ResultVo<ContentCommentVo[]>>({
    url: '/content/comment/list',
    method: 'GET',
  })
}

export const getContentCommentSelectAll = () => {
  return request<ResultVo<ContentCommentVo[]>>({
    url: '/content/comment/selectAll',
    method: 'GET',
  })
}

