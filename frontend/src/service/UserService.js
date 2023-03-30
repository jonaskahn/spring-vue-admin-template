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
        secure: false,
        redirectOnerror: false,
        showToast: false
      }
    )
    if (res.ok) {
      LocalStorageManager.reset()
    }
    return res.payload
  }
}
