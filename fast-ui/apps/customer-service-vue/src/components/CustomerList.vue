<template>
  <div class="customer-list-container">
    <div class="header">
      <div class="title">
        <span>消息列表</span>
        <div class="header-actions">
          <a-button type="text" class="action-btn"><template #icon><filter-outlined /></template></a-button>
          <a-button type="text" class="action-btn"><template #icon><form-outlined /></template></a-button>
        </div>
      </div>
      <div class="search-wrapper">
        <a-input v-model:value="searchText" placeholder="搜索联系人..." class="custom-search">
          <template #prefix><search-outlined style="color: #a2a3b7" /></template>
        </a-input>
      </div>
    </div>
    <div class="customer-list">
      <div
        v-for="customer in filteredCustomers"
        :key="customer.id"
        class="customer-item"
        :class="{ active: chatStore.activeCustomerId === customer.id }"
        @click="chatStore.setActiveCustomer(customer.id)"
      >
        <div class="avatar-wrapper">
          <div class="avatar-container">
            <img :src="customer.avatar" class="avatar-img" :class="{ grayscale: !customer.online }" />
            <div v-if="customer.online" class="online-status"></div>
          </div>
        </div>
        <div class="info">
          <div class="top">
            <span class="name">{{ customer.name }}</span>
            <span class="time">{{ formatTime(customer.lastTime) }}</span>
          </div>
          <div class="bottom">
            <div class="last-msg">{{ customer.lastMessage }}</div>
            <div v-if="customer.unreadCount > 0" class="unread-count">
              {{ customer.unreadCount > 99 ? '99+' : customer.unreadCount }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useChatStore } from '@/stores/chat'
import dayjs from 'dayjs'
import { SearchOutlined, FilterOutlined, FormOutlined } from '@ant-design/icons-vue'

const chatStore = useChatStore()
const searchText = ref('')

const filteredCustomers = computed(() => {
  if (!searchText.value) return chatStore.customers
  return chatStore.customers.filter(c => c.name.includes(searchText.value))
})

const formatTime = (time?: number) => {
  if (!time) return ''
  const now = dayjs()
  const d = dayjs(time)
  if (now.isSame(d, 'day')) return d.format('HH:mm')
  if (now.subtract(1, 'day').isSame(d, 'day')) return '昨天'
  return d.format('MM/DD')
}
</script>

<style scoped>
.customer-list-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  background-color: #fff;
  width: 100%;
}

.header {
  padding: 32px 24px 20px;
}

.title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  font-size: 22px;
  font-weight: 700;
  color: #1e1e2d;
  letter-spacing: -0.5px;
}

.header-actions {
  display: flex;
  gap: 8px;
}

.action-btn {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f4f7f9;
  border-radius: 10px;
  color: #7e8299;
  transition: all 0.3s ease;
}

.action-btn:hover {
  background-color: #0084ff;
  color: #fff;
}

.search-wrapper {
  margin-bottom: 12px;
}

.custom-search {
  background-color: #f4f7f9;
  border: 1px solid transparent;
  border-radius: 12px;
  padding: 10px 16px;
  transition: all 0.3s ease;
}

.custom-search:focus-within {
  background-color: #fff;
  border-color: #0084ff;
  box-shadow: 0 4px 12px rgba(0, 132, 255, 0.08);
}

.custom-search :deep(.ant-input) {
  background-color: transparent;
  font-size: 14px;
}

.customer-list {
  flex: 1;
  overflow-y: auto;
  padding: 0 16px 20px;
}

.customer-item {
  display: flex;
  padding: 16px;
  margin-bottom: 8px;
  border-radius: 18px;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  border: 1px solid transparent;
}

.customer-item:hover {
  background-color: #f9fafc;
  transform: translateY(-1px);
}

.customer-item.active {
  background-color: #f4f7ff;
  border-color: rgba(0, 132, 255, 0.1);
  box-shadow: 0 4px 15px rgba(0, 132, 255, 0.05);
}

.avatar-wrapper {
  margin-right: 16px;
}

.avatar-container {
  position: relative;
  width: 52px;
  height: 52px;
}

.avatar-img {
  width: 100%;
  height: 100%;
  border-radius: 16px;
  object-fit: cover;
  background-color: #f0f0f0;
}

.online-status {
  position: absolute;
  bottom: -2px;
  right: -2px;
  width: 14px;
  height: 14px;
  background-color: #10b981;
  border: 3px solid #fff;
  border-radius: 50%;
}

.info {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.top {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 6px;
}

.name {
  font-weight: 600;
  font-size: 16px;
  color: #1e1e2d;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.time {
  font-size: 12px;
  color: #a2a3b7;
  font-weight: 500;
}

.bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.last-msg {
  font-size: 14px;
  color: #7e8299;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  flex: 1;
  margin-right: 12px;
}

.unread-count {
  background: linear-gradient(135deg, #ff4d4f 0%, #ff7875 100%);
  color: #fff;
  font-size: 11px;
  font-weight: 700;
  height: 20px;
  min-width: 20px;
  padding: 0 6px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 8px rgba(255, 77, 79, 0.2);
}

.grayscale {
  filter: grayscale(100%);
  opacity: 0.6;
}

@media (max-width: 768px) {
  .header {
    padding: 24px 16px 12px;
  }
  .title {
    font-size: 24px;
    margin-bottom: 20px;
    display: flex; /* Override previous none */
  }
  .search-wrapper {
    display: block; /* Override previous none */
  }
  .info {
    display: flex; /* Override previous none */
  }
  .customer-item {
    justify-content: flex-start; /* Override previous center */
    padding: 16px;
    margin-bottom: 8px;
    border-radius: 18px;
  }
  .avatar-wrapper {
    margin-right: 16px;
  }
  .header-actions {
    display: none;
  }
}
</style>
