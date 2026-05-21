<script setup lang="ts">
import { computed } from 'vue'
import { getFileUrl } from '@/api/file/fileupload'

interface Slide {
  src?: string | number
  alt?: string
  link?: string
  /** Optional per-slide overrides */
  height?: number
  objectFit?: 'cover' | 'contain' | 'fill' | 'none' | 'scale-down'
  bgColor?: string
}

interface CarouselShowProps {
  autoplay?: boolean
  autoplaySpeed?: number
  dots?: boolean
  dotPosition?: 'top' | 'bottom' | 'left' | 'right'
  effect?: 'scrollx' | 'fade'
  arrows?: boolean
  /** Global height (px) - used when slide has no override */
  height?: number
  /** Global object-fit - used when slide has no override */
  objectFit?: 'cover' | 'contain' | 'fill' | 'none' | 'scale-down'
  /** Global slide background color */
  bgColor?: string
  slides?: Slide[]
}

const props = withDefaults(defineProps<{ config: CarouselShowProps }>(), {
  config: () => ({}),
})

const cfg = computed(() => ({
  autoplay:      props.config.autoplay !== false,
  autoplaySpeed: Number(props.config.autoplaySpeed) || 3000,
  dots:          props.config.dots !== false,
  dotPosition:   props.config.dotPosition || 'bottom',
  effect:        props.config.effect || 'scrollx',
  arrows:        !!props.config.arrows,
  height:        Number(props.config.height) || 200,
  objectFit:     props.config.objectFit || 'cover',
  bgColor:       props.config.bgColor || '#f5f5f5',
  slides:        Array.isArray(props.config.slides) ? props.config.slides : [],
}))

// Resolve image src: numeric/short ID -> getFileUrl, otherwise return as-is
const resolveImageSrc = (src: any): string => {
  if (!src) return ''
  const s = String(src)
  if (s.startsWith('http') || s.startsWith('/') || s.includes('/')) return s
  return getFileUrl(src)
}

const slideStyle = (slide: Slide) => ({
  height: `${slide.height || cfg.value.height}px`,
  backgroundColor: slide.bgColor || cfg.value.bgColor,
})

const imgStyle = (slide: Slide) => ({
  width: '100%',
  height: `${slide.height || cfg.value.height}px`,
  objectFit: slide.objectFit || cfg.value.objectFit,
  display: 'block',
})
</script>

<template>
  <div class="ant-carousel-wrap">
    <a-carousel
      v-if="cfg.slides.length > 0"
      :autoplay="cfg.autoplay"
      :autoplay-speed="cfg.autoplaySpeed"
      :dots="cfg.dots"
      :dot-position="cfg.dotPosition"
      :effect="cfg.effect"
      :arrows="cfg.arrows"
    >
      <div
        v-for="(slide, si) in cfg.slides"
        :key="si"
        class="ant-carousel-slide"
        :style="slideStyle(slide)"
      >
        <img
          v-if="slide && slide.src"
          :src="resolveImageSrc(slide.src)"
          :alt="slide.alt"
          :style="imgStyle(slide)"
          class="ant-carousel-img"
        />
        <div v-else class="ant-carousel-placeholder">
          <span style="font-size:32px;opacity:.4">🖼</span>
          <span>未设置图片</span>
        </div>
      </div>
    </a-carousel>

    <div
      v-else
      class="ant-carousel-empty"
      :style="{ height: `${cfg.height}px` }"
    >
      <span style="font-size:32px;opacity:.4">🎠</span>
      <span>请在右侧添加轮播项</span>
    </div>
  </div>
</template>

<style scoped>
.ant-carousel-wrap { width: 100%; overflow: hidden; }
.ant-carousel-wrap :deep(.slick-slide) { text-align: center; overflow: hidden; }

.ant-carousel-slide {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.ant-carousel-img {
  display: block;
}

.ant-carousel-placeholder,
.ant-carousel-empty {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
  color: #aaa;
  font-size: 12px;
  background: repeating-linear-gradient(45deg, #fafafa, #fafafa 6px, #f5f5f5 6px, #f5f5f5 12px);
  border: 1.5px dashed #e0e0e0;
  border-radius: 4px;
}
</style>
