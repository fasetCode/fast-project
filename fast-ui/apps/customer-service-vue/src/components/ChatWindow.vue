<template>
  <div class="chat-window-container">
    <!-- Chat Content -->
    <template v-if="activeCustomer">
      <div class="chat-header">
        <div class="header-left">
          <a-button 
            v-if="chatStore.isMobileView" 
            type="text" 
            class="back-btn" 
            @click="chatStore.backToList"
          >
            <template #icon><left-outlined /></template>
          </a-button>
          <div class="active-avatar-wrapper">
            <img :src="activeCustomer.avatar" class="active-avatar" />
            <div v-if="activeCustomer.online" class="online-status-pulse"></div>
          </div>
          <div class="header-info">
            <span class="name">{{ activeCustomer.name }}</span>
            <div class="status-row">
              <span class="status-dot" :class="{ online: activeCustomer.online }"></span>
              <span class="status-text">{{ activeCustomer.online ? '正在线上' : '离线状态' }}</span>
            </div>
          </div>
        </div>
        <div class="header-right">
          <div class="header-action-group">
            <a-button type="text" class="header-btn"><template #icon><phone-outlined /></template></a-button>
            <a-button type="text" class="header-btn"><template #icon><video-camera-outlined /></template></a-button>
          </div>
          <a-divider type="vertical" style="height: 24px; margin: 0 12px" />
          <a-button type="text" class="header-btn"><template #icon><more-outlined /></template></a-button>
        </div>
      </div>

      <div class="message-list-wrapper">
        <div class="message-list" ref="messageListRef">
          <div
            v-for="msg in messages"
            :key="msg.id"
            class="message-item"
            :class="{ 'self': msg.senderId === 'cs_admin' }"
          >
            <div class="avatar-col">
              <img :src="msg.senderId === 'cs_admin' ? 'https://api.dicebear.com/7.x/avataaars/svg?seed=admin' : activeCustomer.avatar" class="msg-avatar" />
            </div>
            <div class="msg-content-col">
              <div class="bubble-container">
                <div class="bubble">
                  <div class="text">{{ msg.content }}</div>
                </div>
              </div>
              <div class="msg-meta">
                <span class="time">{{ formatTime(msg.timestamp) }}</span>
                <span v-if="msg.senderId === 'cs_admin'" class="status-check">已读</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="input-area-container">
        <div class="input-area">
          <div class="tools">
            <div class="tool-left">
              <a-tooltip title="表情"><a-button type="text" class="tool-btn"><template #icon><smile-outlined /></template></a-button></a-tooltip>
              <a-tooltip title="图片"><a-button type="text" class="tool-btn"><template #icon><picture-outlined /></template></a-button></a-tooltip>
              <a-tooltip title="文件"><a-button type="text" class="tool-btn"><template #icon><folder-outlined /></template></a-button></a-tooltip>
              <a-tooltip title="语音"><a-button type="text" class="tool-btn"><template #icon><audio-outlined /></template></a-button></a-tooltip>
            </div>
            <div class="tool-right">
              <a-button type="text" class="history-btn">
                <template #icon><history-outlined /></template>
                <span>聊天记录</span>
              </a-button>
            </div>
          </div>
          <div class="text-wrapper">
            <a-textarea
              v-model:value="inputValue"
              placeholder="在此输入您的消息..."
              :auto-size="{ minRows: 2, maxRows: 5 }"
              class="custom-textarea"
              @press-enter="handleSend"
            />
          </div>
          <div class="footer">
            <div class="shortcuts">
              <span><b>Enter</b> 发送</span>
              <span class="divider"></span>
              <span><b>Shift + Enter</b> 换行</span>
            </div>
            <a-button type="primary" :disabled="!inputValue.trim()" @click="handleSend" class="send-btn">
              发送消息
              <template #icon><send-outlined /></template>
            </a-button>
          </div>
        </div>
      </div>
    </template>

    <!-- Empty State -->
    <div v-else class="empty-state">
      <div class="empty-content">
        <div class="empty-illustration">
          <div class="circle circle-1"></div>
          <div class="circle circle-2"></div>
          <message-filled style="font-size: 64px; color: #0084ff" />
        </div>
        <h3>开启专业服务</h3>
        <p>选择一个活跃的会话开始处理客户请求</p>
        <a-button type="primary" shape="round" size="large" class="start-btn" @click="handleSelectFirst">查看待办任务</a-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, nextTick, watch } from 'vue'
import { useChatStore } from '@/stores/chat'
import dayjs from 'dayjs'
import {
  PictureOutlined,
  FolderOutlined,
  SmileOutlined,
  MessageOutlined,
  PhoneOutlined,
  VideoCameraOutlined,
  MoreOutlined,
  HistoryOutlined,
  AudioOutlined,
  SendOutlined,
  MessageFilled,
  LeftOutlined
} from '@ant-design/icons-vue'
import { useWebSocket } from '@/utils/websocket'

const chatStore = useChatStore()
const wsService = useWebSocket()
const inputValue = ref('')
const messageListRef = ref<HTMLElement | null>(null)

const activeCustomer = computed(() => {
  return chatStore.customers.find(c => c.id === chatStore.activeCustomerId)
})

const messages = computed(() => {
  if (!chatStore.activeCustomerId) return []
  return chatStore.messages[chatStore.activeCustomerId] || []
})

const formatTime = (time: number) => {
  return dayjs(time).format('HH:mm')
}

const scrollToBottom = () => {
  nextTick(() => {
    if (messageListRef.value) {
      messageListRef.value.scrollTop = messageListRef.value.scrollHeight
    }
  })
}

const handleSelectFirst = () => {
  if (chatStore.customers.length > 0) {
    chatStore.setActiveCustomer(chatStore.customers[0].id)
  }
}

watch(messages, scrollToBottom, { deep: true })
watch(() => chatStore.activeCustomerId, scrollToBottom)

const handleSend = (e?: KeyboardEvent) => {
  if (e && e.shiftKey) return
  if (e) e.preventDefault()
  
  if (!inputValue.value.trim() || !chatStore.activeCustomerId) return

  const newMsg = {
    id: Date.now().toString(),
    content: inputValue.value,
    senderId: 'cs_admin',
    receiverId: chatStore.activeCustomerId,
    timestamp: Date.now(),
    type: 'text' as const
  }

  chatStore.addMessage(chatStore.activeCustomerId, newMsg)
  wsService.send(inputValue.value)
  inputValue.value = ''
  scrollToBottom()
}
</script>

<style scoped>
.chat-window-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: #fff;
}

.chat-header {
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 32px;
  background-color: #fff;
  border-bottom: 1px solid #f0f0f0;
  z-index: 10;
}

.header-left {
  display: flex;
  align-items: center;
}

.active-avatar-wrapper {
  position: relative;
  margin-right: 16px;
}

.active-avatar {
  width: 48px;
  height: 48px;
  border-radius: 14px;
  object-fit: cover;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.online-status-pulse {
  position: absolute;
  bottom: -2px;
  right: -2px;
  width: 12px;
  height: 12px;
  background-color: #10b981;
  border: 2px solid #fff;
  border-radius: 50%;
}

.header-info .name {
  font-size: 18px;
  font-weight: 700;
  color: #1e1e2d;
  display: block;
}

.status-row {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-top: 4px;
}

.status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background-color: #a2a3b7;
}

.status-dot.online {
  background-color: #10b981;
  box-shadow: 0 0 8px #10b981;
}

.status-text {
  font-size: 12px;
  color: #a2a3b7;
  font-weight: 500;
}

.header-right {
  display: flex;
  align-items: center;
}

.header-action-group {
  display: flex;
  gap: 4px;
}

.header-btn {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
  color: #7e8299;
  transition: all 0.3s ease;
}

.header-btn:hover {
  background-color: #f4f7f9;
  color: #1e1e2d;
}

.message-list-wrapper {
  flex: 1;
  overflow: hidden;
  background-color: #f9fafc;
  position: relative;
}

.message-list {
  height: 100%;
  padding: 32px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
}

.message-item {
  display: flex;
  margin-bottom: 28px;
  max-width: 75%;
}

.message-item.self {
  align-self: flex-end;
  flex-direction: row-reverse;
}

.msg-avatar {
  width: 40px;
  height: 40px;
  border-radius: 12px;
  object-fit: cover;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
}

.msg-content-col {
  margin: 0 16px;
  display: flex;
  flex-direction: column;
}

.self .msg-content-col {
  align-items: flex-end;
}

.bubble-container {
  position: relative;
}

.bubble {
  padding: 12px 18px;
  border-radius: 18px;
  font-size: 15px;
  line-height: 1.6;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.03);
}

.message-item:not(.self) .bubble {
  background-color: #fff;
  color: #1e1e2d;
  border-top-left-radius: 4px;
}

.self .bubble {
  background: linear-gradient(135deg, #0084ff 0%, #00c6ff 100%);
  color: #fff;
  border-top-right-radius: 4px;
  box-shadow: 0 4px 15px rgba(0, 132, 255, 0.2);
}

.msg-meta {
  margin-top: 6px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.time {
  font-size: 11px;
  color: #a2a3b7;
  font-weight: 500;
}

.status-check {
  font-size: 11px;
  color: #0084ff;
  font-weight: 600;
}

.input-area-container {
  padding: 0 32px 32px;
  background-color: #f9fafc;
}

.input-area {
  background-color: #fff;
  border-radius: 20px;
  padding: 12px 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.04);
  border: 1px solid #f0f0f0;
}

.tools {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
  padding-bottom: 8px;
  border-bottom: 1px solid #f8f9fa;
}

.tool-left {
  display: flex;
  gap: 8px;
}

.tool-btn {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  color: #a2a3b7;
  transition: all 0.3s ease;
}

.tool-btn:hover {
  background-color: #f4f7f9;
  color: #0084ff;
}

.history-btn {
  color: #a2a3b7;
  font-size: 13px;
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 0 12px;
  border-radius: 8px;
}

.history-btn:hover {
  color: #1e1e2d;
  background-color: #f4f7f9;
}

.custom-textarea {
  border: none;
  padding: 8px 0;
  font-size: 15px;
  resize: none;
  color: #1e1e2d;
}

.custom-textarea::placeholder {
  color: #a2a3b7;
}

.custom-textarea:focus {
  box-shadow: none;
}

.footer {
  margin-top: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.shortcuts {
  font-size: 12px;
  color: #a2a3b7;
  display: flex;
  align-items: center;
}

.shortcuts b {
  color: #7e8299;
}

.shortcuts .divider {
  width: 1px;
  height: 12px;
  background-color: #e0e0e0;
  margin: 0 10px;
}

.send-btn {
  height: 40px;
  padding: 0 24px;
  border-radius: 12px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
  box-shadow: 0 4px 12px rgba(0, 132, 255, 0.2);
}

.empty-state {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f9fafc;
}

.empty-content {
  text-align: center;
  max-width: 400px;
}

.empty-illustration {
  position: relative;
  width: 120px;
  height: 120px;
  margin: 0 auto 32px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.circle {
  position: absolute;
  border-radius: 50%;
  border: 2px solid rgba(0, 132, 255, 0.1);
}

.circle-1 {
  width: 120px;
  height: 120px;
  animation: pulse 3s infinite ease-in-out;
}

.circle-2 {
  width: 80px;
  height: 80px;
  animation: pulse 3s infinite ease-in-out 1s;
}

@keyframes pulse {
  0% { transform: scale(0.8); opacity: 0.5; }
  50% { transform: scale(1.1); opacity: 0.2; }
  100% { transform: scale(0.8); opacity: 0.5; }
}

.empty-content h3 {
  font-size: 24px;
  font-weight: 700;
  color: #1e1e2d;
  margin-bottom: 12px;
}

.empty-content p {
  font-size: 15px;
  color: #7e8299;
  line-height: 1.6;
  margin-bottom: 32px;
}

.start-btn {
  padding: 0 32px;
  font-weight: 600;
}
@media (max-width: 768px) {
  .chat-header {
    height: 60px;
    padding: 0 16px;
  }
  .active-avatar {
    width: 36px;
    height: 36px;
    border-radius: 10px;
  }
  .header-info .name {
    font-size: 16px;
  }
  .header-right {
    display: none;
  }
  .message-list {
    padding: 16px;
  }
  .message-item {
    max-width: 90%;
    margin-bottom: 20px;
  }
  .input-area-container {
    padding: 0 12px 12px;
  }
  .input-area {
    padding: 8px 12px;
    border-radius: 12px;
  }
  .tools {
    display: none;
  }
  .back-btn {
    margin-right: 8px;
    padding: 0;
    width: 32px;
    height: 32px;
  }
}
</style>
