import { ToastSeverity } from 'primevue/api'
import { app } from '@/main'
import constants from '@/constants'
import { isMobile, translate } from '@/helper/index'
class Toast {
  static sendInfoMessage({ title, body = 'Replace me, please !!!' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.INFO,
      summary: !title ? translate('global.notification.default-title.info') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.INFO,
      group: isMobile() ? 'mobile' : 'default'
    })
  }

  static sendWarnMessage({ title, body = 'Replace me, please !!!' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.WARN,
      summary: !title ? translate('global.notification.default-title.warn') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.WARN,
      group: isMobile() ? 'mobile' : 'default'
    })
  }

  static sendSuccessMessage({ title, body = 'Replace me, please !!!' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.SUCCESS,
      summary: !title ? translate('global.notification.default-title.success') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.SUCCESS,
      group: isMobile() ? 'mobile' : 'default'
    })
  }

  static sendErrorMessage({ title, body = 'Replace me, please !!!' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.ERROR,
      summary: !title ? translate('global.notification.default-title.error') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.ERROR,
      group: isMobile() ? 'mobile' : 'default'
    })
  }
}

export default Toast
