import request from '@/utils/axios.ts'

export interface SysConfigVo {
  id?: number
  name: string
  configKey: string
  configValue?: string
  type?: string
  status?: number
  remark?: string
}

export interface SysConfigQuery {
  page: number
  pageSize: number
  name?: string
  configKey?: string
  type?: string
  status?: number
}

export interface SysConfigCreate {
  name: string
  configKey: string
  configValue?: string
  type?: string
  status?: number
  remark?: string
}

export interface SysConfigUpdate extends SysConfigCreate {
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

export const getConfigPage = (data: SysConfigQuery) => {
  return request<ResultVo<PageVo<SysConfigVo>>>({
    url: '/sys/config/page',
    method: 'POST',
    data,
  })
}

export const getConfigById = (id: number) => {
  return request<ResultVo<SysConfigVo>>({
    url: `/sys/config/${id}`,
    method: 'GET',
  })
}

export const createConfig = (data: SysConfigCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/config',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateConfig = (data: SysConfigUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/sys/config',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteConfig = (id: number) => {
  return request<ResultVo<object>>({
    url: `/sys/config/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteConfig = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/sys/config/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getConfigValue = (configKey: string) => {
  return request<ResultVo<string>>({
    url: `/sys/config/key/${configKey}`,
    method: 'GET',
  })
}
