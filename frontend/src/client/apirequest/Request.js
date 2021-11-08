import axios from "axios";


const POST_REQUEST = async (action, url, token, data) => {
    try {
        return await axios({
            method: action,
            url: url,
            data: data,
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json;charset=UTF-8',
            }
        })
    }catch (error) {
        return error;
    }
}


const PUT_REQUEST = async (url, token, data) => {

    try {
        return await axios({
            method: 'PUT',
            url: url,
            data: data,
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json;charset=UTF-8',
            }
        })
    }catch (error) {
        return error;
    }
}

const GET_REQUEST = async (url, inputValue, token) => {
    try {
        return await axios({
            method: 'GET',
            url: `${url}${inputValue ? inputValue : ''}`,
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
    }catch (error) {
        return error;
    }
}


export {
    POST_REQUEST,
    GET_REQUEST,
    PUT_REQUEST
}
