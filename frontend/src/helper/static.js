import i18n from '@/i18n'
import { app } from '@/main'

const translate = i18n.global.t

export { translate }

export function isMobile() {
  return app.config.globalProperties.$isMobile()
}
