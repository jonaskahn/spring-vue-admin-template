import { request } from '@/common/http'
import api from '@/constants/api'
import constants from '@/constants'
import logger from '@/common/logger'

export default class AuthService {
  async login(params) {
    try {
      const data = await request().post(api.AUTH.TOKEN_REQUEST, params)
      localStorage.setItem(constants.TOKEN.ACCESS_TOKEN, data.payload.accessToken)
      localStorage.setItem(constants.TOKEN.ACCESS_TOKEN_EXPIRED, data.payload.accessTokenExpiredAt)
      return true
    } catch (e) {
      logger.debug(e)
      return false
    }
  }
}
