import request from '@/utils/axios.ts'

export interface ContentFavoriteVo {
  id?: number
  contentId?: number
  userId?: number
}

export interface ContentFavoriteQuery {
  page: number
  pageSize: number
}

export interface ContentFavoriteCreate extends Omit<ContentFavoriteVo, 'id'> {}

export interface ContentFavoriteUpdate extends ContentFavoriteCreate {
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

export const getContentFavoritePage = (data: ContentFavoriteQuery) => {
  return request<ResultVo<PageVo<ContentFavoriteVo>>>({
    url: '/content/favorite/page',
    method: 'POST',
    data,
  })
}

export const getContentFavoriteById = (id: number) => {
  return request<ResultVo<ContentFavoriteVo>>({
    url: `/content/favorite/${id}`,
    method: 'GET',
  })
}

export const createContentFavorite = (data: ContentFavoriteCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/favorite',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentFavorite = (data: ContentFavoriteUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/favorite',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentFavorite = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/favorite/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentFavorite = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/favorite/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentFavoriteList = () => {
  return request<ResultVo<ContentFavoriteVo[]>>({
    url: '/content/favorite/list',
    method: 'GET',
  })
}

export const getContentFavoriteSelectAll = () => {
  return request<ResultVo<ContentFavoriteVo[]>>({
    url: '/content/favorite/selectAll',
    method: 'GET',
  })
}

