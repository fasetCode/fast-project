import request from '@/utils/axios.ts'

export interface LoginDto {
  username: string
  password: string
  captchaKey: string
  captcha: string
}

export interface LoginVo {
  token: string
  username: string
  nickname: string
  avatar?: string
}

export interface UserInfoVo {
  id: number
  username: string
  nickname: string
  avatar?: string
}

export interface CaptchaVo {
  captchaKey: string
  captchaImage: string
}

export const login = (data: LoginDto, requestId?: string) => {
  return request<LoginVo>({
    url: '/auth/login',
    method: 'POST',
    data,
    headers: { 'x-request-id': requestId }
  })
}

/**
 * 获取验证码
 */
export const getCaptcha = () => {
  return request<CaptchaVo>({
    url: '/auth/captcha',
    method: 'GET',
  })
}

export const logout = () => {
  return request<object>({
    url: '/auth/logout',
    method: 'POST',
  })
}

/**
 * 获取当前登录用户信息
 */
export const getUserInfo = () => {
  return request<UserInfoVo>({
    url: '/auth/getUserInfo',
    method: 'GET',
  })
}
