import {Requester} from "@/service/Requester";

export class AuthService extends Requester {
  async login(params) {
    console.log(params)
    const res = await Requester.init().post("aaaaa")
    console.log(res.status)
  }
}