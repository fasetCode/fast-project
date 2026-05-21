import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Antd from 'ant-design-vue'
import * as Icons from '@ant-design/icons-vue'
import 'ant-design-vue/dist/reset.css'
import './style.css'
import App from '@/App.vue'
import router from '@/router'

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(Antd)

Object.keys(Icons).forEach((key) => {
  app.component(key, Icons[key as keyof typeof Icons])
})

app.mount('#app')
