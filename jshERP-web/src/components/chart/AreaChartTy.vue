<template>
  <div :style="{ padding: '0' }">
    <h4 :style="{ marginBottom: '20px' }">{{ title }}</h4>
    <v-chart ref="chartRef" class="chart" :option="chartOption" :autoresize="true" :style="{ height: `${height}px` }" />
  </div>
</template>

<script>
import { defineComponent, ref, computed, watch, onMounted, onBeforeUnmount } from 'vue'
import { use } from 'echarts/core'
import { LineChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, DataZoomComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import VChart from 'vue-echarts'

// 注册必要的ECharts模块
use([CanvasRenderer, LineChart, GridComponent, TooltipComponent, DataZoomComponent])

export default defineComponent({
  name: 'AreaChartTy',
  components: { VChart },
  props: {
    dataSource: {
      type: Array,
      required: true,
    },
    title: {
      type: String,
      default: '',
    },
    x: {
      type: String,
      default: 'x',
    },
    y: {
      type: String,
      default: 'y',
    },
    min: {
      type: Number,
      default: 0,
    },
    max: {
      type: Number,
      default: null,
    },
    height: {
      type: Number,
      default: 254,
    },
    lineSize: {
      type: Number,
      default: 2,
    },
    color: {
      type: String,
      default: '#1890FF',
    },
    lineColor: {
      type: String,
      default: '#1890FF',
    },
  },
  setup(props) {
    const chartRef = ref(null) // 图表实例引用

    // 计算属性：生成ECharts配置
    const chartOption = computed(() => {
      const xData = props.dataSource.map((item) => item.x)
      const yData = props.dataSource.map((item) => item.y)

      return {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line',
            label: {
              backgroundColor: '#6a7985',
            },
          },
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          top: '10%',
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: xData,
          name: props.x,
          nameLocation: 'end',
          axisLabel: {
            interval: 0,
            rotate: xData.length > 10 ? 45 : 0,
          },
        },
        yAxis: {
          type: 'value',
          name: props.y,
          min: props.min,
          max: props.max,
        },
        series: [
          {
            name: props.y,
            type: 'line',
            smooth: true,
            symbol: 'none',
            lineStyle: {
              width: props.lineSize,
              color: props.lineColor,
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {
                    offset: 0,
                    color: `${props.color}80`, // 80表示50%透明度
                  },
                  {
                    offset: 1,
                    color: `${props.color}00`, // 00表示0%透明度
                  },
                ],
              },
            },
            data: yData,
          },
        ],
        dataZoom: [
          {
            type: 'inside',
            start: 0,
            end: 100,
          },
          {
            start: 0,
            end: 10,
          },
        ],
      }
    })

    // 监听数据源变化 - 自动更新图表
    watch(
      () => props.dataSource,
      (newVal, oldVal) => {
        if (!chartRef.value) return

        const chartInstance = chartRef.value.getEchartsInstance()

        // 仅当数据实际变化时更新图表
        if (JSON.stringify(newVal) !== JSON.stringify(oldVal)) {
          // 更新图表数据
          chartInstance.setOption(
            {
              xAxis: {
                data: newVal.map((item) => item.x),
              },
              series: [
                {
                  data: newVal.map((item) => item.y),
                },
              ],
            },
            {
              notMerge: false, // 合并选项而不是完全替换
              lazyUpdate: true, // 延迟更新提高性能
            }
          )

          // 添加数据更新动画效果
          chartInstance.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
          })

          // 自动调整图表大小
          setTimeout(() => {
            chartInstance.resize()
          }, 100)
        }
      },
      { deep: true }
    )

    // 监听窗口大小变化
    const handleResize = () => {
      if (chartRef.value) {
        chartRef.value.getEchartsInstance().resize()
      }
    }

    onMounted(() => {
      window.addEventListener('resize', handleResize)
    })

    onBeforeUnmount(() => {
      window.removeEventListener('resize', handleResize)
      if (chartRef.value) {
        chartRef.value.dispose()
      }
    })

    return {
      chartOption,
      chartRef,
    }
  },
})
</script>

<style lang="less" scoped>
.chart {
  width: 100%;
  transition: all 0.3s ease;
}
</style>
