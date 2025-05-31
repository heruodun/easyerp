import ls from '@/utils/storage' // 独立存储实例
import axios from 'axios'
import store from '@/store'
import { Modal, notification } from 'ant-design-vue'
import { ACCESS_TOKEN } from '@/store/mutation-types'

// 获取 API 基础 URL
const apiBaseUrl = window._CONFIG?.domianURL || '/jshERP-boot'

// 创建 axios 实例
const service = axios.create({
  baseURL: apiBaseUrl,
  timeout: 300000,
})

// 错误处理函数
const errorHandler = (error) => {
  if (error.response) {
    const { status, data } = error.response
    const token = ls.get(ACCESS_TOKEN)

    switch (status) {
      case 401:
        notification.error({
          message: '系统提示',
          description: '未授权，请重新登录',
          duration: 4,
        })
        if (token) {
          console.log('errorHandler--Logout--' + error)
          store.dispatch('Logout').then(() => {
            setTimeout(() => {
              window.location.reload()
            }, 1500)
          })
        }
        break

      case 403:
        notification.error({
          message: '系统提示',
          description: '拒绝访问',
          duration: 4,
        })
        break

      case 404:
        notification.error({
          message: '系统提示',
          description: '很抱歉，资源未找到!',
          duration: 4,
        })
        break

      case 500:
        if (token && data === 'loginOut') {
          Modal.error({
            title: '登录已过期',
            content: '很抱歉，登录已过期，请重新登录',
            okText: '重新登录',
            mask: false,
            onOk: () => {
              ls.remove(ACCESS_TOKEN)
              window.location.reload()
            },
          })
        }
        break

      case 504:
        notification.error({
          message: '系统提示',
          description: '网络超时',
        })
        break

      default:
        notification.error({
          message: '系统提示',
          description: data?.message || '未知错误',
          duration: 4,
        })
    }
  }
  return Promise.reject(error)
}

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    const token = ls.get(ACCESS_TOKEN)
    if (token) {
      config.headers['X-Access-Token'] = token
    }
    return config
  },
  (error) => Promise.reject(error)
)

// 响应拦截器
service.interceptors.response.use((response) => response.data, errorHandler)

// Vue 3 插件安装器
const installer = {
  install(app, router = {}) {
    // 全局挂载 axios 实例
    app.config.globalProperties.$axios = service
    app.config.globalProperties.$http = service

    // 提供 inject 支持
    app.provide('axios', service)
  },
}

export { installer, service as axios }
