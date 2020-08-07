import axios from "axios";
import {initAuth, removeAuth} from "./auth";
import {Message, MessageBox} from "element-ui";
import router from "../router";

axios.interceptors.request.use(
    config => {
        config.timeout = 4000;
        return config
    },
    error => {
        MessageBox.alert(error);
        Promise.reject(error)
    }
);

axios.interceptors.response.use(
    response => {
        if (response.data.status === "error") {
            Message.error(response.data.message)
        }
        return response.data
    },
    error => {
        switch (error.response.status) {
            case 401:
                if (initAuth() != null) {
                    Message.error(error.response.data.message);
                    router.push({name: "login"});
                    removeAuth();
                }
                break;
            case 403:
                Message.error(error.response.data.message);
                break;
            default:
                Message.error(error.response.statusText);
        }
    }
);

export function get(url, params) {
    return axios({
        url,
        params,
        method: "get",
    });
}

export async function download(method, url, params, fileName) {
    axios({
        method: method,
        url: url,
        params:params,
        responseType: 'blob'
    }).then(response => {
        if (!response) {
            return;
        }
        let url = window.URL.createObjectURL(new Blob([response]));
        let link = document.createElement('a');
        link.style.display = 'none';
        link.href = url;
        link.setAttribute('download', fileName);

        document.body.appendChild(link);
        link.click();
    });
}


export function post(url, data) {
    return axios({
        url,
        data,
        method: "post",
    });
}