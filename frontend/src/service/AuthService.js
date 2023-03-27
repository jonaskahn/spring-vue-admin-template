import { request } from '@/common/http'
import api from '@/constants/api'
import { LocalStorageManager } from '@/helper'
import jwt_decode from 'jwt-decode'

export default class AuthService {
  async login(params) {
    const res = await request().post(api.AUTH.TOKEN_REQUEST, params)
    if (res.ok) {
      this.#updateAccessTokenInfo(res)
    }
    return res.ok
  }

  #updateAccessTokenInfo(res) {
    const token = res.payload.accessToken
    const expiration = res.payload.accessTokenExpiredAt
    const authorities = jwt_decode(res.payload.accessToken)['x-authority']
    LocalStorageManager.updateTokenInfo(token, expiration, authorities)
    LocalStorageManager.updateSigninState()
  }

  async logout() {
    await request({ auth: true }).delete(api.AUTH.TOKEN_REVOKE)
    LocalStorageManager.reset()
    return Promise.resolve()
  }
}
