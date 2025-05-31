// babel.config.js
module.exports = {
  plugins: [
    [
      '@vue/babel-plugin-jsx', // 安装：npm install @vue/babel-plugin-jsx -D

      {
        transformOn: true, // 转换事件处理
        optimize: true, // 启用优化
        mergeProps: true, // 合并静态和动态属性
        injectH: true, // Vue 3 必须开启
      },
    ],
  ],
  presets: [
    [
      '@vue/cli-plugin-babel/preset',
      {
        jsx: true, // 显式启用JSX支持
      },
    ],
  ],
}

