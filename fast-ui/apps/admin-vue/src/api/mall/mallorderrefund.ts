import request from '@/utils/axios.ts'

export interface MallOrderRefundVo {
  id?: number
  refundNo?: string
  orderId?: number
  orderNo?: string
  orderItemId?: number
  userId?: number
  shopId?: number
  refundType?: number
  refundStatus?: number
  refundAmount?: number
  refundPoints?: number
  refundQuantity?: number
  reason?: string
  description?: string
  evidence?: string
  shopReplyTime?: string
  shopReplyText?: string
  shopRejectReason?: string
  returnExpressCompany?: string
  returnExpressNo?: string
  payRefundTradeNo?: string
  finishTime?: string
  cancelTime?: string
  extra?: string
}

export interface MallOrderRefundQuery {
  page: number
  pageSize: number
  refundNo?: string
  orderNo?: string
  orderId?: number
  userId?: number
  shopId?: number
  refundType?: number
  refundStatus?: number
  startTime?: string
  endTime?: string
}

export interface MallOrderRefundCreate {
  refundNo?: string
  orderId?: number
  orderNo?: string
  orderItemId?: number
  userId?: number
  shopId?: number
  refundType?: number
  refundStatus?: number
  refundAmount?: number
  refundPoints?: number
  refundQuantity?: number
  reason?: string
  description?: string
  evidence?: string
  shopReplyTime?: string
  shopReplyText?: string
  shopRejectReason?: string
  returnExpressCompany?: string
  returnExpressNo?: string
  payRefundTradeNo?: string
  finishTime?: string
  cancelTime?: string
  extra?: string
}

export interface MallOrderRefundUpdate extends MallOrderRefundCreate {
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

export const getOrderRefundPage = (data: MallOrderRefundQuery) => {
  return request<ResultVo<PageVo<MallOrderRefundVo>>>({
    url: '/mall/orderRefund/page',
    method: 'POST',
    data,
  })
}

export const getOrderRefundById = (id: number) => {
  return request<ResultVo<MallOrderRefundVo>>({
    url: `/mall/orderRefund/${id}`,
    method: 'GET',
  })
}

export const createOrderRefund = (data: MallOrderRefundCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/orderRefund',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateOrderRefund = (data: MallOrderRefundUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/orderRefund',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteOrderRefund = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/orderRefund/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteOrderRefund = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/orderRefund/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getOrderRefundList = () => {
  return request<ResultVo<MallOrderRefundVo[]>>({
    url: '/mall/orderRefund/list',
    method: 'GET',
  })
}

export const getOrderRefundByRefundNo = (refundNo: string) => {
  return request<ResultVo<MallOrderRefundVo>>({
    url: `/mall/orderRefund/byRefundNo/${refundNo}`,
    method: 'GET',
  })
}
