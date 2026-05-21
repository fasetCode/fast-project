import request from '@/utils/axios.ts'

export interface MallProductCategoryVo {
  id?: number
  name?: string
  parentId?: number
  level?: number
  icon?: string
  image?: string
  sort?: number
  showInHome?: number
  status?: number
  description?: string
  children?: MallProductCategoryVo[]
}

export interface MallProductCategoryQuery {
  page: number
  pageSize: number
  name?: string
  parentId?: number
  status?: number
}

export interface MallProductCategoryCreate {
  name?: string
  parentId?: number
  level?: number
  icon?: string
  image?: string
  sort?: number
  showInHome?: number
  status?: number
  description?: string
}

export interface MallProductCategoryUpdate extends MallProductCategoryCreate {
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

export const getCategoryPage = (data: MallProductCategoryQuery) => {
  return request<ResultVo<PageVo<MallProductCategoryVo>>>({
    url: '/mall/category/page',
    method: 'POST',
    data,
  })
}

export const getCategoryById = (id: number) => {
  return request<ResultVo<MallProductCategoryVo>>({
    url: `/mall/category/${id}`,
    method: 'GET',
  })
}

export const createCategory = (data: MallProductCategoryCreate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/category',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const updateCategory = (data: MallProductCategoryUpdate, requestId?: string) => {
  return request<ResultVo<object>>({
    url: '/mall/category',
    method: 'PUT',
    data,
    headers: { 'x-request-id': requestId }
  })
}

export const deleteCategory = (id: number) => {
  return request<ResultVo<object>>({
    url: `/mall/category/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteCategory = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/mall/category/batch',
    method: 'DELETE',
    data: ids,
  })
}

export const getCategoryList = () => {
  return request<ResultVo<MallProductCategoryVo[]>>({
    url: '/mall/category/list',
    method: 'GET',
  })
}

export const getCategoryTree = () => {
  return request<ResultVo<MallProductCategoryVo[]>>({
    url: '/mall/category/tree',
    method: 'GET',
  })
}

export const getCategorySelectAll = () => {
  return request<ResultVo<MallProductCategoryVo[]>>({
    url: '/mall/category/selectAll',
    method: 'GET',
  })
}
