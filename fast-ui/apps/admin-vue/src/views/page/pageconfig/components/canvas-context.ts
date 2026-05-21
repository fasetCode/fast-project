import type { Ref } from 'vue'
import type { CanvasNode } from './types'

export interface CanvasContext {
  selectedId:  Ref<string | null>
  draggingId:  Ref<string | null>
  /** 当前选中页面的应用类型编码，例如 ant_design_vue */
  typeCode:    Ref<string>
  selectNode:  (id: string) => void
  deleteNode:  (id: string) => void
  /**
   * Insert a new node.
   * @param node        node to insert
   * @param parentId    null = root; otherwise the container's id
   * @param index       null = append to end; otherwise insert before that index
   */
  insertNode:  (node: CanvasNode, parentId: string | null, index: number | null) => void
  /**
   * Move an existing node (identified by nodeId) to a new location.
   * @param nodeId      id of the node to move
   * @param parentId    null = root
   * @param index       null = append to end
   */
  moveNode:    (nodeId: string, parentId: string | null, index: number | null) => void
}

export const CANVAS_CONTEXT_KEY = Symbol('canvas-context')
