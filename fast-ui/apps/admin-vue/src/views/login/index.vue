<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { message } from 'ant-design-vue'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { login, getCaptcha } from '@/api/system/auth.ts'
import { getRequestId } from "@/utils/idUtils.ts";
import TokenService from '@/utils/token.ts'
import { usePermissionStore } from '@/stores/modules/permission.ts'

const permissionStore = usePermissionStore()

const appTitle = import.meta.env.VITE_APP_TITLE || 'Fast Admin Pro'
const appLogo = import.meta.env.VITE_APP_LOGO || '/favicon.svg'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const requestId = ref<string>('')
const formRef = ref()
const captchaImage = ref('')

// 开发模式默认账号密码
const isDev = import.meta.env.DEV
const defaultUsername = isDev ? import.meta.env.VITE_DEFAULT_USERNAME : ''
const defaultPassword = isDev ? import.meta.env.VITE_DEFAULT_PASSWORD : ''

const formState = reactive({
  username: defaultUsername,
  password: defaultPassword,
  remember: true,
  captcha: '',
  captchaKey: '',
})

const rules = {
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }, { min: 6, message: '密码至少6位', trigger: 'blur' }],
  captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
}

// 获取验证码
const fetchCaptcha = async () => {
  try {
    const res = await getCaptcha()
    if (res.code === 200) {
      formState.captchaKey = res.data.captchaKey
      captchaImage.value = res.data.captchaImage
      formState.captcha = ''
    }
  } catch (error) {
    console.error('获取验证码失败:', error)
  }
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    requestId.value = getRequestId();
    const res = await login(formState, requestId.value)
    if (res.code === 200) {
      TokenService.setToken(res.data)
      // 重置路由状态，强制重新获取
      permissionStore.resetRoutes()
      message.success({ content: '登录成功，欢迎回来！', class: 'custom-message' })
      // 跳转到 redirect 参数指定的页面，默认首页
      const redirect = route.query.redirect as string
      router.replace(redirect || '/')
    } else {
      message.error({ content: res.msg || '登录失败', class: 'custom-message' })
      // 登录失败后刷新验证码
      fetchCaptcha()
    }
  } catch (error) {
    console.error(error)
    // 登录异常后刷新验证码
    fetchCaptcha()
  } finally {
    loading.value = false
  }
}

// 页面加载时获取验证码
onMounted(() => {
  fetchCaptcha()
})
</script>

<template>
  <div class="login-container">
    <!-- 左侧：品牌展示与炫酷背景 -->
    <div class="login-left">
      <div class="login-left-bg">
        <div class="bg-decoration dec-1"></div>
        <div class="bg-decoration dec-2"></div>
        <div class="bg-decoration dec-3"></div>
        <div class="bg-glass"></div>
      </div>
      <div class="login-left-content">
        <div class="logo-wrapper">
          <img :src="appLogo" alt="logo" class="logo-icon" />
          <span class="logo-text">{{ appTitle }}</span>
        </div>
        <div class="hero-text">
          <h1>探索无限可能<br/>构建卓越体验</h1>
          <p>企业级权限管理系统，为您提供安全、高效、稳定的业务基石。以极致的性能与优雅的设计，赋能企业数字化转型。</p>
        </div>
        <div class="login-footer-left">
          <span>© 2024 Fast Project. All rights reserved.</span>
        </div>
      </div>
    </div>

    <!-- 右侧：登录表单 -->
    <div class="login-right">
      <div class="login-card">
        <div class="login-header">
          <div class="mobile-logo">
            <img :src="appLogo" alt="logo" class="logo-icon-mobile" />
          </div>
          <h2 class="login-title">欢迎回来</h2>
          <p class="login-subtitle">请登录您的账号以继续</p>
        </div>

        <a-form
          ref="formRef"
          :model="formState"
          :rules="rules"
          class="login-form"
          @finish="handleSubmit"
          layout="vertical"
        >
          <a-form-item name="username">
            <a-input 
              v-model:value="formState.username" 
              placeholder="请输入账号"
              size="large"
              class="custom-input"
            >
              <template #prefix>
                <UserOutlined class="input-icon" />
              </template>
            </a-input>
          </a-form-item>

          <a-form-item name="password">
            <a-input-password 
              v-model:value="formState.password" 
              placeholder="请输入密码"
              size="large"
              class="custom-input"
            >
              <template #prefix>
                <LockOutlined class="input-icon" />
              </template>
            </a-input-password>
          </a-form-item>

          <a-form-item name="captcha">
            <div class="captcha-wrapper">
              <a-input 
                v-model:value="formState.captcha" 
                placeholder="请输入验证码"
                size="large"
                class="custom-input captcha-input"
                @pressEnter="handleSubmit"
              />
              <div class="captcha-image" @click="fetchCaptcha">
                <img v-if="captchaImage" :src="captchaImage" alt="验证码" />
                <div v-else class="captcha-placeholder">点击刷新</div>
              </div>
            </div>
          </a-form-item>

          <div class="form-actions">
            <a-checkbox v-model:checked="formState.remember" class="remember-checkbox">记住我</a-checkbox>
            <a class="forgot-password">忘记密码？</a>
          </div>

          <a-form-item>
            <a-button 
              type="primary" 
              html-type="submit" 
              size="large"
              block
              :loading="loading"
              class="login-btn"
            >
              {{ loading ? '登录中...' : '登 录' }}
            </a-button>
          </a-form-item>
        </a-form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  min-height: 100vh;
  background-color: #ffffff;
}

/* --- 左侧区域 --- */
.login-left {
  position: relative;
  flex: 1.2;
  display: none;
  background: #0f172a;
  overflow: hidden;
}

@media (min-width: 992px) {
  .login-left {
    display: flex;
    flex-direction: column;
  }
}

.login-left-bg {
  position: absolute;
  inset: 0;
  z-index: 1;
  overflow: hidden;
  background: #020617;
}

.bg-decoration {
  position: absolute;
  border-radius: 50%;
  filter: blur(100px);
  opacity: 0.6;
  animation: pulse 12s cubic-bezier(0.4, 0, 0.2, 1) infinite alternate;
}

.dec-1 {
  width: 500px;
  height: 500px;
  background: #3b82f6;
  top: -150px;
  left: -100px;
}

.dec-2 {
  width: 600px;
  height: 600px;
  background: #8b5cf6;
  bottom: -200px;
  right: -150px;
  animation-delay: -6s;
}

.dec-3 {
  width: 400px;
  height: 400px;
  background: #06b6d4;
  top: 40%;
  left: 10%;
  animation-duration: 18s;
}

.bg-glass {
  position: absolute;
  inset: 0;
  background: rgba(15, 23, 42, 0.4);
  backdrop-filter: blur(40px);
  z-index: 2;
}

@keyframes pulse {
  0% { transform: scale(1) translate(0, 0); }
  100% { transform: scale(1.1) translate(40px, 40px); }
}

.login-left-content {
  position: relative;
  z-index: 3;
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 60px 80px;
  color: #ffffff;
}

.logo-wrapper {
  display: flex;
  align-items: center;
  gap: 14px;
  margin-bottom: auto;
}

.logo-icon {
  width: 42px;
  height: 42px;
  color: #60a5fa;
}

.logo-text {
  font-size: 28px;
  font-weight: 700;
  letter-spacing: 0.5px;
  color: #f8fafc;
}

.hero-text {
  margin-bottom: auto;
  animation: slideUp 1s ease-out;
}

.hero-text h1 {
  font-size: 56px;
  font-weight: 800;
  line-height: 1.15;
  margin-bottom: 24px;
  background: linear-gradient(135deg, #ffffff 0%, #94a3b8 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  letter-spacing: -1px;
}

.hero-text p {
  font-size: 18px;
  color: #cbd5e1;
  max-width: 500px;
  line-height: 1.7;
}

.login-footer-left {
  font-size: 14px;
  color: #64748b;
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(40px); }
  to { opacity: 1; transform: translateY(0); }
}

/* --- 右侧区域 --- */
.login-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #ffffff;
  padding: 40px;
  position: relative;
}

html.dark .login-right {
  background: #0f172a;
}

.login-card {
  width: 100%;
  max-width: 440px;
  animation: fadeIn 0.8s ease-out;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.login-header {
  margin-bottom: 48px;
}

.mobile-logo {
  display: none;
  margin-bottom: 24px;
}

@media (max-width: 991px) {
  .mobile-logo {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 64px;
    height: 64px;
    background: linear-gradient(135deg, #3b82f6 0%, #8b5cf6 100%);
    border-radius: 16px;
    color: white;
    box-shadow: 0 8px 20px rgba(59, 130, 246, 0.3);
  }
}

.logo-icon-mobile {
  width: 32px;
  height: 32px;
}

.login-title {
  font-size: 36px;
  font-weight: 700;
  color: #0f172a;
  margin-bottom: 12px;
  letter-spacing: -0.5px;
}

html.dark .login-title {
  color: #f8fafc;
}

.login-subtitle {
  font-size: 16px;
  color: #64748b;
}

html.dark .login-subtitle {
  color: #94a3b8;
}

.custom-input {
  border-radius: 10px;
  height: 56px;
  font-size: 16px;
  background: #f8fafc;
  border-color: #e2e8f0;
  transition: all 0.3s;
}

.custom-input:hover,
.custom-input:focus {
  border-color: #3b82f6;
  background: #ffffff;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

html.dark .custom-input {
  background: #1e293b;
  border-color: #334155;
  color: #f8fafc;
}

html.dark .custom-input:hover,
html.dark .custom-input:focus {
  border-color: #3b82f6;
  background: #0f172a;
}

html.dark .custom-input :deep(.ant-input) {
  background: transparent;
  color: #f8fafc;
}

html.dark .custom-input :deep(.ant-input::placeholder) {
  color: #64748b;
}

.input-icon {
  color: #94a3b8;
  font-size: 18px;
  margin-right: 10px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  margin-top: -8px;
}

html.dark .remember-checkbox {
  color: #cbd5e1;
}

.forgot-password {
  color: #3b82f6;
  font-size: 14px;
  font-weight: 500;
  transition: color 0.3s;
}

.forgot-password:hover {
  color: #2563eb;
}

.captcha-wrapper {
  display: flex;
  gap: 12px;
  align-items: center;
}

.captcha-input {
  flex: 1;
}

.captcha-input :deep(.ant-input) {
  text-align: center;
  font-size: 18px;
  letter-spacing: 4px;
}

.captcha-image {
  width: 130px;
  height: 56px;
  border-radius: 10px;
  overflow: hidden;
  cursor: pointer;
  border: 1px solid #e2e8f0;
  background: #f8fafc;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s;
}

.captcha-image:hover {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.captcha-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.captcha-placeholder {
  font-size: 12px;
  color: #94a3b8;
}

html.dark .captcha-image {
  border-color: #334155;
  background: #1e293b;
}

html.dark .captcha-image:hover {
  border-color: #3b82f6;
}

.login-btn.ant-btn-primary {
  height: 56px;
  border-radius: 10px;
  font-size: 18px;
  font-weight: 600;
  background: #0f172a;
  border-color: #0f172a;
  box-shadow: 0 4px 14px rgba(15, 23, 42, 0.2);
  transition: all 0.3s ease;
}

.login-btn.ant-btn-primary:hover {
  background: #1e293b;
  border-color: #1e293b;
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(15, 23, 42, 0.3);
}

html.dark .login-btn.ant-btn-primary {
  background: #3b82f6;
  border-color: #3b82f6;
  box-shadow: 0 4px 14px rgba(59, 130, 246, 0.3);
}

html.dark .login-btn.ant-btn-primary:hover {
  background: #2563eb;
  border-color: #2563eb;
}
</style>
