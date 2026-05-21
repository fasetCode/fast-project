function toHex(value: number): string {
    return value.toString(16).padStart(2, '0')
}

function generateByRandomValues(): string {
    const bytes = new Uint8Array(16)
    globalThis.crypto.getRandomValues(bytes)

    bytes[6] = (bytes[6] & 0x0f) | 0x40
    bytes[8] = (bytes[8] & 0x3f) | 0x80

    const hex = Array.from(bytes, toHex).join('')
    return `${hex.slice(0, 8)}${hex.slice(8, 12)}${hex.slice(12, 16)}${hex.slice(16, 20)}${hex.slice(20, 32)}`
}

function generateByFallback(): string {
    const seed = `${Date.now()}${Math.random()}${Math.random()}`
    return seed.replace(/[^\d]/g, '').padEnd(32, '0').slice(0, 32)
}

export function generateRequestId(): string {
    const cryptoApi = globalThis.crypto
    if (cryptoApi?.randomUUID) {
        return cryptoApi.randomUUID().replace(/-/g, '')
    }

    if (cryptoApi?.getRandomValues) {
        return generateByRandomValues()
    }

    return generateByFallback()
}

export function getRequestId(): string {
    return `${generateRequestId()}${generateRequestId()}`
}
