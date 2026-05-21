<script setup lang="ts">
import { computed, nextTick, onBeforeUnmount, onMounted, ref, watch, h } from 'vue'
import { message } from 'ant-design-vue'
import type { UploadProps } from 'ant-design-vue'
import { EditorContent, useEditor, VueNodeViewRenderer, nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import Underline from '@tiptap/extension-underline'
import Link from '@tiptap/extension-link'
import Image from '@tiptap/extension-image'
import TextAlign from '@tiptap/extension-text-align'
import Color from '@tiptap/extension-color'
import { TextStyle } from '@tiptap/extension-text-style'
import { Table } from '@tiptap/extension-table'
import { TableRow } from '@tiptap/extension-table-row'
import { TableCell } from '@tiptap/extension-table-cell'
import { TableHeader } from '@tiptap/extension-table-header'
import Dropcursor from '@tiptap/extension-dropcursor'
import { uploadSimple } from '@/api/file/fileupload'
import {
  BoldOutlined,
  ItalicOutlined,
  UnderlineOutlined,
  StrikethroughOutlined,
  UnorderedListOutlined,
  OrderedListOutlined,
  AlignLeftOutlined,
  AlignCenterOutlined,
  AlignRightOutlined,
  LinkOutlined,
  PictureOutlined,
  RedoOutlined,
  UndoOutlined,
  TableOutlined,
  UploadOutlined,
  DownOutlined,
  DragOutlined,
} from '@ant-design/icons-vue'

interface Props {
  modelValue?: string
  placeholder?: string
  disabled?: boolean
  minHeight?: number
  allowImage?: boolean
  uploadConfigId?: number
}

const buildFileRedirectUrl = (id: string | number) => `${import.meta.env.VITE_API_BASE_URL}/file/getUrl/${id}`

const ResizableImage = {
  props: nodeViewProps,
  setup(props: any) {
    const isResizing = ref(false)
    const startX = ref(0)
    const startWidth = ref(0)
    const imgRef = ref<HTMLImageElement>()
    const wrapperRef = ref<HTMLElement>()
    const toolbarRef = ref<HTMLElement>()
    const toolbarStyle = ref<Record<string, string>>({})

    const updateToolbarPosition = () => {
      nextTick(() => {
        const wrapper = wrapperRef.value
        const toolbar = toolbarRef.value
        if (!wrapper || !toolbar || !props.selected) return

        const editorRoot = wrapper.closest('.ProseMirror') as HTMLElement | null
        if (!editorRoot) return

        const wrapperRect = wrapper.getBoundingClientRect()
        const toolbarRect = toolbar.getBoundingClientRect()
        const editorRect = editorRoot.getBoundingClientRect()
        const edgePadding = 8

        const desiredLeft = (wrapperRect.width - toolbarRect.width) / 2
        const minLeft = editorRect.left + edgePadding - wrapperRect.left
        const maxLeft = editorRect.right - edgePadding - wrapperRect.left - toolbarRect.width
        const clampedLeft = Math.max(minLeft, Math.min(desiredLeft, maxLeft))

        toolbarStyle.value = {
          left: `${clampedLeft}px`,
          transform: 'none',
        }
      })
    }

    const onMouseDown = (e: MouseEvent) => {
      if (props.editor.isEditable === false) return
      e.preventDefault()
      e.stopPropagation()
      isResizing.value = true
      startX.value = e.clientX
      startWidth.value = imgRef.value?.clientWidth || 0
      
      document.addEventListener('mousemove', onMouseMove)
      document.addEventListener('mouseup', onMouseUp)
    }

    const onMouseMove = (e: MouseEvent) => {
      if (!isResizing.value) return
      const deltaX = e.clientX - startX.value
      const newWidth = Math.max(50, startWidth.value + deltaX)
      props.updateAttributes({ width: `${newWidth}px` })
      updateToolbarPosition()
    }

    const onMouseUp = () => {
      isResizing.value = false
      document.removeEventListener('mousemove', onMouseMove)
      document.removeEventListener('mouseup', onMouseUp)
    }

    const setWidth = (width: string) => {
      if (props.editor.isEditable === false) return
      props.updateAttributes({ width })
      updateToolbarPosition()
    }

    watch(
      () => props.selected,
      value => {
        if (value) {
          updateToolbarPosition()
        }
      }
    )

    watch(
      () => props.node.attrs.width,
      () => updateToolbarPosition()
    )

    onMounted(() => {
      window.addEventListener('resize', updateToolbarPosition)
      updateToolbarPosition()
    })

    onBeforeUnmount(() => {
      window.removeEventListener('resize', updateToolbarPosition)
    })

    return { imgRef, wrapperRef, toolbarRef, toolbarStyle, onMouseDown, isResizing, setWidth }
  },
  render(ctx: any) {
    const { node, selected, editor } = ctx
    const isEditable = editor.isEditable
    const imageAlign = node.attrs.align || 'left'
    const wrapperWidth = node.attrs.width && node.attrs.width.endsWith('%')
      ? node.attrs.width
      : (node.attrs.width && node.attrs.width !== 'auto' ? node.attrs.width : 'fit-content')
    const marginStyle = imageAlign === 'center'
      ? '0 auto'
      : imageAlign === 'right'
        ? '0 0 0 auto'
        : '0 auto 0 0'

    return h(NodeViewWrapper, {
      ref: 'wrapperRef',
      class: ['resizable-image-container', selected ? 'is-selected' : ''].join(' '),
      style: {
        display: 'block',
        lineHeight: '0',
        position: 'relative',
        userSelect: 'none',
        width: wrapperWidth,
        maxWidth: '100%',
        margin: marginStyle,
      }
    }, [
      h('img', {
        ref: 'imgRef',
        src: node.attrs.src,
        'data-img-id': node.attrs['data-img-id'],
        width: node.attrs.width,
        class: 'resizable-image',
        style: {
          display: 'block',
          width: node.attrs.width && node.attrs.width.endsWith('%') ? '100%' : (node.attrs.width || 'auto'),
          maxWidth: '100%',
          height: 'auto',
        }
      }),
      isEditable ? h('div', {
        class: 'resize-handle',
        onMousedown: ctx.onMouseDown,
        style: {
          position: 'absolute',
          right: '-4px',
          bottom: '-4px',
          width: '12px',
          height: '12px',
          backgroundColor: '#1890ff',
          border: '2px solid #fff',
          borderRadius: '50%',
          cursor: 'nwse-resize',
          zIndex: '10',
          boxShadow: '0 2px 4px rgba(0,0,0,0.2)',
          opacity: selected ? '1' : '0',
          transition: 'opacity 0.2s',
        }
      }) : null,
      (isEditable && selected) ? h('div', {
        ref: 'toolbarRef',
        class: 'image-toolbar',
        style: {
          position: 'absolute',
          top: '8px',
          left: '0',
          transform: 'none',
          display: 'flex',
          gap: '4px',
          padding: '4px',
          backgroundColor: 'rgba(255, 255, 255, 0.9)',
          backdropFilter: 'blur(4px)',
          borderRadius: '6px',
          boxShadow: '0 2px 8px rgba(0,0,0,0.15)',
          zIndex: '100',
          whiteSpace: 'nowrap',
          border: '1px solid #f0f0f0',
          maxWidth: 'calc(100vw - 32px)',
          overflowX: 'auto',
          ...ctx.toolbarStyle,
        }
      }, [
        h('button', {
          onClick: () => ctx.setWidth('25%'),
          style: { cursor: 'pointer', padding: '2px 6px', fontSize: '12px', border: '1px solid #d9d9d9', borderRadius: '4px', background: node.attrs.width === '25%' ? '#e6f7ff' : '#fff', color: node.attrs.width === '25%' ? '#1890ff' : '#333' }
        }, '25%'),
        h('button', {
          onClick: () => ctx.setWidth('50%'),
          style: { cursor: 'pointer', padding: '2px 6px', fontSize: '12px', border: '1px solid #d9d9d9', borderRadius: '4px', background: node.attrs.width === '50%' ? '#e6f7ff' : '#fff', color: node.attrs.width === '50%' ? '#1890ff' : '#333' }
        }, '50%'),
        h('button', {
          onClick: () => ctx.setWidth('75%'),
          style: { cursor: 'pointer', padding: '2px 6px', fontSize: '12px', border: '1px solid #d9d9d9', borderRadius: '4px', background: node.attrs.width === '75%' ? '#e6f7ff' : '#fff', color: node.attrs.width === '75%' ? '#1890ff' : '#333' }
        }, '75%'),
        h('button', {
          onClick: () => ctx.setWidth('100%'),
          style: { cursor: 'pointer', padding: '2px 6px', fontSize: '12px', border: '1px solid #d9d9d9', borderRadius: '4px', background: node.attrs.width === '100%' ? '#e6f7ff' : '#fff', color: node.attrs.width === '100%' ? '#1890ff' : '#333' }
        }, '100%'),
        h('button', {
          onClick: () => ctx.setWidth('auto'),
          style: { cursor: 'pointer', padding: '2px 6px', fontSize: '12px', border: '1px solid #d9d9d9', borderRadius: '4px', background: !node.attrs.width || node.attrs.width === 'auto' ? '#e6f7ff' : '#fff', color: !node.attrs.width || node.attrs.width === 'auto' ? '#1890ff' : '#333' }
        }, '自适应'),
      ]) : null
    ])
  }
}

const ImageWithFileId = Image.extend({
  parseHTML() {
    return [
      { tag: 'img[src]' },
      { tag: 'img[data-img-id]' },
    ]
  },
  addAttributes() {
    return {
      ...this.parent?.(),
      'data-img-id': {
        default: null,
        parseHTML: element => element.getAttribute('data-img-id'),
        renderHTML: attributes => {
          if (!attributes['data-img-id']) return {}
          return { 'data-img-id': attributes['data-img-id'] }
        },
      },
      width: {
        default: 'auto',
        parseHTML: element => element.getAttribute('width') || element.style.width || 'auto',
        renderHTML: attributes => {
          if (!attributes.width || attributes.width === 'auto') return {}
          return { width: attributes.width }
        },
      },
      align: {
        default: 'left',
        parseHTML: element => element.getAttribute('data-align') || 'left',
        renderHTML: attributes => {
          if (!attributes.align || attributes.align === 'left') return {}
          return { 'data-align': attributes.align }
        },
      },
    }
  },
  addNodeView() {
    return VueNodeViewRenderer(ResizableImage)
  },
})

const normalizeImageHtml = (html: string) => {
  if (!html) return html
  if (typeof window === 'undefined' || typeof DOMParser === 'undefined') return html

  try {
    const parser = new DOMParser()
    const doc = parser.parseFromString(html, 'text/html')
    const imgs = doc.querySelectorAll('img')
    imgs.forEach(img => {
      const id = img.getAttribute('data-img-id')
      if (id) {
        const expected = buildFileRedirectUrl(id)
        const current = img.getAttribute('src') || ''
        if (current !== expected) {
          img.setAttribute('src', expected)
        }
      }
    })
    return doc.body.innerHTML
  } catch {
    return html
  }
}

const stripImageSrcInHtml = (html: string) => {
  if (!html) return html
  if (typeof window === 'undefined' || typeof DOMParser === 'undefined') return html

  try {
    const parser = new DOMParser()
    const doc = parser.parseFromString(html, 'text/html')
    const imgs = doc.querySelectorAll('img[data-img-id]')
    imgs.forEach(img => {
      img.removeAttribute('src')
    })
    return doc.body.innerHTML
  } catch {
    return html
  }
}

const tableMenuVisible = ref(false)
const tableMenuX = ref(0)
const tableMenuY = ref(0)
const tableMenuEl = ref<HTMLElement>()

const closeTableMenu = () => {
  tableMenuVisible.value = false
}

const clampTableMenuToViewport = () => {
  nextTick(() => {
    const el = tableMenuEl.value
    if (!el) return
    const rect = el.getBoundingClientRect()
    const padding = 8
    const maxX = window.innerWidth - rect.width - padding
    const maxY = window.innerHeight - rect.height - padding
    tableMenuX.value = Math.max(padding, Math.min(tableMenuX.value, maxX))
    tableMenuY.value = Math.max(padding, Math.min(tableMenuY.value, maxY))
  })
}

const openTableMenu = (event: MouseEvent) => {
  tableMenuX.value = event.clientX
  tableMenuY.value = event.clientY
  tableMenuVisible.value = true
  clampTableMenuToViewport()
}

const withEditor = (callback: (ed: NonNullable<typeof editor.value>) => void) => {
  const ed = editor.value
  if (!ed || props.disabled) return
  callback(ed)
}

const canTable = computed(() => {
  const ed = editor.value
  if (!ed) {
    return {
      addRowBefore: false,
      addRowAfter: false,
      deleteRow: false,
      addColumnBefore: false,
      addColumnAfter: false,
      deleteColumn: false,
      mergeCells: false,
      splitCell: false,
      deleteTable: false,
    }
  }

  return {
    addRowBefore: ed.can().chain().addRowBefore().run(),
    addRowAfter: ed.can().chain().addRowAfter().run(),
    deleteRow: ed.can().chain().deleteRow().run(),
    addColumnBefore: ed.can().chain().addColumnBefore().run(),
    addColumnAfter: ed.can().chain().addColumnAfter().run(),
    deleteColumn: ed.can().chain().deleteColumn().run(),
    mergeCells: ed.can().chain().mergeCells().run(),
    splitCell: ed.can().chain().splitCell().run(),
    deleteTable: ed.can().chain().deleteTable().run(),
  }
})

const execTableAction = (action: string) => {
  withEditor(ed => {
    const chain = ed.chain().focus()
    switch (action) {
      case 'addRowBefore':
        chain.addRowBefore().run()
        break
      case 'addRowAfter':
        chain.addRowAfter().run()
        break
      case 'deleteRow':
        chain.deleteRow().run()
        break
      case 'addColumnBefore':
        chain.addColumnBefore().run()
        break
      case 'addColumnAfter':
        chain.addColumnAfter().run()
        break
      case 'deleteColumn':
        chain.deleteColumn().run()
        break
      case 'mergeCells':
        chain.mergeCells().run()
        break
      case 'splitCell':
        chain.splitCell().run()
        break
      case 'deleteTable':
        chain.deleteTable().run()
        break
    }
  })
  closeTableMenu()
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  placeholder: '请输入内容...',
  disabled: false,
  minHeight: 260,
  allowImage: true,
  uploadConfigId: undefined,
})

const emit = defineEmits<{
  'update:modelValue': [value: string]
  change: [value: string]
}>()

const imageModalVisible = ref(false)
const imageUrl = ref('')
const uploadLoading = ref(false)

const linkModalVisible = ref(false)
const isEditingLink = ref(false)
const linkForm = ref({
  url: '',
  openInNewTab: true,
})

const showFontSizeDropdown = ref(false)
const customFontSize = ref('')

const showTableModal = ref(false)
const tableRows = ref(3)
const tableCols = ref(3)
const hoveredTableSize = ref({ row: 0, col: 0 })

const colors = [
  '#000000', '#262626', '#595959', '#8C8C8C', '#BFBFBF', '#D9D9D9', '#E8E8E8', '#F5F5F5',
  '#FF4D4F', '#FF7875', '#FF9C6E', '#FFA940', '#FFC53D', '#FFD666', '#FFE58F', '#FFF1B8',
  '#52C41A', '#73D13D', '#95DE64', '#B7EB8F', '#D9F7BE', '#BAE637', '#D3F261', '#E8F6A3',
  '#13C2C2', '#36CFC9', '#5CDBD3', '#87E8DE', '#B5F5EC', '#08979C', '#006D75', '#00474F',
  '#2F54EB', '#597EF7', '#85A5FF', '#ADC6FF', '#D6E4FF', '#10239E', '#061178', '#030852',
  '#EB2F96', '#F759AB', '#FF85C0', '#FFC2E8', '#FFDBF5', '#9E1068', '#780650', '#520339',
  '#722ED1', '#9254DE', '#B37FEB', '#D3ADF7', '#EFDBFF', '#531DAB', '#391085', '#22075E',
]

const fontSizes = [
  { label: '12px', value: '12px' },
  { label: '14px', value: '14px' },
  { label: '16px', value: '16px' },
  { label: '18px', value: '18px' },
  { label: '20px', value: '20px' },
  { label: '22px', value: '22px' },
  { label: '24px', value: '24px' },
  { label: '28px', value: '28px' },
  { label: '32px', value: '32px' },
  { label: '36px', value: '36px' },
  { label: '40px', value: '40px' },
]

const FontSizeTextStyle = TextStyle.extend({
  addAttributes() {
    return {
      ...this.parent?.(),
      fontSize: {
        default: null,
        parseHTML: element => element.style.fontSize?.replace(/['\"]+/g, '') || null,
        renderHTML: attributes => {
          if (!attributes.fontSize) {
            return {}
          }
          return { style: `font-size: ${attributes.fontSize}` }
        },
      },
    }
  },
})

const beforeUpload: UploadProps['beforeUpload'] = file => {
  const isImage = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'].includes(file.type)
  if (!isImage) {
    message.error('只能上传 JPG/PNG/GIF/WEBP 格式的图片!')
  }
  const isLt5M = file.size / 1024 / 1024 < 5
  if (!isLt5M) {
    message.error('图片大小不能超过 5MB!')
  }
  return isImage && isLt5M
}

const getEditorValue = () => {
  const instance = editor.value
  if (!instance) {
    return ''
  }
  const html = instance.getHTML()
  if (html === '<p></p>' || html === '') {
    return ''
  }
  return stripImageSrcInHtml(html)
}

const emitContent = () => {
  const value = getEditorValue()
  emit('update:modelValue', value)
  emit('change', value)
}

const handleImageFile = async (file: File) => {
  const ok = beforeUpload(file as any)
  if (!ok) return

  uploadLoading.value = true
  try {
    const res = await uploadSimple(file, props.uploadConfigId)
    if (res.code !== 200 || !res.data) {
      message.error(res.msg || '上传失败')
      return
    }
    if (res.data.fileId !== undefined && res.data.fileId !== null) {
      insertImageById(res.data.fileId)
      message.success('上传成功')
      return
    }
    if (res.data.accessPath) {
      insertImageWithUrl(res.data.accessPath)
      message.success('上传成功')
      return
    }
    message.error(res.msg || '上传失败')
  } catch (error: any) {
    message.error(error?.message || '上传失败')
  } finally {
    uploadLoading.value = false
  }
}

const editor = useEditor({
  content: normalizeImageHtml(props.modelValue || ''),
  editable: !props.disabled,
  extensions: [
    StarterKit.configure({
      // 避免与下面自定义 Dropcursor 重复注册
      dropcursor: false,
    }),
    Underline,
    ImageWithFileId,
    Link.configure({
      openOnClick: false,
    }),
    TextAlign.configure({
      types: ['heading', 'paragraph'],
    }),
    FontSizeTextStyle,
    Color,
    Table.configure({
      resizable: true,
    }),
    TableRow,
    TableCell,
    TableHeader,
    Dropcursor.configure({
      color: '#1890ff',
      width: 2,
    }),
  ],
  onUpdate: () => {
    emitContent()
  },
  editorProps: {
    attributes: {
      class: 'ProseMirror',
    },
    handleDrop: (_view, event, _slice, moved) => {
      if (!moved && event.dataTransfer?.files?.length) {
        const file = event.dataTransfer.files[0]
        if (file.type.startsWith('image/')) {
          void handleImageFile(file)
          return true
        }
      }
      return false
    },
    handlePaste: (_view, event) => {
      const items = event.clipboardData?.items
      if (items) {
        for (let i = 0; i < items.length; i++) {
          const item = items[i]
          if (item.type.startsWith('image/')) {
            const file = item.getAsFile()
            if (file) {
              void handleImageFile(file)
              return true
            }
          }
        }
      }
      return false
    },
    handleClickOn: (_view, _pos, node) => {
      if (node.type.name === 'text') {
        const hasLink = node.marks.some(mark => mark.type.name === 'link')
        if (hasLink) {
          return true
        }
      }
      return false
    },
    handleDOMEvents: {
      contextmenu: (view, event) => {
        if (props.disabled) {
          return false
        }

        const mouseEvent = event as MouseEvent
        const target = mouseEvent.target as HTMLElement | null
        if (!target) {
          return false
        }

        const table = target.closest('table')
        if (!table) {
          return false
        }

        mouseEvent.preventDefault()

        const ed = editor.value
        if (ed) {
          const selection: any = ed.state.selection as any
          const isCellSelection = Boolean(selection && selection.$anchorCell && selection.$headCell)
          if (!isCellSelection) {
            const coords = view.posAtCoords({ left: mouseEvent.clientX, top: mouseEvent.clientY })
            if (coords?.pos) {
              ed.commands.setTextSelection(coords.pos)
            }
          }
        }

        openTableMenu(mouseEvent)
        return true
      },
    },
  },
})

watch(
  () => tableMenuVisible.value,
  visible => {
    if (!visible) return
    clampTableMenuToViewport()
  }
)

onMounted(() => {
  window.addEventListener('scroll', closeTableMenu, true)
})

onBeforeUnmount(() => {
  window.removeEventListener('scroll', closeTableMenu, true)
})

watch(
  () => props.modelValue,
  newValue => {
    const instance = editor.value
    if (!instance) return
    const nextRaw = newValue || ''
    const currentRaw = getEditorValue()
    if (currentRaw === nextRaw) {
      return
    }

    const next = normalizeImageHtml(nextRaw)
    if (instance.getHTML() !== next) {
      instance.commands.setContent(next, false)
    }
  }
)

watch(
  () => props.disabled,
  value => {
    editor.value?.setEditable(!value)
  }
)

onBeforeUnmount(() => {
  editor.value?.destroy()
})

const setHeading = (level: number) => {
  editor.value?.chain().focus().toggleHeading({ level: level as any }).run()
}

const setBold = () => {
  editor.value?.chain().focus().toggleBold().run()
}

const setItalic = () => {
  editor.value?.chain().focus().toggleItalic().run()
}

const setUnderline = () => {
  editor.value?.chain().focus().toggleUnderline().run()
}

const setStrike = () => {
  editor.value?.chain().focus().toggleStrike().run()
}

const setBulletList = () => {
  editor.value?.chain().focus().toggleBulletList().run()
}

const setOrderedList = () => {
  editor.value?.chain().focus().toggleOrderedList().run()
}

const setTextAlign = (align: 'left' | 'center' | 'right') => {
  const instance = editor.value
  if (!instance) return

  if (instance.isActive('image')) {
    instance.chain().focus().updateAttributes('image', { align }).run()
    return
  }

  instance.chain().focus().setTextAlign(align).run()
}

const isImageAlignActive = (align: 'left' | 'center' | 'right') => {
  const instance = editor.value
  if (!instance || !instance.isActive('image')) return false
  return (instance.getAttributes('image')?.align || 'left') === align
}

const undo = () => {
  editor.value?.chain().focus().undo().run()
}

const redo = () => {
  editor.value?.chain().focus().redo().run()
}

const getCurrentColor = () => {
  if (!editor.value) return null
  const attrs: any = editor.value.getAttributes('textStyle')
  return attrs.color || null
}

const setColor = (color: string) => {
  editor.value?.chain().focus().setColor(color).run()
}

const clearColor = () => {
  editor.value?.chain().focus().unsetColor().run()
}

const getCurrentFontSize = () => {
  if (!editor.value) return null
  const attrs: any = editor.value.getAttributes('textStyle')
  return attrs.fontSize || null
}

const setFontSize = (size: string) => {
  editor.value?.chain().focus().setMark('textStyle', { fontSize: size }).run()
  showFontSizeDropdown.value = false
  customFontSize.value = ''
}

const setCustomFontSize = () => {
  const size = customFontSize.value.trim()
  if (!size) return
  const sizeWithUnit = /^\d+$/.test(size) ? `${size}px` : size
  editor.value?.chain().focus().setMark('textStyle', { fontSize: sizeWithUnit }).run()
  showFontSizeDropdown.value = false
  customFontSize.value = ''
}

const unsetFontSize = () => {
  editor.value?.chain().focus().updateAttributes('textStyle', { fontSize: null }).run()
  showFontSizeDropdown.value = false
}

const insertTable = (rows: number, cols: number) => {
  editor.value?.chain().focus().insertTable({ rows, cols, withHeaderRow: true }).run()
  showTableModal.value = false
  tableRows.value = 3
  tableCols.value = 3
}

const insertTableFromModal = () => {
  insertTable(tableRows.value, tableCols.value)
}

const insertImageById = (id: string | number) => {
  if (!editor.value) return
  const idStr = String(id)
  editor.value
    .chain()
    .focus()
    .setImage({
      src: buildFileRedirectUrl(idStr),
      'data-img-id': idStr,
    } as any)
    .run()
  imageUrl.value = ''
}

const insertImageWithUrl = (url: string) => {
  if (!url || !editor.value) return
  editor.value.chain().focus().setImage({ src: url } as any).run()
  imageUrl.value = ''
}

const showImageModal = () => {
  if (props.disabled) return
  imageModalVisible.value = true
  imageUrl.value = ''
}

const insertImageByInput = () => {
  const input = imageUrl.value.trim()
  if (!input) return
  if (/^\d+$/.test(input)) {
    insertImageById(input)
  } else {
    insertImageWithUrl(input)
  }
  imageModalVisible.value = false
}

const customUpload = async (options: any) => {
  const { file, onSuccess, onError } = options
  uploadLoading.value = true
  try {
    const res = await uploadSimple(file as File, props.uploadConfigId)
    if (res.code !== 200 || !res.data) {
      throw new Error(res.msg || '上传失败')
    }

    if (res.data.fileId !== undefined && res.data.fileId !== null) {
      insertImageById(res.data.fileId)
    } else if (res.data.accessPath) {
      insertImageWithUrl(res.data.accessPath)
    } else {
      throw new Error(res.msg || '上传失败')
    }
    onSuccess?.(res.data)
    message.success('上传成功')
    imageModalVisible.value = false
  } catch (error: any) {
    onError?.(error)
    message.error(error?.message || '上传失败')
  } finally {
    uploadLoading.value = false
  }
}

const openLinkModal = () => {
  if (props.disabled || !editor.value) return
  const attrs: any = editor.value.getAttributes('link')
  if (attrs.href) {
    isEditingLink.value = true
    linkForm.value.url = attrs.href
    linkForm.value.openInNewTab = attrs.target === '_blank'
  } else {
    isEditingLink.value = false
    linkForm.value.url = ''
    linkForm.value.openInNewTab = true
  }
  linkModalVisible.value = true
}

const saveLink = () => {
  if (!editor.value) return
  const url = linkForm.value.url.trim()
  if (!url) return

  const attrs: any = { href: url }
  if (linkForm.value.openInNewTab) {
    attrs.target = '_blank'
    attrs.rel = 'noopener noreferrer'
  } else {
    attrs.target = null
    attrs.rel = null
  }

  if (isEditingLink.value) {
    editor.value.chain().focus().extendMarkRange('link').updateAttributes('link', attrs).run()
  } else {
    editor.value.chain().focus().setLink(attrs).run()
  }
  linkModalVisible.value = false
}

const removeLink = () => {
  editor.value?.chain().focus().unsetLink().run()
  linkModalVisible.value = false
}

const clearContent = () => {
  if (props.disabled) return
  editor.value?.commands.clearContent()
  emitContent()
}

const headingValue = computed(() => {
  const instance = editor.value
  if (!instance) return 'p'
  if (instance.isActive('heading', { level: 1 })) return 'h1'
  if (instance.isActive('heading', { level: 2 })) return 'h2'
  if (instance.isActive('heading', { level: 3 })) return 'h3'
  return 'p'
})

const handleHeadingChange = (val: string) => {
  if (!editor.value || props.disabled) return
  if (val === 'p') {
    editor.value.chain().focus().setParagraph().run()
    return
  }
  setHeading(parseInt(val.replace('h', ''), 10))
}
</script>

<template>
  <div class="rich-editor" :class="{ 'is-disabled': disabled }">
    <div v-if="editor" class="toolbar">
      <a-space :size="4" wrap>
        <a-select
          size="small"
          style="width: 90px"
          :value="headingValue"
          :disabled="disabled"
          @change="handleHeadingChange"
        >
          <a-select-option value="p">正文</a-select-option>
          <a-select-option value="h1">标题 1</a-select-option>
          <a-select-option value="h2">标题 2</a-select-option>
          <a-select-option value="h3">标题 3</a-select-option>
        </a-select>

        <a-divider type="vertical" />

        <a-tooltip title="字体大小">
          <a-dropdown :trigger="['click']" placement="bottom" v-model:open="showFontSizeDropdown" :disabled="disabled">
            <a-button size="small" style="min-width: 60px; padding: 0 8px;">
              <span style="font-size: 12px;">{{ getCurrentFontSize() || '字号' }}</span>
              <DownOutlined style="font-size: 10px; margin-left: 4px;" />
            </a-button>
            <template #overlay>
              <div class="font-size-dropdown-panel" @click.stop>
                <div class="font-size-list">
                  <div
                    v-for="size in fontSizes"
                    :key="size.value"
                    class="font-size-item"
                    :class="{ 'font-size-item-active': getCurrentFontSize() === size.value }"
                    :style="{ fontSize: size.value }"
                    @click="setFontSize(size.value)"
                  >
                    {{ size.label }}
                    <span v-if="getCurrentFontSize() === size.value" class="font-size-check">✓</span>
                  </div>
                </div>
                <a-divider style="margin: 8px 0" />
                <div class="font-size-custom">
                  <a-input v-model:value="customFontSize" placeholder="自定义字号 (如: 18px)" size="small" @pressEnter="setCustomFontSize">
                    <template #suffix>
                      <a-button type="primary" size="small" @click="setCustomFontSize">确定</a-button>
                    </template>
                  </a-input>
                </div>
                <a-divider style="margin: 8px 0" />
                <div class="font-size-actions">
                  <a-button type="text" size="small" block @click="unsetFontSize">清除字号</a-button>
                </div>
              </div>
            </template>
          </a-dropdown>
        </a-tooltip>

        <a-divider type="vertical" />

        <a-tooltip title="粗体">
          <a-button size="small" :type="editor.isActive('bold') ? 'primary' : 'default'" :disabled="disabled" @click="setBold"><BoldOutlined /></a-button>
        </a-tooltip>
        <a-tooltip title="斜体">
          <a-button size="small" :type="editor.isActive('italic') ? 'primary' : 'default'" :disabled="disabled" @click="setItalic"><ItalicOutlined /></a-button>
        </a-tooltip>
        <a-tooltip title="下划线">
          <a-button size="small" :type="editor.isActive('underline') ? 'primary' : 'default'" :disabled="disabled" @click="setUnderline"><UnderlineOutlined /></a-button>
        </a-tooltip>
        <a-tooltip title="删除线">
          <a-button size="small" :type="editor.isActive('strike') ? 'primary' : 'default'" :disabled="disabled" @click="setStrike"><StrikethroughOutlined /></a-button>
        </a-tooltip>

        <a-divider type="vertical" />

        <a-tooltip title="左对齐">
          <a-button size="small" :type="(editor.isActive({ textAlign: 'left' }) || isImageAlignActive('left')) ? 'primary' : 'default'" :disabled="disabled" @click="setTextAlign('left')"><AlignLeftOutlined /></a-button>
        </a-tooltip>
        <a-tooltip title="居中">
          <a-button size="small" :type="(editor.isActive({ textAlign: 'center' }) || isImageAlignActive('center')) ? 'primary' : 'default'" :disabled="disabled" @click="setTextAlign('center')"><AlignCenterOutlined /></a-button>
        </a-tooltip>
        <a-tooltip title="右对齐">
          <a-button size="small" :type="(editor.isActive({ textAlign: 'right' }) || isImageAlignActive('right')) ? 'primary' : 'default'" :disabled="disabled" @click="setTextAlign('right')"><AlignRightOutlined /></a-button>
        </a-tooltip>

        <a-divider type="vertical" />

        <a-tooltip title="无序列表">
          <a-button size="small" :type="editor.isActive('bulletList') ? 'primary' : 'default'" :disabled="disabled" @click="setBulletList"><UnorderedListOutlined /></a-button>
        </a-tooltip>
        <a-tooltip title="有序列表">
          <a-button size="small" :type="editor.isActive('orderedList') ? 'primary' : 'default'" :disabled="disabled" @click="setOrderedList"><OrderedListOutlined /></a-button>
        </a-tooltip>

        <a-divider type="vertical" />

        <a-tooltip :title="editor.isActive('link') ? '编辑链接' : '插入链接'">
          <a-button size="small" :type="editor.isActive('link') ? 'primary' : 'default'" :disabled="disabled" @click="openLinkModal"><LinkOutlined /></a-button>
        </a-tooltip>
        <a-tooltip v-if="allowImage" title="插入图片">
          <a-button size="small" :disabled="disabled" @click="showImageModal"><PictureOutlined /></a-button>
        </a-tooltip>

        <a-divider type="vertical" />

        <a-tooltip title="撤销">
          <a-button size="small" :disabled="disabled || !editor.can().undo()" @click="undo"><UndoOutlined /></a-button>
        </a-tooltip>
        <a-tooltip title="重做">
          <a-button size="small" :disabled="disabled || !editor.can().redo()" @click="redo"><RedoOutlined /></a-button>
        </a-tooltip>

        <a-divider type="vertical" />

        <a-tooltip title="文字颜色">
          <a-dropdown :trigger="['click']" placement="bottom" :disabled="disabled">
            <a-button size="small">
              <div class="color-trigger"></div>
            </a-button>
            <template #overlay>
              <div class="color-picker-panel" @click.stop>
                <div class="color-section">
                  <div class="color-section-title">主题色</div>
                  <div class="color-grid">
                    <div
                      v-for="color in colors"
                      :key="color"
                      class="color-item"
                      :class="{ 'color-active': getCurrentColor() === color }"
                      :style="{ backgroundColor: color }"
                      @click="setColor(color)"
                    >
                      <span v-if="getCurrentColor() === color" class="color-check">✓</span>
                    </div>
                  </div>
                </div>
                <a-divider style="margin: 8px 0" />
                <div class="color-actions">
                  <a-button type="text" size="small" @click="clearColor">清除颜色</a-button>
                </div>
              </div>
            </template>
          </a-dropdown>
        </a-tooltip>

        <a-divider type="vertical" />

        <a-tooltip title="插入表格">
          <a-dropdown :trigger="['click']" placement="bottom" :disabled="disabled">
            <a-button size="small" :type="editor.isActive('table') ? 'primary' : 'default'">
              <TableOutlined />
            </a-button>
            <template #overlay>
              <div class="table-dropdown-panel" @click.stop>
                <div class="table-insert-section">
                  <div class="table-section-title">快速插入</div>
                  <div class="table-grid-selector">
                    <div v-for="row in 6" :key="`row-${row}`" class="table-grid-row">
                      <div
                        v-for="col in 8"
                        :key="`cell-${row}-${col}`"
                        class="table-grid-cell"
                        :class="{ 'table-grid-cell-active': hoveredTableSize.row >= row && hoveredTableSize.col >= col }"
                        @mouseenter="hoveredTableSize = { row, col }"
                        @mouseleave="hoveredTableSize = { row: 0, col: 0 }"
                        @click="insertTable(row, col)"
                      />
                    </div>
                  </div>
                  <div class="table-size-hint">
                    {{ hoveredTableSize.row > 0 ? `${hoveredTableSize.row} × ${hoveredTableSize.col}` : '移动鼠标选择表格大小' }}
                  </div>
                </div>
                <a-divider style="margin: 8px 0" />
                <a-button type="dashed" block size="small" @click="showTableModal = true">更多设置</a-button>
              </div>
            </template>
          </a-dropdown>
        </a-tooltip>

        <a-divider type="vertical" />

        <a-button size="small" danger :disabled="disabled" @click="clearContent">清空</a-button>
      </a-space>
    </div>

    <div class="editor-wrapper" :style="{ minHeight: `${minHeight}px` }">
      <EditorContent v-if="editor" :editor="editor" class="editor-content" />
      <div v-else class="editor-loading">Loading...</div>
    </div>

    <a-modal v-model:open="imageModalVisible" title="插入图片" :footer="null" width="480px">
      <a-tabs>
        <a-tab-pane key="upload" tab="本地上传">
          <a-upload-dragger
            :custom-request="customUpload"
            :before-upload="beforeUpload"
            :show-upload-list="false"
            accept="image/*"
            :disabled="uploadLoading"
          >
            <p class="ant-upload-drag-icon"><UploadOutlined /></p>
            <p class="ant-upload-text">点击或拖拽文件到此处上传</p>
            <p class="ant-upload-hint">支持 JPG/PNG/GIF/WEBP 格式，单文件不超过 5MB</p>
          </a-upload-dragger>
        </a-tab-pane>
        <a-tab-pane key="url" tab="网络图片">
          <a-space direction="vertical" style="width: 100%">
            <a-input v-model:value="imageUrl" placeholder="请输入图片URL地址" size="large" />
            <a-button type="primary" block size="large" :disabled="!imageUrl.trim()" @click="insertImageByInput">插入图片</a-button>
          </a-space>
        </a-tab-pane>
      </a-tabs>
    </a-modal>

    <a-modal v-model:open="linkModalVisible" :title="isEditingLink ? '编辑链接' : '插入链接'" :footer="null" width="420px">
      <a-space direction="vertical" style="width: 100%">
        <div class="link-form-item">
          <label class="link-form-label">链接地址：</label>
          <a-input v-model:value="linkForm.url" placeholder="https://example.com" size="large" />
        </div>
        <div class="link-form-item">
          <a-checkbox v-model:checked="linkForm.openInNewTab">在新窗口打开链接</a-checkbox>
        </div>
        <div class="link-form-actions">
          <a-button @click="linkModalVisible = false">取消</a-button>
          <a-button v-if="isEditingLink" danger @click="removeLink">移除链接</a-button>
          <a-button type="primary" :disabled="!linkForm.url.trim()" @click="saveLink">{{ isEditingLink ? '保存' : '插入' }}</a-button>
        </div>
      </a-space>
    </a-modal>

    <a-modal v-model:open="showTableModal" title="插入表格" :footer="null" width="360px">
      <a-space direction="vertical" style="width: 100%">
        <div class="table-modal-row">
          <span class="table-modal-label">行数:</span>
          <a-input-number v-model:value="tableRows" :min="1" :max="20" style="width: 120px" />
        </div>
        <div class="table-modal-row">
          <span class="table-modal-label">列数:</span>
          <a-input-number v-model:value="tableCols" :min="1" :max="10" style="width: 120px" />
        </div>
        <div class="table-modal-actions">
          <a-button @click="showTableModal = false">取消</a-button>
          <a-button type="primary" @click="insertTableFromModal">插入</a-button>
        </div>
      </a-space>
    </a-modal>

    <Teleport to="body">
      <div v-if="tableMenuVisible" class="table-context-overlay" @mousedown="closeTableMenu">
        <div
          ref="tableMenuEl"
          class="table-context-menu"
          :style="{ left: `${tableMenuX}px`, top: `${tableMenuY}px` }"
          @mousedown.stop
        >
          <div class="table-context-group">
            <div class="table-context-group-title">行</div>
            <a-button type="text" size="small" :disabled="!canTable.addRowBefore" @click="execTableAction('addRowBefore')">上方插入行</a-button>
            <a-button type="text" size="small" :disabled="!canTable.addRowAfter" @click="execTableAction('addRowAfter')">下方插入行</a-button>
            <a-button type="text" size="small" danger :disabled="!canTable.deleteRow" @click="execTableAction('deleteRow')">删除行</a-button>
          </div>
          <div class="table-context-divider"></div>
          <div class="table-context-group">
            <div class="table-context-group-title">列</div>
            <a-button type="text" size="small" :disabled="!canTable.addColumnBefore" @click="execTableAction('addColumnBefore')">左侧插入列</a-button>
            <a-button type="text" size="small" :disabled="!canTable.addColumnAfter" @click="execTableAction('addColumnAfter')">右侧插入列</a-button>
            <a-button type="text" size="small" danger :disabled="!canTable.deleteColumn" @click="execTableAction('deleteColumn')">删除列</a-button>
          </div>
          <div class="table-context-divider"></div>
          <div class="table-context-group">
            <div class="table-context-group-title">单元格</div>
            <a-button type="text" size="small" :disabled="!canTable.mergeCells" @click="execTableAction('mergeCells')">合并单元格</a-button>
            <a-button type="text" size="small" :disabled="!canTable.splitCell" @click="execTableAction('splitCell')">拆分单元格</a-button>
          </div>
          <div class="table-context-divider"></div>
          <div class="table-context-group">
            <a-button type="text" size="small" danger :disabled="!canTable.deleteTable" @click="execTableAction('deleteTable')">删除表格</a-button>
          </div>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<style scoped>
.rich-editor {
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  overflow: hidden;
}

.toolbar {
  padding: 8px 12px;
  background: #fafafa;
  border-bottom: 1px solid #d9d9d9;
}

.editor-wrapper {
  width: 100%;
}

.editor-content {
  min-height: 260px;
  padding: 12px;
}

.editor-content :deep(.ProseMirror) {
  outline: none;
  min-height: inherit;
}

.editor-content :deep(.ProseMirror p) {
  margin: 0 0 8px 0;
}

.editor-content :deep(.ProseMirror h1) {
  font-size: 24px;
  font-weight: 600;
  margin: 0 0 12px 0;
}

.editor-content :deep(.ProseMirror h2) {
  font-size: 20px;
  font-weight: 600;
  margin: 0 0 10px 0;
}

.editor-content :deep(.ProseMirror h3) {
  font-size: 18px;
  font-weight: 600;
  margin: 0 0 8px 0;
}

.editor-content :deep(.ProseMirror ul),
.editor-content :deep(.ProseMirror ol) {
  padding-left: 20px;
  margin: 0 0 8px 0;
}

.editor-content :deep(.ProseMirror blockquote) {
  border-left: 3px solid #d9d9d9;
  padding-left: 12px;
  margin: 0 0 8px 0;
  color: #666;
}

.editor-content :deep(.ProseMirror img) {
  max-width: 100%;
  height: auto;
}

.editor-content :deep(.ProseMirror a) {
  color: #1890ff;
  text-decoration: underline;
}

.editor-content :deep(.ProseMirror .tableWrapper) {
  margin: 14px 0;
  overflow-x: auto;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  background: #fff;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.8);
}

.editor-content :deep(.ProseMirror table) {
  width: 100%; 
  border-collapse: separate;
  border-spacing: 0;
  table-layout: fixed;
  margin: 0;
}

.editor-content :deep(.ProseMirror th),
.editor-content :deep(.ProseMirror td) {
  position: relative;
  padding: 12px 14px;
  border-right: 1px solid #e5e7eb;
  border-bottom: 1px solid #e5e7eb;
  vertical-align: top;
  text-align: left;
  line-height: 1.7;
  word-break: break-word;
  background: #fff;
  transition: background-color 0.2s ease;
}

.editor-content :deep(.ProseMirror th) {
  font-weight: 600;
  color: #111827;
  background: linear-gradient(180deg, #f8fafc 0%, #f1f5f9 100%);
}

.editor-content :deep(.ProseMirror tr:last-child td) {
  border-bottom: none;
}

.editor-content :deep(.ProseMirror tr > :last-child) {
  border-right: none;
}

.editor-content :deep(.ProseMirror table p:last-child) {
  margin-bottom: 0;
}

.editor-content :deep(.ProseMirror td:hover),
.editor-content :deep(.ProseMirror th:hover) {
  background: #f8fbff;
}

.editor-content :deep(.ProseMirror .selectedCell::after) {
  content: '';
  position: absolute;
  inset: 0;
  pointer-events: none;
  background: rgba(24, 144, 255, 0.08);
  box-shadow: inset 0 0 0 2px rgba(24, 144, 255, 0.28);
}

.editor-content :deep(.ProseMirror .column-resize-handle) {
  position: absolute;
  top: 0;
  right: -2px;
  bottom: 0;
  width: 4px;
  background: #1890ff;
  opacity: 0;
  transition: opacity 0.2s ease;
}

.editor-content :deep(.ProseMirror.resize-cursor) {
  cursor: col-resize;
}

.editor-content :deep(.ProseMirror.resize-cursor .column-resize-handle),
.editor-content :deep(.ProseMirror th:hover .column-resize-handle),
.editor-content :deep(.ProseMirror td:hover .column-resize-handle) {
  opacity: 1;
}

.table-context-overlay {
  position: fixed;
  inset: 0;
  z-index: 9999;
}

.table-context-menu {
  position: fixed;
  min-width: 180px;
  padding: 10px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(8px);
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 10px;
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.18);
}

.table-context-group {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.table-context-group-title {
  padding: 2px 8px;
  font-size: 12px;
  color: #6b7280;
}

.table-context-divider {
  height: 1px;
  margin: 8px 0;
  background: rgba(0, 0, 0, 0.06);
}

.table-context-menu :deep(.ant-btn) {
  justify-content: flex-start;
  padding: 0 8px;
  height: 30px;
  border-radius: 8px;
}

.table-context-menu :deep(.ant-btn:not(.ant-btn-dangerous):hover:not(:disabled)) {
  background: rgba(24, 144, 255, 0.08);
}

.editor-content :deep(.ProseMirror p[data-text-align='center']) {
  text-align: center;
}

.editor-content :deep(.ProseMirror p[data-text-align='right']) {
  text-align: right;
}

.editor-content :deep(.ProseMirror p[data-text-align='left']) {
  text-align: left;
}

.editor-loading {
  padding: 16px;
  color: #9ca3af;
}

.color-trigger {
  margin-top: 2px;
  display: inline-block;
  width: 16px;
  height: 16px;
  background: linear-gradient(135deg, #ff4d4f, #52c41a, #2f54eb);
  border-radius: 2px;
}

.color-picker-panel {
  background: #fff;
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  padding: 12px;
  min-width: 240px;
}

.color-section-title {
  font-size: 12px;
  color: #999;
  margin-bottom: 8px;
  font-weight: 500;
}

.color-grid {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  gap: 6px;
}

.color-item {
  width: 22px;
  height: 22px;
  border-radius: 4px;
  cursor: pointer;
  position: relative;
  transition: all 0.2s ease;
  border: 1px solid transparent;
}

.color-item:hover {
  transform: scale(1.15);
  z-index: 1;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
}

.color-item.color-active {
  box-shadow: 0 0 0 2px #1890ff;
}

.color-check {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 12px;
  font-weight: bold;
  color: #fff;
  text-shadow: 0 0 2px rgba(0, 0, 0, 0.5);
  pointer-events: none;
}

.color-actions {
  display: flex;
  justify-content: center;
}

.font-size-dropdown-panel {
  background: #fff;
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  padding: 8px;
  min-width: 160px;
}

.font-size-list {
  max-height: 240px;
  overflow-y: auto;
}

.font-size-item {
  padding: 6px 12px;
  cursor: pointer;
  border-radius: 4px;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  justify-content: space-between;
  line-height: 1.4;
}

.font-size-item:hover {
  background: #f5f5f5;
}

.font-size-item-active {
  background: #e6f7ff;
  color: #1890ff;
}

.font-size-check {
  font-size: 12px;
  font-weight: bold;
}

.font-size-actions {
  display: flex;
  justify-content: center;
}

.link-form-item {
  margin-bottom: 16px;
}

.link-form-label {
  display: block;
  margin-bottom: 8px;
  font-size: 14px;
  color: #333;
  font-weight: 500;
}

.link-form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}

.table-dropdown-panel {
  background: #fff;
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  padding: 12px;
  min-width: 200px;
}

.table-insert-section {
  padding: 8px;
}

.table-section-title {
  font-size: 12px;
  color: #666;
  margin-bottom: 8px;
  font-weight: 500;
}

.table-grid-selector {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.table-grid-row {
  display: flex;
  gap: 2px;
}

.table-grid-cell {
  width: 20px;
  height: 20px;
  border: 1px solid #e8e8e8;
  background: #fff;
  cursor: pointer;
  transition: all 0.15s;
  border-radius: 1px;
}

.table-grid-cell:hover,
.table-grid-cell-active {
  background: #1890ff;
  border-color: #1890ff;
}

.table-size-hint {
  text-align: center;
  margin-top: 8px;
  font-size: 12px;
  color: #666;
  height: 18px;
}

.table-modal-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

.table-modal-label {
  width: 60px;
  text-align: right;
}

.table-modal-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
  gap: 8px;
}

html.dark .rich-editor {
  border-color: #334155;
}

html.dark .toolbar {
  background: #0f172a;
  border-bottom-color: #334155;
}

html.dark .editor-content :deep(.ProseMirror) {
  color: #e2e8f0;
}

html.dark .editor-content :deep(.ProseMirror .tableWrapper) {
  background: #0f172a;
  border-color: #334155;
  box-shadow: inset 0 1px 0 rgba(148, 163, 184, 0.06);
}

html.dark .editor-content :deep(.ProseMirror th),
html.dark .editor-content :deep(.ProseMirror td) {
  background: #0f172a;
  border-right-color: #334155;
  border-bottom-color: #334155;
  color: #e2e8f0;
}

html.dark .editor-content :deep(.ProseMirror th) {
  background: linear-gradient(180deg, #1e293b 0%, #172033 100%);
  color: #f8fafc;
}

html.dark .editor-content :deep(.ProseMirror td:hover),
html.dark .editor-content :deep(.ProseMirror th:hover) {
  background: #162033;
}

html.dark .editor-content :deep(.ProseMirror .selectedCell::after) {
  background: rgba(56, 189, 248, 0.12);
  box-shadow: inset 0 0 0 2px rgba(56, 189, 248, 0.35);
}

html.dark .table-context-menu {
  background: rgba(30, 41, 59, 0.92);
  border-color: rgba(148, 163, 184, 0.18);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.5);
}

html.dark .table-context-group-title {
  color: #94a3b8;
}

html.dark .table-context-divider {
  background: rgba(148, 163, 184, 0.16);
}

html.dark .table-context-menu :deep(.ant-btn:not(.ant-btn-dangerous):hover:not(:disabled)) {
  background: rgba(56, 189, 248, 0.12);
}

html.dark .color-picker-panel,
html.dark .font-size-dropdown-panel,
html.dark .table-dropdown-panel {
  background: #1e293b;
  border-color: #334155;
}

html.dark .font-size-item:hover {
  background: #334155;
}

html.dark .table-grid-cell {
  background: #1e293b;
  border-color: #334155;
}

html.dark .table-section-title,
html.dark .table-size-hint,
html.dark .color-section-title {
  color: #94a3b8;
}
</style>
