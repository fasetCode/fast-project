import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import JSONbig from 'json-bigint'
import router from '@/router'
import TokenService from './token'

const createAxios = (config?: AxiosRequestConfig): AxiosInstance => {
  const instance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
    timeout: 10000,
    transformResponse: [
      (data: string) => {
        try {
          const res = JSONbig.parse(data)
          return res
        } catch (err) {
          return data
        }
      }
    ],
    ...config,
  })

  // 请求拦截器
  instance.interceptors.request.use(
    (config) => {
      const token = TokenService.getToken()
      if (token) {
        config.headers.Authorization = `${token}`
      }
      return config
    },
    (error) => Promise.reject(error)
  )

  // 响应拦截器
  instance.interceptors.response.use(
    (response: AxiosResponse) => response.data,
    (error) => {
      const status = error.response?.status
      const data = error.response?.data

      // 401 未登录：清除 token 并跳转到登录页
      if (status === 401) {
        TokenService.removeToken()
        router.push('/login')
        return Promise.reject(new Error('请先登录'))
      }
      // 403 未授权：返回统一错误格式
      if (status === 403) {
        return Promise.resolve({ code: 403, msg: data?.msg || '没有权限访问该资源', data: null })
      }
      return Promise.reject(error)
    }
  )

  return instance
}

export default createAxios()
