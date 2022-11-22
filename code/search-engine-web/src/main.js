import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import request from "@/utils/request";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import 'element-plus/theme-chalk/display.css'


const app = createApp(App)
app.config.globalProperties.$http = request
app.use(ElementPlus,{
    locale: zhCn,
})
app.use(store).use(router).mount('#app')
