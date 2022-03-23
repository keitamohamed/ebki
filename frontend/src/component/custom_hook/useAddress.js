import {useEffect, useState} from "react";
import {GET_REQUEST, REQUEST_MAPPING} from "../../client/apirequest/Request";
import {Path} from "../../client/apirequest/Path";


const useAddress = (id, driverID, token, validateAddressInput, type) => {
    const [address, setAddress] = useState({
        street: '',
        city: '',
        state: '',
        zipcode: 0
    })

    const [error, setError] = useState({})
    const [message, setMessage] = useState({})

    const onChange = event => {
        setAddress({
            ...address,
            [event.target.name]: event.target.value
        })
        setError({
            ...error,
            [event.target.name]: ''
        })
    }

    const isValidate = () => {
        setError(validateAddressInput(address))
        return Object.values(address).every(x => x === '' || x === 0)
    }

    const onSubmit = async () => {
        if (type === 'Submit') {
            if (isValidate()) {
                return
            }
            await REQUEST_MAPPING('POST', Path.ADD_NEW_ADDRESS, driverID, token, address)
                .then(response => setMessage(response.headers.message))
            return;
        }
        await REQUEST_MAPPING('PUT', Path.UPDATE_DRIVER_ADDRESS, id, token, address)
            .then(response => setMessage(response.headers.message))
    }

    useEffect(() => {
        if (id) {
            GET_REQUEST(Path.FIND_ADDRESS_BY_ID, id, token)
                .then(response => setAddress(response.data))
        }
    }, [id, message])
    return {address, onChange, onSubmit, error, message}
}

export {
    useAddress
}