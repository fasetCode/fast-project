const TOKEN_KEY = 'token'

export const TokenService = {
  /**
   * 设置 Token
   */
  setToken(token: string): void {
    localStorage.setItem(TOKEN_KEY, token)
  },

  /**
   * 获取 Token
   */
  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY)
  },

  /**
   * 移除 Token
   */
  removeToken(): void {
    localStorage.removeItem(TOKEN_KEY)
    sessionStorage.removeItem('admin_vue_tabs')
  },

  /**
   * 检查 Token 是否存在
   */
  hasToken(): boolean {
    return !!this.getToken()
  },

  /**
   * 清除所有认证信息并跳转到登录页
   */
  clearAndRedirect(): void {
    this.removeToken()
    window.location.href = '/login'
  },
}

export default TokenService
