import request from '@/utils/axios.ts'

export interface ContentCategoryVo {
  id?: number
  name?: string
  parentId?: number
  sort?: number
  status?: number
  remark?: string
}

export interface ContentCategoryTreeVo extends ContentCategoryVo {
  children?: ContentCategoryTreeVo[]
}

export interface ContentCategoryQuery {
  page: number
  pageSize: number
}

export interface ContentCategoryCreate extends Omit<ContentCategoryVo, 'id'> {}

export interface ContentCategoryUpdate extends ContentCategoryCreate {
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

export const getContentCategoryPage = (data: ContentCategoryQuery) => {
  return request<ResultVo<PageVo<ContentCategoryVo>>>({
    url: '/content/category/page',
    method: 'POST',
    data,
  })
}

export const getContentCategoryById = (id: number) => {
  return request<ResultVo<ContentCategoryVo>>({
    url: `/content/category/${id}`,
    method: 'GET',
  })
}

export const createContentCategory = (data: ContentCategoryCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/category',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentCategory = (data: ContentCategoryUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/category',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentCategory = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/category/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentCategory = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/category/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentCategoryList = () => {
  return request<ResultVo<ContentCategoryVo[]>>({
    url: '/content/category/list',
    method: 'GET',
  })
}

export const getContentCategorySelectAll = () => {
  return request<ResultVo<ContentCategoryVo[]>>({
    url: '/content/category/selectAll',
    method: 'GET',
  })
}

export const getContentCategoryTree = () => {
  return request<ResultVo<ContentCategoryTreeVo[]>>({
    url: '/content/category/tree',
    method: 'GET',
  })
}
