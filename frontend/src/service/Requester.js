import axios from 'axios';
import Constant from '@/constants/default';

export class Requester {
    static init(status = { auth: false }) {
        axios.defaults.baseURL = import.meta.env.VITE_BASE_API_URL;
        axios.defaults.headers.post['Content-Type'] = 'application/json';
        if (status.auth) {
            const token = sessionStorage.getItem(Constant.API_TOKEN);
            axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
        }
        return axios;
    }
}
