import { ToastSeverity } from 'primevue/api'
import { app } from '@/main'
import constants from '@/constants'
import { isMobileDevice } from '@/helper/shared'
import { translate } from '@/helper/locale'

export default class ToastHelper {
  static sendInfoMessage({ title, body = 'Replace me, please !!!' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.INFO,
      summary: translate(title || 'global.notification.default-title.info'),
      detail: translate(body),
      life: constants.TOAST_TIMEOUT.INFO,
      group: isMobileDevice() ? 'mobile' : 'desktop'
    })
  }

  static sendWarnMessage({ title, body = 'Replace me, please !!!' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.WARN,
      summary: translate(title || 'global.notification.default-title.warn'),
      detail: translate(body),
      life: constants.TOAST_TIMEOUT.WARN,
      group: isMobileDevice() ? 'mobile' : 'desktop'
    })
  }

  static sendSuccessMessage({ title, body = 'Replace me, please !!!' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.SUCCESS,
      summary: translate(title || 'global.notification.default-title.success'),
      detail: translate(body),
      life: constants.TOAST_TIMEOUT.SUCCESS,
      group: isMobileDevice() ? 'mobile' : 'desktop'
    })
  }

  static sendErrorMessage({ title, body = 'Replace me, please !!!' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.ERROR,
      summary: translate(title || 'global.notification.default-title.error'),
      detail: translate(body),
      life: constants.TOAST_TIMEOUT.ERROR,
      group: isMobileDevice() ? 'mobile' : 'desktop'
    })
  }
}
