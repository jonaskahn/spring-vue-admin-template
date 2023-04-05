import { LocalStorageManager } from "@/helper"
import jwt_decode from "jwt-decode"
import BaseService from "@/service/BaseService"
import api from "@/constants/api"

export default class AuthService extends BaseService {
  async login(params) {
    const res = await this.request(
      {
        url: api.AUTH.TOKEN_REQUEST,
        method: "post",
        data: params
      },
      {
        secure: false,
        showToast: true
      }
    )
    if (res.state) {
      this.#updateAccessTokenInfo(res.payload)
    }
    return res.state
  }

  #updateAccessTokenInfo(data) {
    const token = data.accessToken
    const expiration = data.accessTokenExpiredAt
    const authorities = jwt_decode(data.accessToken)["x-authority"]
    LocalStorageManager.updateTokenInfo(token, expiration, authorities)
    LocalStorageManager.updateSigninState()
  }

  async logout() {
    await this.request({
      url: api.AUTH.TOKEN_REVOKE,
      method: "delete"
    })
    LocalStorageManager.reset()
    return Promise.resolve()
  }
}
