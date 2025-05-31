<template>
  <!-- 两步验证 -->
  <a-modal
    centered
    :visible="visible"
    @update:visible="handleVisibleChange"
    @cancel="handleCancel"
    :maskClosable="false"
  >
    <template #title>
      <div :style="{ textAlign: 'center' }">两步验证</div>
    </template>

    <template #footer>
      <div :style="{ textAlign: 'center' }">
        <a-button key="back" @click="handleCancel">返回</a-button>
        <a-button key="submit" type="primary" :loading="stepLoading" @click="handleStepOk"> 继续 </a-button>
      </div>
    </template>

    <a-spin :spinning="stepLoading">
      <a-form layout="vertical" ref="formRef">
        <div class="step-form-wrapper">
          <p style="text-align: center" v-if="!stepLoading">
            请在手机中打开 Google Authenticator 或两步验证 APP<br />
            输入 6 位动态码
          </p>
          <p style="text-align: center" v-else>正在验证..<br />请稍后</p>

          <a-form-item
            :style="{ textAlign: 'center' }"
            name="stepCode"
            :rules="[{ required: true, message: '请输入 6 位动态码!', pattern: /^\d{6}$/, len: 6 }]"
          >
            <a-input :style="{ textAlign: 'center' }" @keyup.enter="handleStepOk" placeholder="000000" />
          </a-form-item>

          <p style="text-align: center">
            <a @click="onForgeStepCode">遗失手机?</a>
          </p>
        </div>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { ref } from 'vue'

export default {
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:visible', 'success', 'error', 'cancel'], // 声明所有自定义事件
  setup(props, { emit }) {
    const stepLoading = ref(false)
    const formRef = ref(null)

    const handleVisibleChange = (newValue) => {
      emit('update:visible', newValue)
    }

    const handleStepOk = async () => {
      stepLoading.value = true
      try {
        await formRef.value.validate()
        const values = formRef.value.getFieldsValue()
        console.log('values', values)

        // 模拟验证过程
        setTimeout(() => {
          stepLoading.value = false
          emit('success', { values })
        }, 2000)
      } catch (err) {
        stepLoading.value = false
        emit('error', { err })
      }
    }

    const handleCancel = () => {
      emit('update:visible', false)
      emit('cancel')
    }

    const onForgeStepCode = () => {
      // 遗失手机处理逻辑
    }

    return {
      stepLoading,
      formRef,
      handleVisibleChange,
      handleStepOk,
      handleCancel,
      onForgeStepCode,
    }
  },
}
</script>

<style lang="less" scoped>
.step-form-wrapper {
  margin: 0 auto;
  width: 80%;
  max-width: 400px;
}
</style>
