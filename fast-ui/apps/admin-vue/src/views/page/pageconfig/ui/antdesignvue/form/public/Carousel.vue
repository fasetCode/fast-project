<script setup lang="ts">
import { computed } from 'vue'
import {
  PlusOutlined,
  DeleteOutlined,
  ArrowUpOutlined,
  ArrowDownOutlined,
} from '@ant-design/icons-vue'
import ImageUpload from '@/components/ImageUpload/index.vue'

interface Slide {
  src: string | number
  alt?: string
  link?: string
  /** Per-slide overrides (optional, fall back to global) */
  height?: number
  objectFit?: 'cover' | 'contain' | 'fill' | 'none' | 'scale-down'
  bgColor?: string
}

interface CarouselProps {
  autoplay?: boolean
  autoplaySpeed?: number
  dots?: boolean
  dotPosition?: 'top' | 'bottom' | 'left' | 'right'
  effect?: 'scrollx' | 'fade'
  arrows?: boolean
  height?: number
  objectFit?: 'cover' | 'contain' | 'fill' | 'none' | 'scale-down'
  bgColor?: string
  slides?: Slide[]
}

const props = withDefaults(defineProps<{ modelValue: CarouselProps }>(), {
  modelValue: () => ({}),
})

const emit = defineEmits<{
  (e: 'update:modelValue', v: CarouselProps): void
}>()

// Normalised view of current config with defaults
const cfg = computed(() => ({
  autoplay:      props.modelValue.autoplay ?? true,
  autoplaySpeed: props.modelValue.autoplaySpeed ?? 3000,
  dots:          props.modelValue.dots ?? true,
  dotPosition:   props.modelValue.dotPosition ?? 'bottom',
  effect:        props.modelValue.effect ?? 'scrollx',
  arrows:        props.modelValue.arrows ?? false,
  height:        props.modelValue.height ?? 200,
  objectFit:     props.modelValue.objectFit ?? 'cover',
  bgColor:       props.modelValue.bgColor ?? '#f5f5f5',
  slides:        Array.isArray(props.modelValue.slides) ? props.modelValue.slides : [],
}))

const set = (key: keyof CarouselProps, value: any) => {
  emit('update:modelValue', { ...props.modelValue, [key]: value })
}

const setSlides = (slides: Slide[]) => set('slides', slides)

const addSlide = () => {
  setSlides([...cfg.value.slides, { src: '', alt: '', link: '' }])
}

const removeSlide = (idx: number) => {
  const list = cfg.value.slides.slice()
  list.splice(idx, 1)
  setSlides(list)
}

const moveSlide = (idx: number, delta: number) => {
  const list = cfg.value.slides.slice()
  const target = idx + delta
  if (target < 0 || target >= list.length) return
  const [item] = list.splice(idx, 1)
  list.splice(target, 0, item)
  setSlides(list)
}

const updateSlide = (idx: number, key: keyof Slide, value: any) => {
  const list = cfg.value.slides.slice()
  list[idx] = { ...list[idx], [key]: value }
  setSlides(list)
}
</script>

<template>
  <div class="carousel-form">
    <!-- ── 基础设置 ──────────────────────────────────────── -->
    <div class="form-group-label">基础设置</div>

    <div class="form-row">
      <label class="form-label">自动播放</label>
      <a-switch
        :checked="cfg.autoplay"
        size="small"
        @change="(v: boolean) => set('autoplay', v)"
      />
    </div>

    <div v-if="cfg.autoplay" class="form-row">
      <label class="form-label">间隔(ms)</label>
      <a-input-number
        :value="cfg.autoplaySpeed"
        :min="500"
        :max="60000"
        :step="500"
        size="small"
        class="form-ctrl"
        @change="(v: number | string) => set('autoplaySpeed', v)"
      />
    </div>

    <div class="form-row">
      <label class="form-label">显示指示点</label>
      <a-switch
        :checked="cfg.dots"
        size="small"
        @change="(v: boolean) => set('dots', v)"
      />
    </div>

    <div v-if="cfg.dots" class="form-row">
      <label class="form-label">指示点位置</label>
      <a-select
        :value="cfg.dotPosition"
        size="small"
        class="form-ctrl"
        @change="(v: string) => set('dotPosition', v)"
      >
        <a-select-option value="top">上</a-select-option>
        <a-select-option value="bottom">下</a-select-option>
        <a-select-option value="left">左</a-select-option>
        <a-select-option value="right">右</a-select-option>
      </a-select>
    </div>

    <div class="form-row">
      <label class="form-label">显示箭头</label>
      <a-switch
        :checked="cfg.arrows"
        size="small"
        @change="(v: boolean) => set('arrows', v)"
      />
    </div>

    <div class="form-row">
      <label class="form-label">切换效果</label>
      <a-select
        :value="cfg.effect"
        size="small"
        class="form-ctrl"
        @change="(v: string) => set('effect', v)"
      >
        <a-select-option value="scrollx">滑动</a-select-option>
        <a-select-option value="fade">淡入淡出</a-select-option>
      </a-select>
    </div>

    <div class="form-row">
      <label class="form-label">高度(px)</label>
      <a-input-number
        :value="cfg.height"
        :min="60"
        :max="800"
        :step="10"
        size="small"
        class="form-ctrl"
        @change="(v: number | string) => set('height', v)"
      />
    </div>

    <div class="form-row">
      <label class="form-label">填充方式</label>
      <a-select
        :value="cfg.objectFit"
        size="small"
        class="form-ctrl"
        @change="(v: string) => set('objectFit', v)"
      >
        <a-select-option value="cover">覆盖 cover</a-select-option>
        <a-select-option value="contain">包含 contain</a-select-option>
        <a-select-option value="fill">拉伸 fill</a-select-option>
        <a-select-option value="none">原始 none</a-select-option>
        <a-select-option value="scale-down">缩小 scale-down</a-select-option>
      </a-select>
    </div>

    <div class="form-row">
      <label class="form-label">背景色</label>
      <div class="prop-color-row">
        <input
          type="color"
          class="prop-color-swatch"
          :value="cfg.bgColor"
          @input="(e: Event) => set('bgColor', (e.target as HTMLInputElement).value)"
        />
        <a-input
          :value="cfg.bgColor"
          size="small"
          class="form-ctrl"
          @change="(e: Event) => set('bgColor', (e.target as HTMLInputElement).value)"
        />
      </div>
    </div>

    <!-- ── 轮播项 ─────────────────────────────────────────── -->
    <div class="form-group-label slides-header">
      <span>轮播项 ({{ cfg.slides.length }})</span>
      <a-button size="small" type="link" @click="addSlide">
        <plus-outlined /> 添加
      </a-button>
    </div>

    <div v-if="cfg.slides.length === 0" class="form-empty">
      暂无轮播项，点击“添加”创建
    </div>

    <div
      v-for="(slide, idx) in cfg.slides"
      :key="idx"
      class="slide-card"
    >
      <div class="slide-head">
        <span class="slide-index">#{{ idx + 1 }}</span>
        <div class="slide-actions">
          <a-button
            size="small"
            type="text"
            :disabled="idx === 0"
            title="上移"
            @click="moveSlide(idx, -1)"
          >
            <arrow-up-outlined />
          </a-button>
          <a-button
            size="small"
            type="text"
            :disabled="idx === cfg.slides.length - 1"
            title="下移"
            @click="moveSlide(idx, 1)"
          >
            <arrow-down-outlined />
          </a-button>
          <a-button
            size="small"
            type="text"
            danger
            title="删除"
            @click="removeSlide(idx)"
          >
            <delete-outlined />
          </a-button>
        </div>
      </div>

      <div class="form-row align-top">
        <label class="form-label">图片</label>
        <div class="form-ctrl">
          <ImageUpload
            :model-value="slide.src || ''"
            value-type="id"
            :limit="1"
            @update:model-value="v => updateSlide(idx, 'src', v)"
          />
        </div>
      </div>

      <div class="form-row">
        <label class="form-label">描述</label>
        <a-input
          :value="slide.alt || ''"
          size="small"
          class="form-ctrl"
          placeholder="图片描述"
          @change="(e: Event) => updateSlide(idx, 'alt', (e.target as HTMLInputElement).value)"
        />
      </div>

      <div class="form-row">
        <label class="form-label">跳转链接</label>
        <a-input
          :value="slide.link || ''"
          size="small"
          class="form-ctrl"
          placeholder="https://..."
          @change="(e: Event) => updateSlide(idx, 'link', (e.target as HTMLInputElement).value)"
        />
      </div>

      <div class="form-row">
        <label class="form-label">高度(px)</label>
        <a-input-number
          :value="slide.height ?? null"
          :min="60"
          :max="800"
          :step="10"
          size="small"
          class="form-ctrl"
          :placeholder="`默认 ${cfg.height}px`"
          @change="(v: number | string | null) => updateSlide(idx, 'height', v === null || v === '' ? undefined : Number(v))"
        />
      </div>

      <div class="form-row">
        <label class="form-label">填充方式</label>
        <a-select
          :value="slide.objectFit ?? ''"
          size="small"
          class="form-ctrl"
          allow-clear
          :placeholder="`默认 ${cfg.objectFit}`"
          @change="(v: string) => updateSlide(idx, 'objectFit', v || undefined)"
        >
          <a-select-option value="cover">覆盖 cover</a-select-option>
          <a-select-option value="contain">包含 contain</a-select-option>
          <a-select-option value="fill">拉伸 fill</a-select-option>
          <a-select-option value="none">原始 none</a-select-option>
          <a-select-option value="scale-down">缩小 scale-down</a-select-option>
        </a-select>
      </div>

      <div class="form-row">
        <label class="form-label">背景色</label>
        <div class="prop-color-row">
          <input
            type="color"
            class="prop-color-swatch"
            :value="slide.bgColor || cfg.bgColor"
            @input="(e: Event) => updateSlide(idx, 'bgColor', (e.target as HTMLInputElement).value)"
          />
          <a-input
            :value="slide.bgColor || ''"
            size="small"
            class="form-ctrl"
            :placeholder="`默认 ${cfg.bgColor}`"
            @change="(e: Event) => updateSlide(idx, 'bgColor', (e.target as HTMLInputElement).value || undefined)"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.carousel-form {
  display: flex;
  flex-direction: column;
}

.form-group-label {
  font-size: 11px;
  font-weight: 600;
  color: #8c8c8c;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  margin: 4px 0 10px;
}

.slides-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 12px;
}

.form-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}
.form-row.align-top { align-items: flex-start; }
.form-row:last-child { margin-bottom: 0; }

.form-label {
  width: 72px;
  flex-shrink: 0;
  font-size: 12px;
  color: #555;
}

.form-ctrl { flex: 1; min-width: 0; }

.form-empty {
  font-size: 11px;
  color: #bfbfbf;
  text-align: center;
  padding: 12px 0;
  border: 1px dashed #f0f0f0;
  border-radius: 4px;
}

.slide-card {
  border: 1px solid #f0f0f0;
  border-radius: 4px;
  padding: 8px;
  margin-bottom: 10px;
  background: #fafafa;
}

.slide-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
}
.slide-index {
  font-size: 11px;
  font-weight: 600;
  color: #1677ff;
}
.slide-actions {
  display: flex;
  gap: 0;
}

/* Color picker row, mirrors PageNodeEditor styling */
.prop-color-row {
  display: flex;
  align-items: center;
  gap: 5px;
  flex: 1;
}
.prop-color-swatch {
  width: 28px;
  height: 28px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  padding: 1px;
  cursor: pointer;
  flex-shrink: 0;
}
</style>
