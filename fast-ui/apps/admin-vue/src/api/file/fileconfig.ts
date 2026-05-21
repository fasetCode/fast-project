import request from '@/utils/axios.ts'

export interface FileConfigVo {
  id?: number
  storagePath?: string
  accessDomain?: string
  status?: number
  type?: string
  description?: string
  remoteUrl?: string
  remoteToken?: string
  config?: string
}

export interface FileConfigQuery {
  page: number
  pageSize: number
  status?: number
  type?: string
  description?: string
}

export interface FileConfigCreate {
  storagePath?: string
  accessDomain?: string
  status?: number
  type: string
  description?: string
  remoteUrl?: string
  remoteToken?: string
  config?: string
}

export interface FileConfigUpdate extends FileConfigCreate {
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

export const getFileConfigPage = (data: FileConfigQuery) => {
  return request<ResultVo<PageVo<FileConfigVo>>>({
    url: '/file/config/page',
    method: 'POST',
    data,
  })
}

export const getFileConfigById = (id: number) => {
  return request<ResultVo<FileConfigVo>>({
    url: `/file/config/${id}`,
    method: 'GET',
  })
}

export const getFileConfigByType = (type: string) => {
  return request<ResultVo<FileConfigVo>>({
    url: `/file/config/type/${type}`,
    method: 'GET',
  })
}

export const createFileConfig = (data: FileConfigCreate) => {
  return request<ResultVo<object>>({
    url: '/file/config',
    method: 'POST',
    data,
  })
}

export const updateFileConfig = (data: FileConfigUpdate) => {
  return request<ResultVo<object>>({
    url: '/file/config',
    method: 'PUT',
    data,
  })
}

export const deleteFileConfig = (id: number) => {
  return request<ResultVo<object>>({
    url: `/file/config/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteFileConfig = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/file/config/batch',
    method: 'DELETE',
    data: ids,
  })
}
