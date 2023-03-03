import http from '@/common/http'

export default class AuthService {
  async login(params) {
    try {
      const res = await http().post('/auth/token', params)
      return res.status
    } catch (e) {
      /* empty */
    }
  }
}
