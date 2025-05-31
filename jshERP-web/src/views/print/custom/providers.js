/* eslint-disable */
// import {hiprint} from '../../index'

import { hiprint } from 'vue-plugin-hiprint'

// 销售出库单打印
export const saleOutProvider = function (ops) {
  var addElementTypes = function (context) {
    context.removePrintElementTypes('saleOutProviderModule')
    context.addPrintElementTypes('saleOutProviderModule', [
      new hiprint.PrintElementTypeGroup('单据主体', [
        {
          tid: 'saleOutProvider.header',
          title: '单据抬头',
          data: '单据抬头',
          type: 'text',
          options: {
            testData: '@单据抬头',
            height: 17,
            width: 150,
            fontSize: 16.5,
            field: 'billTitle',
            fontWeight: '700',
            textAlign: 'center',
            hideTitle: true,
          },
        },

        {
          tid: 'saleOutProvider.organName',
          title: '客户',
          data: '1888888888',
          type: 'text',
          options: {
            field: 'organName',
            testData: '@客户',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.contacts',
          title: '客户联系人',
          data: '客户联系人',
          type: 'text',
          options: {
            testData: '客户联系人',
            height: 13,
            width: 150,
            fontSize: 10.5,
            field: 'contacts',
            textAlign: 'center',
            hideTitle: true,
          },
        },
        {
          tid: 'saleOutProvider.telephone',
          title: '客户手机',
          data: '1888888888',
          type: 'text',
          options: {
            field: 'telephone',
            testData: '@客户手机',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
        {
          tid: 'saleOutProvider.address',
          title: '客户地址',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'address',
            testData: '@客户地址',
            height: 13,
            width: 189,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.operTimeStr',
          title: '单据日期',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'operTimeStr',
            testData: '@单据日期',
            height: 13,
            width: 186,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.number',
          title: '单据编号',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'number',
            testData: '@单据编号',
            height: 13,
            width: 159,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.linkNumber',
          title: '关联订单',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'linkNumber',
            testData: '@关联订单',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.discount',
          title: '优惠率',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'discount',
            testData: '@优惠率',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.discountMoney',
          title: '收款优惠',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'discountMoney',
            testData: '@收款优惠',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.otherMoney',
          title: '其他费用',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'otherMoney',
            testData: '@其他费用',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.deposit',
          title: '扣除订金',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'deposit',
            testData: '@扣除订金',
            height: 13,
            width: 150,
            fontSize: 10.5,
            fontWeight: '700',
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.discountLastMoney',
          title: '优惠后金额',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'discountLastMoney',
            testData: '@优惠后金额',
            height: 13,
            width: 150,
            fontSize: 10.5,
            fontWeight: '700',
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.debt',
          title: '本次欠款',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'debt',
            testData: '@本次欠款',
            height: 13,
            width: 150,
            fontSize: 10.5,
            fontWeight: '700',
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.previousDebt',
          title: '此前欠款',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'previousDebt',
            testData: '@此前欠款',
            height: 13,
            width: 150,
            fontSize: 10.5,
            fontWeight: '700',
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.allNeed',
          title: '累计欠款',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'allNeed',
            testData: '@累计欠款',
            height: 13,
            width: 150,
            fontSize: 10.5,
            fontWeight: '700',
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.changeAmount',
          title: '本次收款',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'changeAmount',
            testData: '@本次收款',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.remark',
          title: '备注',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'remark',
            testData: '@备注',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
        {
          tid: 'saleOutProvider.salesManStr',
          title: '销售人员',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'salesManStr',
            testData: '@销售人员',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.accountName',
          title: '结算账户',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'accountName',
            testData: '@结算账户',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.billDetailAllPrice',
          title: '合计',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'billDetailAllPrice',
            testData: '@合计',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.billDetailCnMoney',
          title: '合计大写',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'billDetailCnMoney',
            testData: '@合计大写',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleOutProvider.creatorName',
          title: '制单人',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'creatorName',
            testData: '@制单人',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
      ]),

      new hiprint.PrintElementTypeGroup('单据明细', [
        {
          tid: 'saleOutProvider.table',
          title: '表格信息',
          type: 'table',
          options: {
            field: 'table',

            fields: [
              { text: '序号', field: 'rowIndex' },
              { text: '条码', field: 'barCode' },
              { text: '名称', field: 'name' },
              { text: '规格', field: 'standard' },
              { text: '型号', field: 'model' },
              { text: '单位', field: 'unit' },
              { text: '数量', field: 'operNumber' },
              { text: '单价', field: 'unitPrice' },
              { text: '金额', field: 'allPrice' },
              { text: '颜色', field: 'color' },
              { text: '仓库名称', field: 'depotName' },
              { text: '库存', field: 'stock' },
              { text: '序列号', field: 'snList' },
              { text: '批号', field: 'batchNumber' },
              { text: '有效期', field: 'expirationDate' },
              { text: '多属性', field: 'sku' },
              { text: '税率(%)', field: 'taxRate' },
              { text: '税额', field: 'taxMoney' },
              { text: '价税合计', field: 'taxLastMoney' },
              { text: '重量', field: 'weight' },
              { text: '仓位货架', field: 'position' },
              { text: '备注', field: 'remark' },
            ],
          },
          editable: true,
          columnDisplayEditable: true, //列显示是否能编辑
          columnDisplayIndexEditable: true, //列顺序显示是否能编辑
          columnTitleEditable: true, //列标题是否能编辑
          columnResizable: true, //列宽是否能调整
          columnAlignEditable: true, //列对齐是否调整
          isEnableEditField: true, //编辑字段
          isEnableContextMenu: true, //开启右键菜单 默认true
          isEnableInsertRow: true, //插入行
          isEnableDeleteRow: true, //删除行
          isEnableInsertColumn: true, //插入列
          isEnableDeleteColumn: true, //删除列

          columns: [
            [
              { title: '序号', align: 'center', field: 'rowIndex', width: 100 },
              { title: '条码', align: 'center', field: 'barCode', width: 100 },
              { title: '名称', align: 'center', field: 'name', width: 100 },
              { title: '规格', align: 'center', field: 'standard', width: 80 },
              { title: '型号', align: 'center', field: 'model', width: 80 },
              { title: '单位', align: 'center', field: 'unit', width: 80 },
              {
                title: '数量',
                align: 'center',
                field: 'operNumber',

                width: 150,
              },
              { title: '单价', align: 'center', field: 'unitPrice', width: 80 },
              {
                title: '金额',
                align: 'center',
                field: 'allPrice',
                width: 100,
              },
              { title: '颜色', align: 'center', field: 'color', width: 80, checked: false },
              { title: '仓库名称', align: 'center', field: 'depotName', width: 100, checked: false },
              { title: '库存', align: 'center', field: 'stock', width: 100, checked: false },
              { title: '序列号', align: 'center', field: 'snList', width: 100, checked: false },
              { title: '批号', align: 'center', field: 'batchNumber', width: 100, checked: false },
              { title: '有效期', align: 'center', field: 'expirationDate', width: 100, checked: false },
              { title: '多属性', align: 'center', field: 'sku', width: 100, checked: false },
              { title: '税率(%)', align: 'center', field: 'taxRate', width: 100, checked: false },
              { title: '税额', align: 'center', field: 'taxMoney', width: 100, checked: false },
              { title: '价税合计', align: 'center', field: 'taxLastMoney', width: 100, checked: false },
              { title: '重量', align: 'center', field: 'weight', width: 100, checked: false },
              { title: '仓位货架', align: 'center', field: 'position', width: 100, checked: false },
              { title: '备注', align: 'center', field: 'remark', width: 100, checked: false },
            ],
          ],
        },
      ]),
      new hiprint.PrintElementTypeGroup('辅助', [
        { tid: 'saleOutProvider.customText', title: '文本', customText: '自定义文本', custom: true, type: 'text' },
        {
          tid: 'saleOutProvider.longText',
          title: '长文本',
          type: 'longText',
          options: {
            field: 'test.longText',
            width: 200,
            testData: '长文本分页/不分页测试',
          },
        },
        {
          tid: 'saleOutProvider.hline',
          title: '横线',
          type: 'hline',
        },
        {
          tid: 'saleOutProvider.vline',
          title: '竖线',
          type: 'vline',
        },
        {
          tid: 'saleOutProvider.rect',
          title: '矩形',
          type: 'rect',
        },
        {
          tid: 'saleOutProvider.oval',
          title: '椭圆',
          type: 'oval',
        },
        {
          tid: 'saleOutProvider.barcode',
          title: '条形码',
          type: 'barcode',
        },
        {
          tid: 'saleOutProvider.qrcode',
          title: '二维码',
          type: 'qrcode',
        },
      ]),
    ])
  }
  return {
    addElementTypes: addElementTypes,
  }
}

export const saleBackProvider = function (ops) {
  var addElementTypes = function (context) {
    context.removePrintElementTypes('saleBackProviderModule')
    context.addPrintElementTypes('saleBackProviderModule', [
      new hiprint.PrintElementTypeGroup('单据主体', [
        {
          tid: 'saleBackProvider.header',
          title: '单据抬头',
          data: '单据抬头',
          type: 'text',
          options: {
            testData: '@单据抬头',
            height: 17,
            fontSize: 16.5,
            field: 'billTitle',
            fontWeight: '700',
            textAlign: 'center',
            hideTitle: true,
          },
        },

        {
          tid: 'saleBackProvider.organName',
          title: '客户',
          data: '1888888888',
          type: 'text',
          options: {
            field: 'organName',
            testData: '@客户',
            height: 13,
            wighth: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.contacts',
          title: '客户联系人',
          data: '客户联系人',
          type: 'text',
          options: {
            testData: '客户联系人',
            height: 13,
            width: 150,
            fontSize: 10.5,
            field: 'contacts',
            textAlign: 'center',
            hideTitle: true,
          },
        },
        {
          tid: 'saleBackProvider.telephone',
          title: '客户手机',
          data: '1888888888',
          type: 'text',
          options: {
            field: 'telephone',
            testData: '@客户手机',
            height: 13,
            wighth: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.address',
          title: '客户地址',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'address',
            testData: '@客户地址',
            height: 13,
            width: 189,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.operTimeStr',
          title: '单据日期',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'operTimeStr',
            testData: '@单据日期',
            height: 13,
            width: 186,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
        {
          tid: 'saleBackProvider.number',
          title: '单据编号',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'number',
            testData: '@单据编号',
            height: 13,
            width: 159,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
        {
          tid: 'saleBackProvider.linkNumber',
          title: '关联订单',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'linkNumber',
            testData: '@关联订单',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.discount',
          title: '优惠率',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'discount',
            testData: '@优惠率',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
        {
          tid: 'saleBackProvider.discountMoney',
          title: '退款优惠',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'discountMoney',
            testData: '@退款优惠',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.discountLastMoney',
          title: '优惠后金额',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'discountLastMoney',
            testData: '@优惠后金额',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.otherMoney',
          title: '其他费用',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'otherMoney',
            testData: '@其他费用',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.accountName',
          title: '结算账户',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'accountName',
            testData: '@结算账户',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.changeAmount',
          title: '本次退款',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'changeAmount',
            testData: '@本次退款',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.debt',
          title: '本次欠款',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'debt',
            testData: '@本次欠款',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
        {
          tid: 'saleBackProvider.previousDebt',
          title: '此前欠款',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'previousDebt',
            testData: '@此前欠款',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
        {
          tid: 'saleBackProvider.allNeed',
          title: '累计欠款',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'allNeed',
            testData: '@累计欠款',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.salesManStr',
          title: '销售人员',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'salesManStr',
            testData: '@销售人员',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.remark',
          title: '备注',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'remark',
            testData: '@备注',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },

        {
          tid: 'saleBackProvider.billDetailAllPrice',
          title: '合计',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'billDetailAllPrice',
            testData: '@合计',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
        {
          tid: 'saleBackProvider.billDetailCnMoney',
          title: '合计大写',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'billDetailCnMoney',
            testData: '@合计大写',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
        {
          tid: 'saleBackProvider.creatorName',
          title: '制单人',
          data: '2020-01-01',
          type: 'text',
          options: {
            field: 'creatorName',
            testData: '@制单人',
            height: 13,
            width: 150,
            fontSize: 10.5,
            textAlign: 'left',
            textContentVerticalAlign: 'middle',
          },
        },
      ]),

      new hiprint.PrintElementTypeGroup('单据明细', [
        {
          tid: 'saleBackProvider.table',
          title: '表格信息',
          type: 'table',
          options: {
            field: 'table',
            fields: [
              { text: '序号', field: 'rowIndex' },
              { text: '条码', field: 'barCode' },
              { text: '名称', field: 'name' },
              { text: '规格', field: 'standard' },
              { text: '型号', field: 'model' },
              { text: '单位', field: 'unit' },
              { text: '数量', field: 'operNumber' },
              { text: '单价', field: 'unitPrice' },
              { text: '金额', field: 'allPrice' },
              { text: '颜色', field: 'color' },
              { text: '仓库名称', field: 'depotName' },
              { text: '库存', field: 'stock' },
              { text: '序列号', field: 'snList' },
              { text: '批号', field: 'batchNumber' },
              { text: '有效期', field: 'expirationDate' },
              { text: '多属性', field: 'sku' },
              { text: '税率(%)', field: 'taxRate' },
              { text: '税额', field: 'taxMoney' },
              { text: '价税合计', field: 'taxLastMoney' },
              { text: '重量', field: 'weight' },
              { text: '仓位货架', field: 'position' },
              { text: '备注', field: 'remark' },
            ],
          },
          editable: true,
          columnDisplayEditable: true,
          columnDisplayIndexEditable: true,
          columnTitleEditable: true,
          columnResizable: true,
          columnAlignEditable: true,
          isEnableEditField: true,
          isEnableContextMenu: true,
          isEnableInsertRow: true,
          isEnableDeleteRow: true,
          isEnableInsertColumn: true,
          isEnableDeleteColumn: true,

          columns: [
            [
              { title: '序号', align: 'center', field: 'rowIndex', width: 100 },
              { title: '条码', align: 'center', field: 'barCode', width: 100 },
              { title: '名称', align: 'center', field: 'name', width: 100 },
              { title: '规格', align: 'center', field: 'standard', width: 80 },
              { title: '型号', align: 'center', field: 'model', width: 80 },
              { title: '单位', align: 'center', field: 'unit', width: 80 },
              {
                title: '数量',
                align: 'center',
                field: 'operNumber',
                tableSummaryTitle: false,
                tableSummary: 'sum',
                tableSummaryAlign: 'left',
                width: 100,
              },
              { title: '单价', align: 'center', field: 'unitPrice', width: 80 },
              {
                title: '金额',
                align: 'center',
                field: 'allPrice',
                width: 100,
                tableSummaryTitle: false,
                tableSummary: 'sum',
                tableSummaryAlign: 'left',
              },
              { title: '颜色', align: 'center', field: 'color', width: 80, checked: false },
              { title: '仓库名称', align: 'center', field: 'depotName', width: 100, checked: false },
              { title: '库存', align: 'center', field: 'stock', width: 100, checked: false },
              { title: '序列号', align: 'center', field: 'snList', width: 100, checked: false },
              { title: '批号', align: 'center', field: 'batchNumber', width: 100, checked: false },
              { title: '有效期', align: 'center', field: 'expirationDate', width: 100, checked: false },
              { title: '多属性', align: 'center', field: 'sku', width: 100, checked: false },
              { title: '税率(%)', align: 'center', field: 'taxRate', width: 100, checked: false },
              { title: '税额', align: 'center', field: 'taxMoney', width: 100, checked: false },
              { title: '价税合计', align: 'center', field: 'taxLastMoney', width: 100, checked: false },
              { title: '重量', align: 'center', field: 'weight', width: 100, checked: false },
              { title: '仓位货架', align: 'center', field: 'position', width: 100, checked: false },
              { title: '备注', align: 'center', field: 'remark', width: 100, checked: false },
            ],
          ],
        },
      ]),
      new hiprint.PrintElementTypeGroup('辅助', [
        { tid: 'saleBackProvider.customText', title: '文本', customText: '自定义文本', custom: true, type: 'text' },
        {
          tid: 'saleBackProvider.longText',
          title: '长文本',
          type: 'longText',
          options: {
            field: 'test.longText',
            width: 200,
            testData: '长文本分页/不分页测试',
          },
        },
        {
          tid: 'saleBackProvider.hline',
          title: '横线',
          type: 'hline',
        },
        {
          tid: 'saleBackProvider.vline',
          title: '竖线',
          type: 'vline',
        },
        {
          tid: 'saleBackProvider.rect',
          title: '矩形',
          type: 'rect',
        },
        {
          tid: 'saleBackProvider.oval',
          title: '椭圆',
          type: 'oval',
        },
        {
          tid: 'saleBackProvider.barcode',
          title: '条形码',
          type: 'barcode',
        },
        {
          tid: 'saleBackProvider.qrcode',
          title: '二维码',
          type: 'qrcode',
        },
      ]),
    ])
  }
  return {
    addElementTypes: addElementTypes,
  }
}

// type: 销售出库 销售退货
export default [
  {
    value: 'saleOutProviderModule',
    type: '销售出库',
    f: saleOutProvider(),
  },

  {
    value: 'saleBackProviderModule',
    type: '销售退货',
    f: saleBackProvider(),
  },
]

