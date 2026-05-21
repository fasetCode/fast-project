import request from '@/utils/axios.ts'

export interface MallOrderVo {
  id?: number
  orderNo?: string
  userId?: number
  shopId?: number
  totalAmount?: number
  payAmount?: number
  freightAmount?: number
  discountAmount?: number
  totalQuantity?: number
  itemKindCount?: number
  itemsSnapshot?: string
  payType?: number
  sourceType?: number
  status?: number
  payTime?: string
  deliveryTime?: string
  receiveTime?: string
  closeTime?: string
  receiverName?: string
  receiverPhone?: string
  receiverProvince?: string
  receiverCity?: string
  receiverDistrict?: string
  receiverAddress?: string
  receiverPostalCode?: string
  expressCompany?: string
  expressNo?: string
  payTradeNo?: string
  userRemark?: string
  shopRemark?: string
}

export interface MallOrderQuery {
  page: number
  pageSize: number
  orderNo?: string
  userId?: number
  shopId?: number
  status?: number
  payType?: number
  sourceType?: number
  startTime?: string
  endTime?: string
}

export interface MallOrderCreate {
  orderNo?: string
  userId?: number
  shopId?: number
  totalAmount?: number
  payAmount?: number
  freightAmount?: number
  discountAmount?: number
  totalQuantity?: number
  itemKindCount?: number
  itemsSnapshot?: string
  payType?: number
  sourceType?: number
  status?: number
  payTime?: string
  deliveryTime?: string
  receiveTime?: string
  closeTime?: string
  receiverName?: string
  receiverPhone?: string
  receiverProvince?: string
  receiverCity?: string
  receiverDistrict?: string
  receiverAddress?: string
  receiverPostalCode?: string
  expressCompany?: string
  expressNo?: string
  payTradeNo?: string
  userRemark?: string
  shopRemark?: string
}

export interface MallOrderUpdate extends MallOrderCreate {
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

export const getOrderPage = (data: MallOrderQuery) => {
  return request<ResultVo<PageVo<MallOrderVo>>>({
    url: '/mall/order/page',
    method: 'POST',
    data,
  })
}

export const getOrderById = (id: number) => {
  return request<ResultVo<MallOrderVo>>({
    url: `/mall/order/${id}`,
    method: 'GET',
  })
}

export const createOrder = (data: MallOrderCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/order',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateOrder = (data: MallOrderUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/order',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteOrder = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/order/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteOrder = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/order/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getOrderList = () => {
  return request<ResultVo<MallOrderVo[]>>({
    url: '/mall/order/list',
    method: 'GET',
  })
}

export const getOrderByOrderNo = (orderNo: string) => {
  return request<ResultVo<MallOrderVo>>({
    url: `/mall/order/byOrderNo/${orderNo}`,
    method: 'GET',
  })
}
