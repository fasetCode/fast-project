<template>
  <div class="init-page">
    <a-card v-if="!loading && !initData?.hasUser && !initData?.hasConfig" class="init-card">
      <h1>初始化配置</h1>
      <a-form
        :model="formState"
        layout="vertical"
        @finish="onFinish"
      >
        <a-divider>客服账号</a-divider>
        <a-row :gutter="16">
          <a-col :span="24">
            <a-form-item label="用户名" name="username" :rules="[{ required: true, message: '请输入用户名' }]">
              <a-input v-model:value="formState.username" placeholder="请输入用户名" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="密码" name="password" :rules="[{ required: true, message: '请输入密码' }]">
              <a-input-password v-model:value="formState.password" placeholder="请输入密码" />
            </a-form-item>
          </a-col>
        </a-row>

        <a-divider>客服系统配置</a-divider>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="消息发送地址" name="messageSendUrl">
              <a-input v-model:value="formState.messageSendUrl" placeholder="消息发送地址" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="消息发送密钥" name="messageSendKey">
              <a-input v-model:value="formState.messageSendKey" placeholder="消息发送密钥" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="应用ID" name="appId">
              <a-input v-model:value="formState.appId" placeholder="应用ID" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="客服分组" name="wsGroup">
              <a-input v-model:value="formState.wsGroup" placeholder="客服分组" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="客户分组" name="customerGroup">
              <a-input v-model:value="formState.customerGroup" placeholder="客户分组" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="WS链接域名" name="wsDomain">
              <a-input v-model:value="formState.wsDomain" placeholder="WS链接域名" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="授权回调" name="authCallback">
              <a-input v-model:value="formState.authCallback" placeholder="授权回调" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="备注" name="remark">
              <a-input v-model:value="formState.remark" placeholder="备注" />
            </a-form-item>
          </a-col>
        </a-row>

        <a-form-item>
          <a-button type="primary" html-type="submit" :loading="submitting" block>
            初始化
          </a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getInitCheck, initSetup } from '@/api'
import router from '@/router'
import { message } from 'ant-design-vue'

interface InitCheckData {
  hasUser: boolean
  hasConfig: boolean
}

interface FormState {
  username: string
  password: string
  messageSendUrl: string
  messageSendKey: string
  appId: string
  wsGroup: string
  customerGroup: string
  wsDomain: string
  authCallback: string
  remark: string
}

const loading = ref(true)
const submitting = ref(false)
const initData = ref<InitCheckData | null>(null)

const formState = reactive<FormState>({
  username: '',
  password: '',
  messageSendUrl: '',
  messageSendKey: '',
  appId: '',
  wsGroup: '',
  customerGroup: '',
  wsDomain: '',
  authCallback: '',
  remark: ''
})

const checkInit = async () => {
  try {
    const res = await getInitCheck()
    initData.value = res.data.data
    if (initData.value?.hasUser || initData.value?.hasConfig) {
      router.push('/404')
    }
  } catch (error) {
    console.error('检查失败:', error)
  } finally {
    loading.value = false
  }
}

const onFinish = async () => {
  submitting.value = true
  try {
    await initSetup({
      user: {
        username: formState.username,
        password: formState.password
      },
      config: {
        messageSendUrl: formState.messageSendUrl,
        messageSendKey: formState.messageSendKey,
        appId: formState.appId,
        wsGroup: formState.wsGroup,
        customerGroup: formState.customerGroup,
        wsDomain: formState.wsDomain,
        authCallback: formState.authCallback,
        remark: formState.remark
      }
    })
    message.success('初始化成功')
    router.push('/chat')
  } catch (error) {
    console.error('初始化失败:', error)
    message.error('初始化失败')
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  checkInit()
})
</script>

<style scoped>
.init-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f0f2f5;
  padding: 20px;
}

.init-card {
  max-width: 800px;
  width: 100%;
}

.init-card h1 {
  text-align: center;
  margin-bottom: 24px;
}
</style>
