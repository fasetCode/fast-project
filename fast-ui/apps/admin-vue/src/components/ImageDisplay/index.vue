<template>
  <div class="image-display" :class="{ 'is-circle': shape === 'circle' }">
    <img
      v-if="imageUrl"
      :src="imageUrl"
      :alt="alt"
      :style="imageStyle"
      @error="handleError"
      @click="$emit('click')"
    />
    <div v-else class="image-placeholder" :style="imageStyle">
      <slot name="placeholder">
        <PictureOutlined />
      </slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch, onMounted } from 'vue'
import { PictureOutlined } from '@ant-design/icons-vue'
import { getFileUrl } from '@/api/file/fileupload'

interface Props {
  value?: string | number
  valueType?: 'url' | 'id'
  width?: number
  height?: number
  shape?: 'square' | 'circle'
  alt?: string
}

const props = withDefaults(defineProps<Props>(), {
  value: undefined,
  valueType: 'id',
  width: 48,
  height: 48,
  shape: 'square',
  alt: 'image'
})

defineEmits(['click'])

const imageUrl = ref('')
const hasError = ref(false)

const imageStyle = computed(() => ({
  width: `${props.width}px`,
  height: `${props.height}px`,
  objectFit: 'cover' as const
}))

const resolveUrl = () => {
  if (!props.value) {
    imageUrl.value = ''
    return
  }

  const valueStr = String(props.value)
  const isUrl = valueStr.startsWith('http') || valueStr.startsWith('/') || valueStr.includes('/')

  if (isUrl) {
    imageUrl.value = valueStr
  } else if (props.valueType === 'id') {
    // 构造重定向URL，让浏览器自动跟随302跳转
    imageUrl.value = getFileUrl(props.value)
  }
}

const handleError = () => {
  hasError.value = true
  imageUrl.value = ''
}

// 监听值变化
watch(() => props.value, () => {
  hasError.value = false
  resolveUrl()
}, { immediate: false })

onMounted(() => {
  resolveUrl()
})
</script>

<style scoped>
.image-display {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  border-radius: 4px;
  background-color: #f5f5f5;
  border: 1px solid #e8e8e8;
}

.image-display.is-circle {
  border-radius: 50%;
}

.image-display img {
  display: block;
  border-radius: inherit;
}

.image-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #bfbfbf;
  font-size: 20px;
}

:global(html.dark) .image-display {
  background-color: #1f1f1f;
  border-color: #303030;
}

:global(html.dark) .image-placeholder {
  color: #595959;
}
</style>
