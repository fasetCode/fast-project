<template>
  <div class="message-test-container">
    <div class="page-header-elegant">
      <div class="header-left">
        <h1 class="page-title">消息功能测试</h1>
        <p class="page-subtitle">在此进行消息发送与验证码校验的全链路功能测试</p>
      </div>
      <div class="header-right">
        <a-button class="icon-btn refresh-btn" @click="reset()" shape="circle">
          <ReloadOutlined />
        </a-button>
      </div>
    </div>

    <div class="test-grid">
      <!-- 发送消息测试卡片 -->
      <div class="test-card glass-card">
        <div class="card-header">
          <div class="card-icon send-icon">
            <SendOutlined />
          </div>
          <div class="card-titles">
            <h3>发送测试</h3>
            <p>基于模板推送实时消息</p>
          </div>
        </div>
        
        <div class="card-body">
          <a-form :model="sendForm" layout="vertical" class="elegant-form">
            <a-form-item label="选择模版">
              <a-select 
                v-model:value="sendForm.templateCode" 
                placeholder="请选择要测试的模版" 
                show-search
                option-filter-prop="label"
                class="elegant-select"
              >
                <a-select-option 
                  v-for="item in templateList" 
                  :key="item.code" 
                  :value="item.code"
                  :label="item.title"
                >
                  {{ item.title }} ({{ item.code }})
                </a-select-option>
              </a-select>
            </a-form-item>

            <a-form-item label="接收者">
              <a-input 
                v-model:value="sendForm.receiver" 
                placeholder="手机号 / 邮箱 / 用户ID" 
                class="elegant-input"
              >
                <template #prefix><UserOutlined class="text-secondary" /></template>
              </a-input>
            </a-form-item>

            <a-form-item label="模板参数 (JSON)">
              <a-textarea 
                v-model:value="sendForm.paramsStr" 
                placeholder='{"code": "123456", "name": "张三"}' 
                :rows="4" 
                class="elegant-textarea"
              />
            </a-form-item>

            <div class="form-actions">
              <a-button 
                type="primary" 
                class="gradient-btn-lg full-width" 
                @click="handleSend" 
                :loading="sendLoading"
              >
                <template #icon><SendOutlined /></template>
                立即发送
              </a-button>
            </div>
          </a-form>
        </div>
      </div>

      <!-- 验证码校验测试卡片 -->
      <div class="test-card glass-card">
        <div class="card-header">
          <div class="card-icon verify-icon">
            <SafetyCertificateOutlined />
          </div>
          <div class="card-titles">
            <h3>验证测试</h3>
            <p>对已发送的验证码进行有效性核验</p>
          </div>
        </div>

        <div class="card-body">
          <a-form :model="verifyForm" layout="vertical" class="elegant-form">
            <a-form-item label="目标账号">
              <a-input 
                v-model:value="verifyForm.target" 
                placeholder="发送时使用的手机号或邮箱" 
                class="elegant-input"
              >
                <template #prefix><MobileOutlined class="text-secondary" /></template>
              </a-input>
            </a-form-item>

            <a-form-item label="验证码">
              <a-input 
                v-model:value="verifyForm.code" 
                placeholder="请输入收到的验证码" 
                class="elegant-input"
              >
                <template #prefix><KeyOutlined class="text-secondary" /></template>
              </a-input>
            </a-form-item>

            <div class="verify-result-box" v-if="verifyResult !== null">
              <div :class="['result-indicator', verifyResult ? 'is-success' : 'is-error']">
                <CheckCircleFilled v-if="verifyResult" />
                <CloseCircleFilled v-else />
                <span>{{ verifyResult ? '验证通过' : '验证失败或已过期' }}</span>
              </div>
            </div>

            <div class="form-actions mt-auto">
              <a-button 
                type="primary" 
                class="gradient-btn-lg full-width" 
                @click="handleVerify" 
                :loading="verifyLoading"
              >
                <template #icon><SafetyOutlined /></template>
                核验验证码
              </a-button>
            </div>
          </a-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { 
  SendOutlined, ReloadOutlined, UserOutlined, 
  SafetyCertificateOutlined, MobileOutlined, KeyOutlined,
  CheckCircleFilled, CloseCircleFilled, SafetyOutlined
} from '@ant-design/icons-vue'
import type { MessageTemplateVo } from '@/api/message/messagetemplate.ts'
import { getMessageTemplateSelectAll } from '@/api/message/messagetemplate.ts'
import { sendTestMessage, verifyTestCode } from '@/api/message/messagetest.ts'

const templateList = ref<MessageTemplateVo[]>([])
const sendLoading = ref(false)
const verifyLoading = ref(false)
const verifyResult = ref<boolean | null>(null)

const sendForm = reactive({
  templateCode: undefined,
  receiver: '',
  paramsStr: '{}'
})

const verifyForm = reactive({
  target: '',
  code: ''
})

const loadTemplates = async () => {
  try {
    const res = await getMessageTemplateSelectAll()
    if (res.code === 200) {
      // 过滤出有 code 的模版
      templateList.value = (res.data || []).filter(t => t.code)
    }
  } catch (error) {
    console.error('加载模版列表失败:', error)
  }
}

const handleSend = async () => {
  if (!sendForm.templateCode) return message.warning('请选择模版')
  if (!sendForm.receiver) return message.warning('请输入接收者')
  
  let params = {}
  try {
    params = JSON.parse(sendForm.paramsStr || '{}')
  } catch (e) {
    return message.error('参数 JSON 格式不正确')
  }

  sendLoading.value = true
  try {
    const res = await sendTestMessage({
      templateCode: sendForm.templateCode,
      receiver: sendForm.receiver,
      params
    })
    if (res.code === 200) {
      message.success('测试消息已提交发送')
    }
  } catch (error: any) {
    message.error(error.message || '发送失败')
  } finally {
    sendLoading.value = false
  }
}

const handleVerify = async () => {
  if (!verifyForm.target) return message.warning('请输入目标账号')
  if (!verifyForm.code) return message.warning('请输入验证码')

  verifyLoading.value = true
  verifyResult.value = null
  try {
    const res = await verifyTestCode({
      target: verifyForm.target,
      code: verifyForm.code
    })
    if (res.code === 200) {
      verifyResult.value = res.data
      if (res.data) {
        message.success('验证码核验通过')
      } else {
        message.error('验证码无效或已过期')
      }
    }
  } catch (error: any) {
    message.error(error.message || '校验失败')
  } finally {
    verifyLoading.value = false
  }
}

const reset = () => {
  Object.assign(sendForm, { templateCode: undefined, receiver: '', paramsStr: '{}' })
  Object.assign(verifyForm, { target: '', code: '' })
  verifyResult.value = null
  loadTemplates()
}

onMounted(() => {
  loadTemplates()
})
</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';

.message-test-container {
  padding: 24px;
}

.page-header-elegant {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
}

.page-title {
  font-size: 24px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 4px;
}

.page-subtitle {
  color: #64748b;
  font-size: 14px;
}

.test-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 24px;
}

.test-card {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.card-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
}

.card-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.send-icon {
  background: rgba(99, 102, 241, 0.1);
  color: #6366f1;
}

.verify-icon {
  background: rgba(16, 185, 129, 0.1);
  color: #10b981;
}

.card-titles h3 {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 2px;
}

.card-titles p {
  font-size: 13px;
  color: #64748b;
}

.card-body {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.full-width {
  width: 100%;
}

.mt-auto {
  margin-top: auto;
}

.verify-result-box {
  margin-bottom: 24px;
  animation: slideDown 0.3s ease-out;
}

.result-indicator {
  padding: 12px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 500;
}

.is-success {
  background: rgba(16, 185, 129, 0.1);
  color: #059669;
}

.is-error {
  background: rgba(239, 68, 68, 0.1);
  color: #dc2626;
}

@keyframes slideDown {
  from { opacity: 0; transform: translateY(-10px); }
  to { opacity: 1; transform: translateY(0); }
}

:global(.elegant-textarea) {
  border-radius: 12px !important;
  border: 1.5px solid #e2e8f0 !important;
  padding: 12px !important;
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 13px;
}

:global(.elegant-textarea:focus) {
  border-color: #6366f1 !important;
  box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.1) !important;
}
</style>
