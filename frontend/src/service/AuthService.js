import { request, ResponseType } from '@/common/http'
import api from '@/constants/api'
import constants from '@/constants'

export default class AuthService {
  async login(params) {
    const res = await request().post(api.AUTH.TOKEN_REQUEST, params)
    if (res.type === ResponseType.SUCCESS) {
      localStorage.setItem(constants.TOKEN.ACCESS_TOKEN, res.payload.accessToken)
      localStorage.setItem(constants.TOKEN.ACCESS_TOKEN_EXPIRED, res.payload.accessTokenExpiredAt)
      return true
    }
    return false
  }
}
