import request from '@/utils/axios.ts'

export interface ContentTagRelVo {
  id?: number
  contentId?: number
  tagId?: number
}

export interface ContentTagRelQuery {
  page: number
  pageSize: number
}

export interface ContentTagRelCreate extends Omit<ContentTagRelVo, 'id'> {}

export interface ContentTagRelUpdate extends ContentTagRelCreate {
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

export const getContentTagRelPage = (data: ContentTagRelQuery) => {
  return request<ResultVo<PageVo<ContentTagRelVo>>>({
    url: '/content/tagRel/page',
    method: 'POST',
    data,
  })
}

export const getContentTagRelById = (id: number) => {
  return request<ResultVo<ContentTagRelVo>>({
    url: `/content/tagRel/${id}`,
    method: 'GET',
  })
}

export const createContentTagRel = (data: ContentTagRelCreate, requestId?: string) => {
  return request<ResultVo<number>>({
    url: '/content/tagRel',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateContentTagRel = (data: ContentTagRelUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/content/tagRel',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteContentTagRel = (id: number) => {
  return request<ResultVo<object>>({
    url: `/content/tagRel/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteContentTagRel = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/content/tagRel/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getContentTagRelList = () => {
  return request<ResultVo<ContentTagRelVo[]>>({
    url: '/content/tagRel/list',
    method: 'GET',
  })
}

export const getContentTagRelSelectAll = () => {
  return request<ResultVo<ContentTagRelVo[]>>({
    url: '/content/tagRel/selectAll',
    method: 'GET',
  })
}

