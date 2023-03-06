import axios from 'axios'
import Toast from '@/helper/Toast'
import logger from '@/common/logger'
import constants from '@/constants'

const anonymous = createInstance({
  'Content-Type': 'application/json'
})

const auth = createInstance({
  'Content-Type': 'application/json',
  Authorization: `Bearer ${localStorage.getItem(constants.TOKEN.ACCESS_TOKEN)}`
})

const anonymousUpload = createInstance({
  'Content-Type': 'multipart/form-data'
})

const authUpload = createInstance({
  'Content-Type': 'multipart/form-data',
  Authorization: `Bearer ${localStorage.getItem(constants.TOKEN.ACCESS_TOKEN)}`
})

function createInstance(headers = {}) {
  const instance = axios.create()
  instance.defaults.baseURL = import.meta.env.VITE_API_REQUEST_URL
  instance.defaults.timeout = import.meta.env.VITE_API_REQUEST_TIMEOUT
  Object.entries(headers).forEach(([key, value]) => {
    instance.defaults.headers[`${key}`] = `${value}`
  })
  instance.interceptors.response.use(
    function (response) {
      Toast.sendSuccessMessage({
        body: response.data.message
      })
      return Promise.resolve(new ResponseData(ResponseType.SUCCESS, response.data.payload))
    },
    function (error) {
      logger.debug(error)
      if (error.response) {
        return handleResponseError(error.response)
      } else if (error.request) {
        return handleRequestError(error)
      } else {
        Toast.sendErrorMessage({
          body: 'Unknown error happen'
        })
        return Promise.resolve(new ResponseData(ResponseType.ERROR, null))
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

  return Promise.resolve(new ResponseData(ResponseType.ERROR, data.payload))
}

async function handleRequestError(error) {
  if (error.message === 'Network Error') {
    Toast.sendErrorMessage({
      body: 'Cannot connect to server'
    })
  } else {
    Toast.sendErrorMessage({
      body: 'Can not process your request, please check again'
    })
  }
  return Promise.resolve(new ResponseData(ResponseType.ERROR, null))
}

const ResponseData = class {
  constructor(type, payload) {
    this._type = type
    this._payload = payload
  }

  get payload() {
    return this._payload
  }

  get type() {
    return this._type
  }
}

const ResponseType = {
  ERROR: 'error',
  SUCCESS: 'success'
}

export function request(settings = { auth: false }) {
  return settings.auth ? auth : anonymous
}

export function upload(settings = { auth: false }) {
  return settings.auth ? authUpload : anonymousUpload
}

export { ResponseData, ResponseType }
