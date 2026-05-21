import request from '@/utils/axios.ts'

export interface SysPostVo {
  id?: number
  name: string
  code?: string
  sort?: number
  status?: number
  remark?: string
}

export interface SysPostQuery {
  page: number
  pageSize: number
  name?: string
  code?: string
  status?: number
}

export interface SysPostCreate {
  name: string
  code?: string
  sort?: number
  status?: number
  remark?: string
}

export interface SysPostUpdate extends SysPostCreate {
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

export const getPostPage = (data: SysPostQuery) => {
  return request<ResultVo<PageVo<SysPostVo>>>({
    url: '/sys/post/page',
    method: 'POST',
    data,
  })
}

export const getPostById = (id: number) => {
  return request<ResultVo<SysPostVo>>({
    url: `/sys/post/${id}`,
    method: 'GET',
  })
}

export const createPost = (data: SysPostCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/post',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updatePost = (data: SysPostUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/post',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deletePost = (id: number) => {
  return request<ResultVo<object>>({
    url: `/sys/post/${id}`,
    method: 'DELETE',
  })
}

export const batchDeletePost = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/sys/post/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getPostList = () => {
  return request<ResultVo<SysPostVo[]>>({
    url: '/sys/post/list',
    method: 'GET',
  })
}

export const getPostSelectAll = () => {
  return request<ResultVo<SysPostVo[]>>({
    url: '/sys/post/selectAll',
    method: 'GET',
  })
}
