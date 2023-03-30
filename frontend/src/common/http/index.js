import axios from 'axios'
import logger from '@/common/logger'
import constants from '@/constants'
import { getCurrentLocale } from '@/helper'

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
      return Promise.resolve(
        new ResponseData(ResponseType.SUCCESS, response.data.message, response.data.payload)
      )
    },
    function (error) {
      logger.debug(error)
      if (error.response) {
        return handleResponseError(error.response)
      } else if (error.request) {
        return handleRequestError(error)
      } else {
        return Promise.resolve(new ResponseData(ResponseType.UNDEFINED))
      }
    }
  )
  return instance
}

async function handleResponseError(res) {
  logger.debug(`Response details :\n${res}`)
  const message = res.data.message
  const details = res.data.payload.details
  const summary =
    message + details
      ? details instanceof Object
        ? '\n' + Object.values(details).join('\n')
        : details
      : ''
  switch (res.status) {
    case 401:
      return Promise.resolve(
        new ResponseData(
          ResponseType.SUCCESS,
          summary ?? 'service.default-message.response-status-401'
        )
      )
    case 403:
      return Promise.resolve(
        new ResponseData(
          ResponseType.ACCESS_DENIED,
          summary ?? 'service.default-message.response-status-403'
        )
      )
    case 404:
      return Promise.resolve(
        new ResponseData(
          ResponseType.NOT_FOUND,
          summary ?? 'service.default-message.response-status-403'
        )
      )
    default:
      return Promise.resolve(new ResponseData(ResponseType.UNDEFINED))
  }
}

async function handleRequestError(error) {
  if (error.message === 'Network Error') {
    return Promise.resolve(
      new ResponseData(ResponseType.NETWORK_ERROR, 'service.default-message.api-error-network')
    )
  } else {
    return Promise.resolve(
      new ResponseData(ResponseType.NETWORK_ERROR, 'service.default-message.api-error-client')
    )
  }
}

export const ResponseType = {
  SUCCESS: Symbol(0),
  NETWORK_ERROR: Symbol(1),
  CLIENT_ERROR: Symbol(1),
  UNAUTHORIZED: Symbol(2),
  ACCESS_DENIED: Symbol(2),
  NOT_FOUND: Symbol(2),
  UNDEFINED: Symbol(2)
}

export class ResponseData {
  #state
  #message
  #payload

  constructor(state, message, payload) {
    this.#state = state
    this.#message = message
    this.#payload = payload
  }

  get payload() {
    return this.#payload
  }

  get message() {
    return this.#message
  }

  get state() {
    return this.#state
  }
}

export default function (settings = { auth: false }) {
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
