import request from '@/utils/axios.ts'

export interface ContentInfoVo {
  id?: number
  title?: string
  summary?: string
  cover?: string
  categoryId?: number
  tags?: string
  authorId?: number
  authorName?: string
  source?: string
  sourceUrl?: string
  topFlag?: boolean
  recommendFlag?: boolean
  allowComment?: boolean
  status?: number
  publishStatus?: number
  auditStatus?: number
  auditTime?: string
  auditBy?: number
  publishTime?: string
  lastCommentTime?: string
  viewCount?: number
  likeCount?: number
  favoriteCount?: number
  commentCount?: number
}

export interface ContentInfoQuery {
  page: number
  pageSize: number
}

export interface ContentInfoCreate extends Omit<ContentInfoVo, 'id'> {}

export interface ContentInfoUpdate extends ContentInfoCreate {
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

export const getContentInfoPage = (data: ContentInfoQuery) => {
  return request<ResultVo<PageVo<ContentInfoVo>>>({
    url: '/content/info/page',
    method: 'POST',
    data,
  })
}

export const getContentInfoById = (id: number) => {
  return request<ResultVo<ContentInfoVo>>({
    url: `/content/info/${id}`,
    method: 'GET',
  })
}

export const createContentInfo = (data: ContentInfoCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/info',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentInfo = (data: ContentInfoUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/info',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentInfo = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/info/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentInfo = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/info/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentInfoList = () => {
  return request<ResultVo<ContentInfoVo[]>>({
    url: '/content/info/list',
    method: 'GET',
  })
}

export const getContentInfoSelectAll = () => {
  return request<ResultVo<ContentInfoVo[]>>({
    url: '/content/info/selectAll',
    method: 'GET',
  })
}

