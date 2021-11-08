import {useState} from "react";
import {Path, MappingType} from "../../client/apirequest/Path";
import {POST_REQUEST} from "../../client/apirequest/Request";
import {useGetData} from "./useGetData";

const useCar = (validateCarInput) => {
    const {car} = useGetData()

    const [newCar, setNewCar] = useState({
        brand: '',
        model: '',
        bodyType: '',
        year: 0,
    })
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
        setError(validateCarInput(newCar))

        const isCarValid = Object.values(error).every(x => x === '')
        if (!isCarValid) {
            return
        }
        const response = await POST_REQUEST(MappingType.POST_MAPPING, Path.ADD_NEW_CAR, null, newCar)
        if (response.status === 200) {
            setMessage({
                ...message,
                msg: `Successfully added ${newCar.year} ${newCar.brand} ${newCar.model}`
            })
        }
    }

    return {car, handleChange, onSubmit, error, message}
}

export {
    useCar
}
