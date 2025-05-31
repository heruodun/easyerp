// import Vue from 'vue'
import { createApp } from 'vue'
import App from './App.vue'
import VueStorage from 'vue-ls' // 关键修改1：直接导入createStorage
import router from './router' // 导入新路由配置
import store from './store/'
import axios from 'axios' // 新增导入
import * as filters from '@/utils/filter' // 导入所有过滤器
import vueBus from '@/utils/vueBus'

import { VueAxios } from '@/utils/request'

import Antd from 'ant-design-vue'
// import 'ant-design-vue/dist/antd.less' // or 'ant-design-vue/dist/antd.less'
import 'ant-design-vue/dist/reset.css' // 新版样式文件[9,10](@ref)

// 初始化存储实例 - 关键修改2
const { ls } = VueStorage.useStorage(config.storageOptions)

import '@/permission' // permission control
import '@/utils/filter' // base filter
import Print from 'vue-print-nb-jeecg'
/*import '@babel/polyfill'*/
// import preview from 'vue-photo-preview'
// import 'vue-photo-preview/dist/skin.css'

import preview from 'vue3-photo-preview' // Vue 3专用版本[8](@ref)
import 'vue3-photo-preview/dist/index.css'

import 'intro.js/introjs.css'

import {
  ACCESS_TOKEN,
  DEFAULT_COLOR,
  DEFAULT_THEME,
  DEFAULT_LAYOUT_MODE,
  DEFAULT_COLOR_WEAK,
  SIDEBAR_TYPE,
  DEFAULT_FIXED_HEADER,
  DEFAULT_FIXED_HEADER_HIDDEN,
  DEFAULT_FIXED_SIDEMENU,
  DEFAULT_CONTENT_WIDTH_TYPE,
  DEFAULT_MULTI_PAGE,
} from '@/store/mutation-types'
import config from '@/defaultSettings'

import hasPermission from '@/utils/hasPermission'
// import vueBus from '@/utils/vueBus'
import JeecgComponents from '@/components/jeecg/index'
import '@/assets/less/JAreaLinkage.less'
import VueAreaLinkage from 'vue-area-linkage'

// 创建应用实例
const app = createApp(App)

// 注册插件
// app.use(Storage, config.storageOptions)
app.use(Antd)
// app.use(Viser)
app.use(hasPermission)
app.use(Print)
app.use(preview)
app.use(vueBus)
app.use(JeecgComponents)
app.use(VueAreaLinkage)

// 挂载全局属性
app.config.globalProperties.$axios = axios // 替代 VueAxios

// 注册全局属性
app.config.globalProperties.$filters = {
  NumberFormat: filters.numberFormat,
  dayjs: filters.formatDateDayjs,
  moment: filters.formatDateMoment,
  ellipsis: filters.ellipsis,
}

// 挂载路由和状态管理
app.use(router)
app.use(store)

// Store 初始化（原 mounted 逻辑迁移）
store.commit('SET_SIDEBAR_TYPE', true)
store.commit('TOGGLE_THEME', ls.get(DEFAULT_THEME, config.navTheme))
store.commit('TOGGLE_LAYOUT_MODE', ls.get(DEFAULT_LAYOUT_MODE, config.layout))
store.commit('TOGGLE_FIXED_HEADER', ls.get(DEFAULT_FIXED_HEADER, config.fixedHeader))
store.commit('TOGGLE_FIXED_SIDERBAR', ls.get(DEFAULT_FIXED_SIDEMENU, config.fixSiderbar))
store.commit('TOGGLE_CONTENT_WIDTH', ls.get(DEFAULT_CONTENT_WIDTH_TYPE, config.contentWidth))
store.commit('TOGGLE_FIXED_HEADER_HIDDEN', ls.get(DEFAULT_FIXED_HEADER_HIDDEN, config.autoHideHeader))
store.commit('TOGGLE_WEAK', ls.get(DEFAULT_COLOR_WEAK, config.colorWeak))
store.commit('TOGGLE_COLOR', ls.get(DEFAULT_COLOR, config.primaryColor))
store.commit('SET_TOKEN', ls.get(ACCESS_TOKEN))
store.commit('SET_MULTI_PAGE', ls.get(DEFAULT_MULTI_PAGE, config.multipage))

// 最终挂载
app.mount('#app')

// Vue.config.productionTip = false
// Vue.use(Storage, config.storageOptions)
// Vue.use(Antd)
// Vue.use(VueAxios, router)
// Vue.use(Viser)
// Vue.use(hasPermission)
// Vue.use(Print)
// Vue.use(preview)
// Vue.use(vueBus)
// Vue.use(JeecgComponents)
// Vue.use(VueAreaLinkage)

// new Vue({
//   router,
//   store,
//   mounted() {
//     // store.commit('SET_SIDEBAR_TYPE', Vue.ls.get(SIDEBAR_TYPE, true))
//     store.commit('SET_SIDEBAR_TYPE', true)
//     store.commit('TOGGLE_THEME', Vue.ls.get(DEFAULT_THEME, config.navTheme))
//     store.commit('TOGGLE_LAYOUT_MODE', Vue.ls.get(DEFAULT_LAYOUT_MODE, config.layout))
//     store.commit('TOGGLE_FIXED_HEADER', Vue.ls.get(DEFAULT_FIXED_HEADER, config.fixedHeader))
//     store.commit('TOGGLE_FIXED_SIDERBAR', Vue.ls.get(DEFAULT_FIXED_SIDEMENU, config.fixSiderbar))
//     store.commit('TOGGLE_CONTENT_WIDTH', Vue.ls.get(DEFAULT_CONTENT_WIDTH_TYPE, config.contentWidth))
//     store.commit('TOGGLE_FIXED_HEADER_HIDDEN', Vue.ls.get(DEFAULT_FIXED_HEADER_HIDDEN, config.autoHideHeader))
//     store.commit('TOGGLE_WEAK', Vue.ls.get(DEFAULT_COLOR_WEAK, config.colorWeak))
//     store.commit('TOGGLE_COLOR', Vue.ls.get(DEFAULT_COLOR, config.primaryColor))
//     store.commit('SET_TOKEN', Vue.ls.get(ACCESS_TOKEN))
//     store.commit('SET_MULTI_PAGE', Vue.ls.get(DEFAULT_MULTI_PAGE, config.multipage))
//   },
//   render: (h) => h(App),
// }).$mount('#app')
