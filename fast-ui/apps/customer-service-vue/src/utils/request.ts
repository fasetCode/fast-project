import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'

const wrapJsonBigIntAsString = (raw: string) => {
    const colon = /(:\s*)(-?\d{16,})(?=\s*[,}\]])/g
    const array = /([\[,]\s*)(-?\d{16,})(?=\s*[,}\]])/g
    return raw.replace(colon, '$1"$2"').replace(array, '$1"$2"')
}

const tryParseJsonPreserveLong = (data: unknown, headers?: any) => {
    if (typeof data !== 'string') return data

    const contentType = typeof headers === 'object' ? (headers['content-type'] || headers['Content-Type']) : undefined
    const maybeJson = typeof contentType === 'string' ? contentType.includes('application/json') : false
    const trimmed = data.trim()
    const jsonLike = trimmed.startsWith('{') || trimmed.startsWith('[')
    if (!maybeJson && !jsonLike) return data

    const normalized = wrapJsonBigIntAsString(trimmed)
    try {
        return JSON.parse(normalized)
    } catch {
        return data
    }
}

const createAxios = (config?: AxiosRequestConfig): AxiosInstance => {
    const instance = axios.create({
        baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
        timeout: 10000,
        transformResponse: [(data, headers) => tryParseJsonPreserveLong(data, headers)],
        ...config,
    })

    instance.interceptors.request.use(
        (config) => {
            const token = localStorage.getItem('token')
            if (token) {
                config.headers.Authorization = `Bearer ${token}`
            }
            return config
        },
        (error) => Promise.reject(error)
    )

    instance.interceptors.response.use(
        (response: AxiosResponse) => response,
        (error) => {
            if (error.response?.status === 401) {
                localStorage.removeItem('token')
                window.location.href = '/login'
            }
            return Promise.reject(error)
        }
    )

    return instance
}

export default createAxios()
