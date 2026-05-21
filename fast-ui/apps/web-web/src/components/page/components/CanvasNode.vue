<script lang="ts">
export default { name: 'CanvasNode' }
</script>

<script setup lang="ts">
import { computed } from 'vue'
import LcButton from './LcButton.vue'
import LcCard   from './LcCard.vue'
import TipTapViewer from '../../TipTapEditor/viewer.vue'
import AndCom from '../../page/ant/index.vue'

const props = defineProps<{ item: any }>()

const children = computed(() => props.item.children ?? [])

// lc-div 容器样式计算（包含吸附固定 + 防止塌陷）
const divStyle = computed(() => {
  const p = props.item.props || {}
  const isFlex = p.display === 'flex'
  const isBlock = !p.display || p.display === 'block'

  // 基础布局
  const style: Record<string, string | undefined> = {
    display:         p.display,
    flexDirection:   isFlex ? p.flexDirection                                  : undefined,
    flexWrap:        isFlex ? (p.flexWrap ? 'wrap' : 'nowrap')                 : undefined,
    alignItems:      isFlex ? p.alignItems                                     : undefined,
    justifyContent:  isFlex ? p.justifyContent                                 : undefined,
    gap:             p.gap          ? `${p.gap}px`          : undefined,
    padding:         `${p.padding ?? 0}px`,
    minHeight:       `${p.minHeight ?? 0}px`,
    backgroundColor: p.bgColor && p.bgColor !== 'transparent' ? p.bgColor      : undefined,
    borderRadius:    p.borderRadius ? `${p.borderRadius}px` : undefined,
  }

  // 防止塌陷：block 容器触发 BFC（优先 flow-root，避免 overflow 截断 sticky 子元素）
  if (p.preventCollapse && isBlock) {
    style.display = 'flow-root'
  }

  // 吸附固定（sticky）
  if (p.sticky === 'top' || p.sticky === 'bottom') {
    style.position = 'sticky'
    const offset = `${p.stickyOffset ?? 0}px`
    if (p.sticky === 'top')    style.top    = offset
    if (p.sticky === 'bottom') style.bottom = offset
    style.zIndex = String(p.stickyZIndex ?? 10)
    // sticky 元素自身不能被父级 overflow:hidden 限制；这里仅声明自身样式，父级需保证可滚动 + 不裁剪
  }

  return style
})

// 解析图片 src：纯数字视为文件 ID，通过 /api/file/getUrl/:id 构造 302 重定向链接
const resolveImageSrc = (src: any): string => {
  if (!src) return ''
  const s = String(src)
  return buildFileRedirectUrl(s)
}
const buildFileRedirectUrl = (id: string | number) => `${import.meta.env.VITE_API_BASE_URL}/file/getUrl/${id}`



</script>

<template>
  <div :style="item.css">

    <!-- lc-heading -->
    <component
      v-if="item.type === 'lc-heading'"
      :is="item.props.level || 'h2'"
      class="lc-heading"
      :style="{ color: item.props.color, textAlign: item.props.align }"
    >{{ item.props.content }}</component>

    <!-- lc-text -->
    <p
      v-else-if="item.type === 'lc-text'"
      class="lc-text"
      :style="{
        fontSize:   `${item.props.fontSize}px`,
        color:      item.props.color,
        fontWeight: String(item.props.fontWeight),
        textAlign:  item.props.align,
        lineHeight: item.props.lineHeight,
      }"
    >{{ item.props.content }}</p>

    <!-- lc-button -->
    <div v-else-if="item.type === 'lc-button'">
      <LcButton v-bind="item.props">{{ item.props.label }}</LcButton>
    </div>

    <!-- lc-tag -->
    <div v-else-if="item.type === 'lc-tag'" class="lc-tag-wrap">
      <span
        class="lc-tag"
        :style="{
          color:           item.props.color,
          backgroundColor: item.props.bgColor,
          borderColor:     item.props.borderColor,
        }"
      >{{ item.props.content }}</span>
    </div>

    <!-- lc-link -->
    <div v-else-if="item.type === 'lc-link'" class="lc-link-wrap">
      <a
        class="lc-link"
        :href="item.props.href"
        :style="{ color: item.props.color, textDecoration: item.props.underline ? 'underline' : 'none' }"
        target="_blank"
        rel="noopener noreferrer"
      >{{ item.props.text }}</a>
    </div>

    <!-- lc-richtext -->
    <div
      v-else-if="item.type === 'lc-richtext'"
      class="lc-richtext"
      :style="{ minHeight: `${item.props.minHeight ?? 120}px` }"
    >
      <TipTapViewer :content="item.props.content" />
    </div>

    <!-- lc-image -->
    <div v-else-if="item.type === 'lc-image'" class="lc-image-wrap">
      <img
        v-if="item.props.src"
        :src="resolveImageSrc(item.props.src)"
        :alt="item.props.alt"
        :style="{
          width:        `${item.props.width}%`,
          height:       item.props.height ? `${item.props.height}px` : 'auto',
          objectFit:    item.props.objectFit,
          borderRadius: `${item.props.borderRadius}px`,
          display:      'block',
        }"
      />
    </div>

    <!-- lc-divider -->
    <div
      v-else-if="item.type === 'lc-divider'"
      :style="{ margin: `${item.props.margin}px 0` }"
    >
      <div v-if="item.props.text" style="text-align:center;font-size:12px;color:#8c8c8c;margin-bottom:4px">
        {{ item.props.text }}
      </div>
      <hr :style="{ border: 'none', borderTop: `1px solid ${item.props.color}`, margin: 0 }" />
    </div>

    <!-- lc-spacer -->
    <div
      v-else-if="item.type === 'lc-spacer'"
      :style="{ height: `${item.props.height}px` }"
    />

    <!-- lc-grid (container) -->
    <div
      v-else-if="item.type === 'lc-grid'"
      :style="{
        display:             'grid',
        gridTemplateColumns: `repeat(${item.props.columns}, 1fr)`,
        gap:                 `${item.props.gap}px`,
        padding:             `${item.props.padding}px`,
        backgroundColor:     item.props.bgColor !== 'transparent' ? item.props.bgColor : undefined,
      }"
    >
      <CanvasNode v-for="child in children" :key="child.id" :item="child" />
    </div>

    <!-- lc-flex (container) -->
    <div
      v-else-if="item.type === 'lc-flex'"
      :style="{
        display:         'flex',
        flexDirection:   item.props.direction,
        gap:             `${item.props.gap}px`,
        flexWrap:        item.props.wrap ? 'wrap' : 'nowrap',
        alignItems:      item.props.align,
        padding:         `${item.props.padding}px`,
        backgroundColor: item.props.bgColor !== 'transparent' ? item.props.bgColor : undefined,
      }"
    >
      <CanvasNode v-for="child in children" :key="child.id" :item="child" />
    </div>

    <!-- lc-card (container) -->
    <LcCard
      v-else-if="item.type === 'lc-card'"
      v-bind="item.props"
    >
      <CanvasNode v-for="child in children" :key="child.id" :item="child" />
    </LcCard>

    <!-- lc-div (custom container) -->
    <div
      v-else-if="item.type === 'lc-div'"
      :style="divStyle"
    >
      <CanvasNode v-for="child in children" :key="child.id" :item="child" />
    </div>

    <!-- lc-holy-grail (圣杯布局) -->
    <div
      v-else-if="item.type === 'lc-holy-grail'"
      class="lc-holy-grail"
      :style="{
        gridTemplateColumns: `${item.props.leftWidth}px 1fr ${item.props.rightWidth}px`,
        gridTemplateRows:    `${item.props.headerHeight}px 1fr ${item.props.footerHeight}px`,
        gap:                 `${item.props.gap}px`,
        backgroundColor:     item.props.bgColor !== 'transparent' ? item.props.bgColor : undefined,
      }"
    >
      <div class="lc-hg-area" style="grid-area:header">
        <CanvasNode v-if="children[0]" :item="children[0]" />
      </div>
      <div class="lc-hg-area" style="grid-area:left;overflow:auto">
        <CanvasNode v-if="children[1]" :item="children[1]" />
      </div>
      <div class="lc-hg-area" :style="{ gridArea: 'main', overflow: 'auto', minHeight: `${item.props.mainMinH}px` }">
        <CanvasNode v-if="children[2]" :item="children[2]" />
      </div>
      <div class="lc-hg-area" style="grid-area:right;overflow:auto">
        <CanvasNode v-if="children[3]" :item="children[3]" />
      </div>
      <div class="lc-hg-area" style="grid-area:footer">
        <CanvasNode v-if="children[4]" :item="children[4]" />
      </div>
    </div>

    <!-- lc-sticky-footer -->
    <div
      v-else-if="item.type === 'lc-sticky-footer'"
      :style="{
        display:         'flex',
        flexDirection:   'column',
        minHeight:       `${item.props.minHeight}px`,
        gap:             item.props.gap ? `${item.props.gap}px` : undefined,
        backgroundColor: item.props.bgColor !== 'transparent' ? item.props.bgColor : undefined,
      }"
    >
      <div style="flex:1;overflow:auto;min-height:0">
        <CanvasNode v-if="children[0]" :item="children[0]" />
      </div>
      <div :style="{ height: `${item.props.footerHeight}px`, flexShrink: '0', overflow: 'hidden' }">
        <CanvasNode v-if="children[1]" :item="children[1]" />
      </div>
    </div>

    <!-- lc-equal-height -->
    <div
      v-else-if="item.type === 'lc-equal-height'"
      :style="{
        display:         'flex',
        flexDirection:   'row',
        alignItems:      'stretch',
        gap:             `${item.props.gap}px`,
        padding:         `${item.props.padding ?? 0}px`,
        backgroundColor: item.props.bgColor !== 'transparent' ? item.props.bgColor : undefined,
      }"
    >
      <CanvasNode v-for="child in children" :key="child.id" :item="child" />
    </div>

    <!-- carousel (ant-design-vue 走马灯) -->
    <template v-else>
      <AndCom :config="item" :type="item.type"></AndCom>
    </template>
  </div>
</template>

<style scoped>
.lc-heading { margin: 0; word-break: break-word; }
.lc-text    { margin: 0; white-space: pre-wrap; word-break: break-word; }

.lc-tag {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
  border: 1px solid;
}

.lc-link { font-size: 14px; }
.lc-image-wrap { line-height: 0; }

/* 富文本渲染样式 */
.lc-richtext { line-height: 1.6; color: #333; font-size: 14px; }
.lc-richtext :deep(p) { margin: 0 0 8px 0; }
.lc-richtext :deep(h1) { font-size: 24px; font-weight: 600; margin: 0 0 12px 0; }
.lc-richtext :deep(h2) { font-size: 20px; font-weight: 600; margin: 0 0 10px 0; }
.lc-richtext :deep(h3) { font-size: 18px; font-weight: 600; margin: 0 0 8px 0; }
.lc-richtext :deep(h4) { font-size: 16px; font-weight: 600; margin: 0 0 8px 0; }
.lc-richtext :deep(ul),
.lc-richtext :deep(ol) { padding-left: 20px; margin: 0 0 8px 0; }
.lc-richtext :deep(ul) { list-style-type: disc; }
.lc-richtext :deep(ol) { list-style-type: decimal; }
.lc-richtext :deep(li) { margin: 4px 0; }
.lc-richtext :deep(blockquote) { border-left: 3px solid #d9d9d9; padding-left: 12px; margin: 0 0 8px 0; color: #666; }
.lc-richtext :deep(a) { color: #1890ff; text-decoration: underline; }
.lc-richtext :deep(img) { max-width: 100%; height: auto; }
.lc-richtext :deep(table) { width: 100%; border-collapse: collapse; margin: 8px 0; }
.lc-richtext :deep(th),
.lc-richtext :deep(td) { border: 1px solid #d9d9d9; padding: 8px 12px; vertical-align: top; }
.lc-richtext :deep(th) { background: #f5f5f5; font-weight: 600; text-align: left; }
.lc-richtext :deep(hr) { border: none; border-top: 1px solid #d9d9d9; margin: 16px 0; }

/* 圣杯布局 */
.lc-holy-grail {
  display: grid;
  grid-template-areas:
    "header header header"
    "left   main   right"
    "footer footer footer";
  width: 100%;
}
.lc-hg-area {
  overflow: hidden;
}

/* carousel (ant-design-vue) */
.lc-carousel-wrap { width: 100%; overflow: hidden; }
.lc-carousel-wrap :deep(.slick-slide) { text-align: center; overflow: hidden; }
.lc-carousel-slide {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  overflow: hidden;
  text-decoration: none;
  color: inherit;
}
</style>