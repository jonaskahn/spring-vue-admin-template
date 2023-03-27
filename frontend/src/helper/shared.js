import { app } from '@/main'
import constants from '@/constants'
import { getCurrentLocale, switchLanguage } from '@/helper/locale'

export function isMobileDevice() {
  return app.config.globalProperties.$isMobile()
}

export class StorageManager {
  static reset() {
    const locale = getCurrentLocale()
    localStorage.clear()
    switchLanguage(locale)
  }

  static updateSigninState() {
    localStorage.setItem(constants.APP.SIGNIN_STATE, 'INVOKED')
  }

  static clearSigninState() {
    localStorage.removeItem(constants.APP.SIGNIN_STATE)
  }

  static updateTokenInfo(token, expiration, authorities) {
    localStorage.setItem(constants.TOKEN.ACCESS_TOKEN, token)
    localStorage.setItem(constants.TOKEN.ACCESS_TOKEN_EXPIRED, expiration)
    localStorage.setItem(constants.TOKEN.AUTHORITIES, authorities)
  }

  static getTokenAuthorities() {
    const rawData = localStorage.getItem(constants.TOKEN.AUTHORITIES)
    return rawData ? rawData.split(',') : []
  }
}
