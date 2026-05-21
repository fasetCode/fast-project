import request from '@/utils/axios.ts'

export interface MessageTestSend {
  templateCode: string
  receiver: string
  params: Record<string, any>
}

export interface MessageTestVerify {
  target: string
  code: string
}

export interface ResultVo<T> {
  code: number
  data: T
  msg: string
}

export const sendTestMessage = (data: MessageTestSend) => {
  return request<ResultVo<any>>({
    url: '/message/test/send',
    method: 'POST',
    data,
  })
}

export const verifyTestCode = (data: MessageTestVerify) => {
  return request<ResultVo<boolean>>({
    url: '/message/test/verify',
    method: 'POST',
    data,
  })
}
