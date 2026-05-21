<script setup lang="ts">
import { ref, watch, computed } from 'vue'
import type { CanvasNode, PropSchema } from './types'
import { findComponentDef, ALL_BUILTIN } from './types'
import ImageUpload from '@/components/ImageUpload/index.vue'
import TipTapEditor from '@/components/TipTapEditor/index.vue'
import AntDesignVueForm from '../ui/antdesignvue/form/index.vue'


const props = defineProps<{
  node: CanvasNode | null,
  typeCode: string
}>()

const emit = defineEmits<{
  (e: 'update',    nodeId: string, newProps: Record<string, any>): void
  (e: 'updateCss', nodeId: string, newCss: Record<string, string>): void
}>()

// ── Local copy ────────────────────────────────────────────────────────────────
const localProps = ref<Record<string, any>>({})

watch(
  () => props.node,
  n => { localProps.value = n ? { ...n.props } : {} },
  { immediate: true },
)

// ── Component def / schema ────────────────────────────────────────────────────
const def    = computed(() => props.node ? findComponentDef(props.node.type) : null)
const schema = computed<PropSchema[]>(() => def.value?.propSchema ?? [])
// Nodes whose type has no built-in def are "specific" (from API)
const isSpecific = computed(() => props.node && !def.value)

// ── Update helpers ────────────────────────────────────────────────────────────
const setProp = (key: string, value: any) => {
  localProps.value = { ...localProps.value, [key]: value }
  if (props.node) emit('update', props.node.id, { ...localProps.value })
}

// Replace the whole props bag (used by specific-component sub-forms)
const setAllProps = (newProps: Record<string, any>) => {
  localProps.value = { ...newProps }
  if (props.node) emit('update', props.node.id, { ...newProps })
}

// ── JSON editor (for specific / fallback) ─────────────────────────────────────
const jsonEditing  = ref(false)
const rawJsonText  = ref('')
const jsonError    = ref('')

const jsonPreview  = computed(() => JSON.stringify(localProps.value, null, 2))

const openJson = () => {
  rawJsonText.value = jsonPreview.value
  jsonError.value   = ''
  jsonEditing.value = true
}

const applyJson = () => {
  try {
    const parsed = JSON.parse(rawJsonText.value)
    localProps.value = parsed
    if (props.node) emit('update', props.node.id, { ...parsed })
    jsonError.value   = ''
    jsonEditing.value = false
  } catch (e: any) {
    jsonError.value = e.message
  }
}

// ── Category badge color ──────────────────────────────────────────────────────
const categoryBadgeClass = computed(() => {
  const cat = def.value?.category ?? 'specific'
  return `badge-${cat}`
})

// ── CSS style editor ──────────────────────────────────────────────────────────
interface CssEntry { prop: string; value: string }

const cssEntries = ref<CssEntry[]>([])

const CSS_SUGGESTIONS = [
  'width', 'height', 'min-width', 'max-width', 'min-height', 'max-height',
  'margin', 'margin-top', 'margin-right', 'margin-bottom', 'margin-left',
  'padding', 'padding-top', 'padding-right', 'padding-bottom', 'padding-left',
  'display', 'flex', 'flex-direction', 'align-items', 'justify-content',
  'position', 'top', 'right', 'bottom', 'left', 'z-index',
  'font-size', 'font-weight', 'color', 'background-color',
  'border', 'border-radius', 'box-shadow', 'opacity',
  'overflow', 'text-align', 'line-height', 'gap',
]

// sync from node.css
watch(
  () => props.node?.css,
  css => {
    cssEntries.value = css
      ? Object.entries(css).map(([prop, value]) => ({ prop, value }))
      : []
  },
  { immediate: true },
)

const emitCss = () => {
  if (!props.node) return
  const css: Record<string, string> = {}
  for (const entry of cssEntries.value) {
    const p = entry.prop.trim()
    const v = entry.value.trim()
    if (p) css[p] = v
  }
  emit('updateCss', props.node.id, css)
}

const addCssEntry = () => {
  cssEntries.value.push({ prop: '', value: '' })
}

const removeCssEntry = (idx: number) => {
  cssEntries.value.splice(idx, 1)
  emitCss()
}

const onCssPropBlur   = () => emitCss()
const onCssValueBlur  = () => emitCss()
const onCssValueEnter = () => emitCss()
</script>

<template>
  <div class="node-editor">
    <!-- Empty -->
    <div v-if="!node" class="editor-empty">
      <div class="empty-icon">🖱</div>
      <p>点击画布组件<br>开始编辑属性</p>
    </div>

    <template v-else>
      <!-- Header -->
      <div class="editor-header">
        <span class="editor-emoji">{{ def?.iconEmoji ?? '🧩' }}</span>
        <div class="editor-title-wrap">
          <span class="editor-title">{{ node.label }}</span>
          <span :class="['editor-badge', categoryBadgeClass]">
            {{ def?.category ?? 'specific' }}
          </span>
        </div>
        <span class="editor-code">{{ node.type }}</span>
      </div>

      <div class="editor-body">
        <!-- Schema-driven form -->
        <template v-if="schema.length > 0">
          <div class="prop-group">
            <div class="prop-group-label">属性配置</div>
            <div
              v-for="field in schema"
              :key="field.key"
              class="prop-row"
            >
              <label class="prop-label" :title="field.key">{{ field.label }}</label>

              <!-- text -->
              <a-input
                v-if="field.type === 'text'"
                :value="String(localProps[field.key] ?? field.defaultValue ?? '')"
                :placeholder="field.placeholder"
                size="small"
                class="prop-ctrl"
                @change="e => setProp(field.key, (e.target as HTMLInputElement).value)"
              />

              <!-- textarea -->
              <a-textarea
                v-else-if="field.type === 'textarea'"
                :value="String(localProps[field.key] ?? field.defaultValue ?? '')"
                :placeholder="field.placeholder"
                :auto-size="{ minRows: 2, maxRows: 6 }"
                size="small"
                class="prop-ctrl"
                @change="e => setProp(field.key, (e.target as HTMLTextAreaElement).value)"
              />

              <!-- number -->
              <a-input-number
                v-else-if="field.type === 'number'"
                :value="Number(localProps[field.key] ?? field.defaultValue ?? 0)"
                :min="field.min"
                :max="field.max"
                :step="field.step ?? 1"
                size="small"
                class="prop-ctrl prop-number"
                @change="v => setProp(field.key, v)"
              />

              <!-- boolean -->
              <a-switch
                v-else-if="field.type === 'boolean'"
                :checked="Boolean(localProps[field.key] ?? field.defaultValue)"
                size="small"
                @change="v => setProp(field.key, v)"
              />

              <!-- color -->
              <div v-else-if="field.type === 'color'" class="prop-color-row">
                <input
                  type="color"
                  class="prop-color-swatch"
                  :value="String(localProps[field.key] ?? field.defaultValue ?? '#000000')"
                  @input="e => setProp(field.key, (e.target as HTMLInputElement).value)"
                />
                <a-input
                  :value="String(localProps[field.key] ?? field.defaultValue ?? '#000000')"
                  size="small"
                  class="prop-color-text"
                  @change="e => setProp(field.key, (e.target as HTMLInputElement).value)"
                />
              </div>

              <!-- image -->
              <ImageUpload
                v-else-if="field.type === 'image'"
                :model-value="localProps[field.key] || ''"
                value-type="id"
                :limit="1"
                @update:model-value="v => setProp(field.key, v)"
              />

              <!-- richtext -->
              <TipTapEditor
                v-else-if="field.type === 'richtext'"
                :model-value="String(localProps[field.key] ?? field.defaultValue ?? '')"
                :min-height="120"
                placeholder="输入富文本内容…"
                @update:model-value="v => setProp(field.key, v)"
              />

              <!-- select -->
              <a-select
                v-else-if="field.type === 'select'"
                :value="localProps[field.key] ?? field.defaultValue"
                size="small"
                class="prop-ctrl"
                @change="v => setProp(field.key, v)"
              >
                <a-select-option
                  v-for="opt in field.options"
                  :key="opt.value"
                  :value="opt.value"
                >{{ opt.label }}</a-select-option>
              </a-select>
            </div>
          </div>
        </template>
        <!-- Specific component: no schema, show JSON editor -->
        <div v-if="isSpecific" class="prop-group">
<!--          <div class="prop-group-label">特定组件配置</div>-->
          <div>
            <template v-if="typeCode==='ant_design_vue'" >
              <AntDesignVueForm
                :node="node"
                :model-value="localProps"
                @update:model-value="v => setAllProps(v)"
              />
            </template>

          </div>

        </div>

        <!-- CSS style override section -->
        <div class="prop-group css-group">
          <div class="section-title-row">
            <span class="prop-group-label" style="margin-bottom:0">样式覆盖</span>
            <button class="json-btn json-btn-edit" @click="addCssEntry">+ 添加</button>
          </div>
          <div v-if="cssEntries.length === 0" class="css-empty">暂无自定义样式</div>
          <div
            v-for="(entry, idx) in cssEntries"
            :key="idx"
            class="css-row"
          >
            <input
              v-model="entry.prop"
              class="css-prop-input"
              placeholder="属性名"
              list="css-prop-list"
              spellcheck="false"
              @blur="onCssPropBlur"
            />
            <span class="css-colon">:</span>
            <input
              v-model="entry.value"
              class="css-val-input"
              placeholder="值"
              spellcheck="false"
              @blur="onCssValueBlur"
              @keydown.enter="onCssValueEnter"
            />
            <button class="css-del" @click="removeCssEntry(idx)" title="删除">✕</button>
          </div>
          <datalist id="css-prop-list">
            <option v-for="s in CSS_SUGGESTIONS" :key="s" :value="s" />
          </datalist>
        </div>

        <!-- JSON section (always shown at bottom) -->
        <div class="prop-group json-group">
          <div class="section-title-row">
            <span class="prop-group-label" style="margin-bottom:0">配置 JSON</span>
            <div v-if="!jsonEditing" class="json-btn-wrap">
              <button class="json-btn json-btn-edit" @click="openJson">编辑</button>
            </div>
            <div v-else class="json-btn-wrap">
              <button class="json-btn json-btn-apply" @click="applyJson">应用</button>
              <button class="json-btn json-btn-cancel" @click="jsonEditing = false">取消</button>
            </div>
          </div>
          <p v-if="jsonError" class="json-error">{{ jsonError }}</p>
          <textarea
            v-if="jsonEditing"
            v-model="rawJsonText"
            class="json-textarea"
            spellcheck="false"
          />
          <pre v-else class="json-preview">{{ jsonPreview }}</pre>
        </div>
      </div>
    </template>
  </div>
</template>

<style scoped>
.node-editor {
  height: 100%;
  display: flex;
  flex-direction: column;
  font-size: 12px;
  overflow: hidden;
}

/* ── Empty ─────────────────────────────────────────────────────────────── */
.editor-empty {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #bfbfbf;
  text-align: center;
  gap: 10px;
}
.editor-empty .empty-icon { font-size: 36px; opacity: 0.4; }
.editor-empty p { margin: 0; font-size: 12px; line-height: 1.8; }

/* ── Header ────────────────────────────────────────────────────────────── */
.editor-header {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 12px;
  background: #fafafa;
  border-bottom: 1px solid #f0f0f0;
  flex-shrink: 0;
}
.editor-emoji { font-size: 20px; flex-shrink: 0; }
.editor-title-wrap { flex: 1; display: flex; flex-direction: column; gap: 2px; overflow: hidden; }
.editor-title {
  font-size: 13px;
  font-weight: 600;
  color: #262626;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.editor-badge {
  font-size: 10px;
  font-weight: 600;
  padding: 1px 5px;
  border-radius: 3px;
  align-self: flex-start;
}
.badge-layout   { background: #f9f0ff; color: #722ed1; }
.badge-basic    { background: #e6f4ff; color: #1677ff; }
.badge-media    { background: #f6ffed; color: #389e0d; }
.badge-specific { background: #fff7e6; color: #d46b08; }

.editor-code {
  font-size: 10px;
  font-family: monospace;
  color: #8c8c8c;
  flex-shrink: 0;
  max-width: 90px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* ── Body ──────────────────────────────────────────────────────────────── */
.editor-body {
  flex: 1;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
}

/* ── Prop group ────────────────────────────────────────────────────────── */
.prop-group {
  border-bottom: 1px solid #f0f0f0;
  padding: 10px 12px;
}
.prop-group-label {
  font-size: 11px;
  font-weight: 600;
  color: #8c8c8c;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  margin-bottom: 10px;
}

/* ── Prop row ──────────────────────────────────────────────────────────── */
.prop-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}
.prop-row:last-child { margin-bottom: 0; }

.prop-label {
  width: 72px;
  flex-shrink: 0;
  font-size: 12px;
  color: #555;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.prop-ctrl  { flex: 1; }
.prop-number { width: 100%; }

/* ── Color ─────────────────────────────────────────────────────────────── */
.prop-color-row  { display: flex; align-items: center; gap: 5px; flex: 1; }
.prop-color-swatch {
  width: 28px;
  height: 28px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  padding: 1px;
  cursor: pointer;
  flex-shrink: 0;
}
.prop-color-text { flex: 1; }

/* ── Specific hint ─────────────────────────────────────────────────────── */
.specific-hint {
  margin: 0;
  font-size: 11px;
  color: #8c8c8c;
  line-height: 1.6;
}

/* ── JSON section ──────────────────────────────────────────────────────── */
.json-group { flex: 1; }

.section-title-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
}

.json-btn-wrap { display: flex; gap: 4px; }

.json-btn {
  font-size: 11px;
  padding: 2px 8px;
  border-radius: 3px;
  cursor: pointer;
  border: 1px solid;
  transition: all 0.15s;
}
.json-btn-edit   { border-color: #d9d9d9; background: #fff;    color: #555; }
.json-btn-edit:hover { border-color: #1677ff; color: #1677ff; }
.json-btn-apply  { border-color: #1677ff; background: #1677ff; color: #fff; }
.json-btn-apply:hover { background: #4096ff; }
.json-btn-cancel { border-color: #d9d9d9; background: #fff;    color: #555; }
.json-btn-cancel:hover { border-color: #ff4d4f; color: #ff4d4f; }

.json-error {
  color: #ff4d4f;
  font-size: 11px;
  margin: 0 0 6px;
  word-break: break-all;
}

.json-preview {
  margin: 0;
  font-size: 11px;
  font-family: monospace;
  color: #555;
  background: #f9f9f9;
  border: 1px solid #f0f0f0;
  border-radius: 4px;
  padding: 8px;
  overflow: auto;
  max-height: 240px;
  white-space: pre;
}

.json-textarea {
  width: 100%;
  min-height: 160px;
  font-size: 11px;
  font-family: monospace;
  padding: 8px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  outline: none;
  resize: vertical;
  box-sizing: border-box;
  background: #fafafa;
}
.json-textarea:focus { border-color: #1677ff; background: #fff; }

/* ── CSS style editor ────────────────────────────────────────── */
.css-group { }

.css-empty {
  font-size: 11px;
  color: #bfbfbf;
  padding: 4px 0;
}

.css-row {
  display: flex;
  align-items: center;
  gap: 4px;
  margin-top: 6px;
}

.css-prop-input {
  width: 96px;
  flex-shrink: 0;
  height: 24px;
  padding: 0 6px;
  font-size: 11px;
  font-family: monospace;
  border: 1px solid #d9d9d9;
  border-radius: 3px;
  outline: none;
  color: #c41d7f;
  background: #fff;
  transition: border-color 0.15s;
}
.css-prop-input:focus { border-color: #1677ff; }

.css-colon {
  font-size: 11px;
  font-family: monospace;
  color: #8c8c8c;
  flex-shrink: 0;
}

.css-val-input {
  flex: 1;
  min-width: 0;
  height: 24px;
  padding: 0 6px;
  font-size: 11px;
  font-family: monospace;
  border: 1px solid #d9d9d9;
  border-radius: 3px;
  outline: none;
  color: #389e0d;
  background: #fff;
  transition: border-color 0.15s;
}
.css-val-input:focus { border-color: #1677ff; }

.css-del {
  flex-shrink: 0;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  background: transparent;
  color: #bfbfbf;
  font-size: 11px;
  cursor: pointer;
  border-radius: 3px;
  padding: 0;
  transition: color 0.15s, background 0.15s;
}
.css-del:hover { color: #ff4d4f; background: #fff1f0; }
</style>
