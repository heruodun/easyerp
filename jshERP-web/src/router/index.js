// import Vue from 'vue'
// import Router from 'vue-router'
// import { constantRouterMap } from '@/config/router.config'

// //update-begin-author:taoyan date:20191011 for:TASK #3214 【优化】访问online功能测试 浏览器控制台抛出异常
// try {
//   const originalPush = Router.prototype.push
//   Router.prototype.push = function push(location) {
//     return originalPush.call(this, location).catch(err => err)
//   }
// } catch (e) {
// }
// //update-end-author:taoyan date:20191011 for:TASK #3214 【优化】访问online功能测试 浏览器控制台抛出异常

// Vue.use(Router)

// export default new Router({
//   mode: 'history',
//   base: process.env.BASE_URL,
//   scrollBehavior: () => ({ y: 0 }),
//   routes: constantRouterMap
// })

import { createRouter, createWebHistory } from 'vue-router' // 替换原有导入
import { constantRouterMap } from '@/config/router.config'

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL), // 替换 mode: 'history'
  scrollBehavior: () => ({ top: 0 }), // 注意：y 改为 top
  routes: constantRouterMap,
})

// 删除 try-catch 块和 Vue.use(Router)
export default router
