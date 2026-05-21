import request from '@/utils/axios.ts'

export interface MallFreightTemplateVo {
  id?: number
  name?: string
  shopId?: number
  chargeType?: number
  freeType?: number
  freeAmount?: number
  freeQuantity?: number
  freeWeight?: number
  defaultFirstUnit?: number
  defaultFirstFee?: number
  defaultAdditionalUnit?: number
  defaultAdditionalFee?: number
  regionRules?: string
  excludeRegions?: string
  freeRegionRules?: string
  isDefault?: number
  status?: number
  sort?: number
  remark?: string
}

export interface MallFreightTemplateQuery {
  page: number
  pageSize: number
  name?: string
  shopId?: number
  chargeType?: number
  status?: number
  isDefault?: number
}

export interface MallFreightTemplateCreate {
  name?: string
  shopId?: number
  chargeType?: number
  freeType?: number
  freeAmount?: number
  freeQuantity?: number
  freeWeight?: number
  defaultFirstUnit?: number
  defaultFirstFee?: number
  defaultAdditionalUnit?: number
  defaultAdditionalFee?: number
  regionRules?: string
  excludeRegions?: string
  freeRegionRules?: string
  isDefault?: number
  status?: number
  sort?: number
  remark?: string
}

export interface MallFreightTemplateUpdate extends MallFreightTemplateCreate {
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

export const getFreightTemplatePage = (data: MallFreightTemplateQuery) => {
  return request<ResultVo<PageVo<MallFreightTemplateVo>>>({
    url: '/mall/freightTemplate/page',
    method: 'POST',
    data,
  })
}

export const getFreightTemplateById = (id: number) => {
  return request<ResultVo<MallFreightTemplateVo>>({
    url: `/mall/freightTemplate/${id}`,
    method: 'GET',
  })
}

export const createFreightTemplate = (data: MallFreightTemplateCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/freightTemplate',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateFreightTemplate = (data: MallFreightTemplateUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/freightTemplate',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteFreightTemplate = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/freightTemplate/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteFreightTemplate = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/freightTemplate/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getFreightTemplateList = () => {
  return request<ResultVo<MallFreightTemplateVo[]>>({
    url: '/mall/freightTemplate/list',
    method: 'GET',
  })
}

export const getFreightTemplateSelectAll = () => {
  return request<ResultVo<MallFreightTemplateVo[]>>({
    url: '/mall/freightTemplate/selectAll',
    method: 'GET',
  })
}
