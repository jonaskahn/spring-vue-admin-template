/// <reference types="vite/client" />

interface ImportMetaEnv {
    readonly VITE_BASE_API_URL: "http://localhost:8668/api/"
}

interface ImportMeta {
    readonly env: ImportMetaEnv
}