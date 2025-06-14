<template>
  <a-card>
    <a-row :gutter="[8, 0]" style="margin-bottom: 10px">
      <a-col :span="4"> </a-col>
      <a-col :span="20">
        <div style="display: flex; align-items: center; gap: 12px">
          <!-- 纸张设置 -->
          <a-button-group>
            <template v-for="(value, type) in paperTypes">
              <a-button :type="curPaperType === type ? 'primary' : 'info'" @click="setPaper(type, value)" :key="type">
                {{ type }}
              </a-button>
            </template>
            <a-popover v-model="paperPopVisible" title="设置纸张宽高(mm)" trigger="click">
              <div slot="content">
                <a-input-group compact style="margin: 10px 10px">
                  <a-input
                    type="number"
                    v-model="paperWidth"
                    style="width: 100px; text-align: center"
                    placeholder="宽(mm)"
                  />
                  <a-input
                    style="width: 30px; border-left: 0; pointer-events: none; backgroundcolor: #fff"
                    placeholder="~"
                    disabled
                  />
                  <a-input
                    type="number"
                    v-model="paperHeight"
                    style="width: 100px; text-align: center; border-left: 0"
                    placeholder="高(mm)"
                  />
                </a-input-group>
                <a-button type="primary" style="width: 100%" @click="otherPaper">确定</a-button>
              </div>
              <a-button :type="'other' == curPaperType ? 'primary' : ''">自定义纸张</a-button>
            </a-popover>
          </a-button-group>
          <a-button type="text" icon="zoom-out" @click="changeScale(false)"></a-button>
          <a-input-number
            :value="scaleValue"
            :min="scaleMin"
            :max="scaleMax"
            :step="0.1"
            disabled
            style="width: 70px"
            :formatter="(value) => `${(value * 100).toFixed(0)}%`"
            :parser="(value) => value.replace('%', '')"
          />
          <a-button type="text" icon="zoom-in" @click="changeScale(true)"></a-button>
          <!-- 预览/打印 -->
          <a-button-group>
            <a-button type="primary" icon="eye" @click="preView"> 预览 </a-button>
            <a-button type="primary" @click="print">
              直接打印
              <a-icon type="printer" />
            </a-button>
            <a-button type="primary" @click="selectAll">全选元素</a-button>
          </a-button-group>
          <!-- 保存/清空 -->
          <a-button-group>
            <a-button type="primary" icon="save" @click="save"> 保存 </a-button>
            <a-popconfirm title="是否确认清空?" okType="danger" okText="确定清空" @confirm="clearPaper">
              <a-icon slot="icon" type="question-circle-o" style="color: red" />
              <a-button type="danger">
                清空
                <a-icon type="close" />
              </a-button>
            </a-popconfirm>
          </a-button-group>
          <json-view :template="template" />
        </div>
      </a-col>
    </a-row>
    <a-row :gutter="[8, 0]">
      <a-col :span="4">
        <a-card style="height: 100vh">
          <a-row>
            <a-col :span="24" class="rect-printElement-types hiprintEpContainer"> </a-col>
          </a-row>
        </a-card>
      </a-col>
      <a-col :span="14">
        <a-card class="card-design">
          <div id="hiprint-printTemplate" class="hiprint-printTemplate"></div>
        </a-card>
      </a-col>
      <a-col :span="6" class="params_setting_container">
        <a-card>
          <a-row class="hinnn-layout-sider">
            <div id="PrintElementOptionSetting"></div>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
    <!-- 预览 -->
    <print-preview ref="preView" />
  </a-card>
</template>

<script>
import printPreview from './preview'
import jsonView from '../json-view.vue'
import { hiprint } from 'vue-plugin-hiprint'
import providers from './providers'
import { ASpace } from 'ant-design-vue'
import {
  findBillPrintTemplate,
  upsertBillPrintTemplate,
  findBillDetailByNumber,
  findFinancialDetailByNumber,
  getPlatformConfigByKey,
  getCurrentSystemConfig,
} from '@/api/api'
import { getMpListShort, getCheckFlag, exportXlsPost } from '@/utils/util'
import Vue from 'vue'
import { getAction, postAction, getFileAccessHttpUrl } from '@/api/manage'

let hiprintTemplate
export default {
  name: 'printCustom',

  components: { printPreview, jsonView, 'a-space': ASpace },
  data() {
    return {
      model: {},
      dataSource: [],
      template: null,
      printType: '',
      tenantId: 0,

      printData: {},
      // 模板选择
      mode: 0,
      modeList: [],
      // 当前纸张
      curPaper: {
        type: 'other',
        width: 220,
        height: 80,
      },
      // 纸张类型
      paperTypes: {
        A3: {
          width: 420,
          height: 296.6,
        },
        A4: {
          width: 210,
          height: 296.6,
        },
        A5: {
          width: 210,
          height: 147.6,
        },
        B3: {
          width: 500,
          height: 352.6,
        },
        B4: {
          width: 250,
          height: 352.6,
        },
        B5: {
          width: 250,
          height: 175.6,
        },
      },
      scaleValue: 1,
      scaleMax: 5,
      scaleMin: 0.5,
      // 自定义纸张
      paperPopVisible: false,
      paperWidth: '220',
      paperHeight: '80',
      lastjson: '',
      loading: false,
    }
  },
  computed: {
    curPaperType() {
      let type = 'other'
      let types = this.paperTypes
      for (const key in types) {
        let item = types[key]
        let { width, height } = this.curPaper
        if (item.width === width && item.height === height) {
          type = key
        }
      }
      return type
    },
  },
  mounted() {
    this.init().then(() => {
      // 确保changeMode完成后调用setPaper
      this.otherPaper()
    })
  },
  methods: {
    async init() {
      this.modeList = providers.map((e) => {
        return { type: e.type, name: e.name, value: e.value }
      })

      await this.fetchPrintData(this.$route.query.no)
      await this.changeMode()
    },
    selectAll() {
      this.template.selectAllElements()
    },
    async changeMode() {
      let provider = providers.find((p) => p.type === this.printType)

      if (!provider) {
        this.$message.error(`未找到模板: ${templateName}`)
        return
      }

      hiprint.init({
        providers: [provider.f],
        i18n: true,
        lang: 'cn',
      })
      $('.hiprintEpContainer').empty()
      hiprint.PrintElementTypeManager.build('.hiprintEpContainer', provider.value)
      $('#hiprint-printTemplate').empty()

      const res = await findBillPrintTemplate({
        printType: this.printType,
      })

      let templateJson = {}
      if (res?.code === 200 && res.data?.info?.template) {
        try {
          templateJson = JSON.parse(res.data.info.template)
        } catch (e) {
          console.error('Template parse error:', e)
          this.$message.error('模板数据格式错误')
        }
      }

      console.info('templateJson+[' + templateJson + ']')

      this.template = hiprintTemplate = new hiprint.PrintTemplate({
        template: templateJson,
        dataMode: 1, // 1:getJson 其他：getJsonTid 默认1
        history: false, // 是否需要 撤销重做功能
        onDataChanged: (type, json) => {
          console.log(type) // 新增、移动、删除、修改(参数调整)、大小、旋转
          console.log(json) // 返回 template
          // 更新模板
          // hiprintTemplate.update(json)
          // console.log(hiprintTemplate.historyList)
        },
        settingContainer: '#PrintElementOptionSetting',
        paginationContainer: '.hiprint-printPagination',
      })

      hiprintTemplate.design('#hiprint-printTemplate')
      // 获取当前放大比例, 当zoom时传true 才会有
      console.info('hiprintTemplate.editingPanel+[' + hiprintTemplate.editingPanel + ']')
      this.scaleValue = hiprintTemplate.editingPanel.scale || 1

      // 降级方案
      const panel = templateJson && templateJson.panels && templateJson.panels[0]
      this.paperWidth = (panel && panel.width) || this.paperWidth
      this.paperHeight = (panel && panel.height) || this.paperHeight
    },
    /**
     * 设置纸张大小
     * @param type [A3, A4, A5, B3, B4, B5, other]
     * @param value {width,height} mm
     */
    setPaper(type, value) {
      try {
        if (Object.keys(this.paperTypes).includes(type)) {
          this.curPaper = { type: type, width: value.width, height: value.height }
          console.info('hiprintTemplate=[' + hiprintTemplate + ']')
          hiprintTemplate.setPaper(value.width, value.height)
        } else {
          this.curPaper = { type: 'other', width: value.width, height: value.height }
          hiprintTemplate.setPaper(value.width, value.height)
        }
      } catch (error) {
        this.$message.error(`设置纸张失败: ${error}`)
      }
    },
    changeScale(big) {
      let scaleValue = this.scaleValue
      if (big) {
        scaleValue += 0.1
        if (scaleValue > this.scaleMax) scaleValue = 5
      } else {
        scaleValue -= 0.1
        if (scaleValue < this.scaleMin) scaleValue = 0.5
      }
      if (hiprintTemplate) {
        // scaleValue: 放大缩小值, false: 不保存(不传也一样), 如果传 true, 打印时也会放大
        hiprintTemplate.zoom(scaleValue, true)
        this.scaleValue = scaleValue
      }
    },
    otherPaper() {
      let value = {}
      value.width = this.paperWidth
      value.height = this.paperHeight
      this.paperPopVisible = false
      this.setPaper('other', value)
    },
    preView() {
      let { width } = this.curPaper
      this.$refs.preView.show(hiprintTemplate, this.printData, width)
    },
    print() {
      if (this.printData.status != 1) {
        this.$message.error('订单未审核通过，不可以打印')
        return
      }
      if (window.hiwebSocket.opened) {
        const printerList = hiprintTemplate.getPrinterList()
        console.log(printerList)

        hiprintTemplate.print2(this.printData, { printer: '', title: 'hiprint测试打印' })

        return
      }
      this.$message.error({
        title: '客户端未连接',
        content: (h) => (
          <div>
            连接【{hiwebSocket.host}】失败！
            <br />
            请确保目标服务器已
            <a href="https://gitee.com/CcSimple/electron-hiprint/releases" target="_blank">
              下载
            </a>
            并
            <a href="hiprint://" target="_blank">
              运行
            </a>
            打印服务！
          </div>
        ),
      })
    },
    async save() {
      if (!this.template) {
        this.$message.warning('模板未初始化，无法保存')
        return
      }

      try {
        const res = await upsertBillPrintTemplate({
          printType: this.printType,
          tenantId: this.tenantId,
          template: this.template.getJson(),
        })

        if (res.code === 200) {
          this.$message.success('保存成功')
        } else {
          this.$message.error(res.data?.message || '保存失败')
        }
      } catch (error) {
        console.error('保存失败:', error)
        this.$message.error('保存失败: ' + (error.message || '未知错误'))
      }
    },
    clearPaper() {
      try {
        hiprintTemplate.clear()
      } catch (error) {
        this.$message.error(`操作失败: ${error}`)
      }
    },

    formatMoney(value) {
      if (value == null || isNaN(value)) return '0.00'
      return parseFloat(value).toFixed(2)
    },
    async fetchPrintData(number) {
      //查询单条单据信息
      const res = await findBillDetailByNumber({ number: number })
      if (res && res.code === 200) {
        let item = res.data
        this.model = Object.assign({}, item)
        if (this.model.backAmount) {
          this.model.getAmount = (this.model.changeAmount + this.model.backAmount).toFixed(2)
        } else {
          this.model.getAmount = this.model.changeAmount
        }
        this.model.debt = this.formatMoney(this.model.debt)
        this.model.allNeed = this.formatMoney(this.model.allNeed)
        this.model.previousDebt = this.formatMoney(this.model.previousDebt)

        let showType = 'basic'
        if (
          item.subType === '采购' ||
          item.subType === '采购退货' ||
          item.subType === '销售' ||
          item.subType === '销售退货'
        ) {
          if (item.status === '3') {
            showType = 'other'
          }
        } else {
          if (item.status === '3') {
            showType = 'basic'
          } else if (item.purchaseStatus === '3') {
            showType = 'purchase'
          }
        }
        let isReadOnly = '1'
        if (item.subType === '组装单' || item.subType === '拆卸单') {
          isReadOnly = '0'
        }
        let params = {
          headerId: this.model.id,
          mpList: getMpListShort(Vue.ls.get('materialPropertyList')), //扩展属性
          linkType: showType,
          isReadOnly: isReadOnly,
        }
        let url = '/depotItem/getDetailList'
        let type = res.data.type === '其它' ? '' : res.data.type
        this.printType = res.data.subType + type
        this.tenantId = res.data.tenantId
        console.log('printType: ' + this.printType)
        await this.requestSubTableData(item, res.data.subType + type, url, params)
      }
    },
    async requestSubTableData(record, type, url, params, success) {
      this.loading = true
      getAction(url, params)
        .then((res) => {
          if (res && res.code === 200) {
            this.dataSource = res.data.rows
            typeof success === 'function' ? success(res) : ''

            this.printData = {
              ...this.model,
              table: this.dataSource.map((item, index) => {
                // 如果是最后一项，返回原对象（不加行号）
                if (index === this.dataSource.length - 1) {
                  return { ...item }
                }
                // 非最后一项添加行号
                return {
                  ...item,
                  rowIndex: index + 1, // 行号从1开始
                }
              }),
            }
          }
        })
        .finally(() => {
          this.loading = false
        })
    },
  },
}
</script>

<style lang="less" scoped>
// build 拖拽
/deep/ .hiprint-printElement-type > li > ul > li > a {
  padding: 4px 4px;
  color: #1296db;
  line-height: 1;
  height: auto;
  text-overflow: ellipsis;
}

// 默认图片
/deep/ .hiprint-printElement-image-content {
  img {
    content: url('~@/assets/logo.png');
  }
}

// 设计容器
.card-design {
  overflow: hidden;
  overflow-x: auto;
  overflow-y: auto;
}
</style>
