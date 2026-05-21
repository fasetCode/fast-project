import { defineStore } from 'pinia'
import { ref } from 'vue'

export interface Message {
  id: string
  content: string
  senderId: string
  receiverId: string
  timestamp: number
  type: 'text' | 'image' | 'file'
}

export interface Customer {
  id: string
  name: string
  avatar: string
  lastMessage?: string
  lastTime?: number
  unreadCount: number
  online: boolean
}

export const useChatStore = defineStore('chat', () => {
  const userId = ref<string | null>(null)
  const groupId = ref<string | null>(null)
  const token = ref<string | null>(null)
  const customers = ref<Customer[]>([
    {
      id: '1',
      name: '张三',
      avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=1',
      lastMessage: '你好，请问有什么可以帮到您的？',
      lastTime: Date.now(),
      unreadCount: 2,
      online: true,
    },
    {
      id: '2',
      name: '李四',
      avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=2',
      lastMessage: '好的，谢谢',
      lastTime: Date.now() - 3600000,
      unreadCount: 0,
      online: false,
    },
    {
      id: '3',
      name: '王五',
      avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=3',
      lastMessage: '我想咨询一下业务',
      lastTime: Date.now() - 7200000,
      unreadCount: 1,
      online: true,
    }
  ])

  const activeCustomerId = ref<string | null>(null)
  const isMobileView = ref(window.innerWidth <= 768)
  const showChatOnMobile = ref(false)
  const messages = ref<Record<string, Message[]>>({
    '1': [
      { id: 'm1', content: '你好，请问有什么可以帮到您的？', senderId: '1', receiverId: 'cs_admin', timestamp: Date.now() - 3600000, type: 'text' },
      { id: 'm2', content: '我想了解一下你们的业务流程。', senderId: 'cs_admin', receiverId: '1', timestamp: Date.now() - 3500000, type: 'text' }
    ],
    '2': [
      { id: 'm3', content: '好的，谢谢', senderId: '2', receiverId: 'cs_admin', timestamp: Date.now() - 7200000, type: 'text' }
    ],
    '3': [
      { id: 'm4', content: '我想咨询一下业务', senderId: '3', receiverId: 'cs_admin', timestamp: Date.now() - 86400000, type: 'text' }
    ]
  })

  const setActiveCustomer = (id: string) => {
    activeCustomerId.value = id
    showChatOnMobile.value = true
    const customer = customers.value.find(c => c.id === id)
    if (customer) {
      customer.unreadCount = 0
    }
  }

  const backToList = () => {
    showChatOnMobile.value = false
  }

  const updateMobileStatus = () => {
    isMobileView.value = window.innerWidth <= 768
  }

  const addMessage = (customerId: string, message: Message) => {
    if (!messages.value[customerId]) {
      messages.value[customerId] = []
    }
    messages.value[customerId].push(message)
    
    // Update last message in customer list
    const customer = customers.value.find(c => c.id === customerId)
    if (customer) {
      customer.lastMessage = message.content
      customer.lastTime = message.timestamp
    }
  }

  return {
    userId,
    groupId,
    token,
    customers,
    activeCustomerId,
    isMobileView,
    showChatOnMobile,
    messages,
    setActiveCustomer,
    backToList,
    updateMobileStatus,
    addMessage
  }
})
