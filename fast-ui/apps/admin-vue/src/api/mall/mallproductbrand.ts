import request from '@/utils/axios.ts'

export interface MallProductBrandVo {
  id?: number
  name?: string
  code?: string
  logo?: string
  firstLetter?: string
  description?: string
  sort?: number
  showInHome?: number
  status?: number
}

export interface MallProductBrandQuery {
  page: number
  pageSize: number
  name?: string
  code?: string
  firstLetter?: string
  status?: number
}

export interface MallProductBrandCreate {
  name?: string
  code?: string
  logo?: string
  firstLetter?: string
  description?: string
  sort?: number
  showInHome?: number
  status?: number
}

export interface MallProductBrandUpdate extends MallProductBrandCreate {
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

export const getBrandPage = (data: MallProductBrandQuery) => {
  return request<ResultVo<PageVo<MallProductBrandVo>>>({
    url: '/mall/brand/page',
    method: 'POST',
    data,
  })
}

export const getBrandById = (id: number) => {
  return request<ResultVo<MallProductBrandVo>>({
    url: `/mall/brand/${id}`,
    method: 'GET',
  })
}

export const createBrand = (data: MallProductBrandCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/brand',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateBrand = (data: MallProductBrandUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/brand',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteBrand = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/brand/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteBrand = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/brand/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getBrandList = () => {
  return request<ResultVo<MallProductBrandVo[]>>({
    url: '/mall/brand/list',
    method: 'GET',
  })
}

export const getBrandSelectAll = () => {
  return request<ResultVo<MallProductBrandVo[]>>({
    url: '/mall/brand/selectAll',
    method: 'GET',
  })
}
