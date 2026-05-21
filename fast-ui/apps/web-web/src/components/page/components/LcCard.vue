<script setup lang="ts">
withDefaults(defineProps<{
  title?:        string
  bgColor?:      string
  borderRadius?: number
  bordered?:     boolean
  shadow?:       boolean
  padding?:      number
}>(), {
  title:        '',
  bgColor:      '#fff',
  borderRadius: 8,
  bordered:     true,
  shadow:       false,
  padding:      16,
})
</script>

<template>
  <div
    class="lc-card"
    :style="{
      backgroundColor: bgColor,
      borderRadius:    `${borderRadius}px`,
      border:          bordered ? '1px solid #e8e8e8' : 'none',
      boxShadow:       shadow   ? '0 2px 12px rgba(0,0,0,0.1)' : 'none',
      overflow:        'hidden',
    }"
  >
    <div v-if="title" class="lc-card-header">{{ title }}</div>
    <div class="lc-card-body" :style="{ padding: `${padding}px` }">
      <slot />
    </div>
  </div>
</template>

<style scoped>
.lc-card {
  overflow: hidden;
}

.lc-card-header {
  padding: 10px 16px;
  font-size: 14px;
  font-weight: 600;
  color: #262626;
  border-bottom: 1px solid #f0f0f0;
}

.lc-card-body {
  min-height: 0;
}
</style>
