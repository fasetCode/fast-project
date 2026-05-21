<template>
  <div class="admin-page">
    <a-card>
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="user" tab="用户管理">
          <a-table
            :columns="userColumns"
            :data-source="userData"
            :loading="userLoading"
            :pagination="userPagination"
            row-key="id"
            @change="handleUserTableChange"
          >
            <template #bodyCell="{ column, record }">
              <template v-if="column.key === 'action'">
                <a-space>
                  <a-button type="link" size="small" @click="openUserModal(record)">编辑</a-button>
                </a-space>
              </template>
            </template>
          </a-table>
        </a-tab-pane>

        <a-tab-pane key="config" tab="配置管理">
          <a-table
            :columns="configColumns"
            :data-source="configData"
            :loading="configLoading"
            :pagination="configPagination"
            row-key="id"
            @change="handleConfigTableChange"
          >
            <template #bodyCell="{ column, record }">
              <template v-if="column.key === 'action'">
                <a-space>
                  <a-button type="link" size="small" @click="openConfigModal(record)">编辑</a-button>
                </a-space>
              </template>
            </template>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </a-card>

    <a-modal
      v-model:open="userModalVisible"
      :title="userEditingId ? '编辑用户' : '新增用户'"
      @ok="handleUserSubmit"
      @cancel="closeUserModal"
    >
      <a-form :model="userForm" layout="vertical">
        <a-form-item label="用户名" name="username" :rules="[{ required: true, message: '请输入用户名' }]">
          <a-input v-model:value="userForm.username" />
        </a-form-item>
        <a-form-item label="密码" name="password" :rules="[{ required: !userEditingId, message: '请输入密码' }]">
          <a-input-password v-model:value="userForm.password" :placeholder="userEditingId ? '留空不修改' : '请输入密码'" />
        </a-form-item>
        <a-form-item label="白名单IP" name="whitelistIp">
          <a-input v-model:value="userForm.whitelistIp" />
        </a-form-item>
        <a-form-item label="开启白名单" name="whitelistEnabled">
          <a-switch v-model:checked="userForm.whitelistEnabled" />
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal
      v-model:open="configModalVisible"
      :title="configEditingId ? '编辑配置' : '新增配置'"
      @ok="handleConfigSubmit"
      @cancel="closeConfigModal"
      width="800px"
    >
      <a-form :model="configForm" layout="vertical">
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="消息发送地址" name="messageSendUrl">
              <a-input v-model:value="configForm.messageSendUrl" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="消息发送密钥" name="messageSendKey">
              <a-input v-model:value="configForm.messageSendKey" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="应用ID" name="appId">
              <a-input v-model:value="configForm.appId" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="客服分组" name="wsGroup">
              <a-input v-model:value="configForm.wsGroup" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="客户分组" name="customerGroup">
              <a-input v-model:value="configForm.customerGroup" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="WS链接域名" name="wsDomain">
              <a-input v-model:value="configForm.wsDomain" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="授权回调" name="authCallback">
              <a-input v-model:value="configForm.authCallback" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="消息回调" name="messageCallback">
              <a-input v-model:value="configForm.messageCallback" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="获取Token回调" name="tokenCallback">
              <a-input v-model:value="configForm.tokenCallback" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="备注" name="remark">
              <a-input v-model:value="configForm.remark" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="用户信息列表接口" name="userInfoList">
              <a-input v-model:value="configForm.userInfoList" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="客服模式" name="type">
              <a-select v-model:value="configForm.type">
                <a-select-option :value="1">统一客服</a-select-option>
                <a-select-option :value="2">多客服</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="获取客服列表接口" name="getCsList">
              <a-input v-model:value="configForm.getCsList" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="昵称" name="nickname">
              <a-input v-model:value="configForm.nickname" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="头像" name="avatar">
              <a-input v-model:value="configForm.avatar" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import {
  getUserPage,
  saveUser,
  updateUser,
  deleteUser,
  batchDeleteUser,
  getConfigPage,
  saveConfig,
  updateConfig,
  deleteConfig,
  batchDeleteConfig
} from '@/api'
import { message } from 'ant-design-vue'

const activeTab = ref('user')

const userColumns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '用户名', dataIndex: 'username', key: 'username' },
  { title: '白名单IP', dataIndex: 'whitelistIp', key: 'whitelistIp' },
  { title: '开启白名单', dataIndex: 'whitelistEnabled', key: 'whitelistEnabled' },
  { title: '操作', key: 'action', width: 100 }
]

const userLoading = ref(false)
const userData = ref([])
const userPagination = reactive({ current: 1, pageSize: 10, total: 0 })
const userSelectedRowKeys = ref<number[]>([])
const userModalVisible = ref(false)
const userEditingId = ref<number | null>(null)
const userForm = reactive({
  username: '',
  password: '',
  whitelistIp: '',
  whitelistEnabled: false
})

const configColumns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '应用ID', dataIndex: 'appId', key: 'appId' },
  { title: '客服分组', dataIndex: 'wsGroup', key: 'wsGroup' },
  { title: '客户分组', dataIndex: 'customerGroup', key: 'customerGroup' },
  { title: 'WS域名', dataIndex: 'wsDomain', key: 'wsDomain' },
  { title: '客服模式', dataIndex: 'type', key: 'type' },
  { title: '获取客服列表接口', dataIndex: 'getCsList', key: 'getCsList' },
  { title: '用户信息列表接口', dataIndex: 'userInfoList', key: 'userInfoList' },
  { title: '昵称', dataIndex: 'nickname', key: 'nickname' },
  { title: '头像', dataIndex: 'avatar', key: 'avatar' },
  { title: '备注', dataIndex: 'remark', key: 'remark' },
  { title: '操作', key: 'action', width: 150 }
]

const configLoading = ref(false)
const configData = ref([])
const configPagination = reactive({ current: 1, pageSize: 10, total: 0 })
const configSelectedRowKeys = ref<number[]>([])
const configModalVisible = ref(false)
const configEditingId = ref<number | null>(null)
const configForm = reactive({
  messageSendUrl: '',
  messageSendKey: '',
  appId: '',
  wsGroup: '',
  customerGroup: '',
  wsDomain: '',
  authCallback: '',
  messageCallback: '',
  tokenCallback: '',
  remark: '',
  userInfoList: '',
  type: null,
  getCsList: '',
  nickname: '',
  avatar: ''
})

const loadUserData = async () => {
  userLoading.value = true
  try {
    const res = await getUserPage({ page: userPagination.current - 1, pageSize: userPagination.pageSize })
    userData.value = res.data.data.data || []
    userPagination.total = res.data.data.total || 0
  } catch (error) {
    console.error('加载用户数据失败:', error)
  } finally {
    userLoading.value = false
  }
}

const loadUserInfoList = async () => {
  try {
    const res = await getUserInfoList()
    console.log('用户信息列表:', res.data.data)
    message.success('获取成功')
  } catch (error) {
    message.error('获取失败')
  }
}

const loadConfigData = async () => {
  configLoading.value = true
  try {
    const res = await getConfigPage({ page: configPagination.current - 1, pageSize: configPagination.pageSize })
    configData.value = res.data.data.data || []
    configPagination.total = res.data.data.total || 0
  } catch (error) {
    console.error('加载配置数据失败:', error)
  } finally {
    configLoading.value = false
  }
}

const handleUserTableChange = (pagination: any) => {
  userPagination.current = pagination.current
  loadUserData()
}

const handleConfigTableChange = (pagination: any) => {
  configPagination.current = pagination.current
  loadConfigData()
}

const onUserSelectChange = (keys: number[]) => {
  userSelectedRowKeys.value = keys
}

const onConfigSelectChange = (keys: number[]) => {
  configSelectedRowKeys.value = keys
}

const openUserModal = (record?: any) => {
  if (record) {
    userEditingId.value = record.id
    userForm.username = record.username
    userForm.password = ''
    userForm.whitelistIp = record.whitelistIp || ''
    userForm.whitelistEnabled = record.whitelistEnabled || false
  } else {
    userEditingId.value = null
    userForm.username = ''
    userForm.password = ''
    userForm.whitelistIp = ''
    userForm.whitelistEnabled = false
  }
  userModalVisible.value = true
}

const closeUserModal = () => {
  userModalVisible.value = false
  userEditingId.value = null
}

const handleUserSubmit = async () => {
  try {
    if (userEditingId.value) {
      const data: any = { id: userEditingId.value, username: userForm.username, whitelistIp: userForm.whitelistIp, whitelistEnabled: userForm.whitelistEnabled }
      if (userForm.password) data.password = userForm.password
      await updateUser(data)
      message.success('更新成功')
    } else {
      await saveUser(userForm)
      message.success('创建成功')
    }
    closeUserModal()
    loadUserData()
  } catch (error) {
    message.error('操作失败')
  }
}

const deleteUser = async (id: number) => {
  try {
    await deleteUser(id)
    message.success('删除成功')
    loadUserData()
  } catch (error) {
    message.error('删除失败')
  }
}

const batchDeleteUser = async () => {
  try {
    await batchDeleteUser(userSelectedRowKeys.value)
    message.success('批量删除成功')
    userSelectedRowKeys.value = []
    loadUserData()
  } catch (error) {
    message.error('批量删除失败')
  }
}

const openConfigModal = (record?: any) => {
  if (record) {
    configEditingId.value = record.id
    Object.assign(configForm, record)
  } else {
    configEditingId.value = null
    Object.assign(configForm, {
      messageSendUrl: '',
      messageSendKey: '',
      appId: '',
      wsGroup: '',
      customerGroup: '',
      wsDomain: '',
      authCallback: '',
      messageCallback: '',
      tokenCallback: '',
      remark: '',
      userInfoList: '',
      type: null,
      getCsList: '',
      nickname: '',
      avatar: ''
    })
  }
  configModalVisible.value = true
}

const closeConfigModal = () => {
  configModalVisible.value = false
  configEditingId.value = null
}

const handleConfigSubmit = async () => {
  try {
    if (configEditingId.value) {
      await updateConfig({ id: configEditingId.value, ...configForm })
      message.success('更新成功')
    } else {
      await saveConfig(configForm)
      message.success('创建成功')
    }
    closeConfigModal()
    loadConfigData()
  } catch (error) {
    message.error('操作失败')
  }
}

const deleteConfig = async (id: number) => {
  try {
    await deleteConfig(id)
    message.success('删除成功')
    loadConfigData()
  } catch (error) {
    message.error('删除失败')
  }
}

const batchDeleteConfig = async () => {
  try {
    await batchDeleteConfig(configSelectedRowKeys.value)
    message.success('批量删除成功')
    configSelectedRowKeys.value = []
    loadConfigData()
  } catch (error) {
    message.error('批量删除失败')
  }
}

onMounted(() => {
  loadUserData()
  loadConfigData()
})
</script>

<style scoped>
.admin-page {
  padding: 24px;
  min-height: 100vh;
  background-color: #f0f2f5;
}
</style>
