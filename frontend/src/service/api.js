import axios from "axios"
import {auth} from "../store/Auth.js";

const send = async ({method='', path='', data={}, accessToken=''} = {}) => {
    const commonUrl = 'http://3.35.215.131:80/api'
    const url = commonUrl + path
    let accessTokenValue;
    const unsubscribe = auth.subscribe(value => {
        accessTokenValue = value.accessToken;
    });
    unsubscribe();

    const headers = {
        "content-type": "application/json;charset=UTF-8",
        "accept": "application/json,",
        'Authorization': accessTokenValue ==='' ?  '' : `Bearer ${accessTokenValue}`
    };

    const options = {
        method,
        url,
        headers,
        data,
        withCredentials: true,
    }

    try {
        const response = await axios(options);
        return response.data
    }
    catch(error) {
        if(error.response.status === 401) {
            alert("로그인하세요!")
        } else if(error.response.status === 404) {
            alert("그런 거 없습니다")
        } else {
            alert("내부 에러 발생")
        }
    }
}

const getApi = ({path='', accessToken=''} = {}) => {
    return send({method: 'GET', path: path, accessToken: accessToken})
}

const putApi = ({path='', data={}, accessToken=''} = {}) => {
    return send({method: 'PUT', path: path, data: data, accessToken: accessToken})
}

const patchApi = ({path='', data={}, accessToken=''} = {}) => {
    return send({method: 'PATCH', path: path, data: data, accessToken: accessToken})
}

const postApi = ({path='', data={}, accessToken=''} = {}) => {
    return send({method: 'POST', path: path, data: data, accessToken: accessToken})
}

const delApi = ({path='', data={}, accessToken=''} = {}) => {
    return send({method:'DELETE', path: path, data: data, accessToken: accessToken})
}

export {
    getApi,
    putApi,
    patchApi,
    postApi,
    delApi,
}