import { request } from '@/common/http'
import api from '@/constants/api'
import constants from '@/constants'

export default class AuthService {
  async login(params) {
    const res = await request().post(api.AUTH.TOKEN_REQUEST, params)
    if (res.ok) {
      localStorage.setItem(constants.TOKEN.ACCESS_TOKEN, res.payload.accessToken)
      localStorage.setItem(constants.TOKEN.ACCESS_TOKEN_EXPIRED, res.payload.accessTokenExpiredAt)
    }
    return res.ok
  }

  async logout() {
    const res = await request({auth: true}).delete(api.AUTH.TOKEN_REVOKE)
    if (res.ok) {
      localStorage.clear()
    }
    return res.ok
  }
}
