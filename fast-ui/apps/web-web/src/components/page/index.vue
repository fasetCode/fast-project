<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { getPageConfigByPath } from '@/api/pageconfig'
import CanvasNode from '@/components/page/components/CanvasNode.vue'

const props = defineProps<{
  path?:  string
  code?:  string
}>()

interface PageConfig {
  nodes: any[]
}

const pageConfig = ref<PageConfig | null>(null)
const loading    = ref(false)
const error      = ref<string | null>(null)

const fetchPageConfig = async () => {
  if (!props.path || !props.code) return
  loading.value = true
  error.value   = null
  try {
    const res = await getPageConfigByPath(props.path, props.code)
    if (res.code === 200) {
      const raw = res.data
      pageConfig.value = typeof raw === 'string' ? JSON.parse(raw) : raw
    } else {
      error.value = res.msg ?? '加载失败'
    }
  } catch (e: any) {
    error.value = e?.message ?? '请求异常'
    console.error('获取页面配置失败:', e)
  } finally {
    loading.value = false
  }
}

onMounted(fetchPageConfig)
watch(() => [props.path, props.code], fetchPageConfig)
</script>

<template>
  <div class="lc-page-renderer">
    <slot v-if="loading" name="loading">
      <div class="lc-page-loading">加载中...</div>
    </slot>
    <slot v-else-if="error" name="error" :error="error">
      <div class="lc-page-error">{{ error }}</div>
    </slot>
    <template v-else-if="pageConfig?.nodes?.length">
      <CanvasNode
        v-for="node in pageConfig.nodes"
        :key="node.id"
        :item="node"
      />
    </template>
  </div>
</template>

<style scoped>
.lc-page-renderer {
  width: 100%;
  height: 100vh;
}

.lc-page-loading,
.lc-page-error {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 120px;
  font-size: 14px;
  color: #999;
}

.lc-page-error {
  color: #ff4d4f;
}
</style>