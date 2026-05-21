import request from '@/utils/request'

export const getInitCheck = () => {
  return request.get('/chat/init/check')
}

export const initSetup = (data: any) => {
  return request.post('/chat/init/setup', data)
}

export const getUserPage = (params: any) => {
  return request.post('/chat/user/page', params)
}

export const getUser = (id: number) => {
  return request.get(`/chat/user/${id}`)
}

export const saveUser = (data: any) => {
  return request.post('/chat/user', data)
}

export const updateUser = (data: any) => {
  return request.put('/chat/user', data)
}

export const deleteUser = (id: number) => {
  return request.delete(`/chat/user/${id}`)
}

export const batchDeleteUser = (ids: number[]) => {
  return request.delete('/chat/user/batch', { data: ids })
}

export const getConfigPage = (params: any) => {
  return request.post('/chat/config/page', params)
}

export const getConfig = (id: number) => {
  return request.get(`/chat/config/${id}`)
}

export const saveConfig = (data: any) => {
  return request.post('/chat/config', data)
}

export const updateConfig = (data: any) => {
  return request.put('/chat/config', data)
}

export const deleteConfig = (id: number) => {
  return request.delete(`/chat/config/${id}`)
}

export const batchDeleteConfig = (ids: number[]) => {
  return request.delete('/chat/config/batch', { data: ids })
}

export const getWsDomain = () => {
  return request.get('/chat/ws-domain')
}

export const getWsToken = (data: { userId: string; groupId: string; token: string }) => {
  return request.post('/chat/init/ws-token', data)
}
