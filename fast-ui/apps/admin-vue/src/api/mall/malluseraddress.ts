import request from '@/utils/axios.ts'

export interface MallUserAddressVo {
  id?: number
  userId?: number
  receiverName?: string
  receiverPhone?: string
  province?: string
  provinceCode?: string
  city?: string
  cityCode?: string
  district?: string
  districtCode?: string
  detailAddress?: string
  postalCode?: string
  isDefault?: number
  tag?: string
  longitude?: string
  latitude?: string
}

export interface MallUserAddressQuery {
  page: number
  pageSize: number
  userId?: number
  isDefault?: number
}

export interface MallUserAddressCreate {
  userId?: number
  receiverName?: string
  receiverPhone?: string
  province?: string
  provinceCode?: string
  city?: string
  cityCode?: string
  district?: string
  districtCode?: string
  detailAddress?: string
  postalCode?: string
  isDefault?: number
  tag?: string
  longitude?: string
  latitude?: string
}

export interface MallUserAddressUpdate extends MallUserAddressCreate {
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

export const getUserAddressPage = (data: MallUserAddressQuery) => {
  return request<ResultVo<PageVo<MallUserAddressVo>>>({
    url: '/mall/userAddress/page',
    method: 'POST',
    data,
  })
}

export const getUserAddressById = (id: number) => {
  return request<ResultVo<MallUserAddressVo>>({
    url: `/mall/userAddress/${id}`,
    method: 'GET',
  })
}

export const createUserAddress = (data: MallUserAddressCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/userAddress',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateUserAddress = (data: MallUserAddressUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/userAddress',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteUserAddress = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/userAddress/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteUserAddress = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/userAddress/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getUserAddressList = () => {
  return request<ResultVo<MallUserAddressVo[]>>({
    url: '/mall/userAddress/list',
    method: 'GET',
  })
}

export const getUserAddressByUser = (userId: number) => {
  return request<ResultVo<MallUserAddressVo[]>>({
    url: `/mall/userAddress/byUser/${userId}`,
    method: 'GET',
  })
}

export const getUserAddressDefault = (userId: number) => {
  return request<ResultVo<MallUserAddressVo>>({
    url: `/mall/userAddress/default/${userId}`,
    method: 'GET',
  })
}
