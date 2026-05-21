<template>
  <div class="nav-sidebar">
    <div class="logo">
      <div class="logo-inner">
        <customer-service-outlined style="font-size: 24px; color: #fff" />
      </div>
    </div>
    <div class="menu-items">
      <div v-for="item in menuItems" :key="item.id" 
           class="menu-item" 
           :class="{ active: activeMenu === item.id }"
           @click="activeMenu = item.id">
        <component :is="item.icon" style="font-size: 20px" />
        <span class="menu-dot" v-if="item.hasBadge"></span>
      </div>
    </div>
    <div class="bottom-actions">
      <div class="menu-item"><setting-outlined style="font-size: 20px" /></div>
      <a-avatar src="https://api.dicebear.com/7.x/avataaars/svg?seed=admin" :size="32" class="admin-avatar" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import {
  MessageOutlined,
  TeamOutlined,
  PieChartOutlined,
  SettingOutlined,
  CustomerServiceOutlined,
  ClockCircleOutlined
} from '@ant-design/icons-vue'

const activeMenu = ref('chat')

const menuItems = [
  { id: 'chat', icon: MessageOutlined, hasBadge: true },
  { id: 'contacts', icon: TeamOutlined, hasBadge: false },
  { id: 'history', icon: ClockCircleOutlined, hasBadge: false },
  { id: 'stats', icon: PieChartOutlined, hasBadge: false },
]
</script>

<style scoped>
.nav-sidebar {
  width: 68px;
  height: 100%;
  background: linear-gradient(180deg, #1e1e2d 0%, #151521 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px 0;
  flex-shrink: 0;
  box-shadow: 4px 0 10px rgba(0, 0, 0, 0.1);
}

.logo {
  margin-bottom: 40px;
}

.logo-inner {
  width: 42px;
  height: 42px;
  background: linear-gradient(135deg, #0084ff 0%, #00c6ff 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 12px rgba(0, 132, 255, 0.3);
}

.menu-items {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.menu-item {
  width: 44px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
  color: #a2a3b7;
  cursor: pointer;
  position: relative;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.menu-item:hover {
  background-color: rgba(255, 255, 255, 0.05);
  color: #fff;
}

.menu-item.active {
  background-color: rgba(0, 132, 255, 0.15);
  color: #0084ff;
}

.menu-dot {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 6px;
  height: 6px;
  background-color: #ff4d4f;
  border-radius: 50%;
  border: 1px solid #1e1e2d;
}

.bottom-actions {
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
}

.admin-avatar {
  border: 2px solid rgba(255, 255, 255, 0.1);
  cursor: pointer;
  transition: transform 0.3s ease;
}

.admin-avatar:hover {
  transform: scale(1.1);
  border-color: #0084ff;
}
@media (max-width: 768px) {
  .nav-sidebar {
    display: none;
  }
}
</style>
