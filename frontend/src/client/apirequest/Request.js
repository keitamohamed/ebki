import axios from "axios";


const REQUEST_MAPPING = async (action, url, id, token, data) => {
    try {
        return await axios({
            method: action,
            url: `${url}${id ? id : ''}`,
            data: data,
            headers: {
                Authorization: token ? `Bearer ${token}` : 'Bearer',
                'Content-Type': 'application/json;charset=UTF-8',
            }
        })
    } catch (error) {
        return error;
    }
}

const DELETE_REQUEST_MAPPING = async (url, token) => {
    try {
        return await axios({
            method: 'DELETE',
            url: url,
            headers: {
                Authorization: token ? `Bearer ${token}` : 'Bearer'
            }
        })
    } catch (error) {
        return error;
    }
}


const POST_REQUEST_FILE = async (action, url, token, data) => {
    try {
        return await axios({
            method: action,
            url: url,
            data: data,
            headers: {
                Authorization: token ? `Bearer ${token}` : 'Bearer',
                'Content-Type': 'multipart/form-data',
            }
        })
    } catch (error) {
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
                Authorization: token ? `Bearer ${token}` : 'Bearer',
                'Content-Type': 'application/json;charset=UTF-8',
            }
        })
    } catch (error) {
        return error;
    }
}

const GET_REQUEST = async (url, inputValue, token) => {
    try {
        return await axios({
            method: 'GET',
            url: `${url}${inputValue ? inputValue : ''}`,
            headers: {
                Authorization: token ? `Bearer ${token}` : 'Bearer'
            }
        })
    } catch (error) {
        return error;
    }
}


export {
    REQUEST_MAPPING,
    POST_REQUEST_FILE,
    GET_REQUEST,
    PUT_REQUEST,
    DELETE_REQUEST_MAPPING
}
