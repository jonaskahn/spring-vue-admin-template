import axios from 'axios';
import Constant from '@/constants/default';

const instance = axios.create({
    baseURL: import.meta.env.VITE_BASE_API_URL
})
export class Requester {
    static init(status = { auth: false }) {
        instance.defaults.headers.post['Content-Type'] = 'application/json';
        if (status.auth) {
            const token = sessionStorage.getItem(Constant.API_TOKEN);
            instance.defaults.headers.common['Authorization'] = 'Bearer ' + token;
        }
        return instance;
    }
}
