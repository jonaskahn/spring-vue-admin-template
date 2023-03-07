import { ToastSeverity } from 'primevue/api'
import { app } from '@/main'
import constants from '@/constants'
import { isMobile, translate } from '@/helper/static'
class Toast {
  static sendInfoMessage({ title, body = 'Dont let me show' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.INFO,
      summary: !title ? translate('common.message.info') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.INFO,
      group: isMobile() ? 'mobile' : ''
    })
  }

  static sendWarnMessage({ title, body = 'Dont let me show' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.WARN,
      summary: !title ? translate('common.message.warn') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.WARN,
      group: isMobile() ? 'mobile' : ''
    })
  }

  static sendSuccessMessage({ title, body = 'Dont let me show' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.SUCCESS,
      summary: !title ? translate('common.message.success') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.SUCCESS,
      group: isMobile() ? 'mobile' : ''
    })
  }

  static sendErrorMessage({ title, body = 'Dont let me show' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.ERROR,
      summary: !title ? translate('common.message.error') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.ERROR,
      group: isMobile() ? 'mobile' : ''
    })
  }
}

export default Toast
