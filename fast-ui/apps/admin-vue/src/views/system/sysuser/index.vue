<template>
  <div class="sysuser-container"> 

    <!-- 悬浮式高级搜索区 -->
    <div class="filter-bar">
      <a-form :model="queryParams" class="elegant-form">
        <div class="filter-grid">
          <div class="filter-item">
            <span class="filter-label">账号</span>
            <a-input 
              v-model:value="queryParams.username" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><UserOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">昵称</span>
            <a-input 
              v-model:value="queryParams.nickname" 
              placeholder="输入搜索..." 
              class="elegant-input"
              allow-clear
            >
              <template #prefix><IdcardOutlined class="text-secondary" /></template>
            </a-input>
          </div>
          <div class="filter-item">
            <span class="filter-label">状态</span>
            <a-select 
              v-model:value="queryParams.status" 
              placeholder="全部状态" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option v-for="item in statusFilterOptions" :key="item.value" :value="item.value">
                <div class="status-option">
                  <span :class="item.value === 1 ? 'dot dot-success' : 'dot dot-error'"></span>
                  {{ item.label }}
                </div>
              </a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">部门</span>
            <a-select 
              v-model:value="queryParams.departmentId" 
              placeholder="全部部门" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option v-for="item in departmentOptions" :key="item.value" :value="item.value">
                {{ item.label }}
              </a-select-option>
            </a-select>
          </div>
          <div class="filter-item">
            <span class="filter-label">岗位</span>
            <a-select 
              v-model:value="queryParams.postId" 
              placeholder="全部岗位" 
              class="elegant-select"
              :bordered="false"
              allow-clear
            >
              <a-select-option v-for="item in postOptions" :key="item.value" :value="item.value">
                {{ item.label }}
              </a-select-option>
            </a-select>
          </div>
          <div class="filter-actions">
            <a-button class="icon-btn refresh-btn" @click="reset()" shape="circle" :loading="loading">
              <template #icon v-if="!loading"><ReloadOutlined /></template>
            </a-button>
            <a-button type="primary" class="gradient-btn" @click="loadData()" :loading="loading">
              <template #icon v-if="!loading"><SearchOutlined /></template>
              {{ loading ? '检索中...' : '检索' }}
            </a-button>
          </div>
        </div>
      </a-form>
    </div>

    <!-- 主体数据区 -->
    <div class="data-board">
      <div class="board-toolbar">
        <div class="toolbar-left">
          <div class="board-tabs">
            <div class="tab active">全部用户 <span class="badge">{{ pagination.total }}</span></div>
          </div>
        </div>
        <div class="toolbar-right">
          <a-button v-if="canAdd" type="primary" class="pill-btn primary-pill" @click="handleAdd()">
            <PlusOutlined /> 新增账户
          </a-button>
          <a-button 
            v-if="canDelete"
            danger 
            class="pill-btn danger-pill" 
            :class="{ 'is-disabled': selectedRowKeys.length === 0 }"
            :disabled="selectedRowKeys.length === 0" 
            @click="handleDelete()"
          >
            <DeleteOutlined /> 批量删除
          </a-button>
        </div>
      </div>

      <div class="table-wrapper">
        <a-skeleton active :loading="loading && dataSource.length === 0" :paragraph="{ rows: 10 }">
          <a-table
            :columns="columns"
            :data-source="dataSource"
            :loading="{ spinning: loading, tip: '数据加载中...', indicator: customLoadingIndicator }"
            :pagination="{
              ...pagination,
              className: 'elegant-pagination'
            }"
          :row-selection="{ selectedRowKeys, onChange: onSelectChange }"
          row-key="id"
          size="middle"
          :scroll="{ x: 1000 }"
          @change="handleTableChange"
          class="elegant-table"
        >
          <template #bodyCell="{ column, record }">
            <!-- 账号列：带头像的复合展示 -->
            <template v-if="column.key === 'username'">
              <div class="user-profile">
                <div v-if="record.avatar" class="avatar-img-box">
                  <img :src="record.avatar" :alt="record.username" class="avatar-img" />
                </div>
                <div v-else class="avatar-box" :style="{ background: getAvatarColor(record.id) }">
                  {{ record.username.charAt(0).toUpperCase() }}
                </div>
                <div class="user-info">
                  <span class="user-name">{{ record.username }}</span>
                </div>
              </div>
            </template>
            
            <!-- 昵称列 -->
            <template v-else-if="column.key === 'nickname'">
              <span class="text-strong">{{ record.nickname || '--' }}</span>
            </template>

            <!-- 联系方式列 -->
            <template v-else-if="column.key === 'contact'">
              <div class="contact-info">
                <div class="contact-item" v-if="record.phone">
                  <MobileOutlined class="text-secondary" /> {{ record.phone }}
                </div>
                <div class="contact-item" v-if="record.email">
                  <MailOutlined class="text-secondary" /> {{ record.email }}
                </div>
                <span class="text-muted" v-if="!record.phone && !record.email">暂无联系方式</span>
              </div>
            </template>
            
            <!-- 性别列 -->
            <template v-else-if="column.key === 'gender'">
              <div class="gender-badge" :class="getGenderClass(record.gender)">
                <component :is="getGenderIcon(record.gender)" />
                {{ getDictLabel('sex', record.gender) || '未知' }}
              </div>
            </template>

            <!-- 状态列 -->
            <template v-else-if="column.key === 'status'">
              <div class="status-toggle" :class="{ 'is-active': record.status === 1 }">
                <a-switch
                  :checked="record.status === 2"
                  size="small"
                  @change="handleStatusChange(record)"
                />
                <span class="status-text" :class="record.status === 1 ? 'text-success' : 'text-error'">
                  {{ getDictLabel('status', record.status) || '未知' }}
                </span>
              </div>
            </template>

            <!-- 操作列 -->
            <template v-else-if="column.key === 'action'">
              <div class="action-group">
                <div v-if="canUpdate" class="action-btn edit" @click="handleEdit(record)" title="编辑">
                  <EditOutlined />
                </div>
                <div v-if="canUpdate" class="action-btn password" @click="handleChangePassword(record)" title="修改密码">
                  <LockOutlined />
                </div>
                <div v-if="canDelete" class="action-btn delete" @click="handleDeleteOne(record)" title="删除">
                  <DeleteOutlined />
                </div>
              </div>
            </template>
          </template>
          </a-table>
        </a-skeleton>
      </div>
    </div>

    <!-- 极简苹果风弹窗 -->
    <a-modal
      v-model:open="modalVisible"
      :title="null"
      :footer="null"
      width="720px"
      :destroy-on-close="true"
      class="glass-modal"
      centered
      @cancel="handleCancel"
    >
      <div class="modal-inner">
        <div class="modal-header">
          <div class="modal-icon" :class="isEdit ? 'edit-icon' : 'add-icon'">
            <EditOutlined v-if="isEdit" />
            <UserAddOutlined v-else />
          </div>
          <div class="modal-titles">
            <h2>{{ isEdit ? '编辑账户信息' : '创建新账户' }}</h2>
            <p>请填写以下必填信息以完成账户设置</p>
          </div>
        </div>

        <a-form
          ref="formRef"
          :model="formData"
          layout="vertical"
          :rules="rules"
          class="glass-form"
        >
          <div class="form-section-title">基础凭证</div>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item name="username">
                <template #label><span class="form-label">登录账号</span></template>
                <a-input v-model:value="formData.username" placeholder="请输入字母或数字" :disabled="isEdit" />
              </a-form-item>
            </a-col>
            <a-col :span="12" v-if="!isEdit">
              <a-form-item name="password">
                <template #label><span class="form-label">安全密码</span></template>
                <a-input-password v-model:value="formData.password" placeholder="请输入至少6位密码" />
              </a-form-item>
            </a-col>
          </a-row>

          <div class="form-section-title mt-4">详细资料</div>
          <a-row :gutter="24">
            <a-col :span="24">
              <a-form-item name="avatar">
                <template #label><span class="form-label">头像</span></template>
                <ImageUpload v-model="formData.avatar" value-type="id" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item name="nickname">
                <template #label><span class="form-label">显示昵称</span></template>
                <a-input v-model:value="formData.nickname" placeholder="如：张三" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="gender">
                <template #label><span class="form-label">性别标识</span></template>
                <SegmentedRadio 
                  v-model:value="formData.gender"
                  :options="genderOptions"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="phone">
                <template #label><span class="form-label">联系电话</span></template>
                <a-input v-model:value="formData.phone" placeholder="11位手机号">
                  <template #prefix><MobileOutlined class="text-muted" /></template>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="email">
                <template #label><span class="form-label">电子邮箱</span></template>
                <a-input v-model:value="formData.email" placeholder="example@domain.com">
                  <template #prefix><MailOutlined class="text-muted" /></template>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="departmentId">
                <template #label><span class="form-label">所属部门</span></template>
                <a-select
                  v-model:value="formData.departmentId"
                  placeholder="选择部门"
                  :options="departmentOptions"
                  allow-clear
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="postId">
                <template #label><span class="form-label">所属岗位</span></template>
                <a-select
                  v-model:value="formData.postId"
                  placeholder="选择岗位"
                  :options="postOptions"
                  allow-clear
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item name="roleIds">
                <template #label><span class="form-label">分配角色</span></template>

                <a-select
                  v-model:value="formData.roleIds"
                  mode="multiple"
                  placeholder="选择角色"
                  :options="roleOptions"
                  :field-names="{ value: 'value', label: 'label' }"
                />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item name="status">
                <template #label><span class="form-label">账户状态</span></template>
                <StatusCardSelect 
                  v-model:value="formData.status"
                  :options="statusOptions"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handleCancel">取消</a-button>
          <a-button type="primary" class="gradient-btn-lg" @click="handleSubmit" :loading="submitLoading">
            {{ isEdit ? '保存修改' : '确认创建' }}
          </a-button>
        </div>
      </div>
    </a-modal>

    <!-- 修改密码弹窗 -->
    <a-modal
      v-model:open="passwordModalVisible"
      title="修改密码"
      :footer="null"
      width="480px"
      :destroy-on-close="true"
      class="glass-modal"
      centered
      @cancel="handlePasswordCancel"
    >
      <div class="modal-inner">
        <a-form
          ref="passwordFormRef"
          :model="passwordFormData"
          layout="vertical"
          :rules="passwordRules"
          class="glass-form"
        >
          <a-form-item name="newPassword">
            <template #label><span class="form-label">新密码</span></template>
            <a-input-password v-model:value="passwordFormData.newPassword" placeholder="请输入新密码（至少6位）" />
          </a-form-item>
          <a-form-item name="confirmPassword">
            <template #label><span class="form-label">确认新密码</span></template>
            <a-input-password v-model:value="passwordFormData.confirmPassword" placeholder="请再次输入新密码" />
          </a-form-item>
        </a-form>

        <div class="modal-footer-custom">
          <a-button class="glass-btn" @click="handlePasswordCancel">取消</a-button>
          <a-button type="primary" class="gradient-btn-lg" @click="handlePasswordSubmit" :loading="passwordLoading">
            确认修改
          </a-button>
        </div>
      </div>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, h, computed } from 'vue'
import { message, Modal } from 'ant-design-vue'
import {
  SearchOutlined, ReloadOutlined, PlusOutlined, DeleteOutlined, 
  EditOutlined, ManOutlined, WomanOutlined, QuestionCircleOutlined, 
  UserOutlined, IdcardOutlined, MobileOutlined, MailOutlined,
  UserAddOutlined, CheckCircleFilled, MinusCircleFilled,
  LoadingOutlined, LockOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import type { SysUsersVo, SysUsersQuery, SysUsersCreate, SysUserUpdate, SysUserPasswordUpdate } from '@/api/system/sysusers.ts'
import { getUsersPage, getUserById, createUser, updateUser, deleteUser, batchDeleteUser, updateUserPassword } from '@/api/system/sysusers.ts'
import { getFileInfoById } from '@/api/file/fileinfo.ts'
import { getRoleSelectAll } from '@/api/system/sysrole.ts'
import { getDepartmentSelectAll } from '@/api/system/sysdepartment.ts'
import { getPostSelectAll } from '@/api/system/syspost.ts'
import StatusCardSelect from '@/components/StatusCardSelect/index.vue'
import SegmentedRadio from '@/components/SegmentedRadio/index.vue'
import ImageUpload from '@/components/ImageUpload/index.vue'
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import { hasButtonPermission } from '@/utils/permission'
import { getDictData, getDictLabel } from '@/utils/dict'
import {getRequestId} from "@/utils/idUtils.ts";

const appStore = useAppStore()
const permissionStore = usePermissionStore()

// 按钮权限控制
const canAdd = ref(false)
const canUpdate = ref(false)
const canDelete = ref(false)

// 加载按钮权限
const loadButtonPermissions = async () => {
  await permissionStore.fetchButtonPermissions()
  canAdd.value = hasButtonPermission('admin:system:user:add')
  canUpdate.value = hasButtonPermission('admin:system:user:update')
  canDelete.value = hasButtonPermission('admin:system:user:delete')
}

// 从字典获取性别选项
const genderOptions = computed(() => {
  const sexData = getDictData('sex') || []
  return sexData.map((item: any) => ({
    value: Number(item.value),
    label: item.label,
    icon: item.value === '0' ? ManOutlined : item.value === '1' ? WomanOutlined : QuestionCircleOutlined
  }))
})

// 从字典获取状态选项（表单用）
const statusOptions = computed(() => {
  const statusData = getDictData('status') || []
  return statusData.map((item: any) => ({
    value: Number(item.value),
    title: item.label,
    icon: item.value === '1' ? CheckCircleFilled : MinusCircleFilled,
    type: item.value === '1' ? 'success' as const : 'error' as const
  }))
})

// 状态筛选选项（表格筛选用）
const statusFilterOptions = computed(() => {
  const statusData = getDictData('status') || []
  return statusData.map((item: any) => ({
    value: Number(item.value),
    label: item.label
  }))
})

const roleOptions = ref<{ value: string; label: string }[]>([])
const departmentOptions = ref<{ value: string; label: string }[]>([])
const postOptions = ref<{ value: string; label: string }[]>([])

const loadRoleOptions = async () => {
  const res = await getRoleSelectAll()
  if (res.code === 200) {
    roleOptions.value = res.data.map((item: any) => ({
      ...item,
      value: String(item.value)
    }))
  }
}

const loadDepartmentOptions = async () => {
  const res = await getDepartmentSelectAll()
  if (res.code === 200) {
    departmentOptions.value = transformTreeToSelect(res.data || [])
  }
}

const loadPostOptions = async () => {
  const res = await getPostSelectAll()
  if (res.code === 200) {
    postOptions.value = res.data?.map((item: any) => ({
      value: String(item.id),
      label: item.name
    })) || []
  }
}

// 将树形数据转换为选择框数据
const transformTreeToSelect = (treeData: any[], prefix = ''): any[] => {
  const result: any[] = []
  for (const item of treeData) {
    result.push({
      value: String(item.id),
      label: prefix + item.name
    })
    if (item.children && item.children.length > 0) {
      result.push(...transformTreeToSelect(item.children, prefix + '├── '))
    }
  }
  return result
}

// 自定义表格加载指示器
const customLoadingIndicator = h(LoadingOutlined, {
  style: {
    fontSize: '32px',
    color: '#6366f1',
  },
  spin: true,
})

// 合并电话和邮箱为联系方式列
const columns = [
  { title: '账户信息', dataIndex: 'username', key: 'username', width: 150 },
  { title: '昵称', dataIndex: 'nickname', key: 'nickname', width: 150 },
  { title: '联系方式', key: 'contact', width: 200 },
  { title: '性别', dataIndex: 'gender', key: 'gender', width: 100, align: 'center' },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const, align: 'center' },
]

const loading = ref(false)
const submitLoading = ref(false)
const dataSource = ref<SysUsersVo[]>([])
const selectedRowKeys = ref<number[]>([])

const queryParams = reactive<SysUsersQuery>({
  page: 1,
  pageSize: 10,
  username: '',
  nickname: '',
  status: undefined,
  departmentId: undefined,
  postId: undefined,
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条数据`,
})

const modalVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
type SysUserFormData = SysUsersCreate & { id?: number }
const formData = reactive<SysUserFormData>({
  id: undefined,
  username: '',
  password: '',
  nickname: '',
  email: '',
  phone: '',
  gender: 0,
  status: 0,
  departmentId: undefined,
  postId: undefined,
  roleIds: [],
  avatar: undefined,
})

// 修改密码相关
const passwordModalVisible = ref(false)
const passwordFormRef = ref<FormInstance>()
const passwordLoading = ref(false)
const passwordFormData = reactive({
  id: undefined as number | undefined,
  newPassword: '',
  confirmPassword: '',
})

const validateConfirmPassword = async (_rule: any, value: string) => {
  if (!value) {
    return Promise.reject('请再次输入新密码')
  }
  if (value !== passwordFormData.newPassword) {
    return Promise.reject('两次输入的密码不一致')
  }
  return Promise.resolve()
}

const passwordRules = {
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, validator: validateConfirmPassword, trigger: 'blur' },
  ],
}

const rules = {
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }, { min: 6, message: '密码至少6位', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  email: [{ type: 'email', message: '请输入正确的邮箱', trigger: 'blur' }],
  phone: [{ pattern: /^1\d{10}$/, message: '请输入11位手机号', trigger: 'blur' }],
}

// 辅助函数
const colors = [appStore.themeColor, '#10b981', '#8b5cf6', '#ec4899', '#ef4444', '#f59e0b', '#eab308']
const getAvatarColor = (id: number | undefined) => {
  if (!id) return colors[0]
  return colors[id % colors.length]
}

const getGenderClass = (gender: number) => {
  if (gender === 0) return 'gender-male'
  if (gender === 1) return 'gender-female'
  return 'gender-unknown'
}

const getGenderIcon = (gender: number) => {
  if (gender === 0) return ManOutlined
  if (gender === 1) return WomanOutlined
  return QuestionCircleOutlined
}

const getDepartmentName = (id: string) => {
  const dept = departmentOptions.value.find((item: any) => item.value === id)
  return dept?.label || '--'
}

const getPostName = (id: string) => {
  const post = postOptions.value.find((item: any) => item.value === id)
  return post?.label || '--'
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current - 1,
      pageSize: pagination.pageSize,
    }
    const res = await getUsersPage(params)
    if (res.code === 200) {
      dataSource.value = res.data?.data || []
      pagination.total = res.data?.total || 0
    }
  } finally {
    loading.value = false
  }
}

const handleTableChange = (pag: any) => {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  loadData()
}

const reset = () => {
  queryParams.username = ''
  queryParams.nickname = ''
  queryParams.status = undefined
  queryParams.departmentId = undefined
  queryParams.postId = undefined
  pagination.current = 1
  loadData()
}

const onSelectChange = (keys: number[]) => {
  selectedRowKeys.value = keys
}

const handleAdd = () => {
  requestId.value = getRequestId();
  isEdit.value = false
  Object.assign(formData, {
    id: undefined, username: '', password: '', nickname: '',
    email: '', phone: '', gender: 0, status: 0,
    departmentId: undefined, postId: undefined, roleIds: [],
    avatar: undefined,
  })
  modalVisible.value = true
}

const handleEdit = async (record: SysUsersVo) => {
  isEdit.value = true
  requestId.value = getRequestId();
  const res = await getUserById(record.id!)
  if (res.code === 200 && res.data) {
    const userData = res.data
    Object.assign(formData, {
      ...userData,
      departmentId: userData.departmentId ? String(userData.departmentId) : undefined,
      postId: userData.postId ? String(userData.postId) : undefined,
      roleIds: userData.roles?.map((r: any) => String(r.id)) || userData.roleIds?.map(String) || [],
    })
  }
  modalVisible.value = true
}

const requestId = ref<string>('')

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    submitLoading.value = true
    if (isEdit.value) {
      const res = await updateUser(formData as SysUserUpdate,requestId.value)
      if (res.code === 200) {
        message.success({ content: '账户更新成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    } else {
      const submitData: SysUsersCreate = { ...(formData as any) }
      delete (submitData as any).id
      const res = await createUser(submitData, requestId.value)
      if (res.code === 200) {
        message.success({ content: '账户创建成功', class: 'custom-message' })
        modalVisible.value = false
        loadData()
      }
    }
  } catch (error) {
    console.error(error)
  } finally {
    submitLoading.value = false
  }
}

const handleCancel = () => {
  modalVisible.value = false
  setTimeout(() => { formRef.value?.resetFields() }, 300)
}

const handleDeleteOne = (record: SysUsersVo) => {
  Modal.confirm({
    title: '危险操作确认',
    content: `将永久删除账户「${record.username}」，此操作无法撤销。`,
    okText: '确认删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await deleteUser(record.id!)
      if (res.code === 200) {
        message.success('已彻底删除')
        loadData()
      }
    },
  })
}

const handleDelete = () => {
  if (selectedRowKeys.value.length === 0) return
  Modal.confirm({
    title: '批量删除确认',
    content: `将彻底删除选中的 ${selectedRowKeys.value.length} 个账户，确定继续？`,
    okText: '全部删除',
    okType: 'danger',
    cancelText: '取消',
    centered: true,
    class: 'danger-modal',
    onOk: async () => {
      const res = await batchDeleteUser(selectedRowKeys.value)
      if (res.code === 200) {
        message.success('批量删除完成')
        selectedRowKeys.value = []
        loadData()
      }
    },
  })
}

const handleStatusChange = async (record: SysUsersVo) => {
  const newStatus = record.status === 1 ? 2 : 1
  requestId.value = getRequestId();
  const res = await updateUser({ ...record, status: newStatus } as SysUserUpdate,requestId.value)
  if (res.code === 200) {
    record.status = newStatus
    message.success(newStatus === 1 ? '账户已恢复正常' : '账户已被冻结')
  }
}

const handleChangePassword = (record: SysUsersVo) => {
  requestId.value = getRequestId();
  passwordFormData.id = record.id
  passwordFormData.newPassword = ''
  passwordFormData.confirmPassword = ''
  passwordModalVisible.value = true
}

const handlePasswordSubmit = async () => {
  try {
    await passwordFormRef.value?.validate()
    passwordLoading.value = true
    const data: SysUserPasswordUpdate = {
      id: passwordFormData.id!,
      newPassword: passwordFormData.newPassword,
    }
    const res = await updateUserPassword(data, requestId.value)
    if (res.code === 200) {
      message.success({ content: '密码重置成功', class: 'custom-message' })
      passwordModalVisible.value = false
    }
  } catch (error: any) {
    if (error.errorFields) {
      return
    }
    const msg = error.response?.data?.msg || '密码重置失败'
    message.error({ content: msg, class: 'custom-message' })
  } finally {
    passwordLoading.value = false
  }
}

const handlePasswordCancel = () => {
  passwordModalVisible.value = false
  setTimeout(() => { passwordFormRef.value?.resetFields() }, 300)
}

onMounted(() => {
  loadData()
  loadRoleOptions()
  loadDepartmentOptions()
  loadPostOptions()
  loadButtonPermissions()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';
</style>
