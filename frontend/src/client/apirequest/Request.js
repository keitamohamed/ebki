import axios from "axios";


const POST_REQUEST = async (url, token, data) => {
    try {
        return await axios({
            method: 'POST',
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
const GET_REQUEST = async (url, token, id) => {
    try {
        return await axios({
            method: 'GET',
            url: `${url}${id ? id : ''}`,
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
    GET_REQUEST
}
