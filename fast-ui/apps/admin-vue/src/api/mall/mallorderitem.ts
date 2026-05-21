import request from '@/utils/axios.ts'

export interface MallOrderItemVo {
  id?: number
  orderId?: number
  orderNo?: string
  userId?: number
  shopId?: number
  productId?: number
  skuId?: number
  categoryId?: number
  brandId?: number
  productSnapshot?: string
  skuSnapshot?: string
  price?: number
  originalPrice?: number
  payType?: number
  pointsPrice?: number
  pointsAmount?: number
  giftPointsAmount?: number
  deliveryType?: number
  virtualType?: number
  quantity?: number
  discountAmount?: number
  totalAmount?: number
  refundStatus?: number
  refundAmount?: number
  refundQuantity?: number
  commented?: number
  remark?: string
  extra?: string
}

export interface MallOrderItemQuery {
  page: number
  pageSize: number
  orderId?: number
  orderNo?: string
  userId?: number
  shopId?: number
  productId?: number
  skuId?: number
  refundStatus?: number
  commented?: number
}

export interface MallOrderItemCreate {
  orderId?: number
  orderNo?: string
  userId?: number
  shopId?: number
  productId?: number
  skuId?: number
  categoryId?: number
  brandId?: number
  productSnapshot?: string
  skuSnapshot?: string
  price?: number
  originalPrice?: number
  payType?: number
  pointsPrice?: number
  pointsAmount?: number
  giftPointsAmount?: number
  deliveryType?: number
  virtualType?: number
  quantity?: number
  discountAmount?: number
  totalAmount?: number
  refundStatus?: number
  refundAmount?: number
  refundQuantity?: number
  commented?: number
  remark?: string
  extra?: string
}

export interface MallOrderItemUpdate extends MallOrderItemCreate {
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

export const getOrderItemPage = (data: MallOrderItemQuery) => {
  return request<ResultVo<PageVo<MallOrderItemVo>>>({
    url: '/mall/orderItem/page',
    method: 'POST',
    data,
  })
}

export const getOrderItemById = (id: number) => {
  return request<ResultVo<MallOrderItemVo>>({
    url: `/mall/orderItem/${id}`,
    method: 'GET',
  })
}

export const createOrderItem = (data: MallOrderItemCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/orderItem',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateOrderItem = (data: MallOrderItemUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/orderItem',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteOrderItem = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/orderItem/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteOrderItem = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/orderItem/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getOrderItemList = () => {
  return request<ResultVo<MallOrderItemVo[]>>({
    url: '/mall/orderItem/list',
    method: 'GET',
  })
}

export const getOrderItemByOrder = (orderId: number) => {
  return request<ResultVo<MallOrderItemVo[]>>({
    url: `/mall/orderItem/byOrder/${orderId}`,
    method: 'GET',
  })
}
