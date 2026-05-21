import request from '@/utils/axios.ts'

export interface SysDictDataVo {
  id?: number
  sort?: number
  label: string
  value: string
  dictTypeId?: number
  status?: number
  remark?: string
}

export interface SysDictDataQuery {
  page: number
  pageSize: number
  label?: string
  value?: string
  dictTypeId?: number
  status?: number
}

export interface SysDictDataCreate {
  sort?: number
  label: string
  value: string
  dictTypeId?: number
  status?: number
  remark?: string
}

export interface SysDictDataUpdate extends SysDictDataCreate {
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

export const getDictDataPage = (data: SysDictDataQuery) => {
  return request<ResultVo<PageVo<SysDictDataVo>>>({
    url: '/sys/dict/data/page',
    method: 'POST',
    data,
  })
}

export const getDictDataById = (id: number) => {
  return request<ResultVo<SysDictDataVo>>({
    url: `/sys/dict/data/${id}`,
    method: 'GET',
  })
}

export const createDictData = (data: SysDictDataCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/dict/data',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateDictData = (data: SysDictDataUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/dict/data',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteDictData = (id: number) => {
  return request<ResultVo<object>>({
    url: `/sys/dict/data/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteDictData = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/sys/dict/data/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getDictDataByDictTypeId = (dictTypeId: number) => {
  return request<ResultVo<SysDictDataVo[]>>({
    url: `/sys/dict/data/type/${dictTypeId}`,
    method: 'GET',
  })
}
