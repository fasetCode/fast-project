import { useDictStore } from '@/stores/modules/dict'
import type { DictData } from '@/stores/modules/dict'

/**
 * 初始化字典数据（全局调用一次）
 */
export const initDict = async () => {
  const dictStore = useDictStore()
  await dictStore.fetchDicts()
}

/**
 * 根据字典类型获取字典数据列表
 * @param type 字典类型
 * @returns 字典数据列表
 */
export const getDictData = (type: string): DictData[] => {
  const dictStore = useDictStore()
  return dictStore.getDictByType(type)
}

/**
 * 根据字典类型和值获取对应的标签
 * @param type 字典类型
 * @param value 字典值
 * @returns 对应的标签，如果未找到则返回原值
 */
export const getDictLabel = (type: string, value: string | number): string => {
  const dictStore = useDictStore()
  return dictStore.getDictLabel(type, String(value))
}

/**
 * 检查字典数据是否已加载
 * @returns 是否已加载
 */
export const isDictLoaded = (): boolean => {
  const dictStore = useDictStore()
  return dictStore.isDictsLoaded
}

export default {
  initDict,
  getDictData,
  getDictLabel,
  isDictLoaded
}
