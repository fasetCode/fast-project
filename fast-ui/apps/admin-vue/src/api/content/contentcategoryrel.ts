import request from '@/utils/axios.ts'

export interface ContentCategoryRelVo {
  id?: number
  contentId?: number
  categoryId?: number
}

export interface ContentCategoryRelQuery {
  page: number
  pageSize: number
}

export interface ContentCategoryRelCreate extends Omit<ContentCategoryRelVo, 'id'> {}

export interface ContentCategoryRelUpdate extends ContentCategoryRelCreate {
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

export const getContentCategoryRelPage = (data: ContentCategoryRelQuery) => {
  return request<ResultVo<PageVo<ContentCategoryRelVo>>>({
    url: '/content/categoryRel/page',
    method: 'POST',
    data,
  })
}

export const getContentCategoryRelById = (id: number) => {
  return request<ResultVo<ContentCategoryRelVo>>({
    url: `/content/categoryRel/${id}`,
    method: 'GET',
  })
}

export const createContentCategoryRel = (data: ContentCategoryRelCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/categoryRel',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentCategoryRel = (data: ContentCategoryRelUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/categoryRel',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentCategoryRel = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/categoryRel/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentCategoryRel = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/categoryRel/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentCategoryRelList = () => {
  return request<ResultVo<ContentCategoryRelVo[]>>({
    url: '/content/categoryRel/list',
    method: 'GET',
  })
}

export const getContentCategoryRelSelectAll = () => {
  return request<ResultVo<ContentCategoryRelVo[]>>({
    url: '/content/categoryRel/selectAll',
    method: 'GET',
  })
}

