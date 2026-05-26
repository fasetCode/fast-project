import request from '@/utils/axios'

export interface PageQuery {
  page: number
  pageSize: number
}

export interface PageVo<T> {
  data: T
  total: number
}

export interface ResultVo<T> {
  code: number
  data: T
  msg: string
}

export const createCrudApi = <T>(baseUrl: string) => {
  return {
    page: (data: PageQuery) =>
      request<ResultVo<PageVo<T[]>>>({
        url: `${baseUrl}/page`,
        method: 'POST',
        data,
      }),

    getById: (id: number) =>
      request<ResultVo<T>>({
        url: `${baseUrl}/${id}`,
        method: 'GET',
      }),

    create: (data: Partial<T>, requestId?: string) =>
      request<ResultVo<number>>({
        url: baseUrl,
        method: 'POST',
        data,
        headers: { 'x-request-id': requestId },
      }),

    update: (data: Partial<T>, requestId?: string) =>
      request<ResultVo<object>>({
        url: baseUrl,
        method: 'PUT',
        data,
        headers: { 'x-request-id': requestId },
      }),

    delete: (id: number) =>
      request<ResultVo<object>>({
        url: `${baseUrl}/${id}`,
        method: 'DELETE',
      }),

    batchDelete: (ids: number[]) =>
      request<ResultVo<object>>({
        url: `${baseUrl}/batch`,
        method: 'DELETE',
        data: ids,
      }),

    list: () =>
      request<ResultVo<T[]>>({
        url: `${baseUrl}/list`,
        method: 'GET',
      }),

    selectAll: () =>
      request<ResultVo<T[]>>({
        url: `${baseUrl}/selectAll`,
        method: 'GET',
      }),
  }
}

