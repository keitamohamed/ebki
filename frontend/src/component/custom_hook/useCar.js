import {useContext, useEffect, useState} from "react";
import {MappingType, Path} from "../../client/apirequest/Path";
import {REQUEST_MAPPING} from "../../client/apirequest/Request";
import {useFetch} from "./useFetch";
import {AuthContext} from "../../context/Context";
import useFile from "./useFile";

const useCar = (carClick, validateCarInput) => {
    const {dropZone, uploadFile} = useFile()
    const authCtx = useContext(AuthContext)
    const {car} = useFetch()

    const initCarUpdate = (carClick) => {
        return {
            brand: carClick.brand,
            model: carClick.model,
            bodyType: carClick.bodyType,
            year: carClick.year
        }
    }

    const initCar = () => {
        return carClick !== null && carClick !== undefined ? initCarUpdate(carClick) : {
            brand: '',
            model: '',
            bodyType: '',
            year: 0,
        }
    }
    const [newCar, setNewCar] = useState(initCar)

    const [message, setMessage] = useState({msg: ''})
    const [error, setError] = useState({})

    const handleChange = event => {
        const {name, value} = event.target
        setNewCar({
            ...newCar,
            [name]: value,
        })
    }

    const onSubmit = async event => {
        event.preventDefault();

        if (!isInputValidate()) {
            return
        }
        const {access_token} = authCtx.cookie
        const response = await REQUEST_MAPPING(MappingType.POST_MAPPING, Path.ADD_NEW_CAR, access_token, newCar)
        if (response.status === 200) {
            setMessage({
                ...message,
                msg: `Successfully added ${newCar.year} ${newCar.brand} ${newCar.model}`
            })
            const {headers: {vin}} = response
            uploadFile(access_token, vin, true, false)
        }
    }

    const onUpdateSubmit = async event => {
        event.preventDefault()
        if (!isInputValidate()) {
            return
        }
        const {access_token} = authCtx.cookie
        await REQUEST_MAPPING(MappingType.PUT_MAPPING, `${Path.UPDATE_CAR_INFO}${carClick.vin}`, access_token, newCar)
        await REQUEST_MAPPING(MappingType.DELETE_MAPPING, `${Path.CAR_DELETE_IMAGE}${carClick.vin}`, access_token)
        uploadFile(access_token, carClick.vin, true, true)
    }

    const isInputValidate = () => {
        setError(validateCarInput(newCar))
        return Object.values(error).every(x => x === '');
    }

    useEffect(() => {
        if (carClick !== null) {
            setNewCar(initCar())
        }
        dropZone()
    }, [carClick])

    return {car, handleChange, onSubmit, onUpdateSubmit, error, message}
}

export {
    useCar
}
