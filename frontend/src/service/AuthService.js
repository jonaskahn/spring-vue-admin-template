import { LocalStorageManager } from '@/helper'
import jwt_decode from 'jwt-decode'
import BaseService from '@/service/BaseService'
import api from '@/constants/api'

export default class AuthService extends BaseService {
  async login(params) {
    const res = await this.request(
      {
        url: api.AUTH.TOKEN_REQUEST,
        method: 'post',
        data: params
      },
      {
        secure: false,
        redirectOnerror: false,
        showToast: true
      }
    )
    if (res.ok) {
      this.#updateAccessTokenInfo(res.data)
    }
    return res.ok
  }

  #updateAccessTokenInfo(data) {
    const token = data.accessToken
    const expiration = data.accessTokenExpiredAt
    const authorities = jwt_decode(data.accessToken)['x-authority']
    LocalStorageManager.updateTokenInfo(token, expiration, authorities)
    LocalStorageManager.updateSigninState()
  }

  async logout() {
    await this.request(
      {
        url: api.AUTH.TOKEN_REVOKE,
        method: 'delete'
      },
      {
        redirectOnerror: false,
        showToast: false
      }
    )
    LocalStorageManager.reset()
    return Promise.resolve()
  }
}
