import { app } from '@/main'
import constants from '@/constants'
import { getCurrentLocale, switchLanguage } from '@/helper/locale'

export function isMobileDevice() {
  return app.config.globalProperties.$isMobile()
}

export function resetLocalStorage() {
  const locale = getCurrentLocale()
  localStorage.clear()
  switchLanguage(locale)
}

export function updateSigninState() {
  localStorage.setItem(constants.APP.SIGNIN_STATE, 'REFRESH')
}

export function removeSigninState() {
  localStorage.removeItem(constants.APP.SIGNIN_STATE)
}
