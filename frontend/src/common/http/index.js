import axios from 'axios'
import logger from '@/common/logger'
import constants from '@/constants'
import { getCurrentLocale, LocalStorageManager, Toast, translate } from '@/helper'
import router from '@/router'
import RouteInfo from '@/constants/page'

const insecure = createInstance({ 'Content-Type': 'application/json;charset=utf-8' })

const secure = createInstance({ 'Content-Type': 'application/json;charset=utf-8' })

function createInstance(headers = {}) {
  const instance = axios.create()
  instance.defaults.baseURL = import.meta.env.VITE_API_REQUEST_URL
  instance.defaults.timeout = import.meta.env.VITE_API_REQUEST_TIMEOUT
  Object.entries(headers).forEach(([key, value]) => {
    instance.defaults.headers.common[`${key}`] = `${value}`
  })
  instance.interceptors.response.use(
    function (response) {
      Toast.sendSuccessMessage({
        body: response.data.message
      })
      return Promise.resolve(new ResponseData(true, response.data.payload))
    },
    function (error) {
      logger.debug(error)
      if (error.response) {
        return handleResponseError(error.response)
      } else if (error.request) {
        return handleRequestError(error)
      } else {
        Toast.sendErrorMessage({
          body: translate('service.default-message.api-error-unknown')
        })
        return Promise.resolve(new ResponseData(false))
      }
    }
  )
  return instance
}

async function handleResponseError(res) {
  logger.debug(res)
  let message = res.data.message
  const details = res.data.payload.details
  switch (res.status) {
    case 401:
      LocalStorageManager.reset()
      Toast.sendErrorMessage({
        body: translate('service.default-message.response-status-401')
      })
      return router.push(RouteInfo.AUTH.LOGIN.path)
    case 403:
      Toast.sendErrorMessage({
        body: translate('service.default-message.response-status-403')
      })
      return router.push(RouteInfo.AUTH.ACCESS_DENIED.path)
    case 404:
      Toast.sendErrorMessage({
        body: translate('service.default-message.response-status-404')
      })
      return router.push(RouteInfo.AUTH.NOT_FOUND.path)
    default:
      if (details) {
        message += details instanceof Object ? '\n' + Object.values(details).join('\n') : details
      }
      Toast.sendErrorMessage({
        body: message
      })
      return Promise.resolve(new ResponseData(false, res.payload))
  }
}

async function handleRequestError(error) {
  if (error.message === 'Network Error') {
    Toast.sendErrorMessage({
      body: translate('service.default-message.api-error-network')
    })
  } else {
    Toast.sendErrorMessage({
      body: translate('service.default-message.api-error-client')
    })
  }
  return Promise.resolve(new ResponseData(false))
}

const ResponseData = class {
  constructor(status, payload) {
    this._status = status
    this._payload = payload
  }

  get payload() {
    return this._payload
  }

  get ok() {
    return this._status
  }
}

export function request(settings = { auth: false }) {
  if (settings.auth) {
    secure.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem(
      constants.TOKEN.ACCESS_TOKEN
    )}`
    secure.defaults.headers.common['Accept-Language'] = getCurrentLocale()
    return secure
  } else {
    insecure.defaults.headers.common['Accept-Language'] = getCurrentLocale()
    return insecure
  }
}

export { ResponseData }
