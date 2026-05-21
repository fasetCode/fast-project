<template>
  <div class="fileupload-container">
    <div class="upload-card">
      <div class="upload-header">
        <div class="header-icon">
          <CloudUploadOutlined />
        </div>
        <div class="header-titles">
          <h2>文件上传</h2>
          <p>支持小文件直接上传和大文件分片上传</p>
        </div>
      </div>

      <a-tabs v-model:activeKey="activeTab" class="upload-tabs">
        <!-- 简单上传 -->
        <a-tab-pane key="simple" tab="普通上传">
          <div class="upload-area">
            <a-upload-dragger
              v-model:fileList="simpleFileList"
              :customRequest="handleSimpleUpload"
              :showUploadList="true"
              multiple
              class="drag-upload"
            >
              <p class="ant-upload-drag-icon">
                <InboxOutlined />
              </p>
              <p class="ant-upload-text">点击或拖拽文件到此区域上传</p>
              <p class="ant-upload-hint">支持单个或批量上传，文件大小不超过 10MB</p>
            </a-upload-dragger>
          </div>
        </a-tab-pane>

        <!-- 分片上传 -->
        <a-tab-pane key="chunk" tab="大文件分片上传">
          <div class="chunk-upload-area">
            <a-upload
              v-model:fileList="chunkFileList"
              :customRequest="handleChunkUpload"
              :showUploadList="true"
              :beforeUpload="beforeChunkUpload"
              class="chunk-upload"
            >
              <a-button type="primary" :loading="isChecking">
                <UploadOutlined /> 选择大文件上传
              </a-button>
            </a-upload>

            <div v-if="currentUpload.fileName" class="upload-progress">
              <div class="progress-info">
                <span class="file-name">{{ currentUpload.fileName }}</span>
                <span class="file-size">{{ formatFileSize(currentUpload.fileSize) }}</span>
              </div>
              <a-progress
                :percent="currentUpload.percent"
                :status="currentUpload.status"
                :strokeColor="{ '0%': '#108ee9', '100%': '#87d068' }"
              />
              <div class="progress-detail">
                <span>{{ currentUpload.chunkIndex }} / {{ currentUpload.totalChunks }} 分片</span>
                <span v-if="currentUpload.isFastUpload" class="fast-tag">秒传</span>
              </div>
            </div>
          </div>
        </a-tab-pane>
      </a-tabs>

      <!-- 上传结果 -->
      <div v-if="uploadResults.length > 0" class="upload-results">
        <h3>上传结果</h3>
        <a-list :data-source="uploadResults" bordered>
          <template #renderItem="{ item }">
            <a-list-item>
              <div class="result-item">
                <div class="result-info">
                  <span class="result-name">{{ item.fileName }}</span>
                  <span class="result-size">{{ formatFileSize(item.fileSize) }}</span>
                </div>
                <div class="result-id">
                  <span>File ID: {{ item.fileId }}</span>
                  <a-tag v-if="item.existed" color="green">秒传</a-tag>
                </div>
              </div>
            </a-list-item>
          </template>
        </a-list>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { message } from 'ant-design-vue'
import { CloudUploadOutlined, InboxOutlined, UploadOutlined } from '@ant-design/icons-vue'
import type { UploadProps } from 'ant-design-vue'
import { uploadSimple, checkChunk, uploadChunk, mergeChunks, calculateFileMd5, type FileUploadResponse } from '@/api/file/fileupload.ts'

// 加载 SparkMD5 库（需要在 index.html 中引入）
// <script src="https://cdn.jsdelivr.net/npm/spark-md5@3.0.2/spark-md5.min.js"><\/script>

const activeTab = ref('simple')
const simpleFileList = ref<any[]>([])
const chunkFileList = ref<any[]>([])
const isChecking = ref(false)
const uploadResults = ref<FileUploadResponse[]>([])

// 当前上传状态
const currentUpload = reactive({
  fileName: '',
  fileSize: 0,
  fileMd5: '',
  percent: 0,
  chunkIndex: 0,
  totalChunks: 0,
  status: 'normal' as 'normal' | 'success' | 'exception' | 'active',
  isFastUpload: false
})

// 分片大小：5MB
const CHUNK_SIZE = 5 * 1024 * 1024

// 普通上传
const handleSimpleUpload = async ({ file, onSuccess, onError, onProgress }: any) => {
  try {
    const res = await uploadSimple(file, undefined, (percent: number) => {
      onProgress({ percent })
    })

    if (res.code === 200 && res.data) {
      uploadResults.value.unshift(res.data)
      onSuccess?.(res.data)
      message.success(res.data.existed ? '文件秒传成功' : '文件上传成功')
    } else {
      throw new Error(res.msg)
    }
  } catch (error: any) {
    message.error('上传失败：' + (error.message || '未知错误'))
    onError?.(error)
  }
}

// 大文件上传前的检查
const beforeChunkUpload: UploadProps['beforeUpload'] = (file) => {
  // 检查文件大小，大于 10MB 才使用分片上传
  if (file.size <= 10 * 1024 * 1024) {
    message.info('文件较小，建议使用普通上传')
    return false
  }
  return true
}

// 分片上传
const handleChunkUpload = async ({ file, onSuccess, onError }: any) => {
  isChecking.value = true
  currentUpload.status = 'active'
  currentUpload.fileName = file.name
  currentUpload.fileSize = file.size
  currentUpload.percent = 0
  currentUpload.chunkIndex = 0
  currentUpload.isFastUpload = false

  try {
    // 1. 计算文件 MD5
    message.loading('正在计算文件校验值...', 0)
    // const fileMd5 = await calculateFileMd5(file)
    const fileMd5 = await mockCalculateMd5(file) // 模拟 MD5 计算
    currentUpload.fileMd5 = fileMd5
    message.destroy()

    // 2. 检查分片上传状态
    const checkRes = await checkChunk(fileMd5, file.size, file.name)
    if (checkRes.code !== 200) {
      throw new Error(checkRes.msg)
    }

    const checkData = checkRes.data

    // 3. 秒传检查
    if (!checkData?.needUpload && checkData?.fileId) {
      currentUpload.isFastUpload = true
      currentUpload.status = 'success'
      currentUpload.percent = 100
      uploadResults.value.unshift({
        fileId: checkData.fileId,
        fileName: file.name,
        fileSize: file.size,
        fileMd5: fileMd5,
        existed: true
      })
      message.success('文件秒传成功')
      onSuccess?.({ fileId: checkData.fileId })
      return
    }

    // 4. 计算总分片数
    const totalChunks = Math.ceil(file.size / CHUNK_SIZE)
    currentUpload.totalChunks = totalChunks

    // 5. 获取已上传的分片
    const uploadedChunks = new Set(checkData?.uploadedChunks || [])
    const failedChunks: number[] = []

    // 6. 上传分片（并发控制：最多同时上传3个分片）
    const CONCURRENT_LIMIT = 3
    const chunksToUpload: number[] = []

    for (let i = 0; i < totalChunks; i++) {
      if (!uploadedChunks.has(i)) {
        chunksToUpload.push(i)
      }
    }

    let completedCount = uploadedChunks.size

    // 批量上传分片
    for (let i = 0; i < chunksToUpload.length; i += CONCURRENT_LIMIT) {
      const batch = chunksToUpload.slice(i, i + CONCURRENT_LIMIT)

      const uploadPromises = batch.map(async (chunkIndex) => {
        const start = chunkIndex * CHUNK_SIZE
        const end = Math.min(start + CHUNK_SIZE, file.size)
        const chunk = file.slice(start, end)

        const chunkRequest = {
          fileMd5: fileMd5,
          fileName: file.name,
          fileSize: file.size,
          fileType: getFileExtension(file.name),
          chunkNumber: chunkIndex,
          totalChunks: totalChunks,
          chunkSize: chunk.size
        }

        try {
          await uploadChunk(chunk, chunkRequest)
          completedCount++
          currentUpload.chunkIndex = completedCount
          currentUpload.percent = Math.round((completedCount / totalChunks) * 100)
        } catch (error) {
          console.error(`分片 ${chunkIndex} 上传失败`, error)
          failedChunks.push(chunkIndex)
        }
      })

      await Promise.all(uploadPromises)
    }

    // 7. 重试失败的分片
    if (failedChunks.length > 0) {
      message.warning(`${failedChunks.length} 个分片上传失败，正在重试...`)
      for (const chunkIndex of failedChunks) {
        const start = chunkIndex * CHUNK_SIZE
        const end = Math.min(start + CHUNK_SIZE, file.size)
        const chunk = file.slice(start, end)

        const chunkRequest = {
          fileMd5: fileMd5,
          fileName: file.name,
          fileSize: file.size,
          fileType: getFileExtension(file.name),
          chunkNumber: chunkIndex,
          totalChunks: totalChunks,
          chunkSize: chunk.size
        }

        await uploadChunk(chunk, chunkRequest)
        completedCount++
        currentUpload.percent = Math.round((completedCount / totalChunks) * 100)
      }
    }

    // 7. 检查是否所有分片都已上传
    if (completedCount < totalChunks) {
      throw new Error(`上传不完整：仅完成 ${completedCount}/${totalChunks} 个分片`)
    }

    // 8. 合并分片
    message.loading('正在合并文件...', 0)
    const mergeRes = await mergeChunks({
      fileMd5: fileMd5,
      fileName: file.name,
      fileSize: file.size,
      fileType: getFileExtension(file.name),
      totalChunks: totalChunks
    })
    message.destroy()

    if (mergeRes.code === 200 && mergeRes.data) {
      currentUpload.status = 'success'
      uploadResults.value.unshift(mergeRes.data)
      message.success('文件上传成功')
      onSuccess?.(mergeRes.data)
    } else {
      throw new Error(mergeRes.msg)
    }

  } catch (error: any) {
    currentUpload.status = 'exception'
    message.error('上传失败：' + (error.message || '未知错误'))
    onError?.(error)
  } finally {
    isChecking.value = false
  }
}

// 模拟计算 MD5（实际项目使用 SparkMD5）
const mockCalculateMd5 = (file: File): Promise<string> => {
  return new Promise((resolve) => {
    setTimeout(() => {
      // 使用文件名 + 大小 + 时间戳生成伪 MD5
      const fakeMd5 = btoa(file.name + file.size + Date.now())
        .replace(/[^a-zA-Z0-9]/g, '')
        .substring(0, 32)
        .toLowerCase()
      resolve(fakeMd5)
    }, 500)
  })
}

// 格式化文件大小
const formatFileSize = (size?: number) => {
  if (!size) return '0 B'
  const units = ['B', 'KB', 'MB', 'GB', 'TB']
  let index = 0
  let fileSize = size
  while (fileSize >= 1024 && index < units.length - 1) {
    fileSize /= 1024
    index++
  }
  return `${fileSize.toFixed(2)} ${units[index]}`
}

// 获取文件扩展名
const getFileExtension = (filename: string) => {
  const lastDot = filename.lastIndexOf('.')
  return lastDot === -1 ? '' : filename.substring(lastDot + 1).toLowerCase()
}
</script>

<style scoped>
.fileupload-container {
  padding: 24px;
}

.upload-card {
  background: #fff;
  border-radius: 12px;
  padding: 32px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}

.upload-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
  padding-bottom: 24px;
  border-bottom: 1px solid #f0f0f0;
}

.header-icon {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: white;
}

.header-titles h2 {
  margin: 0;
  font-size: 20px;
  font-weight: 600;
  color: #1f2937;
}

.header-titles p {
  margin: 4px 0 0 0;
  font-size: 14px;
  color: #6b7280;
}

.upload-tabs :deep(.ant-tabs-nav) {
  margin-bottom: 24px;
}

.upload-area {
  padding: 24px;
}

.drag-upload :deep(.ant-upload) {
  padding: 48px 24px;
}

.chunk-upload-area {
  padding: 24px;
}

.chunk-upload {
  margin-bottom: 24px;
}

.upload-progress {
  background: #f9fafb;
  border-radius: 8px;
  padding: 16px;
  margin-top: 16px;
}

.progress-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
}

.file-name {
  font-weight: 500;
  color: #1f2937;
}

.file-size {
  color: #6b7280;
  font-size: 14px;
}

.progress-detail {
  display: flex;
  justify-content: space-between;
  margin-top: 8px;
  font-size: 14px;
  color: #6b7280;
}

.fast-tag {
  background: #10b981;
  color: white;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.upload-results {
  margin-top: 32px;
  padding-top: 24px;
  border-top: 1px solid #f0f0f0;
}

.upload-results h3 {
  margin: 0 0 16px 0;
  font-size: 16px;
  font-weight: 600;
  color: #1f2937;
}

.result-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.result-info {
  display: flex;
  gap: 16px;
  align-items: center;
}

.result-name {
  font-weight: 500;
}

.result-size {
  color: #6b7280;
  font-size: 14px;
}

.result-id {
  display: flex;
  gap: 8px;
  align-items: center;
  font-size: 14px;
  color: #6b7280;
}

:global(html.dark) .upload-card {
  background: #1f2937;
}

:global(html.dark) .header-titles h2 {
  color: #f9fafb;
}

:global(html.dark) .upload-results h3 {
  color: #f9fafb;
}

:global(html.dark) .upload-progress {
  background: #374151;
}

:global(html.dark) .file-name,
:global(html.dark) .result-name {
  color: #f9fafb;
}
</style>
