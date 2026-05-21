import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from '@/utils/axios'

// 字典数据接口
export interface DictData {
  label: string
  value: string
}

export interface Dict {
  type: string
  data: DictData[]
}

export const useDictStore = defineStore('dict', () => {
  const dicts = ref<Dict[]>([])
  const isDictsLoaded = ref(false)
  // 字典数据映射，方便快速查找
  const dictMap = ref<Map<string, DictData[]>>(new Map())

  // 从后端获取所有字典数据
  const fetchDicts = async () => {
    // 已有数据直接返回
    if (isDictsLoaded.value && dicts.value.length > 0) {
      return dicts.value
    }
    try {
      const res = await axios.get('/auth/getDict')
      if (res && res.code === 200) {
        dicts.value = res.data || []
        isDictsLoaded.value = true
        // 构建映射
        buildDictMap()
        return dicts.value
      }
      return []
    } catch (error) {
      console.error('Failed to fetch dicts:', error)
      return []
    }
  }

  // 构建字典映射
  const buildDictMap = () => {
    dictMap.value.clear()
    for (const dict of dicts.value) {
      dictMap.value.set(dict.type, dict.data || [])
    }
  }

  // 根据类型获取字典数据
  const getDictByType = (type: string): DictData[] => {
    return dictMap.value.get(type) || []
  }

  // 根据类型和value获取label
  const getDictLabel = (type: string, value: string): string => {
    const dataList = dictMap.value.get(type) || []
    const item = dataList.find((d) => d.value === value)
    return item?.label || value
  }

  // 重置字典状态
  const resetDicts = () => {
    dicts.value = []
    dictMap.value.clear()
    isDictsLoaded.value = false
  }

  return {
    dicts,
    isDictsLoaded,
    dictMap,
    fetchDicts,
    getDictByType,
    getDictLabel,
    resetDicts
  }
})
