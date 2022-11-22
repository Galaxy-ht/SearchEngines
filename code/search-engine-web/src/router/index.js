import { createRouter, createWebHistory } from 'vue-router'
import homeView from '@/views/homeView'

const routes = [
  {
    path: '/',
    name: 'home',
    component: homeView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
