<script setup lang="ts">
import { ref, watch } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  getPageWebConfigPage,
  deletePageWebConfig,
  type PageWebConfigVo,
} from '@/api/page/pagewebconfig.ts'

// ── Props / Emits ─────────────────────────────────────────────────────────────
const props = defineProps<{
  open:          boolean
  applicationId: string
}>()

const emit = defineEmits<{
  (e: 'update:open', val: boolean): void
}>()

// ── List state ────────────────────────────────────────────────────────────────
const list       = ref<PageWebConfigVo[]>([])
const total      = ref(0)
const loading    = ref(false)
const page       = ref(1)
const pageSize   = ref(10)
const pathFilter = ref('')

const columns = [
  { title: 'ID',       dataIndex: 'id',              key: 'id',              width: 80  },
  { title: '路径 URL', dataIndex: 'pathUrl',         key: 'pathUrl',         ellipsis: true },
  { title: '所属应用', dataIndex: 'applicationName', key: 'applicationName', width: 160 },
  { title: '操作',     key: 'action',                width: 120             },
]

// ── JSON detail state ─────────────────────────────────────────────────────────
const jsonVisible    = ref(false)
const selectedRecord = ref<PageWebConfigVo | null>(null)

// ── Data loading ──────────────────────────────────────────────────────────────
const load = async () => {
  if (!props.applicationId) return
  loading.value = true
  try {
    const res = await getPageWebConfigPage({
      page:          page.value - 1,
      pageSize:      pageSize.value,
      applicationId: props.applicationId,
      pathUrl:       pathFilter.value || undefined,
    })
    list.value  = res.data?.data ?? []
    total.value = res.data?.total ?? 0
  } catch {
    message.error('加载发布记录失败')
  } finally {
    loading.value = false
  }
}

const search = () => {
  page.value = 1
  load()
}

const onPageChange = (p: number, ps: number) => {
  page.value     = p
  pageSize.value = ps
  load()
}

// ── Delete ────────────────────────────────────────────────────────────────────
const handleDelete = (record: PageWebConfigVo) => {
  Modal.confirm({
    title:   '确认删除',
    content: `确定要删除发布记录「${record.pathUrl}」吗？此操作不可恢复。`,
    okText:  '删除',
    okType:  'danger',
    cancelText: '取消',
    async onOk() {
      try {
        await deletePageWebConfig(record.id!)
        message.success('删除成功')
        load()
      } catch {
        message.error('删除失败')
      }
    },
  })
}

// ── Auto load when drawer opens ───────────────────────────────────────────────
watch(() => props.open, (val) => {
  if (val) {
    page.value       = 1
    pathFilter.value = ''
    load()
  }
})
</script>

<template>
  <!-- ── Main list drawer ─────────────────────────────────────────────────── -->
  <a-drawer
    :open="open"
    title="📋 发布管理"
    placement="right"
    :width="640"
    @update:open="emit('update:open', $event)"
  >
    <!-- Search bar -->
    <div class="pm-search-bar">
      <a-input
        v-model:value="pathFilter"
        placeholder="过滤路径 URL"
        allow-clear
        style="width: 240px"
        @pressEnter="search"
        @change="(e: any) => { if (!e.target.value) search() }"
      />
      <a-button type="primary" @click="search">🔍 查询</a-button>
      <a-button @click="load">↻ 刷新</a-button>
    </div>

    <!-- Table -->
    <a-table
      :columns="columns"
      :data-source="list"
      :loading="loading"
      row-key="id"
      :pagination="false"
      size="small"
      :scroll="{ y: 'calc(100vh - 220px)' }"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'pathUrl'">
          <a-tooltip :title="record.pathUrl">
            <span class="pm-path">{{ record.pathUrl }}</span>
          </a-tooltip>
        </template>
        <template v-if="column.key === 'action'">
          <a-tooltip title="查看配置 JSON">
            <a-button
              size="small"
              type="link"
              @click="() => { selectedRecord = record; jsonVisible = true }"
            >详情</a-button>
          </a-tooltip>
          <a-divider type="vertical" />
          <a-tooltip title="删除发布记录">
            <a-button
              size="small"
              type="link"
              danger
              @click="handleDelete(record)"
            >删除</a-button>
          </a-tooltip>
        </template>
      </template>
    </a-table>

    <!-- Pagination -->
    <div class="pm-pagination">
      <a-pagination
        v-model:current="page"
        v-model:page-size="pageSize"
        :total="total"
        :show-size-changer="true"
        :page-size-options="['10','20','50']"
        show-quick-jumper
        :show-total="(t: number) => `共 ${t} 条`"
        @change="onPageChange"
        @showSizeChange="onPageChange"
        size="small"
      />
    </div>
  </a-drawer>

  <!-- ── JSON detail drawer ────────────────────────────────────────────────── -->
  <a-drawer
    v-model:open="jsonVisible"
    :title="`配置详情 — ${selectedRecord?.pathUrl ?? ''}`"
    placement="right"
    :width="520"
  >
    <pre class="pm-json">{{ selectedRecord?.config
      ? JSON.stringify(JSON.parse(selectedRecord.config), null, 2)
      : '无配置'
    }}</pre>
  </a-drawer>
</template>

<style scoped>
.pm-search-bar {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
.pm-path {
  font-family: monospace;
  font-size: 12px;
  color: #1677ff;
  word-break: break-all;
}
.pm-pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 12px;
}
.pm-json {
  background: #1e1e1e;
  color: #d4d4d4;
  font-family: 'Consolas', 'Courier New', monospace;
  font-size: 12px;
  line-height: 1.6;
  padding: 16px;
  border-radius: 6px;
  overflow: auto;
  max-height: calc(100vh - 120px);
  white-space: pre-wrap;
  word-break: break-all;
}
</style>
