import axios from 'axios'
import constants from '@/constants'
import { sendErrorMessage } from '@/helper/Toast'
import { app } from '@/main'

const log = app.config.globalProperties.$log

const instance = axios.create()
instance.defaults.baseURL = import.meta.env.VITE_API_REQUEST_URL
instance.defaults.timeout = import.meta.env.VITE_API_REQUEST_TIMEOUT
instance.interceptors.response.use(
  function (response) {
    return response
  },
  function (error) {
    log.debug(error)
    sendErrorMessage(error)
    return Promise.reject(error)
  }
)

export default function (settings = { contentType: 'application/json', auth: false }) {
  instance.interceptors.request.use((config) => {
    if (settings.auth) {
      config.headers.Authorization = `Bearer ${localStorage.getItem(constants.TOKEN.ACCESS_TOKEN)}`
    }
    config.headers['Content-Type'] = settings.contentType
  })
  return instance
}
