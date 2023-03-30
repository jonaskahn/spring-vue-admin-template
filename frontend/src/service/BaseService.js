import http, { ResponseType } from '@/common/http'
import ToastHelper from '@/helper/toast'
import router from '@/router'
import Page from '@/constants/page'

export default class BaseService {
  static showMessage(res) {
    switch (res.state) {
      case ResponseType.SUCCESS:
        this.#showSuccessMessage(res.message)
        break
      case ResponseType.NOT_FOUND:
        this.#showErrorMessage(res.message)
        break
      case ResponseType.UNAUTHORIZED:
        this.#showErrorMessage(res.message)
        break
      case ResponseType.ACCESS_DENIED:
        this.#showErrorMessage(res.message)
        break
      case ResponseType.CLIENT_ERROR:
        this.#showErrorMessage(res.message)
        break
      case ResponseType.NETWORK_ERROR:
        this.#showErrorMessage(res.message)
        break
      case ResponseType.UNDEFINED:
        this.#showErrorMessage('service.default-message.unknown-error')
        break
      default:
        break
    }
  }

  static #showErrorMessage(message) {
    ToastHelper.sendErrorMessage({
      body: message
    })
  }

  static #showSuccessMessage(message) {
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
      showToast: true,
      redirectOnerror: true
    }
  ) {
    const res = await http({ auth: option.secure ?? true })({
      url: spec.url,
      method: spec.method,
      data: spec.data
    })
    if (option.showToast ?? true) {
      BaseService.showMessage(res)
    }
    if ((option.redirectOnerror ?? true) && res.state !== ResponseType.SUCCESS) {
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
          return Promise.reject()
      }
    }
    switch (res.state) {
      case ResponseType.SUCCESS:
        return Promise.resolve({
          ok: true,
          data: res.payload
        })
      default:
        return Promise.reject({
          ok: false,
          data: res.payload
        })
    }
  }
}
