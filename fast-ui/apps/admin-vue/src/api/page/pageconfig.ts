import request from '@/utils/axios.ts'

export interface PageConfigVo {
  id?: string
  title?: string
  pathUrl?: string
  config?: string
  status?: number
  version?: string
  applicationId?: string
  applicationName?: string
}

export interface PageConfigListVo {
  id?: string
  title?: string
  pathUrl?: string
  status?: number
  version?: string
  applicationId?: string
  applicationName?: string
}

export interface PageConfigQuery {
  page: number
  pageSize: number
  pathUrl?: string
  status?: number
  version?: string
  applicationId?: string
}

export interface PageConfigCreate {
  title?: string
  pathUrl?: string
  config?: string
  status?: number
  version?: string
  applicationId?: string
}

export interface PageConfigUpdate extends PageConfigCreate {
  id: string
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

export const getPageConfigPage = (data: PageConfigQuery) => {
  return request<ResultVo<PageVo<PageConfigVo>>>({
    url: '/page/config/page',
    method: 'POST',
    data,
  })
}

export const getPageConfigById = (id: string) => {
  return request<ResultVo<PageConfigVo>>({
    url: `/page/config/${id}`,
    method: 'GET',
  })
}

export const createPageConfig = (data: PageConfigCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/config',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updatePageConfig = (data: PageConfigUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/config',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deletePageConfig = (id: string) => {
  return request<ResultVo<object>>({
    url: `/page/config/${id}`,
    method: 'DELETE',
  })
}

export const batchDeletePageConfig = (ids: string[]) => {
  return request<ResultVo<object>>({
    url: '/page/config/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getPageConfigList = (applicationId: string) => {
  return request<ResultVo<PageConfigListVo[]>>({
    url: `/page/config/list/${applicationId}`,
    method: 'GET',
  })
}

export const getPageConfigComponentsByTypeId = (typeId: string)=>{
  return request<ResultVo<object>>({
    url: `/page/config/components/${typeId}`,
    method: 'GET',
  })
}

export const publishPageConfig = (data: PageConfigUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/page/config/publish',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}
