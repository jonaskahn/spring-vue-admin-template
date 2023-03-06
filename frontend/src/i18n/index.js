import { createI18n } from 'vue-i18n'
import en from '@/i18n/locales/en.json'
import vi from '@/i18n/locales/vi.json'

const i18n = createI18n({
  locale: import.meta.env.VITE_DEFAULT_LOCALE,
  fallbackLocale: import.meta.env.VITE_FALLBACK_LOCALE,
  legacy: false,
  globalInjection: true,
  messages: {
    en,
    vi
  }
})

export default i18n
