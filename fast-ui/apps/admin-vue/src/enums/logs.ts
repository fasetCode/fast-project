/**
 * 日志类型枚举
 */
export enum LogType {
  SYSTEM = 'SYSTEM',
  BUSINESS = 'BUSINESS',
  LOGIN = 'LOGIN',
  EXCEPTION = 'EXCEPTION',
  OPERATION = 'OPERATION',
}

/**
 * 日志操作动作枚举
 */
export enum LogAction {
  QUERY = 'QUERY',
  CREATE = 'CREATE',
  UPDATE = 'UPDATE',
  DELETE = 'DELETE',
  IMPORT = 'IMPORT',
  EXPORT = 'EXPORT',
  LOGIN = 'LOGIN',
  LOGOUT = 'LOGOUT',
  OTHER = 'OTHER',
}

/**
 * 日志类型标签映射
 */
export const LogTypeLabel: Record<LogType, string> = {
  [LogType.SYSTEM]: '系统日志',
  [LogType.BUSINESS]: '业务日志',
  [LogType.LOGIN]: '登录日志',
  [LogType.EXCEPTION]: '异常日志',
  [LogType.OPERATION]: '操作日志',
}

/**
 * 日志类型颜色映射
 */
export const LogTypeColor: Record<LogType, string> = {
  [LogType.SYSTEM]: 'purple',
  [LogType.BUSINESS]: 'blue',
  [LogType.LOGIN]: 'green',
  [LogType.EXCEPTION]: 'red',
  [LogType.OPERATION]: 'orange',
}

/**
 * 日志动作标签映射
 */
export const LogActionLabel: Record<LogAction, string> = {
  [LogAction.QUERY]: '查询',
  [LogAction.CREATE]: '新增',
  [LogAction.UPDATE]: '修改',
  [LogAction.DELETE]: '删除',
  [LogAction.IMPORT]: '导入',
  [LogAction.EXPORT]: '导出',
  [LogAction.LOGIN]: '登录',
  [LogAction.LOGOUT]: '登出',
  [LogAction.OTHER]: '其他',
}

/**
 * 日志动作颜色映射
 */
export const LogActionColor: Record<LogAction, string> = {
  [LogAction.QUERY]: 'cyan',
  [LogAction.CREATE]: 'green',
  [LogAction.UPDATE]: 'blue',
  [LogAction.DELETE]: 'red',
  [LogAction.IMPORT]: 'purple',
  [LogAction.EXPORT]: 'orange',
  [LogAction.LOGIN]: 'green',
  [LogAction.LOGOUT]: 'default',
  [LogAction.OTHER]: 'default',
}

/**
 * HTTP 方法颜色映射
 */
export const HttpMethodColor: Record<string, string> = {
  GET: 'green',
  POST: 'blue',
  PUT: 'orange',
  DELETE: 'red',
  PATCH: 'purple',
}
