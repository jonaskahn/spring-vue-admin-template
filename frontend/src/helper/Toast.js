import { ToastSeverity } from 'primevue/api'
import { app } from '@/main'
import constants from '@/constants'

class Toast {
  static sendInfoMessage({ title, body = 'Dont let me show' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.INFO,
      summary: !title ? app.config.globalProperties.$t('common.message.info') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.INFO
    })
  }

  static sendWarnMessage({ title, body = 'Dont let me show' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.WARN,
      summary: !title ? app.config.globalProperties.$t('common.message.warn') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.WARN
    })
  }

  static sendSuccessMessage({ title, body = 'Dont let me show' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.SUCCESS,
      summary: !title ? app.config.globalProperties.$t('common.message.success') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.SUCCESS
    })
  }

  static sendErrorMessage({ title, body = 'Dont let me show' }) {
    app.config.globalProperties.$toast.add({
      severity: ToastSeverity.ERROR,
      summary: !title ? app.config.globalProperties.$t('common.message.error') : title,
      detail: body,
      life: constants.TOAST_TIMEOUT.ERROR
    })
  }
}

export default Toast
