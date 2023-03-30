import { request } from '@/common/http'
import api from '@/constants/api'
import logger from '@/common/logger'
import BaseService from '@/service/BaseService'

export default class UserService extends BaseService {
  async verifyEmail(params) {
    const res = await request().post(api.USER.VERIFY_EMAIL, params)
    logger.debug(res)
  }
}
