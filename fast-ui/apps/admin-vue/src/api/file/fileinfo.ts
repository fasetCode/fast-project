import request from '@/utils/axios.ts'

export interface FileInfoVo {
  id?: number
  fileName?: string
  fileSize?: number
  fileType?: string
  fileMd5?: string
  status?: number
  fileStorage?: string
  accessPath?: string
}

export interface FileInfoQuery {
  page: number
  pageSize: number
  fileName?: string
  fileType?: string
  status?: number
  fileMd5?: string
}

export interface FileInfoCreate {
  fileName?: string
  fileSize?: number
  fileType?: string
  fileMd5?: string
  status?: number
  fileStorage?: string
  accessPath?: string
}

export interface FileInfoUpdate extends FileInfoCreate {
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

export interface FileTypeStat {
  id?: number
  name?: string
  platformRatio?: number
  fileSpace?: number
}

export const getFileTypeStats = () => {
  return request<ResultVo<FileTypeStat[]>>({
    url: '/file/info/type-stats',
    method: 'GET',
  })
}

export const getFileInfoPage = (data: FileInfoQuery) => {
  return request<ResultVo<PageVo<FileInfoVo>>>({
    url: '/file/info/page',
    method: 'POST',
    data,
  })
}

export const getFileInfoById = (id: number) => {
  return request<ResultVo<FileInfoVo>>({
    url: `/file/info/${id}`,
    method: 'GET',
  })
}

export const getFileInfoByMd5 = (fileMd5: string) => {
  return request<ResultVo<FileInfoVo>>({
    url: `/file/info/md5/${fileMd5}`,
    method: 'GET',
  })
}

export const createFileInfo = (data: FileInfoCreate) => {
  return request<ResultVo<object>>({
    url: '/file/info',
    method: 'POST',
    data,
  })
}

export const updateFileInfo = (data: FileInfoUpdate) => {
  return request<ResultVo<object>>({
    url: '/file/info',
    method: 'PUT',
    data,
  })
}

export const deleteFileInfo = (id: number) => {
  return request<ResultVo<object>>({
    url: `/file/info/${id}`,
    method: 'DELETE',
  })
}

export const batchDeleteFileInfo = (ids: number[]) => {
  return request<ResultVo<object>>({
    url: '/file/info/batch',
    method: 'DELETE',
    data: ids,
  })
}
