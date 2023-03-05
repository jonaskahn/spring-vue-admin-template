import axios from 'axios'
import { sendErrorMessage } from '@/helper/Toast'
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
      return response
    },
    function (error) {
      if (error.response) {
        sendErrorMessage(error)
      } else if (error.request) {
        sendErrorMessage('Your request is invalid')
      } else {
        sendErrorMessage('Something wrong', 'Incorrect API CAll')
      }
      logger.debug(error)
      return Promise.reject(error)
    }
  )
  return instance
}

export function request(settings = { auth: false }) {
  return settings.auth ? auth : anonymous
}

export function upload(settings = { auth: false }) {
  return settings.auth ? authUpload : anonymousUpload
}
