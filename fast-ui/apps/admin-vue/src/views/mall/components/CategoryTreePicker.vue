<template>
  <a-tree-select
    v-model:value="innerValue"
    :tree-data="treeData"
    :placeholder="placeholder"
    :disabled="disabled"
    @focus="handleFocus"
    @dropdownVisibleChange="handleDropdownVisibleChange"
    allow-clear
    show-search
    tree-default-expand-all
    tree-node-filter-prop="title"
    style="width:100%"
  />
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { message } from 'ant-design-vue'
import { getCategoryTree, type MallProductCategoryVo } from '@/api/mall/mallproductcategory.ts'

let categoryTreeCache: MallProductCategoryVo[] | undefined
let categoryTreeLoading: Promise<MallProductCategoryVo[] | undefined> | undefined

interface Props {
  modelValue?: string | number | bigint | object | null
  placeholder?: string
  disabled?: boolean
}

type CategoryTreeSelectNode = {
  title: string
  label: string
  value: string
  children?: CategoryTreeSelectNode[]
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: undefined,
  placeholder: '请选择分类',
  disabled: false
})

const emit = defineEmits<{
  (e: 'update:modelValue', value: string | undefined): void
}>()

const categoryTree = ref<MallProductCategoryVo[]>([])

const toStringId = (val: unknown): string | undefined => {
  if (val === null || val === undefined || val === '') return undefined
  if (typeof val === 'string') return val
  if (typeof val === 'number' || typeof val === 'bigint') return String(val)
  if (typeof val === 'object') {
    const str = (val as any).toString?.()
    return str ? String(str) : undefined
  }
  return undefined
}

const toTreeData = (nodes: MallProductCategoryVo[]): CategoryTreeSelectNode[] => {
  return nodes.map(node => ({
    title: node.name || '',
    label: node.name || '',
    value: toStringId(node.id) || '',
    children: node.children?.length ? toTreeData(node.children) : undefined
  }))
}

const treeData = computed<CategoryTreeSelectNode[]>(() => toTreeData(categoryTree.value))

const innerValue = computed<string | undefined>({
  get() {
    return toStringId(props.modelValue)
  },
  set(value) {
    emit('update:modelValue', value || undefined)
  }
})

const ensureCategoryTreeLoaded = async () => {
  if (categoryTree.value.length) return
  if (categoryTreeCache?.length) {
    categoryTree.value = categoryTreeCache
    return
  }
  if (categoryTreeLoading) {
    await categoryTreeLoading
    if (categoryTreeCache?.length) categoryTree.value = categoryTreeCache
    return
  }
  categoryTreeLoading = (async () => {
    try {
      const res: any = await getCategoryTree()
      if (res.code === 200) {
        categoryTreeCache = res.data || []
        categoryTree.value = categoryTreeCache
      }
      return categoryTreeCache
    } catch (error) {
      message.error('加载分类树失败')
      return undefined
    } finally {
      categoryTreeLoading = undefined
    }
  })()
  await categoryTreeLoading
}

const handleFocus = () => {
  ensureCategoryTreeLoaded()
}

const handleDropdownVisibleChange = (open: boolean) => {
  if (open) ensureCategoryTreeLoaded()
}

watch(
  () => props.modelValue,
  (val) => {
    const id = toStringId(val)
    if (!id) return
    ensureCategoryTreeLoaded()
  },
  { immediate: true }
)
</script>
