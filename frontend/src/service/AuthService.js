import { request } from '@/common/http'
import api from '@/constants/api'

export default class AuthService {
  async login(params) {
    try {
      const res = await request().post(api.AUTH.TOKEN_REQUEST, params)
      console.log(res)
    } catch (e) {
      //
    }
  }
}
