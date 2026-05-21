import request from '@/utils/axios.ts'

export interface FileUploadResponse {
  fileId?: number
  fileName?: string
  fileSize?: number
  fileMd5?: string
  accessPath?: string
  existed?: boolean
  uploadedChunks?: number[]
}

export interface ChunkCheckResponse {
  needUpload?: boolean
  fileId?: number
  uploadedChunks?: number[]
  totalChunks?: number
}

export interface FileChunkUploadRequest {
  fileMd5: string
  fileName: string
  fileSize: number
  fileType: string
  chunkNumber: number
  totalChunks: number
  chunkSize: number
  configId?: number
}

export interface FileChunkMergeRequest {
  fileMd5: string
  fileName: string
  fileSize: number
  fileType: string
  totalChunks: number
  configId?: number
}

export interface ResultVo<T> {
  code: number
  data: T
  msg: string
}

/**
 * 小文件直接上传
 */
export const uploadSimple = (file: File, configId?: number, onProgress?: (percent: number) => void) => {
  const formData = new FormData()
  formData.append('file', file)
  if (configId) {
    formData.append('configId', configId.toString())
  }

  return request<ResultVo<FileUploadResponse>>({
    url: '/file/upload/simple',
    method: 'POST',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    onUploadProgress: (progressEvent) => {
      if (onProgress && progressEvent.total) {
        const percent = Math.round((progressEvent.loaded * 100) / progressEvent.total)
        onProgress(percent)
      }
    }
  })
}

/**
 * 检查分片上传状态（秒传检查）
 */
export const checkChunk = (fileMd5: string, fileSize: number, fileName: string) => {
  return request<ResultVo<ChunkCheckResponse>>({
    url: '/file/upload/chunk/check',
    method: 'GET',
    params: { fileMd5, fileSize, fileName }
  })
}

/**
 * 上传单个分片
 */
export const uploadChunk = (chunk: Blob, uploadRequest: FileChunkUploadRequest, onProgress?: (percent: number) => void) => {
  const formData = new FormData()
  formData.append('file', chunk)
  formData.append('fileMd5', uploadRequest.fileMd5)
  formData.append('fileName', uploadRequest.fileName)
  formData.append('fileSize', uploadRequest.fileSize.toString())
  formData.append('fileType', uploadRequest.fileType)
  formData.append('chunkNumber', uploadRequest.chunkNumber.toString())
  formData.append('totalChunks', uploadRequest.totalChunks.toString())
  formData.append('chunkSize', uploadRequest.chunkSize.toString())
  if (uploadRequest.configId) {
    formData.append('configId', uploadRequest.configId.toString())
  }

  return request<ResultVo<FileUploadResponse>>({
    url: '/file/upload/chunk',
    method: 'POST',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    onUploadProgress: (progressEvent) => {
      if (onProgress && progressEvent.total) {
        const percent = Math.round((progressEvent.loaded * 100) / progressEvent.total)
        onProgress(percent)
      }
    }
  })
}

/**
 * 合并分片
 */
export const mergeChunks = (data: FileChunkMergeRequest) => {
  return request<ResultVo<FileUploadResponse>>({
    url: '/file/upload/chunk/merge',
    method: 'POST',
    data
  })
}

/**
 * 计算文件MD5
 */
export const calculateFileMd5 = (file: File): Promise<string> => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = (e) => {
      const spark = new (window as any).SparkMD5.ArrayBuffer()
      spark.append(e.target?.result as ArrayBuffer)
      const md5 = spark.end()
      resolve(md5)
    }
    reader.onerror = (e) => reject(e)
    reader.readAsArrayBuffer(file)
  })
}

/**
 * 根据文件ID构造文件访问URL（后端会302重定向到实际地址，浏览器<img>标签会自动跟随）
 */
export const getFileUrl = (fileId: number | string): string => {
  return `/api/file/getUrl/${fileId}`
}
