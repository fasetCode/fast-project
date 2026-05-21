import request from '@/utils/axios.ts'

export interface PageVo<T> {
  data: T[]
  total: number
}

export interface ResultVo<T> {
  code: number
  data: T
  msg: string
}

/**
 * 租户 VO
 */
export interface SysTenantVo {
  id?: number
  name: string
  contactName: string
  contactPhone: string
  adminId?: string
  status: number
  domain: string
  expireTime: string
  accountCount: number
  remark: string
  createTime?: string
}

/**
 * 租户查询请求 DTO
 */
export interface SysTenantQuery {
  page: number
  pageSize: number
  name?: string
  contactName?: string
  status?: number
  domain?: string
}

/**
 * 租户创建请求 DTO
 */
export interface SysTenantCreate {
  name: string
  contactName: string
  contactPhone: string
  adminId?: string
  status: number
  domain: string
  expireTime: string
  accountCount: number
  remark: string
}

/**
 * 租户更新请求 DTO
 */
export interface SysTenantUpdate extends SysTenantCreate {
  id: number
}

/**
 * 分页查询租户
 */
export function getTenantPage(data: SysTenantQuery) {
  return request<ResultVo<PageVo<SysTenantVo>>>({
    url: '/sys/tenant/page',
    method: 'POST',
    data,
  })
}

/**
 * 获取租户详情
 */
export function getTenantById(id: number) {
  return request<ResultVo<SysTenantVo>>({
    url: `/sys/tenant/${id}`,
    method: 'GET',
  })
}

/**
 * 创建租户
 */
export function createTenant(data: SysTenantCreate, requestId: string) {
  return request<ResultVo<number>>({
    url: '/sys/tenant',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

/**
 * 更新租户
 */
export function updateTenant(data: SysTenantUpdate, requestId: string) {
  return request<ResultVo<void>>({
    url: '/sys/tenant',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

/**
 * 删除租户
 */
export function deleteTenant(id: number) {
  return request<ResultVo<object>>({
    url: `/sys/tenant/${id}`,
    method: 'DELETE',
  })
}

/**
 * 批量删除租户
 */
export function batchDeleteTenant(ids: number[]) {
  return request<ResultVo<object>>({
    url: '/sys/tenant/batch',
    method: 'DELETE',
    data: ids,
  })
}
