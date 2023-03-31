import api from '@/constants/api'
import BaseService from '@/service/BaseService'
import { LocalStorageManager } from '@/helper'

export default class UserService extends BaseService {
  async verifyEmail(params) {
    const res = await this.request(
      {
        url: api.USER.VERIFY_EMAIL,
        method: 'post',
        data: params
      },
      {
        secure: false
      }
    )
    if (res.state) {
      LocalStorageManager.reset()
    }
    return res
  }
}
