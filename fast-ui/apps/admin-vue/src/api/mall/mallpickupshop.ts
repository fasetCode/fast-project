import request from '@/utils/axios.ts'

export interface MallPickupShopVo {
  id?: number
  shopId?: number
  name?: string
  code?: string
  contactName?: string
  contactPhone?: string
  province?: string
  provinceCode?: string
  city?: string
  cityCode?: string
  district?: string
  districtCode?: string
  detailAddress?: string
  postalCode?: string
  longitude?: number
  latitude?: number
  openTime?: string
  pickupNotice?: string
  sort?: number
  status?: number
  remark?: string
}

export interface MallPickupShopQuery {
  page: number
  pageSize: number
  name?: string
  shopId?: number
  province?: string
  city?: string
  status?: number
}

export interface MallPickupShopCreate {
  shopId?: number
  name?: string
  code?: string
  contactName?: string
  contactPhone?: string
  province?: string
  provinceCode?: string
  city?: string
  cityCode?: string
  district?: string
  districtCode?: string
  detailAddress?: string
  postalCode?: string
  longitude?: number
  latitude?: number
  openTime?: string
  pickupNotice?: string
  sort?: number
  status?: number
  remark?: string
}

export interface MallPickupShopUpdate extends MallPickupShopCreate {
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

export const getPickupShopPage = (data: MallPickupShopQuery) => {
  return request<ResultVo<PageVo<MallPickupShopVo>>>({
    url: '/mall/pickupShop/page',
    method: 'POST',
    data,
  })
}

export const getPickupShopById = (id: number) => {
  return request<ResultVo<MallPickupShopVo>>({
    url: `/mall/pickupShop/${id}`,
    method: 'GET',
  })
}

export const createPickupShop = (data: MallPickupShopCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/pickupShop',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updatePickupShop = (data: MallPickupShopUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/pickupShop',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deletePickupShop = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/pickupShop/${id}`,
    method: 'DELETE',
  })
}

export const batchDeletePickupShop = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/pickupShop/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getPickupShopList = () => {
  return request<ResultVo<MallPickupShopVo[]>>({
    url: '/mall/pickupShop/list',
    method: 'GET',
  })
}

export const getPickupShopSelectAll = () => {
  return request<ResultVo<MallPickupShopVo[]>>({
    url: '/mall/pickupShop/selectAll',
    method: 'GET',
  })
}
