import { request } from '@/common/http'
import api from '@/constants/api'
import { StorageManager } from '@/helper'
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
    StorageManager.updateTokenInfo(token, expiration, authorities)
    StorageManager.updateSigninState()
  }

  async logout() {
    await request({ auth: true }).delete(api.AUTH.TOKEN_REVOKE)
    StorageManager.reset()
    return Promise.resolve()
  }
}
