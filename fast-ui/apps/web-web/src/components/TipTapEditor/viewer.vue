<script setup lang="ts">
import { computed } from 'vue'
import './style.css'

interface Props {
  content?: string
  modelValue?: string
  emptyText?: string
  showEmpty?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  content: '',
  modelValue: '',
  emptyText: '暂无内容',
  showEmpty: false,
})

const buildFileRedirectUrl = (id: string | number) => `${import.meta.env.VITE_API_BASE_URL}/file/getUrl/${id}`

const sourceContent = computed(() => props.content || props.modelValue || '')

const normalizedContent = computed(() => {
  const html = sourceContent.value
  if (!html) return ''
  if (typeof window === 'undefined' || typeof DOMParser === 'undefined') return html

  try {
    const parser = new DOMParser()
    const doc = parser.parseFromString(html, 'text/html')

    const imgs = doc.querySelectorAll('img')
    imgs.forEach(img => {
      const imgId = img.getAttribute('data-img-id')
      if (imgId) {
        img.setAttribute('src', buildFileRedirectUrl(imgId))
      }

      const width = img.getAttribute('width')
      if (width) {
        img.style.width = width
        img.style.maxWidth = '100%'
        img.style.height = 'auto'
      }

      const align = img.getAttribute('data-align') || 'left'
      img.style.display = 'block'
      if (align === 'center') {
        img.style.margin = '8px auto'
      } else if (align === 'right') {
        img.style.margin = '8px 0 8px auto'
      } else {
        img.style.margin = '8px auto 8px 0'
      }
    })

    return doc.body.innerHTML
  } catch {
    return html
  }
})
</script>

<template>
  <div class="tiptap-viewer">
    <div v-if="normalizedContent" class="rich-content" v-html="normalizedContent"></div>
    <div v-else-if="showEmpty" class="tiptap-viewer-empty">{{ emptyText }}</div>
  </div>
</template>

<style scoped>
.tiptap-viewer-empty {
  padding: 16px 0;
  color: #9ca3af;
  font-size: 14px;
}

html.dark .tiptap-viewer-empty {
  color: #94a3b8;
}
</style>
