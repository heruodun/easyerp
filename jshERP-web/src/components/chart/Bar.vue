<template>
  <div :style="{ padding: '0 0 32px 32px' }">
    <h3 :style="{ marginBottom: '20px' }">{{ title }}</h3>
    <v-chart ref="chartRef" class="chart" :option="chartOption" :autoresize="true" :style="{ height: `${height}px` }" />
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted, onBeforeUnmount } from 'vue'
import { use } from 'echarts/core'
import { BarChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, DataZoomComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import VChart from 'vue-echarts'
import { DEFAULT_COLOR } from '@/store/mutation-types'

// 注册必要的ECharts模块
use([CanvasRenderer, BarChart, GridComponent, TooltipComponent])

const props = defineProps({
  dataSource: {
    type: Array,
    required: true,
  },
  yaxisText: {
    type: String,
    default: 'y',
  },
  title: {
    type: String,
    default: '',
  },
  height: {
    type: Number,
    default: 254,
  },
})

// 响应式状态
const color = ref(localStorage.getItem(DEFAULT_COLOR) || '#1890ff')
const chartRef = ref(null) // 图表实例引用

// 计算属性：生成ECharts配置
const chartOption = computed(() => {
  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow',
      },
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '15%',
      top: '10%',
      containLabel: true,
    },
    xAxis: {
      type: 'category',
      data: props.dataSource.map((item) => item.x),
      axisLabel: {
        interval: 0,
        rotate: props.dataSource.length > 6 ? 45 : 0,
      },
    },
    yAxis: {
      type: 'value',
      name: props.yaxisText,
    },
    series: [
      {
        name: props.yaxisText,
        type: 'bar',
        barWidth: '60%',
        itemStyle: {
          color: color.value,
        },
        data: props.dataSource.map((item) => item.y),
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

// 监听数据源变化 - 核心更新逻辑
watch(
  () => props.dataSource,
  (newVal, oldVal) => {
    if (!chartRef.value) return

    const chartInstance = chartRef.value.getEchartsInstance()

    // 优化：仅当数据实际变化时更新图表
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
</script>

<style scoped>
.chart {
  width: 100%;
  transition: all 0.3s ease;
}
</style>
