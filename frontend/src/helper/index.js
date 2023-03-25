import i18n from '@/i18n'
import { app } from '@/main'
import constants from '@/constants'

const translate = i18n.global.t

export { translate }

export function isMobile() {
  return app.config.globalProperties.$isMobile()
}

export function getCurrentLocale() {
  return localStorage.getItem(constants.APP.CURRENT_LOCALE) ?? 'en'
}
export function resetLocalStorage() {
  const locale = getCurrentLocale()
  localStorage.clear()
  switchLanguage(locale)
}

export function switchLanguage(locale) {
  i18n.global.locale.value = locale
  document.querySelector('html').setAttribute('lang', locale)
  localStorage.setItem(constants.APP.CURRENT_LOCALE, locale)
}
