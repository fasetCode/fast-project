<template>
  <div class="app-container">
    <div class="chat-page-container" :class="{ 'mobile-view': chatStore.isMobileView }">
      <NavSidebar v-if="!chatStore.isMobileView" />
<!--      {{chatStore.userId}}-->
      <div class="main-content">
        <transition name="fade-slide" mode="out-in">
          <div 
            v-if="!chatStore.isMobileView || !chatStore.showChatOnMobile" 
            key="list"
            class="left-panel"
          >
            <CustomerList />
          </div>
          <div 
            v-else-if="chatStore.isMobileView && chatStore.showChatOnMobile" 
            key="chat-mobile"
            class="right-panel mobile"
          >
            <ChatWindow />
          </div>
        </transition>

        <!-- Desktop Chat Window -->
        <div 
          v-if="!chatStore.isMobileView" 
          class="right-panel desktop"
        >
          <ChatWindow />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import ChatWindow from '@/components/ChatWindow.vue'
import CustomerList from '@/components/CustomerList.vue'
import NavSidebar from '@/components/NavSidebar.vue'
import { useWebSocket } from '@/utils/websocket'
import { useChatStore } from '@/stores/chat'
import {getWsDomain, getWsToken} from '@/api'

const route = useRoute()
const wsService = useWebSocket()
const chatStore = useChatStore()

const handleResize = () => {
  chatStore.updateMobileStatus()
}

const initWsConnection = async () => {

  let tokenRes = await getWsToken({ userId: chatStore.userId, groupId: chatStore.groupId, token: chatStore.token });

  console.log("token")
  console.log(tokenRes.data.data)

  try {
    const res = await getWsDomain()
    if (res.data.code === 200 && res.data.data) {
      let wsUrl = res.data.data;
      
      const params = new URLSearchParams();
      
      const appId = route.query.appId as string;
      if (appId) {
        params.append('appId', appId);
      } else {
        params.append('appId', 'chat');
      }
      
      if (tokenRes.data?.data) {
        params.append('token', tokenRes.data.data);
      }
      if (chatStore.userId) {
        params.append('userId', chatStore.userId);
      }
      if (chatStore.groupId) {
        params.append('groupId', chatStore.groupId);
      }
      
      const separator = wsUrl.includes('?') ? '&' : '?';
      wsUrl = `${wsUrl}${separator}${params.toString()}`;
      
      wsService.setUrl(wsUrl);
    }
  } catch (error) {
    console.error('Failed to get ws domain:', error)
  }
  wsService.connect()
}

onMounted(() => {
  chatStore.userId = route.query.userId as string || null
  chatStore.groupId = route.query.groupId as string || null
  chatStore.token = route.query.token as string || null
  initWsConnection()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
})
</script>

<style scoped>
.app-container {
  height: 100vh;
  width: 100vw;
  background-color: #f0f2f5;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.chat-page-container {
  display: flex;
  height: 95vh;
  width: 98vw;
  max-width: 1600px;
  overflow: hidden;
  background-color: #fff;
  border-radius: 24px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.3);
  backdrop-filter: blur(20px);
}

.main-content {
  flex: 1;
  display: flex;
  height: 100%;
  position: relative;
  overflow: hidden;
}

.left-panel {
  width: 320px;
  flex-shrink: 0;
  border-right: 1px solid #f0f0f0;
  background-color: #fff;
  height: 100%;
}

.right-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
  background-color: #fff;
  height: 100%;
}

/* Transitions */
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: all 0.3s ease;
}

.fade-slide-enter-from {
  opacity: 0;
  transform: translateX(30px);
}

.fade-slide-leave-to {
  opacity: 0;
  transform: translateX(-30px);
}

@media (max-width: 1200px) {
  .left-panel {
    width: 280px;
  }
}

@media (max-width: 768px) {
  .app-container {
    padding: 0;
  }
  .chat-page-container {
    height: 100vh;
    width: 100vw;
    border-radius: 0;
    box-shadow: none;
    border: none;
  }
  .left-panel {
    width: 100%;
    border-right: none;
  }
  .right-panel.mobile {
    width: 100%;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 100;
  }
}
</style>
