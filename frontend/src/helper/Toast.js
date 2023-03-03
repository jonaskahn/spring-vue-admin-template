import { ToastSeverity } from 'primevue/api'
import { app } from '@/main'
import constants from '@/constants'

export function sendInfoMessage(title = 'I am title', body = 'I am body') {
  app.config.globalProperties.$toast.add({
    severity: ToastSeverity.INFO,
    summary: title,
    detail: body,
    life: constants.TOAST_TIMEOUT
  })
}

export function sendWarnMessage(title = 'I am title', body = 'I am body') {
  app.config.globalProperties.$toast.add({
    severity: ToastSeverity.WARN,
    summary: title,
    detail: body,
    life: constants.TOAST_TIMEOUT
  })
}

export function sendSuccessMessage(title = 'I am title', body = 'I am body') {
  app.config.globalProperties.$toast.add({
    severity: ToastSeverity.SUCCESS,
    summary: title,
    detail: body,
    life: constants.TOAST_TIMEOUT
  })
}

export function sendErrorMessage(title = 'I am title', body = 'I am body') {
  app.config.globalProperties.$toast.add({
    severity: ToastSeverity.ERROR,
    summary: title,
    detail: body,
    life: constants.TOAST_TIMEOUT
  })
}
