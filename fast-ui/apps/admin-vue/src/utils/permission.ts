import { usePermissionStore } from '@/stores/modules/permission'

/**
 * 检查当前用户是否拥有指定按钮权限
 * @param permission 权限标识
 * @returns 是否拥有该权限
 */
export const hasButtonPermission = (permission: string): boolean => {
  const permissionStore = usePermissionStore()
  return permissionStore.buttonPermissions.includes(permission)
}

/**
 * 获取当前用户的所有按钮权限
 * @returns 按钮权限列表
 */
export const getButtonPermissions = (): string[] => {
  const permissionStore = usePermissionStore()
  return permissionStore.buttonPermissions
}

/**
 * 批量检查多个按钮权限
 * @param permissions 权限标识数组
 * @returns 是否有任意一个权限
 */
export const hasAnyButtonPermission = (permissions: string[]): boolean => {
  const permissionStore = usePermissionStore()
  return permissions.some((p) => permissionStore.buttonPermissions.includes(p))
}

/**
 * 批量检查多个按钮权限，必须全部拥有
 * @param permissions 权限标识数组
 * @returns 是否拥有所有权限
 */
export const hasAllButtonPermissions = (permissions: string[]): boolean => {
  const permissionStore = usePermissionStore()
  return permissions.every((p) => permissionStore.buttonPermissions.includes(p))
}

export default {
  hasButtonPermission,
  getButtonPermissions,
  hasAnyButtonPermission,
  hasAllButtonPermissions,
}
