import http, { ResponseType } from '@/common/http'
import ToastHelper from '@/helper/toast'
import router from '@/router'
import Page from '@/constants/page'
import logger from '@/common/logger'

export default class BaseService {
  static #showMessage(res) {
    switch (res.state) {
      case ResponseType.SUCCESS:
        this.showSuccessMessage(res.message)
        break
      case ResponseType.NOT_FOUND:
      case ResponseType.BAD_REQUEST:
      case ResponseType.UNAUTHORIZED:
      case ResponseType.ACCESS_DENIED:
      case ResponseType.CLIENT_ERROR:
      case ResponseType.NETWORK_ERROR:
        this.showErrorMessage(res.message)
        break
      case ResponseType.UNDEFINED:
        this.showErrorMessage('service.default-message.unknown-error')
        break
      default:
        break
    }
  }

  static showErrorMessage(message) {
    ToastHelper.sendErrorMessage({
      body: message
    })
  }

  static showSuccessMessage(message) {
    ToastHelper.sendSuccessMessage({
      body: message
    })
  }

  async request(
    spec = {
      url: '',
      method: 'post',
      data: {}
    },
    option = {
      secure: true,
      showToast: false,
      redirectOnerror: false
    }
  ) {
    const res = await http({ auth: option.secure ?? true })({
      url: spec.url,
      method: spec.method,
      data: spec.data
    })
    logger.debug(res)
    if (option.showToast ?? false) {
      BaseService.#showMessage(res)
    }
    if ((option.redirectOnerror ?? false) && res.state !== ResponseType.SUCCESS) {
      switch (res.state) {
        case ResponseType.UNAUTHORIZED:
          return router.push({
            name: Page.AUTH.LOGIN.name
          })
        case ResponseType.ACCESS_DENIED:
          return router.push({
            name: Page.ACCESS.DENIED.name
          })
        default:
          break
      }
    }
    switch (res.state) {
      case ResponseType.SUCCESS:
        return Promise.resolve({
          state: true,
          payload: res.payload
        })
      default:
        return Promise.resolve({
          state: false,
          payload: res.message
        })
    }
  }
}
