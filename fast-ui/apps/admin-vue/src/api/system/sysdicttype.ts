import request from '@/utils/axios.ts'

export interface SysDictTypeVo {
  id?: number
  name: string
  type?: string
  status?: number
  remark?: string
}

export interface SysDictTypeQuery {
  page: number
  pageSize: number
  name?: string
  type?: string
  status?: number
}

export interface SysDictTypeCreate {
  name: string
  type?: string
  status?: number
  remark?: string
}

export interface SysDictTypeUpdate extends SysDictTypeCreate {
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

export const getDictTypePage = (data: SysDictTypeQuery) => {
  return request<ResultVo<PageVo<SysDictTypeVo>>>({
    url: '/sys/dict/type/page',
    method: 'POST',
    data,
  })
}

export const getDictTypeById = (id: number) => {
  return request<ResultVo<SysDictTypeVo>>({
    url: `/sys/dict/type/${id}`,
    method: 'GET',
  })
}

export const createDictType = (data: SysDictTypeCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/dict/type',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateDictType = (data: SysDictTypeUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/dict/type',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteDictType = (id: number) => {
  return request<ResultVo<object>>({
    url: `/sys/dict/type/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteDictType = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/sys/dict/type/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getDictTypeSelectAll = () => {
  return request<ResultVo<SysDictTypeVo[]>>({
    url: '/sys/dict/type/selectAll',
    method: 'GET',
  })
}

export const getDictTypeList = () => {
  return request<ResultVo<SysDictTypeVo[]>>({
    url: '/sys/dict/type/list',
    method: 'GET',
  })
}
