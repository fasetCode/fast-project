import axios from '@/utils/axios'

export interface ResultVo<T = any> {
  code: number
  msg: string
  data: T
}

export interface PageVo<T = any> {
  records: T[]
  total: number
  size: number
  current: number
  pages: number
}

// 示例：获取数据
export const getExampleData = () => {
  return axios.get<any, ResultVo>('/example/data')
}

// 示例：分页查询
export const getExamplePage = (params: any) => {
  return axios.post<any, ResultVo<PageVo>>('/example/page', params)
}
