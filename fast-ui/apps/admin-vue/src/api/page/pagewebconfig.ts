import request from '@/utils/axios.ts'

export interface PageWebConfigVo {
  id?: number
  pathUrl?: string
  config?: string
  applicationId?: number
  applicationName?: string
}

export interface PageWebConfigQuery {
  page: number
  pageSize: number
  pathUrl?: string
  applicationId?: string
}

export interface PageWebConfigCreate {
  pathUrl?: string
  config?: string
  applicationId?: number
}

export interface PageWebConfigUpdate extends PageWebConfigCreate {
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

export const getPageWebConfigPage = (data: PageWebConfigQuery) => {
  return request<ResultVo<PageVo<PageWebConfigVo>>>({
    url: '/page/web/config/page',
    method: 'POST',
    data,
  })
}

export const getPageWebConfigById = (id: number) => {
  return request<ResultVo<PageWebConfigVo>>({
    url: `/page/web/config/${id}`,
    method: 'GET',
  })
}

export const createPageWebConfig = (data: PageWebConfigCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/web/config',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updatePageWebConfig = (data: PageWebConfigUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/web/config',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deletePageWebConfig = (id: number) => {
  return request<ResultVo<object>>({
    url: `/page/web/config/${id}`,
    method: 'DELETE',
  })
}

export const batchDeletePageWebConfig = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/page/web/config/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getPageWebConfigList = () => {
  return request<ResultVo<PageWebConfigVo[]>>({
    url: '/page/web/config/list',
    method: 'GET',
  })
}

export const getPageWebConfigSelectAll = () => {
  return request<ResultVo<PageWebConfigVo[]>>({
    url: '/page/web/config/selectAll',
    method: 'GET',
  })
}
