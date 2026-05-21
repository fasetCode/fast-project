# ImageUpload 图片上传组件

基于 Ant Design Vue 封装的图片上传组件，支持单图/多图上传、图片预览、拖拽上传等功能。

## 基础用法

```vue
<template>
  <!-- 单图上传 -->
  <ImageUpload v-model:value="imageUrl" :multiple="false" />

  <!-- 多图上传 -->
  <ImageUpload v-model:value="imageList" :multiple="true" :maxCount="9" />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ImageUpload } from '@/components'

const imageUrl = ref([])
const imageList = ref([])
</script>
```

## Props 参数

| 参数 | 说明 | 类型 | 默认值 |
|------|------|------|--------|
| value | 已上传图片列表 | `ImageUploadFile[]` | `[]` |
| multiple | 是否支持多选 | `boolean` | `false` |
| maxCount | 最大上传数量 | `number` | `9` |
| maxSize | 单文件大小限制（MB） | `number` | `10` |
| accept | 允许的图片格式 | `string[]` | `['jpg', 'jpeg', 'png', 'gif', 'webp']` |
| size | 组件尺寸（px） | `number` | `120` |
| disabled | 是否禁用 | `boolean` | `false` |
| showPreview | 是否显示预览区域 | `boolean` | `true` |
| showDeleteIcon | 是否显示删除按钮 | `boolean` | `true` |
| showPreviewIcon | 是否显示预览按钮 | `boolean` | `true` |
| uploadText | 上传按钮文字 | `string` | `'上传图片'` |
| showHint | 是否显示提示文字 | `boolean` | `true` |
| hintText | 自定义提示文字 | `string` | `''` |
| compress | 是否压缩图片 | `boolean` | `false` |
| compressQuality | 压缩质量（0-1） | `number` | `0.8` |
| compressMaxWidth | 压缩后最大宽度 | `number` | `1920` |
| compressMaxHeight | 压缩后最大高度 | `number` | `1080` |

## Events 事件

| 事件名 | 说明 | 回调参数 |
|--------|------|----------|
| update:value | 图片列表变化时触发 | `(value: ImageUploadFile[]) => void` |
| change | 图片列表变化时触发 | `(value: ImageUploadFile[]) => void` |
| success | 单张图片上传成功时触发 | `(response: FileUploadResponse, file: ImageUploadFile) => void` |
| error | 单张图片上传失败时触发 | `(error: Error, file: ImageUploadFile) => void` |
| remove | 删除图片时触发 | `(file: ImageUploadFile, index: number) => void` |

## ImageUploadFile 类型

```typescript
interface ImageUploadFile {
  uid: string          // 唯一标识
  name: string         // 文件名
  status: 'uploading' | 'done' | 'error' | 'removed'  // 上传状态
  url?: string         // 图片访问地址
  percent?: number     // 上传进度
  response?: { data: FileUploadResponse }  // 服务器响应
  raw?: File           // 原始文件对象
}
```

## 高级用法

### 带图片压缩

```vue
<template>
  <ImageUpload
    v-model:value="imageList"
    :multiple="true"
    :compress="true"
    :compressQuality="0.8"
    :compressMaxWidth="1920"
    :compressMaxHeight="1080"
  />
</template>
```

### 自定义尺寸和格式限制

```vue
<template>
  <ImageUpload
    v-model:value="imageList"
    :size="80"
    :accept="['png', 'jpg']"
    :maxSize="5"
    :maxCount="5"
    uploadText="点击上传"
  />
</template>
```

### 监听事件

```vue
<template>
  <ImageUpload
    v-model:value="imageList"
    @success="handleSuccess"
    @error="handleError"
    @remove="handleRemove"
  />
</template>

<script setup lang="ts">
const handleSuccess = (response, file) => {
  console.log('上传成功:', response)
}

const handleError = (error, file) => {
  console.error('上传失败:', error)
}

const handleRemove = (file, index) => {
  console.log('删除图片:', file.name, '索引:', index)
}
</script>
```

### 表单中使用

```vue
<template>
  <a-form :model="formState">
    <a-form-item label="商品图片" name="images">
      <ImageUpload
        v-model:value="formState.images"
        :multiple="true"
        :maxCount="5"
      />
    </a-form-item>
  </a-form>
</template>

<script setup lang="ts">
import { reactive } from 'vue'

const formState = reactive({
  images: []
})
</script>
```

## 注意事项

1. 组件使用 `/file/upload/simple` 接口进行上传，确保后端接口正常
2. 图片上传后会返回 `accessPath` 作为访问地址
3. 组件支持暗黑模式，会自动适配 `html.dark` 类名
