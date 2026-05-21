import axios from '@/utils/axios'

export interface ResultVo<T = any> {
  code: number
  msg: string
  data: T
}

// 根据路径获取页面配置
export const getPageConfigByPath = (path: string, appCode: string) => {
  return axios.get<any, ResultVo<string>>('/common/page/config/getByPath', {
    params: {
      path,
      appCode
    }
  })
}
