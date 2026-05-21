import request from '@/utils/axios.ts'

export interface IdempotentDuplicateLogVo {
  id?: number
  /** 请求ID（前端生成的唯一标识） */
  requestId?: string
  /** 幂等键前缀 */
  prefix?: string
  /** 请求路径 */
  requestUrl?: string
  /** 请求方法（GET/POST/PUT/DELETE） */
  requestMethod?: string
  /** 请求参数（JSON） */
  requestParams?: string
  /** 用户ID */
  userId?: number
  /** 用户名 */
  username?: string
  /** IP地址 */
  ipAddress?: string
  /** User-Agent */
  userAgent?: string
  /** 操作标题 */
  title?: string
  /** 首次请求时间 */
  firstRequestTime?: string
  /** 最后重复提交时间 */
  lastDuplicateTime?: string
  /** 重复提交次数 */
  duplicateCount?: number
  /** 提示消息 */
  message?: string
  /** 创建时间 */
  createTime?: string
  /** 更新时间 */
  updateTime?: string
}

export interface IdempotentDuplicateLogQuery {
  page: number
  pageSize: number
  requestId?: string
  prefix?: string
  requestUrl?: string
  requestMethod?: string
  userId?: number
  username?: string
  ipAddress?: string
  title?: string
  firstRequestTimeStart?: string
  firstRequestTimeEnd?: string
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

// 获取幂等重复提交记录分页列表
export const getDuplicateLogPage = (params: IdempotentDuplicateLogQuery) => {
  return request<ResultVo<PageVo<IdempotentDuplicateLogVo[]>>>({
    url: '/idempotent/duplicate-log/page',
    method: 'POST',
    data: params,
  })
}

// 获取幂等重复提交记录详情
export const getDuplicateLogById = (id: number) => {
  return request<ResultVo<IdempotentDuplicateLogVo>>({
    url: `/idempotent/duplicate-log/${id}`,
    method: 'GET',
  })
}

// 删除幂等重复提交记录
export const deleteDuplicateLog = (id: number) => {
  return request<ResultVo<object>>({
    url: `/idempotent/duplicate-log/${id}`,
    method: 'DELETE',
  })
}

// 批量删除幂等重复提交记录
export const batchDeleteDuplicateLog = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/idempotent/duplicate-log/batch',
    method: 'DELETE',
    data: ids,
  })
}
