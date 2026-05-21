<script setup lang="ts">
import type { CanvasNode } from '../../../components/types'
import CarouselForm from './public/Carousel.vue'
import TopInformationBarForm from './mall/TopInformationBar.vue'

defineProps<{
  node: CanvasNode | null
  modelValue: Record<string, any>
}>()

defineEmits<{
  (e: 'update:modelValue', v: Record<string, any>): void
}>()
</script>

<template>
  <template v-if="node?.type === 'carousel'">
    <CarouselForm
      :model-value="modelValue"
      @update:model-value="v => $emit('update:modelValue', v)"
    />
  </template>
  <template v-else-if="node?.type === 'mall_top_information_bar'">
    <TopInformationBarForm
      :model-value="modelValue"
      @update:model-value="v => $emit('update:modelValue', v)"
    />
  </template>
</template>

<style scoped>
</style>
