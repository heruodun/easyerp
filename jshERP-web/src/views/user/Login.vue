<template>
  <div class="main">
    <a-form ref="formRef" :model="formState" :rules="rules" class="user-layout-login">
      <a-form-item name="loginName">
        <a-input size="large" v-model:value="formState.loginName" placeholder="请输入用户名">
          <template #prefix>
            <UserOutlined :style="{ color: 'rgba(0,0,0,.25)' }" />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item name="password">
        <a-input-password size="large" v-model:value="formState.password" autocomplete="false" placeholder="请输入密码">
          <template #prefix>
            <LockOutlined :style="{ color: 'rgba(0,0,0,.25)' }" />
          </template>
        </a-input-password>
      </a-form-item>

      <a-row :gutter="0">
        <a-col :span="14">
          <a-form-item name="inputCode">
            <a-input size="large" v-model:value="formState.inputCode" placeholder="请输入验证码">
              <template #prefix>
                <SmileOutlined :style="{ color: 'rgba(0,0,0,.25)' }" />
              </template>
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :span="10" style="text-align: right">
          <img v-if="requestCodeSuccess" style="margin-top: 2px" :src="randCodeImage" @click="handleChangeCheckCode" />
          <img v-else style="margin-top: 2px" src="../../assets/checkcode.png" @click="handleChangeCheckCode" />
        </a-col>
      </a-row>

      <a-form-item>
        <a-checkbox v-model:checked="rememberPwd">记住密码</a-checkbox>
        <router-link
          v-if="registerFlag === '1'"
          :to="{ name: 'register' }"
          class="forge-password"
          style="float: right; margin-right: 10px"
        >
          注册租户
        </router-link>
      </a-form-item>

      <a-form-item style="margin-top: 16px">
        <a-button
          size="large"
          type="primary"
          class="login-button"
          :loading="loginBtn"
          @click="handleSubmit"
          :disabled="loginBtn"
        >
          登 录
        </a-button>
      </a-form-item>

      <div v-if="device === 'mobile'" class="login-copyright">
        <a-row>
          <a-col>
            © 2015-2030 Powered By
            <a :href="systemUrl" target="_blank" style="color: #00458a">官方网站</a>
          </a-col>
        </a-row>
      </div>
    </a-form>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useStore } from 'vuex'
import { UserOutlined, LockOutlined, SmileOutlined } from '@ant-design/icons-vue'
import md5 from 'md5'
import { message, notification } from 'ant-design-vue'
import {
  ACCESS_TOKEN,
  DEFAULT_MULTI_PAGE,
  SIDEBAR_TYPE,
  DEFAULT_THEME,
  DEFAULT_LAYOUT_MODE,
  DEFAULT_FIXED_HEADER,
  DEFAULT_FIXED_SIDEMENU,
  DEFAULT_CONTENT_WIDTH_TYPE,
  DEFAULT_FIXED_HEADER_HIDDEN,
  DEFAULT_COLOR_WEAK,
  DEFAULT_COLOR,
} from '@/store/mutation-types'
import config from '@/defaultSettings'
import { timeFix } from '@/utils/util'
import { getAction } from '@/api/manage'
import ls from '@/utils/storage' // 使用封装的存储实例替代Vue.ls

const router = useRouter()
const route = useRoute()
const store = useStore()

// 响应式状态
const formRef = ref()
const loginBtn = ref(false)
const rememberPwd = ref(false)
const requestCodeSuccess = ref(false)
const randCodeImage = ref('')
const uuid = ref('')
const registerFlag = ref('')
const device = ref('desktop') // 设备类型

const formState = reactive({
  loginName: '',
  password: '',
  inputCode: '',
})

// 验证规则
const rules = reactive({
  loginName: [{ required: true, message: '请输入用户名!' }, { validator: validateLoginName }],
  password: [{ required: true, message: '请输入密码!' }],
  inputCode: [{ required: true, message: '请输入验证码!' }],
})

// 初始化
onMounted(() => {
  loadInfo()
  checkScreen()
  getRegisterFlag()
  handleChangeCheckCode()
})

// 方法定义
function validateLoginName(_, value) {
  const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
  return regex.test(value) ? Promise.resolve() : Promise.resolve()
}

function loadInfo() {
  const cachedLogin = ls.get('cache_loginName')
  const cachedPassword = ls.get('cache_password')

  if (cachedLogin && cachedPassword) {
    formState.loginName = cachedLogin
    formState.password = cachedPassword
    rememberPwd.value = true
  }

  if (route.params.loginName) {
    formState.loginName = route.params.loginName
    formState.password = ''
    rememberPwd.value = false
  }
}

function handleChangeCheckCode() {
  getAction('/user/randomImage')
    .then((res) => {
      if (res.code === 200) {
        uuid.value = res.data.uuid
        randCodeImage.value = res.data.base64
        requestCodeSuccess.value = true
      } else {
        message.error(res.data || '获取验证码失败')
        requestCodeSuccess.value = false
      }
    })
    .catch(() => {
      requestCodeSuccess.value = false
    })
}

async function handleSubmit() {
  try {
    loginBtn.value = true
    await formRef.value.validate()

    const loginParams = {
      loginName: formState.loginName,
      password: md5(formState.password),
      code: formState.inputCode,
      uuid: uuid.value,
    }

    // 记住密码处理
    if (rememberPwd.value) {
      ls.set('cache_loginName', formState.loginName)
      ls.set('cache_password', formState.password)
    } else {
      ls.remove('cache_loginName')
      ls.remove('cache_password')
    }

    // 执行登录
    const res = await store.dispatch('Login', loginParams)
    departConfirm(res, formState.loginName)
  } catch (error) {
    console.error('表单验证失败', error)
    formState.inputCode = ''
    handleChangeCheckCode()
  } finally {
    loginBtn.value = false
  }
}

function departConfirm(res, loginName) {
  if (res.code === 200) {
    loginSuccess(res)
    initStoreState()
  } else {
    const errorMap = {
      'user is not exist': '用户不存在',
      'user password error': '用户密码不正确',
      'user is black': '用户被禁用',
      'tenant is black': loginName === 'jsh' ? 'jsh用户已停用，请注册租户进行体验！' : '用户所属的租户被禁用',
      'tenant is expire': '试用期已结束，请联系客服续费',
      'access service error': '查询服务异常',
    }

    message.error(errorMap[res.data.msgTip] || res.message || '登录失败')
    console.log('departConfirm--Logout--')
    store.dispatch('Logout')
  }
}

function loginSuccess(res) {
  router.push({ path: '/dashboard/analysis' })
  notification.success({
    message: '欢迎',
    description: `${timeFix()}，欢迎回来`,
  })

  if (res.data?.pwdSimple) {
    setTimeout(() => {
      notification.warning({
        message: '友情提醒',
        description: '密码过于简单，请尽快修改',
      })
    }, 3000)
  }

  if (res.data?.user) {
    if (res.data.user.loginName === 'admin') {
      const desc =
        'admin只是平台运维用户，真正的管理员是租户(测试账号为jsh），admin不能编辑任何业务数据，只能配置平台菜单和创建租户'
      message.info(desc, 30)
    } else {
      checkTenantStatus()
    }
  }

  initMPropertyShort()
}

function checkTenantStatus() {
  getAction('/user/infoWithTenant', {}).then((res) => {
    if (res?.code === 200) {
      const currentTime = new Date()
      const expireTime = new Date(res.data.expireTime)
      const type = res.data.type
      const difftime = expireTime - currentTime
      const tipInfo = '您好，服务即将到期，请及时续费！'

      if ((type === '0' && difftime < 86400000 * 5) || (type === '1' && difftime < 86400000 * 15)) {
        message.warning(tipInfo, 8)
      }
    }
  })
}

function initStoreState() {
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
}

function getRegisterFlag() {
  getAction('/platformConfig/getPlatform/registerFlag').then((res) => {
    registerFlag.value = String(res)
  })
}

function initMPropertyShort() {
  getAction('/materialProperty/getAllList').then((res) => {
    if (res?.code === 200 && res.data) {
      ls.set('materialPropertyList', res.data, 7 * 24 * 60 * 60 * 1000)
    }
  })
}

function checkScreen() {
  const percentage = '100%'
  const basicWidth = 1920
  const currentWidth = window.screen.width
  const currentHeight = window.screen.height
  const currentRatio = window.devicePixelRatio.toFixed(2)
  let needRatio = 1
  const ratio = currentWidth / basicWidth

  if (ratio > 0.5 && ratio < 0.67) {
    percentage = '50%'
    needRatio = 0.5
  }
  if (ratio >= 0.67 && ratio < 0.75) {
    percentage = '67%'
    needRatio = 0.67
  } else if (ratio >= 0.75 && ratio < 0.8) {
    percentage = '75%'
    needRatio = 0.75
  } else if (ratio >= 0.8 && ratio < 0.9) {
    percentage = '80%'
    needRatio = 0.8
  } else if (ratio >= 1.1 && ratio < 1.25) {
    percentage = '110%'
    needRatio = 1.1
  } else if (ratio >= 1.25 && ratio < 1.5) {
    percentage = '125%'
    needRatio = 1.25
  } else if (ratio >= 1.5 && ratio < 1.75) {
    percentage = '150%'
    needRatio = 1.5
  }

  if (currentRatio !== needRatio) {
    openNotificationWithIcon('warning', currentWidth, currentHeight, percentage)
  }
}

function openNotificationWithIcon(type, currentWidth, currentHeight, percentage) {
  notification[type]({
    message: '浏览器的缩放比例调整提示',
    description: `检测到您显示器的分辨率为：${currentWidth}*${currentHeight}，为了获得更好的操作体验，建议您将浏览器的缩放比例调整至${percentage}`,
    duration: 10,
  })
}
</script>

<style lang="less" scoped>
.user-layout-login {
  label {
    font-size: 14px;
  }

  .ant-form-item {
    margin-bottom: 16px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .forge-password {
    font-size: 14px;
    font-weight: bolder;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }

  .weixin {
    padding-left: 10px;
    color: red;
    cursor: pointer;
  }
}

.login-copyright {
  text-align: center;
  margin-top: 20px;

  &,
  a {
    color: #666;
  }
}
</style>
