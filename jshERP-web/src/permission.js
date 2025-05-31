import ls from '@/utils/storage' // 替换Vue.ls为独立存储实例[1,6](@ref)
import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { USER_ID, INDEX_MAIN_PAGE_PATH } from '@/store/mutation-types'
import { generateIndexRouter } from '@/utils/util'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/user/login', '/user/register', '/user/register-result'] // no redirect whitelist

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  if (ls.get(USER_ID)) {
    /* has token */
    if (to.path === '/' || to.path === '/user/login') {
      next({ path: INDEX_MAIN_PAGE_PATH })
      NProgress.done()
    } else {
      if (store.getters.permissionList.length === 0) {
        store
          .dispatch('GetPermissionList')
          .then((res) => {
            const menuData = res
            if (menuData === null || menuData === '' || menuData === undefined) {
              return
            }
            // 缓存用户的按钮权限
            store.dispatch('GetUserBtnList').then((res) => {
              ls.set('winBtnStrList', res.data.userBtn, 7 * 24 * 60 * 60 * 1000)
            })
            let constRoutes = []
            constRoutes = generateIndexRouter(menuData)
            // 添加主界面路由
            store.dispatch('UpdateAppRouter', { constRoutes }).then(() => {
              // 根据roles权限生成可访问的路由表
              // 动态添加可访问路由表
              router.addRoute(store.getters.addRouters)
              // const redirect = decodeURIComponent(from.query.redirect || to.path)
              // next({ path: redirect })
              // 使用 to.fullPath 保留参数（完整路径 + 查询参数）
              const redirect = from.query.redirect ? decodeURIComponent(from.query.redirect) : to.fullPath
              // 直接传递完整路径（含参数）
              next(redirect) // 等同于 next({ path: to.path, query: to.query })
            })
          })
          .catch((error) => {
            console.log('router--Logout--', error)
            // 关键信息提取
            const errorInfo = {
              path: to.fullPath,
              time: new Date().toISOString(),
              status: error.response?.status,
              message: error.message || '未知错误',
            }
            console.error('[错误详情]', JSON.stringify(errorInfo))
            store.dispatch('Logout').then(() => {
              next({ path: '/user/login' })
            })
          })
      } else {
        if (to.path) {
          _hmt.push(['_trackPageview', '/#' + to.fullPath])
        }
        next()
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next({ path: '/user/login' })
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
