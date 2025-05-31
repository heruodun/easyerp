// import Bus from 'vue';
// let install = function (Vue) {
//   Vue.prototype.$bus = new Bus()
// }
// export default { install };

// src/utils/vueBus.js
import mitt from 'mitt' // 安装: npm install mitt

const emitter = mitt()

export default {
  install(app) {
    // 兼容 Vue 2 的 $bus 接口
    app.config.globalProperties.$bus = {
      $on: emitter.on,
      $emit: emitter.emit,
      $off: emitter.off,
    }

    // 提供 Composition API 支持
    app.provide('eventBus', emitter)
  },
}
