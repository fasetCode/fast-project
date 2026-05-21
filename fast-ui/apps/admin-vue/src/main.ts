import { createApp } from 'vue'
import router from './router'
import pinia from './stores'
import Antd from 'ant-design-vue'
import App from './App.vue'
import './style.css'

const app = createApp(App)

app.use(pinia)
app.use(router)
app.use(Antd)
router.isReady().then(() => {
  app.mount('#app')
})
