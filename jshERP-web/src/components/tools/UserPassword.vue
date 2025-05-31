<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="modalWidth"
      :open="visible"
      :confirm-loading="confirmLoading"
      :get-container="() => containerRef"
      :mask-style="{ top: '93px', left: '154px' }"
      :wrap-class-name="wrapClassNameInfo()"
      :mask="isDesktop()"
      :mask-closable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      cancel-text="关闭"
      style="top: 20%; height: 50%"
    >
      <a-spin :spinning="confirmLoading">
        <a-form ref="formRef" :model="formState" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-form-item label="旧密码" name="oldpassword">
            <a-input-password v-model:value="formState.oldpassword" type="password" placeholder="请输入旧密码" />
          </a-form-item>
          <a-form-item label="新密码" name="password">
            <a-input-password
              v-model:value="formState.password"
              type="password"
              placeholder="新密码至少6位，区分大小写"
            />
          </a-form-item>
          <a-form-item label="确认新密码" name="confirmPassword">
            <a-input-password v-model:value="formState.confirmPassword" type="password" placeholder="请确认新密码" />
          </a-form-item>
        </a-form>
      </a-spin>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { Modal, Form, Input, Spin, message } from 'ant-design-vue'
import { putAction } from '@/api/manage'
import md5 from 'md5'

// 组件引用
const AForm = Form
const AFormItem = Form.Item
const AInputPassword = Input.Password
const ASpin = Spin

// 响应式数据
const containerRef = ref(null)
const formRef = ref(null)
const visible = ref(false)
const confirmLoading = ref(false)
const userId = ref('')
const title = ref('修改密码')
const modalWidth = ref(800)

// 表单状态
const formState = reactive({
  oldpassword: '',
  password: '',
  confirmPassword: '',
})

// 布局配置
const labelCol = reactive({
  xs: { span: 24 },
  sm: { span: 5 },
})
const wrapperCol = reactive({
  xs: { span: 24 },
  sm: { span: 16 },
})

// 验证规则
const validatePassword = async (_, value) => {
  if (value === formState.oldpassword) {
    return Promise.reject('新密码和旧密码不能相同!')
  }

  const reg = /^(?=.*[a-z])(?=.*\d).{6,}$/
  if (!reg.test(value)) {
    return Promise.reject('密码由6位数字、小写字母组成!')
  }

  return Promise.resolve()
}

const validateConfirmPassword = async (_, value) => {
  if (!value) {
    return Promise.reject('请输入密码!')
  }

  if (value !== formState.password) {
    return Promise.reject('两次密码不一致!')
  }

  return Promise.resolve()
}

const rules = reactive({
  oldpassword: [{ required: true, message: '请输入旧密码!', trigger: 'blur' }],
  password: [
    { required: true, message: '请输入新密码!', trigger: 'blur' },
    { validator: validatePassword, trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码!', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' },
  ],
})

// 方法定义
const show = (id) => {
  if (!id) {
    message.warning('当前系统无登陆用户!')
    return
  }

  userId.value = id
  resetForm()
  visible.value = true
}

const handleCancel = () => {
  close()
}

const close = () => {
  visible.value = false
}

const resetForm = () => {
  formRef.value?.resetFields()
  Object.assign(formState, {
    oldpassword: '',
    password: '',
    confirmPassword: '',
  })
}

const handleOk = async () => {
  try {
    await formRef.value.validate()

    confirmLoading.value = true

    const params = {
      userId: userId.value,
      oldpassword: md5(formState.oldpassword),
      password: md5(formState.password),
    }

    const res = await putAction('/user/updatePwd', params)

    if (res.code === 200) {
      if (res.data.status === 2 || res.data.status === 3) {
        message.warning(res.data.message)
      } else {
        message.success(res.data.message)
        close()
      }
    } else {
      message.warning(res.data.message)
    }
  } catch (error) {
    console.error('表单验证失败:', error)
  } finally {
    confirmLoading.value = false
  }
}

// 设备检测方法（替代 mixin）
const isDesktop = () => {
  return window.innerWidth > 768
}

// 样式类名生成
const wrapClassNameInfo = () => {
  return isDesktop() ? 'desktop-modal' : 'mobile-modal'
}
</script>

<style scoped>
/* 响应式样式 */
@media (max-width: 768px) {
  .mobile-modal {
    width: 95% !important;
    max-width: 100%;
  }
}

.desktop-modal {
  width: 800px;
}
</style>
