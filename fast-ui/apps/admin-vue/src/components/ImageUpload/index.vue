<template>
  <div class="image-upload">
    <a-upload
      v-model:file-list="fileList"
      list-type="picture-card"
      :custom-request="handleCustomRequest"
      :multiple="limit > 1"
      :max-count="limit"
      :disabled="disabled"
      @preview="handlePreview"
      @remove="handleRemove"
    >
      <div v-if="fileList.length < limit">
        <plus-outlined />
        <div style="margin-top: 8px">上传图片</div>
      </div>
    </a-upload>

    <a-modal :open="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import type { UploadProps, UploadFile } from 'ant-design-vue'
import { uploadSimple, getFileUrl } from '@/api/file/fileupload'
import { message } from 'ant-design-vue'

interface Props {
  modelValue?: string | string[] | number | number[]
  valueType?: 'url' | 'id'
  limit?: number
  disabled?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  valueType: 'url',
  limit: 1,
  disabled: false
})

const emit = defineEmits(['update:modelValue', 'change'])

const fileList = ref<UploadFile[]>([])
const previewVisible = ref(false)
const previewImage = ref('')
const previewTitle = ref('')

const toIdString = (value: any): string => {
  if (value === null || value === undefined) return ''
  if (typeof value === 'string') return value
  if (typeof value === 'number' || typeof value === 'bigint') return String(value)
  if (typeof value === 'object' && typeof value.toString === 'function') {
    const s = value.toString()
    if (s && s !== '[object Object]') return s
  }
  return String(value)
}

// 同步初始值
const syncValueToFileList = () => {
  if (!props.modelValue) {
    fileList.value = []
    return
  }

  const values = Array.isArray(props.modelValue) ? props.modelValue : [props.modelValue]
  
  fileList.value = values.map((val, index) => {
    const valueStr = props.valueType === 'id' ? toIdString(val) : String(val)
    // 判断是否是 URL 地址
    const isUrl = valueStr.startsWith('http') || valueStr.startsWith('/') || valueStr.includes('/')
    
    // 如果是 ID 类型且非 URL，构造 /api/file/getUrl/{id} 让浏览器跟随 302 重定向渲染图片
    const url = (!isUrl && props.valueType === 'id') ? getFileUrl(val) : (isUrl ? valueStr : '')
    
    return {
      uid: `-${index}`,
      name: valueStr.substring(valueStr.lastIndexOf('/') + 1) || 'image.png',
      status: 'done',
      url: url,
      response: { 
        data: isUrl ? { accessPath: valueStr } : { fileId: val } 
      }
    } as UploadFile
  })
}

onMounted(() => {
  syncValueToFileList()
})

// 监听外部值变化
watch(() => props.modelValue, () => {
  // 避免循环更新
  const currentValues = fileList.value
    .filter(f => f.status === 'done')
    .map(f => {
      if (props.valueType === 'id') {
        return toIdString(f.response?.data?.fileId || f.uid)
      }
      return f.url || f.response?.data?.accessPath
    })
  
  const propValues = Array.isArray(props.modelValue) ? props.modelValue : [props.modelValue].filter(Boolean)
  
  if (JSON.stringify(currentValues) !== JSON.stringify(propValues)) {
    syncValueToFileList()
  }
}, { deep: true })

const handleCancel = () => {
  previewVisible.value = false
  previewTitle.value = ''
}

const handlePreview = async (file: UploadFile) => {
  if (!file.url && !file.preview) {
    file.preview = (await getBase64(file.originFileObj as Blob)) as string
  }
  previewImage.value = file.url || (file.preview as string)
  previewVisible.value = true
  previewTitle.value = file.name || file.url!.substring(file.url!.lastIndexOf('/') + 1)
}

const getBase64 = (file: Blob): Promise<string | ArrayBuffer | null> => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

const handleCustomRequest = async (options: any) => {
  const { file, onSuccess, onError, onProgress } = options
  try {
    const res = await uploadSimple(file as File, undefined, (percent) => {
      onProgress({ percent })
    })

    if (res.code === 200) {
      const data = res.data
      onSuccess(res, file)
      updateModelValue()
    } else {
      message.error(res.msg || '上传失败')
      onError(new Error(res.msg))
    }
  } catch (error: any) {
    message.error(error.message || '上传接口异常')
    onError(error)
  }
}

const handleRemove = () => {
  // a-upload 的 v-model:file-list 会自动处理 list 的移除
  // 我们只需要在移除后更新 modelValue
  setTimeout(() => {
    updateModelValue()
  }, 0)
}

const updateModelValue = () => {
  const values = fileList.value
    .filter(f => f.status === 'done')
    .map(f => {
      const resData = f.response?.data
      if (props.valueType === 'id') {
        return toIdString(resData?.fileId || f.url)
      }
      return resData?.accessPath || f.url
    })
    .filter(Boolean)

  const finalValue = props.limit === 1 ? (values[0] || '') : values
  emit('update:modelValue', finalValue)
  emit('change', finalValue)
}
</script>

<style scoped>
.image-upload {
  display: inline-block;
}

/* 限制图片卡片大小 */
:deep(.ant-upload-select-picture-card),
:deep(.ant-upload-list-picture-card-item) {
  width: 100px !important;
  height: 100px !important;
}
</style>
