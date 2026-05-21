<template>
  <div class="icon-selector">
    <a-input
      v-model:value="currentIcon"
      placeholder="点击选择图标"
      readonly
      @click="visible = true"
    >
      <template #prefix>
        <component :is="icons[currentIcon]" v-if="currentIcon && icons[currentIcon]" />
      </template>
      <template #suffix>
        <AppstoreOutlined @click.stop="visible = true" style="cursor: pointer;" />
      </template>
    </a-input>

    <a-modal
      v-model:open="visible"
      title="选择图标"
      width="800px"
      :footer="null"
      centered
      class="icon-selector-modal"
    >
      <div class="icon-search">
        <a-input-search v-model:value="searchKey" placeholder="搜索图标" style="margin-bottom: 16px" allow-clear />
      </div>
      
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="outlined" tab="线框风格">
          <div class="icon-list">
            <div
              v-for="icon in filteredOutlinedIcons"
              :key="icon"
              class="icon-item"
              :class="{ active: currentIcon === icon }"
              @click="handleSelect(icon)"
            >
              <component :is="icons[icon]" class="icon-svg" />
              <span class="icon-name" :title="icon">{{ icon }}</span>
            </div>
          </div>
        </a-tab-pane>
        <a-tab-pane key="filled" tab="实底风格">
          <div class="icon-list">
            <div
              v-for="icon in filteredFilledIcons"
              :key="icon"
              class="icon-item"
              :class="{ active: currentIcon === icon }"
              @click="handleSelect(icon)"
            >
              <component :is="icons[icon]" class="icon-svg" />
              <span class="icon-name" :title="icon">{{ icon }}</span>
            </div>
          </div>
        </a-tab-pane>
        <a-tab-pane key="twotone" tab="双色风格">
          <div class="icon-list">
            <div
              v-for="icon in filteredTwoToneIcons"
              :key="icon"
              class="icon-item"
              :class="{ active: currentIcon === icon }"
              @click="handleSelect(icon)"
            >
              <component :is="icons[icon]" class="icon-svg" />
              <span class="icon-name" :title="icon">{{ icon }}</span>
            </div>
          </div>
        </a-tab-pane>
      </a-tabs>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import * as antIcons from '@ant-design/icons-vue'
import { AppstoreOutlined } from '@ant-design/icons-vue'

const props = defineProps({
  value: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:value', 'change'])

const icons = antIcons as Record<string, any>

const visible = ref(false)
const searchKey = ref('')
const activeTab = ref('outlined')

const currentIcon = ref(props.value)

watch(() => props.value, (val) => {
  currentIcon.value = val
})

const allIconKeys = Object.keys(icons)
const outlinedIcons = allIconKeys.filter(key => key.endsWith('Outlined') && key !== 'AppstoreOutlined') // keep others intact, actually no need to filter AppstoreOutlined out, it's just an icon.
// actually let's just use all keys.
const outlinedIconsList = allIconKeys.filter(key => key.endsWith('Outlined'))
const filledIcons = allIconKeys.filter(key => key.endsWith('Filled'))
const twoToneIcons = allIconKeys.filter(key => key.endsWith('TwoTone'))

const filteredOutlinedIcons = computed(() => {
  if (!searchKey.value) return outlinedIconsList
  return outlinedIconsList.filter(key => key.toLowerCase().includes(searchKey.value.toLowerCase()))
})

const filteredFilledIcons = computed(() => {
  if (!searchKey.value) return filledIcons
  return filledIcons.filter(key => key.toLowerCase().includes(searchKey.value.toLowerCase()))
})

const filteredTwoToneIcons = computed(() => {
  if (!searchKey.value) return twoToneIcons
  return twoToneIcons.filter(key => key.toLowerCase().includes(searchKey.value.toLowerCase()))
})

const handleSelect = (iconName: string) => {
  currentIcon.value = iconName
  emit('update:value', iconName)
  emit('change', iconName)
  visible.value = false
}
</script>

<style scoped>
.icon-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
  gap: 12px;
  max-height: 400px;
  overflow-y: auto;
  padding: 8px;
}

.icon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 12px 8px;
  border: 1px solid transparent;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;
}

.icon-item:hover {
  background-color: #f5f5f5;
}

:global(html.dark) .icon-item:hover {
  background-color: #334155;
}

.icon-item.active {
  background-color: var(--app-primary-color-light, #e6f7ff);
  border-color: var(--app-primary-color, #1890ff);
  color: var(--app-primary-color, #1890ff);
}

:global(html.dark) .icon-item.active {
  background-color: rgba(99, 102, 241, 0.2);
  border-color: #6366f1;
  color: #6366f1;
}

.icon-svg {
  font-size: 24px;
  margin-bottom: 8px;
}

.icon-name {
  font-size: 12px;
  text-align: center;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 滚动条样式 */
.icon-list::-webkit-scrollbar {
  width: 6px;
}
.icon-list::-webkit-scrollbar-thumb {
  background: #ccc;
  border-radius: 3px;
}
:global(html.dark) .icon-list::-webkit-scrollbar-thumb {
  background: #475569;
}
</style>
