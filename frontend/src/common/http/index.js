import axios from 'axios'
import Toast from '@/helper/toast'
import logger from '@/common/logger'
import constants from '@/constants'
import { translate } from '@/helper/static'

const instance = createInstance({})

function createInstance() {
  const instance = axios.create()
  instance.defaults.baseURL = import.meta.env.VITE_API_REQUEST_URL
  instance.defaults.timeout = import.meta.env.VITE_API_REQUEST_TIMEOUT
  instance.defaults.headers.common['Content-Type'] = 'application/json'
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
          body: translate('common.error.unknown')
        })
        return Promise.resolve(new ResponseData(false))
      }
    }
  )
  return instance
}

async function handleResponseError(data) {
  let message = data.data.message
  const details = data.data.payload.details
  if (details) {
    message += details instanceof Object ? '\n' + Object.values(details).join('\n') : details
  }

  Toast.sendErrorMessage({
    body: message
  })

  return Promise.resolve(new ResponseData(false, data.payload))
}

async function handleRequestError(error) {
  if (error.message === 'Network Error') {
    Toast.sendErrorMessage({
      body: translate('common.error.network')
    })
  } else {
    Toast.sendErrorMessage({
      body: translate('common.error.client')
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
  if(settings.auth){
    instance.defaults.headers.common.Authorization= `Bearer ${localStorage.getItem(constants.TOKEN.ACCESS_TOKEN)}`
  } else {
    delete instance.defaults.headers.common.Authorization
  }
  return instance;
}

export { ResponseData }
