import { useChatStore } from '@/stores/chat'

export class WebSocketService {
  private socket: WebSocket | null = null
  private url: string
  private reconnectTimer: any = null
  private chatStore = useChatStore()

  constructor(url: string = './ws') {
    this.url = url
  }

  connect() {
    if (this.socket && (this.socket.readyState === WebSocket.OPEN || this.socket.readyState === WebSocket.CONNECTING)) {
      return
    }

    this.socket = new WebSocket(this.url)

    this.socket.onopen = () => {
      console.log('WebSocket Connected')
      if (this.reconnectTimer) {
        clearInterval(this.reconnectTimer)
        this.reconnectTimer = null
      }
    }

    this.socket.onmessage = (event) => {
      console.log('Received:', event.data)
      try {
        // Netty server currently echoes back messages
        // For demonstration, let's treat the echo as a message from the customer
        if (this.chatStore.activeCustomerId) {
          const newMsg = {
            id: Date.now().toString() + Math.random(),
            content: event.data,
            senderId: this.chatStore.activeCustomerId, // Simulated as customer response
            receiverId: 'cs_admin',
            timestamp: Date.now(),
            type: 'text' as const
          }
          this.chatStore.addMessage(this.chatStore.activeCustomerId, newMsg)
        }
      } catch (err) {
        console.error('Failed to parse message:', err)
      }
    }

    this.socket.onclose = () => {
      console.log('WebSocket Closed. Reconnecting...')
      this.reconnect()
    }

    this.socket.onerror = (error) => {
      console.error('WebSocket Error:', error)
      this.socket?.close()
    }
  }

  reconnect() {
    if (this.reconnectTimer) return
    this.reconnectTimer = setInterval(() => {
      console.log('Attempting to reconnect...')
      this.connect()
    }, 5000)
  }

  setUrl(url: string) {
    this.url = url
  }

  send(message: string) {
    if (this.socket && this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(message)
    } else {
      console.error('WebSocket is not open. Current state:', this.socket?.readyState)
    }
  }

  close() {
    if (this.socket) {
      this.socket.close()
    }
  }
}

// Singleton instance
let wsService: WebSocketService | null = null

export const useWebSocket = () => {
  if (!wsService) {
    wsService = new WebSocketService()
  }
  return wsService
}
