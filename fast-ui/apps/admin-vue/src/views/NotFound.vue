<script setup lang="ts">
import { useRouter } from 'vue-router'
import { HomeOutlined, RollbackOutlined } from '@ant-design/icons-vue'

const router = useRouter()

const goHome = () => {
  router.push('/')
}

const goBack = () => {
  router.back()
}
</script>

<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <div class="error-code">404</div>
      <div class="error-title">页面不存在</div>
      <div class="error-desc">抱歉，您访问的页面不存在或已被移除</div>
      <div class="error-actions">
        <a-button type="primary" size="large" class="action-btn primary" @click="goHome">
          <template #icon><HomeOutlined /></template>
          返回首页
        </a-button>
        <a-button size="large" class="action-btn" @click="goBack">
          <template #icon><RollbackOutlined /></template>
          返回上一页
        </a-button>
      </div>
    </div>
    <div class="bg-decoration">
      <div class="bg-circle circle-1"></div>
      <div class="bg-circle circle-2"></div>
      <div class="bg-circle circle-3"></div>
    </div>
  </div>
</template>

<style scoped>
.not-found-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);
  position: relative;
  overflow: hidden;
}

html.dark .not-found-container {
  background: linear-gradient(135deg, #0f172a 0%, #1e293b 100%);
}

.not-found-content {
  position: relative;
  z-index: 10;
  text-align: center;
  padding: 40px;
}

.error-code {
  font-size: 160px;
  font-weight: 800;
  line-height: 1;
  background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 50%, #d946ef 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 20px 60px rgba(99, 102, 241, 0.3);
  margin-bottom: 16px;
  animation: float 3s ease-in-out infinite;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

.error-title {
  font-size: 28px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 12px;
}

html.dark .error-title {
  color: #f1f5f9;
}

.error-desc {
  font-size: 16px;
  color: #64748b;
  margin-bottom: 32px;
}

html.dark .error-desc {
  color: #94a3b8;
}

.error-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
}

.action-btn {
  height: 44px;
  padding: 0 28px;
  border-radius: 12px;
  font-size: 15px;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
}

.action-btn.primary {
  background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%);
  border: none;
  box-shadow: 0 4px 14px rgba(99, 102, 241, 0.4);
  transition: all 0.3s ease;
}

.action-btn.primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(99, 102, 241, 0.5);
  background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%);
}

.action-btn:not(.primary) {
  background: white;
  border: 1px solid #e2e8f0;
  color: #475569;
}

html.dark .action-btn:not(.primary) {
  background: #1e293b;
  border-color: #334155;
  color: #e2e8f0;
}

.action-btn:not(.primary):hover {
  border-color: #6366f1;
  color: #6366f1;
}

/* 背景装饰 */
.bg-decoration {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.bg-circle {
  position: absolute;
  border-radius: 50%;
  background: linear-gradient(135deg, rgba(99, 102, 241, 0.1) 0%, rgba(139, 92, 246, 0.05) 100%);
}

.circle-1 {
  width: 400px;
  height: 400px;
  top: -100px;
  right: -100px;
  animation: drift 8s ease-in-out infinite;
}

.circle-2 {
  width: 300px;
  height: 300px;
  bottom: -50px;
  left: -50px;
  animation: drift 10s ease-in-out infinite reverse;
}

.circle-3 {
  width: 200px;
  height: 200px;
  top: 40%;
  left: 20%;
  animation: drift 12s ease-in-out infinite;
}

@keyframes drift {
  0%, 100% {
    transform: translate(0, 0) rotate(0deg);
  }
  33% {
    transform: translate(20px, -20px) rotate(5deg);
  }
  66% {
    transform: translate(-10px, 10px) rotate(-3deg);
  }
}
</style>