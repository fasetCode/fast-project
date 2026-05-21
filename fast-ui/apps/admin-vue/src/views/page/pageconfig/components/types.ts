// ─── Prop Schema ─────────────────────────────────────────────────────────────

export type PropType = 'text' | 'textarea' | 'number' | 'boolean' | 'color' | 'select' | 'image' | 'richtext'

export interface PropOption {
  label: string
  value: any
}

export interface PropSchema {
  key: string
  label: string
  type: PropType
  defaultValue?: any
  options?: PropOption[]
  min?: number
  max?: number
  step?: number
  placeholder?: string
}

// ─── Component Definition ────────────────────────────────────────────────────

export type ComponentCategory = 'layout' | 'basic' | 'media' | 'specific'

export interface ComponentDef {
  code: string
  title: string
  icon?: string         // file ID (for API specific components)
  iconEmoji?: string    // emoji fallback icon
  category: ComponentCategory
  propSchema: PropSchema[]
  defaultProps: Record<string, any>
  canHaveChildren?: boolean
}

// ─── Canvas Node ─────────────────────────────────────────────────────────────

export interface CanvasNode {
  id: string
  type: string          // ComponentDef.code
  label: string         // display name
  props: Record<string, any>
  css?: Record<string, string>  // inline style overrides
  children?: CanvasNode[]
}

// ─── Page Schema (serialised → PageConfig.config) ────────────────────────────

export interface PageSchema {
  version: '1'
  nodes: CanvasNode[]
}

// ─── Drag Transfer Keys ──────────────────────────────────────────────────────

export const DRAG_KEY_COMPONENT = 'application/x-lc-component'
export const DRAG_KEY_NODE      = 'application/x-lc-node'

// ─── Utility helpers ─────────────────────────────────────────────────────────

export const genNodeId = (): string =>
  `${Date.now()}-${Math.random().toString(36).slice(2, 7)}`

export const createNode = (def: ComponentDef): CanvasNode => {
  const node: CanvasNode = {
    id: genNodeId(),
    type: def.code,
    label: def.title,
    props: { ...def.defaultProps },
    children: def.canHaveChildren ? [] : undefined,
  }
  // 预初始化固定区域容器
  const divArea = (label: string, extra?: Record<string, any>): CanvasNode => ({
    id: genNodeId(), type: 'lc-div', label,
    props: { display: 'flex', flexDirection: 'column', gap: 0, padding: 8, minHeight: 40, bgColor: 'transparent', borderRadius: 0, flexWrap: false, alignItems: 'stretch', justifyContent: 'flex-start', ...extra },
    children: [],
  })
  if (def.code === 'lc-holy-grail') {
    node.children = [
      divArea('头部',   { minHeight: 60, bgColor: '#fafafa' }),
      divArea('左栏',   { minHeight: 120 }),
      divArea('主内容', { minHeight: 120 }),
      divArea('右栏',   { minHeight: 120 }),
      divArea('底部',   { minHeight: 60, bgColor: '#fafafa' }),
    ]
  } else if (def.code === 'lc-sticky-footer') {
    node.children = [
      divArea('主内容', { minHeight: 200 }),
      divArea('Footer', { minHeight: 60, bgColor: '#f5f5f5' }),
    ]
  }
  return node
}

export const findComponentDef = (code: string): ComponentDef | undefined =>
  ALL_BUILTIN.find(c => c.code === code)

// ─── Built-in Component Registry ─────────────────────────────────────────────

export const LAYOUT_COMPONENTS: ComponentDef[] = [
  {
    code: 'lc-grid',
    title: '栅格布局',
    iconEmoji: '▦',
    category: 'layout',
    canHaveChildren: true,
    propSchema: [
      { key: 'columns',  label: '列数',   type: 'number',  defaultValue: 2,  min: 1, max: 6 },
      { key: 'gap',      label: '间距',   type: 'number',  defaultValue: 12, min: 0, max: 64 },
      { key: 'padding',  label: '内边距', type: 'number',  defaultValue: 0,  min: 0, max: 64 },
      { key: 'bgColor',  label: '背景色', type: 'color',   defaultValue: 'transparent' },
    ],
    defaultProps: { columns: 2, gap: 12, padding: 0, bgColor: 'transparent' },
  },
  {
    code: 'lc-flex',
    title: '弹性容器',
    iconEmoji: '⇔',
    category: 'layout',
    canHaveChildren: true,
    propSchema: [
      {
        key: 'direction', label: '方向', type: 'select', defaultValue: 'row',
        options: [{ label: '水平', value: 'row' }, { label: '垂直', value: 'column' }],
      },
      { key: 'gap',     label: '间距',   type: 'number',  defaultValue: 12, min: 0, max: 64 },
      { key: 'wrap',    label: '换行',   type: 'boolean', defaultValue: true },
      {
        key: 'align', label: '交叉轴对齐', type: 'select', defaultValue: 'flex-start',
        options: [
          { label: '顶部/左侧', value: 'flex-start' },
          { label: '居中',     value: 'center' },
          { label: '底部/右侧', value: 'flex-end' },
          { label: '拉伸',     value: 'stretch' },
        ],
      },
      { key: 'padding', label: '内边距', type: 'number',  defaultValue: 0, min: 0, max: 64 },
      { key: 'bgColor', label: '背景色', type: 'color',   defaultValue: 'transparent' },
    ],
    defaultProps: { direction: 'row', gap: 12, wrap: true, align: 'flex-start', padding: 0, bgColor: 'transparent' },
  },
  {
    code: 'lc-card',
    title: '卡片',
    iconEmoji: '▤',
    category: 'layout',
    canHaveChildren: true,
    propSchema: [
      { key: 'title',        label: '卡片标题', type: 'text',    defaultValue: '卡片标题', placeholder: '留空则不显示标题栏' },
      { key: 'bordered',     label: '显示边框', type: 'boolean', defaultValue: true },
      { key: 'shadow',       label: '显示阴影', type: 'boolean', defaultValue: false },
      { key: 'padding',      label: '内边距',   type: 'number',  defaultValue: 16, min: 0, max: 64 },
      { key: 'bgColor',      label: '背景色',   type: 'color',   defaultValue: '#ffffff' },
      { key: 'borderRadius', label: '圆角',     type: 'number',  defaultValue: 8,  min: 0, max: 32 },
    ],
    defaultProps: { title: '卡片标题', bordered: true, shadow: false, padding: 16, bgColor: '#ffffff', borderRadius: 8 },
  },
  {
    code: 'lc-divider',
    title: '分割线',
    iconEmoji: '—',
    category: 'layout',
    propSchema: [
      { key: 'text',   label: '分割文字', type: 'text',   defaultValue: '',        placeholder: '留空则为纯线' },
      { key: 'color',  label: '线条颜色', type: 'color',  defaultValue: '#e8e8e8' },
      { key: 'margin', label: '上下间距', type: 'number', defaultValue: 16, min: 0, max: 64 },
    ],
    defaultProps: { text: '', color: '#e8e8e8', margin: 16 },
  },
  {
    code: 'lc-spacer',
    title: '间距',
    iconEmoji: '↕',
    category: 'layout',
    propSchema: [
      { key: 'height', label: '高度(px)', type: 'number', defaultValue: 24, min: 4, max: 400 },
    ],
    defaultProps: { height: 24 },
  },
  // ─── 进阶布局 ───
  {
    code: 'lc-div',
    title: '自定义 Div',
    iconEmoji: '◻',
    category: 'layout',
    canHaveChildren: true,
    propSchema: [
      {
        key: 'display', label: '布局方式', type: 'select', defaultValue: 'block',
        options: [
          { label: '块级 block',  value: 'block'  },
          { label: '弹性 flex',   value: 'flex'   },
          { label: '网格 grid',   value: 'grid'   },
        ],
      },
      {
        key: 'flexDirection', label: 'Flex 方向', type: 'select', defaultValue: 'row',
        options: [{ label: '水平', value: 'row' }, { label: '垂直', value: 'column' }],
      },
      { key: 'flexWrap', label: 'Flex 换行', type: 'boolean', defaultValue: false },
      {
        key: 'alignItems', label: '交叉轴', type: 'select', defaultValue: 'stretch',
        options: [
          { label: '拉伸',   value: 'stretch'    },
          { label: '顶部',   value: 'flex-start' },
          { label: '居中',   value: 'center'     },
          { label: '底部',   value: 'flex-end'   },
        ],
      },
      {
        key: 'justifyContent', label: '主轴', type: 'select', defaultValue: 'flex-start',
        options: [
          { label: '起点', value: 'flex-start'    },
          { label: '居中', value: 'center'        },
          { label: '终点', value: 'flex-end'      },
          { label: '两端', value: 'space-between' },
          { label: '均匀', value: 'space-around'  },
        ],
      },
      { key: 'gap',          label: '间距',     type: 'number', defaultValue: 0,  min: 0, max: 64 },
      { key: 'padding',      label: '内边距',   type: 'number', defaultValue: 0,  min: 0, max: 64 },
      { key: 'minHeight',    label: '最小高度', type: 'number', defaultValue: 60, min: 0, max: 2000 },
      { key: 'bgColor',      label: '背景色',   type: 'color',  defaultValue: 'transparent' },
      { key: 'borderRadius', label: '圆角',     type: 'number', defaultValue: 0,  min: 0, max: 32 },
      {
        key: 'sticky', label: '吸附固定', type: 'select', defaultValue: 'none',
        options: [
          { label: '不固定',   value: 'none'   },
          { label: '顶部固定', value: 'top'    },
          { label: '底部固定', value: 'bottom' },
        ],
      },
      { key: 'stickyOffset',   label: '吸附偏移(px)', type: 'number',  defaultValue: 0, min: 0, max: 500 },
      { key: 'stickyZIndex',   label: '吸附层级',     type: 'number',  defaultValue: 10, min: 0, max: 9999 },
      { key: 'preventCollapse', label: '防止塌陷',     type: 'boolean', defaultValue: true },
    ],
    defaultProps: { display: 'block', flexDirection: 'row', flexWrap: false, alignItems: 'stretch', justifyContent: 'flex-start', gap: 0, padding: 0, minHeight: 60, bgColor: 'transparent', borderRadius: 0, sticky: 'none', stickyOffset: 0, stickyZIndex: 10, preventCollapse: true },
  },
  {
    code: 'lc-holy-grail',
    title: '圣杯布局',
    iconEmoji: '⊞',
    category: 'layout',
    canHaveChildren: true,
    propSchema: [
      { key: 'leftWidth',    label: '左栏宽(px)',  type: 'number', defaultValue: 180, min: 60,  max: 600 },
      { key: 'rightWidth',   label: '右栏宽(px)',  type: 'number', defaultValue: 180, min: 60,  max: 600 },
      { key: 'headerHeight', label: '头部高(px)',  type: 'number', defaultValue: 64,  min: 0,   max: 300 },
      { key: 'footerHeight', label: '底部高(px)',  type: 'number', defaultValue: 56,  min: 0,   max: 300 },
      { key: 'mainMinH',     label: '主区最小高', type: 'number', defaultValue: 200, min: 60,  max: 1200 },
      { key: 'gap',          label: '间距(px)',    type: 'number', defaultValue: 0,   min: 0,   max: 32 },
      { key: 'bgColor',      label: '背景色',      type: 'color',  defaultValue: 'transparent' },
    ],
    defaultProps: { leftWidth: 180, rightWidth: 180, headerHeight: 64, footerHeight: 56, mainMinH: 200, gap: 0, bgColor: 'transparent' },
  },
  {
    code: 'lc-sticky-footer',
    title: '粘性 Footer',
    iconEmoji: '⊟',
    category: 'layout',
    canHaveChildren: true,
    propSchema: [
      { key: 'footerHeight', label: 'Footer 高(px)', type: 'number', defaultValue: 60,  min: 30, max: 200 },
      { key: 'minHeight',    label: '容器高(px)',    type: 'number', defaultValue: 400, min: 100, max: 1200 },
      { key: 'gap',          label: '间距(px)',         type: 'number', defaultValue: 0,   min: 0,   max: 32 },
      { key: 'bgColor',      label: '背景色',         type: 'color',  defaultValue: 'transparent' },
    ],
    defaultProps: { footerHeight: 60, minHeight: 400, gap: 0, bgColor: 'transparent' },
  },
  {
    code: 'lc-equal-height',
    title: '等高布局',
    iconEmoji: '⊜',
    category: 'layout',
    canHaveChildren: true,
    propSchema: [
      { key: 'gap',     label: '间距',   type: 'number', defaultValue: 12, min: 0, max: 64 },
      { key: 'padding', label: '内边距', type: 'number', defaultValue: 0,  min: 0, max: 64 },
      { key: 'bgColor', label: '背景色', type: 'color',  defaultValue: 'transparent' },
    ],
    defaultProps: { gap: 12, padding: 0, bgColor: 'transparent' },
  },
]

export const BASIC_COMPONENTS: ComponentDef[] = [
  {
    code: 'lc-heading',
    title: '标题',
    iconEmoji: 'H',
    category: 'basic',
    propSchema: [
      { key: 'content', label: '内容', type: 'text', defaultValue: '标题文字' },
      {
        key: 'level', label: '级别', type: 'select', defaultValue: 'h2',
        options: [
          { label: 'H1 特大', value: 'h1' },
          { label: 'H2 大',   value: 'h2' },
          { label: 'H3 中',   value: 'h3' },
          { label: 'H4 小',   value: 'h4' },
        ],
      },
      { key: 'color', label: '颜色', type: 'color', defaultValue: '#1a1a1a' },
      {
        key: 'align', label: '对齐', type: 'select', defaultValue: 'left',
        options: [
          { label: '左', value: 'left' },
          { label: '中', value: 'center' },
          { label: '右', value: 'right' },
        ],
      },
    ],
    defaultProps: { content: '标题文字', level: 'h2', color: '#1a1a1a', align: 'left' },
  },
  {
    code: 'lc-text',
    title: '文本',
    iconEmoji: '¶',
    category: 'basic',
    propSchema: [
      { key: 'content',    label: '内容',   type: 'textarea', defaultValue: '文本内容' },
      { key: 'fontSize',   label: '字号',   type: 'number',   defaultValue: 14,       min: 8,  max: 96 },
      { key: 'color',      label: '颜色',   type: 'color',    defaultValue: '#333333' },
      {
        key: 'fontWeight', label: '字重', type: 'select', defaultValue: 'normal',
        options: [
          { label: '细',   value: '300' },
          { label: '正常', value: 'normal' },
          { label: '中等', value: '500' },
          { label: '粗',   value: 'bold' },
        ],
      },
      {
        key: 'align', label: '对齐', type: 'select', defaultValue: 'left',
        options: [
          { label: '左',   value: 'left' },
          { label: '中',   value: 'center' },
          { label: '右',   value: 'right' },
          { label: '两端', value: 'justify' },
        ],
      },
      { key: 'lineHeight', label: '行高', type: 'number', defaultValue: 1.6, min: 1, max: 4, step: 0.1 },
    ],
    defaultProps: { content: '文本内容', fontSize: 14, color: '#333333', fontWeight: 'normal', align: 'left', lineHeight: 1.6 },
  },
  {
    code: 'lc-button',
    title: '按钮',
    iconEmoji: '□',
    category: 'basic',
    propSchema: [
      { key: 'label', label: '文字', type: 'text', defaultValue: '按钮' },
      {
        key: 'btnType', label: '类型', type: 'select', defaultValue: 'primary',
        options: [
          { label: '主要', value: 'primary' },
          { label: '默认', value: 'default' },
          { label: '虚线', value: 'dashed' },
          { label: '危险', value: 'danger' },
        ],
      },
      {
        key: 'size', label: '尺寸', type: 'select', defaultValue: 'middle',
        options: [
          { label: '大', value: 'large' },
          { label: '中', value: 'middle' },
          { label: '小', value: 'small' },
        ],
      },
      { key: 'block', label: '宽度占满', type: 'boolean', defaultValue: false },
    ],
    defaultProps: { label: '按钮', btnType: 'primary', size: 'middle', block: false },
  },
  {
    code: 'lc-tag',
    title: '标签',
    iconEmoji: '◈',
    category: 'basic',
    propSchema: [
      { key: 'content',     label: '内容',   type: 'text',  defaultValue: '标签' },
      { key: 'color',       label: '文字色', type: 'color', defaultValue: '#1677ff' },
      { key: 'bgColor',     label: '背景色', type: 'color', defaultValue: '#e6f4ff' },
      { key: 'borderColor', label: '边框色', type: 'color', defaultValue: '#91caff' },
    ],
    defaultProps: { content: '标签', color: '#1677ff', bgColor: '#e6f4ff', borderColor: '#91caff' },
  },
  {
    code: 'lc-richtext',
    title: '富文本',
    iconEmoji: '📝',
    category: 'basic',
    propSchema: [
      { key: 'content',   label: '内容',       type: 'richtext', defaultValue: '<p>在此编辑富文本内容</p>' },
      { key: 'minHeight', label: '最小高度(px)', type: 'number',   defaultValue: 120, min: 40, max: 1000 },
    ],
    defaultProps: { content: '<p>在此编辑富文本内容</p>', minHeight: 120 },
  },
  {
    code: 'lc-link',
    title: '链接',
    iconEmoji: '🔗',
    category: 'basic',
    propSchema: [
      { key: 'text',      label: '链接文字', type: 'text',    defaultValue: '链接文字' },
      { key: 'href',      label: '链接地址', type: 'text',    defaultValue: '#',        placeholder: 'https://...' },
      { key: 'color',     label: '颜色',     type: 'color',   defaultValue: '#1677ff' },
      { key: 'underline', label: '下划线',   type: 'boolean', defaultValue: true },
    ],
    defaultProps: { text: '链接文字', href: '#', color: '#1677ff', underline: true },
  },
]

export const MEDIA_COMPONENTS: ComponentDef[] = [
  {
    code: 'lc-image',
    title: '图片',
    iconEmoji: '🖼',
    category: 'media',
    propSchema: [
      { key: 'src',          label: '图片',     type: 'image',  defaultValue: '' },
      { key: 'alt',          label: '描述文字', type: 'text',   defaultValue: '' },
      { key: 'width',        label: '宽度(%)',  type: 'number', defaultValue: 100,     min: 1, max: 100 },
      { key: 'height',       label: '高度(px)', type: 'number', defaultValue: 200,     min: 0, max: 2000 },
      {
        key: 'objectFit', label: '填充方式', type: 'select', defaultValue: 'cover',
        options: [
          { label: '覆盖', value: 'cover' },
          { label: '包含', value: 'contain' },
          { label: '拉伸', value: 'fill' },
        ],
      },
      { key: 'borderRadius', label: '圆角(px)', type: 'number', defaultValue: 0, min: 0, max: 100 },
    ],
    defaultProps: { src: '', alt: '', width: 100, height: 200, objectFit: 'cover', borderRadius: 0 },
  },
]

export const ALL_BUILTIN: ComponentDef[] = [
  ...LAYOUT_COMPONENTS,
  ...BASIC_COMPONENTS,
  ...MEDIA_COMPONENTS,
]
