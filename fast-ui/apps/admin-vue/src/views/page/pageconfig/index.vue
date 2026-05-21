<script setup lang="ts">
import {ref, computed, watch, onMounted, onUnmounted} from 'vue'
import {useRoute} from 'vue-router'
import {message} from 'ant-design-vue'
import PageList from './components/PageList.vue'
import PageEditorLayout from './components/PageEditorLayout.vue'
import PageLibraryPanel from './components/PageLibraryPanel.vue'
import PageDesignCanvas from './components/PageDesignCanvas.vue'
import PageNodeEditor from './components/PageNodeEditor.vue'
import PagePublishManageDrawer from './components/PagePublishManageDrawer.vue'
import {
  getPageConfigComponentsByTypeId,
  getPageConfigById,
  updatePageConfig,
  publishPageConfig,
  type PageConfigListVo,
} from '@/api/page/pageconfig.ts'
import {type ComponentDef, type CanvasNode, type PageSchema, findComponentDef} from './components/types'
import {getRequestId} from "@/utils/idUtils.ts";

// ── Route / application id ────────────────────────────────────────────────────
const route = useRoute()
const pageApplicationId = computed(() => {
  const id = route.query.id
  return Array.isArray(id) ? String(id[0] ?? '') : String(id ?? '')
})

// ── Current page meta ─────────────────────────────────────────────────────────
const currentPage = ref<PageConfigListVo | null>(null)
const isSaving = ref(false)
const isPublishing = ref(false)

// ── Publish manage drawer ────────────────────────────────────────────────
const publishDrawerVisible = ref(false)

// ── Specific components from API ──────────────────────────────────────────────
const specificComps = ref<ComponentDef[]>([])

const handleApplicationType = (typeId: string) => {
  if (!typeId) return
  getPageConfigComponentsByTypeId(typeId.toString()).then(res => {
    specificComps.value = (res.data ?? []).map((item: any) => ({
      ...item,
      category: 'specific' as const,
      propSchema: [],
      defaultProps: item.defaultConfig ?? {},
    }))
  })
}

// ── Canvas nodes ──────────────────────────────────────────────────────────────
const canvasNodes = ref<CanvasNode[]>([])
const selectedNodeId = ref<string | null>(null)

// ── Undo / Redo history ───────────────────────────────────────────────────────
const MAX_HISTORY = 50
const history = ref<CanvasNode[][]>([])
const histIndex = ref(-1)

const snapshot = () => JSON.parse(JSON.stringify(canvasNodes.value)) as CanvasNode[]

const pushHistory = () => {
  // Drop forward history when a new change comes in after undo
  history.value = history.value.slice(0, histIndex.value + 1)
  history.value.push(snapshot())
  if (history.value.length > MAX_HISTORY) history.value.shift()
  histIndex.value = history.value.length - 1
}

const undo = () => {
  if (histIndex.value > 0) {
    histIndex.value--
    canvasNodes.value = JSON.parse(JSON.stringify(history.value[histIndex.value]))
  }
}

const redo = () => {
  if (histIndex.value < history.value.length - 1) {
    histIndex.value++
    canvasNodes.value = JSON.parse(JSON.stringify(history.value[histIndex.value]))
  }
}

// Record history whenever nodes change (but not on undo/redo replays)
let _suppressHistory = false
watch(canvasNodes, () => {
  if (_suppressHistory) return
  pushHistory()
}, {deep: true})

// ── Page select → load config ─────────────────────────────────────────────────
const handleSelectPage = async (page: PageConfigListVo) => {
  currentPage.value = page
  selectedNodeId.value = null
  try {
    const res = await getPageConfigById(page.id!)
    const fullPage = res.data
    if (fullPage?.config) {
      const schema = JSON.parse(fullPage.config) as PageSchema
      _suppressHistory = true
      canvasNodes.value = schema.nodes ?? []
      // Reset history to this loaded state
      history.value = [snapshot()]
      histIndex.value = 0
      _suppressHistory = false
    } else {
      _suppressHistory = true
      canvasNodes.value = []
      history.value = [[]]
      histIndex.value = 0
      _suppressHistory = false
    }
  } catch {
    message.error('加载页面配置失败')
  }
}

// ── Nodes update from canvas ──────────────────────────────────────────────────
const handleNodesUpdate = (nodes: CanvasNode[]) => {
  canvasNodes.value = nodes
}

// ── Selected node ─────────────────────────────────────────────────────────────
const selectedNode = computed<CanvasNode | null>(() => {
  if (!selectedNodeId.value) return null
  return findNodeInTree(canvasNodes.value, selectedNodeId.value)
})

const findNodeInTree = (list: CanvasNode[], id: string): CanvasNode | null => {
  for (const n of list) {
    if (n.id === id) return n
    if (n.children?.length) {
      const found = findNodeInTree(n.children, id)
      if (found) return found
    }
  }
  return null
}

// ── Props update from editor ──────────────────────────────────────────────────
const handlePropsUpdate = (nodeId: string, newProps: Record<string, any>) => {
  updateNodeProps(canvasNodes.value, nodeId, newProps)
}

const updateNodeProps = (list: CanvasNode[], id: string, newProps: Record<string, any>): boolean => {
  for (const n of list) {
    if (n.id === id) {
      n.props = newProps;
      return true
    }
    if (n.children?.length && updateNodeProps(n.children, id, newProps)) return true
  }
  return false
}

// ── Delete node (from tree) ───────────────────────────────────────────────────
const removeNodeFromTree = (list: CanvasNode[], id: string): boolean => {
  const idx = list.findIndex(n => n.id === id)
  if (idx !== -1) {
    list.splice(idx, 1);
    return true
  }
  for (const n of list) {
    if (n.children?.length && removeNodeFromTree(n.children, id)) return true
  }
  return false
}

const handleDeleteNode = (id: string) => {
  removeNodeFromTree(canvasNodes.value, id)
  // Clear selection if the deleted node was selected
  if (selectedNodeId.value === id) selectedNodeId.value = null
}

// ── Move node (from tree drag-and-drop) ──────────────────────────────────────────
const _findNode = (
    list: CanvasNode[],
    id: string,
): [CanvasNode, CanvasNode[], number] | null => {
  for (let i = 0; i < list.length; i++) {
    if (list[i].id === id) return [list[i], list, i]
    if (list[i].children?.length) {
      const found = _findNode(list[i].children!, id)
      if (found) return found
    }
  }
  return null
}

const _getChildren = (list: CanvasNode[], parentId: string | null): CanvasNode[] | null => {
  if (parentId === null) return list
  const res = _findNode(list, parentId)
  if (!res) return null
  const [node] = res
  if (!node.children) node.children = []
  return node.children
}

const handleMoveNode = (nodeId: string, parentId: string | null, index: number | null) => {
  if (nodeId === parentId) return
  const list = JSON.parse(JSON.stringify(canvasNodes.value)) as CanvasNode[]
  const res = _findNode(list, nodeId)
  if (!res) return
  const [node, srcParent, srcIndex] = res
  srcParent.splice(srcIndex, 1)
  const children = _getChildren(list, parentId)
  if (!children) return
  if (index === null) {
    children.push(node)
  } else {
    const adjustedIndex = (srcParent === children && srcIndex < index) ? index - 1 : index
    children.splice(Math.max(0, adjustedIndex), 0, node)
  }
  canvasNodes.value = list
}

// ── CSS update from editor ────────────────────────────────────────────────
const handleCssUpdate = (nodeId: string, newCss: Record<string, string>) => {
  updateNodeCss(canvasNodes.value, nodeId, newCss)
}

const updateNodeCss = (list: CanvasNode[], id: string, newCss: Record<string, string>): boolean => {
  for (const n of list) {
    if (n.id === id) {
      n.css = Object.keys(newCss).length > 0 ? newCss : undefined
      return true
    }
    if (n.children?.length && updateNodeCss(n.children, id, newCss)) return true
  }
  return false
}

// ── Save ──────────────────────────────────────────────────────────────────────
const handleSave = async () => {
  if (!currentPage.value?.id) {
    message.warning('请先从左侧选择要保存的页面')
    return
  }
  isSaving.value = true
  try {
    const schema: PageSchema = {version: '1', nodes: canvasNodes.value}

    await updatePageConfig({
      id: currentPage.value.id,
      config: JSON.stringify(schema),
      // Carry over existing meta fields
      title: currentPage.value.title,
      pathUrl: currentPage.value.pathUrl,
      status: currentPage.value.status,
      version: currentPage.value.version,
      applicationId: currentPage.value.applicationId,
    }, getRequestId())
    message.success('保存成功')
  } catch {
    message.error('保存失败')
  } finally {
    isSaving.value = false
  }
}

// ── Publish ───────────────────────────────────────────────────────────────────
const handlePublish = async () => {
  if (!currentPage.value?.id) {
    message.warning('请先从左侧选择要发布的页面')
    return
  }
  isPublishing.value = true
  try {
    const schema: PageSchema = {version: '1', nodes: canvasNodes.value}

    await publishPageConfig({
      id: currentPage.value.id,
      config: JSON.stringify(schema),
      // Carry over existing meta fields
      title: currentPage.value.title,
      pathUrl: currentPage.value.pathUrl,
      status: currentPage.value.status,
      version: currentPage.value.version,
      applicationId: currentPage.value.applicationId,
    }, getRequestId())
    message.success('发布成功')
  } catch {
    message.error('发布失败')
  } finally {
    isPublishing.value = false
  }
}

// ── Keyboard shortcuts ────────────────────────────────────────────────────────
const onKeyDown = (e: KeyboardEvent) => {
  const ctrl = e.ctrlKey || e.metaKey
  if (ctrl && e.key === 'z' && !e.shiftKey) {
    e.preventDefault();
    undo()
  }
  if (ctrl && (e.key === 'y' || (e.key === 'z' && e.shiftKey))) {
    e.preventDefault();
    redo()
  }
  if (ctrl && e.key === 's') {
    e.preventDefault();
    handleSave()
  }
}

onMounted(() => window.addEventListener('keydown', onKeyDown))
onUnmounted(() => window.removeEventListener('keydown', onKeyDown))

const typeCode = ref<string>('')

const handleTypeCode = (code: string) => {
  typeCode.value = code
}

</script>

<template>
  <div class="page-editor-root">
    <!-- Left: page list -->
    <div class="side-panel">
      <PageList
          :application-id="pageApplicationId"
          @select="handleSelectPage"
          @applicationType="handleApplicationType"
          @select-type-code="handleTypeCode"
      />
    </div>

    <!-- Main: 3-panel editor layout -->
    <PageEditorLayout :component-lib-width="220" :property-panel-width="280">
      <!-- Component library + node tree -->
      <template #component-list>
        <PageLibraryPanel
            :specific-components="specificComps"
            :nodes="canvasNodes"
            :selected-node-id="selectedNodeId"
            @update:selected-node-id="id => selectedNodeId = id"
            @delete-node="handleDeleteNode"
            @move-node="handleMoveNode"
        />
      </template>

      <!-- Design canvas -->
      <template #page-editor>
        <PageDesignCanvas
            :type-code="typeCode"
            :nodes="canvasNodes"
            :selected-node-id="selectedNodeId"
            @update:nodes="handleNodesUpdate"
            @update:selected-node-id="id => selectedNodeId = id"
            @save="handleSave"
            @publish="handlePublish"
            @publish-manage="() => publishDrawerVisible = true"
            @undo="undo"
            @redo="redo"
        />
      </template>

      <!-- Property editor -->
      <template #node-editor>
        <PageNodeEditor
            :type-code="typeCode"
            :node="selectedNode"
            @update="handlePropsUpdate"
            @update-css="handleCssUpdate"
        />
      </template>
    </PageEditorLayout>
  </div>

  <!-- ── Publish management drawer ───────────────────────────────────────── -->
  <PagePublishManageDrawer
      v-model:open="publishDrawerVisible"
      :application-id="pageApplicationId"
  />
</template>

<style scoped>
.page-editor-root {
  display: flex;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background: #f0f2f5;
}

.side-panel {
  width: 200px;
  flex-shrink: 0;
  background: #fff;
  border-right: 1px solid #e8e8e8;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>
