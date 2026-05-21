<template>
  <div class="personal-container">
    <a-row :gutter="24">
      <!-- 左侧：个人资料卡片 -->
      <a-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <div class="profile-card elegant-card">
          <div class="profile-header">
            <div class="avatar-wrapper">
              <a-avatar :size="100" :src="userInfo.avatar || defaultAvatar" class="profile-avatar" />
              <div class="avatar-upload-btn" @click="handleAvatarClick">
                <CameraOutlined />
              </div>
            </div>
            <h2 class="profile-name">{{ userInfo.nickname }}</h2>
            <p class="profile-desc">{{ userInfo.department || '未分配部门' }} | {{ userInfo.post || '未分配岗位' }}</p>
          </div>
          
          <div class="profile-details">
  
            <div class="detail-item" v-if="growthInfo">
              <div class="detail-icon"><TrophyOutlined /></div>
              <div class="detail-content">
                <span class="detail-label">当前积分</span>
                <span class="detail-value">{{ growthInfo.integral || 0 }}</span>
              </div>
            </div>
            <!-- 等级与进度信息 -->
            <div class="detail-item" v-if="growthInfo">
              <div class="detail-icon"><CrownOutlined /></div>
              <div class="detail-content" style="flex: 1;">
                <div style="display: flex; justify-content: space-between; margin-bottom: 4px;">
                  <span class="detail-label">当前等级</span>
                  <span class="detail-value" :style="{ color: growthInfo.level?.color || 'inherit' }">
                    {{ growthInfo.level?.title || '暂无等级' }}
                  </span>
                </div>
                  <div v-if="growthInfo.nextLevel" style="margin-top: 8px;">
                    <div style="display: flex; justify-content: space-between; font-size: 12px; color: #64748b; margin-bottom: 4px;">
                      <span>距离 下一等级</span> 
                    </div>
                    <a-progress 
                      :percent="getGrowthProgressPercent(growthInfo)" 
                      :show-info="false" 
                      size="small" 
                      :stroke-color="{ '0%': '#108ee9', '100%': '#87d068' }"
                    />
                  </div>
                  <div v-else style="margin-top: 8px; font-size: 12px; color: #10b981;">
                    已达到最高等级
                  </div>
              </div>
            </div>
            
            <a-divider style="margin: 12px 0;" v-if="growthInfo" />

            <div class="detail-item">
              <div class="detail-icon"><UserOutlined /></div>
              <div class="detail-content">
                <span class="detail-label">登录账号</span>
                <span class="detail-value">{{ userInfo.username }}</span>
              </div>
            </div>
            <div class="detail-item">
              <div class="detail-icon"><MobileOutlined /></div>
              <div class="detail-content">
                <span class="detail-label">手机号码</span>
                <span class="detail-value">{{ userInfo.phone || '暂无' }}</span>
              </div>
            </div>
            <div class="detail-item">
              <div class="detail-icon"><MailOutlined /></div>
              <div class="detail-content">
                <span class="detail-label">电子邮箱</span>
                <span class="detail-value">{{ userInfo.email || '暂无' }}</span>
              </div>
            </div>
            <div class="detail-item">
              <div class="detail-icon"><SafetyCertificateOutlined /></div>
              <div class="detail-content">
                <span class="detail-label">所属角色</span>
                <span class="detail-value">{{ userInfo.roles?.join(', ') || '暂无' }}</span>
              </div>
            </div>
          </div>
        </div>
      </a-col>

      <!-- 右侧：标签页 (基本信息、修改密码) -->
      <a-col :xs="24" :sm="24" :md="16" :lg="16" :xl="18">
        <div class="content-card elegant-card">
          <a-tabs v-model:activeKey="activeTab" class="elegant-tabs">
            
            <!-- 基本信息 -->
            <a-tab-pane key="basic" tab="基本信息">
              <div class="tab-content-inner">
                <a-form 
                  ref="basicFormRef" 
                  :model="basicFormData" 
                  :rules="basicRules" 
                  layout="vertical"
                  class="elegant-form max-w-2xl"
                >
                  <a-row :gutter="24">
                    <a-col :span="12">
                      <a-form-item label="用户昵称" name="nickname">
                        <a-input v-model:value="basicFormData.nickname" placeholder="请输入昵称" />
                      </a-form-item>
                    </a-col>
                    <a-col :span="12">
                      <a-form-item label="性别" name="gender">
                        <a-radio-group v-model:value="basicFormData.gender" class="elegant-radio">
                          <a-radio :value="0">男</a-radio>
                          <a-radio :value="1">女</a-radio>
                          <a-radio :value="2">未知</a-radio>
                        </a-radio-group>
                      </a-form-item>
                    </a-col>
                    <a-col :span="12">
                      <a-form-item label="手机号码" name="phone">
                        <a-input v-model:value="basicFormData.phone" placeholder="请输入手机号码" />
                      </a-form-item>
                    </a-col>
                    <a-col :span="12">
                      <a-form-item label="电子邮箱" name="email">
                        <a-input v-model:value="basicFormData.email" placeholder="请输入电子邮箱" />
                      </a-form-item>
                    </a-col>
                    <a-col :span="24">
                      <a-form-item label="个人简介" name="remark">
                        <a-textarea v-model:value="basicFormData.remark" :rows="4" placeholder="请输入个人简介" />
                      </a-form-item>
                    </a-col>
                  </a-row>
                  <div class="form-actions">
                    <a-button type="primary" class="gradient-btn-lg" @click="handleUpdateBasic" :loading="basicLoading">
                      保存修改
                    </a-button>
                  </div>
                </a-form>
              </div>
            </a-tab-pane>

            <!-- 修改密码 -->
            <a-tab-pane key="password" tab="修改密码">
              <div class="tab-content-inner">
                <a-form 
                  ref="pwdFormRef" 
                  :model="pwdFormData" 
                  :rules="pwdRules" 
                  layout="vertical"
                  class="elegant-form max-w-md"
                >
                  <a-form-item label="旧密码" name="oldPassword">
                    <a-input-password v-model:value="pwdFormData.oldPassword" placeholder="请输入当前密码" />
                  </a-form-item>
                  <a-form-item label="新密码" name="newPassword">
                    <a-input-password v-model:value="pwdFormData.newPassword" placeholder="请输入新密码 (至少6位)" />
                  </a-form-item>
                  <a-form-item label="确认新密码" name="confirmPassword">
                    <a-input-password v-model:value="pwdFormData.confirmPassword" placeholder="请再次输入新密码" />
                  </a-form-item>
                  
                  <div class="form-actions mt-6">
                    <a-button type="primary" class="gradient-btn-lg" @click="handleUpdatePassword" :loading="pwdLoading">
                      确认修改
                    </a-button>
                  </div>
                </a-form>
              </div>
            </a-tab-pane>



          </a-tabs>
        </div>
      </a-col>
    </a-row>

    <!-- 头像裁剪弹窗 (仅占位演示) -->
    <a-modal v-model:open="avatarModalVisible" title="修改头像" :footer="null" class="glass-modal" width="400px" centered>
      <div class="avatar-upload-placeholder">
        <ImageUpload v-model="avatarUploadValue" value-type="id" />
        <div class="mt-6 text-center">
          <a-button type="primary" class="gradient-btn" @click="handleAvatarSave" :loading="avatarLoading">
            确认更换
          </a-button>
        </div>
      </div>
    </a-modal>

  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { 
  UserOutlined, MobileOutlined, MailOutlined, SafetyCertificateOutlined,
  CameraOutlined, TrophyOutlined, CrownOutlined
} from '@ant-design/icons-vue'
import type { FormInstance } from 'ant-design-vue'
import ImageUpload from '@/components/ImageUpload/index.vue'
import { getProfile, updateProfile, updateProfilePassword, getGrowthAccount, type UserGrowthAccountVo } from '@/api/system/profile.ts'
import { getRequestId } from "@/utils/idUtils.ts";

// -- 数据源 --

const defaultAvatar = 'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png'

// 用户信息
const userInfo = reactive({
  id: 0,
  username: '',
  nickname: '',
  avatar: '',
  department: '',
  post: '',
  phone: '',
  email: '',
  roles: [] as string[],
  gender: 0,
  remark: ''
})

// 成长账户信息
const growthInfo = ref<UserGrowthAccountVo | null>(null)

const getGrowthProgressPercent = (growth: UserGrowthAccountVo | null) => {
  if (!growth?.nextLevel) {
    return 100
  }

  const currentGrowthValue = Number(growth.growthValue || 0)
  const currentLevelGrowthValue = Number(growth.level?.growthValue || 0)
  const nextLevelGrowthValue = Number(growth.nextLevel.growthValue || 0)

  if (nextLevelGrowthValue <= currentLevelGrowthValue) {
    return currentGrowthValue >= nextLevelGrowthValue ? 100 : 0
  }

  const percent =
    ((currentGrowthValue - currentLevelGrowthValue) /
      (nextLevelGrowthValue - currentLevelGrowthValue)) *
    100

  return Math.max(0, Math.min(100, Math.floor(percent)))
}

// -- 状态管理 --

const activeTab = ref('basic')

// -- 基本信息表单 --

const basicFormRef = ref<FormInstance>()
const basicLoading = ref(false)
const basicFormData = reactive({
  nickname: '',
  phone: '',
  email: '',
  gender: 0,
  remark: ''
})

const requestId = ref<string>('')

const basicRules = {
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  phone: [{ pattern: /^1\d{10}$/, message: '请输入正确的11位手机号', trigger: 'blur' }],
  email: [{ type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }]
}

const initBasicForm = async () => {
  const res = await getProfile()
  if (res.code === 200 && res.data) {
    Object.assign(userInfo, res.data)
    basicFormData.nickname = userInfo.nickname || ''
    basicFormData.phone = userInfo.phone || ''
    basicFormData.email = userInfo.email || ''
    basicFormData.gender = userInfo.gender || 0
    basicFormData.remark = userInfo.remark || ''
  }

  try {
    const growthRes = await getGrowthAccount()
    if (growthRes.code === 200 && growthRes.data) {
      growthInfo.value = growthRes.data
    }
  } catch (error) {
    console.error('获取成长账户信息失败', error)
  }
}

const handleUpdateBasic = async () => {
  try {
    await basicFormRef.value?.validate()
    basicLoading.value = true
    
    const updateData = {
      nickname: basicFormData.nickname,
      phone: basicFormData.phone,
      email: basicFormData.email,
      gender: basicFormData.gender,
      remark: basicFormData.remark,
      avatar: userInfo.avatar
    }

    requestId.value = getRequestId();
    const res = await updateProfile(updateData, requestId.value)
    if (res.code === 200) {
      message.success('基本信息已更新')
      await initBasicForm() // reload data
    }
  } catch (error) {
    console.error('表单校验或提交失败', error)
  } finally {
    basicLoading.value = false
  }
}

// -- 修改密码表单 --

const pwdFormRef = ref<FormInstance>()
const pwdLoading = ref(false)
const pwdFormData = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirmPassword = async (_rule: any, value: string) => {
  if (value !== pwdFormData.newPassword) {
    return Promise.reject('两次输入的密码不一致')
  }
  return Promise.resolve()
}

const pwdRules = {
  oldPassword: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handleUpdatePassword = async () => {
  try {
    await pwdFormRef.value?.validate()
    pwdLoading.value = true
    
    const updateData = {
      oldPassword: pwdFormData.oldPassword,
      newPassword: pwdFormData.newPassword
    }

    requestId.value = getRequestId();
    const res = await updateProfilePassword(updateData, requestId.value)
    if (res.code === 200) {
      message.success('密码修改成功，请重新登录')
      pwdFormRef.value?.resetFields()
    }
  } catch (error) {
    console.error('表单校验或提交失败', error)
  } finally {
    pwdLoading.value = false
  }
}

// -- 头像上传 --

const avatarModalVisible = ref(false)
const avatarUploadValue = ref<string | undefined>(undefined)
const avatarLoading = ref(false)

const handleAvatarClick = () => {
  avatarUploadValue.value = undefined
  avatarModalVisible.value = true
}

const handleAvatarSave = async () => {
  if (!avatarUploadValue.value) {
    message.warning('请先上传图片')
    return
  }
  avatarLoading.value = true
  try {
    const updateData = {
      nickname: basicFormData.nickname,
      phone: basicFormData.phone,
      email: basicFormData.email,
      gender: basicFormData.gender,
      remark: basicFormData.remark,
      avatar: avatarUploadValue.value
    }
    requestId.value = getRequestId();
    const res = await updateProfile(updateData, requestId.value)
    if (res.code === 200) {
      message.success('头像更换成功')
      avatarModalVisible.value = false
      await initBasicForm() // reload data
    }
  } catch (error) {
    console.error('头像上传失败', error)
  } finally {
    avatarLoading.value = false
  }
}



onMounted(() => {
  initBasicForm()
})

</script>

<style scoped>
@import '@/assets/styles/modern-dashboard.css';



/* 卡片通用样式 (基于 modern-dashboard.css 的拓展) */
.elegant-card {
  background: var(--component-bg, #ffffff);
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.03);
  padding: 24px;
  margin-bottom: 24px;
  transition: all 0.3s ease;
  border: 1px solid var(--border-color, #f1f5f9);
}
.dark .elegant-card {
  background: #1e293b;
  border-color: #334155;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
}

/* 个人资料卡片 - 头部 */
.profile-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 24px;
  border-bottom: 1px dashed var(--border-color, #f1f5f9);
  margin-bottom: 24px;
}
.dark .profile-header {
  border-color: #334155;
}

.avatar-wrapper {
  position: relative;
  margin-bottom: 16px;
}
.profile-avatar {
  border: 4px solid #fff;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
}
.dark .profile-avatar {
  border-color: #1e293b;
}

.avatar-upload-btn {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 32px;
  height: 32px;
  background: var(--app-primary-color, #6366f1);
  color: #fff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 2px 8px rgba(99, 102, 241, 0.4);
  transition: all 0.2s;
  border: 2px solid #fff;
}
.dark .avatar-upload-btn {
  border-color: #1e293b;
}
.avatar-upload-btn:hover {
  transform: scale(1.1);
  background: var(--app-primary-color-hover, #4f46e5);
}

.profile-name {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-color, #1e293b);
  margin: 0 0 8px;
}
.dark .profile-name {
  color: #f8fafc;
}

.profile-desc {
  font-size: 14px;
  color: var(--text-secondary, #64748b);
  margin: 0;
}
.dark .profile-desc {
  color: #94a3b8;
}

/* 个人资料卡片 - 详情 */
.profile-details {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.detail-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.detail-icon {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  background: var(--bg-hover, #f8fafc);
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--app-primary-color, #6366f1);
  font-size: 16px;
}
.dark .detail-icon {
  background: #0f172a;
}

.detail-content {
  display: flex;
  flex-direction: column;
}

.detail-label {
  font-size: 12px;
  color: var(--text-secondary, #64748b);
  margin-bottom: 4px;
}
.dark .detail-label {
  color: #94a3b8;
}

.detail-value {
  font-size: 14px;
  color: var(--text-color, #334155);
  font-weight: 500;
}
.dark .detail-value {
  color: #e2e8f0;
}

/* 标签页内容区 */
.tab-content-inner {
  padding: 16px 0;
}

.max-w-2xl {
  max-width: 42rem;
}
.max-w-md {
  max-width: 28rem;
}

.mt-6 {
  margin-top: 24px;
}

.text-center {
  text-align: center;
}

.text-success {
  color: #10b981;
}
.text-error {
  color: #ef4444;
}
</style>
