import request from '@/utils/axios.ts'

export interface FileDomainVo {
  id?: number
  configId?: number
  domain?: string
  status?: number
}

export interface FileDomainQuery {
  page: number
  pageSize: number
  configId?: number
  domain?: string
  status?: number
}

export interface FileDomainCreate {
  configId?: number
  domain: string
  status?: number
}

export interface FileDomainUpdate extends FileDomainCreate {
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

export const getFileDomainPage = (data: FileDomainQuery) => {
  return request<ResultVo<PageVo<FileDomainVo>>>({
    url: '/file/domain/page',
    method: 'POST',
    data,
  })
}

export const getFileDomainById = (id: number) => {
  return request<ResultVo<FileDomainVo>>({
    url: `/file/domain/${id}`,
    method: 'GET',
  })
}

export const getFileDomainByDomain = (domain: string) => {
  return request<ResultVo<FileDomainVo>>({
    url: `/file/domain/domain/${domain}`,
    method: 'GET',
  })
}

export const getFileDomainByConfigId = (configId: number) => {
  return request<ResultVo<FileDomainVo[]>>({
    url: `/file/domain/config/${configId}`,
    method: 'GET',
  })
}

export const getFileDomainEnabled = () => {
  return request<ResultVo<FileDomainVo[]>>({
    url: '/file/domain/enabled',
    method: 'GET',
  })
}

export const createFileDomain = (data: FileDomainCreate) => {
  return request<ResultVo<object>>({
    url: '/file/domain',
    method: 'POST',
    data,
  })
}

export const updateFileDomain = (data: FileDomainUpdate) => {
  return request<ResultVo<object>>({
    url: '/file/domain',
    method: 'PUT',
    data,
  })
}

export const deleteFileDomain = (id: number) => {
  return request<ResultVo<object>>({
    url: `/file/domain/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteFileDomain = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/file/domain/batch',
    method: 'DELETE',
    data: ids,
  })
}
