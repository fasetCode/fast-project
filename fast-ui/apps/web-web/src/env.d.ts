/// <reference types="vite/client" />

declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

declare module 'json-bigint' {
  const JSONbig: {
    parse(text: string): any
    stringify(value: any): string
  }
  export default JSONbig
}
